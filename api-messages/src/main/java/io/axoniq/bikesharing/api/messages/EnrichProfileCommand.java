package io.axoniq.bikesharing.api.messages;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.UUID;

public record EnrichProfileCommand(@TargetAggregateIdentifier String bikeId, UUID userId, String naam, Vakgebied vakgebied) {
}
