package com.nscorp.obis.services;

import java.math.BigDecimal;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nscorp.obis.common.CommonConstants;
import com.nscorp.obis.common.UserId;
import com.nscorp.obis.domain.EquipmentCar;
import com.nscorp.obis.domain.EquipmentCustomerRange;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordNotAddedException;
import com.nscorp.obis.repository.AARTypeRepository;
import com.nscorp.obis.repository.EquipmentCarRepository;
import com.nscorp.obis.repository.EquipmentChassisRepository;
import com.nscorp.obis.repository.EquipmentContRepository;
import com.nscorp.obis.repository.EquipmentCustomerRangeRepository;
import com.nscorp.obis.repository.EquipmentHitchRepository;
import com.nscorp.obis.repository.EquipmentLocationRepository;
import com.nscorp.obis.repository.EquipmentTrailorRepository;
import com.nscorp.obis.repository.HoldOrdersRepository;

@Service
@Transactional
public class EquipmentCarServiceImpl implements EquipmentCarService {

	@Autowired
	EquipmentCarRepository carRepository;

	@Autowired
	EquipmentHitchRepository equipmentHitchRepository;

	@Autowired
	EquipmentLocationRepository equipmentLocationRepository;

	@Autowired
	HoldOrdersRepository holdOrdersRepository;

	@Autowired
	EquipmentChassisRepository equipmentChassisRepository;

	@Autowired
	EquipmentContRepository equipmentContRepository;

	@Autowired
	EquipmentTrailorRepository equipmentTrailorRepository;

	@Autowired
	EquipmentCustomerRangeRepository equipmentCustomerRangeRepository;

	@Autowired
	AARTypeRepository aarTypeRepository;

	private void validations(EquipmentCar eqCar) {
		if (equipmentContRepository.existsByContainerInitAndContainerNbr(eqCar.getCarInit(), eqCar.getCarNbr())) {
			throw new RecordNotAddedException("Car Init & Car Number found in Equipment Type - Container");
		} else if (equipmentTrailorRepository.existsByTrailorInitAndTrailorNumber(eqCar.getCarInit(),
				eqCar.getCarNbr())) {
			throw new RecordNotAddedException("Car Init & Car Number found in Equipment Type - Trailor");
		} else if (equipmentChassisRepository.existsByChasInitAndChasNbr(eqCar.getCarInit(), eqCar.getCarNbr())) {
			throw new RecordNotAddedException("Car Init & Car Number found in Equipment Type - Chassis");
		} else if (equipmentCustomerRangeRepository.existsByEquipmentInit(eqCar.getCarInit())) {
			EquipmentCustomerRange equipCustRange = equipmentCustomerRangeRepository
					.getEquipmentCustomerRange(eqCar.getCarInit(), eqCar.getCarNbr());
			if (equipCustRange != null) {
				throw new RecordNotAddedException(
						"Car Init & Car Number found in Equipment Type - " + equipCustRange.getEquipmentType());
			}
		} else {
			if (carRepository.existsByCarInitAndCarNbrAndCarEquipType(eqCar.getCarInit(), eqCar.getCarNbr(),
					eqCar.getCarEquipType())) {
				throw new RecordNotAddedException("Record Already Exists!!");
			}
		}
		if (!aarTypeRepository.existsByAarType(eqCar.getAarType())) {
			throw new RecordNotAddedException("AAR Type is not Valid !");
		}
	}

	@Override
	public EquipmentCar getEquipmentCar(String carInit, BigDecimal carNbr, String carEquipType) {
		EquipmentCar retrivedCar = carRepository.findByCarInitAndCarNbrAndCarEquipType(carInit, carNbr, carEquipType);
		if (retrivedCar == null) {
			throw new NoRecordsFoundException("No Record Found under this search!");
		}
		return setEquipmentHitchLocationAndHoldOrders(retrivedCar);
	}

	@Override
	public EquipmentCar addEquipmentCar(EquipmentCar eqCar, Map<String, String> headers) {
		UserId.headerUserID(headers);
		validations(eqCar);
		eqCar.setCreateUserId(headers.get(CommonConstants.USER_ID).toUpperCase());
		eqCar.setUpdateUserId(headers.get(CommonConstants.USER_ID).toUpperCase());
		eqCar.setUpdateExtensionSchema(headers.get(CommonConstants.EXTENSION_SCHEMA).toUpperCase());
		eqCar.setUversion("!");
		EquipmentCar equipCar = carRepository.save(eqCar);
		return setEquipmentHitchLocationAndHoldOrders(equipCar);
	}

	@Override
	public EquipmentCar updateEquipmentCar(EquipmentCar eqCar, Map<String, String> headers) {
		UserId.headerUserID(headers);

		if (carRepository.existsByCarInitAndCarNbrAndCarEquipTypeAndUversion(eqCar.getCarInit(), eqCar.getCarNbr(),
				eqCar.getCarEquipType(), eqCar.getUversion())) {
			EquipmentCar existingEquipmentCar = carRepository.findByCarInitAndCarNbrAndCarEquipType(eqCar.getCarInit(),
					eqCar.getCarNbr(), eqCar.getCarEquipType());
			if (StringUtils.isNotBlank(eqCar.getAarType()) && !aarTypeRepository.existsByAarType(eqCar.getAarType())) {
				throw new RecordNotAddedException("AAR Type is not Valid !");
			}
			return updateExistingEquipmentCar(existingEquipmentCar, eqCar, headers);
		} else
			throw new NoRecordsFoundException("No record Found to Update Under this car Init: " + eqCar.getCarInit()
					+ ", car Nbr: " + eqCar.getCarNbr() + " and U_Version: " + eqCar.getUversion());
	}

	private EquipmentCar updateExistingEquipmentCar(EquipmentCar existingEquipmentCar, EquipmentCar eqCar,
			Map<String, String> headers) {
		existingEquipmentCar.setUpdateUserId(headers.get(CommonConstants.USER_ID));
		String extensionSchema = headers.get(CommonConstants.EXTENSION_SCHEMA);
		if (StringUtils.isNotBlank(extensionSchema)) {
			existingEquipmentCar.setUpdateExtensionSchema(extensionSchema.toUpperCase());
		} else {
			throw new NullPointerException("Extension Schema should not be null, empty or blank");
		}

		if (StringUtils.isNotEmpty(existingEquipmentCar.getUversion())) {
			existingEquipmentCar.setUversion(
					Character.toString((char) ((((int) existingEquipmentCar.getUversion().charAt(0) - 32) % 94) + 33)));
		}
		existingEquipmentCar.setAarType(eqCar.getAarType());
		existingEquipmentCar.setCarLgth(eqCar.getCarLgth());
		existingEquipmentCar.setCarTareWgt(eqCar.getCarTareWgt());
		existingEquipmentCar.setCarOwnerType(eqCar.getCarOwnerType());
		existingEquipmentCar.setPlatformHeight_inches(eqCar.getPlatformHeight_inches());
		existingEquipmentCar.setCarLoadLimit(eqCar.getCarLoadLimit());
		existingEquipmentCar.setNrOfAxles(eqCar.getNrOfAxles());
		existingEquipmentCar.setArticulate(eqCar.getArticulate());
		return setEquipmentHitchLocationAndHoldOrders(carRepository.save(existingEquipmentCar));
	}

	private EquipmentCar setEquipmentHitchLocationAndHoldOrders(EquipmentCar existingCar) {

		existingCar.setEquipmentHitch(equipmentHitchRepository.findByCarInitAndCarNbrAndCarEquipType(
				existingCar.getCarInit(), existingCar.getCarNbr(), existingCar.getCarEquipType()));
		existingCar.setEquipmentLocation(equipmentLocationRepository.findByEquipInitAndEquipNbrAndEquipTp(
				existingCar.getCarInit(), existingCar.getCarNbr(), existingCar.getCarEquipType()));
		existingCar.setHoldOrders(holdOrdersRepository.findByEquipmentInitAndEquipmentNbrAndEquipmentType(
				existingCar.getCarInit(), existingCar.getCarNbr(), existingCar.getCarEquipType()));

		return existingCar;

	}

}
