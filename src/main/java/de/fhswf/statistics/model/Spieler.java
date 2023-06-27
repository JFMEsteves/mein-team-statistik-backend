package de.fhswf.statistics.model;

import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;
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
    private Set<SpielSpieler> stats = new LinkedHashSet<>();

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


    public Set<SpielSpieler> getStats() {
        if (this.stats == null) this.stats = new LinkedHashSet<>();
        return stats;
    }

    public void setStats(Set<SpielSpieler> stats) {
        this.stats = stats;
    }

    /**
     *
     * @param stat, dank Set ist es nicht nötig bspw. ein stats.contains if zu setzen.
     */
    public Spieler addStats(SpielSpieler stat) {
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
                    .forEach(q -> statsArray.add(q.toJson(true,false)));
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
