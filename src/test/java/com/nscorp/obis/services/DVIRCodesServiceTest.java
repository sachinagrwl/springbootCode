package com.nscorp.obis.services;

import com.nscorp.obis.domain.DVIRCodes;
import com.nscorp.obis.dto.DVIRCodesDTO;
import com.nscorp.obis.dto.mapper.DVIRCodesMapper;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.exception.RecordNotDeletedException;
import com.nscorp.obis.repository.DVIRCodesRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.sql.SQLException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class DVIRCodesServiceTest {

    @Mock
    DVIRCodesRepository dvirCodesRepository;
    @InjectMocks
    DVIRCodesServiceImpl dvirCodesServiceImpl;
    DVIRCodes dvirCodes;
    Optional<DVIRCodes> dvirCode;

    List<DVIRCodes> dvirCodesList;
    DVIRCodesDTO dvirCodesDTO;
    List<DVIRCodesDTO> dvirCodesDTOList;
    Map<String, String> headers;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        dvirCodes = new DVIRCodes();
        dvirCodes.setDvirCd("abcd");
        dvirCodes.setDvirDesc("qwe");
        dvirCodes.setDvirHHDesc("qwer");
        dvirCodes.setDisplayCd("sd");
        dvirCodes.setUversion("a");

        dvirCodesList = new ArrayList<>();
        dvirCodesList.add(dvirCodes);
       
        dvirCodesDTO = new DVIRCodesDTO();
        dvirCodesDTOList = new ArrayList<>();
        dvirCodesDTOList.add(dvirCodesDTO);
        headers = new HashMap<>();
        headers.put("userid", "test");
        headers.put("extensionschema", "test");
    }

    @AfterEach
    void tearDown() throws Exception {
        dvirCodes = null;
        dvirCodesList = null;
        dvirCodesDTO = null;
        dvirCodesDTOList = null;
    }

    @Test
    void testGetAllDVIRCodes() {
        when(dvirCodesRepository.findAll()).thenReturn(dvirCodesList);
        List<DVIRCodes> result = dvirCodesServiceImpl.getAllDVIRCodes();
		assertEquals(result,dvirCodesList);

    }

    @Test
    void testGetAllDVIRCodesNoRecordsFound() {
    	NoRecordsFoundException exception = assertThrows(NoRecordsFoundException.class,
                () -> when(dvirCodesServiceImpl.getAllDVIRCodes()));
        assertEquals("No DVIRCodes Found!", exception.getMessage());
    }

    @Test
    void testDeleteDVIRCodes() {
        when(dvirCodesRepository.existsByDvirCd(Mockito.any())).thenReturn(true);
        when(dvirCodesRepository.findByDvirCd(Mockito.any())).thenReturn(dvirCodesList);
        dvirCodesServiceImpl.deleteDVIRCodes(dvirCodes);
    }
    @Test
    void testDeleteDVIRCodesRecordNotDeletedException() {
        when(dvirCodesRepository.existsByDvirCd(Mockito.any())).thenReturn(false);
        RecordNotDeletedException exe = assertThrows(RecordNotDeletedException.class,
                () -> dvirCodesServiceImpl.deleteDVIRCodes(dvirCodes));
        assertEquals("No records found", exe.getMessage());
    }
    
    @Test
    void testUpdateDVIRCodes() {
        when(dvirCodesRepository.existsByDvirCdAndUversion(Mockito.any(), Mockito.any())).thenReturn(true);
        when(dvirCodesRepository.findById(Mockito.any())).thenReturn(Optional.ofNullable((dvirCodes)));
        DVIRCodes result = dvirCodesServiceImpl.updateDvirCodes(dvirCodes, headers);
		assertEquals(result,dvirCodes);

    }
    @Test
    void testUpdateDVIRCodesNoRecordsFound() {
    	NoRecordsFoundException exception = assertThrows(NoRecordsFoundException.class,
                () -> when(dvirCodesServiceImpl.updateDvirCodes(dvirCodes, headers)));
        assertEquals("No Record Found", exception.getMessage());
    }

    @Test
    void testAddDVIRCodes(){
        when(dvirCodesRepository.existsByDvirCd(Mockito.any())).thenReturn(false);
        DVIRCodes result = dvirCodesServiceImpl.addDvirCodes(dvirCodes, headers);
        assertNotNull(result);

    }

    @Test
    void testAddDVIRCodesExceptions() throws SQLException {

        when(dvirCodesRepository.existsByDvirCd(Mockito.any())).thenReturn(true);
        assertThrows(RecordAlreadyExistsException.class, ()->dvirCodesServiceImpl.addDvirCodes(dvirCodes, headers));

      //  when(dvirCodesRepository.existsByDvirCd(Mockito.any())).thenReturn(true);
        //assertThrows(NoRecordsFoundException.class, ()->dvirCodesServiceImpl.addDvirCodes(dvirCodes, headers));

    }

}
