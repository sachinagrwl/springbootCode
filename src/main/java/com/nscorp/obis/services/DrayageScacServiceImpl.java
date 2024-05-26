package com.nscorp.obis.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.nscorp.obis.domain.*;
import com.nscorp.obis.exception.*;
import com.nscorp.obis.repository.CityStateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.apache.commons.lang3.StringUtils;

import com.nscorp.obis.common.SpecificationGenerator;
import com.nscorp.obis.common.UserId;
import com.nscorp.obis.dto.DrayageScacDTO;
import com.nscorp.obis.dto.mapper.DrayageScacMapper;
import com.nscorp.obis.repository.DrayageSCACRepository;
import com.nscorp.obis.response.data.PaginatedResponse;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class DrayageScacServiceImpl implements DrayageScacService {

    @Autowired
    DrayageSCACRepository drayageScacRepo;

    @Autowired
    SpecificationGenerator specificationGenerator;
    @Autowired
    CityStateRepository cityStateRepo;


    @Override
    public PaginatedResponse<DrayageScacDTO> getDrayageScac(String drayId, String carrierName, String carrierCity,
                                                            String state, Integer pageSize, Integer pageNumber, String[] sort) {
        log.info("DrayageScacServiceImpl : getDrayageScac : Method Starts");

        Page<DrayageScac> page = drayageScacRepo.findAll(
                specificationGenerator.drayageScacSpecification(drayId, carrierName, carrierCity, state),
                PageRequest.of(pageNumber, pageSize, Sort.by(SortFilter.sortOrder(sort))));
        if (page.isEmpty()) {
            throw new NoRecordsFoundException("No records found for given combination");
        }
        List<DrayageScacDTO> drayageScacDTOs = new ArrayList<>();
        page.stream().forEach(temp -> {
            drayageScacDTOs.add(DrayageScacMapper.INSTANCE.drayageScacToDrayageDto(temp));
        });
        PaginatedResponse<DrayageScacDTO> paginatedResponse = PaginatedResponse.of(drayageScacDTOs, page);
        log.info("DrayageScacServiceImpl : getDrayageScac : Method Ends");
        return paginatedResponse;
    }

    @Override
    public DrayageScac addDrayageScac(DrayageScac drayageScac, Map<String, String> headers) {

        if (drayageScac.getDrayId() == null)
            throw new NoRecordsFoundException("Dray Id can't be null");
        if (drayageScac.getCarrierName() == null)
            throw new NoRecordsFoundException("Carrier Name can't be null");
        if (drayageScac.getCarrierCity() == null)
            throw new NoRecordsFoundException("Carrier City can't be null");
        if (drayageScac.getState() == null)
            throw new NoRecordsFoundException("State can't be null");

        if (drayageScac.getPhoneNumber() != null) {
            int areaCode = Integer.parseInt(drayageScac.getPhoneNumber().substring(0, 3));
            int exchangeCode = Integer.parseInt(drayageScac.getPhoneNumber().substring(3, 6));
            int customerBase = Integer.parseInt(drayageScac.getPhoneNumber().substring(6));

            if (!(areaCode >= 100)) {
                throw new InvalidDataException("Area code for phone number must be positive and greater than 99");
            }
            if (!(drayageScac.getPhoneNumber().substring(3, 6).length() == 3)) {
                throw new InvalidDataException("Exchange for phone number should have 3 digits!");
            }
            if (!(drayageScac.getPhoneNumber().substring(6).length() == 4)) {
                throw new InvalidDataException("Customer Base should have 4 digits!");

            }

        }

        if (drayageScacRepo.existsById(drayageScac.getDrayId()))
            throw new RecordAlreadyExistsException("This Drayage SCAC already exist. 2006 - Illegal duplicate key");

        if (drayageScac.getState() != null) {
            List<CityState> cityList = cityStateRepo.findAllByStateAbbreviation(drayageScac.getState());
            if (cityList.size() == 0)
                throw new NoRecordsFoundException("No State Record Found For Given State Id");
        }

        UserId.headerUserID(headers);
        drayageScac.setCreateUserId(headers.get("userid"));
        drayageScac.setUpdateUserId(headers.get("userid"));
        drayageScac.setUversion("!");
        if (headers.get("extensionschema") != null)
            drayageScac.setUpdateExtensionSchema(headers.get("extensionschema"));

        drayageScac = drayageScacRepo.save(drayageScac);
        if (drayageScac == null) {
            throw new RecordNotAddedException("Record Not added to Database");
        }

        return drayageScac;
    }

    @Override
    public DrayageScacDTO updateDrayageScac(DrayageScacDTO drayageScacDTO, Map<String, String> headers) {


        DrayageScac drayageScac;
        Optional<DrayageScac> custOptional = drayageScacRepo.findById(drayageScacDTO.getDrayId());
        if (!custOptional.isPresent())
            throw new NoRecordsFoundException("No Record Found For Given Dray Id");

        drayageScac = custOptional.get();

        if (!StringUtils.equals(drayageScacDTO.getDrayId(), drayageScac.getDrayId()))
            throw new InvalidDataException("Dray Id is not editable");

        if (drayageScacDTO.getCarrierName() == null)
            throw new NoRecordsFoundException("Carrier Name can't be null");
        if (drayageScacDTO.getCarrierCity() == null)
            throw new NoRecordsFoundException("Carrier City can't be null");
        if (drayageScacDTO.getState() == null)
            throw new NoRecordsFoundException("State can't be null");

        if (drayageScacDTO.getPhoneNumber() != null) {
            int areaCode = Integer.parseInt(drayageScacDTO.getPhoneNumber().substring(0, 3));
            int exchangeCode = Integer.parseInt(drayageScacDTO.getPhoneNumber().substring(3, 6));
            int customerBase = Integer.parseInt(drayageScacDTO.getPhoneNumber().substring(6));

            if (!(areaCode >= 100)) {
                throw new InvalidDataException("Area code for phone number must be positive and greater than 99");
            }
            if (!(drayageScacDTO.getPhoneNumber().substring(3, 6).length() == 3)) {
                throw new InvalidDataException("Exchange for phone number should have 3 digits!");
            }
            if (!(drayageScacDTO.getPhoneNumber().substring(6).length() == 4)) {
                throw new InvalidDataException("Customer Base should have 4 digits!");

            }
            drayageScac.setPhoneNumber(drayageScacDTO.getPhoneNumber());

        }

        if (drayageScacDTO.getState() != null) {
            List<CityState> cityList = cityStateRepo.findAllByStateAbbreviation(drayageScacDTO.getState());
            if (cityList.size() == 0)
                throw new NoRecordsFoundException("No State Record Found For Given State Id");
        }

        drayageScac.setCarrierName(drayageScacDTO.getCarrierName());
        drayageScac.setCarrierCity(drayageScacDTO.getCarrierCity());
        drayageScac.setCarrierAddress(drayageScacDTO.getCarrierAddress());
        drayageScac.setState(drayageScacDTO.getState());
        drayageScac.setCountry(drayageScacDTO.getCountry());
        drayageScac.setZipCode(drayageScacDTO.getZipCode());
        drayageScac.setMcdIccNr(drayageScacDTO.getMcdIccNr());

        UserId.headerUserID(headers);
        drayageScac.setCreateUserId(headers.get("userid"));
        drayageScac.setUpdateUserId(headers.get("userid"));
        drayageScac.setUversion(Character.toString((char) ((((int) drayageScac.getUversion().charAt(0) - 32) % 94) + 33)));
        if (headers.get("extensionschema") != null)
            drayageScac.setUpdateExtensionSchema(headers.get("extensionschema"));

        drayageScac = drayageScacRepo.save(drayageScac);
        drayageScacDTO = DrayageScacMapper.INSTANCE.drayageScacToDrayageDto(drayageScac);
        return drayageScacDTO;
    }

    @Override
    public DrayageScacDTO deleteDrayageScac(DrayageScacDTO drayageScacDTO) {
        log.info("DrayageScacServiceImpl : deleteDrayageScac : Method Starts");

        DrayageScac drayageScac = drayageScacRepo.getByDrayId(drayageScacDTO.getDrayId());
        if (drayageScac == null) {
            throw new NoRecordsFoundException("no record exist for given drayage id :" + drayageScacDTO.getDrayId());
        }
        drayageScac.setUversion(drayageScacDTO.getUversion());
        drayageScacRepo.delete(drayageScac);
        DrayageScacDTO scacDTO = DrayageScacMapper.INSTANCE.drayageScacToDrayageDto(drayageScac);

        log.info("DrayageScacServiceImpl : deleteDrayageScac : Method Starts");
        return scacDTO;
    }

}
