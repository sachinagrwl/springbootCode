package com.nscorp.obis.services;

import com.nscorp.obis.domain.DamageArea;
import com.nscorp.obis.domain.DamageCompLoc;
import com.nscorp.obis.domain.DamageComponent;
import com.nscorp.obis.dto.DamageCompLocDTO;
import com.nscorp.obis.dto.mapper.DamageCompLocMapper;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.exception.RecordNotDeletedException;
import com.nscorp.obis.repository.DamageAreaRepository;
import com.nscorp.obis.repository.DamageCompLocRepository;
import com.nscorp.obis.repository.DamageComponentRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

public class DamageComponentLocationServiceTest {

    Map<String, String> header;

    DamageCompLoc damageCompLoc;
    List<DamageCompLoc> damageCompLocList;
    DamageCompLocDTO damageCompLocDto;

    @Mock
    DamageCompLocMapper mapper;


    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        damageCompLocList = new ArrayList<>();
        damageCompLoc = new DamageCompLoc();
        damageCompLoc.setCompLocCode("TES");
        damageCompLoc.setUpdateUserId("Test");
        damageCompLoc.setUversion("!");
        damageCompLocList.add(damageCompLoc);

        damageCompLocDto = new DamageCompLocDTO();
        damageCompLocDto.setCompLocCode("TES");
        damageCompLocDto.setJobCode(01);
        damageCompLocDto.setCompDscr("FLAT INN");
        damageCompLocDto.setAreaCd("T");
        damageCompLocDto.setAreaDscr("TEST");
        damageCompLocDto.setUversion("!");
        
        header = new HashMap<String, String>();
        header.put("userid", "Test");
        header.put("extensionschema", "Test");

    }

    @AfterEach
    void tearDown() throws Exception {

        damageCompLoc=null;
        damageCompLocDto=null;
        damageCompLocList=null;

    }

    @Mock
    private DamageCompLocRepository damageCompLocRepository;
    @Mock
    private DamageComponentRepository damageComponentRepository;
    @Mock
    private DamageAreaRepository damageAreaRepository;

    @InjectMocks
    private DamageComponentLocationServiceImpl damageCompLocService;

    @Test
    public void testGetDamageComponentLocation() throws Exception {
        when(damageCompLocRepository.findAll()).thenReturn(damageCompLocList);
        List<DamageCompLoc> result = damageCompLocService.getDamageComponentLocation(null, null);
        assertEquals(result, damageCompLocList);
    }


    @Test
    public void testGetDamageComponentLocationException() {
    	when(damageCompLocRepository.findByDamageComponent_JobCode(01)).thenReturn(new ArrayList<>());
        NoRecordsFoundException exception = assertThrows(NoRecordsFoundException.class, () -> {
            damageCompLocService.getDamageComponentLocation(01,null);
        });
        assertEquals("No Records Found!", exception.getMessage());
        verify(damageCompLocRepository).findByDamageComponent_JobCode(01);
    }

    @Test
    public void testDeleteDamageComponent(){
    	when(damageCompLocRepository.existsByCompLocCodeAndDamageComponent_JobCodeAndDamageArea_AreaCdAndUversion("TES",01,"T", "!")).thenReturn(true);
        when(damageCompLocRepository.findByCompLocCodeAndDamageComponent_JobCodeAndDamageArea_AreaCd("TES",01,"T")).thenReturn(damageCompLoc);
    	damageCompLocService.deleteDamageComponent(damageCompLocDto);
    }

    @Test
    public void testDeleteDamageComponentWhenRecordNotDeletedExceptionIsThrown() {

        when(damageCompLocRepository.existsByCompLocCodeAndDamageComponent_JobCodeAndDamageArea_AreaCdAndUversion("TES",01,"T", "!")).thenReturn(false);

        RecordNotDeletedException exception = assertThrows(RecordNotDeletedException.class, () -> {
            damageCompLocService.deleteDamageComponent(damageCompLocDto);
        });
        assertEquals("Record Not Found!", exception.getMessage());
    }

    @Test
    void testAddDamageCompLocation() throws SQLException {
        header.put("extensionschema", null);
        when(damageCompLocRepository.existsByCompLocCodeAndDamageComponent_JobCodeAndDamageArea_AreaCd(Mockito.anyString(), Mockito.anyInt(),Mockito.anyString())).thenReturn(false);
        DamageComponent damageComponent = new DamageComponent();
        damageComponent.setJobCode(123);
        when(damageComponentRepository.findByJobCode(Mockito.anyInt())).thenReturn(damageComponent);
        DamageArea damageArea = new DamageArea();
        damageArea.setAreaCd("T");
        when(damageAreaRepository.findByAreaCd(Mockito.anyString())).thenReturn(damageArea);
        when(mapper.damageCompLocDTOToDamageCompLoc(Mockito.any())).thenReturn(damageCompLoc);
        when(damageCompLocRepository.save(Mockito.any())).thenReturn(damageCompLoc);
        when(mapper.damageCompLocToDamageCompLocDTO(Mockito.any())).thenReturn(damageCompLocDto);
        damageCompLocDto = damageCompLocService.addDamageCompLocation(damageCompLocDto,header);
        assertEquals(damageCompLoc.getCompLocCode(), damageCompLocDto.getCompLocCode());
    }

    @Test
    void testUpdateDamageCompLocation() throws SQLException {
        header.put("extensionschema", null);
        when(damageCompLocRepository.findByCompLocCodeAndDamageComponent_JobCodeAndDamageArea_AreaCd(Mockito.anyString(), Mockito.anyInt(),Mockito.anyString())).thenReturn(damageCompLoc);
        when(damageCompLocRepository.save(Mockito.any())).thenReturn(damageCompLoc);
        when(mapper.damageCompLocToDamageCompLocDTO(Mockito.any())).thenReturn(damageCompLocDto);
        damageCompLocDto = damageCompLocService.updateDamageCompLocation(damageCompLocDto,header);
        assertEquals(damageCompLoc.getCompLocCode(), damageCompLocDto.getCompLocCode());
    }

    @Test
    void testExceptionForAdd() throws SQLException{
        when(damageCompLocRepository.existsByCompLocCodeAndDamageComponent_JobCodeAndDamageArea_AreaCd(Mockito.anyString(), Mockito.anyInt(),Mockito.anyString())).thenReturn(true);
        assertThrows(RecordAlreadyExistsException.class,
                () -> damageCompLocService.addDamageCompLocation(damageCompLocDto, header));

        when(damageCompLocRepository.existsByCompLocCodeAndDamageComponent_JobCodeAndDamageArea_AreaCd(Mockito.anyString(), Mockito.anyInt(),Mockito.anyString())).thenReturn(false);

        when(damageComponentRepository.findByJobCode(Mockito.anyInt())).thenReturn(null);
        NoRecordsFoundException exception1 = assertThrows(NoRecordsFoundException.class,
                () -> when(damageCompLocService.addDamageCompLocation(damageCompLocDto,header)));
        assertEquals(exception1.getMessage(),"No Damage Component record found for given JobCode");

        when(damageComponentRepository.findByJobCode(Mockito.anyInt())).thenReturn(new DamageComponent());

        when(damageAreaRepository.findByAreaCd(Mockito.anyString())).thenReturn(null);
        NoRecordsFoundException exception2 = assertThrows(NoRecordsFoundException.class,
                () -> when(damageCompLocService.addDamageCompLocation(damageCompLocDto,header)));
        assertEquals(exception2.getMessage(),"No Damage Area record found for given AreaCd");


    }
    @Test
    void testExceptionForUpdate() throws SQLException{
        when(damageCompLocRepository.findByCompLocCodeAndDamageComponent_JobCodeAndDamageArea_AreaCd(Mockito.anyString(), Mockito.anyInt(),Mockito.anyString())).thenReturn(null);
        assertThrows(NoRecordsFoundException.class,
                () -> damageCompLocService.updateDamageCompLocation(damageCompLocDto, header));
    }


}
