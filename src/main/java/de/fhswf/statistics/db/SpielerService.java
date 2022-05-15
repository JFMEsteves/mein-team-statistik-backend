package de.fhswf.statistics.db;

import de.fhswf.statistics.model.Spieler;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.validation.constraints.NotNull;

import java.util.List;

/**
 * Klasse für die Persistierung von Spieler.
 */
@Stateless
public class SpielerService {

    @PersistenceContext
    EntityManager em;

    @NotNull
    public Spieler findById(@NotNull int id) {
        Spieler spieler = em.find(Spieler.class, id);

        if (spieler == null)
            throw new RuntimeException(String.format("Can't find spieler with id '%s'!", id));

        return spieler;
    }

    @NotNull
    public List<Spieler> findAll() {
        CriteriaBuilder cB = em.getCriteriaBuilder();
        CriteriaQuery<Spieler> criteriaQuery = cB.createQuery(Spieler.class);

        criteriaQuery.select(criteriaQuery.from(Spieler.class));

        TypedQuery<Spieler> query = em.createQuery(criteriaQuery);

        List<Spieler> resultList = query.getResultList();
        resultList.stream().forEach(s -> {
            em.refresh(s);
        });


        return resultList;
    }

    public Spieler save(@NotNull Spieler spieler) {
        em.persist(spieler);
        return spieler;
    }

    public Spieler update(@NotNull Spieler spieler) {
        return em.merge(spieler);
    }

    public void remove(@NotNull Spieler spieler) {
        spieler = em.merge(spieler);
        em.remove(spieler);
    }

}
