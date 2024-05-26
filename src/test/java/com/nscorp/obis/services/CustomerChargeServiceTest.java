package com.nscorp.obis.services;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

import com.nscorp.obis.domain.CashException;
import com.nscorp.obis.domain.Customer;
import com.nscorp.obis.domain.CustomerCharge;
import com.nscorp.obis.domain.GenericCodeUpdate;
import com.nscorp.obis.domain.MoneyReceived;
import com.nscorp.obis.domain.SecUser;
import com.nscorp.obis.domain.ShipEntity;
import com.nscorp.obis.domain.ShipVessel;
import com.nscorp.obis.domain.Shipment;
import com.nscorp.obis.domain.ShipmentDlvyDate;
import com.nscorp.obis.domain.ShipmentExt;
import com.nscorp.obis.domain.StorageRates;
import com.nscorp.obis.domain.Terminal;
import com.nscorp.obis.dto.CustomerChargeDTO;
import com.nscorp.obis.exception.InvalidDataException;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.repository.CashExceptionRepository;
import com.nscorp.obis.repository.CustomerChargeRepository;
import com.nscorp.obis.repository.CustomerRepository;
import com.nscorp.obis.repository.GenericCodeUpdateRepository;
import com.nscorp.obis.repository.MoneyReceivedRepository;
import com.nscorp.obis.repository.PayGuaranteeRepository;
import com.nscorp.obis.repository.SecUserRepository;
import com.nscorp.obis.repository.ShipEntityRepo;
import com.nscorp.obis.repository.ShipVesselRepository;
import com.nscorp.obis.repository.ShipmentDlvyDateRepository;
import com.nscorp.obis.repository.ShipmentExtRepository;
import com.nscorp.obis.repository.ShipmentRepository;
import com.nscorp.obis.repository.StorageRatesRepository;
import com.nscorp.obis.repository.TerminalRepository;
import com.nscorp.obis.response.data.PaginatedResponse;

class CustomerChargeServiceTest {
    @Mock
    CustomerChargeRepository customerChargeRepository;
    @Mock
    SecUserRepository secUserRepo;
    @Mock
    MoneyReceivedRepository moneyReceivedRepository;
    @Mock
    PayGuaranteeRepository payGuaranteeRepository;
    @Mock
    ShipmentExtRepository shipmentExtRepo;
    @Mock
    ShipVesselRepository shipVesselRepo;
    @Mock
    ShipEntityRepo shipEntityRepo;
    @Mock
    CustomerRepository customerRepository;
    @Mock
    TerminalRepository terminalRepository;
    @Mock
    StorageRatesRepository storageRatesRepository;

    @Mock
    CashExceptionRepository cashExceptionRepository;
    @Mock
    GenericCodeUpdateRepository genericCodeUpdateRepository;
    @Mock
    ShipmentDlvyDateRepository shipmentDlvyDateRepository;
    @Mock
    ShipmentRepository shipmentRepository;
    @InjectMocks
    CustomerChargeServiceImpl customerChargeServiceImpl;

    MoneyReceived moneyReceived;
    CustomerCharge customerCharge;
    CustomerChargeDTO customerChargeDTO;
    Customer customer;
    CashException cashException;
    SecUser secUser;
    Map<String, String> header;

    CustomerChargeDTO customerChargeDto;
    List<CustomerChargeDTO> customerCharges;
    Page<CustomerChargeDTO> pageResponse;
    Page<CustomerCharge> pageCustomerChange;
    PaginatedResponse<CustomerChargeDTO> paginatedResponse;

    ShipEntity shipEntity;
    List<ShipEntity> shipEntityList;

    Specification<CustomerCharge> specification;
    String equipInit;
    String equipType;
    Integer equipNbr;
    String equipId;
    Integer pageNumber;
    Integer pageSize;
    Map<String, String> headers;
    CustomerChargeDTO custChrgDto;

    StorageRates storageRates;

    GenericCodeUpdate genericCodeUpdate;

    Terminal terminal;

    ShipmentDlvyDate shipmentDlvyDate;

    Shipment shipment;

    ShipVessel shipVessel;

    ShipmentExt shipmentExt;
    List<MoneyReceived> moneyReceivedList;
    List<CustomerCharge> customerChargeList;
    CustomerCharge customerCharge1;
    List<CustomerCharge> customerChargeList1;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        equipInit = "FSCU";
        equipType = "C";
        equipNbr = 616048;
        equipId = "0";
        customerCharge1 = new CustomerCharge();
        customerChargeList1 = new ArrayList<>();
        customerCharge1.setBgnEvtCd("C");
        customerCharge1.setBgnLclDtTm(null);
        customerCharge1.setBgnLEInd("L");
        customerCharge1.setBgnTermId(123465L);
        customerCharge1.setBillCustId(5645L);
        customerCharge1.setBillReleaseDate(null);
        customerCharge1.setBondedInd("Y");
        customerCharge1.setChrgAmt(null);
        customerCharge1.setChrgBaseDays(null);
        customerCharge1.setChrgCd("TRE");
        customerCharge1.setChrgDays(null);
        customerCharge1.setChrgId(34567L);
        customerCharge1.setChrgTp("");
        customerCharge1.setEndEvtCd("R");
        customerCharge1.setEndLclDtTm(null);
        customerCharge1.setEndLEInd(null);
        customerCharge1.setEndTermId(76567L);
        customerCharge1.setEquipId(equipId);
        customerCharge1.setEquipInit(equipInit);
        customerCharge1.setEquipNbr(equipNbr);
        customerCharge1.setEquipType("T");
        customerCharge1.setLastFreeDtTm(null);
        customerCharge1.setLocallyBilledInd(null);
        customerCharge1.setOverrideNm("HYTRER");
        customerCharge1.setPeakRtInd(null);
        customerCharge1.setRateId(76545L);
        customerCharge1.setRateType("C");
        customerCharge1.setSvcId(23456L);

        customerChargeList1.add(customerCharge1);
        moneyReceived = new MoneyReceived();
        moneyReceivedList = new ArrayList<>();
        moneyReceivedList.add(moneyReceived);

        shipmentExt = new ShipmentExt();
        shipmentExt.setSpecEndorCd1("XP");
        shipmentExt.setSpecEndorCd2("XP");
        shipmentExt.setSpecEndorCd3("XP");
        shipmentExt.setSpecEndorCd4("XP");
        shipmentExt.setSpecEndorCd5("XP");
        shipmentExt.setSpecEndorCd6("XP");
        shipmentExt.setSpecEndorCd7("XP");
        shipmentExt.setSpecEndorCd8("XP");
        shipmentExt.setSpecEndorCd9("XP");
        shipmentExt.setSpecEndorCd10("XP");
        shipmentExt.setSpecEndorCd11("XP");

        shipment = new Shipment();
        shipment.setReqDlvyEta(new Timestamp(System.currentTimeMillis()));
        shipment.setShipStat("P");
        shipVessel = new ShipVessel();
        shipVessel.setVesselSailDt(new Timestamp(System.currentTimeMillis()));


        storageRates = new StorageRates();
        storageRates.setBillCustId(1234L);
        storageRates.setSchedDelRateInd("Y");

        customer = new Customer();
        customer.setCustomerId(Long.valueOf(123));

        terminal = new Terminal();
        terminal.setTerminalName("AUSTELL");

        genericCodeUpdate = new GenericCodeUpdate();
        genericCodeUpdate.setGenericLongDescription("STO");

        shipmentDlvyDate = new ShipmentDlvyDate();
        shipmentDlvyDate.setDlvyByDtTm(new Timestamp(System.currentTimeMillis()));


        customerCharges = new ArrayList<>();
        customerChargeDto = new CustomerChargeDTO();
        customerCharge = new CustomerCharge();
        customerCharge.setBgnEvtCd("C");
        customerCharge.setBgnLclDtTm(null);
        customerCharge.setBgnLEInd("L");
        customerCharge.setBgnTermId(123465L);
        customerCharge.setBillCustId(5645L);
        customerCharge.setBillReleaseDate(null);
        customerCharge.setBondedInd("Y");
        customerCharge.setChrgAmt(null);
        customerCharge.setChrgBaseDays(null);
        customerCharge.setChrgCd("TRE");
        customerCharge.setChrgDays(null);
        customerCharge.setChrgId(34567L);
        customerCharge.setChrgTp("STO");
        customerCharge.setEndEvtCd("R");
        customerCharge.setEndLclDtTm(null);
        customerCharge.setEndLEInd(null);
        customerCharge.setEndTermId(76567L);
        customerCharge.setEquipId(equipId);
        customerCharge.setEquipInit(equipInit);
        customerCharge.setEquipNbr(equipNbr);
        customerCharge.setEquipType(equipType);
        customerCharge.setLastFreeDtTm(null);
        customerCharge.setLocallyBilledInd(null);
        customerCharge.setOverrideNm("HYTRER");
        customerCharge.setPeakRtInd(null);
        customerCharge.setRateId(76545L);
        customerCharge.setRateType("C");
        customerCharge.setSvcId(23456L);
        customerChargeList = new ArrayList<>();
        customerChargeList.add(customerCharge);

        pageCustomerChange = new PageImpl<>(customerChargeList);
        {
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
            custChrgDto.setChrgTp("PDM");
            custChrgDto.setEquipType("C");
            customerCharges.add(custChrgDto);
        }

        //Update
        {
            header = new HashMap<String, String>();
            shipEntity = new ShipEntity();
            shipEntityList = new ArrayList<>();
            shipEntity.setSvcId(1234L);
            shipEntityList.add(shipEntity);
            header.put("userid", "Test");
            header.put("extensionschema", "Test");
            customerChargeDTO = new CustomerChargeDTO();
            cashException = new CashException();
            cashException.setCashExceptionId(Long.valueOf(1));
            customerCharge = new CustomerCharge();
            customer = new Customer();
            customer.setCustomerName("abed");
            secUser = new SecUser();
            secUser.setSecUserId("abed");
            secUser.setSecUserName("abed");
            secUser.setSecUserTitle("title");
            customerCharge.setUversion("!");
            customerCharge.setBillReleaseDate(null);
            customerCharge.setRateType("D");
            customerCharge.setSvcId(Long.valueOf(1));
            customerCharge.setEquipId("123");
            customerCharge.setEquipInit("abed");
            customerCharge.setEquipType("C");
            customerCharge.setEquipNbr(12);
            customerCharge.setBillCustId(Long.valueOf(1));
            customerCharge.setBgnTermId(Long.valueOf(1));
            customerCharge.setBgnLclDtTm(new Timestamp(System.currentTimeMillis()));
            customerChargeDTO.setBillReleaseDate(null);
            customerChargeDTO.setRateType("D");
            customerChargeDTO.setSvcId(Long.valueOf(1));
            customerChargeDTO.setEquipId("123");
            customerChargeDTO.setEquipInit("abed");
            customerChargeDTO.setEquipType("C");
            customerChargeDTO.setEquipNbr(12);
            customerChargeDTO.setBillCustId(Long.valueOf(1));
            customerChargeDTO.setBgnLclDtTm(new Timestamp(System.currentTimeMillis()));

        }
        specification = (Root<CustomerCharge> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        };

        pageResponse = new PageImpl<>(customerCharges, PageRequest.of(0, 10), 100L);
        paginatedResponse = PaginatedResponse.of(customerCharges, pageResponse);
        pageNumber = 1;
        pageSize = 20;

    }

    @AfterEach
    void tearUp() {
        customerCharge = null;
        customerChargeDTO = null;
        customer = null;
        cashException = null;
        secUser = null;
        header = null;

        equipInit = null;
        equipType = null;
        equipNbr = null;
        equipId = null;
        custChrgDto = null;
    }

    @Test
    void testUpdateCustomerCharge() {
        // AdjustCharge
        customerChargeDTO.setActiveCharge(true);
        customerChargeDTO.setCifOverride(false);
        customerChargeDTO.setVoidRecord(false);
        when(customerChargeRepository.existsById(any())).thenReturn(true);
        when(secUserRepo.findBySecUserId(any())).thenReturn(secUser);
        when(customerChargeRepository.save(any())).thenReturn(customerCharge);
        CustomerCharge result = customerChargeServiceImpl.updateCustomerCharge(customerChargeDTO, header);
        Assertions.assertNotNull(result);
        // Void
        customerChargeDTO.setActiveCharge(false);
        customerChargeDTO.setCifOverride(false);
        customerChargeDTO.setVoidRecord(true);
        result = customerChargeServiceImpl.updateCustomerCharge(customerChargeDTO, header);
        Assertions.assertNotNull(result);
        // CIF Override
        customerChargeDTO.setActiveCharge(false);
        customerChargeDTO.setCifOverride(true);
        customerChargeDTO.setVoidRecord(false);
        when(shipEntityRepo.findAllBySegTypeAndSvcId(any(), any())).thenReturn(shipEntityList);
        when(shipEntityRepo.getEntityCustomerNr(anyLong())).thenReturn("1234567890");
        when(customerRepository.findByCustomerId(anyLong())).thenReturn(customer);

        when(cashExceptionRepository.save(any())).thenReturn(cashException);
        result = customerChargeServiceImpl.updateCustomerCharge(customerChargeDTO, header);
        Assertions.assertNotNull(result);

    }

    @Test
    void testUpdateCustomerChargeInvalidDataException() {
        // AdjustCharge
        customerChargeDTO.setActiveCharge(true);
        customerChargeDTO.setCifOverride(false);
        customerChargeDTO.setVoidRecord(false);
        when(customerChargeRepository.existsById(any())).thenReturn(true);
        customerChargeDTO.setBillReleaseDate(new Date(System.currentTimeMillis()));
        assertThrows(InvalidDataException.class,
                () -> customerChargeServiceImpl.updateCustomerCharge(customerChargeDTO, header));
        customerChargeDTO.setBillReleaseDate(null);
        customerChargeDTO.setRateType("M");
        assertThrows(InvalidDataException.class,
                () -> customerChargeServiceImpl.updateCustomerCharge(customerChargeDTO, header));
        // CIF Override
        customerChargeDTO.setActiveCharge(false);
        customerChargeDTO.setCifOverride(true);
        customerChargeDTO.setVoidRecord(false);
        customerChargeDTO.setBillReleaseDate(new Date(System.currentTimeMillis()));
        assertThrows(InvalidDataException.class,
                () -> customerChargeServiceImpl.updateCustomerCharge(customerChargeDTO, header));
        customerChargeDTO.setBillReleaseDate(null);
        customerChargeDTO.setRateType("M");
        assertThrows(InvalidDataException.class,
                () -> customerChargeServiceImpl.updateCustomerCharge(customerChargeDTO, header));
        customerChargeDTO.setRateType("D");
        shipEntity.setSvcId(1254L);
        shipEntityList.add(shipEntity);
        when(shipEntityRepo.findAllBySegTypeAndSvcId(any(), any())).thenReturn(shipEntityList);
        assertThrows(InvalidDataException.class,
                () -> customerChargeServiceImpl.updateCustomerCharge(customerChargeDTO, header));
        // VOID
        customerChargeDTO.setActiveCharge(false);
        customerChargeDTO.setCifOverride(false);
        customerChargeDTO.setVoidRecord(true);
        customerChargeDTO.setBillReleaseDate(new Date(System.currentTimeMillis()));
        assertThrows(InvalidDataException.class,
                () -> customerChargeServiceImpl.updateCustomerCharge(customerChargeDTO, header));
    }

    @Test
    void testUpdateCustomerChargeNoRecordFoundException() {
        assertThrows(NoRecordsFoundException.class,
                () -> customerChargeServiceImpl.updateCustomerCharge(customerChargeDTO, header));
    }

    @Test
    void testGetCustomerCharge1() {
        genericCodeUpdate.setGenericLongDescription("PDM");

        when(customerChargeRepository.findByEquipInitAndEquipNbrOrderByBgnLclDtTmDesc(equipInit, equipNbr)).thenReturn(customerChargeList);
        when(storageRatesRepository.findByStorageId(any())).thenReturn(storageRates);
        when(customerRepository.findByCustomerId(any())).thenReturn(customer);
        when(genericCodeUpdateRepository.findByGenericTableCodeAndGenericTable(any(), any())).thenReturn(genericCodeUpdate);
        when(terminalRepository.findByTerminalId(any())).thenReturn(null);
        when(shipmentDlvyDateRepository.findBySvcId(any())).thenReturn(shipmentDlvyDate);
        when(shipmentRepository.findBySvcId(any())).thenReturn(shipment);
        when(shipVesselRepo.findBySvcIdAndVesselDirCd(any(), any())).thenReturn(shipVessel);
        when(shipmentExtRepo.findBySvcId(any())).thenReturn(shipmentExt);
        when(payGuaranteeRepository.existsByChrgId(any())).thenReturn(false);
        when(moneyReceivedRepository.findByChrgId(any())).thenReturn(moneyReceivedList);
        List<CustomerChargeDTO> infoDTOs = customerChargeServiceImpl.getStorageCharge( equipInit, equipNbr);
    }

    @Test
    void testGetCustomerCharge() {
        genericCodeUpdate.setGenericLongDescription("PDM");
        when(customerChargeRepository.findByEquipInitAndEquipNbrOrderByBgnLclDtTmDesc(equipInit, equipNbr)).thenReturn(customerChargeList);
        when(customerRepository.findByCustomerId(any())).thenReturn(customer);
        when(genericCodeUpdateRepository.findByGenericTableCodeAndGenericTable(any(), any())).thenReturn(genericCodeUpdate);
        when(terminalRepository.findByTerminalId(any())).thenReturn(terminal);
        when(storageRatesRepository.findByStorageId(any())).thenReturn(storageRates);
        when(shipmentDlvyDateRepository.findBySvcId(any())).thenReturn(shipmentDlvyDate);
        when(shipmentRepository.findBySvcId(any())).thenReturn(shipment);
        when(shipVesselRepo.findBySvcIdAndVesselDirCd(any(), any())).thenReturn(shipVessel);
        when(shipmentExtRepo.findBySvcId(any())).thenReturn(shipmentExt);
        when(payGuaranteeRepository.existsByChrgId(any())).thenReturn(true);
        when(moneyReceivedRepository.findByChrgId(any())).thenReturn(moneyReceivedList);
        List<CustomerChargeDTO> infoDTOs = customerChargeServiceImpl.getStorageCharge(equipInit, equipNbr);

        storageRates.setBillCustId(null);
        when(storageRatesRepository.findByStorageId(any())).thenReturn(storageRates);
        infoDTOs = customerChargeServiceImpl.getStorageCharge( equipInit, equipNbr);
    }

    @Test
    public void testGetCustomerChargeNoRecordFound() {
        equipInit =null;
        equipNbr = null;
        when(customerChargeRepository.findByEquipInitAndEquipNbrOrderByBgnLclDtTmDesc(equipInit, equipNbr)).thenReturn(customerChargeList);
        NoRecordsFoundException exception = assertThrows(NoRecordsFoundException.class,
                () -> when(customerChargeServiceImpl.getStorageCharge( equipInit, equipNbr)));
        }

}
