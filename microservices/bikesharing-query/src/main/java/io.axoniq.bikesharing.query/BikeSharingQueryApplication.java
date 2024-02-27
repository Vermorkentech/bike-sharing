package io.axoniq.bikesharing.query;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.axoniq.bikesharing.api.messages.BikeStatus;
import org.axonframework.eventhandling.tokenstore.jpa.TokenEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@EntityScan(basePackageClasses = {BikeStatus.class, TokenEntry.class})
@SpringBootApplication
public class BikeSharingQueryApplication {

    public static void main(String[] args) {
        SpringApplication.run(BikeSharingQueryApplication.class, args);
    }

    @Autowired
    public void configureSerializers(ObjectMapper objectMapper) {
        objectMapper.activateDefaultTyping(objectMapper.getPolymorphicTypeValidator(), ObjectMapper.DefaultTyping.JAVA_LANG_OBJECT);
    }
}