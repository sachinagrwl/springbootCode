package com.nscorp.obis.services;

import com.nscorp.obis.domain.Block;
import com.nscorp.obis.domain.DamageComponent;
import com.nscorp.obis.dto.BlockDTO;
import com.nscorp.obis.dto.DamageComponentDTO;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.exception.RecordNotAddedException;
import com.nscorp.obis.exception.RecordNotDeletedException;
import com.nscorp.obis.repository.DamageComponentRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

public class DamageComponentServiceTest {

    Map<String, String> header;

    DamageComponent damageComponent;

    DamageComponent damageComponentAdded;

    List<DamageComponent> damageComponentList;

    List<DamageComponentDTO> damageComponentDtoList;

    DamageComponentDTO damageComponentDto;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        damageComponentList = new ArrayList<>();
        damageComponentDtoList = new ArrayList<>();
        damageComponent = new DamageComponent();
        damageComponent.setJobCode(1123);
        damageComponent.setReasonTp("M");
        damageComponent.setCompDscr("FLAT INN");
        damageComponent.setUpdateUserId("Test");
        damageComponent.setUversion("!");
        damageComponentList.add(damageComponent);

        damageComponentDto = new DamageComponentDTO();
        damageComponentDto.setJobCode(1123);
        damageComponentDto.setCInd("Y");
        damageComponentDto.setZInd("Y");
        damageComponentDto.setTInd("Y");
        damageComponentDto.setReasonTp("M");
        damageComponentDto.setCompDscr("FLAT INN");
        damageComponentDtoList.add(damageComponentDto);



        header = new HashMap<String, String>();
        header.put("userid", "Test");
        header.put("extensionschema", "Test");

    }

    @AfterEach
    void tearDown() throws Exception {

        damageComponent=null;
        damageComponentDtoList=null;
        damageComponentList=null;
        damageComponentDto=null;

    }

    @Mock
    private DamageComponentRepository damageComponentRepository;

    @InjectMocks
    private DamageComponentServiceImpl damageComponentService;

    @Test
    public void testGetAllDamageComponents() throws Exception {

        List<DamageComponent> damageComponentList = new ArrayList<>();
        damageComponentList.add(new DamageComponent());
        Mockito.when(damageComponentRepository.findAllByOrderByJobCode()).thenReturn(damageComponentList);

        List<DamageComponent> result = damageComponentService.getAllDamageComponents();

        assertNotNull(result);
        assertEquals(damageComponentList.size(), result.size());
    }


    @Test
    public void testGetDamageComponentsByJobCode() throws Exception {


        Mockito.when(damageComponentRepository.findByJobCode(1123)).thenReturn(damageComponent);

        DamageComponent result = damageComponentService.getDamageComponentsByJobCode(1123);

        assertNotNull(result);
        assertEquals(damageComponent.getJobCode(), result.getJobCode());
        assertEquals(damageComponent.getUpdateUserId(), result.getUpdateUserId());
    }

    @Test
    public void testGetDamageComponentsByJobCodeWhenNoRecordsFoundExceptionIsThrown() {

        when(damageComponentRepository.findByJobCode(anyInt())).thenReturn(null);

        NoRecordsFoundException exception = assertThrows(NoRecordsFoundException.class, () -> {
            damageComponentService.getDamageComponentsByJobCode(1123);
        });

        assertEquals("No DamageComponentList Found!", exception.getMessage());
        verify(damageComponentRepository).findByJobCode(1123);
    }

    @Test
    public void testDeleteDamageComponent() throws Exception {

        Mockito.when(damageComponentRepository.existsByJobCodeAndUversion(1123, "!")).thenReturn(true);

        damageComponentService.deleteDamageComponent(damageComponent);

        Mockito.verify(damageComponentRepository, Mockito.times(1)).deleteByJobCode(1123);
    }

    @Test
    public void testDeleteDamageComponentWhenRecordNotDeletedExceptionIsThrown() {

        when(damageComponentRepository.existsByJobCodeAndUversion(1123, "!")).thenReturn(false);

        RecordNotDeletedException exception = assertThrows(RecordNotDeletedException.class, () -> {
            damageComponentService.deleteDamageComponent(damageComponent);
        });

        assertEquals("1123 Record Not Found!", exception.getMessage());
        verify(damageComponentRepository).existsByJobCodeAndUversion(1123, "!");
        verifyNoMoreInteractions(damageComponentRepository);
    }

    @Test
    void testAddDamageComponent() {
        when(damageComponentRepository.existsByJobCode(Mockito.any())).thenReturn(false);
//        damageComponent.setcInd("Y");
        when(damageComponentRepository.save(Mockito.any())).thenReturn(damageComponent);
        damageComponentAdded = damageComponentService.insertDamageComponent(damageComponent, header);
        assertEquals(damageComponentAdded,damageComponent);
    }

    @Test
    void testDamageComponentRecordAlreadyExistsException() {
        when(damageComponentRepository.existsByJobCode(Mockito.any())).thenReturn(true);
        RecordAlreadyExistsException exception = assertThrows(RecordAlreadyExistsException.class,
                () -> when(damageComponentService.insertDamageComponent(damageComponent, header)));
        assertEquals("Record with Job Code Already Exists!", exception.getMessage());

    }


    @Test
    void testDamageComponentRecordNotAddedExceptionException() {
        when(damageComponentRepository.existsByJobCode(Mockito.any())).thenReturn(false);
//        damageComponent.setcInd(null);
//        damageComponent.setzInd(null);
//        damageComponent.settInd(null);
        RecordNotAddedException exception = assertThrows(RecordNotAddedException.class,
                () -> when(damageComponentService.insertDamageComponent(damageComponent, header)));
        assertEquals("Please Enter Equipment Type!", exception.getMessage());


    }

    @Test
    void testUpdateDamageComponentRecordNotAddedExceptionException() {
        when(damageComponentRepository.existsByJobCode(Mockito.any())).thenReturn(true);
        when(damageComponentRepository.findByJobCode(Mockito.any())).thenReturn(damageComponent);
        damageComponent.setCInd(null);
        damageComponent.setZInd(null);
        damageComponent.setTInd(null);
        RecordNotAddedException exception1 = assertThrows(RecordNotAddedException.class,
                () -> when(damageComponentService.UpdateDamageComponent(damageComponent, header)));
        assertEquals("Please Enter Equipment Type!", exception1.getMessage());
    }

    @Test
    void testUpdateDamageComponentNoRecordFoundException(){
        when(damageComponentRepository.existsByJobCode(Mockito.any())).thenReturn(false);
        NoRecordsFoundException exception = assertThrows(NoRecordsFoundException.class,
                () -> when(damageComponentService.UpdateDamageComponent(damageComponent, header)));
        assertEquals("No Record Found!", exception.getMessage());
    }

    @Test
    void testUpdateDamageComponent(){
        when(damageComponentRepository.existsByJobCode(Mockito.any())).thenReturn(true);
        when(damageComponentRepository.findByJobCode(Mockito.any())).thenReturn(damageComponent);
        damageComponent.setCInd("Y");
        when(damageComponentRepository.save(Mockito.any())).thenReturn(damageComponent);
        DamageComponent addedDamage = damageComponentService.UpdateDamageComponent(damageComponent,header);
        assertEquals(addedDamage,damageComponent);
    }

    }
