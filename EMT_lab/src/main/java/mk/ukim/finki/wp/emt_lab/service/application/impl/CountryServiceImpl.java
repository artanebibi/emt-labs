package mk.ukim.finki.wp.emt_lab.service.application.impl;

import mk.ukim.finki.wp.emt_lab.model.domain.Country;
import mk.ukim.finki.wp.emt_lab.model.exceptions.InvalidCountryIdException;
import mk.ukim.finki.wp.emt_lab.repository.CountryRepository;
import mk.ukim.finki.wp.emt_lab.service.application.CountryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("ApplicationCountryServiceImpl")
public class CountryServiceImpl implements CountryService {
    private final CountryRepository countryRepository;

    public CountryServiceImpl(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    @Override
    public List<Country> findAll() {
        return countryRepository.findAll();
    }

    @Override
    public Country findById(Long id) {
        return countryRepository.findById(id).orElseThrow(InvalidCountryIdException::new);
    }

    @Override
    public Country delete(Long id) {
        Country country = countryRepository.findById(id).orElseThrow(InvalidCountryIdException::new);
        countryRepository.delete(country);
        return country;
    }

    @Override
    public Country create(String name, String continent) {
        return countryRepository.save(new Country(name, continent));
    }

    @Override
    public Country update(Long id, String name, String continent) {
        Country country = countryRepository.findById(id).orElseThrow(InvalidCountryIdException::new);
        country.setName(name);
        country.setContinent(continent);
        return countryRepository.save(country);
    }
}
