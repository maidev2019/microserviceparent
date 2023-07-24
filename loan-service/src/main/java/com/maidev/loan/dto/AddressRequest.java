package com.maidev.loan.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AddressRequest {
    private String street;
    private String streetAdditionalLine;
    private String city;
    private String state;
    private String postalcode;
}