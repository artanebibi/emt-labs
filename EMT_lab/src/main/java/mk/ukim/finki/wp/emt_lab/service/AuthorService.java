package mk.ukim.finki.wp.emt_lab.service;

import mk.ukim.finki.wp.emt_lab.model.Author;
import mk.ukim.finki.wp.emt_lab.model.Country;
import mk.ukim.finki.wp.emt_lab.model.dto.AuthorDto;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface AuthorService {
    List<Author> findAll();

    Optional<Author> findById(Long id);

    void delete(Long id);

    Optional<Author> update (Long id, AuthorDto authorDto);

    Optional<Author> save (AuthorDto authorDto);

    Author create(String name, String surname, Long countryId);

}
