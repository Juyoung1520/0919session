package com.likelion.lionlib.service;

import com.likelion.lionlib.domain.Book;
import com.likelion.lionlib.dto.BookRequest;
import com.likelion.lionlib.dto.BookResponse;
import com.likelion.lionlib.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;

    private final GlobalService globalService;

    public BookResponse addBook(BookRequest bookRequest) {
        log.info("새 책 추가: {}", bookRequest.getTitle());

        Book book = Book.builder()
                .title(bookRequest.getTitle())
                .category(bookRequest.getCategory())
                .author(bookRequest.getAuthor())
                .publisher(bookRequest.getPublisher())
                .isbn(bookRequest.getIsbn())
                .quantity(bookRequest.getQuantity())
                .build();
        Book savedBook = bookRepository.save(book);

        log.info("책저장, 책 아이디: {}", savedBook.getId());

        return BookResponse.fromEntity(savedBook);
    }

    public BookResponse getBook(Long bookId) {
        Book book = globalService.findBookById(bookId);
        return BookResponse.fromEntity(book);
    }

    public List<BookResponse> getAllBooks() {
        return bookRepository.findAll().stream()
                .map(BookResponse::fromEntity)
                .collect(Collectors.toList());
    }

    public BookResponse updateBook(Long bookId, BookRequest bookRequest) {
        Book book = globalService.findBookById(bookId);
        Book updatedBook = Book.builder()
                .id(book.getId()) // 유지된 ID로 Book 객체를 생성
                .title(bookRequest.getTitle())
                .category(bookRequest.getCategory())
                .author(bookRequest.getAuthor())
                .publisher(bookRequest.getPublisher())
                .isbn(bookRequest.getIsbn())
                .quantity(bookRequest.getQuantity())
                .build();
        Book savedBook = bookRepository.save(updatedBook);
        return BookResponse.fromEntity(savedBook);
    }

    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }
}