package com.nscorp.obis.services;

import com.nscorp.obis.domain.ResourceList;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.repository.ResourceListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResourceListServiceImpl implements ResourceListService {
	
	@Autowired
	ResourceListRepository resourceRepo;

	@Override
	public List<ResourceList> getAllResourceList() {
		// TODO Auto-generated method stub
		List<ResourceList> resourceInfo = resourceRepo.findAll();
		
		if(resourceInfo.isEmpty()) {
			throw new NoRecordsFoundException("Record Not found");
		}
		return resourceInfo;
	}

}
