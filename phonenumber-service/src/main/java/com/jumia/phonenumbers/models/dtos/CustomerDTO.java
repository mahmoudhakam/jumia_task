package com.jumia.phonenumbers.models.dtos;

import com.jumia.phonenumbers.models.enums.PhoneNumberState;
import lombok.Data;

@Data
public class CustomerDTO extends BaseDTO {
    private String name;
    private String phone;
    private PhoneNumberState state;
    private CountryDTO country;
}
