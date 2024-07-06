package io.axoniq.bikesharing.config;

import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.messaging.MessageDispatchInterceptor;
import org.axonframework.messaging.MetaData;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.BiFunction;

public class MetaDataDispatchInterceptor implements MessageDispatchInterceptor<CommandMessage<?>> {
    @NotNull
    @Override
    public BiFunction<Integer, CommandMessage<?>, CommandMessage<?>> handle(@NotNull List<? extends CommandMessage<?>> messages) {
        return (integer, commandMessage) -> commandMessage.andMetaData(MetaData.with("intercepted", true));
    }
}
