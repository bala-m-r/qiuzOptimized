package com.kgisl.newQuiz.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Answer {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    String answer;

    boolean isright;

    @Override
    public String toString() {
        return "Answer [id=" + id + ", answer=" + answer + ", isright=" + isright + "]";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public boolean isIsright() {
        return isright;
    }

    public void setIsright(boolean isright) {
        this.isright = isright;
    }
    

}
