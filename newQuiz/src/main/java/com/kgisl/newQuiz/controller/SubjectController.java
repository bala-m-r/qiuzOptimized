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

import com.kgisl.newQuiz.entity.Subject;
import com.kgisl.newQuiz.service.SubjectService;

@RestController
@RequestMapping("/subject")
@CrossOrigin(origins = "http://localhost:4200")
public class SubjectController {

    private SubjectService subjectService;

    SubjectController(SubjectService subjectService){
        this.subjectService = subjectService;
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Subject>> getAllSubjects(){
        return subjectService.getAllSubjects();
    }

    @PostMapping("/add")
    public ResponseEntity<Subject> addSubject(@RequestBody Subject subject){
        return ResponseEntity.ok(subjectService.addSubject(subject));
    }

    @DeleteMapping("/delete/{id}")
    public void deleteSubject(@PathVariable int id){
        subjectService.deleteSubject(id);
    }

     @GetMapping("get/{id}")
    public ResponseEntity<Subject> getById(@PathVariable int id){
        return subjectService.getById(id);
    }

    @PutMapping("/edit/{id}")
    public void edit(@PathVariable int id, @RequestBody Subject subject){
        subjectService.edit(id, subject);
    }
    
}
