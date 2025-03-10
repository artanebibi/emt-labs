package mk.ukim.finki.wp.emt_lab.service;


import mk.ukim.finki.wp.emt_lab.model.Author;
import mk.ukim.finki.wp.emt_lab.model.Book;
import mk.ukim.finki.wp.emt_lab.model.Category;
import mk.ukim.finki.wp.emt_lab.model.dto.BookDto;
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
}
