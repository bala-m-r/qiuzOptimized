package com.kgisl.newQuiz.service;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.kgisl.newQuiz.entity.Subject;
import com.kgisl.newQuiz.repository.SubjectRepository;

import jakarta.transaction.Transactional;

@Service
public class SubjectService {

    private SubjectRepository subjectRepository;

    SubjectService(SubjectRepository subjectRepository){
        this.subjectRepository = subjectRepository;
    }

    public ResponseEntity<List<Subject>> getAllSubjects() {
        List<Subject> subjects = subjectRepository.findAll();
        return new ResponseEntity<>(subjects, HttpStatus.OK);
    }

    public Subject addSubject(Subject subject){
        if (subjectRepository.existsBySubjectcode(subject.getSubjectcode())) {
            throw new SubjectCodeAlreadyExistsException("Subject code already exists: " + subject.getSubjectcode());
        }
        return subjectRepository.save(subject);
    }

    @Transactional
    public void deleteSubject(int id){
        subjectRepository.deleteById(id);
    }

    public ResponseEntity<Subject> getById(int id) {
        Subject subject = subjectRepository.findById(id).orElse(null);
        return new ResponseEntity<>(subject, HttpStatus.OK);
    }

    @Transactional
    public void edit(int id, Subject subject) {
        subjectRepository.save(subject);
    }
    
}
