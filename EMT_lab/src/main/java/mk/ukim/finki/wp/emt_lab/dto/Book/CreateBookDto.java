package mk.ukim.finki.wp.emt_lab.dto.Book;

import mk.ukim.finki.wp.emt_lab.model.domain.Author;
import mk.ukim.finki.wp.emt_lab.model.domain.Book;
import mk.ukim.finki.wp.emt_lab.model.enumerations.Category;

import java.util.List;
import java.util.stream.Collectors;

public record CreateBookDto(
        String name,
        Category category,
        List<Long> authorIds,
        int availableCopies,
        boolean rented,
        boolean isDeleted
) {
    public static CreateBookDto from(Book book) {
        return new CreateBookDto(
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

    public static List<CreateBookDto> from(List<Book> books) {
        return books.stream().map(CreateBookDto::from).collect(Collectors.toList());
    }

    public Book toBook(List<Author> authors) {
        return new Book(name, category, authors, availableCopies, rented, isDeleted);
    }
}
