package com.nscorp.obis.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.*;

import com.nscorp.obis.exception.InvalidDataException;
import com.nscorp.obis.repository.CustomerIndexRepository;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;

import com.nscorp.obis.common.SpecificationGenerator;
import com.nscorp.obis.domain.BeneficialOwnerDetail;
import com.nscorp.obis.dto.BeneficialOwnerDetailDTO;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.repository.BeneficialOwnerDetailRepository;
import com.nscorp.obis.repository.BeneficialOwnerRepository;
import com.nscorp.obis.domain.BeneficialOwner;

class BeneficialOwnerDetailServiceTest {

	@Mock
	BeneficialOwnerDetailRepository repository;

	@Mock
	BeneficialOwnerRepository ownerRepository;

	@Mock
	CustomerIndexRepository customerIndexRepository;

	@Mock
	SpecificationGenerator specificationGenerator;

	@InjectMocks
	BeneficialOwnerDetailServiceImpl beneficialOwnerDetailService;

	BeneficialOwnerDetail beneficialOwnerDetail;

	BeneficialOwner beneficialOwner;

	BeneficialOwnerDetailDTO beneficialOwnerDetailDTO;
	List<BeneficialOwnerDetail> beneficialOwnerDetailList;
	List<BeneficialOwnerDetailDTO> beneficialOwnerDetailDTOList;
	Specification<BeneficialOwnerDetail> specification;

	Long bnfCustId = 10581789492437L;
	String bnfOwnerNumber = "445949";

	Map<String, String> headers;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
		beneficialOwnerDetail = new BeneficialOwnerDetail();
		beneficialOwnerDetailDTO = new BeneficialOwnerDetailDTO();
		beneficialOwnerDetailList = new ArrayList<BeneficialOwnerDetail>();
		beneficialOwnerDetailDTOList = new ArrayList<BeneficialOwnerDetailDTO>();
		beneficialOwner = new BeneficialOwner();
		beneficialOwner.setUpdateUserId("testApi");
		beneficialOwner.setUpdateExtensionSchema("IMS02745");
		beneficialOwner.setUpdateDateTime(new Timestamp(new Date().getTime()));
		beneficialOwner.setUversion("!");
		specification = specificationGenerator.beneficialOwnerDetailSpecification(bnfCustId, bnfOwnerNumber);
		beneficialOwnerDetailDTO.setBnfCustId(bnfCustId);
		beneficialOwnerDetailDTO.setBnfOwnerNumber(bnfOwnerNumber);
		beneficialOwnerDetail.setBnfCustId(bnfCustId);
		beneficialOwnerDetail.setBnfOwnerNumber(bnfOwnerNumber);
		BeneficialOwnerDetail bnfCust = new BeneficialOwnerDetail();
		bnfCust.setBnfOwnerNumber("1234");
		bnfCust.setBnfCustId(123l);
		beneficialOwnerDetailList.add(bnfCust);
		beneficialOwnerDetailList.add(beneficialOwnerDetail);

		headers = new HashMap<>();
		headers.put("userid", "test");
		headers.put("extensionschema", "test");
	}

	@AfterEach
	void tearDown() throws Exception {
		beneficialOwner = null;
		beneficialOwnerDetail = null;
		beneficialOwnerDetailList = null;
		beneficialOwnerDetailDTO = null;
		beneficialOwnerDetailDTOList = null;
	}

	@Test
	void testFetchBeneficialCustomerDetails() throws SQLException {

		when(repository.findAll(specification)).thenReturn(beneficialOwnerDetailList);
		when(beneficialOwnerDetailService.fetchBeneficialOwnerDetails(bnfCustId, bnfOwnerNumber))
				.thenReturn(beneficialOwnerDetailList);

	}

	@Test
	void testNoRecordFoundException() throws SQLException {

		beneficialOwnerDetailList = null;
		NoRecordsFoundException exception1 = assertThrows(NoRecordsFoundException.class,
				() -> when(beneficialOwnerDetailService.fetchBeneficialOwnerDetails(bnfCustId, bnfOwnerNumber)));
		assertEquals("No Record found for the given parameters", exception1.getMessage());

	}

	@Test
	void testDeleteBeneficialCustDtls() {
		beneficialOwnerDetail.setBnfCustId(bnfCustId);
		beneficialOwnerDetail.setBnfOwnerNumber(bnfOwnerNumber);

		when(repository.existsByBnfCustId(Mockito.any())).thenReturn(true);
		when(repository.findByBnfCustId(Mockito.any())).thenReturn(beneficialOwnerDetailList);

		when(repository.findByBnfOwnerNumberAndBnfCustId(Mockito.any(), Mockito.any()))
				.thenReturn(beneficialOwnerDetail);
		when(ownerRepository.findByBnfCustomerId(Mockito.any())).thenReturn(beneficialOwner);

		beneficialOwnerDetailService.deleteBeneficialDetails(beneficialOwnerDetail, headers);
		Assertions.assertEquals(beneficialOwnerDetailDTO.getBnfCustId(), beneficialOwnerDetail.getBnfCustId());
	}

	@Test
	void testDeleteBenficialCustNoRecordsFoundException() {
		when(repository.existsByBnfCustId(Mockito.any())).thenReturn(false);
		assertThrows(NoRecordsFoundException.class,
				() -> beneficialOwnerDetailService.deleteBeneficialDetails(beneficialOwnerDetail, headers));

		when(repository.existsByBnfCustId(Mockito.any())).thenReturn(true);
		when(repository.findByBnfCustId(Mockito.any())).thenReturn(beneficialOwnerDetailList);
		when(repository.findByBnfOwnerNumberAndBnfCustId(Mockito.any(), Mockito.any())).thenReturn(null);
		assertThrows(NoRecordsFoundException.class,
				() -> beneficialOwnerDetailService.deleteBeneficialDetails(beneficialOwnerDetail, headers));

		when(repository.findByBnfOwnerNumberAndBnfCustId(Mockito.any(), Mockito.any()))
				.thenReturn(beneficialOwnerDetail);
		when(ownerRepository.findByBnfCustomerId(Mockito.any())).thenReturn(null);
		assertThrows(NoRecordsFoundException.class,
				() -> beneficialOwnerDetailService.deleteBeneficialDetails(beneficialOwnerDetail, headers));

	}

	@Test
	void testDeleteBeneficialCustInvalidDataException() {
		beneficialOwnerDetail.setBnfCustId(null);
		assertThrows(InvalidDataException.class,
				() -> beneficialOwnerDetailService.deleteBeneficialDetails(beneficialOwnerDetail, headers));

		beneficialOwnerDetail.setBnfCustId(bnfCustId);
		beneficialOwnerDetail.setBnfOwnerNumber(null);
		assertThrows(InvalidDataException.class,
				() -> beneficialOwnerDetailService.deleteBeneficialDetails(beneficialOwnerDetail, headers));

		beneficialOwnerDetail.setBnfOwnerNumber(bnfOwnerNumber);
		when(repository.existsByBnfCustId(Mockito.any())).thenReturn(true);
		when(repository.findByBnfCustId(Mockito.any())).thenReturn(new ArrayList<>());
		assertThrows(InvalidDataException.class,
				() -> beneficialOwnerDetailService.deleteBeneficialDetails(beneficialOwnerDetail, headers));

	}

	@Test
	void testAddBeneficialOwnersDetail() {
//		beneficialOwnerDetailDTO.setBnfOwnerNumber("123456");
//		beneficialOwnerDetailDTO.setBnfCustId(123L);
		when(repository.existsBybnfOwnerNumber(Mockito.any())).thenReturn(false);
		when(repository.existsByBnfCustId(Mockito.any())).thenReturn(false);

		when(customerIndexRepository.existsByCustomerNumberStartsWith(Mockito.any()))
				.thenReturn(true);

		when(repository.save(Mockito.any())).thenReturn(beneficialOwnerDetail);

		BeneficialOwnerDetailDTO beneficialOwnerDetailAdded = beneficialOwnerDetailService
				.addBeneficialOwnerDetail(beneficialOwnerDetailDTO, headers);

		assertEquals(beneficialOwnerDetailAdded.getBnfOwnerNumber(), beneficialOwnerDetail.getBnfOwnerNumber());
	}

	@Test
	void testAddException() {
		beneficialOwnerDetailDTO.setBnfOwnerNumber("12345");
		beneficialOwnerDetailDTO.setBnfCustId(10581789492438L);
		when(customerIndexRepository.existsById(beneficialOwnerDetailDTO.getBnfCustId())).thenReturn(true);

		InvalidDataException exception = assertThrows(InvalidDataException.class,
				() -> when(beneficialOwnerDetailService.addBeneficialOwnerDetail(beneficialOwnerDetailDTO, headers)));
		assertEquals("Beneficial owner number length must be 6", exception.getMessage());

		beneficialOwnerDetailDTO.setBnfOwnerNumber("123456");
		beneficialOwnerDetailDTO.setBnfCustId(10581789492438L);
		when(customerIndexRepository.existsByCustomerNumberStartsWith(beneficialOwnerDetailDTO.getBnfOwnerNumber())).thenReturn(false);

		NoRecordsFoundException exception1 = assertThrows(NoRecordsFoundException.class,
				() -> when(beneficialOwnerDetailService.addBeneficialOwnerDetail(beneficialOwnerDetailDTO, headers)));
		assertEquals("No customer records found with given beneficial owner number", exception1.getMessage());

	}
	/*
	beneficialOwnerDTO.setCustomerId(bnfCustomerId);
		when(customerIndexRepository.existsById(beneficialOwnerDTO.getCustomerId())).thenReturn(false);
		NoRecordsFoundException exception6 = assertThrows(NoRecordsFoundException.class,
				() -> when(beneficialOwnerService.updateBeneficialOwner(beneficialOwnerDTO, header)));
		assertEquals("No Customer Found with this id : " + beneficialOwnerDTO.getCustomerId(),exception6.getMessage());

	 */

}
