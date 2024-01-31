package io.axoniq.bikesharing.query;

import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

import io.axoniq.bikesharing.api.BikeCheckedoutEvent;
import io.axoniq.bikesharing.api.BikeRegisteredEvent;
import io.axoniq.bikesharing.api.BikeReturnedEvent;
import io.axoniq.bikesharing.api.BikeStatus;

@Component
public class BikeStatusProjection {
    
    private final BikeStatusRepository bikeSummaryRepository;
    
    public BikeStatusProjection(BikeStatusRepository bikeSummaryRepository) {
        this.bikeSummaryRepository = bikeSummaryRepository;
    }

    @EventHandler
    public void on(BikeRegisteredEvent event) {
        var bikeSummary = new BikeStatus(event.bikeId(), event.bikeType(), event.location());
        bikeSummaryRepository.save(bikeSummary);
    }

    @EventHandler
    public void on(BikeCheckedoutEvent event) {
        bikeSummaryRepository.findById(event.bikeId())
                            .map(bs -> {
                                bs.checkedOutBy(event.renter());
                                return bs;
                            });
    }

    @EventHandler
    public void on(BikeReturnedEvent event) {
        bikeSummaryRepository.findById(event.bikeId())
                            .map(bs -> {
                                bs.returned(event.location());
                                return bs;
                            });
    }

    @QueryHandler(queryName = "findAll")
    public Iterable<BikeStatus> findAll() {
        return bikeSummaryRepository.findAll();
    }

    @QueryHandler(queryName = "findOne")
    public BikeStatus findOne(String bikeId) {
        return bikeSummaryRepository.findById(bikeId).orElse(null);
    }


}
