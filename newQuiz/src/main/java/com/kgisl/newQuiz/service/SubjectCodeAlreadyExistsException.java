package com.kgisl.newQuiz.service;

public class SubjectCodeAlreadyExistsException extends RuntimeException {
    public SubjectCodeAlreadyExistsException(String message) {
        super(message);
    }
}

