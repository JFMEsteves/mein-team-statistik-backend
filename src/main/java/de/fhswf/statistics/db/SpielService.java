package de.fhswf.statistics.db;


import de.fhswf.statistics.model.Spiel;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.validation.constraints.NotNull;

import java.util.List;

/**
 * Klasse für die Persistierung von Spiel.
 */
@Stateless
public class SpielService {
    @PersistenceContext
    EntityManager em;

    @NotNull
    public Spiel findById(@NotNull int id) {
        Spiel spiel = em.find(Spiel.class, id);

        if (spiel == null)
            throw new RuntimeException(String.format("Can't find spiel with id '%s'!", id));

        return spiel;
    }

    @NotNull
    public List<Spiel> findAll() {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Spiel> criteriaQuery = criteriaBuilder.createQuery(Spiel.class);
        criteriaQuery.select(criteriaQuery.from(Spiel.class));
        TypedQuery<Spiel> query = em.createQuery(criteriaQuery);
        List<Spiel> resultList = query.getResultList();
        resultList.stream().forEach(s -> em.refresh(s));
        return resultList;
    }

    public Spiel update(@NotNull Spiel spiel) {
        return em.merge(spiel);
    }

    public Spiel save(@NotNull Spiel spiel) {
        em.persist(spiel);
        return spiel;
    }

    public void remove(@NotNull Spiel spiel) {
        spiel = em.merge(spiel);
        em.remove(spiel);
    }
}
