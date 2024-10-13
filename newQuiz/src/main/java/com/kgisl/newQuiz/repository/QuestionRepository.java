package com.kgisl.newQuiz.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kgisl.newQuiz.entity.Question;
import com.kgisl.newQuiz.entity.Subject;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Integer>{
    List<Question> findAllBySubject(Subject subject);
}
