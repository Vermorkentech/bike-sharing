package io.axoniq.bikesharing.query.customers;

import io.axoniq.bikesharing.api.messages.Beroep;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.UUID;

@Entity
public class UserProfile {

    @Id
    private UUID userId;
    private String naam;
    private Beroep beroep;
    private String bsn;

    public UserProfile() {}
    public UserProfile(UUID userId, String naam, Beroep beroep) {
        this.userId = userId;
        this.naam = naam;
        this.beroep = beroep;
    }

    public void setBsn(String bsn) {
        this.bsn = bsn;
    }
}
