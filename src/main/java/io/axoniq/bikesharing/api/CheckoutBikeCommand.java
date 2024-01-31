package io.axoniq.bikesharing.api;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

public record CheckoutBikeCommand(@TargetAggregateIdentifier String bikeId, String renter) {
}