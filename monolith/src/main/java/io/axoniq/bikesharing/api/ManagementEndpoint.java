package io.axoniq.bikesharing.api;

import io.axoniq.dataprotection.cryptoengine.CryptoEngine;
import org.axonframework.config.EventProcessingConfiguration;
import org.axonframework.eventhandling.StreamingEventProcessor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/ops")
public class ManagementEndpoint {

    private final EventProcessingConfiguration eventProcessingConfiguration;
    private final CryptoEngine cryptoEngine;

    public ManagementEndpoint(EventProcessingConfiguration eventProcessingConfiguration, CryptoEngine cryptoEngine) {
        this.eventProcessingConfiguration = eventProcessingConfiguration;
        this.cryptoEngine = cryptoEngine;
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

    @DeleteMapping("/gdpr/delete/{key}")
    public ResponseEntity<Void> deleteKey(@PathVariable String key) {
        cryptoEngine.deleteKey(key);
        return ResponseEntity.ok().build();
    }
}