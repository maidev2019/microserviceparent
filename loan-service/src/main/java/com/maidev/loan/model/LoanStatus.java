package com.maidev.loan.model;

import com.fasterxml.jackson.annotation.JsonValue;

public enum LoanStatus {

    /**Loan types  */
    NEW("Neu"), 
    REQUESTED("Beantragt"), 
    APPROVED("Bewilligt"),
    REJECTED("Abgelehnt"),
    WITHDRAWN("Zurückgezogen");    

	public final String label;

	LoanStatus(String label) {
		this.label = label;
	}

	/**The method returns the value of the variable label.
	 * @return label the value of label
	 */
	@JsonValue
	public String getLabel() {
		return label;
	}
}
