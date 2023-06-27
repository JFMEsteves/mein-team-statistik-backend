package de.fhswf.statistics.model;

import de.fhswf.statistics.util.DateConverter;
import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "Spiel")
public class Spiel implements Serializable {
    @Id
    @Column(name = "spielId", nullable = false)
    private int id;

    @Column(nullable = false, name = "Datum")
    private LocalDate datum;
    @Column(name = "Name")
    private String name;

    @Column(name = "UnserePunkte")
    @Min(value = 0)
    @Max(value = 999)
    private int unserePunkte;
    @Min(value = 0)
    @Max(value = 999)
    @Column(name = "GegnerPunkte")
    private int gegnerPunkte;

    @OneToMany(fetch = FetchType.EAGER,mappedBy = "spiel",cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Spieldetails> spieldetails = new LinkedHashSet<>();


    @OneToMany(mappedBy = "spiel", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<SpielSpieler> stats = new LinkedHashSet<>();

    public Spiel(int id, LocalDate datum) {
        this.id = id;
        this.datum = datum;
    }

    public Spiel() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDatum() {
        return datum;
    }

    public void setDatum(LocalDate datum) {
        this.datum = datum;
    }

    public int getGegnerPunkte() {
        return gegnerPunkte;
    }

    public void setGegnerPunkte(int gegnerPunkte) {
        this.gegnerPunkte = gegnerPunkte;
    }

    public int getUnserePunkte() {
        return unserePunkte;
    }

    public void setUnserePunkte(int unserePunkte) {
        this.unserePunkte = unserePunkte;
    }

    public Set<Spieldetails> getSpieldetails() {
        return spieldetails;
    }

    public void setSpieldetails(Set<Spieldetails> spieldetails) {
        this.spieldetails = spieldetails;
    }

    public Set<SpielSpieler> getStats() {
        if (this.stats == null) this.stats = new LinkedHashSet<>();
        return stats;
    }


    public void setStats(Set<SpielSpieler> stats) {
        this.stats = stats;
    }

    public Spiel addStats(SpielSpieler stat) {
        if (this.stats == null)
            this.stats = new LinkedHashSet<>();
        stats.add(stat);
        return this;
    }

    public JsonObject toJson(boolean includeStats) {
        JsonArrayBuilder statsArray = Json.createArrayBuilder();
        if (getStats() != null && includeStats) {
            getStats().stream()
                    .collect(Collectors.toSet())
                    .forEach(q -> statsArray.add(q.toJson(false, true)));
        }
        JsonArrayBuilder HeimViertel = Json.createArrayBuilder();
        if(getSpieldetails() != null){
            getSpieldetails().stream().collect(Collectors.toSet())
                    .forEach(q -> HeimViertel.add(q.toJson(true)));
        }
        //Aussehen der JSON Datei
        return Json.createObjectBuilder()
                .add("id", getId())
                .add("name", getName())
                .add("datum", DateConverter.DateToString(getDatum()))
                .add("unserePunkte", getUnserePunkte())
                .add("gegnerPunkte", getGegnerPunkte())
                .add("viertel",HeimViertel)
                .add("stats", statsArray)
                .build();
    }

    @NotNull
    public JsonObject toJson() {
        return this.toJson(true);
    }


}
