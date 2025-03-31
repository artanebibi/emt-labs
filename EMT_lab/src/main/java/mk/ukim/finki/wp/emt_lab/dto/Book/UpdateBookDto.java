package mk.ukim.finki.wp.emt_lab.dto.Book;

import mk.ukim.finki.wp.emt_lab.model.domain.Author;
import mk.ukim.finki.wp.emt_lab.model.domain.Book;
import mk.ukim.finki.wp.emt_lab.model.enumerations.Category;

import java.util.List;
import java.util.stream.Collectors;

public record UpdateBookDto(
        Long id,
        String name,
        Category category,
        List<Long> authorIds,
        int availableCopies,
        boolean rented,
        boolean isDeleted
) {
    public static UpdateBookDto from(Book book) {
        return new UpdateBookDto(
                book.getId(),
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

    public static List<UpdateBookDto> from(List<Book> books) {
        return books.stream().map(UpdateBookDto::from).collect(Collectors.toList());
    }

    public Book toBook(List<Author> authors) {
        return new Book(id, name, category, authors, availableCopies, rented, isDeleted);
    }


}
