package mk.ukim.finki.wp.emt_lab.dto.Country;

import mk.ukim.finki.wp.emt_lab.model.domain.Country;

public record UpdateCountryDto(
        Long id,
        String name,
        String continent
) {
    public static UpdateCountryDto from(Country country) {
        return new UpdateCountryDto(
                country.getId(),
                country.getName(),
                country.getContinent()
        );
    }

    public Country toCountry() {
        return new Country(name, continent);
    }
}
