package io.axoniq.bikesharing.api.messages;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;


@Entity
public class BikeStatus {
    
    @Id
    private String bikeId;
    private String bikeType;
    private String location;
    private String checkedOutBy;
    private Availability status;
    private Integer timesUsed;

    public BikeStatus() {
    }

    public BikeStatus(String bikeId, String bikeType, String location) {
        this.bikeId = bikeId;
        this.bikeType = bikeType;
        this.location = location;
        this.status = Availability.AVAILABLE;
        this.timesUsed = 0;
    }

    public String getBikeId() {
        return bikeId;
    }

    public String getLocation() {
        return location;
    }

    public void returned(String location) {
        this.checkedOutBy = null;
        this.status = Availability.AVAILABLE;
        this.location = location;
    }

    public String getCheckedOutBy() {
        return checkedOutBy;
    }

    public void requestedBy(String renter) {
        this.checkedOutBy = renter;
    }

    public void checkedOutBy(String checkedOutBy) {
        this.checkedOutBy = checkedOutBy;
        this.status = Availability.NOT_AVAILABLE;
    }

    public void registerUsage() {
        timesUsed++;
    }

    public Availability getStatus() {
        return status;
    }

    public String description() {
        switch (status) {
            case NOT_AVAILABLE:
                return String.format("Bike %s was rented by %s in %s", bikeId, checkedOutBy, location);
            case AVAILABLE:
                return String.format("Bike %s is available for rental in %s.", bikeId, location);
            default:
                return "Status unknown";
        }
    }

    public String getBikeType() {
        return bikeType;
    }
}
