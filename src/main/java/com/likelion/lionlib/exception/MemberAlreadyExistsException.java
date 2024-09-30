package com.likelion.lionlib.exception;

public class MemberAlreadyExistsException extends RuntimeException {
    public MemberAlreadyExistsException(String email) {
        super(email + ": 이미 존재하는 이메일 입니다.");
    }
}
