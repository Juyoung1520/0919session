package com.likelion.lionlib.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter //@Getter : getTitle(), getCategory(),... get~()를 자동으로 생성해줌
@NoArgsConstructor // 파라미터가 없는 디폴트 생성자 생성
//@AllArgsConstructor : 모든 필드 값을 파라미터로 받는 생성자를 생성
//@RequiredArgsConstructor : final이나 @NonNull으로 선언된 필드만을 파라미터로 받는 생성자를 생성
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class BookRequest {
    private String title;
    private String category;
    private String author;
    private String publisher;
    private String isbn;
    private int quantity;
    // 디폴트 생성자(파라미터x) 자동 생성
    // ex
    // public BookRequest(){}

    //@AllArgsConstructor라면
    // public BookRequest(String title, String category, ... , int quantity){
    // this.title = title;
    // ...
    // this.quantity = quantity;
}