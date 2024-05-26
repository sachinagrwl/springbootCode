package com.nscorp.obis.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import com.nscorp.obis.domain.CorporateCustomerDetail;
import com.nscorp.obis.dto.CorporateCustomerDetailDTO;
import com.nscorp.obis.dto.mapper.CorporateCustomerDetailMapper;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.services.CorporateCustomerDetailService;


public class CorporateCustomerDetailControllerTest {
	@Mock
	CorporateCustomerDetailMapper corporateCustomerDetailMapper;

	@Mock
	CorporateCustomerDetailService corporateCustomerDetailService;

	@InjectMocks
	CorporateCustomerDetailController corporateCustomerDetailController;
    CorporateCustomerDetail corporateCustomerDetail;
	CorporateCustomerDetailDTO corporateCustomerDetailDto;
	List<CorporateCustomerDetailDTO> corporateCustomerDetailDtoList;
	List<CorporateCustomerDetail> corporateCustomerDetailList;
	Map<String, String> header;
	
	
	@SuppressWarnings("deprecation")
	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		corporateCustomerDetailDtoList = new ArrayList<>();
		corporateCustomerDetail = new CorporateCustomerDetail();
		corporateCustomerDetail.setCorpCustId(Long.valueOf(1));
		corporateCustomerDetail.setCorpCust6(String.valueOf(2));

		corporateCustomerDetailDto = new CorporateCustomerDetailDTO();
		corporateCustomerDetailDto.setCorpCustId(Long.valueOf(1));
		corporateCustomerDetailDto.setCorpCust6(String.valueOf(2));
		corporateCustomerDetailList = new ArrayList<>();	
		corporateCustomerDetailList.add(corporateCustomerDetail);
		corporateCustomerDetailDtoList.add(corporateCustomerDetailDto);
		header = new HashMap<String,String>();
		header.put("userid", "test");
		header.put("extensionschema", "test");
	}
	
	@AfterEach
	void tearDown() throws Exception {
		corporateCustomerDetail = null;
		corporateCustomerDetailList = null;
		corporateCustomerDetailDto = null;
	}

	@Test
	void testGetAllEquipment() {

		when(corporateCustomerDetailService.getCorporateCustomerDetails(Mockito.any(), Mockito.any()))
				.thenReturn(corporateCustomerDetailList);
		ResponseEntity<APIResponse<List<CorporateCustomerDetail>>> corpList = corporateCustomerDetailController
				.getCorporateCustomers(Mockito.any(), Mockito.any());
		assertEquals(corpList.getStatusCodeValue(), 200);
	
		when(corporateCustomerDetailService.getCorporateCustomerDetails(Mockito.any(), Mockito.any()))
		.thenReturn(null);
		corpList = corporateCustomerDetailController
		.getCorporateCustomers(Mockito.any(), Mockito.any());
		
		when(corporateCustomerDetailService.getCorporateCustomerDetails(Mockito.any(), Mockito.any()))
		.thenReturn(Collections.emptyList());
		corpList = corporateCustomerDetailController
		.getCorporateCustomers(Mockito.any(), Mockito.any());
	}

	@Test
	void testEquipmentNoRecordsFoundException() {
		when(corporateCustomerDetailService.getCorporateCustomerDetails(Mockito.any(), Mockito.any()))
				.thenThrow(new NoRecordsFoundException());
		ResponseEntity<APIResponse<List<CorporateCustomerDetail>>> corpList = corporateCustomerDetailController
				.getCorporateCustomers(Mockito.any(), Mockito.any());
		assertEquals(corpList.getStatusCodeValue(), 404);

	}

	@Test
	void testEquipmentException() {
		when(corporateCustomerDetailService.getCorporateCustomerDetails(Mockito.any(), Mockito.any()))
				.thenThrow(new RuntimeException());
		ResponseEntity<APIResponse<List<CorporateCustomerDetail>>> corpList = corporateCustomerDetailController
				.getCorporateCustomers(Mockito.any(), Mockito.any());
		assertEquals(corpList.getStatusCodeValue(), 500);

		when(corporateCustomerDetailService.deleteCorpCustDetail(Mockito.any()))
				.thenThrow(new RuntimeException());
		ResponseEntity<List<APIResponse<CorporateCustomerDetailDTO>>> corpList1 = corporateCustomerDetailController.deletecorpCustDtl(corporateCustomerDetailDtoList);

		assertEquals(corpList1.getStatusCodeValue(), 500);
	}

	@Test
	void testDeletePrimary6() {
		ResponseEntity<List<APIResponse<CorporateCustomerDetailDTO>>> deleteList1 = corporateCustomerDetailController
				.deletecorpCustDtl(null);
		deleteList1 = corporateCustomerDetailController.deletecorpCustDtl(Collections.emptyList());
		
		corporateCustomerDetailService.deleteCorpCustDetail(Mockito.any());
		 deleteList1 = corporateCustomerDetailController.deletecorpCustDtl(corporateCustomerDetailDtoList);
//		assertNotNull(deleteList1);

	}

	@SuppressWarnings("unused")
	@Test
	void testAddPrimary6() {
		when(corporateCustomerDetailService.addPrimary6(CorporateCustomerDetailMapper.INSTANCE.corporateCustomerDetailDTOToCorporateCustomerDetail(corporateCustomerDetailDto), header)).thenReturn(corporateCustomerDetail);
		ResponseEntity<APIResponse<CorporateCustomerDetailDTO>> addedPrimary6 = corporateCustomerDetailController
				.addPrimary6(corporateCustomerDetailDto, header);
//		assertNotNull(addedPrimary6);
	}

	@Test
	void testAddPrimary6Exception() {
		when(corporateCustomerDetailService.addPrimary6(Mockito.any(), Mockito.any()))
				.thenThrow(new RuntimeException());
		ResponseEntity<APIResponse<CorporateCustomerDetailDTO>> addedPrimary6 = corporateCustomerDetailController
				.addPrimary6(corporateCustomerDetailDto,header);
		assertEquals(addedPrimary6.getStatusCodeValue(), 500);
	}

	@Test
	void testAddPrimary6NoRecordsFoundException() {
		when(corporateCustomerDetailService.addPrimary6(Mockito.any(), Mockito.any()))
				.thenThrow(new NoRecordsFoundException());
		ResponseEntity<APIResponse<CorporateCustomerDetailDTO>> addedPrimary6 = corporateCustomerDetailController
				.addPrimary6(corporateCustomerDetailDto,header);
		assertEquals(addedPrimary6.getStatusCodeValue(), 404);
	}

	@Test
	void testAddPrimary6RecordAlreadyExistsException() {
		when(corporateCustomerDetailService.addPrimary6(Mockito.any(), Mockito.any()))
				.thenThrow(new RecordAlreadyExistsException());
		ResponseEntity<APIResponse<CorporateCustomerDetailDTO>> addedPrimary6 = corporateCustomerDetailController
				.addPrimary6(corporateCustomerDetailDto,header);
		assertEquals(addedPrimary6.getStatusCodeValue(), 208);
	}
}