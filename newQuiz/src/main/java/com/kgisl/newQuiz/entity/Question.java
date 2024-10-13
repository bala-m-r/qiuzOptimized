package com.kgisl.newQuiz.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.ManyToOne;

@Entity
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    String question;

    @ManyToOne
    @JoinColumn(name = "subjectcode", referencedColumnName = "subjectcode")
    @JsonIgnore
    Subject subject;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "questionid", referencedColumnName = "id") // This will set the foreign key in the Answer table
    List<Answer> options;

    

    @Override
    public String toString() {
        return "Question [id=" + id + ", question=" + question + ", subject=" + subject + ", options=" + options + "]";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public List<Answer> getOptions() {
        return options;
    }

    public void setOptions(List<Answer> options) {
        this.options = options;
    }

    
}
