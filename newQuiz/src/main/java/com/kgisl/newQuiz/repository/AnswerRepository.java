package com.kgisl.newQuiz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kgisl.newQuiz.entity.Answer;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Integer>{
    
}
