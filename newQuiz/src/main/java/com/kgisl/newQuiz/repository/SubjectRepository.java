package com.kgisl.newQuiz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kgisl.newQuiz.entity.Subject;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Integer>{

    public Subject findBySubjectcode(String subjectcode);

    public boolean existsBySubjectcode(String subjectcode);
    
}
