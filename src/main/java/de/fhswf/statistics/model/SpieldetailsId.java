package de.fhswf.statistics.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class SpieldetailsId implements Serializable {

    @NotNull
    @Column(name = "spielID", nullable = false)
    private int spielid;

    @NotNull
    @Column(name = "enemy", nullable = false)
    private int enemy;

    public SpieldetailsId(@NotNull int spielid, int enemy) {
        this.spielid = spielid;
        this.enemy = enemy;
    }

    public SpieldetailsId() {
    }

    public int getSpielid() {
        return spielid;
    }

    public void setSpielid(int spielid) {
        this.spielid = spielid;
    }


    public int getEnemy() {
        return enemy;
    }

    public void setEnemy(int enemy) {
        this.enemy = enemy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SpieldetailsId entity = (SpieldetailsId) o;
        return Objects.equals(this.enemy, entity.enemy) &&
                Objects.equals(this.spielid, entity.spielid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(enemy, spielid);
    }

}