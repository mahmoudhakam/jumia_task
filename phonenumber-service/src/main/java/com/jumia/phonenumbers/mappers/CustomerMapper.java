package com.jumia.phonenumbers.mappers;

import com.jumia.phonenumbers.models.dtos.CustomerDTO;
import com.jumia.phonenumbers.models.entities.Customer;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerMapper extends IMapper<Customer, CustomerDTO> {
}
