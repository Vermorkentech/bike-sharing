package io.axoniq.bikesharing.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.axoniq.dataprotection.api.FieldEncryptingSerializer;
import io.axoniq.dataprotection.cryptoengine.CryptoEngine;
import io.axoniq.dataprotection.cryptoengine.jpa.JpaCryptoEngine;
import jakarta.persistence.EntityManagerFactory;
import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.CommandBusSpanFactory;
import org.axonframework.commandhandling.DuplicateCommandHandlerResolver;
import org.axonframework.commandhandling.SimpleCommandBus;
import org.axonframework.common.transaction.TransactionManager;
import org.axonframework.messaging.Message;
import org.axonframework.messaging.correlation.CorrelationDataProvider;
import org.axonframework.messaging.correlation.MessageOriginProvider;
import org.axonframework.messaging.correlation.MultiCorrelationDataProvider;
import org.axonframework.messaging.correlation.SimpleCorrelationDataProvider;
import org.axonframework.messaging.interceptors.CorrelationDataInterceptor;
import org.axonframework.serialization.Serializer;
import org.axonframework.serialization.json.JacksonSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.Arrays;

@Configuration
public class AxonConfiguration {

    @Bean
    public CorrelationDataProvider correlationDataProvider() {
        return new MultiCorrelationDataProvider<Message<?>>(
                Arrays.asList(
                        new SimpleCorrelationDataProvider("source", "intercepted"),
                        new MessageOriginProvider()
                )
        );
    }

    @Qualifier("localSegment")
    @Bean
    public SimpleCommandBus commandBus(TransactionManager txManager, org.axonframework.config.Configuration axonConfiguration,
                                       DuplicateCommandHandlerResolver duplicateCommandHandlerResolver) {
        SimpleCommandBus commandBus =
                SimpleCommandBus.builder()
                        .transactionManager(txManager)
                        .duplicateCommandHandlerResolver(duplicateCommandHandlerResolver)
                        .spanFactory(axonConfiguration.getComponent(CommandBusSpanFactory.class))
                        .messageMonitor(axonConfiguration.messageMonitor(CommandBus.class, "commandBus"))
                        .build();
        commandBus.registerHandlerInterceptor(new CorrelationDataInterceptor<>(axonConfiguration.correlationDataProviders()));
        commandBus.registerDispatchInterceptor(new MetaDataDispatchInterceptor());
        return commandBus;
    }

    @Bean
    public CryptoEngine cryptoEngine(EntityManagerFactory entityManagerFactory) {
        return new JpaCryptoEngine(entityManagerFactory);
    }

    @Bean
    @Primary
    public Serializer generalSerializer(CryptoEngine cryptoEngine, ObjectMapper objectMapper) {
        var jacksonSerializer = JacksonSerializer.builder()
                .objectMapper(objectMapper)
                .lenientDeserialization()
                .build();
        return new FieldEncryptingSerializer(cryptoEngine, jacksonSerializer, jacksonSerializer);
    }
}
