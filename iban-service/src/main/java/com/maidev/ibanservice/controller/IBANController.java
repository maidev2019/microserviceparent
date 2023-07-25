package com.maidev.ibanservice.controller;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.maidev.ibanservice.model.Iban;
import com.maidev.ibanservice.service.IBANService;

import lombok.AllArgsConstructor;


@AllArgsConstructor
@RestController
public class IBANController {
    @Autowired
    private final IBANService ibanService;

    @GetMapping("/iban/{country}")
    public ResponseEntity<Object> generateIBAN(@PathVariable("country") String country) {
        Iban iban = ibanService.generateIBAN(country);
        if (iban == null) {
            JSONObject errorObject = new JSONObject();
            errorObject.put("error", "Invalid country");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorObject.toString());
        }

        JSONObject response = new JSONObject();
        response.put("ibanValue", iban.getIbanvalue());
        response.put("country", iban.getCountry());
        return ResponseEntity.ok(response.toString());
    }

    @GetMapping("/iban/validate/{ibannumber}")
    public ResponseEntity<String> validateIBAN(@PathVariable("ibannumber") String ibannumber) {
        
        if (ibannumber == null || ibannumber.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid IBAN");
        }

        if (!ibanService.validateIBAN(ibannumber)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid IBAN");
        }

        return ResponseEntity.ok("Valid IBAN");
    }
}
