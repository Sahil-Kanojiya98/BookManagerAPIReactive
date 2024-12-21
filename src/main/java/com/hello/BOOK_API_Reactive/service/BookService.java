package com.hello.BOOK_API_Reactive.service;

import com.hello.BOOK_API_Reactive.dto.BookRequestDto;
import com.hello.BOOK_API_Reactive.dto.BookResponseDto;
import com.hello.BOOK_API_Reactive.model.Book;
import com.hello.BOOK_API_Reactive.repository.BookRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Delayed;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepo bookRepo;

    public Flux<BookResponseDto> getAllBooks() {
        return bookRepo.findAll().map(this::toDto).delayElements(Duration.ofSeconds(1));
    }

    public Mono<BookResponseDto> getBookById(Integer id) {
        return bookRepo.findById(id).map(this::toDto);
    }

    public Mono<BookResponseDto> saveBook(BookRequestDto bookRequest) {
        Book book = toEntity(bookRequest);
        return bookRepo.save(book).map(this::toDto);
    }

    public Flux<BookResponseDto> saveAllBooks(List<BookRequestDto> bookRequests) {
        List<Book> books = bookRequests.stream().map(this::toEntity).collect(Collectors.toList());
        return bookRepo.saveAll(books).map(this::toDto);
    }

    public Mono<BookResponseDto> updateBook(Integer id, BookRequestDto bookRequest) {
        return bookRepo.findById(id)
                .flatMap(existingBook -> {
                    Optional.ofNullable(bookRequest.getName())
                            .ifPresent(existingBook::setName);
                    Optional.of(bookRequest.getPrice()).filter(price -> price > 0)
                            .ifPresent(existingBook::setPrice);
                    Optional.ofNullable(bookRequest.getAuthor())
                            .ifPresent(existingBook::setAuthor);
                    Optional.ofNullable(bookRequest.getPublicationDate())
                            .ifPresent(existingBook::setPublicationDate);
                    return bookRepo.save(existingBook);
                })
                .map(this::toDto);
    }

    public Mono<Void> deleteBook(Integer id) {
        return bookRepo.findById(id).flatMap(bookRepo::delete);
    }

    public Flux<BookResponseDto> searchBooks(String name, Integer minPrice, Integer maxPrice) {
        return bookRepo.searchBooks(name, minPrice, maxPrice).map(this::toDto).delayElements(Duration.ofSeconds(1));
    }

    private Book toEntity(BookRequestDto dto) {
        return Book.builder()
                .name(dto.getName())
                .price(dto.getPrice())
                .author(dto.getAuthor())
                .publicationDate(dto.getPublicationDate())
                .build();
    }

    private BookResponseDto toDto(Book book) {
        return BookResponseDto.builder()
                .id(book.getId())
                .name(book.getName())
                .price(book.getPrice())
                .author(book.getAuthor())
                .publicationDate(book.getPublicationDate())
                .build();
    }

}
