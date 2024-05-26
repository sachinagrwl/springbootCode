package com.nscorp.obis.services;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nscorp.obis.domain.EquipmentCustomerLesseeRange;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.repository.CorporateCustomerRepository;
import com.nscorp.obis.repository.EquipmentCustomerLesseeRangeRepository;

@Service
@Transactional
public class EquipmentCustomerLesseeRangeServiceImpl implements EquipmentCustomerLesseeRangeService {

	@Autowired
	EquipmentCustomerLesseeRangeRepository equipLesseeRepository;

	@Autowired
	CorporateCustomerRepository corporateCustomerRepository;

	@Override
	public Page<EquipmentCustomerLesseeRange> getEquipLessee(String equipmentInit, String corporateLongName,
			Pageable pageable) {
		Page<EquipmentCustomerLesseeRange> equipLesseeRange;

		equipLesseeRange = (StringUtils.isNotBlank(equipmentInit) && StringUtils.isNotBlank(corporateLongName))
				? equipLesseeRepository.findByEquipmentInitAndCorporateCustomer_CorporateLongName(equipmentInit,
						corporateLongName, pageable)
				: StringUtils.isNotBlank(equipmentInit)
						? equipLesseeRepository.findByEquipmentInit(equipmentInit, pageable)
						: equipLesseeRepository.findByCorporateCustomer_CorporateLongName(corporateLongName, pageable);

		if (equipLesseeRange.getContent().isEmpty()) {
			throw new NoRecordsFoundException("No Records Found");
		}

		return equipLesseeRange;

	}

	@Override
	public EquipmentCustomerLesseeRange deleteEquipLesseeRange(
			EquipmentCustomerLesseeRange equipmentCustomerLesseeRange) {
		if (equipLesseeRepository.existsByEquipmentCustomerRangeIdAndUversion(
				equipmentCustomerLesseeRange.getEquipmentCustomerRangeId(),
				equipmentCustomerLesseeRange.getUversion())) {
			EquipmentCustomerLesseeRange existingEquipCustLesseeRange = equipLesseeRepository
					.findById(equipmentCustomerLesseeRange.getEquipmentCustomerRangeId()).get();
			equipLesseeRepository.deleteById(equipmentCustomerLesseeRange.getEquipmentCustomerRangeId());
			return existingEquipCustLesseeRange;
		} else
			throw new NoRecordsFoundException("No record Found to delete Under this EquipmentCustomerRangeId: "
					+ equipmentCustomerLesseeRange.getEquipmentCustomerRangeId() + " and U_Version: "
					+ equipmentCustomerLesseeRange.getUversion());
	}

	@Override
	public List<String> getAllEquipmentInit() {
		List<String> eqInitList = equipLesseeRepository.findAllDistinctEquipmentInit();
		return eqInitList.stream().map(StringUtils::trim).filter(StringUtils::isNotBlank).sorted()
				.collect(Collectors.toList());
	}

	@Override
	public List<String> getAllCorporateLongName() {
		List<Long> eqLesseIds = equipLesseeRepository.findAllDistinctCorporateLongName();
		return eqLesseIds.stream()
				.filter(id -> (id != null && corporateCustomerRepository.existsByCorporateCustomerId(id)))
				.map(corporateCustomerRepository::findCorporateLongName).map(StringUtils::trim)
				.filter(StringUtils::isNotBlank).sorted().collect(Collectors.toList());
	}
}
