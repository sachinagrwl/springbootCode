package com.nscorp.obis.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.nscorp.obis.repository.TruckerRepository;
import com.nscorp.obis.domain.Trucker;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.repository.TiaTruckerRepository;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TiaTruckerServiceImpl implements TiaTruckerService{
	@Autowired
	TruckerRepository truckerRepo;
	@Autowired
	TiaTruckerRepository tiaTruckerRepo;
	public List<Trucker> getTruckerNameByTruckerCode(String truckerCode) {
		List<Trucker> truckerList = truckerRepo.findTruckerByTruckerCode(truckerCode);
		if(tiaTruckerRepo.existsByTruckerCode(truckerCode) && !truckerList.isEmpty()) {
			return truckerList;
		}
		else {
			throw new NoRecordsFoundException("No records found");
		}
	}
}
