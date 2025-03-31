package mk.ukim.finki.wp.emt_lab.model.domain;

import jakarta.persistence.*;
import lombok.Data;
import mk.ukim.finki.wp.emt_lab.model.enumerations.Category;

import java.util.List;

@Data
@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private Category category;

    @ManyToMany
    private List<Author> authors;

    private int availableCopies;

    private boolean rented;

    private boolean isDeleted;

    public Book(Long id, String name, Category category, List<Author> authors, int availableCopies, boolean rented, boolean isDeleted) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.authors = authors;
        this.availableCopies = availableCopies;
        this.rented = rented;
        this.isDeleted = isDeleted;
    }

    public Book() {
    }

    public Book(String name, Category category, List<Author> allById, int availableCopies, boolean rented, boolean deleted) {
        this.name = name;
        this.category = category;
        this.authors = allById;
        this.availableCopies = availableCopies;
        this.rented = rented;
        this.isDeleted = deleted;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public void setAvailableCopies(int availableCopies) {
        this.availableCopies = availableCopies;
    }

    public void setRented(boolean rented) {
        this.rented = rented;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Category getCategory() {
        return category;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public int getAvailableCopies() {
        return availableCopies;
    }

    public boolean isRented() {
        return rented;
    }

    public boolean isDeleted() {
        return isDeleted;
    }
}