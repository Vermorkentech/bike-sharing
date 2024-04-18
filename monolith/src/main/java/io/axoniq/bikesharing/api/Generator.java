package io.axoniq.bikesharing.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.concurrent.ExecutionException;

@Component
@Profile("!node2")
@EnableAsync
public class Generator {

    private BikeController controller;

    Logger logger = LoggerFactory.getLogger(Generator.class);
    public Generator(BikeController controller) {
        this.controller = controller;
    }



    @Scheduled(fixedRate = 250, initialDelay = 5000)
    private void runThis() throws ExecutionException, InterruptedException {

        var bikesQuery = this.controller.findAll();

        var bikes = bikesQuery.get();

        if (bikes.size() < 500) {
            var future = this.controller.generateBikes(5, "city");
            future.get();
        }

        Random rand = new Random();
        int random = rand.nextInt(bikes.size()-1);

        var bike = bikes.get(random);

        var checkout = this.controller.checkoutBike(bike.getBikeId(), String.format("Renter {0}", random));
        checkout.get();

        var bikeStatusQuery = this.controller.findById(bike.getBikeId());
        var bikeStatus = bikeStatusQuery.get();

        var returnBike = this.controller.returnBike(bike.getBikeId(),String.format("Location {0}", random));
        returnBike.get();
        logger.info("generator complete...");
    }
}
