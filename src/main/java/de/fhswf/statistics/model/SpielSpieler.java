package de.fhswf.statistics.model;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.Objects;

@Entity
@Table(name = "SpielSpieler")
// implements Serializable entfernt
public class SpielSpieler {

    @EmbeddedId
    protected SpielSpielerPK spielSpielerPK;

    @Max(value = 100)
    @Min(value = 0)
    @Column(name = "Punkte")
    private int punkte;

    @Max(value = 100)
    @Min(value = 0)
    private int geworfeneFreiwuerfe;

    @Max(value = 100)
    @Min(value = 0)
    private int getroffeneFreiwuerfe;

    @Max(value = 100)
    @Min(value = 0)
    private int dreiPunkteTreffer;

    @Max(value = 5)
    @Min(value = 0)
    @Column(name = "Fouls")
    private int fouls;

    @ManyToOne(optional = false)
    @JoinColumn(name = "spielId", nullable = false, insertable = false, updatable = false, referencedColumnName = "spielId")
    private Spiel spiel;

    @ManyToOne(optional = false)
    @JoinColumn(name = "spielerId", nullable = false, insertable = false, updatable = false, referencedColumnName = "spielerId")
    private Spieler spieler;

    public Spieler getSpieler() {
        return spieler;
    }

    public void setSpieler(Spieler spieler) {
        this.spieler = spieler;
    }

    public Spiel getSpiel() {
        return spiel;
    }

    public void setSpiel(Spiel spiel) {
        this.spiel = spiel;
    }


    public SpielSpieler(SpielSpielerPK spielSpielerPK) {
        this.spielSpielerPK = spielSpielerPK;
    }

    public SpielSpieler() {
    }

    public SpielSpielerPK getSpielSpielerPK() {
        return spielSpielerPK;
    }

    public void setSpielSpielerPK(SpielSpielerPK spielSpielerPK) {
        this.spielSpielerPK = spielSpielerPK;
    }

    public int getPunkte() {
        return punkte;
    }

    public void setPunkte(int punkte) {
        this.punkte = punkte;
    }

    public int getGeworfeneFreiwuerfe() {
        return geworfeneFreiwuerfe;
    }

    public void setGeworfeneFreiwuerfe(int geworfeneFreiwuerfe) {
        this.geworfeneFreiwuerfe = geworfeneFreiwuerfe;
    }

    public int getGetroffeneFreiwuerfe() {
        return getroffeneFreiwuerfe;
    }

    public void setGetroffeneFreiwuerfe(int getroffeneFreiwuerfe) {
        this.getroffeneFreiwuerfe = getroffeneFreiwuerfe;
    }

    public int getDreiPunkteTreffer() {
        return dreiPunkteTreffer;
    }

    public void setDreiPunkteTreffer(int dreiPunkteTreffer) {
        this.dreiPunkteTreffer = dreiPunkteTreffer;
    }

    public int getFouls() {
        return fouls;
    }

    public void setFouls(int fouls) {
        this.fouls = fouls;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SpielSpieler that = (SpielSpieler) o;
        return spielSpielerPK.equals(that.spielSpielerPK);
    }

    @Override
    public int hashCode() {
        return Objects.hash(spielSpielerPK);
    }

    @Override
    public String toString() {
        return "SpielSpieler{" +
                "spielSpielerPK=" + spielSpielerPK +
                ", punkte=" + punkte +
                ", geworfeneFreiwuerfe=" + geworfeneFreiwuerfe +
                ", getroffeneFreiwuerfe=" + getroffeneFreiwuerfe +
                ", dreiPunkteTreffer=" + dreiPunkteTreffer +
                ", Fouls=" + fouls +
                '}';
    }

    /**
     * Gebe die Statistiken eines Spiels für einen Spielerid als JSON-Objekt aus.
     *
     * @return (Jakarta) {@link JsonObject}.
     */
    @NotNull
    public JsonObject toJson(boolean includeSpiel, boolean includeSpieler) {
        JsonObjectBuilder spielerObject = Json.createObjectBuilder();
        JsonObjectBuilder spielObject = Json.createObjectBuilder();
        if(getSpiel() != null && includeSpiel){
           spielObject.add("id",getSpiel().getId())
                   .add("name",getSpiel().getName());

        }
        if(getSpieler() != null && includeSpieler){
            spielerObject.add("id",getSpieler().getId())
                    .add("name",getSpieler().getName());
        }

        return Json.createObjectBuilder()
                .add("spielerId", getSpielSpielerPK().getSpielerId())
                .add("spielId", getSpielSpielerPK().getSpielId())
                .add("punkte", getPunkte())
                .add("getroffeneFreiwuerfe", getGetroffeneFreiwuerfe())
                .add("geworfeneFreiwuerfe", getGeworfeneFreiwuerfe())
                .add("dreiPunkteTreffer", getDreiPunkteTreffer())
                .add("fouls", getFouls())
                .add("spiel",spielObject)
                .add("spieler",spielerObject)
                .build();
    }
}
