package de.fhswf.statistics.model;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import jakarta.persistence.*;

import java.io.Serializable;

/**
 * @author joeyf
 * @version 1.0
 *  int statt Integer, da die Flexibilität von Integer nicht gebraucht wird und int effizienter bei Laufzeit ist.
 */
@Entity
@Table(name = "spieldetails")
public class Spieldetails implements Serializable {
    @EmbeddedId
    private SpieldetailsId spieldetailsId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "spielID", nullable = false,insertable = false, updatable = false,referencedColumnName = "spielId")
    private Spiel spiel;
    @Column(name = "VIERTEL1")
    private int viertel1;
    @Column(name = "VIERTEL2")
    private int viertel2;
    @Column(name = "VIERTEL3")
    private int viertel3;
    @Column(name = "VIERTEL4")
    private int viertel4;

    public Spieldetails(SpieldetailsId id) {
        this.spieldetailsId = id;
    }

    public Spieldetails() {
    }

    public SpieldetailsId getSpieldetailsId() {
        return spieldetailsId;
    }

    public void setSpieldetailsId(SpieldetailsId spieldetailsId) {
        this.spieldetailsId = spieldetailsId;
    }


    public Spiel getSpiel() {
        return spiel;
    }

    public void setSpiel(Spiel spiel) {
        this.spiel = spiel;
    }


    public int getViertel1() {
        return viertel1;
    }

    public void setViertel1(int viertel1) {
        this.viertel1 = viertel1;
    }


    public int getViertel2() {
        return viertel2;
    }

    public void setViertel2(int viertel2) {
        this.viertel2 = viertel2;
    }


    public int getViertel3() {
        return viertel3;
    }

    public void setViertel3(int viertel3) {
        this.viertel3 = viertel3;
    }


    public int getViertel4() {
        return viertel4;
    }

    public void setViertel4(int viertel4) {
        this.viertel4 = viertel4;
    }
    public JsonObject toJson(boolean includeSpiel){
        JsonObjectBuilder spielObject = Json.createObjectBuilder();
        if(getSpiel() != null && includeSpiel){
            spielObject.add("id",getSpiel().getId())
                    .add("name",getSpiel().getName());
        }
        return Json.createObjectBuilder()
                .add("detailsId", getSpieldetailsId().getSpielid())
                .add("enemy", getSpieldetailsId().getEnemy())
                .add("viertel1",getViertel1())
                .add("viertel2",getViertel2())
                .add("viertel3",getViertel3())
                .add("viertel4",getViertel4())
                .add("spielname",spielObject)
                .build();
    }

}