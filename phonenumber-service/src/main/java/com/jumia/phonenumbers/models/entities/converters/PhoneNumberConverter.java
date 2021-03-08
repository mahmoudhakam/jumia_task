package com.jumia.phonenumbers.models.entities.converters;

import com.jumia.phonenumbers.models.enums.PhoneNumberState;

import javax.persistence.AttributeConverter;
import java.util.Arrays;

public class PhoneNumberConverter implements AttributeConverter<PhoneNumberState, Integer> {
    @Override
    public Integer convertToDatabaseColumn(PhoneNumberState phoneNumberStateEnum) {
        return phoneNumberStateEnum.getValue();
    }

    @Override
    public PhoneNumberState convertToEntityAttribute(Integer integer) {
        return Arrays.stream(PhoneNumberState.values()).filter(v -> v.getValue() == integer).findFirst().get();
    }
}
