package com.voisovych.library.controller;

import com.voisovych.library.model.Book;
import com.voisovych.library.myexception.MyException;
import com.voisovych.library.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/allBooks")
    public List<Book> allBooks() throws MyException {
        return bookService.allBooks();
    }

    @PutMapping("/addBook")
    public ResponseEntity<String> addBook(@RequestBody Book book) throws MyException {
        bookService.addBook(book);
        return ResponseEntity.ok("Book has added Successfully!!!");
    }

    @DeleteMapping("/deleteBook")
    public ResponseEntity<String> deleteBook(@RequestParam String bookNumber) throws MyException {
        bookService.deleteBook(bookNumber);
        return ResponseEntity.ok("Book has deleted Successfully!!!");
    }

    @GetMapping("/findBook")
    public Book findBook(@RequestParam String bookNumber) throws MyException {
        return bookService.findBook(bookNumber);
    }

    @PostMapping("/editBook")
    public ResponseEntity<String> editBook(@RequestBody Book book) throws MyException {
        bookService.editBook(book);
        return ResponseEntity.ok("Book has edited Successfully!!!");
    }

    @PostMapping("/takeBook")
    public ResponseEntity<String> takeBook(@RequestParam String bookNumber, @RequestParam String userNumber) throws MyException {
        bookService.takeBook(bookNumber, userNumber);
        return ResponseEntity.ok("Book has taken Successfully!!!");
    }

    @PostMapping("/returnBook")
    public ResponseEntity<String> returnBook(@RequestParam String bookNumber) throws MyException {
        bookService.returnBook(bookNumber);
        return ResponseEntity.ok("Book has returned Successfully!!!");
    }
}
