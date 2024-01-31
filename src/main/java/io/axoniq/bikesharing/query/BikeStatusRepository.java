package io.axoniq.bikesharing.query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.axoniq.bikesharing.api.BikeStatus;

@Repository
public interface BikeStatusRepository extends JpaRepository<BikeStatus, String> {
    
}
