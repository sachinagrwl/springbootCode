package com.nscorp.obis.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nscorp.obis.domain.ActivityNotifyProfile;
import com.nscorp.obis.domain.DayOfWeek;
import com.nscorp.obis.domain.Shift;

public interface ActivityNotifyProfileRepository extends JpaRepository<ActivityNotifyProfile, Long> {

	Boolean existsByNotifyProfileId(Long notifyProfileID);
	
	void deleteByNotifyProfileId(Long notifyProfileID);

	Integer findByDayOfWeek(List<DayOfWeek> dayOfWeek);

	boolean existsByDayOfWeekAndEventCodeAndShiftAndNotifyTerminalId(List<DayOfWeek> dayOfWeek, String eventCode, List<Shift> shift,
			Long notifyTerminalId);

}
