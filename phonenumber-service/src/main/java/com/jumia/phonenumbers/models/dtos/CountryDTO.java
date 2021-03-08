package com.jumia.phonenumbers.models.dtos;

import lombok.Data;

@Data
public class CountryDTO extends BaseDTO {
    private String name;
    private String code;
}
