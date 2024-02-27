package io.axoniq.bikesharing.api.messages;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

public record ReturnBikeCommand(@TargetAggregateIdentifier String bikeId, String location) {

}
