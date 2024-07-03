package io.axoniq.bikesharing.api.messages;

import java.util.UUID;

public record Gebeurtenis(UUID gebeurtenisId, String naam, Vakgebied vakgebied) {
}
