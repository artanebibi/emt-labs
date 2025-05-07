package mk.ukim.finki.wp.emt_lab.dto.Book;

import mk.ukim.finki.wp.emt_lab.model.domain.Author;
import mk.ukim.finki.wp.emt_lab.model.domain.Book;
import mk.ukim.finki.wp.emt_lab.model.enumerations.Category;

import java.util.List;
import java.util.stream.Collectors;

public record DisplayBookDto(
        String name,
        Category category,
        List<Long> authors,
        int availableCopies,
        boolean rented,
        boolean isDeleted
) {

    public static DisplayBookDto from(Book book) {
        return new DisplayBookDto(
                book.getName(),
                book.getCategory(),
                book.getAuthors()
                        .stream()
                        .map(Author::getId)
                        .collect(Collectors.toList()),
                book.getAvailableCopies(),
                book.isRented(),
                book.isDeleted()
        );
    }

    public static List<DisplayBookDto> from(List<Book> books) {
        return books.stream()
                .map(DisplayBookDto::from)
                .collect(Collectors.toList());
    }

    public Book toBook(List<Author> authorEntities) {
        return new Book(
                this.name,
                this.category,
                authorEntities,
                this.availableCopies,
                this.rented,
                this.isDeleted
        );
    }
}
