package com.likelion.lionlib.exception;

public class BookNotFoundException extends RuntimeException {


    public BookNotFoundException(Long bookId) {
        super("Book: " + bookId + " 를 찾을 수 없습니다.");
    }

}