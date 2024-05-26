package com.nscorp.obis.services;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.nscorp.obis.common.CommonConstants;
import com.nscorp.obis.common.UserId;
import com.nscorp.obis.domain.ConventionalEquipmentWidth;
import com.nscorp.obis.domain.LowProfileEquipmentWidth;
import com.nscorp.obis.domain.UmlerConventionalCar;
import com.nscorp.obis.exception.RecordNotAddedException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nscorp.obis.domain.UmlerLowProfileCar;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.repository.LowProfileEquipmentWidthRepository;
import com.nscorp.obis.repository.UmlerLowProfileCarRepository;

@Service
@Transactional
public class UmlerLowProfileCarServiceImpl implements UmlerLowProfileCarService {
	
	@Autowired
	UmlerLowProfileCarRepository umlerLowProfileCarRepository;
	
	@Autowired
	LowProfileEquipmentWidthRepository lowProfileEqWidthRepository;

	private void validations(UmlerLowProfileCar lowProfileCar) {

		if (lowProfileCar.getAarType() != null) {
			if (!lowProfileCar.getAarType().startsWith("Q")) {
				throw new RecordNotAddedException("AAR Type For Conventional Cars should start only with 'Q'");
			}
		}
		if (lowProfileCar.getAarCd() != null) {
			if (!lowProfileCar.getAarCd().startsWith("Q")) {
				throw new RecordNotAddedException("AAR Cd For Conventional Cars AAR Type should start only with 'Q'");
			}
		}
		if (lowProfileCar.getCarLowNr() != null && lowProfileCar.getCarHighNr() != null) {
			if (lowProfileCar.getCarLowNr().compareTo(lowProfileCar.getCarHighNr()) > 0) {
				throw new RecordNotAddedException("Car Low Nr: " + lowProfileCar.getCarLowNr()
						+ " should be less than or equal to Car High Nr: "
						+ lowProfileCar.getCarHighNr());
			}
		}

		if (lowProfileCar.getMinEqWidth() != null && lowProfileCar.getMaxEqWidth() != null) {
			if (lowProfileCar.getMinEqWidth().compareTo(lowProfileCar.getMaxEqWidth()) > 0) {
				throw new RecordNotAddedException("Min EQ Width: " + lowProfileCar.getMinEqWidth()
						+ " should be less than or equal to Max EQ Width: "
						+ lowProfileCar.getMaxEqWidth());
			}
		}
		if (lowProfileCar.getMinEqLength() != null && lowProfileCar.getMaxEqLength() != null) {
			if (!Arrays.asList(CommonConstants.VALID_CONT_LENGTHS).contains(lowProfileCar.getMinEqLength())) {
				throw new RecordNotAddedException("'minEqLength' should be 20, 40, 45, 48 & 53");
			}
			if (!Arrays.asList(CommonConstants.VALID_CONT_LENGTHS).contains(lowProfileCar.getMaxEqLength())) {
				throw new RecordNotAddedException("'maxEqLength' should be 20, 40, 45, 48 & 53");
			}
			if (lowProfileCar.getMinEqLength().compareTo(lowProfileCar.getMaxEqLength()) > 0) {
				throw new RecordNotAddedException("Min Cont Length: " + lowProfileCar.getMinEqLength()
						+ " should be less than or equal to Max Cont Length: "
						+ lowProfileCar.getMaxEqLength());
			}
		}
		/* Per Car */
		if(lowProfileCar.getPlatCarInd() != null && lowProfileCar.getPlatCarInd().equals("C")){
			lowProfileCar.setMinEqLength(null);
			lowProfileCar.setMaxEqLength(null);
			lowProfileCar.setMinTrailerLength(null);
			lowProfileCar.setMaxTrailerLength(null);
			lowProfileCar.setCntPairsWellInd(null);
			lowProfileCar.setCntPairsMinLength(null);
			lowProfileCar.setCntPairsMaxLength(null);
			lowProfileCar.setAcceptTrailerPairsInd(null);
			lowProfileCar.setTrailerPairsMinLength(null);
			lowProfileCar.setTrailerPairsMaxLength(null);
		}

//		/* Per Platform */
//		if(lowProfileCar.getPlatCarInd() != null && lowProfileCar.getPlatCarInd().equals("P")){
//
//		}
	}

	@Override
	public List<UmlerLowProfileCar> getUmlerLowProfileCars(String aarType, String carInit) {
		List<UmlerLowProfileCar> umlerLowProfileCarList = umlerLowProfileCarRepository.findAllByAarTypeAndCarInit(
				StringUtils.isBlank(aarType) ? null : aarType, StringUtils.isBlank(carInit) ? null : carInit);

		if (umlerLowProfileCarList.isEmpty()) {
			throw new NoRecordsFoundException("No Record Found under this search!");
		}
		return umlerLowProfileCarList;
	}

	@Override
	public UmlerLowProfileCar addLowProfileCar(UmlerLowProfileCar lowProfileCar, Map<String, String> headers) {
		UserId.headerUserID(headers);
		lowProfileCar.setUpdateExtensionSchema(headers.get(CommonConstants.EXTENSION_SCHEMA));
		if(lowProfileCar.getUpdateExtensionSchema() != null) {
			lowProfileCar.setUpdateExtensionSchema(lowProfileCar.getUpdateExtensionSchema().toUpperCase());
		} else {
			throw new NullPointerException("Extension Schema should not be null, empty or blank");
		}
		if(lowProfileCar.getAarType() != null){
			if(!lowProfileCar.getAarType().startsWith("Q")){
				throw new RecordNotAddedException("AAR Type For Conventional Cars should start only with 'Q'");
			}

			if(umlerLowProfileCarRepository.existsByAarType(lowProfileCar.getAarType())){
				throw new RecordNotAddedException("AAR Type: "+lowProfileCar.getAarType()+" already exists!");
			}
		}
		if(lowProfileCar.getAarCd() != null && lowProfileCar.getAar1stNoLow() != null && lowProfileCar.getAar1stNoHigh() != null && lowProfileCar.getAar2ndNoLow() != null && lowProfileCar.getAar2ndNoHigh() != null){

			if(umlerLowProfileCarRepository.existsByAarCdAndAar1stNoLowAndAar1stNoHighAndAar2ndNoLowAndAar2ndNoHigh(lowProfileCar.getAarCd(), lowProfileCar.getAar1stNoLow(), lowProfileCar.getAar1stNoHigh(), lowProfileCar.getAar2ndNoLow(), lowProfileCar.getAar2ndNoHigh())){
				throw new RecordNotAddedException("Multiple AAR Type already exists!");
			}
		}

		if(lowProfileCar.getCarInit() != null && lowProfileCar.getCarLowNr() != null && lowProfileCar.getCarHighNr() != null){
			if(umlerLowProfileCarRepository.existsByCarInitAndCarLowNrAndCarHighNr(lowProfileCar.getCarInit(), lowProfileCar.getCarLowNr(), lowProfileCar.getCarHighNr())){
				throw new RecordNotAddedException("Car Low Nr: "+lowProfileCar.getCarLowNr()+" and Car High Nr: "+lowProfileCar.getCarHighNr()+" already exists for Car Init: "+lowProfileCar.getCarInit());
			}
		}

		if(lowProfileCar.getAarType() != null){
			if(Integer.valueOf(lowProfileCar.getAarType().substring(2,2)) == 0){
				lowProfileCar.setNumberOfPlatform(10);
			} else {
				lowProfileCar.setNumberOfPlatform(Integer.valueOf(lowProfileCar.getAarType().substring(2, 2)));
			}
		}
		lowProfileCar.setUmlerId(umlerLowProfileCarRepository.SGKLong());
		validations(lowProfileCar);
		for(LowProfileEquipmentWidth lowProfileEqWidth : lowProfileCar.getLowProfileEquipmentWidth()){
			if(lowProfileEqWidth.getAar1stNr() != null){
				if(lowProfileEqWidth.getMinEqWidth() != null && lowProfileEqWidth.getMaxEqWidth() != null) {
					if (lowProfileEqWidth.getMinEqWidth().compareTo(lowProfileEqWidth.getMaxEqWidth()) > 0) {
						throw new RecordNotAddedException("Min EQ Width: " + lowProfileEqWidth.getMinEqWidth()
								+ " should be less than or equal to Max EQ Width: "
								+ lowProfileEqWidth.getMaxEqWidth());
					}
				}
				lowProfileEqWidth.setUversion("!");
				lowProfileEqWidth.setCreateDateTime(new Timestamp(System.currentTimeMillis()));
				lowProfileEqWidth.setCreateUserId(headers.get(CommonConstants.USER_ID));
				lowProfileEqWidth.setUpdateUserId(headers.get(CommonConstants.USER_ID));
				lowProfileEqWidth.setUpdateExtensionSchema("LOWPROFEQWIDTH");
				lowProfileEqWidth.setUmlerId(lowProfileCar.getUmlerId());
				lowProfileEqWidthRepository.save(lowProfileEqWidth);
			}
		}
		lowProfileCar.setCreateDateTime(new Timestamp(System.currentTimeMillis()));
		lowProfileCar.setCreateUserId(headers.get(CommonConstants.USER_ID));
		lowProfileCar.setUpdateUserId(headers.get(CommonConstants.USER_ID));

		lowProfileCar.setUversion("!");

		UmlerLowProfileCar addedLowProfileCar = umlerLowProfileCarRepository.save(lowProfileCar);
		if(addedLowProfileCar == null) {
			throw new RecordNotAddedException("Record with Umler Id "+addedLowProfileCar.getUmlerId()+" cannot be Added!");
		}
		return addedLowProfileCar;
	}

	@Override
	public UmlerLowProfileCar updateUmlerLowProfileCars(UmlerLowProfileCar umlerLowProfileCar, Map<String, String> headers) {
		UserId.headerUserID(headers);
		umlerLowProfileCar.setUpdateUserId(headers.get(CommonConstants.USER_ID).toUpperCase());
		umlerLowProfileCar.setUpdateExtensionSchema(headers.get(CommonConstants.EXTENSION_SCHEMA));
		if (StringUtils.isNotBlank(umlerLowProfileCar.getUpdateExtensionSchema())) {
			umlerLowProfileCar.setUpdateExtensionSchema(umlerLowProfileCar.getUpdateExtensionSchema().toUpperCase());
		} else {
			throw new NullPointerException("Extension Schema should not be null, empty or blank");
		}
		if(umlerLowProfileCarRepository.existsByUmlerIdAndUversion(umlerLowProfileCar.getUmlerId(), umlerLowProfileCar.getUversion())){
			validations(umlerLowProfileCar);
			UmlerLowProfileCar existingLowProfileCar = umlerLowProfileCarRepository
					.findById(umlerLowProfileCar.getUmlerId()).get();
			existingLowProfileCar.setUpdateUserId(umlerLowProfileCar.getUpdateUserId());
			existingLowProfileCar.setUpdateExtensionSchema(umlerLowProfileCar.getUpdateExtensionSchema());
			existingLowProfileCar.setSingleMultipleAarInd(umlerLowProfileCar.getSingleMultipleAarInd());
			existingLowProfileCar.setAarType(umlerLowProfileCar.getAarType());
			existingLowProfileCar.setAarCd(umlerLowProfileCar.getAarCd());
			existingLowProfileCar.setAar1stNoLow(umlerLowProfileCar.getAar1stNoLow());
			existingLowProfileCar.setAar1stNoHigh(umlerLowProfileCar.getAar1stNoHigh());
			existingLowProfileCar.setAar2ndNoLow(umlerLowProfileCar.getAar2ndNoLow());
			existingLowProfileCar.setAar2ndNoHigh(umlerLowProfileCar.getAar2ndNoHigh());
			existingLowProfileCar.setAar3rdNo(umlerLowProfileCar.getAar3rdNo());
			existingLowProfileCar.setCarInit(umlerLowProfileCar.getCarInit());
			existingLowProfileCar.setCarLowNr(umlerLowProfileCar.getCarLowNr());
			existingLowProfileCar.setCarHighNr(umlerLowProfileCar.getCarHighNr());
			existingLowProfileCar.setCarOwner(umlerLowProfileCar.getCarOwner());
			existingLowProfileCar.setTofcCofcInd(umlerLowProfileCar.getTofcCofcInd());
			existingLowProfileCar.setMinEqWidth(umlerLowProfileCar.getMinEqWidth());
			existingLowProfileCar.setMaxEqWidth(umlerLowProfileCar.getMaxEqWidth());
			existingLowProfileCar.setNumberOfPlatform(umlerLowProfileCar.getNumberOfPlatform());
			existingLowProfileCar.setPlatCarInd(umlerLowProfileCar.getPlatCarInd());
			existingLowProfileCar.setTWellInd(umlerLowProfileCar.getTWellInd());
			existingLowProfileCar.setQWellInd(umlerLowProfileCar.getQWellInd());
			existingLowProfileCar.setL3MinEqLength(umlerLowProfileCar.getL3MinEqLength());
			existingLowProfileCar.setL3MaxEqLength(umlerLowProfileCar.getL3MaxEqLength());
			existingLowProfileCar.setL3CenterMinLength(umlerLowProfileCar.getL3CenterMinLength());
			existingLowProfileCar.setL4MinEqLength(umlerLowProfileCar.getL4MinEqLength());
			existingLowProfileCar.setL4MaxEqLength(umlerLowProfileCar.getL4MaxEqLength());
			existingLowProfileCar.setL42UnitsTotLgth(umlerLowProfileCar.getL42UnitsTotLgth());
			existingLowProfileCar.setAccept2c20Ind(umlerLowProfileCar.getAccept2c20Ind());
			existingLowProfileCar.setAccept3t28Ind(umlerLowProfileCar.getAccept3t28Ind());
			existingLowProfileCar.setMinEqLength(umlerLowProfileCar.getMinEqLength());
			existingLowProfileCar.setMaxEqLength(umlerLowProfileCar.getMaxEqLength());
			existingLowProfileCar.setMinTrailerLength(umlerLowProfileCar.getMinTrailerLength());
			existingLowProfileCar.setMaxTrailerLength(umlerLowProfileCar.getMaxTrailerLength());
			existingLowProfileCar.setCntPairsWellInd(umlerLowProfileCar.getCntPairsWellInd());
			existingLowProfileCar.setCntPairsMinLength(umlerLowProfileCar.getCntPairsMinLength());
			existingLowProfileCar.setCntPairsMaxLength(umlerLowProfileCar.getCntPairsMaxLength());
			existingLowProfileCar.setAcceptTrailerPairsInd(umlerLowProfileCar.getAcceptTrailerPairsInd());
			existingLowProfileCar.setTrailerPairsMinLength(umlerLowProfileCar.getTrailerPairsMinLength());
			existingLowProfileCar.setTrailerPairsMaxLength(umlerLowProfileCar.getTrailerPairsMaxLength());
			existingLowProfileCar.setAcceptNoseMountedReefer(umlerLowProfileCar.getAcceptNoseMountedReefer());
			existingLowProfileCar.setReeferWellInd(umlerLowProfileCar.getReeferWellInd());
			existingLowProfileCar.setNoReeferT40Ind(umlerLowProfileCar.getNoReeferT40Ind());
			existingLowProfileCar.setMaxLoadWeight(umlerLowProfileCar.getMaxLoadWeight());
			existingLowProfileCar.setC20MaxWeight(umlerLowProfileCar.getC20MaxWeight());
			if(StringUtils.isNotEmpty(existingLowProfileCar.getUversion())) {
				existingLowProfileCar.setUversion(
						Character.toString((char) ((((int)existingLowProfileCar.getUversion().charAt(0) - 32) % 94) + 33)));
			}

			for(LowProfileEquipmentWidth lowProfileEquipmentWidth : umlerLowProfileCar.getLowProfileEquipmentWidth()){
				if(lowProfileEquipmentWidth.getUmlerId() != null && lowProfileEquipmentWidth.getAar1stNr() != null){
					if(lowProfileEqWidthRepository.existsByUmlerIdAndAar1stNr(lowProfileEquipmentWidth.getUmlerId(), lowProfileEquipmentWidth.getAar1stNr())) {
						if(lowProfileEquipmentWidth.getMinEqWidth() != null && lowProfileEquipmentWidth.getMaxEqWidth() != null) {
							if (lowProfileEquipmentWidth.getMinEqWidth().compareTo(lowProfileEquipmentWidth.getMaxEqWidth()) > 0) {
								throw new RecordNotAddedException("Min EQ Width: " + lowProfileEquipmentWidth.getMinEqWidth()
										+ " should be less than or equal to Max EQ Width: "
										+ lowProfileEquipmentWidth.getMaxEqWidth());
							}
						}
						LowProfileEquipmentWidth existingLowProfileEquipmentWidth = lowProfileEqWidthRepository.findByUmlerIdAndAar1stNr(lowProfileEquipmentWidth.getUmlerId(), lowProfileEquipmentWidth.getAar1stNr());
						existingLowProfileEquipmentWidth.setMinEqWidth(lowProfileEquipmentWidth.getMinEqWidth());
						existingLowProfileEquipmentWidth.setMaxEqWidth(lowProfileEquipmentWidth.getMaxEqWidth());
						System.out.println("MinEqWidth: "+existingLowProfileEquipmentWidth.getMinEqWidth());
						System.out.println("MaxEqWidth: "+existingLowProfileEquipmentWidth.getMaxEqWidth());
						existingLowProfileEquipmentWidth.setUpdateUserId(headers.get(CommonConstants.USER_ID));
						existingLowProfileEquipmentWidth.setUpdateExtensionSchema("LOWPROFILEEQWIDTH");
						existingLowProfileEquipmentWidth.setUpdateDateTime(new Timestamp(System.currentTimeMillis()));
						lowProfileEquipmentWidth.setUpdateUserId(headers.get(CommonConstants.USER_ID).toUpperCase());
						lowProfileEquipmentWidth.setUpdateExtensionSchema(headers.get(CommonConstants.EXTENSION_SCHEMA).toUpperCase());
						lowProfileEquipmentWidth.setUpdateDateTime(new Timestamp(System.currentTimeMillis()));
						if(StringUtils.isNotEmpty(existingLowProfileEquipmentWidth.getUversion())) {
							existingLowProfileEquipmentWidth.setUversion(
									Character.toString((char) ((((int)existingLowProfileEquipmentWidth.getUversion().charAt(0) - 32) % 94) + 33)));
						}
						lowProfileEquipmentWidth.setUversion(existingLowProfileEquipmentWidth.getUversion());
						lowProfileEqWidthRepository.save(existingLowProfileEquipmentWidth);
					} else{
						throw new NoRecordsFoundException("Record with Umler Id: "+ lowProfileEquipmentWidth.getUmlerId()+" and AAR 1st NR: "+lowProfileEquipmentWidth.getAar1stNr()+" not found!");
					}
				}
			}
			umlerLowProfileCarRepository.save(existingLowProfileCar);
			return existingLowProfileCar;
		} else{
			throw new NoRecordsFoundException("Record with Umler Id: "+ umlerLowProfileCar.getUmlerId()+" not found!");
		}
	}

	@Override
	public UmlerLowProfileCar deleteProfileLoad(UmlerLowProfileCar umlerLowProfileCar) {
		
		if (umlerLowProfileCarRepository.existsByUmlerIdAndUversion(umlerLowProfileCar.getUmlerId()
				, umlerLowProfileCar .getUversion())) {
			UmlerLowProfileCar existingLowProfileCar = umlerLowProfileCarRepository
							.findById(umlerLowProfileCar.getUmlerId()).get();
					if (lowProfileEqWidthRepository.existsByUmlerId(umlerLowProfileCar.getUmlerId())) {
						lowProfileEqWidthRepository.deleteByUmlerId(umlerLowProfileCar.getUmlerId());
					}
					umlerLowProfileCarRepository.deleteById(umlerLowProfileCar.getUmlerId());
					return existingLowProfileCar;
				} else
					throw new NoRecordsFoundException("No record Found to delete Under this Umler Id: "
							+ umlerLowProfileCar.getUmlerId() + " and U_Version: " + umlerLowProfileCar.getUversion());
			}

}
