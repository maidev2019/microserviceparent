package com.maidev.loan.model;

import com.fasterxml.jackson.annotation.JsonValue;

public enum LoanType {

    /**Loan types  */
    BUSSINES_LUNCHING("Bussines Lunching"), 
    INVESTMENT("Investment"), 
    HOUSE_BUYING("House Buying"),
    HOME_IMPROVEMENT("Home Improvement"),
    EDUCATION("Education"),
    OTHER("Other");

	public final String label;

	LoanType(String label) {
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
