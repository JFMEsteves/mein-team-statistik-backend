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

    //Viertel Punkte eigenes Team
    @Min(value = 0)
    @Max(value = 50)
    @Column(name = "ErstesViertelTeam")
    private int erstesViertelTeam;
    @Min(value = 0)
    @Max(value = 50)
    @Column(name = "ZweitesViertelTeam")
    private int zweitesViertelTeam;
    @Min(value = 0)
    @Max(value = 50)
    @Column(name = "DrittesViertelTeam")
    private int drittesViertelTeam;

    @Min(value = 0)
    @Max(value = 50)
    @Column(name = "ViertesViertelTeam")
    private int viertesViertelTeam;

    //Viertel Punkte Gegner
    @Min(value = 0)
    @Max(value = 50)
    @Column(name = "ErstesViertelGegner")
    private int erstesViertelGegner;


    @Min(value = 0)
    @Max(value = 50)
    @Column(name = "ZweitesViertelGegner")
    private int zweitesViertelGegner;
    @Min(value = 0)
    @Max(value = 50)
    @Column(name = "DrittesViertelGegner")
    private int drittesViertelGegner;
    @Min(value = 0)
    @Max(value = 50)
    @Column(name = "ViertesViertelGegner")
    private int viertesViertelGegner;


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

    public int getErstesViertelTeam() {
        return erstesViertelTeam;
    }

    public void setErstesViertelTeam(int erstesViertelTeam) {
        this.erstesViertelTeam = erstesViertelTeam;
    }

    public int getZweitesViertelTeam() {
        return zweitesViertelTeam;
    }

    public void setZweitesViertelTeam(int zweitesViertelTeam) {
        this.zweitesViertelTeam = zweitesViertelTeam;
    }

    public int getDrittesViertelTeam() {
        return drittesViertelTeam;
    }

    public void setDrittesViertelTeam(int drittesViertelTeam) {
        this.drittesViertelTeam = drittesViertelTeam;
    }

    public int getViertesViertelTeam() {
        return viertesViertelTeam;
    }

    public void setViertesViertelTeam(int viertesViertelTeam) {
        this.viertesViertelTeam = viertesViertelTeam;
    }

    public int getErstesViertelGegner() {
        return erstesViertelGegner;
    }

    public void setErstesViertelGegner(int erstesViertelGegner) {
        this.erstesViertelGegner = erstesViertelGegner;
    }

    public int getZweitesViertelGegner() {
        return zweitesViertelGegner;
    }

    public void setZweitesViertelGegner(int zweitesViertelGegner) {
        this.zweitesViertelGegner = zweitesViertelGegner;
    }

    public int getDrittesViertelGegner() {
        return drittesViertelGegner;
    }

    public void setDrittesViertelGegner(int drittesViertelGegner) {
        this.drittesViertelGegner = drittesViertelGegner;
    }

    public int getViertesViertelGegner() {
        return viertesViertelGegner;
    }

    public void setViertesViertelGegner(int viertesViertelGegner) {
        this.viertesViertelGegner = viertesViertelGegner;
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
                    .forEach(q -> statsArray.add(q.toJson(false, true)));
        }
        //Aussehen der JSON Datei
        return Json.createObjectBuilder()
                .add("id", getId())
                .add("name", getName())
                .add("datum", DateConverter.DateToString(getDatum()))
                .add("unserePunkte", getUnserePunkte())
                .add("gegnerPunkte", getGegnerPunkte())
                .add("erstesViertelTeam", getErstesViertelTeam())
                .add("zweitesViertelTeam", getZweitesViertelTeam())
                .add("drittesViertelTeam", getDrittesViertelTeam())
                .add("viertesViertelTeam", getViertesViertelTeam())
                .add("erstesViertelGegner",getErstesViertelGegner())
                .add("zweitesViertelGegner",getZweitesViertelGegner())
                .add("drittesViertelGegner",getDrittesViertelGegner())
                .add("viertesViertelGegner",getViertesViertelGegner())
                .add("stats", statsArray)
                .build();
    }

    @NotNull
    public JsonObject toJson() {
        return this.toJson(true);
    }


}
