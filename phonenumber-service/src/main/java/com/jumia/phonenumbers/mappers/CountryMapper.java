package com.jumia.phonenumbers.mappers;

import com.jumia.phonenumbers.models.dtos.CountryDTO;
import com.jumia.phonenumbers.models.entities.Country;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CountryMapper extends IMapper<Country, CountryDTO> {
}
