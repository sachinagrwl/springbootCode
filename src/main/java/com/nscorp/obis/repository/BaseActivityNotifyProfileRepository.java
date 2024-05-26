package com.nscorp.obis.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nscorp.obis.domain.BaseActivityNotifyProfile;

public interface BaseActivityNotifyProfileRepository extends JpaRepository<BaseActivityNotifyProfile, Long> {

	boolean existsByDayOfWeekAndEventCodeAndShiftAndNotifyTerminalId(String dayOfWeek, String eventCode, String shift,
			Long notifyTerminalId);
}
