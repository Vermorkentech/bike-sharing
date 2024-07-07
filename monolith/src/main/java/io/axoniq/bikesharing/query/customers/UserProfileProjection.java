package io.axoniq.bikesharing.query.customers;

import io.axoniq.bikesharing.api.messages.Gebeurtenis;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

@Component
@ProcessingGroup("UserProfile")
public class UserProfileProjection {

    private final UserProfileRepository userProfileRepository;

    public UserProfileProjection(UserProfileRepository userProfileRepository) {
        this.userProfileRepository = userProfileRepository;
    }

    @EventHandler
    public void on(Gebeurtenis gebeurtenis) {
        userProfileRepository.save(new UserProfile(gebeurtenis.getGebeurtenisId(), gebeurtenis.getNaam(), gebeurtenis.getVakgebied()));
    }
}
