package io.axoniq.bikesharing.api;

import org.axonframework.config.EventProcessingConfiguration;
import org.axonframework.eventhandling.StreamingEventProcessor;
import org.axonframework.eventhandling.TrackingEventProcessor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/ops")
public class ManagementEndpoint {

    private final EventProcessingConfiguration eventProcessingConfiguration;

    public ManagementEndpoint(EventProcessingConfiguration eventProcessingConfiguration) {
        this.eventProcessingConfiguration = eventProcessingConfiguration;
    }

    @PostMapping("/eventProcessor/{processorName}/reset")
    public ResponseEntity<String> resetEventProcessor(@PathVariable String processorName) {
        Optional<StreamingEventProcessor> optionalStreamingEventProcessor =
                eventProcessingConfiguration.eventProcessor(processorName, StreamingEventProcessor.class);
        if (optionalStreamingEventProcessor.isPresent()) {
            StreamingEventProcessor eventProcessorToReset = optionalStreamingEventProcessor.get();

            eventProcessorToReset.shutDown();
            eventProcessorToReset.resetTokens();
            eventProcessorToReset.start();

            return ResponseEntity.ok()
                    .body(String.format("Event Processor [%s] has been reset", processorName));
        } else {
            return ResponseEntity.badRequest()
                    .body(String.format(
                            "Event Processor [%s] is not a streaming event processor, hence cannot reset.",
                            processorName
                    ));
        }
    }
}