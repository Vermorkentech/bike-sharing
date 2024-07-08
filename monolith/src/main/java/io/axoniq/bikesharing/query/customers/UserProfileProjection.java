package io.axoniq.bikesharing.query.customers;

import io.axoniq.bikesharing.api.messages.Gebeurtenis;
import io.axoniq.bikesharing.api.messages.SensitiveEvent;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventhandling.ResetHandler;
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
        userProfileRepository.save(
                new UserProfile(gebeurtenis.getGebeurtenisId(),
                        gebeurtenis.getVoornaam() + " " + gebeurtenis.getAchternaam(),
                        gebeurtenis.getBeroep()
                )
        );
    }

    @EventHandler
    public void on(SensitiveEvent event) {
        userProfileRepository.findById(event.getGebeurtenisId())
                .ifPresent(userProfile -> {
                    userProfile.setBsn(event.getBsn());
                    userProfileRepository.save(userProfile);
                });
    }

    @ResetHandler
    public void reset() {
        userProfileRepository.deleteAll();
    }
}
