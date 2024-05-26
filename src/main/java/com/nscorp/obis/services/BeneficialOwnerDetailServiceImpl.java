package com.nscorp.obis.services;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.transaction.Transactional;
import javax.validation.Valid;

import com.nscorp.obis.common.CommonConstants;
import com.nscorp.obis.common.UserId;
import com.nscorp.obis.domain.*;
import com.nscorp.obis.exception.InvalidDataException;
import com.nscorp.obis.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.nscorp.obis.common.SpecificationGenerator;
import com.nscorp.obis.dto.BeneficialOwnerDetailDTO;
import com.nscorp.obis.dto.mapper.BeneficialOwnerDetailMapper;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordAlreadyExistsException;


@Service
@Slf4j
@Transactional
public class BeneficialOwnerDetailServiceImpl implements BeneficialOwnerDetailService {

    @Autowired
    BeneficialOwnerDetailRepository repository;
    @Autowired
    CustomerIndexRepository customerIndexRepository;
    @Autowired
    SpecificationGenerator specificationGenerator;
    @Autowired
    BeneficialOwnerRepository beneficialOwnerRepository;

    @Autowired
    CustomerInfoRepository customerRepo;


    @Autowired
    BeneficialOwnerRepository ownerRepository;

    @Override
    public List<BeneficialOwnerDetail> fetchBeneficialOwnerDetails(@Valid Long bnfCustId, @Valid String bnfOwnerNumber) throws SQLException {

        Specification<BeneficialOwnerDetail> specification = specificationGenerator.beneficialOwnerDetailSpecification(bnfCustId, bnfOwnerNumber);
        List<BeneficialOwnerDetail> BeneficialOwnerDetailList = repository.findAll(specification);
        if (BeneficialOwnerDetailList.isEmpty()) {
            throw new NoRecordsFoundException("No Record found for the given parameters");
        }
        List<BeneficialOwnerDetail> ownerDetailList = new ArrayList<>();
        for (BeneficialOwnerDetail ownerDetails : BeneficialOwnerDetailList) {
            String primarySix = ownerDetails.getBnfOwnerNumber().substring(0,ownerDetails.getBnfOwnerNumber().length()-4);
            List<String> customer = customerRepo.findByCustomerNumber(primarySix).stream().map(String::trim).collect(Collectors.toList());
            ownerDetails.setCustomer(customer);
            ownerDetailList.add(ownerDetails);
        }
        return ownerDetailList;

    }
 
    @Override
    public BeneficialOwnerDetail deleteBeneficialDetails(BeneficialOwnerDetail bnfCustDetails, Map<String, String> headers) {
        log.info("deleteBeneficialDetails: Method Starts");

        if (bnfCustDetails.getBnfCustId() == null) {
            throw new InvalidDataException("Beneficial Customer Id Can't Be Null");
        }

        if (bnfCustDetails.getBnfOwnerNumber() == null) {
            throw new InvalidDataException("Beneficial Owner Number Can't Be Null");
        }

        if (!repository.existsByBnfCustId(bnfCustDetails.getBnfCustId())) {
            throw new NoRecordsFoundException(
                    "No Details Found with this given  Beneficial Customer Id : " + bnfCustDetails.getBnfCustId());
        }

        List<BeneficialOwnerDetail> bnfCustDtls = repository.findByBnfCustId(bnfCustDetails.getBnfCustId());

        if (bnfCustDtls.size() > 1) {
            BeneficialOwnerDetail cust = repository.findByBnfOwnerNumberAndBnfCustId(
                    bnfCustDetails.getBnfOwnerNumber(), bnfCustDetails.getBnfCustId());


            if (cust == null) {
                throw new NoRecordsFoundException("No Record found for this combination");
            }
            BeneficialOwner bnfOwner = ownerRepository.findByBnfCustomerId(bnfCustDetails.getBnfCustId());

            if (bnfOwner == null) {
                throw new NoRecordsFoundException("No Benificial Owner Record found for Beneficial Customer Id : " + bnfCustDetails.getBnfCustId());
            }

            UserId.headerUserID(headers);
            String userId = headers.get(CommonConstants.USER_ID);
            bnfOwner.setUpdateUserId(userId.toUpperCase());
            bnfOwner.setUpdateExtensionSchema("IMS02745");
            bnfOwner.setUpdateDateTime(new Timestamp(new Date().getTime()));
            if (bnfOwner.getUversion() != null) {
                bnfOwner.setUversion(Character.toString((char) ((((int) bnfOwner.getUversion().charAt(0) - 32) % 94) + 33)));
            } else {
                bnfOwner.setUversion("!");
            }
            ownerRepository.save(bnfOwner);
            repository.deleteByBnfOwnerNumberAndBnfCustId(bnfCustDetails.getBnfOwnerNumber(), bnfCustDetails.getBnfCustId());
            log.info("deleteBeneficialDetails: Method Ends");
            return cust;
        } else {
            throw new InvalidDataException("Beneficial Owner Details must have at least one Primary Six");
        }
    }
    
    @Override
    public BeneficialOwnerDetailDTO addBeneficialOwnerDetail(BeneficialOwnerDetailDTO beneficialOwnerDetail, Map<String, String> headers) {
        UserId.headerUserID(headers);
        if(beneficialOwnerDetail.getBnfOwnerNumber().length() != 6) {
            throw new InvalidDataException("Beneficial owner number length must be 6");
        }
        //works

        if(repository.existsBybnfOwnerNumber(beneficialOwnerDetail.getBnfOwnerNumber()+"0000")){
            throw new RecordAlreadyExistsException("Record already exists with given Beneficial Owner " + beneficialOwnerDetail.getBnfOwnerNumber());
        }

        if(beneficialOwnerDetail.getBnfCustId()==null){
            throw new InvalidDataException("Beneficial owner Id cannot be null");
        }
        //works

        if(!customerIndexRepository.existsByCustomerNumberStartsWith(beneficialOwnerDetail.getBnfOwnerNumber())) {
            throw new NoRecordsFoundException("No customer records found with given beneficial owner number");
        }
        if(!beneficialOwnerRepository.existsByBnfCustomerId(beneficialOwnerDetail.getBnfCustId())){
            throw new NoRecordsFoundException("No beneficial owner records found with given beneficial customer Id");
        }
        //checks if it exist on BeneficailOwnerTable


        beneficialOwnerDetail.setBnfOwnerNumber(beneficialOwnerDetail.getBnfOwnerNumber()+"0000");
        beneficialOwnerDetail.setBnfCustId(beneficialOwnerDetail.getBnfCustId());
        if (beneficialOwnerDetail.getUpdateExtensionSchema() == null) {
            beneficialOwnerDetail.setUpdateExtensionSchema("IMS02745");
        }

        beneficialOwnerDetail.setUversion("!");
        beneficialOwnerDetail.setCreateDateTime(beneficialOwnerDetail.getCreateDateTime());
        beneficialOwnerDetail.setUpdateDateTime(beneficialOwnerDetail.getUpdateDateTime());
        beneficialOwnerDetail.setCreateUserId(headers.get(CommonConstants.USER_ID).toUpperCase());
        beneficialOwnerDetail.setUpdateUserId(headers.get(CommonConstants.USER_ID).toUpperCase());
        beneficialOwnerDetail.setUpdateExtensionSchema(headers.get(CommonConstants.EXTENSION_SCHEMA));

        BeneficialOwnerDetail beneficialOwner=repository.save(BeneficialOwnerDetailMapper.INSTANCE.beneficialOwnerDetailDTOToBeneficialOwnerDetail(beneficialOwnerDetail));
        return BeneficialOwnerDetailMapper.INSTANCE.beneficialOwnerDetailToBeneficialOwnerDetailDTO(beneficialOwner);

    }

}
