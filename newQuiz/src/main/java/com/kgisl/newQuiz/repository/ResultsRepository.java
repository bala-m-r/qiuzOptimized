package com.kgisl.newQuiz.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kgisl.newQuiz.entity.Results;
import com.kgisl.newQuiz.entity.Student;
import com.kgisl.newQuiz.entity.Subject;

@Repository
public interface ResultsRepository extends JpaRepository<Results, Integer> {

    Page<Results> findAll(Pageable pageable);

    Results findByStudent(Student student);

    Results findBySubject(Subject subject);
    
}
