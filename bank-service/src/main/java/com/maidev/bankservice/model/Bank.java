package com.maidev.bankservice.model;

import java.util.List;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name="bank")
public class Bank {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    private String name;    

    @Column
    private Double maxLoanAmount;

    @Column
    private int maxNumberMonth;   
    
    @Enumerated(EnumType.STRING)	
    @Column
    private List<LoanType> supportedLoanTypes;
}



