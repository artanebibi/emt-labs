package mk.ukim.finki.wp.emt_lab.service;

import mk.ukim.finki.wp.emt_lab.model.Author;
import mk.ukim.finki.wp.emt_lab.model.Country;

import java.util.List;

public interface AuthorService {
    List<Author> findAll();

    Author findById(Long id);

    void save(Author author);

    Author delete(Long id);

    Author create(String name, String surname, Long a);

    Author update( Long id, String name, String surname, Long countryId);
}
