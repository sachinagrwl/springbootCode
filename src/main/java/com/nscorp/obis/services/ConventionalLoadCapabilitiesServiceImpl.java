package com.nscorp.obis.services;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.nscorp.obis.common.CommonConstants;
import com.nscorp.obis.common.UserId;
import com.nscorp.obis.domain.ConventionalEquipmentWidth;
import com.nscorp.obis.domain.UmlerConventionalCar;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordNotAddedException;
import com.nscorp.obis.repository.ConventionalEquipmentWidthRepository;
import com.nscorp.obis.repository.UmlerConventionalCarRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class ConventionalLoadCapabilitiesServiceImpl implements ConventionalLoadCapabilitiesService {

	@Autowired
	ConventionalEquipmentWidthRepository convEqRepo;

	@Autowired
	UmlerConventionalCarRepository umblerConvRepo;

	private void validations(UmlerConventionalCar convCar) {

		if (convCar.getAarType() != null) {
			if (!convCar.getAarType().startsWith("P")) {
				throw new RecordNotAddedException("AAR Type For Conventional Cars should start only with 'P'");
			}
		}
		if (convCar.getAarCd() != null) {
			if (!convCar.getAarCd().startsWith("P")) {
				throw new RecordNotAddedException("AAR Cd For Conventional Cars AAR Type should start only with 'P'");
			}
		}
		if (convCar.getCarLowNr() != null && convCar.getCarHighNr() != null) {
			if (convCar.getCarLowNr().compareTo(convCar.getCarHighNr()) > 0) {
				throw new RecordNotAddedException("Car Low Nr: " + convCar.getCarLowNr()
						+ " should be less than or equal to Car High Nr: " + convCar.getCarHighNr());
			}
		}

		if (convCar.getMinEqWidth() != null && convCar.getMaxEqWidth() != null) {
			if (convCar.getMinEqWidth().compareTo(convCar.getMaxEqWidth()) > 0) {
				throw new RecordNotAddedException("Min EQ Width: " + convCar.getMinEqWidth()
						+ " should be less than or equal to Max EQ Width: " + convCar.getMaxEqWidth());
			}
		}
		if (convCar.getMinEqLength() != null && convCar.getMaxEqLength() != null) {
			if (!Arrays.asList(CommonConstants.VALID_CONT_LENGTHS).contains(convCar.getMinEqLength())) {
				throw new RecordNotAddedException("'minEqLength' should be 20, 40, 45, 48 & 53");
			}
			if (!Arrays.asList(CommonConstants.VALID_CONT_LENGTHS).contains(convCar.getMaxEqLength())) {
				throw new RecordNotAddedException("'maxEqLength' should be 20, 40, 45, 48 & 53");
			}
			if (convCar.getMinEqLength().compareTo(convCar.getMaxEqLength()) > 0) {
				throw new RecordNotAddedException("Min Cont Length: " + convCar.getMinEqLength()
						+ " should be less than or equal to Max Cont Length: " + convCar.getMaxEqLength());
			}
		}
		if (convCar.getMinTrailerLength() != null && convCar.getMaxTrailerLength() != null) {
			if (!Arrays.asList(CommonConstants.VALID_TRLR_LENGTHS).contains(convCar.getMinTrailerLength())) {
				throw new RecordNotAddedException("'minTrailerLength' should be 28, 40, 45, 48 & 53");
			}
			if (!Arrays.asList(CommonConstants.VALID_TRLR_LENGTHS).contains(convCar.getMaxTrailerLength())) {
				throw new RecordNotAddedException("'maxTrailerLength' should be 28, 40, 45, 48 & 53");
			}
			if (convCar.getMinTrailerLength().compareTo(convCar.getMaxTrailerLength()) > 0) {
				throw new RecordNotAddedException("Min Trlr Length: " + convCar.getMinTrailerLength()
						+ " should be less than or equal to Max Trlr Length: " + convCar.getMaxTrailerLength());
			}
		}
		if (convCar.getSingleDoubleWellInd() != null) {
			if (convCar.getSingleDoubleWellInd().equals("S")) {
				convCar.setAccept2c20Ind(null);
				convCar.setAccept3t28Ind(null);
				convCar.setReeferAwellInd(null);
				convCar.setReeferTofcInd(null);
				convCar.setNoReeferT40Ind(null);
			}
			if (convCar.getTofcCofcInd() != null && convCar.getTofcCofcInd().equals("T")) {
				if (convCar.getSingleDoubleWellInd().equals("S")) {
					convCar.setMinEqLength(null);
					convCar.setMaxEqLength(null);
					convCar.setAggregateLength(null);
					convCar.setTotCofcLength(null);
					convCar.setAccept2c20Ind(null);
					convCar.setAccept3t28Ind(null);
				}
				if (convCar.getSingleDoubleWellInd().equals("D")) {
					convCar.setMinEqLength(null);
					convCar.setMaxEqLength(null);
					convCar.setAccept2c20Ind(null);
				}
			}
			if (convCar.getTofcCofcInd() != null && convCar.getTofcCofcInd().equals("C")) {
				if (convCar.getSingleDoubleWellInd().equals("S")) {
					convCar.setMinTrailerLength(null);
					convCar.setMaxTrailerLength(null);
					convCar.setAggregateLength(null);
					convCar.setTotCofcLength(null);
					convCar.setAccept2c20Ind(null);
					convCar.setAccept3t28Ind(null);
				}
				if (convCar.getSingleDoubleWellInd().equals("D")) {
					convCar.setMinTrailerLength(null);
					convCar.setMaxTrailerLength(null);
					convCar.setAccept3t28Ind(null);
				}
			}
			if (convCar.getTofcCofcInd() != null && convCar.getTofcCofcInd().equals("B")
					&& convCar.getSingleDoubleWellInd().equals("S")) {
				convCar.setAggregateLength(null);
				convCar.setTotCofcLength(null);
				convCar.setAccept2c20Ind(null);
				convCar.setAccept3t28Ind(null);
			}
			if (StringUtils.isEmpty(convCar.getTofcCofcInd()) && convCar.getSingleDoubleWellInd().equals("S")) {
				convCar.setAggregateLength(null);
				convCar.setTotCofcLength(null);
				convCar.setAccept2c20Ind(null);
				convCar.setAccept3t28Ind(null);
			}

		}
		if (convCar.getAcceptNoseMountedReefer() == null || convCar.getAcceptNoseMountedReefer().equals("N")) {
			convCar.setReeferAwellInd(null);
			convCar.setReeferTofcInd(null);
			convCar.setNoReeferT40Ind(null);
		}

	}

	@Override
	public List<UmlerConventionalCar> getConvLoad(String aarType, String carInit) {

		List<UmlerConventionalCar> umlerConvCarList = umblerConvRepo.findAllByAarTypeAndCarInit(
				StringUtils.isBlank(aarType) ? null : aarType, StringUtils.isBlank(carInit) ? null : carInit);

		if (umlerConvCarList.isEmpty()) {
			throw new NoRecordsFoundException("No Record Found under this search!");
		}
		return umlerConvCarList;
	}

	@SuppressWarnings("null")
	@Override
	public UmlerConventionalCar addConventionalCar(UmlerConventionalCar convCar, Map<String, String> headers) {

		UserId.headerUserID(headers);
		convCar.setUpdateExtensionSchema(headers.get(CommonConstants.EXTENSION_SCHEMA));
		if (convCar.getUpdateExtensionSchema() != null) {
			convCar.setUpdateExtensionSchema(convCar.getUpdateExtensionSchema().toUpperCase());
		} else {
			throw new NullPointerException("Extension Schema should not be null, empty or blank");
		}

		if (convCar.getAarType() != null) {
			if (!convCar.getAarType().startsWith("P")) {
				throw new RecordNotAddedException("AAR Type For Conventional Cars should start only with 'P'");
			}

			if (umblerConvRepo.existsByAarType(convCar.getAarType())) {
				throw new RecordNotAddedException("AAR Type: " + convCar.getAarType() + " already exists!");
			}
		}

		if (convCar.getAarCd() != null && convCar.getAar1stNoLow() != null && convCar.getAar1stNoHigh() != null
				&& convCar.getAar2ndNoLow() != null && convCar.getAar2ndNoHigh() != null) {

			if (umblerConvRepo.existsByAarCdAndAar1stNoLowAndAar1stNoHighAndAar2ndNoLowAndAar2ndNoHigh(
					convCar.getAarCd(), convCar.getAar1stNoLow(), convCar.getAar1stNoHigh(), convCar.getAar2ndNoLow(),
					convCar.getAar2ndNoHigh())) {
				throw new RecordNotAddedException("Multiple AAR Type already exists!");
			}
		}

		if (convCar.getCarInit() != null && convCar.getCarLowNr() != null && convCar.getCarHighNr() != null) {
			if (umblerConvRepo.existsByCarInitAndCarLowNrAndCarHighNr(convCar.getCarInit(), convCar.getCarLowNr(),
					convCar.getCarHighNr())) {
				throw new RecordNotAddedException("Car Low Nr: " + convCar.getCarLowNr() + " and Car High Nr: "
						+ convCar.getCarHighNr() + " already exists for Car Init: " + convCar.getCarInit());
			}
		}
		convCar.setUmlerId(umblerConvRepo.SGKLong());
		validations(convCar);
		for (ConventionalEquipmentWidth convEqWidth : convCar.getConventionalEquipmentWidth()) {
			if (convEqWidth.getAar1stNr() != null) {
				if (convEqWidth.getMinEqWidth() != null && convEqWidth.getMaxEqWidth() != null) {
					if (convEqWidth.getMinEqWidth().compareTo(convEqWidth.getMaxEqWidth()) > 0) {
						throw new RecordNotAddedException("Min EQ Width: " + convEqWidth.getMinEqWidth()
								+ " should be less than or equal to Max EQ Width: " + convEqWidth.getMaxEqWidth());
					}
				}
				convEqWidth.setUversion("!");
				convEqWidth.setCreateDateTime(new Timestamp(System.currentTimeMillis()));
				convEqWidth.setCreateUserId(headers.get(CommonConstants.USER_ID));
				convEqWidth.setUpdateUserId(headers.get(CommonConstants.USER_ID));
				convEqWidth.setUpdateExtensionSchema("CONVEQWIDTH");
				convEqWidth.setUmlerId(convCar.getUmlerId());
				convEqRepo.save(convEqWidth);
			}
		}
		convCar.setCreateDateTime(new Timestamp(System.currentTimeMillis()));
		convCar.setCreateUserId(headers.get(CommonConstants.USER_ID));
		convCar.setUpdateUserId(headers.get(CommonConstants.USER_ID));

		convCar.setUversion("!");

		UmlerConventionalCar addedConvCar = umblerConvRepo.save(convCar);
		if (addedConvCar == null) {
			throw new RecordNotAddedException(
					"Record with Umler Id " + addedConvCar.getUmlerId() + " cannot be Added!");
		}
		return addedConvCar;
	}

	@Override
	public UmlerConventionalCar updateConventionalCar(UmlerConventionalCar convCar, Map<String, String> headers) {

		UserId.headerUserID(headers);
		convCar.setUpdateUserId(headers.get(CommonConstants.USER_ID).toUpperCase());
		convCar.setUpdateExtensionSchema(headers.get(CommonConstants.EXTENSION_SCHEMA));
		if (StringUtils.isNotBlank(convCar.getUpdateExtensionSchema())) {
			convCar.setUpdateExtensionSchema(convCar.getUpdateExtensionSchema().toUpperCase());
		} else {
			throw new NullPointerException("Extension Schema should not be null, empty or blank");
		}
		if (umblerConvRepo.existsByUmlerId(convCar.getUmlerId())) {
			validations(convCar);
			UmlerConventionalCar existingConvCar = umblerConvRepo.findByUmlerId(convCar.getUmlerId());

			if (StringUtils.isNotBlank(convCar.getAarType())
					&& !StringUtils.equalsIgnoreCase(existingConvCar.getAarType(), convCar.getAarType())
					&& umblerConvRepo.existsByAarType(convCar.getAarType())) {
				throw new RecordNotAddedException("AAR Type: " + convCar.getAarType() + " already exists!");
			}

			boolean isAarcdAndLowHighUpdated = !StringUtils.equalsIgnoreCase(existingConvCar.getAarCd(),
					convCar.getAarCd())
					|| !StringUtils.equalsIgnoreCase(existingConvCar.getAar1stNoLow(), convCar.getAar1stNoLow())
					|| !StringUtils.equalsIgnoreCase(existingConvCar.getAar1stNoHigh(), convCar.getAar1stNoHigh())
					|| !StringUtils.equalsIgnoreCase(existingConvCar.getAar2ndNoLow(), convCar.getAar2ndNoLow())
					|| !StringUtils.equalsIgnoreCase(existingConvCar.getAar2ndNoHigh(), convCar.getAar2ndNoHigh());
			if (isAarcdAndLowHighUpdated
					&& StringUtils.isNoneBlank(convCar.getAarCd(), convCar.getAar1stNoLow(), convCar.getAar1stNoHigh(),
							convCar.getAar2ndNoLow(), convCar.getAar2ndNoHigh())
					&& umblerConvRepo.existsByAarCdAndAar1stNoLowAndAar1stNoHighAndAar2ndNoLowAndAar2ndNoHigh(
							convCar.getAarCd(), convCar.getAar1stNoLow(), convCar.getAar1stNoHigh(),
							convCar.getAar2ndNoLow(), convCar.getAar2ndNoHigh())) {

				throw new RecordNotAddedException("Multiple AAR Type already exists!");
			}
			if (convCar.getCarHighNr() != null && convCar.getCarLowNr() != null
					&& StringUtils.isNotBlank(convCar.getCarInit())
					&& umblerConvRepo.existsByCarInitAndCarLowNrAndCarHighNr(convCar.getCarInit(),
							convCar.getCarLowNr(), convCar.getCarHighNr())) {
				List<UmlerConventionalCar> existingCarInits = umblerConvRepo.findByCarInitAndCarLowNrAndCarHighNr(
						convCar.getCarInit(), convCar.getCarLowNr(), convCar.getCarHighNr());

				List<UmlerConventionalCar> filteredCarInits = existingCarInits.stream()
						.filter(car -> !car.getUmlerId().equals(convCar.getUmlerId())).collect(Collectors.toList());
				if (!CollectionUtils.isEmpty(filteredCarInits))
					throw new RecordNotAddedException("Car Low Nr: " + convCar.getCarLowNr() + " and Car High Nr: "
							+ convCar.getCarHighNr() + " already exists for Car Init: " + convCar.getCarInit());
			}

			existingConvCar.setUpdateUserId(convCar.getUpdateUserId());
			existingConvCar.setUpdateExtensionSchema(convCar.getUpdateExtensionSchema());
			existingConvCar.setAarType(convCar.getAarType());
			existingConvCar.setSingleMultipleAarInd(convCar.getSingleMultipleAarInd());
			existingConvCar.setAarCd(convCar.getAarCd());
			existingConvCar.setAar1stNoLow(convCar.getAar1stNoLow());
			existingConvCar.setAar1stNoHigh(convCar.getAar1stNoHigh());
			existingConvCar.setAar2ndNoLow(convCar.getAar2ndNoLow());
			existingConvCar.setAar2ndNoHigh(convCar.getAar2ndNoHigh());
			existingConvCar.setAar3rdNo(convCar.getAar3rdNo());
			existingConvCar.setCarInit(convCar.getCarInit());
			existingConvCar.setCarLowNr(convCar.getCarLowNr());
			existingConvCar.setCarHighNr(convCar.getCarHighNr());
			existingConvCar.setCarOwner(convCar.getCarOwner());
			existingConvCar.setTofcCofcInd(convCar.getTofcCofcInd());
			existingConvCar.setMinEqWidth(convCar.getMinEqWidth());
			existingConvCar.setMaxEqWidth(convCar.getMaxEqWidth());
			existingConvCar.setSingleDoubleWellInd(convCar.getSingleDoubleWellInd());
			existingConvCar.setMinEqLength(convCar.getMinEqLength());
			existingConvCar.setMaxEqLength(convCar.getMaxEqLength());
			existingConvCar.setMinTrailerLength(convCar.getMinTrailerLength());
			existingConvCar.setMaxTrailerLength(convCar.getMaxTrailerLength());
			existingConvCar.setAggregateLength(convCar.getAggregateLength());
			existingConvCar.setTotCofcLength(convCar.getTotCofcLength());
			existingConvCar.setAccept2c20Ind(convCar.getAccept3t28Ind());
			existingConvCar.setAcceptNoseMountedReefer(convCar.getAcceptNoseMountedReefer());
			existingConvCar.setReeferAwellInd(convCar.getReeferAwellInd());
			existingConvCar.setReeferTofcInd(convCar.getReeferTofcInd());
			existingConvCar.setNoReeferT40Ind(convCar.getNoReeferT40Ind());
			existingConvCar.setMaxLoadWeight(convCar.getMaxLoadWeight());
			existingConvCar.setC20MaxWeight(convCar.getC20MaxWeight());
			if (StringUtils.isNotEmpty(existingConvCar.getUversion())) {
				existingConvCar.setUversion(
						Character.toString((char) ((((int) existingConvCar.getUversion().charAt(0) - 32) % 94) + 33)));
			}

			for (ConventionalEquipmentWidth convEqWidth : convCar.getConventionalEquipmentWidth()) {
				if (convEqWidth.getUmlerId() != null && convEqWidth.getAar1stNr() != null) {

					if (convEqRepo.existsByUmlerIdAndAar1stNr(convEqWidth.getUmlerId(), convEqWidth.getAar1stNr())) {
						if (convEqWidth.getMinEqWidth() != null && convEqWidth.getMaxEqWidth() != null) {
							if (convEqWidth.getMinEqWidth().compareTo(convEqWidth.getMaxEqWidth()) > 0) {
								throw new RecordNotAddedException("Min EQ Width: " + convEqWidth.getMinEqWidth()
										+ " should be less than or equal to Max EQ Width: "
										+ convEqWidth.getMaxEqWidth());
							}
						}
						ConventionalEquipmentWidth existingEqWidth = convEqRepo
								.findByUmlerIdAndAar1stNr(convEqWidth.getUmlerId(), convEqWidth.getAar1stNr());
						existingEqWidth.setMinEqWidth(convEqWidth.getMinEqWidth());
						existingEqWidth.setMaxEqWidth(convEqWidth.getMaxEqWidth());
						log.info("MinEqWidth: " + existingEqWidth.getMinEqWidth());
						log.info("MaxEqWidth: " + existingEqWidth.getMaxEqWidth());
						System.out.println("MinEqWidth: " + existingEqWidth.getMinEqWidth());
						System.out.println("MaxEqWidth: " + existingEqWidth.getMaxEqWidth());
						existingEqWidth.setUpdateUserId(headers.get(CommonConstants.USER_ID));
						existingEqWidth.setUpdateExtensionSchema("CONVEQWIDTH");
						existingEqWidth.setUpdateDateTime(new Timestamp(System.currentTimeMillis()));
						convEqWidth.setUpdateUserId(headers.get(CommonConstants.USER_ID).toUpperCase());
						convEqWidth
								.setUpdateExtensionSchema(headers.get(CommonConstants.EXTENSION_SCHEMA).toUpperCase());
						convEqWidth.setUpdateDateTime(new Timestamp(System.currentTimeMillis()));
						if (StringUtils.isNotEmpty(existingEqWidth.getUversion())) {
							existingEqWidth.setUversion(Character.toString(
									(char) ((((int) existingEqWidth.getUversion().charAt(0) - 32) % 94) + 33)));
						}
						convEqWidth.setUversion(existingEqWidth.getUversion());
						convEqRepo.saveAndFlush(existingEqWidth);
					} else {
						if (StringUtils.isNotBlank(convEqWidth.getAar1stNr())) {
							if (convEqWidth.getMinEqWidth() != null && convEqWidth.getMaxEqWidth() != null
									&& convEqWidth.getMinEqWidth().compareTo(convEqWidth.getMaxEqWidth()) > 0) {
								throw new RecordNotAddedException("Min EQ Width: " + convEqWidth.getMinEqWidth()
										+ " should be less than or equal to Max EQ Width: "
										+ convEqWidth.getMaxEqWidth());
							}
							convEqWidth.setUversion("!");
							convEqWidth.setCreateDateTime(new Timestamp(System.currentTimeMillis()));
							convEqWidth.setCreateUserId(headers.get(CommonConstants.USER_ID));
							convEqWidth.setUpdateUserId(headers.get(CommonConstants.USER_ID));
							convEqWidth.setUpdateExtensionSchema("CONVEQWIDTH");
							convEqWidth.setUmlerId(convCar.getUmlerId());
							convEqRepo.saveAndFlush(convEqWidth);
						}
					}

				}
			}
			List<String> existingAarNrs = convEqRepo.findAar1stNRByUmlerId(existingConvCar.getUmlerId());
			List<String> updateRequestAarNrs = convCar.getConventionalEquipmentWidth().stream()
					.map(convEq -> convEq.getAar1stNr()).collect(Collectors.toList());
			existingAarNrs.removeAll(updateRequestAarNrs);
			existingAarNrs.forEach(aar -> convEqRepo.deleteByUmlerIdAndAar1stNr(existingConvCar.getUmlerId(), aar));
			UmlerConventionalCar savedCar = umblerConvRepo.saveAndFlush(existingConvCar);
			savedCar.setConventionalEquipmentWidth(convEqRepo.findByUmlerId(existingConvCar.getUmlerId()));
			return savedCar;
		} else {
			throw new NoRecordsFoundException("Record with Umler Id: " + convCar.getUmlerId() + " not found!");
		}

	}

	@Override
	public UmlerConventionalCar deleteConvLoad(UmlerConventionalCar umlerConventionalCar) {
		if (umblerConvRepo.existsByUmlerIdAndUversion(umlerConventionalCar.getUmlerId(),
				umlerConventionalCar.getUversion())) {
			UmlerConventionalCar existingUmlerConventionalCar = umblerConvRepo
					.findById(umlerConventionalCar.getUmlerId()).get();
			if (convEqRepo.existsByUmlerId(umlerConventionalCar.getUmlerId())) {
				convEqRepo.deleteByUmlerId(umlerConventionalCar.getUmlerId());
			}
			umblerConvRepo.deleteById(umlerConventionalCar.getUmlerId());
			return existingUmlerConventionalCar;
		} else
			throw new NoRecordsFoundException("No record Found to delete Under this Umler Id: "
					+ umlerConventionalCar.getUmlerId() + " and U_Version: " + umlerConventionalCar.getUversion());
	}

}
