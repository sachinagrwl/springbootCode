package com.nscorp.obis.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.sql.SQLException;
import java.util.*;

import com.nscorp.obis.domain.CityState;
import com.nscorp.obis.repository.CityStateRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.nscorp.obis.domain.CustomerInfo;
import com.nscorp.obis.domain.CustomerLocalInfo;
import com.nscorp.obis.dto.CustomerLocalInfoDTO;
import com.nscorp.obis.exception.InvalidDataException;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.repository.CustomerInfoRepository;
import com.nscorp.obis.repository.CustomerLocalInfoRepository;
import com.nscorp.obis.repository.TerminalRepository;

public class CustomerLocalInfoServiceTest {

    @Mock
    CustomerLocalInfoRepository customerLocalInfoRepository;

    @Mock
    CustomerInfoRepository customerRepo;

    @Mock
    CityStateRepository cityStateRepository;

    @Mock
    TerminalRepository terminalRepo;

    @InjectMocks
    CustomerLocalInfoServiceImpl customerLocalInfoService;

    CustomerLocalInfo customerLocalInfo;
    CustomerLocalInfoDTO customerLocalInfoDTO;

    CustomerInfo customerInfo;

    Optional<CustomerInfo> custOptional;

    Long customerId;
    Long terminalId;
    String customerName;
    String state;
    Map<String, String> headers;
    List<CityState> stateList;


    @BeforeAll
    static void setUpBeforeClass() throws Exception {
    }

    @AfterAll
    static void tearDownAfterClass() throws Exception {
    }

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        customerLocalInfo = new CustomerLocalInfo();
        customerLocalInfoDTO = new CustomerLocalInfoDTO();
        CityState cityState = new CityState();
        stateList = new ArrayList<>();
        customerInfo = new CustomerInfo();
        customerId = 619919702687L;
        terminalId = 99990010120811L;
        customerName = "TEST";
        state = "AB";
        cityState.setStateAbbreviation(state);
        stateList.add(cityState);
        customerInfo.setCustomerId(customerId);
        customerInfo.setCustomerName(customerName);
        customerInfo.setState(state);
        customerLocalInfo.setCustomerState(state);
        customerLocalInfo.setCustomerId(customerId);
        customerLocalInfo.setTerminalId(terminalId);
        customerLocalInfoDTO.setCustomerId(customerId);
        customerLocalInfoDTO.setTerminalId(terminalId);
        customerLocalInfoDTO.setCustomerState(state);
        custOptional = Optional.of(customerInfo);
        headers = new HashMap<>();
        headers.put("userid", "test");
        headers.put("extensionschema", "test");
    }

    @AfterEach
    void tearDown() throws Exception {
        customerLocalInfo = null;
        customerLocalInfoDTO = null;
        customerInfo = null;
        custOptional = null;
        headers = null;
    }

    @Test
    void testFetchCustomerLocalInfo() throws SQLException {
        when(customerLocalInfoRepository.existsByCustomerId(Mockito.any())).thenReturn(true);
        when(customerLocalInfoRepository.existsByTerminalId(Mockito.any())).thenReturn(true);
        when(customerLocalInfoRepository.findByCustomerIdAndTerminalId(customerId, terminalId))
                .thenReturn(customerLocalInfo);
    }

    @Test
    void testDeleteCustomerLocalInfo() {
        when(customerLocalInfoRepository.existsByCustomerId(Mockito.any())).thenReturn(true);
        when(customerLocalInfoRepository.existsByTerminalId(Mockito.any())).thenReturn(true);
        when(customerLocalInfoRepository.findByCustomerIdAndTerminalId(customerId, terminalId))
                .thenReturn(customerLocalInfo);
        CustomerLocalInfoDTO dto = customerLocalInfoService.deleteCustomerLocalInfo(customerLocalInfoDTO);
        assertEquals(dto.getCustomerId(), customerLocalInfoDTO.getCustomerId());
    }

    @Test
    void testAddCustomerLocalInfo() {
        when(customerRepo.findById(Mockito.anyLong())).thenReturn(custOptional);
        when(terminalRepo.existsById(Mockito.anyLong())).thenReturn(true);
        when(customerLocalInfoRepository.findByCustomerIdAndTerminalId(customerId, terminalId)).thenReturn(null);
        when(cityStateRepository.findAllByStateAbbreviation(state)).thenReturn(stateList);
        when(customerLocalInfoRepository.save(Mockito.any())).thenReturn(customerLocalInfo);
        CustomerLocalInfoDTO infoDTO = customerLocalInfoService.addCustomerLocalInfo(customerLocalInfoDTO, headers);
        System.out.println(infoDTO.getCustomerId() + " - " + customerLocalInfoDTO.getCustomerId());
        assertEquals(infoDTO.getCustomerId(), customerLocalInfoDTO.getCustomerId());
    }

    @Test
    void testUpdateCustomerLocalInfo() {
        when(customerLocalInfoRepository.existsByCustomerId(Mockito.any())).thenReturn(true);
        when(customerLocalInfoRepository.existsByTerminalId(Mockito.any())).thenReturn(true);
        when(customerLocalInfoRepository.findByCustomerIdAndTerminalId(customerId, terminalId))
                .thenReturn(customerLocalInfo);
        when(cityStateRepository.findAllByStateAbbreviation(state)).thenReturn(stateList);
        when(customerLocalInfoRepository.save(Mockito.any())).thenReturn(customerLocalInfo);

        when(customerLocalInfoService.updateCustomerLocalInfo(customerLocalInfoDTO, headers))
                .thenReturn(customerLocalInfo);
    }

    @Test
    void testAddCustomerLocalInfoHeaderException() {
        headers.put("userid", null);
        assertThrows(InvalidDataException.class,
                () -> customerLocalInfoService.addCustomerLocalInfo(customerLocalInfoDTO, headers));
        headers.put("userid", "test");
        headers.put("extensionschema", " ");
        assertThrows(NoRecordsFoundException.class,
                () -> customerLocalInfoService.addCustomerLocalInfo(customerLocalInfoDTO, headers));
    }

    @Test
    void testAddCustomerLocalInfoException() {
        custOptional = Optional.empty();
        when(customerRepo.findById(Mockito.anyLong())).thenReturn(custOptional);
        assertThrows(NoRecordsFoundException.class,
                () -> customerLocalInfoService.addCustomerLocalInfo(customerLocalInfoDTO, headers));
        custOptional = Optional.of(customerInfo);
        when(customerRepo.findById(Mockito.anyLong())).thenReturn(custOptional);
        when(terminalRepo.existsById(Mockito.anyLong())).thenReturn(false);
        assertThrows(NoRecordsFoundException.class,
                () -> customerLocalInfoService.addCustomerLocalInfo(customerLocalInfoDTO, headers));
        when(customerLocalInfoRepository.findByCustomerIdAndTerminalId(Mockito.anyLong(), Mockito.anyLong()))
                .thenReturn(customerLocalInfo);
        when(terminalRepo.existsById(Mockito.anyLong())).thenReturn(true);
        assertThrows(RecordAlreadyExistsException.class,
                () -> customerLocalInfoService.addCustomerLocalInfo(customerLocalInfoDTO, headers));
        customerInfo.setCustomerName(" ");
        custOptional = Optional.of(customerInfo);
        when(customerRepo.findById(Mockito.anyLong())).thenReturn(custOptional);
        assertThrows(InvalidDataException.class,
                () -> customerLocalInfoService.addCustomerLocalInfo(customerLocalInfoDTO, headers));
        customerInfo.setCustomerName("TEST");
        custOptional = Optional.of(customerInfo);
        when(customerLocalInfoRepository.findByCustomerIdAndTerminalId(Mockito.anyLong(),Mockito.anyLong())).thenReturn(null);

        stateList = new ArrayList<>();
        when(cityStateRepository.findAllByStateAbbreviation(Mockito.anyString())).thenReturn(stateList);
        assertThrows(NoRecordsFoundException.class,
                () -> customerLocalInfoService.addCustomerLocalInfo(customerLocalInfoDTO, headers));

    }

    @Test
    void testNoRecordFoundException() throws SQLException {
        when(customerLocalInfoRepository.existsByCustomerId(customerId)).thenReturn(false);
        when(customerLocalInfoRepository.existsByTerminalId(terminalId)).thenReturn(true);
        NoRecordsFoundException exception = assertThrows(NoRecordsFoundException.class,
                () -> when(customerLocalInfoService.fetchCustomerLocalInfo(customerId, terminalId)));
        assertEquals("No Customer Found with this customer id : " + customerId, exception.getMessage());
        NoRecordsFoundException deleteException = assertThrows(NoRecordsFoundException.class,
                () -> when(customerLocalInfoService.deleteCustomerLocalInfo(customerLocalInfoDTO)));
        assertEquals("No Customer Found with this customer id : " + customerId, deleteException.getMessage());
        when(customerLocalInfoRepository.existsByCustomerId(customerId)).thenReturn(true);
        when(customerLocalInfoRepository.existsByTerminalId(terminalId)).thenReturn(false);
        NoRecordsFoundException exception2 = assertThrows(NoRecordsFoundException.class,
                () -> when(customerLocalInfoService.fetchCustomerLocalInfo(customerId, terminalId)));
        assertEquals("No Terminal Found with this terminal id : " + terminalId, exception2.getMessage());
        NoRecordsFoundException deleteException2 = assertThrows(NoRecordsFoundException.class,
                () -> when(customerLocalInfoService.deleteCustomerLocalInfo(customerLocalInfoDTO)));
        assertEquals("No Terminal Found with this terminal id : " + terminalId, deleteException2.getMessage());
        when(customerLocalInfo == null).thenReturn(true);
        NoRecordsFoundException exception3 = assertThrows(NoRecordsFoundException.class,
                () -> when(customerLocalInfoService.fetchCustomerLocalInfo(customerId, terminalId)));
        assertEquals("No Record found for this combination", exception3.getMessage());
        when(customerLocalInfoRepository.findByCustomerIdAndTerminalId(customerId, terminalId)).thenReturn(null);
        NoRecordsFoundException deleteException3 = assertThrows(NoRecordsFoundException.class,
                () -> when(customerLocalInfoService.deleteCustomerLocalInfo(customerLocalInfoDTO)));
        assertEquals("No Record found for this combination", deleteException3.getMessage());

        when(customerLocalInfoRepository.existsByCustomerId(customerId)).thenReturn(false);
        NoRecordsFoundException updateException = assertThrows(NoRecordsFoundException.class,
                () -> when(customerLocalInfoService.updateCustomerLocalInfo(customerLocalInfoDTO, headers)));
        assertEquals("No Customer Found with this customer id : " + customerId, updateException.getMessage());
        when(customerLocalInfoRepository.existsByCustomerId(customerId)).thenReturn(true);
        when(customerLocalInfoRepository.existsByTerminalId(terminalId)).thenReturn(false);
        NoRecordsFoundException updateException1 = assertThrows(NoRecordsFoundException.class,
                () -> when(customerLocalInfoService.updateCustomerLocalInfo(customerLocalInfoDTO, headers)));
        assertEquals("No Terminal Found with this terminal id : " + terminalId, updateException1.getMessage());
        when(customerLocalInfoRepository.existsByTerminalId(terminalId)).thenReturn(true);
        customerLocalInfoDTO.setCustomerId(null);
        NoRecordsFoundException updateException2 = assertThrows(NoRecordsFoundException.class,
                () -> when(customerLocalInfoService.updateCustomerLocalInfo(customerLocalInfoDTO, headers)));
        assertEquals("Customer Id is required field", updateException2.getMessage());
        customerLocalInfoDTO.setCustomerId(customerId);
        customerLocalInfoDTO.setTerminalId(null);
        NoRecordsFoundException updateException3 = assertThrows(NoRecordsFoundException.class,
                () -> when(customerLocalInfoService.updateCustomerLocalInfo(customerLocalInfoDTO, headers)));
        assertEquals("Terminal Id is required field", updateException3.getMessage());
        customerLocalInfoDTO.setTerminalId(terminalId);
        NoRecordsFoundException updateException4 = assertThrows(NoRecordsFoundException.class,
                () -> when(customerLocalInfoService.updateCustomerLocalInfo(customerLocalInfoDTO, headers)));
        assertEquals("No record found for this customerId : " + customerId + " and terminalId : " + terminalId, updateException4.getMessage());

        when(customerLocalInfoRepository.findByCustomerIdAndTerminalId(customerId,terminalId)).thenReturn(customerLocalInfo);

        stateList = new ArrayList<>();
        when(cityStateRepository.findAllByStateAbbreviation(state)).thenReturn(stateList);
        NoRecordsFoundException updateException5 = assertThrows(NoRecordsFoundException.class,
                () -> when(customerLocalInfoService.updateCustomerLocalInfo(customerLocalInfoDTO, headers)));
        assertEquals("No State Record Found For Given State Id", updateException5.getMessage());

    }

}
