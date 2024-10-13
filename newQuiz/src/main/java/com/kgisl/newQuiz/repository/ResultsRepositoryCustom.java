package com.kgisl.newQuiz.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.ArrayList;

import com.kgisl.newQuiz.entity.Results;
import com.kgisl.newQuiz.entity.Student;
import com.kgisl.newQuiz.entity.Subject;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

@Repository
public class ResultsRepositoryCustom {

    private EntityManager entityManager;

    ResultsRepositoryCustom(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Page<Results> searchResultsForStudent(String searchQuery, Pageable pageable) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Results> cq = cb.createQuery(Results.class);
        Root<Results> result = cq.from(Results.class);

        List<Predicate> predicates = new ArrayList<>();
        if (searchQuery != null && !searchQuery.isEmpty()) {
            Predicate studentNamePredicate = cb.like(cb.lower(result.get("student").get("name")),
                    "%" + searchQuery.toLowerCase() + "%");
            Predicate rollNoPredicate = cb.like(cb.lower(result.get("student").get("rollno").as(String.class)),
                    "%" + searchQuery + "%");
            Predicate markPredicate = cb.like(result.get("mark").as(String.class), "%" + searchQuery + "%");

            predicates.add(cb.or(studentNamePredicate, rollNoPredicate, markPredicate));
        }

        cq.where(predicates.toArray(new Predicate[0]));
        cq.orderBy(cb.asc(result.get("mark"))); // Sort by mark or dynamically based on sortOrder

        List<Results> resultList = entityManager.createQuery(cq)
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();

        long total = getTotalResultsForStudent(searchQuery);

        return new PageImpl<>(resultList, pageable, total);
    }

    public Page<Results> searchResultsForSubject(String searchQuery, Pageable pageable) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Results> cq = cb.createQuery(Results.class);
        Root<Results> result = cq.from(Results.class);

        List<Predicate> predicates = new ArrayList<>();
        if (searchQuery != null && !searchQuery.isEmpty()) {
            Predicate subjectCodePredicate = cb.like(cb.lower(result.get("subject").get("subjectcode")),
                    "%" + searchQuery.toLowerCase() + "%");
            Predicate subjectNamePredicate = cb.like(cb.lower(result.get("subject").get("subject")),
                    "%" + searchQuery.toLowerCase() + "%");
            Predicate markPredicate = cb.like(result.get("mark").as(String.class), "%" + searchQuery + "%");

            predicates.add(cb.or(subjectCodePredicate, subjectNamePredicate, markPredicate));
        }

        cq.where(predicates.toArray(new Predicate[0]));
        cq.orderBy(cb.asc(result.get("mark"))); // Sort by mark or dynamically based on sortOrder

        List<Results> resultList = entityManager.createQuery(cq)
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();

        long total = getTotalResultsForSubject(searchQuery);

        return new PageImpl<>(resultList, pageable, total);
    }

    private long getTotalResultsForStudent(String searchQuery) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<Results> result = cq.from(Results.class);

        List<Predicate> predicates = new ArrayList<>();
        if (searchQuery != null && !searchQuery.isEmpty()) {
            Predicate studentNamePredicate = cb.like(cb.lower(result.get("student").get("name")),
                    "%" + searchQuery.toLowerCase() + "%");
            Predicate rollNoPredicate = cb.like(cb.lower(result.get("student").get("rollno").as(String.class)),
                    "%" + searchQuery + "%");
            Predicate markPredicate = cb.like(result.get("mark").as(String.class), "%" + searchQuery + "%");

            predicates.add(cb.or(studentNamePredicate, rollNoPredicate, markPredicate));
        }

        cq.select(cb.count(result)).where(predicates.toArray(new Predicate[0]));

        return entityManager.createQuery(cq).getSingleResult();
    }

    private long getTotalResultsForSubject(String searchQuery) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<Results> result = cq.from(Results.class);

        List<Predicate> predicates = new ArrayList<>();
        if (searchQuery != null && !searchQuery.isEmpty()) {
            Predicate subjectCodePredicate = cb.like(cb.lower(result.get("subject").get("subjectcode")),
                    "%" + searchQuery.toLowerCase() + "%");
            Predicate subjectNamePredicate = cb.like(cb.lower(result.get("subject").get("subject")),
                    "%" + searchQuery.toLowerCase() + "%");
            Predicate markPredicate = cb.like(result.get("mark").as(String.class), "%" + searchQuery + "%");

            predicates.add(cb.or(subjectCodePredicate, subjectNamePredicate, markPredicate));
        }

        cq.select(cb.count(result)).where(predicates.toArray(new Predicate[0]));

        return entityManager.createQuery(cq).getSingleResult();
    }

    public Integer getIdByRollnoAndSubjectcode(Long rollno, String subjectcode) {

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Integer> query = cb.createQuery(Integer.class);
        Root<Results> resultRoot = query.from(Results.class);

        Join<Results, Student> studentJoin = resultRoot.join("student");
        Join<Results, Subject> subjectJoin = resultRoot.join("subject");

        query.select(resultRoot.get("id"))
                .where(
                        cb.equal(studentJoin.get("rollno"), rollno),
                        cb.equal(subjectJoin.get("subjectcode"), subjectcode)
                    );

        List<Integer> resultIds = entityManager.createQuery(query).getResultList();

        if(resultIds.size() != 0){
            return resultIds.get(0);
        }
        return null;

        
    }

}
