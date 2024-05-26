package com.nscorp.obis.services;

import com.ibm.db2.jcc.b.c.f;
import com.nscorp.obis.domain.DamageArea;
import com.nscorp.obis.domain.DamageAreaComp;
import com.nscorp.obis.domain.DamageComponent;
import com.nscorp.obis.dto.DamageAreaComponentDTO;
import com.nscorp.obis.dto.mapper.DamageAreaComponentMapper;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.exception.RecordNotDeletedException;
import com.nscorp.obis.repository.DamageAreaCompRepository;
import com.nscorp.obis.repository.DamageAreaRepository;
import com.nscorp.obis.repository.DamageComponentRepository;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Sort;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class DamageAreaComponentServiceTest {
    @Mock
    DamageAreaCompRepository repository;

    @Mock
    DamageAreaRepository areaRepository;

    @Mock
    DamageComponentRepository componentRepository;

    @Mock
    DamageAreaComponentMapper mapper;
    @InjectMocks
    DamageAreaComponentServiceImpl damageAreaComponentService;

    Map<String, String> header;
    DamageAreaComp damageAreaComponent;
    DamageAreaComponentDTO damageAreaComponentDTO;

    List<DamageAreaComp> damageAreaComponentList;
    List<DamageAreaComponentDTO> damageAreaComponentDTOList;

    //Specification
    Map<String, String> headers;
    @BeforeAll
    static void setUpBeforeClass() throws Exception {
    }
    @AfterAll
    static void tearDownAfterClass() throws Exception {
    }
    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        damageAreaComponent = new DamageAreaComp();
        damageAreaComponent.setJobCode(1100);
        damageAreaComponent.setAreaCd("A");
        damageAreaComponent.setOrderCode("A");
        damageAreaComponent.setUversion("!");
        damageAreaComponent.setCreateUserId("Test");
        damageAreaComponent.setUpdateUserId("Test");

        damageAreaComponentDTO = new DamageAreaComponentDTO();
        damageAreaComponentDTO.setJobCode(1100);
        damageAreaComponentDTO.setAreaCd("A");
        damageAreaComponentDTO.setOrderCode("A");
        damageAreaComponentDTO.setUversion("!");
        damageAreaComponentDTO.setCreateUserId("Test");
        damageAreaComponentDTO.setUpdateUserId("Test");
        damageAreaComponentList = new ArrayList<>();
        damageAreaComponentDTOList = new ArrayList<>();
        damageAreaComponentList.add(damageAreaComponent);
        damageAreaComponentDTOList.add(damageAreaComponentDTO);

        header = new HashMap<String, String>();
        header.put("userid", "Test");
        header.put("extensionschema", "Test");

    }
    @AfterEach
    void tearDown() throws Exception{
        damageAreaComponent = null;
        damageAreaComponentDTO = null;
        damageAreaComponentDTOList =null;
        damageAreaComponentList = null;
    }

    @Test
    public void testGetDamageAreaComponent() throws Exception {
        when(repository.findAll((Sort) Mockito.any())).thenReturn(damageAreaComponentList);
        when(mapper.DamageAreaComponentToDamageAreaComponentDTO(Mockito.any())).thenReturn(damageAreaComponentDTO);
        List<DamageAreaComponentDTO> result = damageAreaComponentService.fetchDamageComponentSize(null,null);
        assertEquals(result, damageAreaComponentDTOList);

        when(repository.findByJobCodeAndAreaCdIgnoreCase(Mockito.anyInt(),Mockito.anyString(),Mockito.any())).thenReturn(damageAreaComponentList);
        when(mapper.DamageAreaComponentToDamageAreaComponentDTO(Mockito.any())).thenReturn(damageAreaComponentDTO);
        List<DamageAreaComponentDTO> result2 = damageAreaComponentService.fetchDamageComponentSize(1100,"A");
        assertEquals(result2, damageAreaComponentDTOList);

        when(repository.findByJobCode(Mockito.anyInt(),Mockito.any())).thenReturn(damageAreaComponentList);
        when(mapper.DamageAreaComponentToDamageAreaComponentDTO(Mockito.any())).thenReturn(damageAreaComponentDTO);
        List<DamageAreaComponentDTO> result3 = damageAreaComponentService.fetchDamageComponentSize(1100,null);
        assertEquals(result3, damageAreaComponentDTOList);

        when(repository.findByAreaCdIgnoreCase(Mockito.anyString(),Mockito.any())).thenReturn(damageAreaComponentList);
        when(mapper.DamageAreaComponentToDamageAreaComponentDTO(Mockito.any())).thenReturn(damageAreaComponentDTO);
        damageAreaComponentDTOList.forEach(dto->{
            DamageComponent damageComponent = new DamageComponent();
            damageComponent.setCompDscr("FLAT");
            when(componentRepository.findByJobCode(Mockito.anyInt())).thenReturn(damageComponent);
            dto.setCompDscr(damageComponent.getCompDscr());

            DamageArea area =new DamageArea();
            area.setAreaDscr("TEST");
            when(areaRepository.findByAreaCd(Mockito.anyString())).thenReturn(area);
            dto.setAreaDscr(area.getAreaDscr());
        });
        List<DamageAreaComponentDTO> result4 = damageAreaComponentService.fetchDamageComponentSize(null,"A");

        result4.forEach(dto->{
            dto.setCompDscr("FLAT");
            dto.setAreaDscr("TEST");
        });

        assertEquals(result4, damageAreaComponentDTOList);
        damageAreaComponentList = new ArrayList<>();
        when(repository.findAll((Sort) Mockito.any())).thenReturn(damageAreaComponentList);
        assertThrows(NoRecordsFoundException.class,
                () -> damageAreaComponentService.fetchDamageComponentSize(null,null));

    }


    @Test
    public void testDeleteDamageAreaComponent(){
        when(repository.findByAreaCdAndJobCode("A",1100)).thenReturn(damageAreaComponent);
        damageAreaComponentService.deleteDamageAreaComponent(damageAreaComponentDTO);
    }

    @Test
    public void testDeleteDamageAreaComponentWhenRecordNotDeletedExceptionIsThrown() {
        when(repository.findByAreaCdAndJobCode("A",1100)).thenReturn(null);
        RecordNotDeletedException exception = assertThrows(RecordNotDeletedException.class, () -> {
            damageAreaComponentService.deleteDamageAreaComponent(damageAreaComponentDTO);
        });
        assertEquals("Record Not Found!", exception.getMessage());
    }

      @Test
    void testAddDamageAreaComponent() throws SQLException {
        header.put("extensionschema", null);
        when(repository.existsByJobCodeAndAreaCdAndOrderCode(Mockito.any(),Mockito.any(),Mockito.any())).thenReturn(false);

        when(mapper.DamageAreaComponentDTOToDamageAreaComponent(Mockito.any())).thenReturn(damageAreaComponent);
        when(repository.save(Mockito.any())).thenReturn(damageAreaComponent);
        when(mapper.DamageAreaComponentToDamageAreaComponentDTO(Mockito.any())).thenReturn(damageAreaComponentDTO);
        damageAreaComponentDTO = damageAreaComponentService.addDamageAreaComponent(damageAreaComponentDTO, header);
        assertEquals(damageAreaComponentDTO.getJobCode(), damageAreaComponentDTO.getJobCode());
    }

    @Test
    void testUpdateDamageAreaComponent() throws SQLException {
        header.put("extensionschema", null);
        when(repository.findByAreaCdAndJobCode(Mockito.any(),Mockito.any())).thenReturn(damageAreaComponent);
        when(repository.save(Mockito.any())).thenReturn(damageAreaComponent);
        when(mapper.DamageAreaComponentToDamageAreaComponentDTO(Mockito.any())).thenReturn(damageAreaComponentDTO);
        damageAreaComponentDTO = damageAreaComponentService.updateDamageAreaComponent(damageAreaComponentDTO, header);
        assertEquals(damageAreaComponent.getJobCode(), damageAreaComponentDTO.getJobCode());
    }

    @Test
    void testExceptionForAdd() throws SQLException {
        when(repository.existsByJobCodeAndAreaCdAndOrderCode(Mockito.any(),Mockito.any(),Mockito.any())).thenReturn(true);
        RecordAlreadyExistsException exception =  assertThrows(RecordAlreadyExistsException.class,
                () -> damageAreaComponentService.addDamageAreaComponent(damageAreaComponentDTO, header));
        assertEquals(exception.getMessage(), "This area/order code already exists.");
    }

    @Test
    void testExceptionForUpdate() throws SQLException {
        when(repository.findByAreaCdAndJobCode(Mockito.any(),Mockito.any())).thenReturn(null);
        assertThrows(NoRecordsFoundException.class,
                () -> damageAreaComponentService.updateDamageAreaComponent(damageAreaComponentDTO, header));
    }
}
