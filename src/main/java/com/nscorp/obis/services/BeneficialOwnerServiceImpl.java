package com.nscorp.obis.services;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.nscorp.obis.common.CommonConstants;
import com.nscorp.obis.common.UserId;
import com.nscorp.obis.dto.BeneficialOwnerDTO;
import com.nscorp.obis.dto.mapper.BeneficialOwnerMapper;
import com.nscorp.obis.exception.InvalidDataException;
import com.nscorp.obis.repository.CustomerInfoRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nscorp.obis.common.SpecificationGenerator;
import com.nscorp.obis.domain.BeneficialOwner;
import com.nscorp.obis.domain.BeneficialOwnerDetail;
import com.nscorp.obis.dto.mapper.BeneficialOwnerDetailMapper;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.repository.BeneficialOwnerDetailRepository;
import com.nscorp.obis.repository.BeneficialOwnerRepository;
import com.nscorp.obis.repository.CustomerIndexRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class BeneficialOwnerServiceImpl implements BeneficialOwnerService {

	@Autowired
	BeneficialOwnerRepository repository;

	@Autowired
	SpecificationGenerator specificationGenerator;

	@Autowired
	CustomerIndexRepository customerIndexRepository;

	@Autowired
	BeneficialOwnerDetailRepository beneficialOwnerDetailRepo;

	@Autowired
	BeneficialOwnerMapper mapper;

	@Autowired
	BeneficialOwnerDetailMapper detailMapper;

	@Override
	public List<BeneficialOwner> fetchBeneficialCustomer(@Valid String bnfLongName, @Valid String bnfShortName)
			throws SQLException {

		Specification<BeneficialOwner> specification = specificationGenerator.beneficialOwnerSpecification(bnfLongName,
				bnfShortName);
		List<BeneficialOwner> beneficialOwnerList = repository.findAll(specification);
		if (beneficialOwnerList.isEmpty()) {
			throw new NoRecordsFoundException("No Record found for the given parameters");
		}
		return beneficialOwnerList;

	}

    @Override
    public BeneficialOwnerDTO updateBeneficialOwner(@Valid BeneficialOwnerDTO beneficialOwnerDTO, Map<String, String> headers) {
		log.info("updateBeneficialOwner: Method Starts");
        UserId.headerUserID(headers);

        BeneficialOwner beneficialOwner;
        
        if(beneficialOwnerDTO.getBnfCustomerId()==null) {
        	throw new InvalidDataException("Beneficial Customer Id can't be null");
        }
        Optional<BeneficialOwner> bnOwnerOptional = repository.findById(beneficialOwnerDTO.getBnfCustomerId());
        if (!bnOwnerOptional.isPresent())
            throw new NoRecordsFoundException("No Record Found For Given Beneficial Customer Id");
        beneficialOwner = bnOwnerOptional.get();

        if (beneficialOwnerDTO.getBnfLongName() == null) {
            throw new InvalidDataException("W-BNF_LONG_NM Required");
        } else if (!StringUtils.equals(beneficialOwner.getBnfLongName(), beneficialOwnerDTO.getBnfLongName())
                && repository.existsByBnfLongName(beneficialOwnerDTO.getBnfLongName())) {
            throw new InvalidDataException("Record already Exist for given  Beneficial Long Name");
        }

        if (!StringUtils.equals(beneficialOwner.getBnfShortName(), beneficialOwnerDTO.getBnfShortName()) && beneficialOwnerDTO.getBnfShortName()!=null
                && repository.existsByBnfShortName(beneficialOwnerDTO.getBnfShortName())) {
            throw new InvalidDataException("Record already Exist for given  Beneficial Short Name");
        }

        if (beneficialOwnerDTO.getCustomerId() == null) {
            throw new InvalidDataException("W-CUST_ID Required");
        } else if (!customerIndexRepository.existsById(beneficialOwnerDTO.getCustomerId())) {
            throw new NoRecordsFoundException("No Customer Found with this id : " + beneficialOwnerDTO.getCustomerId());
        }

        beneficialOwner.setCustomerId(beneficialOwnerDTO.getCustomerId());
        beneficialOwner.setBnfLongName(beneficialOwnerDTO.getBnfLongName());
        beneficialOwner.setBnfShortName(beneficialOwnerDTO.getBnfShortName());
        beneficialOwner.setCategory(beneficialOwnerDTO.getCategory());
        beneficialOwner.setAccountManager(beneficialOwnerDTO.getAccountManager());
        beneficialOwner.setSubCategory(beneficialOwnerDTO.getSubCategory());

        if (headers.get(CommonConstants.EXTENSION_SCHEMA) != null) {
            beneficialOwner.setUpdateExtensionSchema(headers.get(CommonConstants.EXTENSION_SCHEMA));
        } else {
            beneficialOwner.setUpdateExtensionSchema(headers.get("IMS02744"));
        }
        beneficialOwner.setUversion(Character.toString((char) ((((int) beneficialOwner.getUversion().charAt(0) - 32) % 94) + 33)));
        String userId = headers.get(CommonConstants.USER_ID);
        beneficialOwner.setUpdateUserId(userId.toUpperCase());
        beneficialOwner = repository.save(beneficialOwner);
        beneficialOwnerDTO = BeneficialOwnerMapper.INSTANCE.beneficialOwnerToBeneficialOwnerDTO(beneficialOwner);
		log.info("updateBeneficialOwner: Method Ends");
		return beneficialOwnerDTO;
    }

	@Override
	@Transactional
	public BeneficialOwnerDTO addBeneficialCustomer(BeneficialOwnerDTO beneficialOwnerDTO, Map<String, String> headers)
			throws SQLException {
		log.info("addBeneficialCustomer: Method Starts");
		UserId.headerUserID(headers);
		if (repository.existsByBnfLongName(beneficialOwnerDTO.getBnfLongName())) {
			throw new RecordAlreadyExistsException(
					"Record already exists with given long name " + beneficialOwnerDTO.getBnfLongName());
		}
		if (beneficialOwnerDTO.getBnfShortName()!=null && repository.existsByBnfShortName(beneficialOwnerDTO.getBnfShortName())) {
			throw new RecordAlreadyExistsException(
					"Record already exists with given short name " + beneficialOwnerDTO.getBnfShortName());
		}
		if (!customerIndexRepository.existsById(beneficialOwnerDTO.getCustomerId())) {
			throw new NoRecordsFoundException("No Records found with given customer id");
		}
		if (beneficialOwnerDTO.getBeneficialOwnerDetails()==null || beneficialOwnerDTO.getBeneficialOwnerDetails().size()==0) {
			throw new InvalidDataException("beneficial owner must have at least one Primary Six");
		}
		Long benfCustId = repository.SGK();
		beneficialOwnerDTO.setCreateUserId(headers.get(CommonConstants.USER_ID).toUpperCase());
		beneficialOwnerDTO.setUpdateUserId(headers.get(CommonConstants.USER_ID).toUpperCase());
		beneficialOwnerDTO.setUversion("!");
		beneficialOwnerDTO.setUpdateExtensionSchema(headers.get(CommonConstants.EXTENSION_SCHEMA));
		if (beneficialOwnerDTO.getUpdateExtensionSchema() == null) {
			beneficialOwnerDTO.setUpdateExtensionSchema("IMS02744");
		}
		List<BeneficialOwnerDetail> beneficialOwnerDetails = beneficialOwnerDTO.getBeneficialOwnerDetails().stream()
				.map((beneficialOwnerDetailDTO) -> {
					if(beneficialOwnerDetailDTO.getBnfOwnerNumber().length()!=6) {
						throw new InvalidDataException("Beneficial Owner Number length must be equal to 6");
					}
					if(!customerIndexRepository.existsByCustomerNumberStartsWith(beneficialOwnerDetailDTO.getBnfOwnerNumber())) {
						throw new NoRecordsFoundException("No customer records found with given beneficial owner number");
					}
					if (beneficialOwnerDetailRepo
							.existsBybnfOwnerNumber(beneficialOwnerDetailDTO.getBnfOwnerNumber()+"0000")) {
						throw new RecordAlreadyExistsException(
								"record already exist with given beneficial owner number");
					}
					BeneficialOwnerDetail obj = new BeneficialOwnerDetail();
					obj.setBnfOwnerNumber(beneficialOwnerDetailDTO.getBnfOwnerNumber()+"0000");
					obj.setBnfCustId(benfCustId);
					obj.setCreateUserId(headers.get(CommonConstants.USER_ID).toUpperCase());
					obj.setUpdateUserId(headers.get(CommonConstants.USER_ID).toUpperCase());
					obj.setUversion("!");
					obj.setUpdateExtensionSchema(headers.get(CommonConstants.EXTENSION_SCHEMA));
					if (obj.getUpdateExtensionSchema() == null) {
						obj.setUpdateExtensionSchema("IMS02744");
					}
					return obj;
				}).collect(Collectors.toList());
		BeneficialOwner beneficialOwner = mapper.beneficialOwnerDTOToBeneficialOwner(beneficialOwnerDTO);
		beneficialOwner.setBnfCustomerId(benfCustId);
		beneficialOwner = repository.save(beneficialOwner);
		beneficialOwnerDetails = beneficialOwnerDetailRepo.saveAll(beneficialOwnerDetails);
		beneficialOwnerDTO = mapper.beneficialOwnerToBeneficialOwnerDTO(beneficialOwner);
		beneficialOwnerDTO.setBeneficialOwnerDetails(beneficialOwnerDetails.stream()
				.map((entity) -> detailMapper.beneficialOwnerDetailToBeneficialOwnerDetailDTO(entity))
				.collect(Collectors.toList()));
		log.info("addBeneficialCustomer: Method Ends");
		return beneficialOwnerDTO;
	}

	@Transactional
    public BeneficialOwner deleteBeneficialCustomers(BeneficialOwner beneficialOwner) {
        log.info("deleteBeneficialCustomers: Method Starts");

        if (beneficialOwner.getBnfCustomerId() == null) {
            throw new InvalidDataException("Beneficial Customer Id Can't Be Null");
        }

        if (!repository.existsByBnfCustomerId(beneficialOwner.getBnfCustomerId())) {
            throw new NoRecordsFoundException(
                    "No Details Found with this given Beneficial Customer Id : " + beneficialOwner.getBnfCustomerId());
        }
		repository.deleteByBnfCustomerId(beneficialOwner.getBnfCustomerId());
		log.info("deleteBeneficialCustomers: Method Ends");
		return beneficialOwner;
        
    }



}
