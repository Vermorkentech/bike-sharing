package io.axoniq.bikesharing.api;

public record BikeCheckedoutEvent(String bikeId, String renter, String sharingReference) {
}
