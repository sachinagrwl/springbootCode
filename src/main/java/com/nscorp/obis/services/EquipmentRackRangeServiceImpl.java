package com.nscorp.obis.services;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nscorp.obis.common.CommonConstants;
import com.nscorp.obis.common.UserId;
import com.nscorp.obis.domain.EquipmentRackRange;
import com.nscorp.obis.exception.InvalidDataException;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.exception.RecordNotAddedException;
import com.nscorp.obis.exception.RecordNotDeletedException;
import com.nscorp.obis.repository.EquipmentContRepository;
import com.nscorp.obis.repository.EquipmentRackRangeRepository;

@Service
@Transactional
public class EquipmentRackRangeServiceImpl implements EquipmentRackRangeService {

	@Autowired
	EquipmentRackRangeRepository equipmentRackRangeRepo;

	@Autowired
	EquipmentContRepository equipmentContRepository;

	public void EqRackRangeValidations(EquipmentRackRange equipmentRackRange) {
		if (equipmentRackRange.getEquipInit().isEmpty()) {
			throw new RecordNotAddedException("Equipment Init value should be present!");
		}
		if (equipmentRackRange.getEquipLowNbr().compareTo(equipmentRackRange.getEquipHighNbr()) > 0) {
			throw new RecordNotAddedException("Equipment Low Number: " + equipmentRackRange.getEquipLowNbr()
					+ " should be less than or equals to Equipment High Number: " + equipmentRackRange.getEquipHighNbr());
		}
		BigDecimal eqNrLow = equipmentRackRange.getEquipLowNbr();
		BigDecimal eqNrHigh = equipmentRackRange.getEquipHighNbr();
		if (equipmentRackRangeRepo.existsByEquipInit(equipmentRackRange.getEquipInit())) {
			List<EquipmentRackRange> eqRackRangeList = equipmentRackRangeRepo
					.findByEquipInit(equipmentRackRange.getEquipInit());
			eqRackRangeList.forEach(eqRackRange -> {
				if ((eqRackRange.getEquipLowNbr().compareTo(eqNrLow) >= 0
						&& eqRackRange.getEquipHighNbr().compareTo(eqNrHigh) <= 0)
						|| (eqRackRange.getEquipLowNbr().compareTo(eqNrLow) <= 0
								&& eqRackRange.getEquipHighNbr().compareTo(eqNrHigh) >= 0)
						|| (eqRackRange.getEquipLowNbr().compareTo(eqNrLow) <= 0
								&& eqRackRange.getEquipHighNbr().compareTo(eqNrLow) >= 0)
						|| (eqRackRange.getEquipLowNbr().compareTo(eqNrHigh) <= 0
								&& eqRackRange.getEquipHighNbr().compareTo(eqNrHigh) >= 0)) {
					throw new RecordNotAddedException("Equipment Number Range are overlapping with existing records");
				}
			});
		}
	}

	@Override
	public List<EquipmentRackRange> getAllTables() {
		List<EquipmentRackRange> equipmentOwner = equipmentRackRangeRepo.findAll();
		if (equipmentOwner.isEmpty()) {
			throw new NoRecordsFoundException("No Records found");
		}
		return equipmentOwner;
	}

	@Override
	public EquipmentRackRange addEquipmentRackRange(@Valid EquipmentRackRange equipmentRackRange,
			Map<String, String> headers) {
		UserId.headerUserID(headers);

		equipmentRackRange.setCreateUserId(headers.get(CommonConstants.USER_ID));
		equipmentRackRange.setUpdateUserId(headers.get(CommonConstants.USER_ID));
		equipmentRackRange.setUpdateExtensionSchema(headers.get(CommonConstants.EXTENSION_SCHEMA));
		equipmentRackRange.setUversion("!");
		EqRackRangeValidations(equipmentRackRange);

		if (equipmentRackRangeRepo.existsByEquipRackRangeId(equipmentRackRange.getEquipRackRangeId())) {
			throw new RecordAlreadyExistsException("Record with EquipRackRangeId Already Exists!");
		}

		if (equipmentContRepository.existsByContainerInit(equipmentRackRange.getEquipInit())) {
			equipmentRackRange.setEquipInit(equipmentRackRange.getEquipInit());
		} else
			throw new NoRecordsFoundException("EQ_INIT needs to be defined as a container first");

		Long equipRackRangeId = equipmentRackRangeRepo.SGK();
		equipmentRackRange.setEquipRackRangeId(equipRackRangeId);
		equipmentRackRange.setEquipType("C");

		equipmentRackRangeRepo.save(equipmentRackRange);
		return equipmentRackRange;
	}

	@Override
	public void deleteEquipmentRackRange(EquipmentRackRange equipmentRackRange) {
		if (equipmentRackRangeRepo.existsById(equipmentRackRange.getEquipRackRangeId())) {
			equipmentRackRangeRepo.deleteById(equipmentRackRange.getEquipRackRangeId());

		} else {
			throw new RecordNotDeletedException("Record Not Found!");
		}
	}

	@Override
	public EquipmentRackRange updateEquipmentRackRange(@Valid EquipmentRackRange equipmentRackRange,
			Map<String, String> headers) {
		UserId.headerUserID(headers);
		if (equipmentRackRangeRepo.existsById(equipmentRackRange.getEquipRackRangeId())) {

			equipmentRackRange.setCreateUserId(headers.get(CommonConstants.USER_ID));
			equipmentRackRange.setUpdateUserId(headers.get(CommonConstants.USER_ID));
			equipmentRackRange.setUpdateExtensionSchema(headers.get(CommonConstants.EXTENSION_SCHEMA));
			equipmentRackRange.setUversion("!");
			if (equipmentRackRange.getEquipInit().isEmpty()) {
				throw new RecordNotAddedException("Equipment Init value should be present!");
			}
			if (equipmentRackRange.getEquipLowNbr().compareTo(equipmentRackRange.getEquipHighNbr()) > 0) {
				throw new RecordNotAddedException("Equipment Low Number: " + equipmentRackRange.getEquipLowNbr()
						+ " should be less than or equals to Equipment High Number: " + equipmentRackRange.getEquipHighNbr());
			}
			BigDecimal eqNrLow = equipmentRackRange.getEquipLowNbr();
			BigDecimal eqNrHigh = equipmentRackRange.getEquipHighNbr();

			if (equipmentRackRangeRepo.existsByEquipInit(equipmentRackRange.getEquipInit())) {
				List<EquipmentRackRange> eqRackRangeList = equipmentRackRangeRepo
						.findByEquipInit(equipmentRackRange.getEquipInit());
				if(eqRackRangeList.size()>1) {
				eqRackRangeList.forEach(eqRackRange -> {
				
					if (eqRackRange.getEquipRackRangeId().equals(equipmentRackRange.getEquipRackRangeId())) {
						if (!(eqRackRange.getEquipLowNbr().compareTo(eqNrLow) <= 0
								&& eqRackRange.getEquipHighNbr().compareTo(eqNrHigh) >= 0)
								&& ((eqRackRange.getEquipLowNbr().compareTo(eqNrLow) >= 0
										&& eqRackRange.getEquipHighNbr().compareTo(eqNrHigh) <= 0)
										|| (eqRackRange.getEquipLowNbr().compareTo(eqNrLow) <= 0
												&& eqRackRange.getEquipHighNbr().compareTo(eqNrHigh) >= 0)
										|| (eqRackRange.getEquipLowNbr().compareTo(eqNrLow) <= 0
												&& eqRackRange.getEquipHighNbr().compareTo(eqNrLow) >= 0)
										|| (eqRackRange.getEquipLowNbr().compareTo(eqNrHigh) <= 0
												&& eqRackRange.getEquipHighNbr().compareTo(eqNrHigh) >= 0))) {

							throw new RecordNotAddedException("Equipment Number Range are overlapping with existing records");
						}
					} else {
						if ((eqRackRange.getEquipLowNbr().compareTo(eqNrLow) >= 0
								&& eqRackRange.getEquipHighNbr().compareTo(eqNrHigh) <= 0)
								|| (eqRackRange.getEquipLowNbr().compareTo(eqNrLow) <= 0
										&& eqRackRange.getEquipHighNbr().compareTo(eqNrHigh) >= 0)
								|| (eqRackRange.getEquipLowNbr().compareTo(eqNrLow) <= 0
										&& eqRackRange.getEquipHighNbr().compareTo(eqNrLow) >= 0)
								|| (eqRackRange.getEquipLowNbr().compareTo(eqNrHigh) <= 0
										&& eqRackRange.getEquipHighNbr().compareTo(eqNrHigh) >= 0)) {

							throw new RecordNotAddedException("Equipment Number Range are overlapping with existing records");
						}
					}
				});
				}
				
			}

			equipmentRackRange.setEquipType("C");
			if (equipmentContRepository.existsByContainerInit(equipmentRackRange.getEquipInit())) {
				equipmentRackRange.setEquipInit(equipmentRackRange.getEquipInit());
			} else
				throw new NoRecordsFoundException("EQ_INIT needs to be defined as a container first");
			equipmentRackRangeRepo.save(equipmentRackRange);

			return equipmentRackRange;

		} else {
			throw new NoRecordsFoundException("Record Not Found!");
		}

	}
}