package com.kgisl.newQuiz.service;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.stream.Collectors;

import com.kgisl.newQuiz.entity.Answer;
import com.kgisl.newQuiz.entity.Question;
import com.kgisl.newQuiz.entity.Subject;
import com.kgisl.newQuiz.repository.AnswerRepository;
import com.kgisl.newQuiz.repository.QuestionRepository;
import com.kgisl.newQuiz.repository.SubjectRepository;

import jakarta.transaction.Transactional;

@Service
public class QuestionService {

    private QuestionRepository questionRepository;
    private SubjectRepository subjectRepository;
    private AnswerRepository answerRepository;

    QuestionService(QuestionRepository questionRepository, SubjectRepository subjectRepository, AnswerRepository answerRepository){
        this.questionRepository = questionRepository;
        this.answerRepository = answerRepository;
        this.subjectRepository = subjectRepository;
    }

    public ResponseEntity<List<Question>> getAll() {
        List<Question> questions = questionRepository.findAll();
        return new ResponseEntity<>(questions, HttpStatus.OK);
    }

    @Transactional
    public Question addQuestion(String subjectcode, Question question) {    
        Subject subject = subjectRepository.findBySubjectcode(subjectcode);
        question.setSubject(subject);
        for(Answer answer : question.getOptions()){
            answerRepository.save(answer);
        }
        return questionRepository.save(question); 
    }

    @Transactional
    public Question editQuestion(int id, String subjectcode, Question question) {    
        Subject subject = subjectRepository.findBySubjectcode(subjectcode);
        question.setId(id);
        question.setSubject(subject);
        for(Answer answer : question.getOptions()){
            answerRepository.save(answer);
        }
        return questionRepository.save(question); 
    }

    public ResponseEntity<List<Question>> getBySubjectId(int id) {
        List<Question> questions = questionRepository.findAll()
                                        .stream()
                                        .filter((question) -> question.getSubject().getId() == id)
                                        .collect(Collectors.toList());
        return new ResponseEntity<>(questions, HttpStatus.OK);
    }

    public ResponseEntity<List<Question>> getBySubjectcode(String subjectcode){
        Subject subject = subjectRepository.findBySubjectcode(subjectcode);
        List<Question> questions = questionRepository.findAllBySubject(subject);
        return new ResponseEntity<>(questions, HttpStatus.OK);
    }

    public Question get(int id){
        return questionRepository.findById(id).orElse(null);
    }

    @Transactional
    public void deleteQuestion(int id){
        questionRepository.deleteById(id);
    }
    
}
