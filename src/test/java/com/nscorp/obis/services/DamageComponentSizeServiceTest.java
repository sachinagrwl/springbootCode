package com.nscorp.obis.services;

import com.nscorp.obis.common.SpecificationGenerator;
import com.nscorp.obis.domain.DamageComponentReason;
import com.nscorp.obis.domain.DamageComponentSize;
import com.nscorp.obis.dto.DamageComponentReasonDTO;
import com.nscorp.obis.dto.DamageComponentSizeDTO;
import com.nscorp.obis.dto.mapper.DamageComponentSizeMapper;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.repository.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.jpa.domain.Specification;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

public class DamageComponentSizeServiceTest {

    Map<String, String> header;

    DamageComponentSize damageComponentSize;
    List<DamageComponentSize> damageComponentSizeList;
    DamageComponentSizeDTO damageComponentSizeDTO;

    List<DamageComponentSizeDTO> damageComponentSizeDTOList;

    Specification<DamageComponentSize> specification;

    @Mock
    DamageComponentSizeMapper mapper;

    @Mock
	SpecificationGenerator specificationGenerator;

    @Mock
    private DamageCompSizeRepository damageComponentSizeRepository;

    @Mock
    DamageComponentReasonRepository damageComponentReasonRepository;

    @InjectMocks
    private DamageComponentSizeServiceImpl damageComponentSizeService;

    Integer jobCode = 1103;
	Integer aarWhyMadeCode = 11;
	Long componentSizeCode = 1234L;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        damageComponentSize = new DamageComponentSize();
        damageComponentSize.setJobCode(1111);
        damageComponentSize.setComponentSizeCode(111L);
        damageComponentSize.setAarWhyMadeCode(111);
        damageComponentSize.setUversion("!");
        damageComponentSize.setCreateUserId("Test");
        damageComponentSize.setUpdateUserId("Test");

        damageComponentSizeList = new ArrayList<>();

        damageComponentSizeDTO = new DamageComponentSizeDTO();
        damageComponentSizeDTO.setJobCode(1111);
        damageComponentSizeDTO.setComponentSizeCode(111L);
        damageComponentSizeDTO.setAarWhyMadeCode(111);
        damageComponentSizeDTO.setUversion("!");
        damageComponentSizeDTO.setCreateUserId("Test");
        damageComponentSizeDTO.setUpdateUserId("Test");

        damageComponentSizeDTOList = new ArrayList<>();

        damageComponentSizeList.add(damageComponentSize);
        damageComponentSizeDTOList.add(damageComponentSizeDTO);

        header = new HashMap<String, String>();
        header.put("userid", "Test");
        header.put("extensionschema", "Test");

    }

    @AfterEach
    void tearDown() throws Exception {

        damageComponentSize = null;
        damageComponentSizeDTO = null;
        damageComponentSizeList = null;
        damageComponentSizeDTOList = null;

    }

    @Test
	void testGetDamageComponentSizes() {
		when(specificationGenerator.damageComponentSizeSpecification(Mockito.eq(jobCode), Mockito.eq(aarWhyMadeCode),
				Mockito.eq(componentSizeCode))).thenReturn(specification);
		when(damageComponentSizeRepository.findAll(specification)).thenReturn(damageComponentSizeList);
		when(mapper.damageComponentSizeToDamageComponentSizeDTO(Mockito.any())).thenReturn(damageComponentSizeDTO);
		List<DamageComponentSizeDTO> response = damageComponentSizeService.fetchDamageComponentSize(jobCode, aarWhyMadeCode, componentSizeCode);
		assertEquals(damageComponentSizeDTOList, response);
		damageComponentSizeList = new ArrayList<>();
		when(damageComponentSizeRepository.findAll(specification)).thenReturn(damageComponentSizeList);
		assertThrows(NoRecordsFoundException.class,
				() -> damageComponentSizeService.fetchDamageComponentSize(jobCode, aarWhyMadeCode, componentSizeCode));
	}

	@Test
	void testDeleteDamageComponentSize() {
		when(damageComponentSizeRepository.findByJobCodeAndAarWhyMadeCodeAndComponentSizeCode(Mockito.any(), Mockito.any(), Mockito.any()))
				.thenReturn(damageComponentSize);
		doNothing().when(damageComponentSizeRepository).delete(Mockito.any());
		when(mapper.damageComponentSizeToDamageComponentSizeDTO(Mockito.any())).thenReturn(damageComponentSizeDTO);
		DamageComponentSizeDTO response = damageComponentSizeService.deleteDamageComponentSize(damageComponentSizeDTO);
		assertEquals(damageComponentSizeDTO , response);
		when(damageComponentSizeRepository.findByJobCodeAndAarWhyMadeCodeAndComponentSizeCode(Mockito.any(), Mockito.any(), Mockito.any()))
				.thenReturn(null);
		assertThrows(NoRecordsFoundException.class, () -> damageComponentSizeService.deleteDamageComponentSize(damageComponentSizeDTO));
	}

    @Test
    void testAddDamageComponentSize() throws SQLException {
        header.put("extensionschema", null);
        when(damageComponentSizeRepository.existsByJobCodeAndAarWhyMadeCodeAndComponentSizeCode(Mockito.anyInt(),Mockito.anyInt(),Mockito.anyLong())).thenReturn(false);
        when(damageComponentReasonRepository.existsByJobCodeAndAarWhyMadeCodeAndSizeRequired(Mockito.anyInt(),Mockito.anyInt(),Mockito.anyString())).thenReturn(true);

        when(mapper.damageComponentSizeDTOToDamageComponentSize(Mockito.any())).thenReturn(damageComponentSize);
        when(damageComponentSizeRepository.save(Mockito.any())).thenReturn(damageComponentSize);
        when(mapper.damageComponentSizeToDamageComponentSizeDTO(Mockito.any())).thenReturn(damageComponentSizeDTO);
        damageComponentSizeDTO = damageComponentSizeService.insertDamageComponentSize(damageComponentSizeDTO, header);
        assertEquals(damageComponentSizeDTO.getComponentSizeCode(), damageComponentSizeDTO.getComponentSizeCode());
    }

    @Test
    void testUpdateDamageComponentSize() throws SQLException {
        header.put("extensionschema", null);
        when(damageComponentSizeRepository.findByJobCodeAndAarWhyMadeCodeAndComponentSizeCode(Mockito.anyInt(),Mockito.anyInt(),Mockito.anyLong())).thenReturn(damageComponentSize);
        when(damageComponentSizeRepository.save(Mockito.any())).thenReturn(damageComponentSize);
        when(mapper.damageComponentSizeToDamageComponentSizeDTO(Mockito.any())).thenReturn(damageComponentSizeDTO);
        damageComponentSizeDTO = damageComponentSizeService.updateDamageCompSize(damageComponentSizeDTO, header);
        assertEquals(damageComponentSize.getComponentSizeCode(), damageComponentSizeDTO.getComponentSizeCode());
    }

    @Test
    void testExceptionForAdd() throws SQLException {
        when(damageComponentSizeRepository.existsByJobCodeAndAarWhyMadeCodeAndComponentSizeCode(Mockito.anyInt(),Mockito.anyInt(),Mockito.anyLong())).thenReturn(true);
        RecordAlreadyExistsException exception =  assertThrows(RecordAlreadyExistsException.class,
                () -> damageComponentSizeService.insertDamageComponentSize(damageComponentSizeDTO, header));
        assertEquals(exception.getMessage(), "Record already exists with given jobCode,aarWhyMadeCode and componentSizeCode.");

        when(damageComponentSizeRepository.existsByJobCodeAndAarWhyMadeCodeAndComponentSizeCode(Mockito.anyInt(),Mockito.anyInt(),Mockito.anyLong())).thenReturn(false);

        when(damageComponentReasonRepository.existsByJobCodeAndAarWhyMadeCodeAndSizeRequired(Mockito.anyInt(),Mockito.anyInt(),Mockito.anyString())).thenReturn(false);
        NoRecordsFoundException exception1 = assertThrows(NoRecordsFoundException.class,
                () -> when(damageComponentSizeService.insertDamageComponentSize(damageComponentSizeDTO, header)));
        assertEquals(exception1.getMessage(), "No damage component reason found for given JobCode and AarWhyMadeCode");
        when(damageComponentReasonRepository.existsByJobCodeAndAarWhyMadeCodeAndSizeRequired(Mockito.anyInt(),Mockito.anyInt(),Mockito.anyString())).thenReturn(true);

    }
//
    @Test
    void testExceptionForUpdate() throws SQLException {
        when(damageComponentSizeRepository.findByJobCodeAndAarWhyMadeCodeAndComponentSizeCode(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyLong())).thenReturn(null);
        assertThrows(NoRecordsFoundException.class,
                () -> damageComponentSizeService.updateDamageCompSize(damageComponentSizeDTO, header));
    }
}
