package com.jumia.phonenumbers.repositories;

import com.jumia.phonenumbers.models.entities.Country;
import com.jumia.phonenumbers.models.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CountryRepository extends JpaRepository<Country, Long> {
    Optional<Country> findByCode(String code);
}
