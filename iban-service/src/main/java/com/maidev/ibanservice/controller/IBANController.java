package com.maidev.ibanservice.controller;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.maidev.ibanservice.dto.ErrorResponse;
import com.maidev.ibanservice.dto.IbanValidationResponse;
import com.maidev.ibanservice.exception.InvalidCountryException;
import com.maidev.ibanservice.model.Iban;
import com.maidev.ibanservice.service.IBANService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class IBANController {
    @Autowired
    private final IBANService ibanService;

    @GetMapping("/iban/{country}")
    public ResponseEntity<Object> generateIBAN(@PathVariable("country") String country) {
        Iban iban = ibanService.generateIBAN(country);
        if (iban == null) {
            return ResponseEntity.badRequest().body(new ErrorResponse("Invalid country"));
        }

        JSONObject response = new JSONObject();
        response.put("ibanValue", iban.getIbanvalue());
        response.put("country", iban.getCountry());
        return ResponseEntity.ok(response.toString());
    }

    @GetMapping("/iban/validate/{ibannumber}")
    public ResponseEntity<IbanValidationResponse> validateIBAN(@PathVariable("ibannumber") String ibannumber) {
        boolean isValid = ibanService.validateIBAN(ibannumber);
        return ResponseEntity.ok(new IbanValidationResponse(isValid));
    }

    // Exception handler for InvalidCountryException
    @ExceptionHandler(InvalidCountryException.class)
    public ResponseEntity<ErrorResponse> handleInvalidCountryException(InvalidCountryException ex) {
        return ResponseEntity.badRequest().body(new ErrorResponse("Invalid country"));
    }
}