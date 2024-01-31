package io.axoniq.bikesharing.api;

public record BikeRegisteredEvent(String bikeId, String bikeType, String location) {
}
