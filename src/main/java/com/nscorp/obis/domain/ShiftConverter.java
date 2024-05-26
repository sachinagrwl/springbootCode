package com.nscorp.obis.domain;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Converter(autoApply =true)
public class ShiftConverter implements AttributeConverter<List<Shift>,String>{

	@Override
	public String convertToDatabaseColumn(List<Shift> shiftList) {
		// TODO Auto-generated method stub
		if(shiftList==null || shiftList.size() == 0)return null;
		String shifts = "";
		for(Shift shift: shiftList) {
			if(shift!=null){
				shifts += shift.getValue();
			}
			char arr[] = shifts.toCharArray();
			Arrays.sort(arr);
			shifts = new String(arr);
		}
		return shifts;
	}

	@Override
	public List<Shift> convertToEntityAttribute(String dbData) {
		if(dbData==null || dbData.length()==0)return null;
	    List<Shift> shifts = new ArrayList<Shift>();
    	for(int i=0;i<dbData.length();i++) {
    		if(dbData.charAt(i)!=' ')
    		shifts.add(Shift.getShiftByValue(dbData.charAt(i)-'0'));
    	}
		return shifts;
	}

}
