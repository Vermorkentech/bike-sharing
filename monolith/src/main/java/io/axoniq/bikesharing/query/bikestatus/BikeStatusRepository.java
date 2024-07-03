package io.axoniq.bikesharing.query.bikestatus;
import io.axoniq.bikesharing.api.messages.BikeStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BikeStatusRepository extends JpaRepository<BikeStatus, String> {
    
}
