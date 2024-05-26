package com.nscorp.obis.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.nscorp.obis.domain.CashException;
import com.nscorp.obis.domain.Customer;
import com.nscorp.obis.domain.CustomerCharge;
import com.nscorp.obis.domain.SecUser;
import com.nscorp.obis.dto.CustomerChargeDTO;
import com.nscorp.obis.dto.mapper.CustomerChargeMapper;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.services.CustomerChargeService;

public class CustomerChargeControllerTest {

	@Mock
	CustomerChargeService customerChargeService;

	@Mock
	CustomerChargeMapper customerChargeMapper;

	@InjectMocks
	CustomerChargeController customerChargeController;

	CustomerChargeDTO customerChargeDto;
	CustomerCharge customerCharge;
	List<CustomerChargeDTO> customerCharges;
	CustomerCharge customerCharge1;
	CustomerChargeDTO customerChargeDTO;
	Customer customer;
	CashException cashException;
	SecUser secUser;

	String equipInit;
	String equipType;
	Integer equipNbr;
	String equipId;
	Map<String, String> headers;
	CustomerChargeDTO custChrgDto;

	@BeforeEach
	void setup() {
		MockitoAnnotations.openMocks(this);
		equipInit = "FSCU";
		equipType = "C";
		equipNbr = 616048;
		equipId = "0";
		customerCharges = new ArrayList<>();
		customerChargeDto = new CustomerChargeDTO();
		customerCharge = new CustomerCharge();
		custChrgDto = new CustomerChargeDTO();
		custChrgDto.setBeginTerminalName("AUSTELL");
		custChrgDto.setBgnEvtCd("C");
		custChrgDto.setBgnLclDtTm(null);
		custChrgDto.setBgnLEInd("L");
		custChrgDto.setBgnTermId(123465L);
		custChrgDto.setBillCustId(5645L);
		custChrgDto.setBillReleaseDate(null);
		custChrgDto.setBondedInd("Y");
		custChrgDto.setChrgAmt(null);
		custChrgDto.setChrgBaseDays(null);
		custChrgDto.setChrgCd("TRE");
		custChrgDto.setChrgDays(null);
		custChrgDto.setChrgId(34567L);
		custChrgDto.setChrgTp("STO");
		custChrgDto.setCustomerName("BGN TERM");
		custChrgDto.setCustomerNbr("765678");
		custChrgDto.setDeliverByDTM("YU");
		custChrgDto.setDeliverOrSailBy(null);
		custChrgDto.setSailByDTM("YUE");
		custChrgDto.setEndEvtCd("R");
		custChrgDto.setEndLclDtTm(null);
		custChrgDto.setEndLEInd(null);
		custChrgDto.setEndTermId(76567L);
		custChrgDto.setEndTerminalName("AUSTELL");
		custChrgDto.setEquipId(equipId);
		custChrgDto.setEquipInit(equipInit);
		custChrgDto.setEquipNbr(equipNbr);
		custChrgDto.setEquipType(equipType);
		custChrgDto.setGuarantee(null);
		custChrgDto.setLastFreeDtTm(null);
		custChrgDto.setLocallyBilledInd(null);
		custChrgDto.setOverrideNm("HYTRER");
		custChrgDto.setPeakRtInd(null);
		custChrgDto.setRateId(76545L);
		custChrgDto.setRateType("C");
		custChrgDto.setSchdDeliveryDTM(null);
		custChrgDto.setSvcId(23456L);
		customerCharges.add(custChrgDto);
		cashException = new CashException();
		cashException.setCashExceptionId(Long.valueOf(1));
		customerCharge1 = new CustomerCharge();
		customer = new Customer();
		customer.setCustomerName("abed");
		secUser = new SecUser();
		secUser.setSecUserId("abed");
		secUser.setSecUserName("abed");
		secUser.setSecUserTitle("title");
		customerCharge1.setUversion("!");
		customerCharge1.setBillReleaseDate(null);
		customerCharge1.setRateType("D");
		customerCharge1.setSvcId(Long.valueOf(1));
		customerCharge1.setEquipId("123");
		customerCharge1.setEquipInit("abed");
		customerCharge1.setEquipType("C");
		customerCharge1.setEquipNbr(12);
		customerCharge1.setBillCustId(Long.valueOf(1));
		customerCharge1.setBgnTermId(Long.valueOf(1));
		customerCharge1.setBgnLclDtTm(new Timestamp(System.currentTimeMillis()));
		customerChargeDTO = new CustomerChargeDTO();
		customerChargeDTO.setBillReleaseDate(null);
		customerChargeDTO.setRateType("D");
		customerChargeDTO.setSvcId(Long.valueOf(1));
		customerChargeDTO.setEquipId("123");
		customerChargeDTO.setEquipInit("abed");
		customerChargeDTO.setEquipType("C");
		customerChargeDTO.setEquipNbr(12);
		customerChargeDTO.setBillCustId(Long.valueOf(1));
		customerChargeDTO.setBgnLclDtTm(new Timestamp(System.currentTimeMillis()));

		headers = new HashMap<>();
		headers.put("userid", "test");
		headers.put("extensionschema", "test");
	}

	@AfterEach
	void tearDown() {
		customerCharges = null;
		customerCharge1 = null;
		equipInit = null;
		equipType = null;
		equipNbr = null;
		equipId = null;
		custChrgDto = null;
		customerCharge = null;
		customer = null;
		cashException = null;
		secUser = null;
		headers = null;
		customerChargeDTO=null;

	}

	@Test
	void testGetCustomerCharge() {
		when(customerChargeService.getStorageCharge( equipInit, equipNbr)).thenReturn(customerCharges);
		ResponseEntity<APIResponse<List<CustomerChargeDTO>>> response = customerChargeController
				.searchCustomerCharge( equipInit, equipNbr);
		assertEquals(response.getStatusCode(), HttpStatus.OK);
	}

	@Test
	void testUpdateCustomerCharge() {
		when(customerChargeService.updateCustomerCharge(any(), any())).thenReturn(customerCharge1);
		ResponseEntity<APIResponse<CustomerChargeDTO>> result = customerChargeController
				.updateCustomerCharge(customerChargeDTO, headers);
		Assertions.assertNotNull(result);
	}
	@Test
	void testGetCustomerChargeNoRecordFound() {
		when(customerChargeController.searchCustomerCharge( equipInit, equipNbr)).thenThrow(new NoRecordsFoundException());
		ResponseEntity<APIResponse<List<CustomerChargeDTO>>> result = customerChargeController
				.searchCustomerCharge( equipInit, equipNbr);
		assertEquals(404, result.getStatusCodeValue());
	}

	@Test
	void testGetCustomerChargeException() {
		when(customerChargeService.getStorageCharge(equipInit, equipNbr)).thenThrow(new RuntimeException());
		ResponseEntity<APIResponse<List<CustomerChargeDTO>>> result = customerChargeController
				.searchCustomerCharge( equipInit, equipNbr);
		Assertions.assertEquals(500, result.getStatusCodeValue());
	}
	@Test
	void testUpdateCustomerChargeNoRecordFound() {
		when(customerChargeController.updateCustomerCharge(customerChargeDTO, headers)).thenThrow(new NoRecordsFoundException());
		ResponseEntity<APIResponse<CustomerChargeDTO>> result = customerChargeController
				.updateCustomerCharge(customerChargeDTO, headers);
		assertEquals(404, result.getStatusCodeValue());
	}

	@Test
	void testUpdateCustomerChargeException() {
		when(customerChargeService.updateCustomerCharge(any(), any())).thenThrow(new RuntimeException());
		ResponseEntity<APIResponse<CustomerChargeDTO>> result = customerChargeController
				.updateCustomerCharge(customerChargeDTO, headers);
		Assertions.assertEquals(500, result.getStatusCodeValue());
	}

}
