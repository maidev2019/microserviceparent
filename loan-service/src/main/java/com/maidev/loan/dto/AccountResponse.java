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
public class AccountResponse {
    private Long id;   
    private String accountholder;
    private String bankname;
    private String iban;
    private String bic;
}