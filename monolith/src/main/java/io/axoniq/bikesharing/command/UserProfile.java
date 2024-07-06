package io.axoniq.bikesharing.command;

import io.axoniq.bikesharing.api.messages.EnrichProfileCommand;
import io.axoniq.bikesharing.api.messages.Gebeurtenis;
import org.axonframework.commandhandling.CommandHandler;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

public class UserProfile {

    @CommandHandler
    public void handle(EnrichProfileCommand command) {
        apply(new Gebeurtenis(command.userId(), command.naam(), command.vakgebied()));
    }
}
