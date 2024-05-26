package com.nscorp.obis.services;

import com.nscorp.obis.domain.TermFreeDay;
import com.nscorp.obis.dto.TermFreeDayDTO;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.exception.RecordNotAddedException;
import com.nscorp.obis.exception.RecordNotDeletedException;
import com.nscorp.obis.repository.GenericCodeUpdateRepository;
import com.nscorp.obis.repository.TermFreeDayRepository;
import com.nscorp.obis.repository.TerminalRepository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class TermFreeDayServiceTest {

    @InjectMocks
    TermFreeDayServiceImpl termFreeDayService;

    @Mock
    TermFreeDayRepository termFreeDayRepository;

    @Mock
    GenericCodeUpdateRepository genericCodeRepo;
    
    @Mock
    TerminalRepository terminalRepo;

    TermFreeDay termFreeDay;
    TermFreeDayDTO termFreeDayDTO;
    List<TermFreeDay> termFreeDayList;
    List<TermFreeDayDTO> termFreeDayDTOList;

    List<TermFreeDay> listObject = new ArrayList<>();

    Map<String, String> header;
    List<Long> termId = new ArrayList<>();
    
    LocalDate closeDate;
    String closeCode;
    LocalTime closeFromTime;

    List<String> reasonDesc;

    @BeforeEach
    void setUp() throws Exception{
        MockitoAnnotations.openMocks(this);
        reasonDesc = new ArrayList<>();
        termFreeDay = new TermFreeDay();
        termFreeDay.setTermId(1234L);
        termFreeDay.setCloseDate(LocalDate.of(2022, 01, 01));
        termFreeDay.setCloseFromTime(LocalTime.of(00,01));
        termFreeDay.setCloseRsnCd("SNO");
        termFreeDay.setCloseRsnDesc("SNOW");
        termFreeDay.setCloseToTime(LocalTime.of(23,59));
        termFreeDay.setFreeDay("Y");
        termFreeDayList = new ArrayList<>();
        termFreeDayList.add(termFreeDay);

        termFreeDayDTO = new TermFreeDayDTO();
        termFreeDayDTO.setTermId(1234L);
        termFreeDayDTO.setCloseDate(LocalDate.of(2022, 01, 01));
        termFreeDayDTO.setCloseFromTime(LocalTime.of(00,01));
        termFreeDayDTO.setCloseRsnCd("SNO");
        termFreeDayDTO.setCloseRsnDesc("SNOW");
        termFreeDayDTO.setCloseToTime(LocalTime.of(23,59));
        termFreeDayDTO.setFreeDay("Y");
        termFreeDayDTOList = new ArrayList<>();
        termFreeDayDTOList.add(termFreeDayDTO);
        termId.add(1234L);
        closeDate = LocalDate.of(2022,01,01);
        closeCode = "SNO";
        
        listObject.add(termFreeDay);

        header = new HashMap<String, String>();
        header.put("userid", "Test");
        header.put("extensionschema", "Test");
    }

    @AfterEach
    void tearDown() throws Exception {
        termFreeDay = null;
        termFreeDayDTO = null;
        termFreeDayList = null;
        termFreeDayDTOList = null;
    }

    @Test
    void getAllFreeDays() {
        when(termFreeDayRepository.findAll(termId,closeDate,closeCode,closeFromTime, "hasValues")).thenReturn(listObject);
        List<TermFreeDay> getTermFreeDay = termFreeDayService.getAllFreeDays(termId,closeDate,closeCode, closeFromTime);
        assertEquals(getTermFreeDay, termFreeDayList);
    }

    @Test
    void getAllReasonDesc() {
        when(termFreeDayRepository.findByDistinctReasonDesc()).thenReturn(reasonDesc);
        List<String> getAllReasonDesc = termFreeDayService.getAllReasonDesc();
        assertEquals(getAllReasonDesc, reasonDesc);
    }

    @Test
    void testgetAllFreeDaysException(){
        listObject = new ArrayList<>();
    	
    	when(termFreeDayRepository.findAll(termId, closeDate, closeCode, closeFromTime, "hasValues")).thenReturn(listObject);
        NoRecordsFoundException exception = assertThrows(NoRecordsFoundException.class,
                () -> when(termFreeDayService.getAllFreeDays(termId,closeDate,closeCode, closeFromTime)));
        Assertions.assertEquals("No Record Found under this search!", exception.getMessage());
    }
    
    @Test
    void testAddTermDayFreeDayRecordNotAddedException() {

    	termFreeDay.setFreeDayAllowance(null);
        
        assertNull(termFreeDay.getFreeDayAllowance());
        when(terminalRepo.existsByTerminalId(1234L)).thenReturn(true);       
        when(genericCodeRepo.existsByGenericTableAndGenericTableCodeIgnoreCase("CLOSERSN", termFreeDay.getCloseRsnCd())).thenReturn(true);
        RecordNotAddedException exception = assertThrows(RecordNotAddedException.class,
                () -> when(termFreeDayService.addTermDay(termFreeDay, header)));
        assertEquals("'Free Day' value must be either 'Y' or 'N'", exception.getMessage());

    }
    
    @Test
    void testAddTermDayCloseFromTimeRecordNotAddedException() {

    	LocalTime closeFromTime = LocalTime.of(00,00);
        
    	termFreeDay.setCloseFromTime(closeFromTime);
        
        assertNotNull(termFreeDay.getCloseFromTime());
        when(terminalRepo.existsByTerminalId(1234L)).thenReturn(true);
        when(genericCodeRepo.existsByGenericTableAndGenericTableCodeIgnoreCase("CLOSERSN", termFreeDay.getCloseRsnCd())).thenReturn(true);
        RecordNotAddedException exception = assertThrows(RecordNotAddedException.class,
                () -> when(termFreeDayService.addTermDay(termFreeDay, header)));
        assertEquals("The Close from time should be greater than or equal to 00:01:00", exception.getMessage());

    }
    
    @Test
    void testAddTermDayCloseToTimeRecordNotAddedException() {
    	
    	LocalTime  closeFromTime = LocalTime.of(23, 55);
    	LocalTime closeToTime = LocalTime.of(23, 59);
    	
    	termFreeDay.setCloseToTime(closeFromTime);
    	termFreeDay.setCloseFromTime(closeToTime);
        
        assertNotNull(termFreeDay.getCloseToTime());
        when(terminalRepo.existsByTerminalId(1234L)).thenReturn(true);
        when(genericCodeRepo.existsByGenericTableAndGenericTableCodeIgnoreCase("CLOSERSN", termFreeDay.getCloseRsnCd())).thenReturn(true);
        RecordNotAddedException exception = assertThrows(RecordNotAddedException.class,
                () -> when(termFreeDayService.addTermDay(termFreeDay, header)));
        assertEquals("Time Reopened should be greater than Time Closed", exception.getMessage());

    }
    
    @Test
    void testAddTermDay() {

    	when(terminalRepo.existsByTerminalId(1234L)).thenReturn(true);
        when(termFreeDayRepository.existsByTermIdAndCloseDateAndCloseFromTime(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(false);
        when(genericCodeRepo.existsByGenericTableAndGenericTableCodeIgnoreCase("CLOSERSN", termFreeDay.getCloseRsnCd())).thenReturn(true);
        when(termFreeDayRepository.save(Mockito.any())).thenReturn(termFreeDay);
        TermFreeDay addedTerm = termFreeDayService.addTermDay(termFreeDay, header);
        assertNotNull(addedTerm);

    }

//    @Test
//    void testAddTermDayException() {
//
//    	when(terminalRepo.existsByTerminalId(12160629731866L)).thenReturn(true);
//        when(termFreeDayRepository.existsByTermIdAndCloseDateAndCloseFromTime(Mockito.any(),Mockito.any(),Mockito.any())).thenReturn(true);
//
//        RecordAlreadyExistsException exception = assertThrows(RecordAlreadyExistsException.class,
//                () -> when(termFreeDayService
//                        .addTermDay(termFreeDay, header)));
//        assertEquals("Record already exists under Terminal Name: APPLIANCE PARK, CloseDate: 2022-01-01, TimeClosed: 00:01", exception.getMessage());
//
//    }

    @Test
    void testAddTermDayRecordNotAddedException() {

        when(termFreeDayRepository.existsByTermIdAndCloseDateAndCloseFromTime(termFreeDay.getTermId(),termFreeDay.getCloseDate(), termFreeDay.getCloseFromTime())).thenReturn(false);
        when(genericCodeRepo.existsByGenericTableAndGenericTableCodeAndGenericShortDescriptionIgnoreCase("CLOSERSN", termFreeDay.getCloseRsnCd(), termFreeDay.getCloseRsnDesc())).thenReturn(true);
        RecordNotAddedException exception = assertThrows(RecordNotAddedException.class,
                () -> when(termFreeDayService
                        .addTermDay(termFreeDay, header)));
        assertEquals("Terminal Id: 1234 Not Found!", exception.getMessage());

    }
    
    @Test
    void testAddTermDayCloseCodeAndDescriptionNoRecordsFoundException() {

        termFreeDay.setCloseRsnCd(null);
        assertNull(termFreeDay.getCloseRsnCd());
        assertNotNull(termFreeDay.getCloseRsnDesc());
        when(terminalRepo.existsByTerminalId(1234L)).thenReturn(true);
        when(genericCodeRepo.existsByGenericTableAndGenericTableCodeIgnoreCase("CLOSERSN", termFreeDay.getCloseRsnCd())).thenReturn(false);
        
        NoRecordsFoundException exception = assertThrows(NoRecordsFoundException.class,
                () -> when(termFreeDayService.addTermDay(termFreeDay, header)));
        assertEquals("Close Code null is not available", exception.getMessage());

    }

    @Test
    void testAddTermDayCloseCodeNoRecordsFoundException() {

        termFreeDay.setCloseRsnCd(null);
        termFreeDay.setCloseRsnDesc(null);
        assertNull(termFreeDay.getCloseRsnCd(), termFreeDay.getCloseRsnDesc());
        when(terminalRepo.existsByTerminalId(1234L)).thenReturn(true);
        when(genericCodeRepo.existsByGenericTableAndGenericTableCodeIgnoreCase("CLOSERSN", termFreeDay.getCloseRsnCd())).thenReturn(false);
        NoRecordsFoundException exception = assertThrows(NoRecordsFoundException.class,
                () -> when(termFreeDayService.addTermDay(termFreeDay, header)));
        assertEquals("Close Code null is not available", exception.getMessage());

    }
    
    @Test
    void testAddFreeDayException() {
    	termFreeDay.setFreeDay("h");
    	
    	when(terminalRepo.existsByTerminalId(1234L)).thenReturn(false);
        when(termFreeDayRepository.existsByTermIdAndCloseDateAndCloseFromTime(Mockito.any(),Mockito.any(),Mockito.any())).thenReturn(false);

        RecordNotAddedException exception = assertThrows(RecordNotAddedException.class,
                () -> when(termFreeDayService
                        .addTermDay(termFreeDay, header)));
        assertEquals("Terminal Id: 1234 Not Found!", exception.getMessage());

    }

    @Test
    void testUpdateTermDay() {

        when(termFreeDayRepository.existsByTermIdAndCloseDateAndCloseFromTime(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(true);
        when(termFreeDayRepository.findByTermIdAndCloseDateAndCloseFromTime(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(termFreeDay);
        when(genericCodeRepo.existsByGenericTableAndGenericTableCodeIgnoreCase("CLOSERSN", termFreeDay.getCloseRsnCd())).thenReturn(true);
        when(termFreeDayRepository.save(termFreeDay)).thenReturn(termFreeDay);
        TermFreeDay updatedTerm = termFreeDayService.updateTermDay(termFreeDay, header);
        assertEquals(updatedTerm, termFreeDay);

    }

    @Test
    void testUpdateTermDayNoRecordsFoundException() {

        NoRecordsFoundException exception = assertThrows(NoRecordsFoundException.class,
                () -> when(termFreeDayService.updateTermDay(termFreeDay, header)));
        assertEquals("No record Found for TermId: 1234, CloseDate: 2022-01-01, TimeClosed: 00:01", exception.getMessage());

    }

    @Test
    void testDeleteTermFreeDay() {

        when(termFreeDayRepository.existsByTermIdAndCloseDateAndCloseFromTime(termFreeDay.getTermId(), termFreeDay.getCloseDate(), termFreeDay.getCloseFromTime())).thenReturn(true);
        termFreeDayService.deleteTermFreeDay(termFreeDay);

    }

    @Test
    void testDeleteTermFreeDayException() {

        RecordNotDeletedException exception = assertThrows(RecordNotDeletedException.class,
                () -> termFreeDayService.deleteTermFreeDay(termFreeDay));
        assertEquals("Record with TermId: 1234 and Closed Date: 2022-01-01 and Time Closed: 00:01 Record Not Found!", exception.getMessage());

    }

}