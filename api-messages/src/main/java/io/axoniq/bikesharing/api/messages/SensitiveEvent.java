package io.axoniq.bikesharing.api.messages;

import io.axoniq.dataprotection.api.DataSubjectId;
import io.axoniq.dataprotection.api.PersonalData;

import java.util.UUID;

public class SensitiveEvent {

    @DataSubjectId
    private UUID gebeurtenisId;
    @PersonalData
    private String bsn;

    public SensitiveEvent(UUID gebeurtenisId, String bsn) {
        this.gebeurtenisId = gebeurtenisId;
        this.bsn = bsn;
    }

    public UUID getGebeurtenisId() {
        return gebeurtenisId;
    }

    public String getBsn() {
        return bsn;
    }
}
