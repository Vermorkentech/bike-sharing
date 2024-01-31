package io.axoniq.bikesharing.api;

public record BikeReturnedEvent(String bikeId, String location) {
}
