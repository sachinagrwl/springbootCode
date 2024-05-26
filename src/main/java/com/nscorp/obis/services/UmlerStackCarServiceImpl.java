package com.nscorp.obis.services;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.nscorp.obis.domain.StackWellLength;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nscorp.obis.common.CommonConstants;
import com.nscorp.obis.common.UserId;
import com.nscorp.obis.domain.UmlerStackCar;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.exception.RecordNotAddedException;
import com.nscorp.obis.repository.StackEquipmentWidthRepository;
import com.nscorp.obis.repository.StackWellLengthRepository;
import com.nscorp.obis.repository.UmlerStackCarRepository;

@Service
@Transactional
public class UmlerStackCarServiceImpl implements UmlerStackCarService {

	@Autowired
	UmlerStackCarRepository umlerStackCarRepository;

	@Autowired
	StackEquipmentWidthRepository stackEquipmentWidthRepository;

	@Autowired
	StackWellLengthRepository stackWellLengthRepository;

	@Override
	public List<UmlerStackCar> getUmlerStackCars(String aarType, String carInit) {
		List<UmlerStackCar> umlerStackCarList = umlerStackCarRepository.findAllByAarTypeAndCarInit(
				StringUtils.isBlank(aarType) ? null : aarType, StringUtils.isBlank(carInit) ? null : carInit);

		if (umlerStackCarList.isEmpty()) {
			throw new NoRecordsFoundException("No Record Found under this search!");
		}
		umlerStackCarList.forEach(umlerStackCar -> {
			umlerStackCar.setStackEquipmentWidthList(stackEquipmentWidthRepository.findByUmlerId(umlerStackCar.getUmlerId()));
		});
		
		return umlerStackCarList;
	}

	@Override
	public UmlerStackCar deleteUmlerStackCar(UmlerStackCar umlerStackCar) {
		if (umlerStackCarRepository.existsByUmlerIdAndUversion(umlerStackCar.getUmlerId(),
				umlerStackCar.getUversion())) {
			UmlerStackCar existingUmlerStackCar = umlerStackCarRepository.findById(umlerStackCar.getUmlerId()).get();
			existingUmlerStackCar.setStackEquipmentWidthList(stackEquipmentWidthRepository.findByUmlerId(umlerStackCar.getUmlerId()));
			if (stackEquipmentWidthRepository.existsByUmlerId(umlerStackCar.getUmlerId()))
				stackEquipmentWidthRepository.deleteByUmlerId(umlerStackCar.getUmlerId());

			if (stackWellLengthRepository.existsByUmlerId(umlerStackCar.getUmlerId()))
				stackWellLengthRepository.deleteByUmlerId(umlerStackCar.getUmlerId());

			umlerStackCarRepository.deleteById(umlerStackCar.getUmlerId());
			return existingUmlerStackCar;
		} else
			throw new NoRecordsFoundException("No record Found to delete Under this Umler Id: "
					+ umlerStackCar.getUmlerId() + " and U_Version: " + umlerStackCar.getUversion());
	}

	public void validationsStackCar(UmlerStackCar stackCar, Map<String, String> headers) {
		if(StringUtils.isNotBlank(stackCar.getUpdateExtensionSchema())) {
			stackCar.setUpdateExtensionSchema(stackCar.getUpdateExtensionSchema().toUpperCase());
		} else {
			throw new NullPointerException("Extension Schema should not be null, empty or blank");
		}

		if (StringUtils.isNotBlank(stackCar.getAarType()) && !stackCar.getAarType().startsWith("S")) {
			throw new RecordNotAddedException("AAR Type For Stack Cars should start only with 'S'");
		}

		if (StringUtils.isNotBlank(stackCar.getAarCd()) && !stackCar.getAarCd().startsWith("S")) {
			throw new RecordNotAddedException("AAR Cd For Stack Cars AAR Type should start only with 'S'");
		}

		if (stackCar.getCarLowNr() != null && stackCar.getCarHighNr() != null
				&& stackCar.getCarLowNr().compareTo(stackCar.getCarHighNr()) > 0) {
			throw new RecordNotAddedException("Car Low Nr: " + stackCar.getCarLowNr()
					+ " should be less than or equal to Car High Nr: " + stackCar.getCarHighNr());
		}

		if (stackCar.getMinEqWidth() != null && stackCar.getMaxEqWidth() != null
				&& stackCar.getMinEqWidth().compareTo(stackCar.getMaxEqWidth()) > 0) {
			throw new RecordNotAddedException("Min EQ Width: " + stackCar.getMinEqWidth()
					+ " should be less than or equal to Max EQ Width: " + stackCar.getMaxEqWidth());
		}

		if (stackCar.getMinEqLength() != null && stackCar.getMaxEqLength() != null) {
			if (!Arrays.asList(CommonConstants.VALID_CONT_LENGTHS).contains(stackCar.getMinEqLength())) {
				throw new RecordNotAddedException("'minEqLength' should be 20, 40, 45, 48 & 53");
			}
			if (!Arrays.asList(CommonConstants.VALID_CONT_LENGTHS).contains(stackCar.getMaxEqLength())) {
				throw new RecordNotAddedException("'maxEqLength' should be 20, 40, 45, 48 & 53");
			}
			if (stackCar.getMinEqLength().compareTo(stackCar.getMaxEqLength()) > 0) {
				throw new RecordNotAddedException("Min Eq Length: " + stackCar.getMinEqLength()
						+ " should be less than or equal to Max Eq Length: " + stackCar.getMaxEqLength());
			}
		}
		if (stackCar.getMinTrlrLength() != null && stackCar.getMaxTrlrLength() != null) {
			if (!Arrays.asList(CommonConstants.VALID_CONT_LENGTHS).contains(stackCar.getMinTrlrLength())) {
				throw new RecordNotAddedException("'minTrlrLength' should be 28, 40, 45, 48 & 53");
			}
			if (!Arrays.asList(CommonConstants.VALID_TRLR_LENGTHS).contains(stackCar.getMaxTrlrLength())) {
				throw new RecordNotAddedException("'maxTrlrLength' should be 28, 40, 45, 48 & 53");
			}
			if (stackCar.getMinTrlrLength().compareTo(stackCar.getMaxTrlrLength()) > 0) {
				throw new RecordNotAddedException("Min Trlr Length: " + stackCar.getMinTrlrLength()
						+ " should be less than or equal to Max Trlr Length: " + stackCar.getMaxTrlrLength());
			}
		}

		if (StringUtils.isNotBlank(stackCar.getLengthDeterminedWidthRestrictionsInd())
				&& stackCar.getLengthDeterminedWidthRestrictionsInd().equalsIgnoreCase("Y")) {
			stackCar.setMinEqWidth(null);
			stackCar.setMaxEqWidth(null);
		}

		if (StringUtils.equalsIgnoreCase(stackCar.getTofcCofcInd(), "T")) {
			stackCar.setEqPairsWellInd(null);
			stackCar.setEqPairsMinLength(null);
			stackCar.setEqPairsMaxLength(null);
			stackCar.setAcceptCntPairsOnTop(null);
			stackCar.setTopCntPairsMinLength(null);
			stackCar.setTopCntPairsMaxLength(null);
		}

		if (StringUtils.equalsIgnoreCase(stackCar.getTofcCofcInd(), "C")) {
			stackCar.setAcceptTrlrPairsInd(null);
			stackCar.setTrlrPairsMinLength(null);
			stackCar.setTrlrPairsMaxLength(null);
		}

		if (StringUtils.equalsIgnoreCase(stackCar.getAcceptCntPairsOnTop(), "N")) {
			stackCar.setTopCntPairsMinLength(null);
			stackCar.setTopCntPairsMaxLength(null);
		}

		if (StringUtils.equalsIgnoreCase(stackCar.getAcceptTrlrPairsInd(), "N")) {
			stackCar.setTrlrPairsMinLength(null);
			stackCar.setTrlrPairsMaxLength(null);
		}
	}

	@Override
	public UmlerStackCar insertStackCar(UmlerStackCar stackCar, Map<String, String> headers) {
		
		UserId.headerUserID(headers);
		stackCar.setUpdateExtensionSchema(headers.get(CommonConstants.EXTENSION_SCHEMA));
		validationsStackCar(stackCar, headers);
		if (umlerStackCarRepository.existsByAarType(stackCar.getAarType())) {
			throw new RecordAlreadyExistsException("AAR Type: " + stackCar.getAarType() + " already exists!");
		}
		if (stackCar.getAarCd() != null && stackCar.getAar1stNoLow() != null && stackCar.getAar1stNoHigh() != null
				&& stackCar.getAar2ndNoLow() != null && stackCar.getAar2ndNoHigh() != null
				&& umlerStackCarRepository.existsByAarCdAndAar1stNoLowAndAar1stNoHighAndAar2ndNoLowAndAar2ndNoHigh(
				stackCar.getAarCd(), stackCar.getAar1stNoLow(), stackCar.getAar1stNoHigh(),
				stackCar.getAar2ndNoLow(), stackCar.getAar2ndNoHigh())) {
			throw new RecordAlreadyExistsException("Multiple AAR Type already exists!");
		}
		if (StringUtils.isNotBlank(stackCar.getCarInit()) && stackCar.getCarLowNr() != null
				&& stackCar.getCarHighNr() != null && umlerStackCarRepository.existsByCarInitAndCarLowNrAndCarHighNr(
				stackCar.getCarInit(), stackCar.getCarLowNr(), stackCar.getCarHighNr())) {
			throw new RecordAlreadyExistsException("Car Low Nr: " + stackCar.getCarLowNr() + " and Car High Nr: "
					+ stackCar.getCarHighNr() + " already exists for Car Init: " + stackCar.getCarInit());
		}
		stackCar.setUmlerId(umlerStackCarRepository.SGKLong());

		if (StringUtils.equalsIgnoreCase(stackCar.getLengthDeterminedWidthRestrictionsInd(), "Y")) {

		stackCar.getStackEquipmentWidthList().forEach(stackEqWidth -> {
			stackEqWidth.setUversion("!");
			stackEqWidth.setCreateUserId(headers.get(CommonConstants.USER_ID));
			stackEqWidth.setUpdateUserId(headers.get(CommonConstants.USER_ID));
			stackEqWidth.setUpdateExtensionSchema(headers.get(CommonConstants.EXTENSION_SCHEMA));
			stackEqWidth.setUmlerId(stackCar.getUmlerId());
			stackEqWidth.setCreateDateTime(new Timestamp(System.currentTimeMillis()));

			stackEquipmentWidthRepository.save(stackEqWidth);
		});
		}
		
		stackCar.getStackWellLengthList().forEach(stackWellLength -> {
			stackWellLength.setUversion("!");
			stackWellLength.setCreateUserId(headers.get(CommonConstants.USER_ID));
			stackWellLength.setUpdateUserId(headers.get(CommonConstants.USER_ID));
			stackWellLength.setUpdateExtensionSchema(headers.get(CommonConstants.EXTENSION_SCHEMA));
			stackWellLength.setUmlerId(stackCar.getUmlerId());
			stackWellLength.setCreateDateTime(new Timestamp(System.currentTimeMillis()));
			
			stackWellLengthRepository.save(stackWellLength);
		});
		
		stackCar.setUversion("!");
		stackCar.setCreateUserId(headers.get(CommonConstants.USER_ID));
		stackCar.setUpdateUserId(headers.get(CommonConstants.USER_ID));
		stackCar.setCreateDateTime(new Timestamp(System.currentTimeMillis()));
		
		UmlerStackCar umlerStackCar = umlerStackCarRepository.save(stackCar);
		
		umlerStackCar.setStackEquipmentWidthList(stackEquipmentWidthRepository.findByUmlerId(umlerStackCar.getUmlerId()));
		
		return umlerStackCar;
		
	}

	@Override
	public UmlerStackCar updateStackCar(UmlerStackCar umlerStackCar, Map<String, String> headers) {
		UserId.headerUserID(headers);
		umlerStackCar.setUpdateUserId(headers.get(CommonConstants.USER_ID).toUpperCase());
		umlerStackCar.setUpdateExtensionSchema(headers.get(CommonConstants.EXTENSION_SCHEMA));
		if(umlerStackCarRepository.existsByUmlerIdAndUversion(umlerStackCar.getUmlerId(), umlerStackCar.getUversion())){
			validationsStackCar(umlerStackCar, headers);
			UmlerStackCar existingStackCar = umlerStackCarRepository
					.findById(umlerStackCar.getUmlerId()).get();
			existingStackCar.setUpdateUserId(umlerStackCar.getUpdateUserId());
			existingStackCar.setUpdateExtensionSchema(umlerStackCar.getUpdateExtensionSchema());
			existingStackCar.setSingleMultipleAarInd(umlerStackCar.getSingleMultipleAarInd());
			existingStackCar.setAarType(umlerStackCar.getAarType());
			existingStackCar.setAarCd(umlerStackCar.getAarCd());
			existingStackCar.setAar1stNoLow(umlerStackCar.getAar1stNoLow());
			existingStackCar.setAar1stNoHigh(umlerStackCar.getAar1stNoHigh());
			existingStackCar.setAar2ndNoLow(umlerStackCar.getAar2ndNoLow());
			existingStackCar.setAar2ndNoHigh(umlerStackCar.getAar2ndNoHigh());
			existingStackCar.setAar3rdNo(umlerStackCar.getAar3rdNo());
			existingStackCar.setCarInit(umlerStackCar.getCarInit());
			existingStackCar.setCarLowNr(umlerStackCar.getCarLowNr());
			existingStackCar.setCarHighNr(umlerStackCar.getCarHighNr());
			existingStackCar.setCarOwner(umlerStackCar.getCarOwner());
			existingStackCar.setTofcCofcInd(umlerStackCar.getTofcCofcInd());
			existingStackCar.setLengthDeterminedWidthRestrictionsInd(umlerStackCar.getLengthDeterminedWidthRestrictionsInd());
			existingStackCar.setMinEqWidth(umlerStackCar.getMinEqWidth());
			existingStackCar.setMaxEqWidth(umlerStackCar.getMaxEqWidth());
			existingStackCar.setEndWellLength(umlerStackCar.getEndWellLength());
			existingStackCar.setMedWellLength(umlerStackCar.getMedWellLength());
			existingStackCar.setNumberOfPlatform(umlerStackCar.getNumberOfPlatform());
			existingStackCar.setMinEqLength(umlerStackCar.getMinEqLength());
			existingStackCar.setMaxEqLength(umlerStackCar.getMaxEqLength());
			existingStackCar.setTopMinEqLength(umlerStackCar.getTopMinEqLength());
			existingStackCar.setTopMaxEqLength(umlerStackCar.getTopMaxEqLength());
			existingStackCar.setMedMinEqLength(umlerStackCar.getMedMinEqLength());
			existingStackCar.setMedMaxEqLength(umlerStackCar.getMedMaxEqLength());
			existingStackCar.setMedTopMinEqLength(umlerStackCar.getMedTopMinEqLength());
			existingStackCar.setMedTopMaxEqLength(umlerStackCar.getMedTopMaxEqLength());
			existingStackCar.setMed2MinEqLength(umlerStackCar.getMed2MinEqLength());
			existingStackCar.setMed2MaxEqLength(umlerStackCar.getMed2MaxEqLength());
			existingStackCar.setMed2TopMinEqLength(umlerStackCar.getMed2TopMinEqLength());
			existingStackCar.setMed2TopMaxEqLength(umlerStackCar.getMed2TopMaxEqLength());
			existingStackCar.setNoMedLengthOnTopInd(umlerStackCar.getNoMedLengthOnTopInd());
			existingStackCar.setCondMaxEqLength(umlerStackCar.getCondMaxEqLength());
			existingStackCar.setEqPairsWellInd(umlerStackCar.getEqPairsWellInd());
			existingStackCar.setEqPairsMinLength(umlerStackCar.getEqPairsMinLength());
			existingStackCar.setEqPairsMaxLength(umlerStackCar.getEqPairsMaxLength());
			existingStackCar.setAcceptCntPairsOnTop(umlerStackCar.getAcceptCntPairsOnTop());
			existingStackCar.setCondMaxEqLength(umlerStackCar.getCondMaxEqLength());
			existingStackCar.setTopCntPairsMinLength(umlerStackCar.getTopCntPairsMinLength());
			existingStackCar.setTopCntPairsMaxLength(umlerStackCar.getTopCntPairsMaxLength());
			existingStackCar.setMinTrlrLength(umlerStackCar.getMinTrlrLength());
			existingStackCar.setMaxTrlrLength(umlerStackCar.getMaxTrlrLength());
			existingStackCar.setAcceptTrlrPairsInd(umlerStackCar.getAcceptTrlrPairsInd());
			existingStackCar.setTrlrPairsMinLength(umlerStackCar.getTrlrPairsMinLength());
			existingStackCar.setTrlrPairsMaxLength(umlerStackCar.getTrlrPairsMaxLength());
			existingStackCar.setAcceptNoseMountedReefer(umlerStackCar.getAcceptNoseMountedReefer());
			existingStackCar.setMaxLoadWeight(umlerStackCar.getMaxLoadWeight());
			existingStackCar.setC20MaxWeight(umlerStackCar.getC20MaxWeight());
			if(StringUtils.isNotEmpty(existingStackCar.getUversion())) {
				existingStackCar.setUversion(
						Character.toString((char) ((((int)existingStackCar.getUversion().charAt(0) - 32) % 94) + 33)));
			}

			for(StackWellLength stackWellLength : umlerStackCar.getStackWellLengthList()){
				if(stackWellLength.getUmlerId() != null && stackWellLength.getAar1stNr() != null){
					if(stackWellLengthRepository.existsByUmlerIdAndAar1stNr(stackWellLength.getUmlerId(), stackWellLength.getAar1stNr())) {
						StackWellLength existingStackWellLength = stackWellLengthRepository.findByUmlerIdAndAar1stNr(stackWellLength.getUmlerId(), stackWellLength.getAar1stNr());
						existingStackWellLength.setMedWellLength(stackWellLength.getMedWellLength());
						existingStackWellLength.setEndWellLength(stackWellLength.getEndWellLength());
						System.out.println("MedWellLength: "+existingStackWellLength.getMedWellLength());
						System.out.println("EndWellLength: "+existingStackWellLength.getEndWellLength());
						existingStackWellLength.setUpdateUserId(headers.get(CommonConstants.USER_ID));
						existingStackWellLength.setUpdateExtensionSchema("STACKWELLLENGTH");
						existingStackWellLength.setUpdateDateTime(new Timestamp(System.currentTimeMillis()));
						stackWellLength.setUpdateUserId(headers.get(CommonConstants.USER_ID).toUpperCase());
						stackWellLength.setUpdateExtensionSchema(headers.get(CommonConstants.EXTENSION_SCHEMA).toUpperCase());
						stackWellLength.setUpdateDateTime(new Timestamp(System.currentTimeMillis()));
						if(StringUtils.isNotEmpty(existingStackWellLength.getUversion())) {
							existingStackWellLength.setUversion(
									Character.toString((char) ((((int)existingStackWellLength.getUversion().charAt(0) - 32) % 94) + 33)));
						}
						stackWellLength.setUversion(existingStackWellLength.getUversion());
						stackWellLengthRepository.save(existingStackWellLength);
					} else{
						throw new NoRecordsFoundException("Record with Umler Id: "+ stackWellLength.getUmlerId()+" and AAR 1st NR: "+stackWellLength.getAar1stNr()+" not found!");
					}
				}
			}
			umlerStackCarRepository.save(existingStackCar);
			return existingStackCar;
		} else{
			throw new NoRecordsFoundException("Record with Umler Id: "+ umlerStackCar.getUmlerId()+" not found!");
		}
	}
}
