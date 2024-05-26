package com.nscorp.obis.domain;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.persistence.AttributeConverter;

public class ListToStringConverter implements AttributeConverter<List<String>, String> {
	@Override
	public String convertToDatabaseColumn(List<String> attribute) {
		return attribute == null ? null : String.join(",", attribute);
	}

	@Override
	public List<String> convertToEntityAttribute(String attribute) {
		return attribute == null ? Collections.emptyList()
				: Stream.of(attribute.split(",")).map(String::trim).collect(Collectors.toList());
	}
}
