package com.generation.amsha.savingsPlan.dao;

import com.generation.amsha.savingsPlan.model.SavingsPlan;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class SavingsPlanDaoImpl implements SavingsPlanDao{
    private EntityManager entityManager;
    @Autowired
    public SavingsPlanDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    @Override
    public SavingsPlan createSavingsPlan(SavingsPlan savingsPlan) {
        entityManager.persist(savingsPlan);
        return savingsPlan;
    }

    @Override
    public SavingsPlan updateSavingsPlan(SavingsPlan savingsPlan) {
        entityManager.merge(savingsPlan);
        return savingsPlan;
    }


    @Override
    public SavingsPlan getSavingsPlanByPlanId(Integer id) {
        TypedQuery<SavingsPlan> query = entityManager.createQuery("from SavingsPlan where id = :id", SavingsPlan.class);
        query.setParameter("id", id);
        return query.getSingleResult();
    }

    @Override
    public List<SavingsPlan> getAllSavingsPlans(Integer userId, String title, String startDate, String endDate) {
        // Create CriteriaBuilder and CriteriaQuery
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<SavingsPlan> query = cb.createQuery(SavingsPlan.class);
        Root<SavingsPlan> savingsPlan = query.from(SavingsPlan.class);

        // Initialize predicate list
        List<Predicate> predicates = new ArrayList<>();

        // Filter by userId if provided
        if (userId != null) {
            predicates.add(cb.equal(savingsPlan.get("userAccount").get("id"), userId));
        }

        // Filter by title if provided
        if (title != null && !title.isEmpty()) {
            predicates.add(cb.like(cb.lower(savingsPlan.get("title")), "%" + title.toLowerCase() + "%"));
        }

        // Filter by startDate if provided
        if (startDate != null && !startDate.isEmpty()) {
            LocalDate start = LocalDate.parse(startDate);
            LocalDateTime startDateTime = start.atStartOfDay(); // Start at 00:00:00
            predicates.add(cb.greaterThanOrEqualTo(savingsPlan.get("createdAt"), startDateTime));
        }

        // Filter by endDate if provided
        if (endDate != null && !endDate.isEmpty()) {
            LocalDate end = LocalDate.parse(endDate);
            LocalDateTime endDateTime = end.atTime(23, 59, 59); // End at 23:59:59
            predicates.add(cb.lessThanOrEqualTo(savingsPlan.get("createdAt"), endDateTime));
        }

        // Combine predicates and apply them to the query
        query.select(savingsPlan).where(cb.and(predicates.toArray(new Predicate[0])));

        // Execute query and return result list
        return entityManager.createQuery(query).getResultList();
    }

}
