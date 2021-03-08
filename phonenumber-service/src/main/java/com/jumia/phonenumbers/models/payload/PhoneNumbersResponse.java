package com.jumia.phonenumbers.models.payload;

import com.jumia.phonenumbers.models.dtos.CountryDTO;
import com.jumia.phonenumbers.models.dtos.CustomerDTO;
import com.jumia.phonenumbers.models.enums.PhoneNumberState;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class PhoneNumbersResponse {
    private long totalItems = 0l;
    private int totalPages = 0;
    private Set<CustomerDTO> customers = new HashSet<>();
    private Filter filter;

    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Setter
    @Getter
    public static class Filter {
        private Set<PhoneNumberState> states;
        private Set<CountryDTO> countries;
    }
}
