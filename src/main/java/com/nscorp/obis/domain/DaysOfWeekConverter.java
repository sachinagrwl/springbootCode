package com.nscorp.obis.domain;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
@Converter(autoApply =true)
public class DaysOfWeekConverter implements AttributeConverter<List<DayOfWeek>,String>{

	@Override
	public String convertToDatabaseColumn(List<DayOfWeek> days) {
		if(days==null)return null;
		String dayOfWeek = "";
		for(DayOfWeek week: days) {
			if(week !=null){
				dayOfWeek += week.getValue();
			}
			char arr[] = dayOfWeek.toCharArray();
			Arrays.sort(arr);	
			dayOfWeek = new String(arr);
		}
		return dayOfWeek;
	}

	@Override
	public List<DayOfWeek> convertToEntityAttribute(String dbData) {
		if(dbData==null || dbData.length()==0)return null;
		List<DayOfWeek> days = new ArrayList<DayOfWeek>();
    	for(int i=0;i<dbData.length();i++) {
    		if(dbData.charAt(i)!=' ')
    		days.add(DayOfWeek.of(dbData.charAt(i)-'0'));
    	}
		return days;
	}

}
