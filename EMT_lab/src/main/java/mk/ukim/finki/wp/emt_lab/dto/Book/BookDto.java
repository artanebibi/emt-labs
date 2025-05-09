package mk.ukim.finki.wp.emt_lab.dto.Book;


import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToMany;
import lombok.Data;
import mk.ukim.finki.wp.emt_lab.model.enumerations.Category;

import java.util.List;

@Data
public class BookDto {
    private String name;

    @Enumerated(EnumType.STRING)
    private Category category;

    @ManyToMany
    private List<Long> authors;

    private int availableCopies;

    private boolean rented;

    private boolean isDeleted;

    public BookDto(String name, Category category, List<Long> authors, int availableCopies, boolean rented, boolean isDeleted) {
        this.name = name;
        this.category = category;
        this.authors = authors;
        this.availableCopies = availableCopies;
        this.rented = rented;
        this.isDeleted = isDeleted;
    }

    public BookDto() {
    }


    public void setRented(boolean rented) {
        this.rented = rented;
    }

    public boolean isRented() {
        return rented;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setAuthors(List<Long> authors) {
        this.authors = authors;
    }

    public void setAvailableCopies(int availableCopies) {
        this.availableCopies = availableCopies;
    }


    public String getName() {
        return name;
    }

    public Category getCategory() {
        return category;
    }

    public List<Long> getAuthors() {
        return authors;
    }

    public int getAvailableCopies() {
        return availableCopies;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }
}
