package io.axoniq.bikesharing.api;

import io.axoniq.bikesharing.api.messages.*;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.MetaData;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadLocalRandom;


@RestController
@RequestMapping("/bikes")
public class BikeController {


    public static final String FIND_ALL_QUERY = "findAll";
    public static final String FIND_ONE_QUERY = "findOne";
    private static final List<String> LOCATIONS = Arrays.asList("Amsterdam", "Paris", "Vilnius", "Barcelona", "London", "New York", "Toronto", "Berlin", "Milan", "Rome", "Belgrade");

    private final CommandGateway commandGateway;
    private final QueryGateway queryGateway;

    public BikeController(CommandGateway commandGateway, QueryGateway queryGateway) {
        this.commandGateway = commandGateway;
        this.queryGateway = queryGateway;
    }

    @PostMapping
    public CompletableFuture<Void> generateBikes(@RequestParam("bikes") int bikeCount,
                                                 @RequestParam(value = "bikeType") String bikeType) {
        CompletableFuture<Void> all = CompletableFuture.completedFuture(null);
        for (int i = 0; i < bikeCount; i++) {
            all = CompletableFuture.allOf(all,
                    commandGateway.send(new RegisterBikeCommand(UUID.randomUUID().toString(), bikeType, randomLocation())));
        }
        return all;
    }

    @GetMapping()
    public CompletableFuture<List<BikeStatus>> findAll() {
        return queryGateway.query(FIND_ALL_QUERY, null, ResponseTypes.multipleInstancesOf(BikeStatus.class));
    }

    @GetMapping("/{bikeId}")
    public CompletableFuture<BikeStatus> findById(@PathVariable("bikeId") String bikeId) {
        var query = new FindBikeStatusByIdQuery(bikeId);
        return queryGateway.query(query, ResponseTypes.instanceOf(BikeStatus.class));

    }

    @PatchMapping("/{bikeId}/checkout/{borrower-name}")
    public CompletableFuture<Void> checkoutBike(@PathVariable("bikeId") String bikeId,
                                                @PathVariable("borrower-name") String borrowerName) {
        return this.commandGateway.send(new CheckoutBikeCommand(bikeId, borrowerName));
    }


    @PatchMapping("/{bikeId}/return/{location-name}")
    public CompletableFuture<Void> returnBike(@PathVariable("bikeId") String bikeId,
                                              @PathVariable("location-name") String locationName) {
        return this.commandGateway.send(new ReturnBikeCommand(bikeId, locationName));
    }

    @PatchMapping("/{bikeId}/enrichProfile")
    public CompletableFuture<Void> enrichProfile(@PathVariable("bikeId") String bikeId) {
        return this.commandGateway.send(new EnrichProfileCommand(bikeId, UUID.randomUUID(), "Jan Janssen", Vakgebied.ONTWIKKELAAR, "12345678"),
                MetaData.with("source", "BikeController"));
    }

    private String randomLocation() {
        return LOCATIONS.get(ThreadLocalRandom.current().nextInt(LOCATIONS.size()));
    }


}
