package com.likelion.lionlib.exception;

public class InvalidLoginException extends RuntimeException {
    public InvalidLoginException() {
        super("Invalid email or password.");
    }
}