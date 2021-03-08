package com.jumia.phonenumbers.services;

import com.jumia.phonenumbers.mappers.CountryMapper;
import com.jumia.phonenumbers.models.dtos.CountryDTO;
import com.jumia.phonenumbers.models.entities.Country;
import com.jumia.phonenumbers.models.entities.Customer;
import com.jumia.phonenumbers.repositories.CountryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class CountryService {
    private final CountryRepository countryRepository;
    private final RegexValidator regexValidator;
    private final CountryMapper countryMapper;

    public List<Country> simulateCountryCreation() {
        List<Country> countries = new ArrayList<>();
        countries.add(insertCountry("+237", Country.builder().code("+237").name("Cameroon").regex("\\(237\\)\\ ?[2368]\\d{7,8}$").build()));
        countries.add(insertCountry("+251", Country.builder().code("+251").name("Ethiopia").regex("\\(251\\)\\ ?[1-59]\\d{8}$").build()));
        countries.add(insertCountry("+212", Country.builder().code("+212").name("Morocco").regex("\\(212\\)\\ ?[5-9]\\d{8}$").build()));
        countries.add(insertCountry("+258", Country.builder().code("+258").name("Mozambique").regex("\\(258\\)\\ ?[28]\\d{7,8}$").build()));
        countries.add(insertCountry("+256", Country.builder().code("+256").name("Uganda").regex("\\(256\\)\\ ?\\d{9}$").build()));
        return countries;
    }

    private Country insertCountry(String code, Country country) {
        Optional<Country> selectedCountry = countryRepository.findByCode(code);
        if (!selectedCountry.isPresent()) {
            return countryRepository.save(country);
        }
        return selectedCountry.get();
    }

    public Country getValidCountryAgainstCustomer(Country country, Customer customer) {
        boolean validPhoneNumber = regexValidator.validateText(country.getRegex(), customer.getPhone());
        if (validPhoneNumber) {
            return country;
        }
        return country.ofNullable();
    }

    public Set<CountryDTO> getCountries() {
        return countryMapper.toDTOs(new HashSet<>(countryRepository.findAll()));
    }
}
