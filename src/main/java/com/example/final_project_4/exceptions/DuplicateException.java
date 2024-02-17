package com.example.final_project_4.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
//@AllArgsConstructor
//@Getter
public class DuplicateException extends RuntimeException{
    public DuplicateException(String message) {
        super(message);
    }
}
