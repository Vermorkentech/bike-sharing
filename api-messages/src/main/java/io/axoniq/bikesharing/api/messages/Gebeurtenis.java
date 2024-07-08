package io.axoniq.bikesharing.api.messages;

import java.util.UUID;

public class Gebeurtenis {
    private UUID gebeurtenisId;
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
