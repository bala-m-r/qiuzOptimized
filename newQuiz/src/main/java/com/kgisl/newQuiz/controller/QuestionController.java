package com.kgisl.newQuiz.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kgisl.newQuiz.entity.Question;
import com.kgisl.newQuiz.service.QuestionService;

@RestController
@RequestMapping("/question")
@CrossOrigin(origins = "http://localhost:4200")
public class QuestionController {

    private QuestionService questionService;

    QuestionController(QuestionService questionService){
        this.questionService = questionService;
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Question>> getAll() {
        return questionService.getAll();
    }

     @GetMapping("/getBySubjectId/{id}")
    public ResponseEntity<List<Question>> getBySubjectId(@PathVariable int id) {
        return questionService.getBySubjectId(id);
    }

    @GetMapping("/getBySubjectcode/{subjectcode}")
    public ResponseEntity<List<Question>> getBySubjectcode(@PathVariable String subjectcode) {
       
        return questionService.getBySubjectcode(subjectcode);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Question> get(@PathVariable int id) {
        Question question = questionService.get(id);
        return new ResponseEntity<>(question, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Question> addQuestion(@RequestParam String subjectcode, @RequestBody Question question){
        Question responseQuestion = questionService.addQuestion(subjectcode, question);
        return ResponseEntity.ok(responseQuestion);
    }

    @PostMapping("/addList")
    public void addQuestionList(@RequestParam String subjectcode, @RequestBody List<Question> questions){
        for(Question question: questions){
           questionService.addQuestion(subjectcode, question);
        }   
    }

    @PostMapping("/edit/{id}")
    public ResponseEntity<Question> editQuestion(@PathVariable int id, @RequestParam String subjectcode, @RequestBody Question question){
        Question responseQuestion = questionService.editQuestion(id, subjectcode, question);
        return ResponseEntity.ok(responseQuestion);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteQuestion(@PathVariable int id){
        questionService.deleteQuestion(id);
    }
    
}
