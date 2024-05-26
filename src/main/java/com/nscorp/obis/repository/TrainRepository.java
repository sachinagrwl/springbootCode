package com.nscorp.obis.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import com.nscorp.obis.domain.Train;

public interface TrainRepository extends JpaRepository<Train, String> {
	
	List<Train> findAll(Specification<Train> specification,Sort sort);

	boolean existsByTrainNumber(String trainNumber);


}
