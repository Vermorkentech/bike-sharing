package io.axoniq.bikesharing.api.messages;

import org.axonframework.serialization.Revision;

import java.util.UUID;

@Revision("1")
public class Gebeurtenis {
    private UUID gebeurtenisId;
    private String voornaam;
    private String achternaam;
    private Beroep beroep;

    public Gebeurtenis(UUID gebeurtenisId, String voornaam, String achternaam, Beroep beroep) {
        this.gebeurtenisId = gebeurtenisId;
        this.voornaam = voornaam;
        this.achternaam = achternaam;
        this.beroep = beroep;
    }

    public UUID getGebeurtenisId() {
        return gebeurtenisId;
    }

    public String getVoornaam() {
        return voornaam;
    }

    public String getAchternaam() {
        return achternaam;
    }

    public Beroep getBeroep() {
        return beroep;
    }
}
