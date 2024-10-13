package com.kgisl.newQuiz.service;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.kgisl.newQuiz.entity.Student;
import com.kgisl.newQuiz.repository.StudentRepository;

import jakarta.transaction.Transactional;

@Service
public class StudentService {

    private StudentRepository studentRepository;

    StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student addStudent(Student student) {
        return this.studentRepository.save(student);
    }

    @Transactional
    public void deleteStudent(int id) {
        studentRepository.deleteById(id);
    }

    public ResponseEntity<List<Student>> getAll() {
        List<Student> students = studentRepository.findAll();
        return new ResponseEntity<>(students, HttpStatus.OK);
    }

    @Transactional
    public void edit(int id, Student student) {
        student.setId(id);
        studentRepository.save(student);
    }

}
