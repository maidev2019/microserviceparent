package com.maidev.ibanservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Iban {

    private String country;
    private String ibanvalue;
    
    @Override
    public String toString() {
        return "Iban{" +
                "country='" + country + '\'' +
                ", ibanvalue='" + ibanvalue + '\'' +
                '}';
    }
}
