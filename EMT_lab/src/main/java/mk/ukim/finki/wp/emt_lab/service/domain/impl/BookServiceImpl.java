package mk.ukim.finki.wp.emt_lab.service.domain.impl;


import mk.ukim.finki.wp.emt_lab.model.domain.Book;
import mk.ukim.finki.wp.emt_lab.dto.Book.BookDto;
import mk.ukim.finki.wp.emt_lab.model.exceptions.InvalidBookIdException;
import mk.ukim.finki.wp.emt_lab.repository.AuthorRepository;
import mk.ukim.finki.wp.emt_lab.repository.BookRepository;
import mk.ukim.finki.wp.emt_lab.service.domain.BookService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    public BookServiceImpl(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Override
    public Optional<Book> findById(Long id) {
        return Optional.ofNullable(bookRepository.findById(id).orElseThrow(InvalidBookIdException::new));
    }

    @Override
    public void delete(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(InvalidBookIdException::new);
        book.setDeleted(true);
        bookRepository.save(book);
    }

    @Override
    public Optional<Book> update(Long id, BookDto bookDto) {
        return bookRepository.findById(id)
                .map(existingBook -> {
                    if (bookDto.getName() != null) {
                        existingBook.setName(bookDto.getName());
                    }
                    if (bookDto.getCategory() != null) {
                        existingBook.setCategory(bookDto.getCategory());
                    }
                    if (!bookDto.getAuthors().isEmpty()) {
                        existingBook.setAuthors(authorRepository.findAllById(bookDto.getAuthors()));
                    }

                    // if a book is rented, decrease avalaible copies
                    if (bookDto.isRented()) {
                        existingBook.setAvailableCopies(bookDto.getAvailableCopies() - 1);
                    }
                    existingBook.setRented(bookDto.isRented());
                    return bookRepository.save(existingBook);
                });
    }

    @Override
    public Optional<Book> rentBook(Long id, BookDto bookDto) {
        return bookRepository.findById(id)
                .map(existingBook -> {
                    existingBook.setAvailableCopies(bookDto.getAvailableCopies() - 1);
                    existingBook.setRented(true);
                    return bookRepository.save(existingBook);
                });
    }

    @Override
    public Optional<Book> save(BookDto bookDto) {
        if (!bookDto.getAuthors().isEmpty()) {
            return Optional.of(bookRepository.save((new Book(bookDto.getName(), bookDto.getCategory(), authorRepository.findAllById(bookDto.getAuthors()), bookDto.getAvailableCopies(), bookDto.isRented(), bookDto.isDeleted()))));
        }
        return Optional.empty();
    }

    @Override
    public List<Book> findByAuthorAndBookName(String authorName, String bookName) {
        return bookRepository.findByBookNameAndAuthorName(bookName, authorName);
    }
}
