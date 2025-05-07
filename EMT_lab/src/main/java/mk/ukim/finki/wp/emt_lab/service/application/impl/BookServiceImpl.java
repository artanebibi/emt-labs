package mk.ukim.finki.wp.emt_lab.service.application.impl;


import mk.ukim.finki.wp.emt_lab.dto.Book.CreateBookDto;
import mk.ukim.finki.wp.emt_lab.dto.Book.DisplayBookDto;
import mk.ukim.finki.wp.emt_lab.dto.Book.UpdateBookDto;
import mk.ukim.finki.wp.emt_lab.model.domain.Book;
import mk.ukim.finki.wp.emt_lab.dto.Book.BookDto;
import mk.ukim.finki.wp.emt_lab.model.exceptions.InvalidBookIdException;
import mk.ukim.finki.wp.emt_lab.repository.AuthorRepository;
import mk.ukim.finki.wp.emt_lab.repository.BookRepository;
import mk.ukim.finki.wp.emt_lab.service.application.BookService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("ApplicationBookServiceImpl")
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
    public Page<DisplayBookDto> findAll(Pageable pageable) {
        return bookRepository.findAll(pageable).map(DisplayBookDto::from);
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
    public Optional<Book> update(Long id, UpdateBookDto bookDto) {
        return bookRepository.findById(id)
                .map(existingBook -> {
                    if (bookDto.name() != null) {
                        existingBook.setName(bookDto.name());
                    }
                    if (bookDto.category() != null) {
                        existingBook.setCategory(bookDto.category());
                    }
                    if (!bookDto.authorIds().isEmpty()) {
                        existingBook.setAuthors(authorRepository.findAllById(bookDto.authorIds()));
                    }

                    // if a book is rented, decrease avalaible copies
                    if (bookDto.rented()) {
                        existingBook.setAvailableCopies(bookDto.availableCopies() - 1);
                    }
                    existingBook.setRented(bookDto.rented());
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
    public Optional<Book> save(CreateBookDto bookDto) {
        if (!bookDto.authorIds().isEmpty()) {
            return Optional.of(bookRepository.save((new Book(bookDto.name(), bookDto.category(), authorRepository.findAllById(bookDto.authorIds()), bookDto.availableCopies(), bookDto.rented(), bookDto.isDeleted()))));
        }
        return Optional.empty();
    }

    @Override
    public List<Book> findByAuthorAndBookName(String authorName, String bookName) {
        return bookRepository.findByBookNameAndAuthorName(bookName, authorName);
    }
}
