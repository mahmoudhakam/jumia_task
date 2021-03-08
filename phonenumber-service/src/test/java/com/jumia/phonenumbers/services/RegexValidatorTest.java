package com.jumia.phonenumbers.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;

class RegexValidatorTest {

    private RegexValidator underTest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        underTest = new RegexValidator();
    }

    @ParameterizedTest
    @CsvSource({"\\(251\\)\\ ?[1-59]\\d{8}$,(251) 914148181,true", "\\(255\\)\\ ?[1-59]\\d{8}$,(251) 914148181,false"})
    void validateText(String regex, String text, boolean expected) {
        boolean actual = underTest.validateText(regex, text);
        assertThat(actual).isEqualTo(expected);
    }
}