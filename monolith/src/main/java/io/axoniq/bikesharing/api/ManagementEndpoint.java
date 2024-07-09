package io.axoniq.bikesharing.api;

import com.google.type.DateTime;
import io.axoniq.dataprotection.cryptoengine.CryptoEngine;
import org.axonframework.config.EventProcessingConfiguration;
import org.axonframework.eventhandling.StreamingEventProcessor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
    public ResponseEntity<String> resetEventProcessor(@PathVariable String processorName, @RequestParam("resetToDate") Optional<String> resetToDate) {
        Optional<StreamingEventProcessor> optionalStreamingEventProcessor =
                eventProcessingConfiguration.eventProcessor(processorName, StreamingEventProcessor.class);
        if (optionalStreamingEventProcessor.isPresent()) {
            StreamingEventProcessor eventProcessorToReset = optionalStreamingEventProcessor.get();

            eventProcessorToReset.shutDown();
            if (resetToDate.isPresent()) {
                var replayFrom = Instant.parse(resetToDate.get());
                eventProcessorToReset.resetTokens((messageSource) -> messageSource.createTokenAt(replayFrom));
            } else {
                eventProcessorToReset.resetTokens();
            }
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