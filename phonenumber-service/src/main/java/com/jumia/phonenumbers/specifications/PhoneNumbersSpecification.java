package com.jumia.phonenumbers.specifications;

import com.jumia.phonenumbers.models.entities.Customer;
import com.jumia.phonenumbers.models.enums.PhoneNumberState;
import com.jumia.phonenumbers.models.payload.PhoneNumberRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.CollectionUtils;

import javax.persistence.criteria.JoinType;
import java.util.Set;

public class PhoneNumbersSpecification {

    public static Specification<Customer> withCountries(Set<Long> countries) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            if (CollectionUtils.isEmpty(countries)) {
                return criteriaBuilder.conjunction();
            } else {
                return criteriaBuilder.in(root.join("country", JoinType.INNER).get("id")).value(countries);
            }
        };
    }

    public static Specification<Customer> withState(PhoneNumberState state) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            if (state == null) {
                return criteriaBuilder.conjunction();
            } else {
                return criteriaBuilder.equal(root.get("state"), state);
            }
        };
    }

    public static Specification<Customer> filter(PhoneNumberRequest phoneNumberRequest) {
        return Specification.where(withCountries(phoneNumberRequest.getCountries())).and(withState(phoneNumberRequest.getState()));
    }
}
