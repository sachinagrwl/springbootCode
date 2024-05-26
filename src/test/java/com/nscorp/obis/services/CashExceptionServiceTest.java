package com.nscorp.obis.services;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Optional;

import com.nscorp.obis.dto.CustomerPerDiemRateSelectionDTO;
import com.nscorp.obis.exception.InvalidDataException;
import com.nscorp.obis.repository.CustomerInfoRepository;
import com.nscorp.obis.repository.TerminalRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.jpa.domain.Specification;

import com.nscorp.obis.common.SpecificationGenerator;
import com.nscorp.obis.domain.CashException;
import com.nscorp.obis.domain.CustomerInfo;
import com.nscorp.obis.dto.CashExceptionDTO;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.repository.CashExceptionRepository;

public class CashExceptionServiceTest {

    @Mock
    CashExceptionRepository cashExceptionRepository;

    @Mock
    SpecificationGenerator specificationGenerator;

    @InjectMocks
    CashExceptionServiceImpl cashExceptionServiceImpl;

    CashExceptionDTO cashExceptionDTO;

    CashException cashException;

    List<CashExceptionDTO> cashExceptionDtos;

    List<CashException> cashExceptions;

    String customerName = "Test Customer";
    String customerPrimarySix = "123456";

    Specification<CashException> specification;

    Map<String, String> headers;

    @Mock
    CustomerInfoRepository custInfoRepo;

    @Mock
    TerminalRepository terminalRepo;

    Optional<CashException> custOptional;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        cashExceptionDTO = new CashExceptionDTO();
        cashExceptionDtos = new ArrayList<CashExceptionDTO>();
        cashException = new CashException();
        cashExceptions = new ArrayList<>();
        cashExceptionDTO.setCashExceptionId(11450798953015L);
        cashExceptionDtos.add(cashExceptionDTO);
        cashException.setCashExceptionId(11450798953015L);
        cashExceptions.add(cashException);
        specification = (Root<CashException> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
        headers = new HashMap<>();
        headers.put("userid", "test");
        headers.put("extensionschema", "test");
        custOptional = Optional.of(cashException);
    }

    @AfterEach
    void tearDown() {
        cashExceptionDTO = null;
        cashExceptionDtos = null;
        specification = null;
        custOptional = null;
    }

    @Test
    void testGetCashException() {
        when(specificationGenerator.cashExceptionSpecification(customerName, customerPrimarySix))
                .thenReturn(specification);
        when(cashExceptionRepository.findAll()).thenReturn(cashExceptions);
        when(cashExceptionRepository.findAll(Mockito.eq(specification))).thenReturn(cashExceptions);
        List<CashExceptionDTO> response = cashExceptionServiceImpl.getCashException(customerName, customerPrimarySix);
        assertEquals(cashExceptionDtos, response);
        List<CashExceptionDTO> response2 = cashExceptionServiceImpl.getCashException(null, null);
        assertEquals(cashExceptionDtos, response2);
        when(cashExceptionRepository.findAll(Mockito.eq(specification))).thenReturn(new ArrayList<>());
        assertThrows(NoRecordsFoundException.class,
                () -> cashExceptionServiceImpl.getCashException(customerName, customerPrimarySix));
    }

    @Test
    void testAddCashException() throws SQLException {
        cashException.setEquipType("C");
        cashExceptionDTO.setEquipType("C");
        cashException.setLoadedOrEmpty("L");
        cashExceptionDTO.setLoadedOrEmpty("L");

        cashExceptionDTO.setUversion("1");
        cashExceptionDTO.setBnfCustomerNumber("Test");
        cashExceptionDTO.setBnfPrimarySix("001234");
        cashExceptionDTO.setEffectiveDate(new Date(11-24-2021).toLocalDate());
        cashExceptionDTO.setEndDate(new Date(11-23-2021).toLocalDate());
        cashExceptionDTO.setEquipId("123");
        cashExceptionDTO.setEquipInit("ABC");

        headers.put("extensionschema", null);
        List<String> customerList = new ArrayList<>();
        customerList.add("AMERICAN FIBRE SUPPLY");

        when(custInfoRepo.checkByCustomerNameAndCustomerPrimarySix(Mockito.anyString(), Mockito.anyString())).thenReturn(customerList);
        cashExceptionDTO.setTermId(1234L);
        when(terminalRepo.existsById(cashExceptionDTO.getTermId())).thenReturn(true);

//        when(cashExceptionRepository.existsByCustomerPrimarySix(Mockito.anyString())).thenReturn(false);

        when(custInfoRepo.existsByCustomerNumber(Mockito.anyString())).thenReturn(true);
        when(cashExceptionRepository.save(Mockito.any())).thenReturn(cashException);
        cashExceptionDTO.setTermId(null);
        cashExceptionDTO.setEquipType(null);
        cashExceptionDTO.setBnfCustomerNumber(null);
        cashExceptionDTO.setBnfPrimarySix(null);
        CashExceptionDTO added = cashExceptionServiceImpl.addCashException(cashExceptionDTO, headers);
        assertEquals(cashException.getCashExceptionId(), added.getCashExceptionId());
    }

    @Test
    void testUpdateCashException() throws SQLException {
        
        when(cashExceptionRepository.findById(Mockito.anyLong())).thenReturn(custOptional);
        cashException.setCustomerName(customerName);
        cashExceptionDTO.setCustomerName(customerName);
        cashException.setCustomerPrimarySix(customerPrimarySix);
        cashExceptionDTO.setCustomerPrimarySix(customerPrimarySix);

        cashException.setEquipType("C");
        cashExceptionDTO.setEquipType("C");
        cashException.setLoadedOrEmpty("L");
        cashExceptionDTO.setLoadedOrEmpty("L");

        cashExceptionDTO.setUversion("1");
        cashExceptionDTO.setBnfCustomerNumber("Test");
        cashExceptionDTO.setBnfPrimarySix("001234");
        cashExceptionDTO.setEffectiveDate(new Date(11-24-2021).toLocalDate());
        cashExceptionDTO.setEndDate(new Date(11-23-2021).toLocalDate());
        cashExceptionDTO.setEquipId("123");
        cashExceptionDTO.setEquipInit("ABC");

        headers.put("extensionschema", null);
        when(cashExceptionRepository.save(Mockito.any())).thenReturn(cashException);
        cashExceptionDTO.setTermId(null);
        cashExceptionDTO.setEquipType(null);
        cashExceptionDTO.setBnfCustomerNumber(null);
        cashExceptionDTO.setBnfPrimarySix(null);
        CashExceptionDTO added = cashExceptionServiceImpl.updateCashException(cashExceptionDTO, headers);
        assertEquals(cashException.getCashExceptionId(), added.getCashExceptionId());

    }

    @Test
	void testExceptionForUpdateCashException() throws SQLException{
		custOptional = Optional.empty();
		when(cashExceptionRepository.findById(Mockito.anyLong())).thenReturn(custOptional);
		assertThrows(NoRecordsFoundException.class,
				() -> cashExceptionServiceImpl.updateCashException(cashExceptionDTO, headers));

		custOptional = Optional.of(cashException);
		when(cashExceptionRepository.findById(Mockito.anyLong())).thenReturn(custOptional);
                
                cashExceptionDTO.setEffectiveDate(new Date(11-24-2021).toLocalDate());
                cashExceptionDTO.setCustomerName("Test");
                cashExceptionDTO.setCustomerPrimarySix("001234");

                cashExceptionDTO.setEquipType("Y");
                cashExceptionDTO.setTermId(null);

                InvalidDataException exceptionname = assertThrows(InvalidDataException.class,
                () -> when(cashExceptionServiceImpl.updateCashException(cashExceptionDTO,headers)));
                assertEquals(exceptionname.getMessage(),"Customer Name is not editable");

                cashException.setCustomerName(customerName);
                cashExceptionDTO.setCustomerName(customerName);
                
                InvalidDataException exceptionprimsix = assertThrows(InvalidDataException.class,
                () -> when(cashExceptionServiceImpl.updateCashException(cashExceptionDTO,headers)));
                assertEquals(exceptionprimsix.getMessage(),"Customer Primary Six is not editable");

                cashException.setCustomerPrimarySix(customerPrimarySix);
                cashExceptionDTO.setCustomerPrimarySix(customerPrimarySix);

                InvalidDataException exception4 = assertThrows(InvalidDataException.class,
                        () -> when(cashExceptionServiceImpl.updateCashException(cashExceptionDTO,headers)));
                assertEquals(exception4.getMessage(),"Must enter terminal for equipment specific exemption");

                cashExceptionDTO.setTermId(123L);
                InvalidDataException exception5 = assertThrows(InvalidDataException.class,
                        () -> when(cashExceptionServiceImpl.updateCashException(cashExceptionDTO,headers)));
                assertEquals(exception5.getMessage(),"Only accepted EQ_TP values for Cash Exception Rate are C= Container, T=Trailer, Z = Chassis");

                cashExceptionDTO.setEquipType(null);
                cashExceptionDTO.setTermId(null);
                cashExceptionDTO.setEquipId(null);
                cashExceptionDTO.setBnfCustomerNumber(null);
                cashExceptionDTO.setEffectiveDate(null);

                InvalidDataException exception3= assertThrows(InvalidDataException.class,
                        () -> when(cashExceptionServiceImpl.updateCashException(cashExceptionDTO,headers)));
                assertEquals(exception3.getMessage(),"Terminal id or Equipment id or Beneficial Customer Number & Effective Date atleast one of this should be present");

                cashExceptionDTO.setEffectiveDate(new Date(11-24-2021).toLocalDate());
                cashExceptionDTO.setLoadedOrEmpty("T");
                cashExceptionDTO.setTermId(123L);
                cashExceptionDTO.setEquipId("123");

                InvalidDataException exception6 = assertThrows(InvalidDataException.class,
                        () -> when(cashExceptionServiceImpl.updateCashException(cashExceptionDTO,headers)));
                assertEquals(exception6.getMessage(),"Only accepted Loaded Or Empty values for Cash Exception are L= Load, E=Empty");

                cashExceptionDTO.setLoadedOrEmpty(null);

                when(terminalRepo.existsByTerminalId(
                        cashExceptionDTO.getTermId())).thenReturn(false);
                NoRecordsFoundException exception7 = assertThrows(NoRecordsFoundException.class,
                        () -> when(cashExceptionServiceImpl.updateCashException(cashExceptionDTO,headers)));
                assertEquals(exception7.getMessage(),"No Terminal Found with this terminal id :" + cashExceptionDTO.getTermId());

                when(terminalRepo.existsByTerminalId(
                        cashExceptionDTO.getTermId())).thenReturn(true);

                cashExceptionDTO.setCustomerName(null);
                cashExceptionDTO.setBnfCustomerNumber("123");
                cashExceptionDTO.setBnfPrimarySix("123456");

                // when(custInfoRepo.existsByCustomerNumber(cashExceptionDTO.getBnfCustomerNumber())).thenReturn(false);
                // NoRecordsFoundException exception8 = assertThrows(NoRecordsFoundException.class,
                //         () -> when(cashExceptionServiceImpl.updateCashException(cashExceptionDTO,headers)));
                // assertEquals(exception8.getMessage(),"No Beneficial Customer Found with this Beneficial customer number  :" + cashExceptionDTO.getBnfCustomerNumber());

                cashExceptionDTO.setBnfCustomerNumber(null);
                cashExceptionDTO.setBnfPrimarySix(null);

                cashExceptionDTO.setEndDate(new Date(11-25-2021).toLocalDate());
                cashExceptionDTO.setEffectiveDate(null);

                InvalidDataException exception9 = assertThrows(InvalidDataException.class,
                        () -> when(cashExceptionServiceImpl.updateCashException(cashExceptionDTO,headers)));
                assertEquals(exception9.getMessage(),"Must have effective date before keying end date");

                cashExceptionDTO.setEffectiveDate(new Date(11-24-2021).toLocalDate());
                cashExceptionDTO.setUversion("!");
                headers.put("extensionschema", null);
                cashExceptionDTO.setEquipInit("abc");
                cashExceptionDTO.setEquipNbr(123);
                cashExceptionDTO.setCashExceptionId(cashExceptionRepository.SGK());

                InvalidDataException exception10 = assertThrows(InvalidDataException.class,
                        () -> when(cashExceptionServiceImpl.updateCashException(cashExceptionDTO,headers)));
                assertEquals(exception10.getMessage(),"Ending date is not greater than effective date");

                cashExceptionDTO.setEquipType("C");
                cashExceptionDTO.setBnfCustomerNumber("123");
                cashExceptionDTO.setBnfPrimarySix("123456");

        }

    @Test
    void testExceptionForAddCashException() throws SQLException{
        cashExceptionDTO.setEffectiveDate(new Date(11-24-2021).toLocalDate());
        cashExceptionDTO.setCustomerName("Test");
        cashExceptionDTO.setCustomerPrimarySix("001234");
        List<String> list = new ArrayList<>();
        when(custInfoRepo.checkByCustomerNameAndCustomerPrimarySix(Mockito.any(),Mockito.any())).thenReturn(list);
        InvalidDataException exception = assertThrows(InvalidDataException.class,
                () -> when(cashExceptionServiceImpl.addCashException(cashExceptionDTO,headers)));
        assertEquals(exception.getMessage(),"No Customer  Found with this customer name  :" + cashExceptionDTO.getCustomerName() + " And customer primary six : " + cashExceptionDTO.getCustomerPrimarySix());
        list.add("AMERICAN FIBRE SUPPLY");


        cashExceptionDTO.setEquipType("Y");
        cashExceptionDTO.setTermId(null);

        InvalidDataException exception4 = assertThrows(InvalidDataException.class,
                () -> when(cashExceptionServiceImpl.addCashException(cashExceptionDTO,headers)));
        assertEquals(exception4.getMessage(),"Must enter terminal for equipment specific exemption");

        cashExceptionDTO.setTermId(123L);
        InvalidDataException exception5 = assertThrows(InvalidDataException.class,
                () -> when(cashExceptionServiceImpl.addCashException(cashExceptionDTO,headers)));
        assertEquals(exception5.getMessage(),"Only accepted EQ_TP values for Cash Exception Rate are C= Container, T=Trailer, Z = Chassis");

        cashExceptionDTO.setEquipType(null);
        cashExceptionDTO.setTermId(null);
        cashExceptionDTO.setEquipId(null);
        cashExceptionDTO.setBnfCustomerNumber(null);
        cashExceptionDTO.setEffectiveDate(null);

        InvalidDataException exception3= assertThrows(InvalidDataException.class,
                () -> when(cashExceptionServiceImpl.addCashException(cashExceptionDTO,headers)));
        assertEquals(exception3.getMessage(),"Must atleast one of this  is required field terminal id or equipment id or Combination of Beneficial Customer- Benf Nr-Effective Date");

        cashExceptionDTO.setEffectiveDate(new Date(11-24-2021).toLocalDate());
        cashExceptionDTO.setLoadedOrEmpty("T");
        cashExceptionDTO.setTermId(123L);
        cashExceptionDTO.setEquipId("123");

        InvalidDataException exception6 = assertThrows(InvalidDataException.class,
                () -> when(cashExceptionServiceImpl.addCashException(cashExceptionDTO,headers)));
        assertEquals(exception6.getMessage(),"Only accepted LoadedOrEmpty values for Cash Exception are L= Load, E=Empty");

        cashExceptionDTO.setLoadedOrEmpty(null);

        when(terminalRepo.existsByTerminalId(
                cashExceptionDTO.getTermId())).thenReturn(false);
        NoRecordsFoundException exception7 = assertThrows(NoRecordsFoundException.class,
                () -> when(cashExceptionServiceImpl.addCashException(cashExceptionDTO,headers)));
        assertEquals(exception7.getMessage(),"No Terminal Found with this terminal id :" + cashExceptionDTO.getTermId());

        when(terminalRepo.existsByTerminalId(
                cashExceptionDTO.getTermId())).thenReturn(true);

        cashExceptionDTO.setCustomerName(null);
        cashExceptionDTO.setBnfCustomerNumber("123");
        cashExceptionDTO.setBnfPrimarySix("123456");

        when(custInfoRepo.existsByCustomerNumber(
                cashExceptionDTO.getBnfCustomerNumber())).thenReturn(false);
        NoRecordsFoundException exception8 = assertThrows(NoRecordsFoundException.class,
                () -> when(cashExceptionServiceImpl.addCashException(cashExceptionDTO,headers)));
        assertEquals(exception8.getMessage(),"No Beneficial Customer  Found with this Beneficial customer number  :" + cashExceptionDTO.getBnfCustomerNumber());

        cashExceptionDTO.setBnfCustomerNumber(null);
        cashExceptionDTO.setBnfPrimarySix(null);

        cashExceptionDTO.setEndDate(new Date(11-25-2021).toLocalDate());
        cashExceptionDTO.setEffectiveDate(null);

        InvalidDataException exception9 = assertThrows(InvalidDataException.class,
                () -> when(cashExceptionServiceImpl.addCashException(cashExceptionDTO,headers)));
        assertEquals(exception9.getMessage(),"Must have effective date before keying end date");

        cashExceptionDTO.setEffectiveDate(new Date(11-24-2021).toLocalDate());
        cashExceptionDTO.setUversion("!");
        headers.put("extensionschema", null);
        cashExceptionDTO.setEquipInit("abc");
        cashExceptionDTO.setEquipNbr(123);
        cashExceptionDTO.setCashExceptionId(cashExceptionRepository.SGK());

        InvalidDataException exception10 = assertThrows(InvalidDataException.class,
                () -> when(cashExceptionServiceImpl.addCashException(cashExceptionDTO,headers)));
        assertEquals(exception10.getMessage(),"Ending date is not greater than effective date");

        cashExceptionDTO.setEquipType("C");
        cashExceptionDTO.setBnfCustomerNumber("123");
        cashExceptionDTO.setBnfPrimarySix("123456");


    }

}
