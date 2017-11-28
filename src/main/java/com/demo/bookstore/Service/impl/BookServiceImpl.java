package com.demo.bookstore.Service.impl;

import com.demo.bookstore.Service.BookService;
import com.demo.bookstore.domain.Book;
import com.demo.bookstore.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
   BookRepository bookRepository;

    @Override
    public List<Book> findAll() {
        /*List<Book> bookList = (List<Book>) bookRepository.findAll();
        List<Book> activeBookList = new ArrayList<>();

        bookList.forEach(book->{
            if(book.isActive()) {
                activeBookList.add(book);
            }
        });
        return activeBookList;*/

        return (List<Book>)bookRepository.findAll();
    }

    @Override
    public Book findOne(Long id) {
        return bookRepository.findOne(id);
    }

    @Override
    public Book save(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public List<Book> blurrySearch(String title) {
       /* List<Book> bookList = bookRepository.findByTitleContaining(title);
        List<Book> activeBookList = new ArrayList<>();
        bookList.forEach(book -> {
            if(book.isActive()) {
                activeBookList.add(book);
            }
        });
        return activeBookList;*/

        return (List<Book>)bookRepository.findAll();
    }

    @Override
    public void removeOne(Long id) {
        bookRepository.delete(id);
    }
}
