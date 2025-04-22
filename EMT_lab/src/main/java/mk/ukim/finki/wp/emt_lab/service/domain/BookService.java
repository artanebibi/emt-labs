package mk.ukim.finki.wp.emt_lab.service.domain;

import mk.ukim.finki.wp.emt_lab.model.domain.Author;
import mk.ukim.finki.wp.emt_lab.model.domain.Book;
import mk.ukim.finki.wp.emt_lab.model.domain.Country;
import mk.ukim.finki.wp.emt_lab.dto.Book.BookDto;
import mk.ukim.finki.wp.emt_lab.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


public interface BookService {
    List<Book> findAll();
    Optional<Book> findById(Long id);
    void delete(Long id);
//    Book create(String name, Category category, List<Long> authorIds, int availableCopies); todo: no dto
//    Book update(Long id, String name, Category category, List<Long> authorIds, int availableCopies); todo: no dto
    Optional<Book> update(Long id, BookDto bookDto);
    Optional<Book> save(BookDto bookDto);
    Optional<Book> rentBook(Long id, BookDto bookDto);
    List<Book> findByAuthorAndBookName(String authorName, String bookName);
    void refreshMaterialized();
}
