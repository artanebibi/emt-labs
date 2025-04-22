package mk.ukim.finki.wp.emt_lab.service.domain.impl;

import jakarta.transaction.Transactional;
import mk.ukim.finki.wp.emt_lab.model.domain.Author;
import mk.ukim.finki.wp.emt_lab.model.domain.Book;
import mk.ukim.finki.wp.emt_lab.model.domain.Country;
import mk.ukim.finki.wp.emt_lab.dto.Author.AuthorDto;
import mk.ukim.finki.wp.emt_lab.model.exceptions.InvalidAuthorIdException;
import mk.ukim.finki.wp.emt_lab.model.exceptions.InvalidCountryIdException;
import mk.ukim.finki.wp.emt_lab.repository.AuthorRepository;
import mk.ukim.finki.wp.emt_lab.repository.AuthorsPerCountryViewRepository;
import mk.ukim.finki.wp.emt_lab.repository.BookRepository;
import mk.ukim.finki.wp.emt_lab.repository.CountryRepository;
import mk.ukim.finki.wp.emt_lab.service.domain.AuthorService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;
    private final CountryRepository countryRepository;
    private final BookRepository bookRepository;
    private final AuthorsPerCountryViewRepository authorsPerCountryViewRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository, CountryRepository countryRepository, BookRepository bookRepository, AuthorsPerCountryViewRepository authorsPerCountryViewRepository) {
        this.authorRepository = authorRepository;
        this.countryRepository = countryRepository;
        this.bookRepository = bookRepository;
        this.authorsPerCountryViewRepository = authorsPerCountryViewRepository;
    }

    @Override
    public List<Author> findAll() {
        return authorRepository.findAll();
    }

    @Override
    public Optional<Author> findById(Long id) {
        return Optional.ofNullable(authorRepository.findById(id).orElseThrow(InvalidAuthorIdException::new));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Author author = authorRepository.findById(id)
                .orElseThrow(InvalidAuthorIdException::new);

        List<Book> books = bookRepository.findByAuthors_Id(id);
        for (Book book : books) {
            book.getAuthors().remove(author);
            bookRepository.save(book);
        }
        this.authorsPerCountryViewRepository.refreshMaterialized();
        authorRepository.delete(author);
    }

    @Override
    public Optional<Author> update(Long id, AuthorDto authorDto) {
        return authorRepository.findById(id)
                .map(existingAuthor -> {
                    if (authorDto.getName() != null) {
                        existingAuthor.setName(authorDto.getName());
                    }
                    if (authorDto.getSurname() != null) {
                        existingAuthor.setSurname(authorDto.getSurname());
                    }
                    if (authorDto.getCountry() != null) {
                        countryRepository.findById(authorDto.getCountry())
                                .ifPresent(existingAuthor::setCountry);
                    }
                    this.authorsPerCountryViewRepository.refreshMaterialized();
                    return authorRepository.save(existingAuthor);
                });
    }

    @Override
    public Optional<Author> save(AuthorDto authorDto) {
        if (authorDto.getCountry() != null) {
            Country country = countryRepository.findById(authorDto.getCountry())
                    .orElseThrow(InvalidCountryIdException::new);
            Author author = new Author(authorDto.getName(), authorDto.getSurname(), country);
            this.authorsPerCountryViewRepository.refreshMaterialized();
            return Optional.of(authorRepository.save(author));
        }
        return Optional.empty();
    }

    @Override
    public Author create(String name, String surname, Long countryId) {
        Country country = countryRepository.findById(countryId).orElseThrow(InvalidCountryIdException::new);
        Author author = new Author(name, surname, country);
        this.authorsPerCountryViewRepository.refreshMaterialized();
        return authorRepository.save(author);
    }

    @Override
    public void refreshMaterialized() {
        this.authorsPerCountryViewRepository.refreshMaterialized();
    }
}