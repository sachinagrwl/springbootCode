package com.nscorp.obis.services;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nscorp.obis.common.CommonConstants;
import com.nscorp.obis.common.UserId;
import com.nscorp.obis.domain.ContainerChassisAssociation;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.exception.RecordNotAddedException;
import com.nscorp.obis.repository.ContainerChassisAssociationRepository;

@Service
@Transactional
public class ContainerChassisAssociationServiceImpl implements ContainerChassisAssociationService {

	@Autowired
	ContainerChassisAssociationRepository containerChassisAssociationRepo;

	private void ContainerChassisValidation(ContainerChassisAssociation association) {

		Timestamp currentDate = new Timestamp(System.currentTimeMillis());

		if (!association.getContainerInit().matches("[a-zA-Z]+")) {
			throw new RecordNotAddedException("'containerInit' value should have only alphabets");
		}

		if (!association.getChassisInit().matches("[a-zA-Z]+")) {
			throw new RecordNotAddedException("'chassisInit' value should have only alphabets");
		}

		if (association.getContainerLowNumber().compareTo(association.getContainerHighNumber()) > 0) {
			throw new RecordNotAddedException("Container Low Number: " + association.getContainerLowNumber()
					+ " should be less than or equal to Container High Number: "
					+ association.getContainerHighNumber());
		}

		if (association.getChassisLowNumber().compareTo(association.getChassisHighNumber()) > 0) {
			throw new RecordNotAddedException("Chassis Low Number: " + association.getChassisLowNumber()
					+ " should be less than or equal to Chassis High Number: " + association.getChassisHighNumber());
		}

		BigDecimal contNrLow = association.getContainerLowNumber();
		BigDecimal contNrHigh = association.getContainerHighNumber();

		BigDecimal chassisNrLow = association.getChassisLowNumber();
		BigDecimal chassisNrHigh = association.getChassisHighNumber();

		if (containerChassisAssociationRepo.existsByContainerInitAndChassisInitAndContainerLengthAndChassisLength(
				association.getContainerInit(), association.getChassisInit(), association.getContainerLength(),
				association.getChassisLength())) {
			List<ContainerChassisAssociation> contOverLapList = containerChassisAssociationRepo
					.findByContainerInitAndChassisInitAndContainerLengthAndChassisLength(association.getContainerInit(),
							association.getChassisInit(), association.getContainerLength(),
							association.getChassisLength());

			contOverLapList.stream()
					.filter(assocOverLap -> (!assocOverLap.getAssociationId().equals(association.getAssociationId()))
							&& (assocOverLap.getExpiredDateTime() == null
									|| assocOverLap.getExpiredDateTime().after(currentDate)))
					.forEach(assocOverLap -> {

						/*
						 * if ((!assocOverLap.getAssociationId().equals(association.getAssociationId()))
						 * && (((assocOverLap .getContainerLowNumber().compareTo(contNrLow) >= 0 &&
						 * assocOverLap.getContainerHighNumber().compareTo(contNrHigh) <= 0) ||
						 * (assocOverLap.getContainerLowNumber().compareTo(contNrLow) <= 0 &&
						 * assocOverLap.getContainerHighNumber().compareTo(contNrHigh) >= 0) ||
						 * (assocOverLap.getContainerLowNumber().compareTo(contNrLow) <= 0 &&
						 * assocOverLap.getContainerHighNumber().compareTo(contNrLow) >= 0) ||
						 * (assocOverLap.getContainerLowNumber().compareTo(contNrHigh) <= 0 &&
						 * assocOverLap.getContainerHighNumber().compareTo(contNrHigh) >= 0)) &&
						 * ((assocOverLap.getChassisLowNumber().compareTo(chassisNrLow) >= 0 &&
						 * assocOverLap.getChassisHighNumber().compareTo(chassisNrHigh) <= 0) ||
						 * (assocOverLap.getChassisLowNumber().compareTo(chassisNrLow) <= 0 &&
						 * assocOverLap.getChassisHighNumber().compareTo(chassisNrHigh) >= 0) ||
						 * (assocOverLap.getChassisLowNumber().compareTo(chassisNrLow) <= 0 &&
						 * assocOverLap.getChassisHighNumber().compareTo(chassisNrLow) >= 0) ||
						 * (assocOverLap.getChassisLowNumber().compareTo(chassisNrHigh) <= 0 &&
						 * assocOverLap.getChassisHighNumber() .compareTo(chassisNrHigh) >= 0)))) {
						 */
						BigDecimal existingLowCon = assocOverLap.getContainerLowNumber();
						BigDecimal existingHighCon = assocOverLap.getContainerHighNumber();
						BigDecimal existingLowChassis = assocOverLap.getChassisLowNumber();
						BigDecimal existingHighChassis = assocOverLap.getChassisHighNumber();
						boolean containerOverlapValidations = ((contNrLow.compareTo(existingLowCon) >= 0
								&& contNrHigh.compareTo(existingHighCon) <= 0)
								|| (contNrLow.compareTo(existingLowCon) >= 0
										&& contNrLow.compareTo(existingHighCon) <= 0)
								|| (contNrHigh.compareTo(existingLowCon) >= 0
										&& contNrHigh.compareTo(existingHighCon) <= 0));
						boolean chassisOverlapValidations = ((chassisNrLow.compareTo(existingLowChassis) >= 0
								&& chassisNrHigh.compareTo(existingHighChassis) <= 0)
								|| (chassisNrLow.compareTo(existingLowChassis) >= 0
										&& chassisNrLow.compareTo(existingHighChassis) <= 0)
								|| (chassisNrHigh.compareTo(existingLowChassis) >= 0
										&& chassisNrHigh.compareTo(existingHighChassis) <= 0));
						if (containerOverlapValidations && chassisOverlapValidations) {
							throw new RecordNotAddedException(
									"Container/Chassis Init and Container/Chassis Number Range are overlapping with existing records");
						}
					});
		}

		if (!Arrays.asList(CommonConstants.validLengths).contains(association.getContainerLength())) {
			throw new RecordNotAddedException("'containerLength' should be 20, 40, 45, 48 & 53");
		}

		if (!Arrays.asList(CommonConstants.validLengths).contains(association.getChassisLength())) {
			throw new RecordNotAddedException("'chassisLength' should be 20, 40, 45, 48 & 53");
		}

	}

	@Override
	public List<ContainerChassisAssociation> getAllControllerChassisAssociations() {

		List<ContainerChassisAssociation> associations = containerChassisAssociationRepo.findAll();
		if (associations.isEmpty()) {
			throw new NoRecordsFoundException("No Records are found for Container Chassis Association");
		}
		return associations;
	}

	@Override
	public ContainerChassisAssociation addContainerChassisAssociation(ContainerChassisAssociation association,
			Map<String, String> headers) {

		UserId.headerUserID(headers);

		ContainerChassisValidation(association);
		Long generatedAssociationId = containerChassisAssociationRepo.SGKLong();
		association.setAssociationId(generatedAssociationId);
		association.setCreateUserId(headers.get(CommonConstants.USER_ID).toUpperCase());
		association.setUpdateUserId(headers.get(CommonConstants.USER_ID).toUpperCase());
		association.setUpdateExtensionSchema(headers.get(CommonConstants.EXTENSION_SCHEMA).toUpperCase());
		association.setUversion("!");
		ContainerChassisAssociation addedAssociation = containerChassisAssociationRepo.save(association);
		return addedAssociation;
	}

	@Override
	public ContainerChassisAssociation updateContainerChassisAssociation(ContainerChassisAssociation association,
			Map<String, String> headers) {
		UserId.headerUserID(headers);
		if (containerChassisAssociationRepo.existsByAssociationIdAndUversion(association.getAssociationId(),
				association.getUversion())) {
			ContainerChassisAssociation existingAssociation = containerChassisAssociationRepo
					.findById(association.getAssociationId()).get();
			if (existingAssociation.getExpiredDateTime() == null) {
				ContainerChassisValidation(association);
				return updateExistingContainerChassisAssociation(existingAssociation, association, headers);
			} else
				throw new RecordAlreadyExistsException(
						"Record under this Association Id: " + existingAssociation.getAssociationId()
								+ " is already expired on : " + existingAssociation.getExpiredDateTime());
		} else
			throw new NoRecordsFoundException("No record Found to update Under this Association Id: "
					+ association.getAssociationId() + " and U_Version: " + association.getUversion());
	}

	private ContainerChassisAssociation updateExistingContainerChassisAssociation(
			ContainerChassisAssociation existingAssociation, ContainerChassisAssociation association,
			Map<String, String> headers) {
		existingAssociation.setUpdateUserId(headers.get(CommonConstants.USER_ID));
		existingAssociation.setUpdateExtensionSchema(association.getUpdateExtensionSchema());
		existingAssociation.setContainerInit(association.getContainerInit());
		existingAssociation.setContainerLowNumber(association.getContainerLowNumber());
		existingAssociation.setContainerHighNumber(association.getContainerHighNumber());
		existingAssociation.setContainerLength(association.getContainerLength());
		existingAssociation.setChassisInit(association.getChassisInit());
		existingAssociation.setChassisLowNumber(association.getChassisLowNumber());
		existingAssociation.setChassisHighNumber(association.getChassisHighNumber());
		existingAssociation.setChassisLength(association.getChassisLength());
		existingAssociation.setAllowIndicator(association.getAllowIndicator());
		if (StringUtils.isNotEmpty(existingAssociation.getUversion())) {
			existingAssociation.setUversion(
					Character.toString((char) ((((int) existingAssociation.getUversion().charAt(0) - 32) % 94) + 33)));
		}
		containerChassisAssociationRepo.save(existingAssociation);
		return existingAssociation;
	}

	@Override
	public ContainerChassisAssociation expireContainerChassisAssociation(ContainerChassisAssociation association,
			Map<String, String> headers) {
		UserId.headerUserID(headers);
		if (containerChassisAssociationRepo.existsByAssociationIdAndUversion(association.getAssociationId(),
				association.getUversion())) {
			ContainerChassisAssociation existingAssociation = containerChassisAssociationRepo
					.findById(association.getAssociationId()).get();
			if (existingAssociation.getExpiredDateTime() == null) {
				existingAssociation.setExpiredDateTime(new Timestamp(System.currentTimeMillis()));
				existingAssociation.setUpdateUserId(headers.get(CommonConstants.USER_ID));
				existingAssociation.setUpdateExtensionSchema(association.getUpdateExtensionSchema());
				if (StringUtils.isNotEmpty(existingAssociation.getUversion())) {
					existingAssociation.setUversion(Character
							.toString((char) ((((int) existingAssociation.getUversion().charAt(0) - 32) % 94) + 33)));
				}
				containerChassisAssociationRepo.save(existingAssociation);
				return existingAssociation;
			} else
				throw new RecordAlreadyExistsException(
						"Record under this Association Id: " + existingAssociation.getAssociationId()
								+ " is already expired on : " + existingAssociation.getExpiredDateTime());

		} else
			throw new NoRecordsFoundException("No record Found to Expire Under this Association Id: "
					+ association.getAssociationId() + " and U_Version: " + association.getUversion());
	}

}
