package io.axoniq.bikesharing.api.messages;

public record BikeRegisteredEvent(String bikeId, String bikeType, String location) {
}
