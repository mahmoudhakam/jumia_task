package com.jumia.phonenumbers.services;

import com.jumia.phonenumbers.exceptions.CustomersNotFound;
import com.jumia.phonenumbers.mappers.CustomerMapper;
import com.jumia.phonenumbers.models.dtos.CountryDTO;
import com.jumia.phonenumbers.models.dtos.CustomerDTO;
import com.jumia.phonenumbers.models.entities.Country;
import com.jumia.phonenumbers.models.entities.Customer;
import com.jumia.phonenumbers.models.enums.PhoneNumberState;
import com.jumia.phonenumbers.models.payload.PhoneNumberRequest;
import com.jumia.phonenumbers.models.payload.PhoneNumbersResponse;
import com.jumia.phonenumbers.repositories.CustomerRepository;
import com.jumia.phonenumbers.specifications.PhoneNumbersSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PhoneNumbersService {
    private final CustomerMapper customerMapper;
    private final CustomerRepository customerRepository;
    private final CountryService countryService;

    public PhoneNumbersResponse listPhoneNumbers(PhoneNumberRequest phoneNumberRequest) {
        PhoneNumbersResponse.PhoneNumbersResponseBuilder builder = PhoneNumbersResponse.builder();
        Page<Customer> customersPage = customerRepository.findAll(PhoneNumbersSpecification.filter(phoneNumberRequest),
                PageRequest.of(phoneNumberRequest.getOffSet(), phoneNumberRequest.getPageSize()));
        return returnCustomerData(builder, customersPage);
    }

    private PhoneNumbersResponse returnCustomerData(PhoneNumbersResponse.PhoneNumbersResponseBuilder builder, Page<Customer> customersPage) {
        if (isEmptyCustomers(customersPage)) {
            throw new CustomersNotFound("Oops, customers not found");
        }
        Set<CustomerDTO> customers = customerMapper.toDTOs(customersPage.get().collect(Collectors.toSet()));
        builder.customers(customers);
        builder.totalItems(customersPage.getTotalElements());
        builder.totalPages(customersPage.getTotalPages());
        builder.filter(getFilter(getCountries()));
        return builder.build();
    }

    private Set<CountryDTO> getCountries() {
        return countryService.getCountries();
    }

    private PhoneNumbersResponse.Filter getFilter(Set<CountryDTO> countryIds) {
        Set<PhoneNumberState> states = Arrays.stream(PhoneNumberState.values()).collect(Collectors.toSet());
        return PhoneNumbersResponse.Filter.builder().countries(countryIds).states(states).build();
    }

    private boolean isEmptyCustomers(Page<Customer> customersPage) {
        return customersPage != null && customersPage.getTotalElements() == 0;
    }
}
