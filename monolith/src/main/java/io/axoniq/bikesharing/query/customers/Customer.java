package io.axoniq.bikesharing.query.customers;

import io.axoniq.bikesharing.api.messages.Vakgebied;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.UUID;

@Entity
public class Customer {

    @Id
    private UUID customerId;
    private String naam;
    private Vakgebied vakgebied;

    public Customer() {}
    public Customer(UUID customerId, String naam, Vakgebied vakgebied) {
        this.customerId = customerId;
        this.naam = naam;
        this.vakgebied = vakgebied;
    }
}
