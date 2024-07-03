package io.axoniq.bikesharing.query.customers;

import io.axoniq.bikesharing.api.messages.Gebeurtenis;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

@Component
public class CustomerProjection {

    private final CustomerRepository customerRepository;

    public CustomerProjection(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @EventHandler
    public void on(Gebeurtenis gebeurtenis) {
        var customer = new Customer(gebeurtenis.gebeurtenisId(), gebeurtenis.naam(), gebeurtenis.vakgebied());
        customerRepository.save(customer);
    }
}
