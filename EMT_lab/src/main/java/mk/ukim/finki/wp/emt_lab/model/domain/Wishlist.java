package mk.ukim.finki.wp.emt_lab.model.domain;


import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;

import java.util.List;

@Entity
@Data
public class Wishlist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @ManyToMany
    private List<Book> books;

    @OneToOne
    private User user;

    public Wishlist(Long id, String title, List<Book> books, User user) {
        this.id = id;
        this.title = title;
        this.books = books;
        this.user = user;
    }

    public Wishlist() {

    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public List<Book> getBooks() {
        return books;
    }

    public User getUser() {
        return user;
    }
}
