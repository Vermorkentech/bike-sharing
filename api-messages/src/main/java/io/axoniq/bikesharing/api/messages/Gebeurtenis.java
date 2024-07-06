package io.axoniq.bikesharing.api.messages;

import io.axoniq.dataprotection.api.DataSubjectId;
import io.axoniq.dataprotection.api.PersonalData;

import java.util.UUID;

public class Gebeurtenis {
    @DataSubjectId
    private UUID gebeurtenisId;
    @PersonalData
    private String naam; // Bestaande uit "voornaam" + " " + "achternaam"
    private Vakgebied vakgebied;

    public Gebeurtenis(UUID gebeurtenisId, String naam, Vakgebied vakgebied) {
        this.gebeurtenisId = gebeurtenisId;
        this.naam = naam;
        this.vakgebied = vakgebied;
    }

    public UUID getGebeurtenisId() {
        return gebeurtenisId;
    }

    public String getNaam() {
        return naam;
    }

    public Vakgebied getVakgebied() {
        return vakgebied;
    }
}
