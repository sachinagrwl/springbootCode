package com.nscorp.obis.services;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.nscorp.obis.common.SpecificationGenerator;
import com.nscorp.obis.domain.CustomerIndex;
import com.nscorp.obis.domain.SortFilter;
import com.nscorp.obis.domain.VoiceNotify;
import com.nscorp.obis.dto.CustomerIndexDTO;
import com.nscorp.obis.dto.mapper.CustomerIndexMapper;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.repository.CustomerIndexRepository;
import com.nscorp.obis.repository.VoiceNotifyRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CustomerIndexServiceImpl implements CustomerIndexService {

	@Autowired
	CustomerIndexRepository customerIndexRepository;

	@Autowired
	SpecificationGenerator specificationGenerator;

	@Autowired
	private EntityManager entityManager;

	@Autowired
	VoiceNotifyRepository voiceNotifyRepo;

	@Override
	public List<CustomerIndexDTO> getCustomers(String customerName, String customerNumber, String city, String state,
			String uniqueGroup, Long corporateId, String latest, String[] sort, String fetchExpired) {
		log.info("getCustomers : Method Starts");
		entityManager.clear();
		List<CustomerIndexDTO> customerIndexDTOs = new ArrayList<>();
		Slice<CustomerIndex> slice = null;
		if (uniqueGroup != null && uniqueGroup.equals("y")) {
			String[] defaultSort = { "customerName,asc", "customerNumber,asc", "city,asc", "state,asc" };
			if (customerName == null)
				customerName = "";
			if (customerNumber == null)
				customerNumber = "";
			customerName = customerName.toUpperCase();
//			customerIndexRepository.checkByCustomerNameOrNumber(customerName,customerNumber,
//					PageRequest.of(0, 20, Sort.by(SortFilter.sortOrder(defaultSort))))
//					.stream()
//					.forEach(r -> customerIndexDTOs.add(new CustomerIndexDTO((String) r[0], (String)r[1], (String)r[2], (String)r[3])));
//			
			slice = customerIndexRepository
					.findAll(
							specificationGenerator.customerIndexUniqueSpecification(customerName, customerNumber,
									corporateId, fetchExpired),
							PageRequest.of(0, 100, Sort.by(SortFilter.sortOrder(defaultSort))));

		} else if (latest != null && latest.equals("y")) {
			String[] sortOrder = { "updateDateTime", "desc" };
			slice = customerIndexRepository
					.findAll(
							specificationGenerator.customerIndexLatestSpecification(customerName, customerNumber, city,
									state, corporateId, fetchExpired),
							PageRequest.of(0, 1, Sort.by(SortFilter.sortOrder(sortOrder))));

		} else {
			slice = customerIndexRepository.findAll(specificationGenerator.customerIndexSpecification(customerName,
					customerNumber, corporateId, fetchExpired),
					PageRequest.of(0, 5000, Sort.by(SortFilter.sortOrder(sort))));
			while (slice.hasNext()) {
				slice.get().forEach((customer) -> {
					customerIndexDTOs.add(CustomerIndexMapper.INSTANCE.customerIndexTCustomerIndexDTO(customer));
				});
				slice = customerIndexRepository.findAll(specificationGenerator.customerIndexSpecification(customerName,
						customerNumber, corporateId, fetchExpired), slice.nextPageable());
				entityManager.clear();
			}
		}
		if (!slice.isEmpty()) {
			slice.get().forEach((customer) -> {
				customerIndexDTOs.add(CustomerIndexMapper.INSTANCE.customerIndexTCustomerIndexDTO(customer));
			});
		}
		if (customerIndexDTOs.isEmpty()) {
			throw new NoRecordsFoundException("No records found for given combination");
		}
		entityManager.clear();
		log.info("getCustomers : Method Ends");
		return customerIndexDTOs;
	}

	@Override
	public CustomerIndex getCustIndex(Long notifyQueueId) {

		VoiceNotify voiceNotify = voiceNotifyRepo.findByNotifyQueueId(notifyQueueId);

		if (voiceNotify == null) {
			throw new NoRecordsFoundException(
					"No Records Found in Voice Notify with this Notify Queue Id: " + notifyQueueId);
		}

		CustomerIndex customerIndex = customerIndexRepository.findByCustomerId(voiceNotify.getNotifyCustId());
		if (customerIndex == null) {
			throw new NoRecordsFoundException(
					"No Records Found in Customer Index with this Notify Cust Id: " + voiceNotify.getNotifyCustId());
		}
		return customerIndex;
	}

}
