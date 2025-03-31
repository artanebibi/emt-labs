package mk.ukim.finki.wp.emt_lab.service.application;

import mk.ukim.finki.wp.emt_lab.dto.Author.CreateAuthorDto;
import mk.ukim.finki.wp.emt_lab.dto.Author.UpdateAuthorDto;
import mk.ukim.finki.wp.emt_lab.model.domain.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorService {
    List<Author> findAll();

    Optional<Author> findById(Long id);

    void delete(Long id);

    Optional<Author> update (Long id, UpdateAuthorDto authorDto);

    Optional<Author> save (CreateAuthorDto authorDto);

    Author create(String name, String surname, Long countryId);

}
