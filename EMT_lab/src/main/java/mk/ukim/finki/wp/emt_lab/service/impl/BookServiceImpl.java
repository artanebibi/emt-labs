package mk.ukim.finki.wp.emt_lab.service.impl;


import mk.ukim.finki.wp.emt_lab.model.Book;
import mk.ukim.finki.wp.emt_lab.model.dto.BookDto;
import mk.ukim.finki.wp.emt_lab.model.exceptions.InvalidBookIdException;
import mk.ukim.finki.wp.emt_lab.repository.AuthorRepository;
import mk.ukim.finki.wp.emt_lab.repository.BookRepository;
import mk.ukim.finki.wp.emt_lab.service.BookService;
import org.springframework.stereotype.Service;

import java.util.List;
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
        bookRepository.deleteById(id);
    }

//    @Override
//    public Book create(String name, Category category, List<Long> authorIds, int availableCopies) {
//        List<Author> authorList = authorRepository.findAllById(authorIds);
//        return bookRepository.save(new Book(name, category, authorList, availableCopies));
//    }
//

//    @Override
//    public Book update(Long id, String name, Category category, List<Long> authorIds, int availableCopies) {
//        Book book = bookRepository.findById(id).orElseThrow(InvalidBookIdException::new);
//        book.setName(name);
//        book.setCategory(category);
//        List<Author> authorList = authorRepository.findAllById(authorIds);
//        book.setAuthors(authorList);
//        book.setAvailableCopies(availableCopies);
//
//        return bookRepository.save(book);
//    }


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
                    existingBook.setAvailableCopies(bookDto.getAvailableCopies());
                    existingBook.setRented(bookDto.isRented());
                    return bookRepository.save(existingBook);
                });
    }

    @Override
    public Optional<Book> rentBook(Long id, BookDto bookDto) {
        return bookRepository.findById(id)
                .map(existingBook -> {
                    existingBook.setRented(bookDto.isRented());
                    return bookRepository.save(existingBook);
                });
    }

    @Override
    public Optional<Book> save(BookDto bookDto) {
        if (!bookDto.getAuthors().isEmpty()) {
            return Optional.of(bookRepository.save((new Book(bookDto.getName(), bookDto.getCategory(), authorRepository.findAllById(bookDto.getAuthors()), bookDto.getAvailableCopies(), bookDto.isRented()))));
        }
        return Optional.empty();
    }
}
