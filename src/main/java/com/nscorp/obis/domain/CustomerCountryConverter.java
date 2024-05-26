package com.nscorp.obis.domain;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply =true)
public class CustomerCountryConverter implements AttributeConverter<CustomerCountry, String>{

	@Override
	public String convertToDatabaseColumn(CustomerCountry attribute) {
		if(attribute==null)return null;
		return attribute.getCode();
	}

	@Override
	public CustomerCountry convertToEntityAttribute(String dbData) {
		if(dbData!=null) 
			return CustomerCountry.of(dbData);
		else
			return null;
	}

}
