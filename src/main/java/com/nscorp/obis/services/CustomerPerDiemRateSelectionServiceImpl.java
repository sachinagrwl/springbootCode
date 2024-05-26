package com.nscorp.obis.services;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.validation.Valid;

import com.nscorp.obis.common.CommonConstants;
import com.nscorp.obis.domain.Customer;
import com.nscorp.obis.domain.CustomerInfo;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.repository.CustomerRepository;
import com.nscorp.obis.repository.TerminalRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nscorp.obis.common.UserId;
import com.nscorp.obis.domain.CustomerPerDiemRateSelection;
import com.nscorp.obis.dto.CustomerPerDiemRateSelectionDTO;
import com.nscorp.obis.dto.mapper.CustomerPerDiemRateSelectionMapper;
import com.nscorp.obis.exception.InvalidDataException;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.repository.CustomerInfoRepository;
import com.nscorp.obis.repository.CustomerPerDiemRateSelectionRepository;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CustomerPerDiemRateSelectionServiceImpl implements CustomerPerDiemRateSelectionService {

    @Autowired
    CustomerPerDiemRateSelectionRepository customerPerDiemRateSelectionRepository;

    @Autowired
    CustomerInfoRepository custInfoRepo;

    @Autowired
    CustomerRepository custRepo;

    @Autowired
    TerminalRepository terminalRepo;

    @Override
    public List<CustomerPerDiemRateSelection> fetchCustomerPerDiemRate(@Valid String custPrimSix) throws SQLException {
        List<CustomerPerDiemRateSelection> customerPerDiemRate = new ArrayList<>();
        customerPerDiemRate = customerPerDiemRateSelectionRepository.findByCustPrimSix(custPrimSix);
        if (customerPerDiemRate.isEmpty()) {
            throw new NoRecordsFoundException("No Records found for this Customer Primsix.");
        }
        return customerPerDiemRate;
    }

    @Override
    public CustomerPerDiemRateSelectionDTO updateCustomerPerDiemRate(
            @Valid CustomerPerDiemRateSelectionDTO perDiemRateDTO, Map<String, String> headers) throws SQLException {
        log.info("updateCustomerPerDiemRate : Method Starts");
        UserId.headerUserID(headers);
        Long perDiemRateId = perDiemRateDTO.getPerDiemId();
        if (perDiemRateId == null) {
            throw new InvalidDataException("perDiemRate id can't be null");
        }
        CustomerPerDiemRateSelection entity = customerPerDiemRateSelectionRepository.findById(perDiemRateId)
                .orElseThrow(() -> new NoRecordsFoundException("No record found for given id"));
        log.info("Entity :" + entity);
        log.info("DTO :" + perDiemRateDTO);
        if (!(StringUtils.equals(entity.getCustomerName(), perDiemRateDTO.getCustomerName()))) {
            throw new InvalidDataException("Customer Name can't be modified");
        }
        if (!(StringUtils.equals(entity.getCustPrimSix(), perDiemRateDTO.getCustPrimSix()))) {
            throw new InvalidDataException("Customer PrimSix can't be modified");
        }
        if (!(StringUtils.equals(entity.getBeneficialCustomerName(), perDiemRateDTO.getBeneficialCustomerName()))) {
            throw new InvalidDataException("Benificial Customer Name can't be modified");
        }
        if (!(StringUtils.equals(entity.getBeneficialPrimSix(), perDiemRateDTO.getBeneficialPrimSix()))) {
            throw new InvalidDataException("Benificial Customer PrimSix can't be modified");
        }
        if (!(StringUtils.equals(entity.getShipCustomerName(), perDiemRateDTO.getShipCustomerName()))) {
            throw new InvalidDataException("Ship Customer Name can't be modified");
        }
        if (!(StringUtils.equals(entity.getShipPrimSix(), perDiemRateDTO.getShipPrimSix()))) {
            throw new InvalidDataException("Ship Customer PrimSix can't be modified");
        }
        if (!(StringUtils.equals(entity.getEquipTp(), perDiemRateDTO.getEquipTp()))) {
            throw new InvalidDataException("Equip type can't be modified");
        }
        if (!(StringUtils.equals(entity.getIngateLoadEmptyStatus(), perDiemRateDTO.getIngateLoadEmptyStatus()))) {
            throw new InvalidDataException("Ingate Load Empty Status can't be modified");
        }
        if (!(StringUtils.equals(entity.getOutgateLoadEmptyStatus(), perDiemRateDTO.getOutgateLoadEmptyStatus()))) {
            throw new InvalidDataException("Outgate Load Empty Status can't be modified");
        }
        if (!(Objects.equals(entity.getTerminalId(), perDiemRateDTO.getTerminalId()))) {
            throw new InvalidDataException("Terminal can't be modified");
        }
//        if (entity.getEffectiveDate() != null && (perDiemRateDTO.getEffectiveDate() == null
//                || (entity.getEffectiveDate().compareTo(perDiemRateDTO.getEffectiveDate()) != 0))) {
//            throw new InvalidDataException("effictive date can't be modified.");
//        }
        entity.setEffectiveDate(perDiemRateDTO.getEffectiveDate());
        if (!Objects.equals(entity.getEndDate(), perDiemRateDTO.getEndDate())) {
            if (entity.getEffectiveDate() != null && perDiemRateDTO.getEndDate() != null
                    && (entity.getEffectiveDate().compareTo(perDiemRateDTO.getEndDate()) >= 0)) {
                throw new InvalidDataException("End date should be later than effective date");
            }
            entity.setEndDate(perDiemRateDTO.getEndDate());
        }
        if(entity.getEndDate()!=null && entity.getEffectiveDate()==null) {
        	throw new InvalidDataException("Effective date is mandatory for having end date!");
        }
        if (headers.get("extensionschema") != null) {
            entity.setUpdateExtensionSchema(headers.get("extensionschema"));
        } else {
            entity.setUpdateExtensionSchema(headers.get("IMS01086"));
        }
        if (perDiemRateDTO.getUversion() != null) {
            entity.setUversion(perDiemRateDTO.getUversion());
        }
        if (perDiemRateDTO.getBillCustomerId() != null) {
            if (!custInfoRepo.existsById(perDiemRateDTO.getBillCustomerId())) {
                throw new NoRecordsFoundException(
                        "No Record found for given customer id " + perDiemRateDTO.getBillCustomerId());
            }
        }
        entity.setBillCustomerId(perDiemRateDTO.getBillCustomerId());

        if ((perDiemRateDTO.getFreeDayLimit() != null
                || perDiemRateDTO.getRateDayLimit() != null
                || perDiemRateDTO.getRateType() != null
                || (perDiemRateDTO.getCountWeekend() != null && perDiemRateDTO.getCountWeekend().equalsIgnoreCase("y"))
                || perDiemRateDTO.getInitialRate() != null
                || perDiemRateDTO.getSecondaryRate() != null
        ) && perDiemRateDTO.getEffectiveDate() == null) {
            throw new InvalidDataException("Effective Date field is required");
        }

        if (perDiemRateDTO.getFreeDayLimit() != null
                && perDiemRateDTO.getFreeDayLimit() > 127) {
            throw new InvalidDataException("0120-Error on field FREE_DD_LMT; subfield too large");
        }
        entity.setFreeDayLimit(perDiemRateDTO.getFreeDayLimit());

        if (perDiemRateDTO.getRateDayLimit() != null) {
            if (perDiemRateDTO.getRateDayLimit() > 127) {
                throw new InvalidDataException("0120- Error on field RT1_DD_LMT; subfield too large");
            }
            if (perDiemRateDTO.getFreeDayLimit() != null && perDiemRateDTO.getFreeDayLimit() >= perDiemRateDTO.getRateDayLimit()) {
                throw new InvalidDataException("Higher rate limit occurs before free time ends");
            }
        }
        entity.setRateDayLimit(perDiemRateDTO.getRateDayLimit());
        entity.setUpdateUserId(headers.get("userid"));
        entity.setRateType(perDiemRateDTO.getRateType());
        entity.setCountWeekend(perDiemRateDTO.getCountWeekend());
        entity.setInitialRate(perDiemRateDTO.getInitialRate());
        entity.setSecondaryRate(perDiemRateDTO.getSecondaryRate());
        entity = customerPerDiemRateSelectionRepository.save(entity);
        perDiemRateDTO = CustomerPerDiemRateSelectionMapper.INSTANCE
                .customerPerDiemRateSelectionToCustomerPerDiemRateSelectionDTO(entity);
        log.info("updateCustomerPerDiemRate : Method Ends");
        return perDiemRateDTO;
    }

    @Override
    public CustomerPerDiemRateSelectionDTO addCustomerPerDiemRate(
            @Valid CustomerPerDiemRateSelectionDTO perDiemRateDTO, Map<String, String> headers) throws SQLException {
        log.info("addCustomerPerDiemRate : Method Starts");
        UserId.headerUserID(headers);

        CustomerPerDiemRateSelection entity = new CustomerPerDiemRateSelection();
        log.info("Entity :" + entity);
        log.info("DTO :" + perDiemRateDTO);

        if (customerPerDiemRateSelectionRepository.existsByCustPrimSixAndTerminalIdAndBeneficialPrimSixAndShipPrimSixAndOutgateLoadEmptyStatusAndIngateLoadEmptyStatusAndEquipTp
                (perDiemRateDTO.getCustPrimSix(), perDiemRateDTO.getTerminalId(), perDiemRateDTO.getBeneficialPrimSix()
                        , perDiemRateDTO.getShipPrimSix(), perDiemRateDTO.getOutgateLoadEmptyStatus(), perDiemRateDTO.getIngateLoadEmptyStatus(), perDiemRateDTO.getEquipTp())) {
            throw new InvalidDataException("Association already exist for given combination");
        }

        if (perDiemRateDTO.getCustomerName() != null && perDiemRateDTO.getCustPrimSix() != null) {
            if (!this.isCustomerExist(perDiemRateDTO.getCustomerName(), perDiemRateDTO.getCustPrimSix())) {
                throw new InvalidDataException("No Customer  Found with this customer name  :" + perDiemRateDTO.getCustomerName() + " And customer primary six : " + perDiemRateDTO.getCustPrimSix());
            }
            entity.setCustomerName(perDiemRateDTO.getCustomerName());
            entity.setCustPrimSix(perDiemRateDTO.getCustPrimSix());
        }

        if (!((perDiemRateDTO.getEquipTp().equalsIgnoreCase("C")) || (perDiemRateDTO.getEquipTp().equalsIgnoreCase("T")) || (perDiemRateDTO.getEquipTp().equalsIgnoreCase("Z")))) {
            throw new InvalidDataException("Only accepted EQ_TP values for Per Diem Rate are C= Container, T=Trailer, Z = Chassis");
        }
        entity.setEquipTp(perDiemRateDTO.getEquipTp());


        if (!((perDiemRateDTO.getIngateLoadEmptyStatus().equalsIgnoreCase("L")) || (perDiemRateDTO.getIngateLoadEmptyStatus().equalsIgnoreCase("E")))) {
            throw new InvalidDataException("Only accepted IngateLoadEmptyStatus values for Per Diem Rate are L= Load, E=Empty");
        }
        entity.setIngateLoadEmptyStatus(perDiemRateDTO.getIngateLoadEmptyStatus());

        if (!((perDiemRateDTO.getOutgateLoadEmptyStatus().equalsIgnoreCase("L")) || (perDiemRateDTO.getOutgateLoadEmptyStatus().equalsIgnoreCase("E")))) {
            throw new InvalidDataException("Only accepted OutgateLoadEmptyStatus values for Per Diem Rate are L= Load, E=Empty");
        }
        entity.setOutgateLoadEmptyStatus(perDiemRateDTO.getOutgateLoadEmptyStatus());


        if (perDiemRateDTO.getTerminalId() != null) {
            if (!terminalRepo.existsByTerminalId(perDiemRateDTO.getTerminalId())) {
                throw new NoRecordsFoundException("No Terminal Found with this terminal id :" + perDiemRateDTO.getTerminalId());
            }
            entity.setTerminalId(perDiemRateDTO.getTerminalId());
        }

        if (perDiemRateDTO.getBeneficialCustomerName() != null && perDiemRateDTO.getBeneficialPrimSix() != null) {
            if (!this.isCustomerExist(perDiemRateDTO.getBeneficialCustomerName(), perDiemRateDTO.getBeneficialPrimSix())) {
                throw new NoRecordsFoundException("No Beneficial Customer  Found with this Beneficial customer name  :" + perDiemRateDTO.getBeneficialCustomerName() + " And Beneficial customer primary six : " + perDiemRateDTO.getBeneficialPrimSix());
            }
            entity.setBeneficialCustomerName(perDiemRateDTO.getBeneficialCustomerName());
            entity.setBeneficialPrimSix(perDiemRateDTO.getBeneficialPrimSix());

        }

        if (perDiemRateDTO.getShipCustomerName() != null && perDiemRateDTO.getShipPrimSix() != null) {
            if (!this.isCustomerExist(perDiemRateDTO.getShipCustomerName(), perDiemRateDTO.getShipPrimSix())) {
                throw new NoRecordsFoundException("No Ship Customer  Found with this Ship customer name  :" + perDiemRateDTO.getShipCustomerName() + " And Ship customer primary six : " + perDiemRateDTO.getShipPrimSix());
            }
            entity.setShipCustomerName(perDiemRateDTO.getShipCustomerName());
            entity.setShipPrimSix(perDiemRateDTO.getShipPrimSix());

        }
        if (perDiemRateDTO.getBillCustomerId() != null) {
            if (!custInfoRepo.existsById(perDiemRateDTO.getBillCustomerId())) {
                throw new NoRecordsFoundException(
                        "No Record found for given customer id " + perDiemRateDTO.getBillCustomerId());
            }
            entity.setBillCustomerId(perDiemRateDTO.getBillCustomerId());

        }
        if ((perDiemRateDTO.getFreeDayLimit() != null
                || perDiemRateDTO.getRateDayLimit() != null
                || perDiemRateDTO.getRateType() != null
                || (perDiemRateDTO.getCountWeekend() != null && perDiemRateDTO.getCountWeekend().equalsIgnoreCase("y"))
                || perDiemRateDTO.getInitialRate() != null
                || perDiemRateDTO.getSecondaryRate() != null
        ) && perDiemRateDTO.getEffectiveDate() == null) {
            throw new InvalidDataException("Effective Date field is required");
        }

        if (perDiemRateDTO.getFreeDayLimit() != null
                && perDiemRateDTO.getFreeDayLimit() > 127) {
            throw new InvalidDataException("0120-Error on field FREE_DD_LMT; subfield too large");
        }
        entity.setFreeDayLimit(perDiemRateDTO.getFreeDayLimit());

        if (perDiemRateDTO.getRateDayLimit() != null) {
            if (perDiemRateDTO.getRateDayLimit() > 127) {
                throw new InvalidDataException("0120- Error on field RT1_DD_LMT; subfield too large");
            }
            if (perDiemRateDTO.getFreeDayLimit() != null && perDiemRateDTO.getFreeDayLimit() >= perDiemRateDTO.getRateDayLimit()) {
                throw new InvalidDataException("Higher rate limit occurs before free time ends");
            }
            entity.setRateDayLimit(perDiemRateDTO.getRateDayLimit());

        }


        entity.setEffectiveDate(perDiemRateDTO.getEffectiveDate());
        entity.setRateType(perDiemRateDTO.getRateType());
        entity.setCountWeekend(perDiemRateDTO.getCountWeekend());
        entity.setInitialRate(perDiemRateDTO.getInitialRate());
        entity.setSecondaryRate(perDiemRateDTO.getSecondaryRate());
        entity.setPerDiemId(customerPerDiemRateSelectionRepository.SGK());
        if (headers.get(CommonConstants.EXTENSION_SCHEMA) != null) {
            entity.setUpdateExtensionSchema(headers.get(CommonConstants.EXTENSION_SCHEMA));
        } else {
            entity.setUpdateExtensionSchema(headers.get("IMS01085"));
        }
        entity.setUversion("!");
        String userId = headers.get(CommonConstants.USER_ID);
        entity.setCreateUserId(userId.toUpperCase());
        entity.setUpdateUserId(userId.toUpperCase());
        if ((perDiemRateDTO.getEffectiveDate() != null && perDiemRateDTO.getEndDate() != null)
                && (perDiemRateDTO.getEffectiveDate().compareTo(perDiemRateDTO.getEndDate()) >= 0)) {
            throw new InvalidDataException("End date should be later than effective date");
        }
        entity.setEndDate(perDiemRateDTO.getEndDate());
        if(entity.getEndDate()!=null && entity.getEffectiveDate()==null) {
        	throw new InvalidDataException("Effective date is mandatory for having end date!");
        }
        entity = customerPerDiemRateSelectionRepository.save(entity);
        perDiemRateDTO = CustomerPerDiemRateSelectionMapper.INSTANCE
                .customerPerDiemRateSelectionToCustomerPerDiemRateSelectionDTO(entity);
        log.info("addCustomerPerDiemRate : Method Ends");
        return perDiemRateDTO;
    }

    public Boolean isCustomerExist(String custName, String custPrimarySix) {
        List<String> results = custInfoRepo.checkByCustomerNameAndCustomerPrimarySix(custName, custPrimarySix);
        return results.size() > 0;
    }

}