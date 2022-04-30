package de.fhswf.statistics.model;


import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Objects;
@Embeddable
public class SpielSpielerPK implements Serializable {

    @Column(name = "spielerId")
    private int spielerId;


    @Column(name = "spielId")
    private int spielId;

    public SpielSpielerPK(int spielerId, int spielId) {
        this.spielerId = spielerId;
        this.spielId = spielId;
    }

    public SpielSpielerPK() {
    }

    public int getSpielerId() {
        return spielerId;
    }

    public void setSpielerId(int spielerID) {
        this.spielerId = spielerID;
    }

    public int getSpielId() {
        return spielId;
    }

    public void setSpielId(int spielID) {
        this.spielId = spielID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SpielSpielerPK that = (SpielSpielerPK) o;
        return spielerId == that.spielerId && spielId == that.spielId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(spielerId, spielId);
    }

    @Override
    public String toString() {
        return "SpielSpielerPK{" +
                "spielerId=" + spielerId +
                ", spielId=" + spielId +
                '}';
    }
}
