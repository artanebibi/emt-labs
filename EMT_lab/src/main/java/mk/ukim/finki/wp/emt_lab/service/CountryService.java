package mk.ukim.finki.wp.emt_lab.service;

import mk.ukim.finki.wp.emt_lab.model.Country;

import java.util.List;

public interface CountryService {
    List<Country> findAll();

    Country findById(Long id);

    Country delete(Long id);

    Country create(String name, String continent);

    Country update(Long id, String name, String continent);
}
