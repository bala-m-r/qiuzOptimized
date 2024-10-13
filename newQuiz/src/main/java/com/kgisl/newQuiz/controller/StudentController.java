package com.kgisl.newQuiz.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kgisl.newQuiz.entity.Student;
import com.kgisl.newQuiz.service.StudentService;


@RestController
@RequestMapping("/student")
@CrossOrigin(origins = "http://localhost:4200")
public class StudentController {
    
     private StudentService studentService;

    StudentController(StudentService studentService){
        this.studentService = studentService;
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Student>> getAll() {
        return studentService.getAll();
    }

    @PostMapping("/add")
    public ResponseEntity<Student> addStudent(@RequestBody Student student){
        return ResponseEntity.ok(this.studentService.addStudent(student));
    }

    @DeleteMapping("/delete/{id}")
    public void deleteStudent(@PathVariable int id){
        studentService.deleteStudent(id);
    }

    @PutMapping("/edit/{id}")
    public void edit(@PathVariable int id, @RequestBody Student student){
        studentService.edit(id, student);
    }

}
