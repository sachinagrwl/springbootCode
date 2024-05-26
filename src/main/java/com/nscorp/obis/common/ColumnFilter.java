package com.nscorp.obis.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nscorp.obis.exception.InvalidDataException;



public class ColumnFilter {

	public static Map<String, List<String>> filterFields(String[] filter) {
		Map<String, List<String>> filterMap = new HashMap<>();
		if (filter == null) {
			return filterMap;
		}
		if (!filter[0].contains(",") && filter.length == 2) {
			String[] temp = ColumnFilter.getFilterFieldValue(filter);
			filterMap.put(temp[0], Arrays.asList(temp[1]));
		} else {
			for (String field : filter) {
				String[] temp = ColumnFilter.getFilterFieldValue(field.split(","));
				if(filterMap.containsKey(temp[0]))
					filterMap.get(temp[0]).add(temp[1]);
				else
					filterMap.put(temp[0], new ArrayList<String>(Arrays.asList(temp[1])));
			}
		}
		return filterMap;
	}

	public static String[] getFilterFieldValue(String[] field) {
		if (field.length != 2) {
			throw new InvalidDataException("invalid filter parameter");
		}
		String fieldName = field[0].trim(), fieldValue = field[1].trim();
		if (fieldName == "" || fieldValue == "") {
			throw new InvalidDataException("invalid filter parameter");
		}
		String[] temp = { fieldName, fieldValue.toUpperCase() };
		return temp;
	}
}
