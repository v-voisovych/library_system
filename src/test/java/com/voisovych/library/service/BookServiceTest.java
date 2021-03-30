package com.voisovych.library.service;

import com.voisovych.library.model.Book;
import com.voisovych.library.model.Status;
import com.voisovych.library.model.User;
import com.voisovych.library.myexception.MyException;
import com.voisovych.library.repository.BookRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BookServiceTest {

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private  BookRepository bookRepository;
    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private  UserService userService;

    @InjectMocks
    BookService bookService;

    @Test
    public void allBooksTest() throws MyException {
        List<Book> list = new ArrayList<>();
        Book bookOne =  new Book("b1", "Robin Hood", "Austin Liz");
        Book bookTwo = new Book("b2", "Blueberries for Sal", "Robert McCloskey");
        list.add(bookOne);
        list.add(bookTwo);

        when(bookRepository.findAll()).thenReturn(list);
        Assert.assertEquals(list, bookService.allBooks());
        verify(bookRepository, times(1)).findAll();
    }

    @Test(expected = MyException.class)
    public void allBooksWithExceptionTest() throws MyException {
        List<Book> list = new ArrayList<>();
        when(bookRepository.findAll()).thenReturn(list);
        bookService.allBooks();
        verify(bookRepository, times(1)).findAll();
    }

    @Test
    public void addBookTest() throws MyException {
        Book bookOne =  new Book("b1", "Robin Hood", "Austin Liz");
        Book bookTwo =  new Book("b1", "Robin Hood", "Austin Liz");
        bookTwo.setStatus(Status.FREE);
        when(bookRepository.findByBookNumber(bookOne.getBookNumber())).thenReturn(Optional.empty());
        when(bookRepository.save(bookOne)).thenReturn(bookOne);
        bookService.addBook(bookOne);
        Assert.assertEquals(bookTwo, bookRepository.save(bookOne));
        verify(bookRepository, times(2)).save(bookOne);
        verify(bookRepository,times(1)).findByBookNumber(bookOne.getBookNumber());
    }

    @Test(expected = MyException.class)
    public void addBookWithExceptionTest() throws MyException {
        Book book =  new Book("b1", "Robin Hood", "Austin Liz");
        when(bookRepository.findByBookNumber(book.getBookNumber())).thenReturn(Optional.ofNullable(book));
        bookService.addBook(book);
        verify(bookRepository, never()).save(book);
        verify(bookRepository,times(1)).findByBookNumber(book.getBookNumber());
    }

    @Test
    public void deleteBookTest() throws MyException {
        Book book =  new Book("b1", "Robin Hood", "Austin Liz");
        when(bookRepository.findByBookNumber(book.getBookNumber())).thenReturn(Optional.ofNullable(book));
        bookService.deleteBook("b1");
        verify(bookRepository, times(1)).delete(book);
    }

    @Test(expected = MyException.class)
    public void deleteBookWithException() throws MyException {
        when(bookRepository.findByBookNumber("b1")).thenReturn(Optional.empty());
        bookService.deleteBook("b1");
    }

    @Test
    public void editBookTest() throws MyException {
        Book bookOne =  new Book("b1", "Robin Hood", "Austin Liz");
        bookOne.setId(1l);
        Book bookTwo =  new Book("b1", "Robin Hood", "Austin Liz");
        when(bookRepository.findByBookNumber(bookOne.getBookNumber())).thenReturn(Optional.ofNullable(bookOne));
        bookService.editBook(bookTwo);
        Assert.assertEquals(bookTwo, bookOne);
        verify(bookRepository, times(1)).save(bookTwo);
    }

    @Test(expected = MyException.class)
    public void editBookWithException() throws MyException {
        Book book =  new Book("b1", "Robin Hood", "Austin Liz");
        when(bookRepository.findByBookNumber("b1")).thenReturn(Optional.empty());
        bookService.editBook(book);
    }

    @Test
    public void findBookTest() throws MyException {
        Book book =  new Book("b1", "Robin Hood", "Austin Liz");
        when(bookRepository.findByBookNumber("b1")).thenReturn(Optional.ofNullable(book));
        Assert.assertEquals(book, bookService.findBook("b1"));
        verify(bookRepository, times(1)).findByBookNumber("b1");
    }

    @Test(expected = MyException.class)
    public void findBookTestWithException() throws MyException {
        when(bookRepository.findByBookNumber("b1")).thenReturn(Optional.empty());
        bookService.findBook("b1");
    }

    @Test
    public void takeBookTest() throws MyException {
        User user = new User("u1", "Robin", "Hood");
        Book bookOne =  new Book("b1", "Robin Hood", "Austin Liz");
        bookOne.setStatus(Status.FREE);
        Book bookTwo =  new Book("b1", "Robin Hood", "Austin Liz");
        bookTwo.setStatus(Status.TAKEN);
        bookTwo.setUser(user);
        when(bookRepository.findByBookNumber("b1")).thenReturn(Optional.ofNullable(bookOne));
        when(userService.findUser("u1")).thenReturn(user);
        bookService.takeBook("b1", "u1");
        Assert.assertEquals(bookTwo, bookOne);
        verify(bookRepository,times(1)).save(bookOne);
    }

    @Test(expected = MyException.class)
    public void takeBookWithExceptionTest() throws MyException {
        User user = new User("u1", "Robin", "Hood");
        Book book =  new Book("b1", "Robin Hood", "Austin Liz");
        book.setStatus(Status.TAKEN);
        book.setUser(user);
        when(bookRepository.findByBookNumber("b1")).thenReturn(Optional.ofNullable(book));
        bookService.takeBook("b1", "u1");
    }

    @Test
    public void returnBookTest() throws MyException {
        User user = new User("u1", "Robin", "Hood");
        Book bookOne =  new Book("b1", "Robin Hood", "Austin Liz");
        bookOne.setStatus(Status.TAKEN);
        bookOne.setUser(user);
        Book bookTwo =  new Book("b1", "Robin Hood", "Austin Liz");
        bookTwo.setStatus(Status.FREE);
        when(bookRepository.findByBookNumber("b1")).thenReturn(Optional.ofNullable(bookOne));
        bookService.returnBook("b1");
        Assert.assertEquals(bookTwo, bookOne);
        verify(bookRepository,times(1)).save(bookOne);
    }

    @Test(expected = MyException.class)
    public void returnBookWithExceptionTest() throws MyException {
        Book book =  new Book("b1", "Robin Hood", "Austin Liz");
        book.setStatus(Status.FREE);
        when(bookRepository.findByBookNumber("b1")).thenReturn(Optional.ofNullable(book));
        bookService.returnBook("b1");
    }
}