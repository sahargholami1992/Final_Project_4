package com.example.final_project_4.exceptions;

//@ResponseStatus(value = HttpStatus.CONFLICT, reason = "this content not found")
public class NotFoundException extends RuntimeException{

    public NotFoundException(String message) {
        super(message);
    }
}
