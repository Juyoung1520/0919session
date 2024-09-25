package com.likelion.lionlib.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.likelion.lionlib.domain.Book;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder //생성자 만들 때 사용
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class BookResponse {
    private Long id;
    private String title;
    private String category;
    private String author;
    private String publisher;
    private String isbn;
    private Integer quantity;

    public static BookResponse fromEntity(Book book) {
        return BookResponse.builder()
                .id(book.getId())
                .title(book.getTitle())
                .category(book.getCategory())
                .author(book.getAuthor())
                .publisher(book.getPublisher())
                .isbn(book.getIsbn())
                .quantity(book.getQuantity())
                .build();
    }
}