package com.nscorp.obis.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nscorp.obis.domain.TrainSchedule;

public interface TrainScheduleRepository extends JpaRepository<TrainSchedule, String> {
	
	boolean existsByTrainNumber(String trainNumber);

}
