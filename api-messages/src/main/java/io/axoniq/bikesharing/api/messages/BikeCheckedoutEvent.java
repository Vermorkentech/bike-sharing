package io.axoniq.bikesharing.api.messages;

public record BikeCheckedoutEvent(String bikeId, String renter, String sharingReference) {
}
