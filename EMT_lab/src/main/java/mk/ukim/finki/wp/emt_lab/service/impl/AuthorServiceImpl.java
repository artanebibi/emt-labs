package mk.ukim.finki.wp.emt_lab.service.impl;

import mk.ukim.finki.wp.emt_lab.model.Author;
import mk.ukim.finki.wp.emt_lab.model.Country;
import mk.ukim.finki.wp.emt_lab.model.dto.AuthorDto;
import mk.ukim.finki.wp.emt_lab.model.exceptions.InvalidAuthorIdException;
import mk.ukim.finki.wp.emt_lab.model.exceptions.InvalidCountryIdException;
import mk.ukim.finki.wp.emt_lab.repository.AuthorRepository;
import mk.ukim.finki.wp.emt_lab.repository.BookRepository;
import mk.ukim.finki.wp.emt_lab.repository.CountryRepository;
import mk.ukim.finki.wp.emt_lab.service.AuthorService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;
    private final CountryRepository countryRepository;
    private final BookRepository bookRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository, CountryRepository countryRepository, BookRepository bookRepository) {
        this.authorRepository = authorRepository;
        this.countryRepository = countryRepository;
        this.bookRepository = bookRepository;
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
    public void delete(Long id) {
        authorRepository.deleteById(id);
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
                    return authorRepository.save(existingAuthor);
                });
    }

    @Override
    public Optional<Author> save(AuthorDto authorDto) {
        if (authorDto.getCountry() != null) {
            return Optional.of(authorRepository.save(new Author(authorDto.getName(), authorDto.getSurname(), countryRepository.findById(authorDto.getCountry()).orElseThrow(InvalidCountryIdException::new))));
        }
        return Optional.empty();
    }

    @Override
    public Author create(String name, String surname, Long countryId) {
        Country country = countryRepository.findById(countryId).orElseThrow(InvalidCountryIdException::new);
        Author author = new Author(name, surname, country);
        return authorRepository.save(author);
    }
}
