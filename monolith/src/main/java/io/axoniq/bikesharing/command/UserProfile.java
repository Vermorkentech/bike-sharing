package io.axoniq.bikesharing.command;

import io.axoniq.bikesharing.api.messages.EnrichProfileCommand;
import io.axoniq.bikesharing.api.messages.Gebeurtenis;
import io.axoniq.bikesharing.api.messages.SensitiveEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.messaging.MetaData;

import java.util.UUID;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

public class UserProfile {

    private UUID gebeurtenisId;

    @CommandHandler
    public void handle(EnrichProfileCommand command) {
        apply(
                new Gebeurtenis(command.userId(), command.voornaam(), command.achternaam(), command.beroep()),
                MetaData.with("randomID", UUID.randomUUID())
        );
        apply(new SensitiveEvent(gebeurtenisId, command.bsn()));
    }

    @EventSourcingHandler
    public void on(Gebeurtenis gebeurtenis) {
        gebeurtenisId = gebeurtenis.getGebeurtenisId();
    }
}
