package com.nscorp.obis.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nscorp.obis.common.CommonConstants;
import com.nscorp.obis.domain.AARHitch;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.repository.AARHitchRepository;

@Service
@Transactional
public class AARHitchServiceImpl implements AARHitchService {
	
	@Autowired
	AARHitchRepository aarHitchRepo;

	@Override
	public List<AARHitch> getAllHitch(String aarType, String hitchLocation) {
		
		if(aarType != null) {
		
			if(aarType.length() != 0 && (!aarType.startsWith("P") && !aarType.startsWith("Q") && !aarType.startsWith("S") && !aarType.startsWith("U") && !aarType.startsWith("Z"))) {
				throw new NoRecordsFoundException("'aarType' should starts with 'P', 'Q', 'S', 'U' and 'Z'");
			}
			
			if(!aarType.substring(1).matches(CommonConstants.REGEX)) {
				throw new NoRecordsFoundException("AARType : Character 2 to 4 must be numeric");
			}
			
		}
		
		if(hitchLocation != null && !hitchLocation.substring(1).matches(CommonConstants.REGEX)) {
			throw new NoRecordsFoundException("HitchLocation : 2nd Character must be numeric");
		}
		
		List<AARHitch> aarHitch = aarHitchRepo.findAARHitch(aarType, hitchLocation);
		if(aarHitch.isEmpty()) {
			throw new NoRecordsFoundException("No Records found!");
		}
		return aarHitch;
	}

}
