package com.nscorp.obis.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nscorp.obis.exception.InvalidDataException;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.nscorp.obis.domain.BeneficialOwner;
import com.nscorp.obis.dto.BeneficialOwnerDTO;
import com.nscorp.obis.dto.mapper.BeneficialOwnerMapper;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.repository.BeneficialOwnerRepository;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.services.BeneficialOwnerService;

class BeneficialOwnerControllerTest {

    @Mock
    BeneficialOwnerMapper beneficialOwnerMapper;

    @Mock
    BeneficialOwnerRepository beneficialOwnerRepository;

    @Mock
    BeneficialOwnerService beneficialOwnerService;

    @InjectMocks
    BeneficialOwnerController beneficialOwnerController;

    BeneficialOwner beneficialOwner;
    BeneficialOwnerDTO beneficialOwnerDTO;
    List<BeneficialOwner> beneficialOwnerList;
    List<BeneficialOwnerDTO> beneficialOwnerDtoList;

    String bnfLongName;
    String bnfShortName;
    Long bnfCustomerId;
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
        beneficialOwner = new BeneficialOwner();
        beneficialOwnerDTO = new BeneficialOwnerDTO();
        beneficialOwnerList = new ArrayList<BeneficialOwner>();
        beneficialOwnerDtoList = new ArrayList<BeneficialOwnerDTO>();
        bnfLongName = "4 WHEEL PARTS";
        bnfShortName = "4 WHEE PAR";
        bnfCustomerId = 6412238551103L;
        beneficialOwner.setAccountManager("manager");
        beneficialOwner.setBnfLongName(bnfLongName);
        beneficialOwner.setBnfShortName(bnfShortName);
        beneficialOwnerDTO.setAccountManager("manager");
        beneficialOwnerDTO.setBnfCustomerId(bnfCustomerId);

        beneficialOwnerList.add(beneficialOwner);
        beneficialOwnerDtoList.add(beneficialOwnerDTO);
        header = new HashMap<>();
        header.put("userid", "test");
        header.put("extensionschema", "test");

    }

    @AfterEach
    void tearDown() throws Exception {
        beneficialOwner = null;
        beneficialOwnerList = null;
        beneficialOwnerDTO = null;
        beneficialOwnerDtoList = null;
    }

    @Test
    void testGetBeneficialCustomer() throws SQLException {

        when(beneficialOwnerService.fetchBeneficialCustomer(Mockito.any(), Mockito.any())).thenReturn(beneficialOwnerList);
        when(beneficialOwnerMapper.beneficialOwnerDTOToBeneficialOwner(Mockito.any())).thenReturn(beneficialOwner);
        when(beneficialOwnerMapper.beneficialOwnerToBeneficialOwnerDTO(Mockito.any())).thenReturn(beneficialOwnerDTO);

        ResponseEntity<APIResponse<List<BeneficialOwnerDTO>>> getData = beneficialOwnerController
                .getBeneficialCustomer(bnfLongName, bnfShortName);
        assertEquals(getData.getStatusCodeValue(), 200);

    }

	@Test
	void testAddBeneficialCustomer() throws SQLException {

		when(beneficialOwnerService.addBeneficialCustomer(Mockito.any(), Mockito.anyMap())).thenReturn(beneficialOwnerDTO);
		ResponseEntity<APIResponse<BeneficialOwnerDTO>> addData = beneficialOwnerController
				.addBeneficialCustomer(beneficialOwnerDTO, header);
		assertEquals(addData.getStatusCodeValue(), 200);

	}

    @Test
	void testDeleteBeneficialCustomer() {
		
		when(beneficialOwnerMapper.beneficialOwnerDTOToBeneficialOwner(Mockito.any())).thenReturn(beneficialOwner);
		when(beneficialOwnerService.deleteBeneficialCustomers(Mockito.any())).thenReturn(beneficialOwner);
		when(beneficialOwnerMapper.beneficialOwnerToBeneficialOwnerDTO(Mockito.any())).thenReturn(beneficialOwnerDTO);
		ResponseEntity<List<APIResponse<BeneficialOwnerDTO>>> deleteData = beneficialOwnerController.deleteBeneficialCustomer(beneficialOwnerDtoList);
		assertEquals(deleteData.getStatusCodeValue(), 200);
	}

	@Test
	void testNoRecordsFoundException() throws SQLException {
		when(beneficialOwnerService.fetchBeneficialCustomer(Mockito.any(), Mockito.any()))
				.thenThrow(new NoRecordsFoundException());
		ResponseEntity<APIResponse<List<BeneficialOwnerDTO>>> getData = beneficialOwnerController
				.getBeneficialCustomer(bnfLongName, bnfShortName);
		assertEquals(getData.getStatusCodeValue(), 404);
		when(beneficialOwnerService.addBeneficialCustomer(Mockito.any(), Mockito.anyMap())).thenThrow(RecordAlreadyExistsException.class);
		ResponseEntity<APIResponse<BeneficialOwnerDTO>> addData = beneficialOwnerController
				.addBeneficialCustomer(beneficialOwnerDTO, header);
		assertEquals(addData.getStatusCodeValue(), 400);

        when(beneficialOwnerService.deleteBeneficialCustomers(Mockito.any())).thenThrow(RecordAlreadyExistsException.class);
		ResponseEntity<List<APIResponse<BeneficialOwnerDTO>>> deleteData = beneficialOwnerController.deleteBeneficialCustomer(beneficialOwnerDtoList);
		assertEquals(deleteData.getStatusCodeValue(), 400);
	}

	@Test
	void testException() throws SQLException {
		when(beneficialOwnerService.fetchBeneficialCustomer(Mockito.any(), Mockito.any()))
				.thenThrow(new RuntimeException());
		ResponseEntity<APIResponse<List<BeneficialOwnerDTO>>> getData = beneficialOwnerController
				.getBeneficialCustomer(bnfLongName, bnfShortName);
		assertEquals(getData.getStatusCodeValue(), 500);
		when(beneficialOwnerService.addBeneficialCustomer(Mockito.any(), Mockito.anyMap())).thenThrow(RuntimeException.class);
		ResponseEntity<APIResponse<BeneficialOwnerDTO>> addData = beneficialOwnerController
				.addBeneficialCustomer(beneficialOwnerDTO, header);
		assertEquals(addData.getStatusCodeValue(), 500);

        when(beneficialOwnerService.deleteBeneficialCustomers(Mockito.any())).thenThrow(new NoRecordsFoundException());
		ResponseEntity<List<APIResponse<BeneficialOwnerDTO>>> deleteData = beneficialOwnerController.deleteBeneficialCustomer(beneficialOwnerDtoList);
		assertEquals(deleteData.getStatusCodeValue(), 500);

	}

   

    @Test
    void testUpdateBeneficialOwner() throws SQLException {
        beneficialOwnerDTO.setBnfLongName("4 WHEEL PARTS");
        beneficialOwnerDTO.setBnfShortName("4 WHEE PAR");
        beneficialOwnerDTO.setCustomerId(123l);
        beneficialOwnerDTO.setCategory("test");
        beneficialOwnerDTO.setSubCategory("test");
        beneficialOwnerDTO.setAccountManager("test manager");
        beneficialOwnerDTO.setBnfCustomerId(123L);
        when(beneficialOwnerService.updateBeneficialOwner(Mockito.any(), Mockito.any()))
                .thenReturn(beneficialOwnerDTO);
        ResponseEntity<APIResponse<BeneficialOwnerDTO>> response = beneficialOwnerController
                .updateBeneficialOwner(beneficialOwnerDTO, header);
        Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);

    }

    @Test
    void testInvalidDataExceptionForUpdate() throws SQLException {
        when(beneficialOwnerService.updateBeneficialOwner(beneficialOwnerDTO, header))
                .thenThrow(InvalidDataException.class);
        ResponseEntity<APIResponse<BeneficialOwnerDTO>> exception = beneficialOwnerController
                .updateBeneficialOwner(beneficialOwnerDTO, header);
        Assertions.assertEquals(exception.getStatusCodeValue(), 400);

        when(beneficialOwnerService.deleteBeneficialCustomers(Mockito.any())).thenThrow(InvalidDataException.class);
		ResponseEntity<List<APIResponse<BeneficialOwnerDTO>>> deleteData = beneficialOwnerController.deleteBeneficialCustomer(beneficialOwnerDtoList);
		assertEquals(deleteData.getStatusCodeValue(), 400);

    }

    @Test
    void testNoRecordsExceptionForUpdate() throws SQLException {
        when(beneficialOwnerService.updateBeneficialOwner(beneficialOwnerDTO, header))
                .thenThrow(NoRecordsFoundException.class);
        ResponseEntity<APIResponse<BeneficialOwnerDTO>> exception = beneficialOwnerController
                .updateBeneficialOwner(beneficialOwnerDTO, header);
        Assertions.assertEquals(exception.getStatusCodeValue(), 404);
    }

    @Test
    void testUpdateBeneficialException() throws SQLException {
        when(beneficialOwnerService.updateBeneficialOwner(beneficialOwnerDTO, header)).thenThrow(RuntimeException.class);
        ResponseEntity<APIResponse<BeneficialOwnerDTO>> exception = beneficialOwnerController
                .updateBeneficialOwner(beneficialOwnerDTO, header);
        Assertions.assertEquals(exception.getStatusCodeValue(), 500);
    }

}
