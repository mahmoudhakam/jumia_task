package com.jumia.phonenumbers.controllers;

import com.jumia.phonenumbers.models.payload.ApiResponse;
import com.jumia.phonenumbers.models.payload.PhoneNumberRequest;
import com.jumia.phonenumbers.models.payload.PhoneNumbersResponse;
import com.jumia.phonenumbers.services.PhoneNumbersService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/phone-numbers")
@Api(tags = "Phone-numbers", value = "Phone-numbers")
public class PhoneNumbersEndpoint {
    private final PhoneNumbersService phoneNumbersService;

    @GetMapping
    @CrossOrigin
    public ApiResponse<PhoneNumbersResponse> listPhoneNumbers(PhoneNumberRequest phoneNumberRequest) {
        long start = System.currentTimeMillis();
        return ApiResponse.ok(phoneNumbersService.listPhoneNumbers(phoneNumberRequest), (System.currentTimeMillis() - start) + " ms");
    }
}
