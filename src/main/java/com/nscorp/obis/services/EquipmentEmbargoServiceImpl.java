package com.nscorp.obis.services;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.nscorp.obis.common.CommonConstants;
import com.nscorp.obis.common.UserId;
import com.nscorp.obis.domain.EquipmentEmbargo;
import com.nscorp.obis.domain.Station;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordNotAddedException;
import com.nscorp.obis.repository.EquipmentEmbargoRepository;
import com.nscorp.obis.repository.StationRepository;

@Transactional
@Service
public class EquipmentEmbargoServiceImpl implements EquipmentEmbargoService {
	
	@Autowired
	EquipmentEmbargoRepository eqEmbargoRepo;
	
	@Autowired
	StationRepository stationRepo;
	
	private void eqEmbargoValidations(EquipmentEmbargo eqEmbargo) {
		
		if(eqEmbargo.getEquipmentInit() == null && eqEmbargo.getEquipmentLength() == null) {
			throw new RecordNotAddedException("Either 'EQ INIT' or 'EQ LENGTH' should be provided");
		}
		
		if(eqEmbargo.getEquipmentNumberLow() != null && eqEmbargo.getEquipmentNumberHigh() != null && eqEmbargo.getEquipmentInit() == null) {
			throw new RecordNotAddedException("'EQ INIT' value should be provided for Nr values");
		} 
		
		if(eqEmbargo.getEquipmentNumberLow() == null && eqEmbargo.getEquipmentNumberHigh() != null) {
			throw new RecordNotAddedException("'EQ NR LOW' value should be provided");
		}
		
		if(eqEmbargo.getEquipmentNumberLow() != null && eqEmbargo.getEquipmentNumberHigh() == null) {
			throw new RecordNotAddedException("'EQ NR HIGH' value should be provided");
		}
		
		if(eqEmbargo.getEquipmentNumberLow() != null && eqEmbargo.getEquipmentNumberHigh() != null && eqEmbargo.getEquipmentNumberLow().compareTo(eqEmbargo.getEquipmentNumberHigh()) > 0) {
			throw new RecordNotAddedException("'EQUIP HIGH NR' should be greater than the 'EQUIP LOW NR'");
		}
		
		if(eqEmbargo.getEquipmentType() == null && eqEmbargo.getEquipmentLength() != null && !((eqEmbargo.getEquipmentLength().equals(20)) || (eqEmbargo.getEquipmentLength().equals(28)) || (eqEmbargo.getEquipmentLength().equals(40))
                || (eqEmbargo.getEquipmentLength().equals(45)) || (eqEmbargo.getEquipmentLength().equals(48)) || (eqEmbargo.getEquipmentLength().equals(53)))) {
			throw new RecordNotAddedException("'equipmentLength' " + eqEmbargo.getEquipmentLength() + " is an Invalid Value");
        }
		
		if(eqEmbargo.getEquipmentType() != null && eqEmbargo.getEquipmentLength() != null && eqEmbargo.getEquipmentType().equals("C") && !((eqEmbargo.getEquipmentLength().equals(20)) || (eqEmbargo.getEquipmentLength().equals(40))
                || (eqEmbargo.getEquipmentLength().equals(45)) || (eqEmbargo.getEquipmentLength().equals(48)) || (eqEmbargo.getEquipmentLength().equals(53)))) {
        	throw new RecordNotAddedException(CommonConstants.EQUIPMENT_LENGTH + eqEmbargo.getEquipmentLength() + CommonConstants.EQUIPMENT_TYPE + eqEmbargo.getEquipmentType());
        }
        
        if(eqEmbargo.getEquipmentType() != null && eqEmbargo.getEquipmentLength() != null && eqEmbargo.getEquipmentType().equals("T") && !((eqEmbargo.getEquipmentLength().equals(28)) || (eqEmbargo.getEquipmentLength().equals(40))
                || (eqEmbargo.getEquipmentLength().equals(45)) || (eqEmbargo.getEquipmentLength().equals(48)) || (eqEmbargo.getEquipmentLength().equals(53)))) {
        	throw new RecordNotAddedException(CommonConstants.EQUIPMENT_LENGTH + eqEmbargo.getEquipmentLength() + CommonConstants.EQUIPMENT_TYPE + eqEmbargo.getEquipmentType());
        }
        
        if(eqEmbargo.getEquipmentType() != null && eqEmbargo.getEquipmentLength() != null && eqEmbargo.getEquipmentType().equals("Z") && !((eqEmbargo.getEquipmentLength().equals(20)) || (eqEmbargo.getEquipmentLength().equals(40))
                || (eqEmbargo.getEquipmentLength().equals(45)) || (eqEmbargo.getEquipmentLength().equals(48)) || (eqEmbargo.getEquipmentLength().equals(53)))) {
        	throw new RecordNotAddedException(CommonConstants.EQUIPMENT_LENGTH + eqEmbargo.getEquipmentLength() + CommonConstants.EQUIPMENT_TYPE + eqEmbargo.getEquipmentType());
        }
		
		if(eqEmbargo.getCarNumberLow() != null && eqEmbargo.getCarNumberHigh() != null && eqEmbargo.getCarInit() ==  null) {
			throw new RecordNotAddedException("'CAR INIT' value should be provided for Nr values");
		}
		
		if(eqEmbargo.getCarNumberLow() == null && eqEmbargo.getCarNumberHigh() != null) {
			throw new RecordNotAddedException("'CAR LOW NR' value should be provided");
		}
		
		if(eqEmbargo.getCarNumberLow() != null && eqEmbargo.getCarNumberHigh() == null) {
			throw new RecordNotAddedException("'CAR HIGH NR' value should be provided");
		}
		
		if(eqEmbargo.getCarNumberLow() != null && eqEmbargo.getCarNumberHigh() != null && eqEmbargo.getCarNumberLow().compareTo(eqEmbargo.getCarNumberHigh()) > 0) {
			throw new RecordNotAddedException("'CAR HIGH NR' should be greater than the 'CAR LOW NR'");
		}
	
		if((StringUtils.isNotBlank(eqEmbargo.getCarAarType()) || StringUtils.isNotBlank(eqEmbargo.getCarInit())) && (eqEmbargo.getRestriction().equalsIgnoreCase("Y") || eqEmbargo.getRestriction().equalsIgnoreCase("N")) && CollectionUtils.isEmpty(eqEmbargo.getRestrictedWells())) {
			throw new RecordNotAddedException("'RESTRICTED WELLS' should be provided");
		}
			
		if(eqEmbargo.getRestriction().equalsIgnoreCase("E") && (StringUtils.isBlank(eqEmbargo.getCarAarType()) && StringUtils.isBlank(eqEmbargo.getCarInit()))) {
			throw new RecordNotAddedException("Either 'CAR INIT' or 'CAR AAR TYPE' should be provided");
		}
		
		if(eqEmbargo.getRestriction().equalsIgnoreCase("E") && StringUtils.isBlank(eqEmbargo.getTofcCofcIndicator())) {
			throw new RecordNotAddedException("'TOFC/COFC Ind' should be provided");
		}
		
		//Check Online destination and offline destinations are valid or not; STATION_XRF where expired date is null
		if(eqEmbargo.getDestinationTerminal() != null && !stationRepo.existsByTermIdAndExpiredDateIsNull(eqEmbargo.getDestinationTerminal().getTermId())) {
			throw new NoRecordsFoundException("Destination Terminal with Id "+eqEmbargo.getDestinationTerminal().getTermId()+" not found!");
		}

		if(eqEmbargo.getOriginTerminal() != null && !stationRepo.existsByTermIdAndExpiredDateIsNull(eqEmbargo.getOriginTerminal().getTermId())) {
			throw new NoRecordsFoundException("Origin Terminal with Id "+eqEmbargo.getOriginTerminal().getTermId()+" not found!");
		}
		
	}

	@Override
	public EquipmentEmbargo addEquipEmbargo(EquipmentEmbargo eqEmbargo, Map<String, String> headers) {
		
		Long generatedEmbargoId = eqEmbargoRepo.SGKLong();
		eqEmbargo.setEmbargoId(generatedEmbargoId);
		UserId.headerUserID(headers);
		String userId = headers.get(CommonConstants.USER_ID);
		String extensionSchema = headers.get(CommonConstants.EXTENSION_SCHEMA);
		
		if(eqEmbargo.getDestinationTerminal() != null) {
			Station destinationTerminal = stationRepo.findById(eqEmbargo.getDestinationTerminal().getTermId()).get();
			eqEmbargo.setDestinationTerminal(destinationTerminal);
		}

		if(eqEmbargo.getOriginTerminal() != null) {
			Station originTerminal = stationRepo.findById(eqEmbargo.getOriginTerminal().getTermId()).get();
			eqEmbargo.setOriginTerminal(originTerminal);
		}
		
		if(extensionSchema != null) {
			extensionSchema = extensionSchema.toUpperCase();
		} else {
			throw new NullPointerException("Extension Schema should not be null, empty or blank");
		}
		
		eqEmbargoValidations(eqEmbargo);
		
		eqEmbargo.setCreateUserId(userId.toUpperCase());
		eqEmbargo.setUpdateUserId(userId.toUpperCase());
		eqEmbargo.setUpdateExtensionSchema(extensionSchema);
		eqEmbargo.setUversion("!");
		 
		eqEmbargoRepo.save(eqEmbargo);
		
		EquipmentEmbargo addEmbargo =  eqEmbargoRepo.findById(eqEmbargo.getEmbargoId()).get();
		return addEmbargo;
       
	}

	@Override
	public List<EquipmentEmbargo> getAllEmbargo() {
		
		List<EquipmentEmbargo> eqEmbargoList = eqEmbargoRepo.findAllByOrderByUpdateDateTimeDesc();
		
		if(eqEmbargoList.isEmpty()) {
			throw new NoRecordsFoundException("No Record Found under this search!");
		}
		return eqEmbargoList;
	}
	
	@Override
	public EquipmentEmbargo updateEquipmentEmbargo(EquipmentEmbargo equipmentEmbargo, Map<String, String> headers) {
		UserId.headerUserID(headers);

		 if(eqEmbargoRepo.existsByEmbargoIdAndUversion(equipmentEmbargo.getEmbargoId(),equipmentEmbargo.getUversion())) {
			 EquipmentEmbargo existingEquipmentEmbargo =eqEmbargoRepo.findById(equipmentEmbargo.getEmbargoId()).get();
	        	eqEmbargoValidations(equipmentEmbargo);
	    		return updateExistingEquipmentEmbargo(existingEquipmentEmbargo,equipmentEmbargo,headers);
	    }else 
	    	throw new NoRecordsFoundException("No record Found to Update Under this Embargo Id: " 
					+ equipmentEmbargo.getEmbargoId() + " and U_Version: " + equipmentEmbargo.getUversion());
	}

	private EquipmentEmbargo updateExistingEquipmentEmbargo(EquipmentEmbargo existingEquipmentEmbargo,EquipmentEmbargo equipmentEmbargo,Map<String, String> headers) {
		existingEquipmentEmbargo.setUpdateUserId(headers.get(CommonConstants.USER_ID));
		String extensionSchema = headers.get(CommonConstants.EXTENSION_SCHEMA);
		
		existingEquipmentEmbargo.setDestinationTerminal(null);
		existingEquipmentEmbargo.setOriginTerminal(null);
		
		if(equipmentEmbargo.getDestinationTerminal() != null) {
			Station destinationTerminal = stationRepo.findById(equipmentEmbargo.getDestinationTerminal().getTermId()).get();
			existingEquipmentEmbargo.setDestinationTerminal(destinationTerminal);
		}

		if(equipmentEmbargo.getOriginTerminal() != null) {
			Station originTerminal = stationRepo.findById(equipmentEmbargo.getOriginTerminal().getTermId()).get();
			existingEquipmentEmbargo.setOriginTerminal(originTerminal);
		}
		
			if(StringUtils.isNotBlank(extensionSchema)) {
				existingEquipmentEmbargo.setUpdateExtensionSchema(extensionSchema.toUpperCase());
			} else {
				throw new NullPointerException("Extension Schema should not be null, empty or blank");
			}
		existingEquipmentEmbargo.setEquipmentInit(equipmentEmbargo.getEquipmentInit());
		existingEquipmentEmbargo.setEquipmentNumberLow(equipmentEmbargo.getEquipmentNumberLow());
		existingEquipmentEmbargo.setEquipmentNumberHigh(equipmentEmbargo.getEquipmentNumberHigh());
		existingEquipmentEmbargo.setEquipmentType(equipmentEmbargo.getEquipmentType());
		existingEquipmentEmbargo.setEquipmentLength(equipmentEmbargo.getEquipmentLength());
		existingEquipmentEmbargo.setTofcCofcIndicator(equipmentEmbargo.getTofcCofcIndicator());
		existingEquipmentEmbargo.setRoadName(equipmentEmbargo.getRoadName());
		existingEquipmentEmbargo.setRestriction(equipmentEmbargo.getRestriction());
		existingEquipmentEmbargo.setDescription(equipmentEmbargo.getDescription());
		existingEquipmentEmbargo.setCarInit(equipmentEmbargo.getCarInit());
		existingEquipmentEmbargo.setCarNumberLow(equipmentEmbargo.getCarNumberLow());
		existingEquipmentEmbargo.setCarNumberHigh(equipmentEmbargo.getCarNumberHigh());
		existingEquipmentEmbargo.setCarAarType(equipmentEmbargo.getCarAarType());
		existingEquipmentEmbargo.setRestrictedWells(equipmentEmbargo.getRestrictedWells());
		
		  if(StringUtils.isNotEmpty(existingEquipmentEmbargo.getUversion())) {
			  existingEquipmentEmbargo.setUversion(
					  Character.toString((char) ((((int)existingEquipmentEmbargo.getUversion().charAt(0) - 32) % 94) + 33)));  
		  }
		  eqEmbargoRepo.save(existingEquipmentEmbargo);
		  return existingEquipmentEmbargo;
	}

	@Override
	public EquipmentEmbargo deleteEquipmentEmbargo(EquipmentEmbargo equipmentEmbargo) {
			if(eqEmbargoRepo.existsByEmbargoIdAndUversion(equipmentEmbargo.getEmbargoId(),equipmentEmbargo.getUversion())) {
				EquipmentEmbargo existingEqEmbargo = eqEmbargoRepo.findById(equipmentEmbargo.getEmbargoId()).get();
				eqEmbargoRepo.deleteById(equipmentEmbargo.getEmbargoId());
				return existingEqEmbargo;
			}else
				throw new NoRecordsFoundException("No record Found to delete Under this Embargo Id: " 
						+ equipmentEmbargo.getEmbargoId() + " and U_Version: " + equipmentEmbargo.getUversion());
	}
}

