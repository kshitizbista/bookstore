package com.demo.bookstore.repository;

import com.demo.bookstore.domain.Book;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BookRepository extends CrudRepository<Book, Long>{
    List<Book> findByTitleContaining(String keyword);
}
