package io.axoniq.bikesharing.api.messages;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

public record CheckoutBikeCommand(@TargetAggregateIdentifier String bikeId, String renter) {
}