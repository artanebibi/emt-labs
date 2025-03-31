package mk.ukim.finki.wp.emt_lab.dto.Author;

import mk.ukim.finki.wp.emt_lab.model.domain.Author;
import mk.ukim.finki.wp.emt_lab.model.domain.Country;

public record UpdateAuthorDto(
        Long id,
        String name,
        String surname,
        Long countryId
) {
    public static UpdateAuthorDto from(Author author) {
        return new UpdateAuthorDto(
                author.getId(),
                author.getName(),
                author.getSurname(),
                author.getCountry().getId()
        );
    }

    public Author toAuthor(Country country) {
        return new Author(name, surname, country);
    }
}
