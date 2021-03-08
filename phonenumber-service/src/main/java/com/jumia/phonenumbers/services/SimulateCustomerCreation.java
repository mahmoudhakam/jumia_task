package com.jumia.phonenumbers.services;

import com.jumia.phonenumbers.models.entities.Country;
import com.jumia.phonenumbers.models.entities.Customer;
import com.jumia.phonenumbers.models.enums.PhoneNumberState;
import com.jumia.phonenumbers.repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.List;

/**
 * This service to simulate customer creation and validation process
 */

@Service
@RequiredArgsConstructor
public class SimulateCustomerCreation {
    private final CountryService countryService;
    private final CustomerService customerService;
    private final CustomerRepository customerRepository;

    @PostConstruct
    void init() {
        simulateCustomerValidation(countryService.simulateCountryCreation());
    }

    public void simulateCustomerValidation(List<Country> countries) {
        List<Customer> customers = customerService.fetchAllCustomers();
        validateCustomers(customers, countries);
    }

    public void validateCustomers(List<Customer> customers, List<Country> countries) {
        customers.forEach(customer -> validateCustomer(customer, countries));
    }

    private void validateCustomer(Customer customer, List<Country> countries) {
        for (Country country : countries) {
            boolean valid = validateCustomerAgainstCountry(customer, country);
            customerRepository.save(customer);
            if (valid) {
                break;
            }
        }
    }

    private boolean validateCustomerAgainstCountry(Customer customer, Country country) {
        Country validCountry = countryService.getValidCountryAgainstCustomer(country, customer);
        if (checkNotValidCountry(validCountry)) {
            flagCustomerAsNotValid(customer);
            return false;
        }
        flagCustomerAsValid(customer, validCountry);
        return true;
    }

    private void flagCustomerAsNotValid(Customer customer) {
        customer.setState(PhoneNumberState.NOT_VALID);
        customer.setCreatedDate(new Date());
    }

    private void flagCustomerAsValid(Customer customer, Country validCountry) {
        customer.setState(PhoneNumberState.VALID);
        customer.setCountry(validCountry);
        customer.setCreatedDate(new Date());
    }

    private boolean checkNotValidCountry(Country validCountry) {
        return validCountry.getCode() == null;
    }

}
