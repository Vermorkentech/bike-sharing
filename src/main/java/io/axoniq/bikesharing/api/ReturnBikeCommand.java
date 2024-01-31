package io.axoniq.bikesharing.api;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

public record ReturnBikeCommand(@TargetAggregateIdentifier String bikeId, String location) {

}
