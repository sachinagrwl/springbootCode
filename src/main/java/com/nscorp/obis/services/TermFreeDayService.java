package com.nscorp.obis.services;

import com.nscorp.obis.domain.TermFreeDay;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

public interface TermFreeDayService {

    List<TermFreeDay> getAllFreeDays(List<Long> termId, LocalDate closeDate, String closeCode, LocalTime closeFromTime);

    void deleteTermFreeDay(@Valid TermFreeDay termFreeDayObj);

    //Add TermFreeDay//
	TermFreeDay addTermDay(@Valid TermFreeDay termDay, Map<String, String> headers);

	//Update TermFreeDay//
	TermFreeDay updateTermDay(@Valid TermFreeDay termDay, Map<String, String> headers);

    List<String> getAllReasonDesc();
	
}
