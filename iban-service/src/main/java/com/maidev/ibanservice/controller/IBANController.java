package com.maidev.ibanservice.controller;


import org.json.simple.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.maidev.ibanservice.model.Iban;
import com.maidev.ibanservice.service.IBANService;


@RestController
public class IBANController {


    @GetMapping(value ="/iban/{country}")
    public ResponseEntity<Object> getTax_Id_Number(@PathVariable("country") String country){
        Iban iban = IBANService.generateIBAN(country);
        JSONObject object = new JSONObject();        
        
        object.put("ibanValue", iban.getIbanvalue());
        object.put("country",iban.getCountry());
        return new ResponseEntity<Object>(object, HttpStatus.OK);
    }

    @GetMapping("/iban/validate/{countryName}")
    public ResponseEntity validateTaxNumber(@PathVariable("countryName") String countryName){
        //if(TaxIDService.validate(toValidateTaxId)){
            return new ResponseEntity(HttpStatus.OK);
        //}
        //    return new ResponseEntity(HttpStatus.NOT_FOUND);

    }
}
