package com.jumia.phonenumbers.models.dtos;

import lombok.Data;

import java.util.Date;

@Data
public class BaseDTO {
    private Long id;
    private Date createdDate;
}
