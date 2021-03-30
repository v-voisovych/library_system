package com.voisovych.library.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.voisovych.library.model.Book;
import com.voisovych.library.model.User;
import com.voisovych.library.myexception.MyException;
import com.voisovych.library.repository.BookRepository;
import com.voisovych.library.service.BookService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(BookController.class)
public class BookControllerTest {

    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @MockBean
    private BookRepository bookRepository;

    @InjectMocks
    private BookController bookController;

    @Test
    public void allBooksTest() throws Exception, MyException {
        List<Book> list = new ArrayList<>();
        Book book = new Book("b1", "Robin Hood", "Austin Liz");
        list.add(book);
        given(bookService.allBooks()).willReturn(list);
        mockMvc.perform(get("/allBooks")
                .contentType(APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].bookNumber", is(book.getBookNumber())))
                .andExpect(jsonPath("$[0].author", is(book.getAuthor())))
                .andExpect(jsonPath("$[0].title", is(book.getTitle())));
        verify(bookService, times(1)).allBooks();

    }

    @Test
    public void addBookTest() throws Exception, MyException {
        Book book = new Book();
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(book);
        mockMvc.perform(put("/addBook").contentType(APPLICATION_JSON_UTF8)
                .content(requestJson))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("Book has added Successfully!!!"));
        verify(bookService, times(1)).addBook(book);
    }

    @Test
    public void deleteTest() throws Exception, MyException {
        mockMvc.perform(delete("/deleteBook")
                .param("bookNumber", "b1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("Book has deleted Successfully!!!"));
        verify(bookService, times(1)).deleteBook("b1");
    }

    @Test
    public void findBookTest() throws Exception, MyException {
        Book book = new Book("b1", "Robin Hood", "Austin Liz");
        when(bookService.findBook("b1")).thenReturn(book);
        mockMvc.perform(get("/findBook")
                .param("bookNumber", "b1"))
                .andExpect(status().isOk())
                .andExpect(content().string("{\"id\":0,\"bookNumber\":\"b1\",\"author\":\"Robin Hood\",\"title\":\"Austin Liz\",\"status\":null}"))
                .andDo(print());
        Assert.assertEquals(book, bookService.findBook("b1"));
        verify(bookService, times(2)).findBook("b1");
    }

    @Test
    public void editBookTest() throws Exception, MyException {
        Book book = new Book();
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(book);
        mockMvc.perform(post("/editBook").contentType(APPLICATION_JSON_UTF8)
                .content(requestJson))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("Book has edited Successfully!!!"));
        verify(bookService, times(1)).editBook(book);
    }

    @Test
    public void takeBookTest() throws Exception, MyException {
        mockMvc.perform(post("/takeBook")
                .param("bookNumber", "b1")
                .param("userNumber", "u1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("Book has taken Successfully!!!"));
        verify(bookService, times(1)).takeBook("b1", "u1");
    }

    @Test
    public void returnBookTest() throws Exception, MyException {
        mockMvc.perform(post("/returnBook")
                .param("bookNumber", "b1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("Book has returned Successfully!!!"));
        verify(bookService, times(1)).returnBook("b1");
    }
}