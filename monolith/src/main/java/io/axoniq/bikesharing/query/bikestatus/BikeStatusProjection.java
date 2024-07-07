package io.axoniq.bikesharing.query.bikestatus;

import io.axoniq.bikesharing.api.messages.*;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventhandling.ResetHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

@Component
@ProcessingGroup("BikeStatus")
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

    @QueryHandler()
    public BikeStatus findOne(FindBikeStatusByIdQuery query) {
        return bikeSummaryRepository.findById(query.bikeId()).orElse(null);
    }

    @ResetHandler
    public void reset() {
        bikeSummaryRepository.deleteAll();
    }

}
