package com.jumia.phonenumbers.services;

import com.jumia.phonenumbers.mappers.CountryMapper;
import com.jumia.phonenumbers.models.entities.Country;
import com.jumia.phonenumbers.models.entities.Customer;
import com.jumia.phonenumbers.repositories.CountryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

/**
 * I used isolated give when then flavor
 * So I will mock any method except method under test
 */
class CountryServiceTest {

    private CountryService underTest;
    @Mock
    private CountryRepository countryRepository;
    @Mock
    private RegexValidator regexValidator;
    private CountryMapper countryMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        underTest = new CountryService(countryRepository, regexValidator,countryMapper);
    }

    @Test
    void itShouldReturnValidCountry() {
        //Given country and customer
        Country country = new Country();
        country.setCode("+251");
        country.setName("Cameroon");
        country.setRegex("\\(251\\)\\ ?[1-59]\\d{8}$");
        country.setId(77l);
        Customer customer = new Customer();
        customer.setCountry(country);
        customer.setName("mahmoud");
        customer.setPhone("(251) 914701723");
        customer.setId(1l);
        //Given valid phone number
        given(regexValidator.validateText(any(), any())).willReturn(true);
        // When
        Country actual = underTest.getValidCountryAgainstCustomer(country, customer);
        //Then
        assertThat(actual).isEqualTo(country);
    }

    @Test
    void itShouldNotReturnValidCountry() {
        //Given country and customer
        Country country = new Country();
        country.setCode("+251");
        country.setName("Cameroon");
        country.setRegex("\\(251\\)\\ ?[1-59]\\d{8}$");
        country.setId(77l);
        Customer customer = new Customer();
        customer.setCountry(country);
        customer.setName("mahmoud");
        customer.setPhone("(255) 914701723");
        customer.setId(1l);
        //Given valid phone number
        given(regexValidator.validateText(any(), any())).willReturn(false);
        // When
        Country actual = underTest.getValidCountryAgainstCustomer(country, customer);
        //Then
        assertThat(actual).isNotEqualTo(country);
    }
}