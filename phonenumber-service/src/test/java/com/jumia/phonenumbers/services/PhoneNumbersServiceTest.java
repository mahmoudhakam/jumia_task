package com.jumia.phonenumbers.services;

import com.jumia.phonenumbers.exceptions.CustomersNotFound;
import com.jumia.phonenumbers.mappers.CustomerMapper;
import com.jumia.phonenumbers.models.entities.Customer;
import com.jumia.phonenumbers.models.payload.PhoneNumberRequest;
import com.jumia.phonenumbers.models.payload.PhoneNumbersResponse;
import com.jumia.phonenumbers.repositories.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

class PhoneNumbersServiceTest {

    private PhoneNumbersService underTest;
    private CustomerMapper customerMapper;
    @Mock
    private CustomerService customerService;
    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private CountryService countryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        customerMapper = Mappers.getMapper(CustomerMapper.class);
        underTest = new PhoneNumbersService(customerMapper, customerRepository, countryService);
    }

    @Test
    public void itShouldThrowNotFoundCustomers() {
        PhoneNumberRequest request = new PhoneNumberRequest();
        request.setOffSet(0);
        request.setPageSize(10);
        List<Customer> customerList = new ArrayList<>();
        Page<Customer> customers = new PageImpl<>(customerList);
        given(customerRepository.findAll(any(Specification.class), any(Pageable.class)))
                .willReturn(customers);
        assertThatThrownBy(() -> underTest.listPhoneNumbers(request))
                .isInstanceOf(CustomersNotFound.class)
                .hasMessageContaining("Oops, customers not found");
    }

    @Test
    public void itShouldListCustomers() {
        //Given
        PhoneNumberRequest request = new PhoneNumberRequest();
        request.setOffSet(0);
        request.setPageSize(10);
        List<Customer> customerList = new ArrayList<>();
        customerList.add(Customer.builder().name("mahmoud").phone("12345").build());
        customerList.add(Customer.builder().name("younis").phone("456489").build());
        Page<Customer> customers = new PageImpl<>(customerList);
        given(customerRepository.findAll(any(Specification.class), any(Pageable.class))).willReturn(customers);
        PhoneNumbersResponse customersResponse = underTest.listPhoneNumbers(request);
        assertThat(customersResponse.getTotalItems()).isEqualTo(customerList.size());
    }

}