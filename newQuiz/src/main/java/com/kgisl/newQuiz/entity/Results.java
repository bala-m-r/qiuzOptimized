package com.kgisl.newQuiz.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Results {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    int mark;
    
    int fullmark;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "rollno", nullable = false, referencedColumnName = "rollno")
    private Student student;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "subjectcode", nullable = false, referencedColumnName = "subjectcode")
    private Subject subject;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    public int getFullmark() {
        return fullmark;
    }

    public void setFullmark(int fullmark) {
        this.fullmark = fullmark;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

   
}
