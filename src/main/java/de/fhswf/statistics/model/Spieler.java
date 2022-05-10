package de.fhswf.statistics.model;

import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "Spieler")
@NamedQuery(name = "Spieler.findAll", query = "SELECT s from Spieler s left join fetch s.stats")
public class Spieler implements Serializable {


    @Id
    @Column(nullable = false, name = "spielerId")
    private int id;

    @Column(nullable = false, name = "Name")
    private String name;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "spieler", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SpielSpieler> stats = new ArrayList<>();

    public Spieler(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Spieler() {

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


    public List<SpielSpieler> getStats() {
        if (this.stats == null) this.stats = new ArrayList<>();
        return stats;
    }

    public void setStats(List<SpielSpieler> stats) {
        this.stats = stats;
    }


    public Spieler addStats(SpielSpieler stat) {
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
        return Json.createObjectBuilder()
                .add("id", getId())
                .add("name", getName())
                .add("stats", statsArray)
                .build();
    }

    @NotNull
    public JsonObject toJson() {
        return this.toJson(true);
    }

}
