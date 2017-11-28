package com.demo.bookstore.controller;

import com.demo.bookstore.Service.BookService;
import com.demo.bookstore.domain.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;

@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    BookService bookService;

    @RequestMapping(value="/add", method = RequestMethod.POST)
    public Book addBook(@RequestBody Book book){
        return bookService.save(book);
    }

    @RequestMapping(value="/update", method = RequestMethod.POST)
    public Book updateBook(@RequestBody Book book){
        return bookService.save(book);
    }

    @RequestMapping(value="/remove", method = RequestMethod.POST)
    public ResponseEntity removeBook(@RequestBody Long id) {
        bookService.removeOne(id);

        try {
            String fileName = id + ".png";
            Files.delete(Paths.get("src/main/resources/static/image/book/" + fileName));
            return new ResponseEntity("Remove Successful", HttpStatus.OK);
        }catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity("Remove UnSuccessful", HttpStatus.BAD_REQUEST);
        }

    }

    @RequestMapping(value = "/add/image", method = RequestMethod.POST)
    public ResponseEntity uploadImage(@RequestParam("id") String id, HttpServletRequest request){
        try {
            MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
            Iterator<String> it = multipartHttpServletRequest.getFileNames();
            MultipartFile multipartFile = multipartHttpServletRequest.getFile(it.next());

            String filename = id + ".png";

            byte[] bytes = multipartFile.getBytes();
            BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File("src/main/resources/static/image/book/" + filename)));
            stream.write(bytes);
            stream.close();
            return new ResponseEntity("Upload Success!", HttpStatus.OK);

        }catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity("Upload Failed", HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/update/image", method = RequestMethod.POST)
    public ResponseEntity modifyImage(@RequestParam("id") String id, HttpServletRequest request){
        try {
            MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
            Iterator<String> it = multipartHttpServletRequest.getFileNames();
            MultipartFile multipartFile = multipartHttpServletRequest.getFile(it.next());

            String fileName = id + ".png";

            Files.delete(Paths.get("src/main/resources/static/image/book/" + fileName));

            byte[] bytes = multipartFile.getBytes();
            BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File("src/main/resources/static/image/book/" + fileName)));
            stream.write(bytes);
            stream.close();
            return new ResponseEntity("Upload Success!", HttpStatus.OK);

        }catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity("Upload Failed", HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping("/bookList")
    public List<Book> listBook() {
        return bookService.findAll();
    }

    @RequestMapping("/{id}")
    public Book getBook(@PathVariable("id") Long id) {
        return bookService.findOne(id);
    }

}
