package com.hello.BOOK_API_Reactive.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;

@Data
@Table(name = "books_tbl")
@Builder
public class Book {
    @Id
    @Column("book_id")
    private Integer id;

    @Column("book_name")
    private String name;

    @Column("book_price")
    private int price;

    @Column("book_author")
    private String author;

    @Column("publication_date")
    private LocalDate publicationDate;
}