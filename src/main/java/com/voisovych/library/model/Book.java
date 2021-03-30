package com.voisovych.library.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @Column(name = "book_number")
    private String bookNumber;
    @Column(name = "author")
    private String author;
    @Column(name = "title")
    private String title;
    @Enumerated(value = EnumType.STRING)
    @Column(name = "status")
    private Status status;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public Book() {
    }

    public Book(long id, String bookNumber, String author, String title, Status status, User user) {
        this.id = id;
        this.bookNumber = bookNumber;
        this.author = author;
        this.title = title;
        this.status = status;
        this.user = user;
    }

    public Book(String bookNumber, String author, String title) {
        this.bookNumber = bookNumber;
        this.author = author;
        this.title = title;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getBookNumber() {
        return bookNumber;
    }

    public void setBookNumber(String bookNumber) {
        this.bookNumber = bookNumber;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", bookNumber='" + bookNumber + '\'' +
                ", author='" + author + '\'' +
                ", title='" + title + '\'' +
                ", status=" + status +
                ", user=" + user +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return id == book.id &&
                Objects.equals(bookNumber, book.bookNumber) &&
                Objects.equals(author, book.author) &&
                Objects.equals(title, book.title) &&
                status == book.status &&
                Objects.equals(user, book.user);
    }

}
