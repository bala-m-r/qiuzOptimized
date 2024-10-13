package com.kgisl.newQuiz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kgisl.newQuiz.entity.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {

    public Student findByRollno(Long rollno);

	public String getNameByRollno(Long rollno);
    
}
