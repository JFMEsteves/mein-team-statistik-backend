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
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "Spiel")
public class Spiel implements Serializable {
    @Id
    @Column(name = "spielId", nullable = false)
   // @SequenceGenerator(name = "sequence", sequenceName = "jakarta_seq", allocationSize = 1)
  //  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq")
    private int id;

    @Column(nullable = false, name = "Datum")
    private LocalDate datum;
    @Column(name = "Name")
    private String name;


    @Min(value = 0)
    @Max(value = 999)
    @Column(name = "GegnerPunkte")
    private int gegnerPunkte;

    @Column(name = "UnserePunkte")
    @Min(value = 0)
    @Max(value = 999)
    private int unserePunkte;

    @OneToMany(mappedBy = "spiel", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SpielSpieler> stats = new ArrayList<>();

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

    public List<SpielSpieler> getStats() {
        if (this.stats == null) this.stats = new ArrayList<>();
        return stats;
    }


    public void setStats(List<SpielSpieler> stats) {
        this.stats = stats;
    }

    public Spiel addStats(SpielSpieler stat) {
        if (this.stats == null)
            this.stats = new ArrayList<>();
        if (!stats.contains(stat))
            stats.add(stat);
        return this;
    }

    public JsonObject toJson(boolean includeStats) {
        JsonArrayBuilder statsArray = Json.createArrayBuilder();
        if (getStats() != null && includeStats) {
            getStats().stream()
                    .collect(Collectors.toList())
                    .forEach(q -> statsArray.add(q.toJson()));
        }
        //Aussehen der JSON Datei
        return Json.createObjectBuilder()
                .add("id", getId())
                .add("name", getName())
                .add("datum", DateConverter.DateToString(getDatum()))
                .add("unserePunkte", getUnserePunkte())
                .add("gegnerPunkte", getGegnerPunkte())
                .add("stats", statsArray)
                .build();
    }

    @NotNull
    public JsonObject toJson() {
        return this.toJson(true);
    }


}
