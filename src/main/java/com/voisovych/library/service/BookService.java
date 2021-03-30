package com.voisovych.library.service;

import com.voisovych.library.model.Book;
import com.voisovych.library.model.Status;
import com.voisovych.library.model.User;
import com.voisovych.library.myexception.MyException;
import com.voisovych.library.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final UserService userService;

    @Autowired
    public BookService(BookRepository bookRepository, UserService userService) {
        this.bookRepository = bookRepository;
        this.userService = userService;
    }

    public List<Book> allBooks() throws MyException {
        List<Book> list = bookRepository.findAll();
        if(list.isEmpty()) {
            throw new MyException("Library is empty");
        }
        return list;
    }

    public void addBook(Book book) throws MyException {
        if(bookRepository.findByBookNumber(book.getBookNumber()).isEmpty()) {
            book.setStatus(Status.FREE);
            bookRepository.save(book);
        }else throw new MyException("Book with book number: " + book.getBookNumber() + " exists!!!!!");
    }

    public void deleteBook(String bookNumber) throws MyException {
        bookRepository.delete(findBook(bookNumber));
    }

    public void editBook(Book book) throws MyException {
        Book b = findBook(book.getBookNumber());
        book.setId(b.getId());
        bookRepository.save(book);
    }

    public Book findBook(String bookNumber) throws MyException {
        Optional<Book> optionalBook = bookRepository.findByBookNumber(bookNumber);
        Book book = optionalBook.orElseThrow(() ->
                new MyException("Book with book number: " + bookNumber + " doesn't exist"));
        return book;
    }

    public void takeBook(String bookNumber, String userNumber) throws MyException {
        Book book = findBook(bookNumber);
        if(book.getStatus().equals(Status.TAKEN)) {
            throw new MyException("Book was taken by user: " + book.getUser().getUserNumber() +
                    " " + book.getUser().getLastName() + " " + book.getUser().getFirstName());
        }
        User user = userService.findUser(userNumber);
        book.setUser(user);
        book.setStatus(Status.TAKEN);
        bookRepository.save(book);
    }

    public void returnBook(String bookNumber) throws MyException {
        Book book = findBook(bookNumber);
        if(book.getStatus().equals(Status.FREE)) {
            throw new MyException("Book book is free!!!!!");
        }
        book.setStatus(Status.FREE);
        book.setUser(null);
        bookRepository.save(book);
    }
}
