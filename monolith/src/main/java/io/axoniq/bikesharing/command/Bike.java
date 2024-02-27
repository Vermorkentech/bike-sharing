package io.axoniq.bikesharing.command;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

import io.axoniq.bikesharing.api.messages.BikeCheckedoutEvent;
import io.axoniq.bikesharing.api.messages.BikeRegisteredEvent;
import io.axoniq.bikesharing.api.messages.BikeReturnedEvent;
import io.axoniq.bikesharing.api.messages.CheckoutBikeCommand;
import io.axoniq.bikesharing.api.messages.RegisterBikeCommand;
import io.axoniq.bikesharing.api.messages.ReturnBikeCommand;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

import java.util.UUID;

@Aggregate
public class Bike {

    @AggregateIdentifier
    private String bikeId;

    private boolean isAvailable;

    public Bike() {
    }
    
    @CommandHandler
    public Bike(RegisterBikeCommand command) {
        apply(new BikeRegisteredEvent(command.bikeId(), command.bikeType(), command.location()));
    }

    @CommandHandler
    public String handle(CheckoutBikeCommand command) {
        if (!this.isAvailable) {
            throw new IllegalStateException("Bike is already rented");
        }
        String sharingReference = UUID.randomUUID().toString();
        apply(new BikeCheckedoutEvent(command.bikeId(), command.renter(), sharingReference));

        return sharingReference;
    }

    @CommandHandler
    public void handle(ReturnBikeCommand command) {
        if (this.isAvailable) {
            throw new IllegalStateException("Bike was already returned");
        }
        apply(new BikeReturnedEvent(command.bikeId(), command.location()));
    }

    @EventSourcingHandler
    protected void on(BikeRegisteredEvent event) {
        this.bikeId = event.bikeId();
        this.isAvailable = true;
    }

    @EventSourcingHandler
    protected void on(BikeCheckedoutEvent event) {
        this.isAvailable = false;
    }

    @EventSourcingHandler
    protected void on(BikeReturnedEvent event) {
        this.isAvailable = true;
    }
}
