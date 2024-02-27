package io.axoniq.bikesharing.api.messages;

public record BikeReturnedEvent(String bikeId, String location) {
}
