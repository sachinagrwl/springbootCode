package com.nscorp.obis.services;

import com.nscorp.obis.common.CommonConstants;
import com.nscorp.obis.common.UserId;
import com.nscorp.obis.domain.CarEmbargo;
import com.nscorp.obis.domain.Station;
import com.nscorp.obis.exception.InvalidDataException;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordNotAddedException;
import com.nscorp.obis.exception.RecordNotDeletedException;
import com.nscorp.obis.repository.CarEmbargoRepository;
import com.nscorp.obis.repository.StationRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class CarEmbargoServiceImpl implements CarEmbargoService {

    @Autowired
    CarEmbargoRepository carEmbargoRepository;

    @Autowired
    StationRepository stationRepository;

    @Override
    public List<CarEmbargo> getAllCarEmbargo(){

        List<CarEmbargo> carEmbargoList = carEmbargoRepository.findAllByOrderByAarType();

        if(carEmbargoList.isEmpty()){
            throw new NoRecordsFoundException("No CarEmbargo Found!");
        }
        return carEmbargoList;
    }

    private void carEmbargoValidations(CarEmbargo carEmbargoObj, Map<String, String> headers){

        if(carEmbargoObj.getAarType() != null && carEmbargoObj.getEquipmentInit() != null){
            throw new InvalidDataException("Please remove AarType or Equipment Init!");
        }

        if(carEmbargoObj.getAarType() == null && carEmbargoObj.getEquipmentInit() == null){
            throw new InvalidDataException("Please Enter AarType or Equipment Init!");
        }
        
        if(carEmbargoObj.getEquipmentNumberLow() == null && carEmbargoObj.getEquipmentNumberHigh() != null) {
			throw new RecordNotAddedException("'EQUIP LOW NR' value should be provided");
		}
		
		if(carEmbargoObj.getEquipmentNumberLow() != null && carEmbargoObj.getEquipmentNumberHigh() == null) {
			throw new RecordNotAddedException("'EQUIP HIGH NR' value should be provided");
		}

        if((carEmbargoObj.getEquipmentNumberLow() != null || carEmbargoObj.getEquipmentNumberHigh() != null) && carEmbargoObj.getEquipmentInit() == null){
            throw new InvalidDataException("'EQUIP INIT' value should be provided for Nr values");
        }

        if(carEmbargoObj.getEquipmentNumberLow() != null && carEmbargoObj.getEquipmentNumberHigh() != null){
            if(carEmbargoObj.getEquipmentNumberLow().doubleValue() > carEmbargoObj.getEquipmentNumberHigh().doubleValue()){
                throw new RecordNotAddedException("'EQUIP HIGH NR' should be greater than the 'EQUIP LOW NR'");
            }
        }

//        if(carEmbargoObj.getTerminal() != null && !stationRepository.existsByTermId(carEmbargoObj.getTerminal().getTermId())){
//                carEmbargoObj.setTerminal(null);
//        }
        
        if(carEmbargoObj.getTerminal() != null && !stationRepository.existsByTermIdAndExpiredDateIsNull(carEmbargoObj.getTerminal().getTermId())){
        	throw new NoRecordsFoundException("Terminal with Id "+carEmbargoObj.getTerminal().getTermId()+" not found!");
	}

        UserId.headerUserID(headers);
        if (headers.get(CommonConstants.EXTENSION_SCHEMA) == null) {
            throw new NullPointerException("Extension Schema should not be null, empty or blank.");
        }
    }

    @Override
    public CarEmbargo insertCarEmbargo(CarEmbargo carEmbargoObj, Map<String, String> headers) {

       Long generatedEmbargoId = carEmbargoRepository.SGKLong();
       carEmbargoObj.setEmbargoId(generatedEmbargoId);

       UserId.headerUserID(headers);
       
       if(carEmbargoObj.getTerminal() != null) {
			Station terminal = stationRepository.findById(carEmbargoObj.getTerminal().getTermId()).get();
			carEmbargoObj.setTerminal(terminal);
		}
       
       carEmbargoValidations(carEmbargoObj, headers);

       carEmbargoObj.setCreateUserId(headers.get(CommonConstants.USER_ID));
       carEmbargoObj.setUpdateUserId(headers.get(CommonConstants.USER_ID));
       carEmbargoObj.setUpdateExtensionSchema(headers.get(CommonConstants.EXTENSION_SCHEMA));
       carEmbargoObj.setUversion("!");

       CarEmbargo carEmbargo = carEmbargoRepository.save(carEmbargoObj);

       return carEmbargo;
    }

    @Override
    public CarEmbargo updateCarEmbargo(CarEmbargo carEmbargoObj, Map<String, String> headers) {
        UserId.headerUserID(headers);
        if(carEmbargoRepository.existsByEmbargoIdAndUversion(carEmbargoObj.getEmbargoId(), carEmbargoObj.getUversion())) {
        	
            carEmbargoValidations(carEmbargoObj, headers);
            CarEmbargo existingCarEmbargo = carEmbargoRepository.findByEmbargoId(carEmbargoObj.getEmbargoId());
            
            existingCarEmbargo.setTerminal(null);
            
            if(carEmbargoObj.getTerminal() != null) {
    			Station terminal = stationRepository.findById(carEmbargoObj.getTerminal().getTermId()).get();
    			existingCarEmbargo.setTerminal(terminal);
    		}
            
            existingCarEmbargo.setUpdateUserId(headers.get(CommonConstants.USER_ID).toUpperCase());
            existingCarEmbargo.setUpdateExtensionSchema(headers.get(CommonConstants.EXTENSION_SCHEMA).toUpperCase());
            existingCarEmbargo.setAarType(carEmbargoObj.getAarType());
            existingCarEmbargo.setEquipmentInit(carEmbargoObj.getEquipmentInit());
            existingCarEmbargo.setAarType(carEmbargoObj.getAarType());
            existingCarEmbargo.setEquipmentNumberLow(carEmbargoObj.getEquipmentNumberLow());
            existingCarEmbargo.setEquipmentNumberHigh(carEmbargoObj.getEquipmentNumberHigh());
            existingCarEmbargo.setRoadName(carEmbargoObj.getRoadName());
            existingCarEmbargo.setRestriction(carEmbargoObj.getRestriction());
            existingCarEmbargo.setDescription(carEmbargoObj.getDescription());
            /* Audit fields */
            if(StringUtils.isNotEmpty(existingCarEmbargo.getUversion())) {
                existingCarEmbargo.setUversion(
                        Character.toString((char) ((((int)existingCarEmbargo.getUversion().charAt(0) - 32) % 94) + 33)));
            }
            carEmbargoRepository.save(existingCarEmbargo);
            return existingCarEmbargo;
        }
        else
            throw new NoRecordsFoundException("No record Found Under this Embargo Id:" + carEmbargoObj.getEmbargoId()
                    + " and Uversion:" + carEmbargoObj.getUversion());
    }

    @Override
    public CarEmbargo deleteCarEmbargo(CarEmbargo carEmbargo) {
        if (carEmbargoRepository.existsByEmbargoIdAndUversion(carEmbargo.getEmbargoId(), carEmbargo.getUversion())) {
            CarEmbargo existingCarEmbargo = carEmbargoRepository.findByEmbargoId(carEmbargo.getEmbargoId());
            carEmbargoRepository.deleteByEmbargoId(carEmbargo.getEmbargoId());
            return existingCarEmbargo;
        }
        else {
            String rep = "No record Found Under this Embargo Id:" + carEmbargo.getEmbargoId()
                    + " and Uversion:" + carEmbargo.getUversion();
            throw new RecordNotDeletedException(rep);
        }
    }
}
