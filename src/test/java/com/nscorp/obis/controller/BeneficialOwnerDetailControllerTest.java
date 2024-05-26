package com.nscorp.obis.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.sql.SQLException;
import java.util.*;

import com.nscorp.obis.exception.InvalidDataException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import com.nscorp.obis.domain.BeneficialOwnerDetail;
import com.nscorp.obis.dto.BeneficialOwnerDetailDTO;
import com.nscorp.obis.dto.mapper.BeneficialOwnerDetailMapper;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.repository.BeneficialOwnerDetailRepository;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.services.BeneficialOwnerDetailService;

class BeneficialOwnerDetailControllerTest {

	@Mock
	BeneficialOwnerDetailRepository repository;

	@Mock
	BeneficialOwnerDetailMapper beneficialOwnerDetailMapper;

	@Mock
	BeneficialOwnerDetailService beneficialOwnerDetailService;

	@InjectMocks
	BeneficialOwnerDetailController beneficialOwnerDetailController;

	BeneficialOwnerDetail beneficialOwnerDetail;
	BeneficialOwnerDetailDTO beneficialOwnerDetailDTO;
	List<BeneficialOwnerDetail> beneficialOwnerDetailList;
	List<BeneficialOwnerDetailDTO> beneficialOwnerDetailDTOList;

	Long bnfCustId = 10581789492437L;
	String bnfOwnerNumber = "445949";

	Map<String, String> header;


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
		beneficialOwnerDetailList = new ArrayList<>();
		beneficialOwnerDetailDTOList = new ArrayList<>();

		beneficialOwnerDetail.setBnfCustId(bnfCustId);
		beneficialOwnerDetail.setBnfOwnerNumber(bnfOwnerNumber);
		beneficialOwnerDetailDTO.setBnfCustId(bnfCustId);
		beneficialOwnerDetailDTO.setBnfOwnerNumber(bnfOwnerNumber);
		BeneficialOwnerDetailDTO bnfOwnerDetailDTO = new BeneficialOwnerDetailDTO();
		bnfOwnerDetailDTO.setBnfCustId(bnfCustId);
		bnfOwnerDetailDTO.setBnfOwnerNumber(bnfOwnerNumber);
		beneficialOwnerDetailDTOList.add(beneficialOwnerDetailDTO);
		beneficialOwnerDetailDTOList.add(bnfOwnerDetailDTO);
		beneficialOwnerDetailList.add(beneficialOwnerDetail);

		header = new HashMap<>();
		header.put("userid", "test");
		header.put("extensionschema", "test");
	}

	@AfterEach
	void tearDown() throws Exception {
		beneficialOwnerDetail = null;
		beneficialOwnerDetailDTO = null;
		beneficialOwnerDetailDTOList = null;
		beneficialOwnerDetailList = null;
	}

	@Test
	void testGetBeneficialCustomerDetails() throws SQLException {

		when(beneficialOwnerDetailService.fetchBeneficialOwnerDetails(Mockito.any(), Mockito.any())).thenReturn(beneficialOwnerDetailList);
		when(beneficialOwnerDetailMapper.beneficialOwnerDetailDTOToBeneficialOwnerDetail(Mockito.any())).thenReturn(beneficialOwnerDetail);
		when(beneficialOwnerDetailMapper.beneficialOwnerDetailToBeneficialOwnerDetailDTO(Mockito.any())).thenReturn(beneficialOwnerDetailDTO);

		ResponseEntity<APIResponse<List<BeneficialOwnerDetailDTO>>> getData = beneficialOwnerDetailController.
			getBeneficialOwnerDetails(bnfCustId, bnfOwnerNumber);
		assertEquals(getData.getStatusCodeValue(), 200);

	}

	@Test
	void testNoRecordsFoundException() throws SQLException {
		when(beneficialOwnerDetailService.fetchBeneficialOwnerDetails(Mockito.any(), Mockito.any()))
				.thenThrow(new NoRecordsFoundException());
		ResponseEntity<APIResponse<List<BeneficialOwnerDetailDTO>>> getData = beneficialOwnerDetailController.
				getBeneficialOwnerDetails(bnfCustId, bnfOwnerNumber);
		assertEquals(getData.getStatusCodeValue(), 404);
		
	}

	@Test
	void testException() throws SQLException {
		when(beneficialOwnerDetailService.fetchBeneficialOwnerDetails(Mockito.any(), Mockito.any()))
				.thenThrow(new RuntimeException());
		ResponseEntity<APIResponse<List<BeneficialOwnerDetailDTO>>> getData = beneficialOwnerDetailController.
				getBeneficialOwnerDetails(bnfCustId, bnfOwnerNumber);
		assertEquals(getData.getStatusCodeValue(), 500);
		
	}

	@Test
	void testDeleteBnfCustDtls() {
		when(beneficialOwnerDetailMapper.beneficialOwnerDetailDTOToBeneficialOwnerDetail(Mockito.any())).thenReturn(beneficialOwnerDetail);
		when(beneficialOwnerDetailService.deleteBeneficialDetails(Mockito.any(),Mockito.any())).thenReturn(beneficialOwnerDetail);
		when(beneficialOwnerDetailMapper.beneficialOwnerDetailToBeneficialOwnerDetailDTO(Mockito.any())).thenReturn(beneficialOwnerDetailDTO);
		ResponseEntity<List<APIResponse<BeneficialOwnerDetailDTO>>> responseEntity = beneficialOwnerDetailController.deleteBnfCustDtl(beneficialOwnerDetailDTOList,header);
		assertEquals(responseEntity.getStatusCodeValue(),200);
	}

	@Test
	void testDeleteBeneficialCustomerDetailsDTOList(){
		ResponseEntity<List<APIResponse<BeneficialOwnerDetailDTO>>> responseEntity = beneficialOwnerDetailController.deleteBnfCustDtl(Collections.EMPTY_LIST,header);
		assertEquals(responseEntity.getStatusCodeValue(),500);
	}

	@Test
	void testDeleteBeneficialCustomerDetails(){
		when(beneficialOwnerDetailService.deleteBeneficialDetails(Mockito.any(),Mockito.any())).thenThrow(new RuntimeException());
		ResponseEntity<List<APIResponse<BeneficialOwnerDetailDTO>>> responseEntity = beneficialOwnerDetailController.deleteBnfCustDtl(beneficialOwnerDetailDTOList,header);
		assertEquals(responseEntity.getStatusCodeValue(),500);
	}

	@Test
	void testDeleteBeneficialCustomerDetailsNoRecordFoundException(){
		when(beneficialOwnerDetailService.deleteBeneficialDetails(Mockito.any(),Mockito.any())).thenThrow(new NoRecordsFoundException());
		ResponseEntity<List<APIResponse<BeneficialOwnerDetailDTO>>> responseEntity = beneficialOwnerDetailController.deleteBnfCustDtl(beneficialOwnerDetailDTOList,header);
		assertEquals(responseEntity.getStatusCodeValue(),500);
	}

	@Test
	void testDeleteCommodityInvalidDataException(){
		when(beneficialOwnerDetailService.deleteBeneficialDetails(Mockito.any(),Mockito.any())).thenThrow(new InvalidDataException());
		ResponseEntity<List<APIResponse<BeneficialOwnerDetailDTO>>> responseEntity = beneficialOwnerDetailController.deleteBnfCustDtl(beneficialOwnerDetailDTOList,header);
		assertEquals(responseEntity.getStatusCodeValue(),500);
	}
	
	@Test
	void TestAddBeneficialOwnerDetail() throws SQLException {
		when(beneficialOwnerDetailService.
				addBeneficialOwnerDetail(Mockito.any(),
						Mockito.anyMap())).thenReturn
				(beneficialOwnerDetailDTO);
		ResponseEntity<APIResponse<BeneficialOwnerDetailDTO>> addData =
				beneficialOwnerDetailController.addBeneficialOwnerDetail(
						beneficialOwnerDetailDTO, header);
		assertEquals(addData.getStatusCodeValue(), 200);
		//this works
	}

	@Test
	void TestAddBeneficialOwnerDetailException() throws SQLException {
		when(beneficialOwnerDetailService.addBeneficialOwnerDetail(Mockito.any(), Mockito.any()))
				.thenThrow(new InvalidDataException());
		ResponseEntity<APIResponse<BeneficialOwnerDetailDTO>> responseEntity = beneficialOwnerDetailController.addBeneficialOwnerDetail(beneficialOwnerDetailDTO, header);
		assertEquals(responseEntity.getStatusCodeValue(), 500);
	}
	@Test
	void TestAddBeneficialOwnerDetailExceptionNoRecordFoundException() throws SQLException {
		when(beneficialOwnerDetailService.addBeneficialOwnerDetail(beneficialOwnerDetailDTO, header))
				.thenThrow(NoRecordsFoundException.class);
		ResponseEntity<APIResponse<BeneficialOwnerDetailDTO>> responseEntity = beneficialOwnerDetailController
				.addBeneficialOwnerDetail(beneficialOwnerDetailDTO, header);
		assertEquals(responseEntity.getStatusCodeValue(), 404);
	}

	@Test
	void TestAddBeneficialOwnerDetailExceptionRecordAlreadyExistsException() throws SQLException {
		when(beneficialOwnerDetailService.addBeneficialOwnerDetail(beneficialOwnerDetailDTO, header))
				.thenThrow(RecordAlreadyExistsException.class);
		ResponseEntity<APIResponse<BeneficialOwnerDetailDTO>> responseEntity = beneficialOwnerDetailController
				.addBeneficialOwnerDetail(beneficialOwnerDetailDTO, header);
		assertEquals(responseEntity.getStatusCodeValue(), 400);
	}

}
