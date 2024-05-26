package com.nscorp.obis.services;

import static org.junit.Assert.assertEquals;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.sql.SQLException;
import java.sql.Timestamp;

import java.util.*;

import com.nscorp.obis.exception.InvalidDataException;
import com.nscorp.obis.repository.CustomerInfoRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.jpa.domain.Specification;

import com.nscorp.obis.common.SpecificationGenerator;
import com.nscorp.obis.domain.BeneficialOwner;
import com.nscorp.obis.domain.BeneficialOwnerDetail;
import com.nscorp.obis.dto.BeneficialOwnerDTO;
import com.nscorp.obis.dto.BeneficialOwnerDetailDTO;
import com.nscorp.obis.dto.mapper.BeneficialOwnerDetailMapper;
import com.nscorp.obis.dto.mapper.BeneficialOwnerMapper;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.repository.BeneficialOwnerDetailRepository;
import com.nscorp.obis.repository.BeneficialOwnerRepository;
import com.nscorp.obis.repository.CustomerIndexRepository;

class BeneficialOwnerServiceTest {

	@Mock
	BeneficialOwnerRepository repository;

	@Mock
	CustomerInfoRepository customerInfoRepository;

	@Mock
	SpecificationGenerator specificationGenerator;

	@InjectMocks
	BeneficialOwnerServiceImpl beneficialOwnerService;

	@Mock
	CustomerIndexRepository customerIndexRepository;

	@Mock
	BeneficialOwnerDetailRepository beneficialOwnerDetailRepo;

	@Mock
	BeneficialOwnerMapper mapper;

	@Mock
	BeneficialOwnerDetailMapper detailMapper;

	BeneficialOwner beneficialOwner;
	BeneficialOwnerDTO beneficialOwnerDTO;
	List<BeneficialOwner> beneficialOwnerList;
	List<BeneficialOwnerDTO> beneficialOwnerDtoList;
	Specification<BeneficialOwner> specification;
	BeneficialOwnerDetail beneficialOwnerDetail;
	BeneficialOwnerDetailDTO beneficialOwnerDetailDto;
	List<BeneficialOwnerDetail> beneficialOwnerDetails;
	List<BeneficialOwnerDetailDTO> beneficialOwnerDetailDtos;


	String bnfLongName="test";
	String bnfShortName="test";

	Long bnfCustomerId;

	Map<String, String> header;

	Optional<BeneficialOwner> optional;


	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
		beneficialOwner = new BeneficialOwner();
		beneficialOwnerDTO = new BeneficialOwnerDTO();
		beneficialOwnerList = new ArrayList<BeneficialOwner>();
		beneficialOwnerDtoList = new ArrayList<BeneficialOwnerDTO>();
		bnfLongName = "4 WHEEL PARTS";
		bnfShortName = "4 WHEE PAR";
		bnfCustomerId = 123L;
		beneficialOwner.setAccountManager("manager");
		beneficialOwner.setBnfLongName(bnfLongName);
		beneficialOwner.setBnfShortName(bnfShortName);
		beneficialOwner.setCategory("TEST");
		beneficialOwner.setSubCategory("TEST");
		beneficialOwner.setCustomerId(bnfCustomerId);

		beneficialOwnerDTO.setBnfLongName(bnfLongName);
		beneficialOwnerDTO.setBnfShortName(bnfShortName);
		beneficialOwnerDTO.setAccountManager("manager");
		beneficialOwnerDTO.setCategory("TEST");
		beneficialOwnerDTO.setSubCategory("TEST");
		beneficialOwnerDTO.setCustomerId(bnfCustomerId);
		beneficialOwnerDTO.setUpdateDateTime(new Timestamp(01 - 02 - 2008));
		beneficialOwnerDTO.setUpdateUserId("test");

		beneficialOwnerList.add(beneficialOwner);
		specification = specificationGenerator.beneficialOwnerSpecification(bnfLongName, bnfShortName);
		header = new HashMap<>();
		header.put("userid", "test");
		optional = Optional.of(beneficialOwner);
		beneficialOwnerDetailDto = new BeneficialOwnerDetailDTO();
		beneficialOwnerDetailDto.setBnfOwnerNumber("123456");
		beneficialOwnerDetailDtos = Arrays.asList(beneficialOwnerDetailDto);
		beneficialOwnerDetail = new BeneficialOwnerDetail();
		beneficialOwnerDetail.setBnfOwnerNumber("123456");
		beneficialOwnerDetails = Arrays.asList(beneficialOwnerDetail);
		beneficialOwnerDTO.setBeneficialOwnerDetails(beneficialOwnerDetailDtos);
	}

	@AfterEach
	void tearDown() throws Exception {
		beneficialOwner = null;
		beneficialOwnerList = null;
		header = null;

	}

	@Test
	void testFetchBeneficialCustomer() throws SQLException {

		when(repository.findAll(specification)).thenReturn(beneficialOwnerList);
		when(beneficialOwnerService.fetchBeneficialCustomer(bnfLongName, bnfShortName)).thenReturn(beneficialOwnerList);

	}

	@Test
	void testDeleteBeneficialCustomers() throws SQLException {
		beneficialOwner.setBnfCustomerId(bnfCustomerId);
		when(repository.existsByBnfCustomerId(beneficialOwner.getBnfCustomerId())).thenReturn(true);
		beneficialOwnerService.deleteBeneficialCustomers(beneficialOwner);
	}

	@Test
	void testAddBeneficialCustomer() throws SQLException {
		when(repository.existsByBnfLongName(Mockito.anyString())).thenReturn(false);
		when(repository.existsByBnfShortName(Mockito.anyString())).thenReturn(false);
		when(customerIndexRepository.existsById(Mockito.any())).thenReturn(true);
		when(repository.SGK()).thenReturn(1234L);
		when(customerIndexRepository.existsByCustomerNumberStartsWith(Mockito.any())).thenReturn(true);
		when(beneficialOwnerDetailRepo.existsBybnfOwnerNumber(Mockito.anyString())).thenReturn(false);
		when(mapper.beneficialOwnerDTOToBeneficialOwner(Mockito.any())).thenReturn(beneficialOwner);
		when(repository.save(Mockito.any())).thenReturn(beneficialOwner);
		when(beneficialOwnerDetailRepo.saveAll(Mockito.anyList())).thenReturn(beneficialOwnerDetails);
		when(mapper.beneficialOwnerToBeneficialOwnerDTO(Mockito.any())).thenReturn(beneficialOwnerDTO);
		when(detailMapper.beneficialOwnerDetailToBeneficialOwnerDetailDTO(Mockito.any()))
				.thenReturn(beneficialOwnerDetailDto);
		BeneficialOwnerDTO response=beneficialOwnerService.addBeneficialCustomer(beneficialOwnerDTO, header);
		assertNotNull(response);
		when(repository.existsByBnfLongName(Mockito.anyString())).thenReturn(true);
		assertThrows(RecordAlreadyExistsException.class, ()->beneficialOwnerService.addBeneficialCustomer(beneficialOwnerDTO, header));
		when(repository.existsByBnfLongName(Mockito.anyString())).thenReturn(false);
		when(repository.existsByBnfShortName(Mockito.anyString())).thenReturn(true);
		assertThrows(RecordAlreadyExistsException.class, ()->beneficialOwnerService.addBeneficialCustomer(beneficialOwnerDTO, header));
		when(repository.existsByBnfShortName(Mockito.anyString())).thenReturn(false);
		when(customerIndexRepository.existsById(Mockito.any())).thenReturn(false);
		assertThrows(NoRecordsFoundException.class, ()->beneficialOwnerService.addBeneficialCustomer(beneficialOwnerDTO, header));
		when(customerIndexRepository.existsById(Mockito.any())).thenReturn(true);
		when(beneficialOwnerDetailRepo.existsBybnfOwnerNumber(Mockito.anyString())).thenReturn(true);
		assertThrows(RecordAlreadyExistsException.class, ()->beneficialOwnerService.addBeneficialCustomer(beneficialOwnerDTO, header));
		when(beneficialOwnerDetailRepo.existsBybnfOwnerNumber(Mockito.anyString())).thenReturn(false);
		when(customerIndexRepository.existsByCustomerNumberStartsWith(Mockito.any())).thenReturn(false);
		assertThrows(NoRecordsFoundException.class, ()->beneficialOwnerService.addBeneficialCustomer(beneficialOwnerDTO, header));
		when(customerIndexRepository.existsByCustomerNumberStartsWith(Mockito.any())).thenReturn(true);
		beneficialOwnerDTO.getBeneficialOwnerDetails().get(0).setBnfOwnerNumber("123");
		assertThrows(InvalidDataException.class, ()->beneficialOwnerService.addBeneficialCustomer(beneficialOwnerDTO, header));
		beneficialOwnerDTO.getBeneficialOwnerDetails().get(0).setBnfOwnerNumber("123456");
	}

	@Test
	void testNoRecordFoundException() throws SQLException {

		beneficialOwnerList = null;
		NoRecordsFoundException exception1 = assertThrows(NoRecordsFoundException.class,
				() -> when(beneficialOwnerService.fetchBeneficialCustomer(bnfLongName, bnfShortName)));
		assertEquals("No Record found for the given parameters", exception1.getMessage());

	}

	@Test
	void testUpdateBeneficialOwner() {
		when(repository.findById(beneficialOwnerDTO.getBnfCustomerId())).thenReturn(optional);
		beneficialOwner = optional.get();
		beneficialOwner.setBnfLongName("Test");
		beneficialOwner.setBnfShortName("Test");
		beneficialOwner.setUversion("!");

		when(repository.existsByBnfLongName(beneficialOwnerDTO.getBnfLongName())).thenReturn(false);
		when(repository.existsByBnfShortName(beneficialOwnerDTO.getBnfShortName())).thenReturn(false);
		when(customerIndexRepository.existsById(beneficialOwnerDTO.getCustomerId())).thenReturn(true);

		when(repository.save(Mockito.any())).thenReturn(beneficialOwner);
		BeneficialOwnerDTO response = beneficialOwnerService.updateBeneficialOwner(beneficialOwnerDTO, header);
		assertEquals(response.getBnfCustomerId(), beneficialOwnerDTO.getBnfCustomerId());
	}


	@Test
	void testUpdateException() {
		beneficialOwnerDTO.setBnfCustomerId(bnfCustomerId);
		beneficialOwner.setBnfCustomerId(bnfCustomerId);
		NoRecordsFoundException exception1 = assertThrows(NoRecordsFoundException.class,
				() -> when(beneficialOwnerService.updateBeneficialOwner(beneficialOwnerDTO, header)));
		assertEquals("No Record Found For Given Beneficial Customer Id / Beneficial Customer Id can't be null or blank or not provided", exception1.getMessage());

		when(repository.findById(Mockito.anyLong())).thenReturn(optional);
		beneficialOwner = optional.get();

		beneficialOwnerDTO.setBnfLongName(null);
		InvalidDataException exception2 = assertThrows(InvalidDataException.class,
				() -> when(beneficialOwnerService.updateBeneficialOwner(beneficialOwnerDTO, header)));
		assertEquals("W-BNF_LONG_NM Required",exception2.getMessage());

		beneficialOwnerDTO.setBnfLongName("test");
		when(repository.existsByBnfLongName(beneficialOwnerDTO.getBnfLongName())).thenReturn(true);
		InvalidDataException exception3 = assertThrows(InvalidDataException.class,
				() -> when(beneficialOwnerService.updateBeneficialOwner(beneficialOwnerDTO, header)));
		assertEquals("Record already Exist for given  Beneficial Long Name",exception3.getMessage());

		beneficialOwnerDTO.setBnfShortName("4 WHEEL PARTS");
		when(repository.existsByBnfLongName(beneficialOwnerDTO.getBnfLongName())).thenReturn(false);
		beneficialOwnerDTO.setBnfShortName("test");
		when(repository.existsByBnfShortName(beneficialOwnerDTO.getBnfShortName())).thenReturn(true);
		InvalidDataException exception4 = assertThrows(InvalidDataException.class,
				() -> when(beneficialOwnerService.updateBeneficialOwner(beneficialOwnerDTO, header)));
		assertEquals("Record already Exist for given  Beneficial Short Name",exception4.getMessage());

		beneficialOwnerDTO.setBnfShortName("4 WHEE PAR");
		when(repository.existsByBnfShortName(beneficialOwnerDTO.getBnfShortName())).thenReturn(false);

		beneficialOwnerDTO.setCustomerId(null);
		InvalidDataException exception5 = assertThrows(InvalidDataException.class,
				() -> when(beneficialOwnerService.updateBeneficialOwner(beneficialOwnerDTO, header)));
		assertEquals("W-CUST_ID Required",exception5.getMessage());

		beneficialOwnerDTO.setCustomerId(bnfCustomerId);
		when(customerIndexRepository.existsById(beneficialOwnerDTO.getCustomerId())).thenReturn(false);
		NoRecordsFoundException exception6 = assertThrows(NoRecordsFoundException.class,
				() -> when(beneficialOwnerService.updateBeneficialOwner(beneficialOwnerDTO, header)));
		assertEquals("No Customer Found with this id : " + beneficialOwnerDTO.getCustomerId(),exception6.getMessage());

		beneficialOwner.setBnfCustomerId(bnfCustomerId);
		when(repository.existsByBnfCustomerId(bnfCustomerId)).thenReturn(false);
		NoRecordsFoundException deleteException1 = assertThrows(NoRecordsFoundException.class,
				() -> when(beneficialOwnerService.deleteBeneficialCustomers(beneficialOwner)));
		assertEquals("No Details Found with this given Beneficial Customer Id : " + beneficialOwner.getBnfCustomerId(), deleteException1.getMessage());

		beneficialOwner.setBnfCustomerId(null);
		InvalidDataException deleteException = assertThrows(InvalidDataException.class,
				() -> when(beneficialOwnerService.deleteBeneficialCustomers(beneficialOwner)));
		assertEquals("Beneficial Customer Id Can't Be Null",deleteException.getMessage());

	}



}
