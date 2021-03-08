package com.jumia.phonenumbers.models.payload;

import com.jumia.phonenumbers.models.enums.PhoneNumberState;
import lombok.Data;

import java.util.Set;

@Data
public class PhoneNumberRequest {
    private int offSet = 0;
    private int pageSize = 10;
    private Set<Long> countries;
    private PhoneNumberState state;
}
