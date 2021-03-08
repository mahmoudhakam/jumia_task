package com.jumia.phonenumbers.repositories;

import com.jumia.phonenumbers.models.entities.Country;
import com.jumia.phonenumbers.models.entities.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest(
        properties = {
                "spring.jpa.properties.javax.persistence.validation.mode=none"
        }
)
class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository underTest;

    @Test
    public void itShouldSaveNewCustomer() {
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
        //When
        Customer saved = underTest.save(customer);
        //Then
        Optional<Customer> selected = underTest.findById(saved.getId());
        assertThat(selected)
                .isPresent()
                .hasValueSatisfying(c -> {
                    assertThat(c).isEqualToComparingFieldByField(saved);
                });
    }

}