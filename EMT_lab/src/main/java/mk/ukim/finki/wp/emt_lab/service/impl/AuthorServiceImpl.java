package mk.ukim.finki.wp.emt_lab.service.impl;

import mk.ukim.finki.wp.emt_lab.model.Author;
import mk.ukim.finki.wp.emt_lab.model.Country;
import mk.ukim.finki.wp.emt_lab.model.exceptions.InvalidAuthorIdException;
import mk.ukim.finki.wp.emt_lab.model.exceptions.InvalidCountryIdException;
import mk.ukim.finki.wp.emt_lab.repository.AuthorRepository;
import mk.ukim.finki.wp.emt_lab.repository.CountryRepository;
import mk.ukim.finki.wp.emt_lab.service.AuthorService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;
    private final CountryRepository countryRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository, CountryRepository countryRepository) {
        this.authorRepository = authorRepository;
        this.countryRepository = countryRepository;
    }


    @Override
    public List<Author> findAll() {
        return authorRepository.findAll();
    }

    @Override
    public Author findById(Long id) {
        return authorRepository.findById(id).orElseThrow(InvalidAuthorIdException::new);
    }

    @Override
    public void save(Author author) {
        authorRepository.save(author);
    }

    @Override
    public Author delete(Long id) {
        Author author = findById(id);
        authorRepository.delete(author);
        return author;
    }

    @Override
    public Author create(String name, String surname, Long countryId) {
        Country country = countryRepository.findById(countryId).orElseThrow(InvalidCountryIdException::new);
        return authorRepository.save(new Author(name, surname, country));
    }

    @Override
    public Author update(Long id, String name, String surname, Long countryId) {
        Country country = countryRepository.findById(countryId).orElseThrow(InvalidCountryIdException::new);
        Author author = findById(id);
        author.setName(name);
        author.setSurname(surname);
        author.setCountry(country);
        return authorRepository.save(author);
    }
}
