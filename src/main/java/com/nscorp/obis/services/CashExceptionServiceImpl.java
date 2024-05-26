package com.nscorp.obis.services;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import com.nscorp.obis.common.CommonConstants;
import com.nscorp.obis.common.UserId;
import com.nscorp.obis.domain.CustomerPerDiemRateSelection;
import com.nscorp.obis.dto.CustomerPerDiemRateSelectionDTO;
import com.nscorp.obis.dto.mapper.CustomerPerDiemRateSelectionMapper;
import com.nscorp.obis.exception.InvalidDataException;
import com.nscorp.obis.repository.CustomerInfoRepository;
import com.nscorp.obis.repository.TerminalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.nscorp.obis.common.SpecificationGenerator;
import com.nscorp.obis.domain.CashException;
import com.nscorp.obis.dto.CashExceptionDTO;
import com.nscorp.obis.dto.mapper.CashExceptionMapper;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.repository.CashExceptionRepository;

import lombok.extern.slf4j.Slf4j;

import javax.validation.Valid;

@Slf4j
@Service
public class CashExceptionServiceImpl implements CashExceptionService {

    @Autowired
    CashExceptionRepository cashExceptionRepository;

    @Autowired
    SpecificationGenerator specificationGenerator;

    @Autowired
    CustomerInfoRepository custInfoRepo;

    @Autowired
    TerminalRepository terminalRepo;

    @Override
    public List<CashExceptionDTO> getCashException(String customerName, String customerPrimSix) {
        log.info("CashExceptionServiceImpl : getCashException : Method Starts");
        Specification<CashException> specification = specificationGenerator.cashExceptionSpecification(customerName,
                customerPrimSix);
        List<CashException> cashExceptions;
        if (customerName == null && customerPrimSix == null) {
            cashExceptions = cashExceptionRepository.findAll();
        } else {
            cashExceptions = cashExceptionRepository.findAll(specification);
        }
        if (cashExceptions.isEmpty()) {
            throw new NoRecordsFoundException("no records found for given combination");
        }
        List<CashExceptionDTO> cashExceptionDTOs = new ArrayList<CashExceptionDTO>();
        cashExceptions.stream().forEach((temp) -> {
            cashExceptionDTOs.add(CashExceptionMapper.INSTANCE.cashExceptionToCashExceptionDTO(temp));
        });
        log.info("CashExceptionServiceImpl : getCashException : Method Ends");
        return cashExceptionDTOs;
    }

    @Override
    public CashExceptionDTO addCashException(
            @Valid CashExceptionDTO cashExceptionDTO, Map<String, String> headers) throws SQLException {
        log.info("addCashException : Method Starts");
        UserId.headerUserID(headers);

        CashException entity = new CashException();
        log.info("Entity :" + entity);
        log.info("DTO :" + cashExceptionDTO);


        if (cashExceptionDTO.getCustomerName() != null && cashExceptionDTO.getCustomerPrimarySix() != null) {
            if (!this.isCustomerExist(cashExceptionDTO.getCustomerName(), cashExceptionDTO.getCustomerPrimarySix())) {
                throw new InvalidDataException("No Customer  Found with this customer name  :" + cashExceptionDTO.getCustomerName() + " And customer primary six : " + cashExceptionDTO.getCustomerPrimarySix());
            }
            entity.setCustomerName(cashExceptionDTO.getCustomerName());
            entity.setCustomerPrimarySix(cashExceptionDTO.getCustomerPrimarySix());
        }

        // if (cashExceptionRepository.existsByCustomerPrimarySix(cashExceptionDTO.getCustomerPrimarySix())) {
        //     throw new InvalidDataException("Record already exist for given customer");

        // }

        if (cashExceptionDTO.getEquipType() != null) {
            if (cashExceptionDTO.getTermId() == null) {
                throw new InvalidDataException("Must enter terminal for equipment specific exemption");
            }
            if (!((cashExceptionDTO.getEquipType().equalsIgnoreCase("C")) || (cashExceptionDTO.getEquipType().equalsIgnoreCase("T")) || (cashExceptionDTO.getEquipType().equalsIgnoreCase("Z")))) {
                throw new InvalidDataException("Only accepted EQ_TP values for Cash Exception Rate are C= Container, T=Trailer, Z = Chassis");
            }
            entity.setEquipType(cashExceptionDTO.getEquipType());
        }
        if (!(cashExceptionDTO.getTermId() != null || cashExceptionDTO.getEquipId() != null ||
                cashExceptionDTO.getEffectiveDate() != null)) {
            throw new InvalidDataException("Terminal id or Equipment id or Effective Date or combination of"
            		+ "Beneficial Customer Number & Effective Date atleast one of this should be present.");

        }

        if (cashExceptionDTO.getLoadedOrEmpty() != null) {

            if (!((cashExceptionDTO.getLoadedOrEmpty().equalsIgnoreCase("L")) || (cashExceptionDTO.getLoadedOrEmpty().equalsIgnoreCase("E")))) {
                throw new InvalidDataException("Only accepted LoadedOrEmpty values for Cash Exception are L= Load, E=Empty");
            }
            entity.setLoadedOrEmpty(cashExceptionDTO.getLoadedOrEmpty());

        }

        if (cashExceptionDTO.getTermId() != null) {
            if (!terminalRepo.existsByTerminalId(cashExceptionDTO.getTermId())) {
                throw new NoRecordsFoundException("No Terminal Found with this terminal id :" + cashExceptionDTO.getTermId());
            }
            entity.setTermId(cashExceptionDTO.getTermId());
        }

        if (cashExceptionDTO.getBnfCustomerNumber() != null && cashExceptionDTO.getBnfPrimarySix() != null) {
        	if (cashExceptionDTO.getEffectiveDate() == null) {
                throw new InvalidDataException("Must have effective date to add Beneficial Customer.");
            } 
            if (!custInfoRepo.existsByCustomerNumber(cashExceptionDTO.getBnfCustomerNumber())) {
                throw new NoRecordsFoundException("No Beneficial Customer  Found with this Beneficial customer number  :" + cashExceptionDTO.getBnfCustomerNumber());
            }
            entity.setBnfCustomerNumber(cashExceptionDTO.getBnfCustomerNumber());
            entity.setBnfPrimarySix(cashExceptionDTO.getBnfPrimarySix());

        }

        if (cashExceptionDTO.getEndDate() != null) {
            if (cashExceptionDTO.getEffectiveDate() == null) {
                throw new InvalidDataException("Must have effective date before keying end date");
            } else if (cashExceptionDTO.getEffectiveDate().compareTo(cashExceptionDTO.getEndDate()) >= 0) {
                throw new InvalidDataException("Ending date is not greater than effective date");
            }
            entity.setEndDate(cashExceptionDTO.getEndDate());
        }

        entity.setEquipId(cashExceptionDTO.getEquipId());
        entity.setEquipInit(cashExceptionDTO.getEquipInit());
        entity.setEquipNbr(cashExceptionDTO.getEquipNbr());
        entity.setEffectiveDate(cashExceptionDTO.getEffectiveDate());

        entity.setCashExceptionId(cashExceptionRepository.SGK());

        if (headers.get(CommonConstants.EXTENSION_SCHEMA) != null) {
            entity.setUpdateExtensionSchema(headers.get(CommonConstants.EXTENSION_SCHEMA));
        } else {
            entity.setUpdateExtensionSchema(headers.get("IMS01084"));
        }
        entity.setUversion("!");
        String userId = headers.get(CommonConstants.USER_ID);
        entity.setCreateUserId(userId.toUpperCase());
        entity.setUpdateUserId(userId.toUpperCase());
        entity = cashExceptionRepository.save(entity);
        cashExceptionDTO = CashExceptionMapper.INSTANCE
                .cashExceptionToCashExceptionDTO(entity);
        log.info("addCashException : Method Ends");
        return cashExceptionDTO;
    }

    public Boolean isCustomerExist(String custName, String custPrimarySix) {
        List<String> results = custInfoRepo.checkByCustomerNameAndCustomerPrimarySix(custName, custPrimarySix);
        return results.size() > 0;
    }

    @Override
    public CashExceptionDTO updateCashException(
            @Valid CashExceptionDTO cashExceptionDTO, Map<String, String> headers) throws SQLException {
        UserId.headerUserID(headers);

        CashException entity = new CashException();
        CashException cashException;


        Optional<CashException> custOptional = cashExceptionRepository.findById(cashExceptionDTO.getCashExceptionId());
        if (!custOptional.isPresent())
            throw new NoRecordsFoundException("No Record Found For Given Cash Exception Id / Cash Exception Id can't be null or blank or not provided");
        cashException = custOptional.get();

        if(!StringUtils.equals(cashException.getCustomerName(), cashExceptionDTO.getCustomerName()))
            throw new InvalidDataException("Customer Name is not editable");
        if(!StringUtils.equals(cashException.getCustomerPrimarySix(), cashExceptionDTO.getCustomerPrimarySix()))
            throw new InvalidDataException("Customer Primary Six is not editable");

        entity.setCashExceptionId(cashExceptionDTO.getCashExceptionId());
        entity.setCustomerName(cashExceptionDTO.getCustomerName());
        entity.setCustomerPrimarySix(cashExceptionDTO.getCustomerPrimarySix());


        if (cashExceptionDTO.getEquipType() != null) {
            if (cashExceptionDTO.getTermId() == null) {
                throw new InvalidDataException("Must enter terminal for equipment specific exemption");
            }
            if (!((cashExceptionDTO.getEquipType().equalsIgnoreCase("C")) || (cashExceptionDTO.getEquipType().equalsIgnoreCase("T")) || (cashExceptionDTO.getEquipType().equalsIgnoreCase("Z")))) {
                throw new InvalidDataException("Only accepted EQ_TP values for Cash Exception Rate are C= Container, T=Trailer, Z = Chassis");
            }
            entity.setEquipType(cashExceptionDTO.getEquipType());
        }

        if (!(cashExceptionDTO.getTermId() != null || cashExceptionDTO.getEquipId() != null ||
                cashExceptionDTO.getEffectiveDate() != null)) {
            throw new InvalidDataException("Terminal id or Equipment id or Effective Date or combination of"
            		+ "Beneficial Customer Number & Effective Date atleast one of this should be present.");

        }

        if (cashExceptionDTO.getLoadedOrEmpty() != null) {
            if (!((cashExceptionDTO.getLoadedOrEmpty().equalsIgnoreCase("L")) || (cashExceptionDTO.getLoadedOrEmpty().equalsIgnoreCase("E")))) {
                throw new InvalidDataException("Only accepted Loaded Or Empty values for Cash Exception are L= Load, E=Empty");
            }
            entity.setLoadedOrEmpty(cashExceptionDTO.getLoadedOrEmpty());
        }

        if (cashExceptionDTO.getTermId() != null) {
            if (!terminalRepo.existsByTerminalId(cashExceptionDTO.getTermId())) {
                throw new NoRecordsFoundException("No Terminal Found with this terminal id :" + cashExceptionDTO.getTermId());
            }
            entity.setTermId(cashExceptionDTO.getTermId());
        }

        if (cashExceptionDTO.getBnfCustomerNumber() != null && cashExceptionDTO.getBnfPrimarySix() != null) {
        	if (cashExceptionDTO.getEffectiveDate() == null) {
                throw new InvalidDataException("Must have effective date to update Beneficial Customer.");
            } 
            if (!custInfoRepo.existsByCustomerNumber(cashExceptionDTO.getBnfCustomerNumber())) {
                throw new NoRecordsFoundException("No Beneficial Customer Found with this Beneficial customer number  :" + cashExceptionDTO.getBnfCustomerNumber());
            }
            entity.setBnfCustomerNumber(cashExceptionDTO.getBnfCustomerNumber());
            entity.setBnfPrimarySix(cashExceptionDTO.getBnfPrimarySix());
        }

        if (cashExceptionDTO.getEndDate() != null) {
            if (cashExceptionDTO.getEffectiveDate() == null)
                throw new InvalidDataException("Must have effective date before keying end date");
            if (cashExceptionDTO.getEffectiveDate().compareTo(cashExceptionDTO.getEndDate()) >= 0)
                throw new InvalidDataException("Ending date is not greater than effective date");

            entity.setEndDate(cashExceptionDTO.getEndDate());

        }

        entity.setEquipId(cashExceptionDTO.getEquipId());
        entity.setEquipInit(cashExceptionDTO.getEquipInit());
        entity.setEquipNbr(cashExceptionDTO.getEquipNbr());
        entity.setEffectiveDate(cashExceptionDTO.getEffectiveDate());

        if (headers.get(CommonConstants.EXTENSION_SCHEMA) != null) {
            entity.setUpdateExtensionSchema(headers.get(CommonConstants.EXTENSION_SCHEMA));
        } else {
            entity.setUpdateExtensionSchema(headers.get("IMS01084"));
        }
        entity.setUversion("!");
        String userId = headers.get(CommonConstants.USER_ID);
        entity.setCreateUserId(userId.toUpperCase());
        entity.setUpdateUserId(userId.toUpperCase());
        entity = cashExceptionRepository.save(entity);
        cashExceptionDTO = CashExceptionMapper.INSTANCE.cashExceptionToCashExceptionDTO(entity);

        return cashExceptionDTO;
    }

}
