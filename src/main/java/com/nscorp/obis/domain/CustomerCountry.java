package com.nscorp.obis.domain;

import java.util.Arrays;
import java.util.List;

public enum CustomerCountry {

	CAN("CAN"), MEX("MEX"), USA("USA"), FGN("FGN");

	private final String code;

	private CustomerCountry(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

	public static CustomerCountry of(String value) {
		CustomerCountry[] method = CustomerCountry.values();
		List<CustomerCountry> methodList = Arrays.asList(method);
		for (CustomerCountry customerCountry : methodList) {
			if (customerCountry.getCode().equals(value)) {
				return customerCountry;

			}
		}
		return null;
	}
}
