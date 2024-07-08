package io.axoniq.bikesharing.upcaster;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import org.axonframework.serialization.SimpleSerializedType;
import org.axonframework.serialization.upcasting.event.IntermediateEventRepresentation;
import org.axonframework.serialization.upcasting.event.SingleEventUpcaster;
import org.springframework.stereotype.Component;

@Component
public class GebeurtenisUpcaster0_to_1 extends SingleEventUpcaster {

    private static final String EVENT_TYPE = "io.axoniq.bikesharing.api.messages.Gebeurtenis";
    private static final String TO_REVISION = "1";

    @Override
    protected boolean canUpcast(IntermediateEventRepresentation intermediateRepresentation) {
        return intermediateRepresentation.getType().getName().equals(EVENT_TYPE)
                && intermediateRepresentation.getType().getRevision() == null;
    }

    @Override
    protected IntermediateEventRepresentation doUpcast(IntermediateEventRepresentation intermediateRepresentation) {
        return intermediateRepresentation.upcastPayload(
                new SimpleSerializedType(EVENT_TYPE, TO_REVISION),
                JsonNode.class,
                jsonNode -> {
                    var event = (ObjectNode) jsonNode;

                    var naam = event.get("naam").textValue();
                    String[] split = naam.split(" ");
                    event.set("voornaam", new TextNode(split[0]));
                    event.set("achternaam", new TextNode(split[1]));

                    event.set("beroep", event.get("vakgebied"));
                    return event;
                }
        );
    }
}
