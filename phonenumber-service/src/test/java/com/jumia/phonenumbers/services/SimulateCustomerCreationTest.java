package com.jumia.phonenumbers.services;

import com.jumia.phonenumbers.models.entities.Country;
import com.jumia.phonenumbers.models.entities.Customer;
import com.jumia.phonenumbers.models.enums.PhoneNumberState;
import com.jumia.phonenumbers.repositories.CustomerRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.then;

class SimulateCustomerCreationTest {
    private SimulateCustomerCreation underTest;
    @Mock
    private CountryService countryService;
    @Mock
    private CustomerService customerService;
    @Mock
    private CustomerRepository customerRepository;
    @Captor
    private ArgumentCaptor<Customer> customerArgumentCaptor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        underTest = new SimulateCustomerCreation(countryService, customerService, customerRepository);
    }

    @Test
    public void itShouldUpdateValidCustomerPhoneNumber() {
        List<Country> countries = new ArrayList<>();
        countries.add(Country.builder().regex("\\(251\\)\\ ?[1-59]\\d{8}$").code("+251").name("Cameroon").build());
        BDDMockito.given(countryService.simulateCountryCreation()).willReturn(countries);

        List<Customer> customers = new ArrayList<>();
        customers.add(Customer.builder().name("Filimon Embaye").phone("(251) 914701723").build());
        BDDMockito.given(customerService.fetchAllCustomers()).willReturn(customers);

        BDDMockito.given(countryService.getValidCountryAgainstCustomer(any(), any()))
                .willReturn(Country.builder().regex("\\(251\\)\\ ?[1-59]\\d{8}$").code("+251").name("Cameroon").build());

        underTest.simulateCustomerValidation(countryService.simulateCountryCreation());

        then(customerRepository).should().save(customerArgumentCaptor.capture());
        Customer customerArgumentCaptorValue = customerArgumentCaptor.getValue();
        Assertions.assertNotNull(customerArgumentCaptorValue.getCountry());
        org.assertj.core.api.Assertions.assertThat(customerArgumentCaptorValue.getState()).isEqualTo(PhoneNumberState.VALID);
    }

    @Test
    public void itShouldUpdateNotValidCustomerPhoneNumber() {
        List<Country> countries = new ArrayList<>();
        countries.add(Country.builder().regex("\\(256\\)\\ ?\\d{9}$").code("+256").name("Uganda").build());
        BDDMockito.given(countryService.simulateCountryCreation()).willReturn(countries);

        List<Customer> customers = new ArrayList<>();
        customers.add(Customer.builder().name("Filimon Embaye").phone("(251) 914701723").build());
        BDDMockito.given(customerService.fetchAllCustomers()).willReturn(customers);

        BDDMockito.given(countryService.getValidCountryAgainstCustomer(any(), any()))
                .willReturn(new Country());

        underTest.simulateCustomerValidation(countryService.simulateCountryCreation());

        then(customerRepository).should().save(customerArgumentCaptor.capture());
        Customer customerArgumentCaptorValue = customerArgumentCaptor.getValue();
        Assertions.assertNull(customerArgumentCaptorValue.getCountry());
        org.assertj.core.api.Assertions.assertThat(customerArgumentCaptorValue.getState()).isEqualTo(PhoneNumberState.NOT_VALID);
    }
}