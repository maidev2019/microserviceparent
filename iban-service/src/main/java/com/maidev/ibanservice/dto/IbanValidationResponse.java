package com.maidev.ibanservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class IbanValidationResponse {
    private boolean valid;
}
