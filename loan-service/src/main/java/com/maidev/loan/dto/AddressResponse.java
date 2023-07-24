package com.maidev.loan.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Getter
@Setter
public class AddressResponse {
    private Long id;
    private String street;
    private String streetAdditionalLine;
    private String city;
    private String state;
    private String postalcode;

}
