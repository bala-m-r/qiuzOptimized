package com.kgisl.newQuiz.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kgisl.newQuiz.entity.Results;
import com.kgisl.newQuiz.repository.ResultsRepositoryCustom;
import com.kgisl.newQuiz.service.ResultsService;


@RestController
@RequestMapping("/results")
@CrossOrigin(origins = "http://localhost:4200")
public class ResultsController {

    private ResultsService resultsService;
    private ResultsRepositoryCustom resultsRepositoryCustom;

    public ResultsController(ResultsService resultsService, ResultsRepositoryCustom resultsRepositoryCustom) {
        this.resultsService = resultsService;
        this.resultsRepositoryCustom = resultsRepositoryCustom;
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Results>> getAll(){
        return resultsService.getAll();
    }

    @GetMapping("/getPage")
    public ResponseEntity<Page<Results>> getResults(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "5") int size,
        @RequestParam(defaultValue = "mark") String sortField,
        @RequestParam(defaultValue = "desc") String sortOrder
    ) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortOrder), sortField));
        Page<Results> resultsPage = resultsService.getResults(pageRequest, sortField, sortOrder);
        return ResponseEntity.ok(resultsPage);
    }

    @PostMapping("/add")
    public ResponseEntity<Results> addResults(
        @RequestParam int mark,
        @RequestParam int fullmark,
        @RequestParam Long rollno,
        @RequestParam String subjectcode
    ) {
        Results savedResult = resultsService.addResults(mark, fullmark, rollno, subjectcode);
        return ResponseEntity.ok(savedResult);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteResult(@PathVariable int id){
        resultsService.deleteResult(id);
    }

    @DeleteMapping("/deleteAll")
    public void deleteAll(){
        resultsService.deleteAll();
    }

    @GetMapping("/search/student")
    public Page<Results> searchResultsForStudent(
            @RequestParam String search,
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam String sortField,
            @RequestParam String sortOrder) {
        PageRequest pageable = PageRequest.of(page, size);
        return resultsRepositoryCustom.searchResultsForStudent(search, pageable);
    }

    @GetMapping("/search/subject")
    public Page<Results> searchResultsForSubject(
            @RequestParam String search,
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam String sortField,
            @RequestParam String sortOrder) {
        PageRequest pageable = PageRequest.of(page, size);
        return resultsRepositoryCustom.searchResultsForSubject(search, pageable);
    }

    
    
    
    
}
