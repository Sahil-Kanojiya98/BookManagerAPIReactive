package com.hello.BOOK_API_Reactive.controller;

import com.hello.BOOK_API_Reactive.dto.BookRequestDto;
import com.hello.BOOK_API_Reactive.dto.BookResponseDto;
import com.hello.BOOK_API_Reactive.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/v1/api/books")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class BookController {

    private final BookService bookService;

    @GetMapping
    public Flux<BookResponseDto> getAllBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("/{id}")
    public Mono<BookResponseDto> getBookById(@PathVariable Integer id) {
        return bookService.getBookById(id);
    }

    @PostMapping
    public Mono<BookResponseDto> saveBook(@RequestBody BookRequestDto bookRequest) {
        return bookService.saveBook(bookRequest);
    }

    @PostMapping("/addAll")
    public Flux<BookResponseDto> addAllBooks(@RequestBody List<BookRequestDto> bookRequests) {
        return bookService.saveAllBooks(bookRequests);
    }

    @PutMapping("/{id}")
    public Mono<BookResponseDto> updateBook(@PathVariable Integer id, @RequestBody BookRequestDto bookRequest) {
        return bookService.updateBook(id, bookRequest);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteBook(@PathVariable Integer id) {
        return bookService.deleteBook(id);
    }

    @GetMapping("/search")
    public Flux<BookResponseDto> searchBooks(@RequestParam(required = false) String name,
                                             @RequestParam(required = false) Integer minPrice,
                                             @RequestParam(required = false) Integer maxPrice) {
        return bookService.searchBooks(name, minPrice, maxPrice);
    }

}