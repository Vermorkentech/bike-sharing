package io.axoniq.bikesharing.api.messages;

import java.security.InvalidParameterException;

public record FindBikeStatusByIdQuery(String bikeId) {
    public FindBikeStatusByIdQuery {
        if (bikeId == null || bikeId.isBlank()) {
            throw new InvalidParameterException("Parameter bikeId cannot be null or blank");
        }
    }
}
