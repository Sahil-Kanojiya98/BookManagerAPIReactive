package com.hello.BOOK_API_Reactive.repository;

import com.hello.BOOK_API_Reactive.model.Book;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface BookRepo extends ReactiveCrudRepository<Book, Integer> {

    @Query("SELECT * FROM books_tbl WHERE "+
            "(:name IS NULL OR book_name LIKE CONCAT('%', :name, '%')) AND "+
            "(:minPrice IS NULL OR book_price >= :minPrice) AND "+
            "(:maxPrice IS NULL OR book_price <= :maxPrice)")
    Flux<Book> searchBooks(@Param("name") String name,
                           @Param("minPrice") Integer minPrice,
                           @Param("maxPrice") Integer maxPrice);

}