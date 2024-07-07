package io.axoniq.bikesharing.query.customers;

import io.axoniq.bikesharing.api.messages.Vakgebied;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.UUID;

@Entity
public class UserProfile {

    @Id
    private UUID userId;
    private String naam;
    private Vakgebied vakgebied;

    public UserProfile() {}
    public UserProfile(UUID userId, String naam, Vakgebied vakgebied) {
        this.userId = userId;
        this.naam = naam;
        this.vakgebied = vakgebied;
    }
}
