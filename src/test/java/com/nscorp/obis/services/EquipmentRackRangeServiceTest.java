package com.nscorp.obis.services;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.nscorp.obis.domain.EquipmentRackRange;
import com.nscorp.obis.dto.EquipmentRackRangeDTO;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.exception.RecordNotAddedException;
import com.nscorp.obis.exception.RecordNotDeletedException;
import com.nscorp.obis.repository.EquipmentContRepository;
import com.nscorp.obis.repository.EquipmentRackRangeRepository;

public class EquipmentRackRangeServiceTest {

	@InjectMocks
	EquipmentRackRangeServiceImpl equiService;

	@Mock
	EquipmentRackRangeRepository equiRepository;

	@Mock
	EquipmentContRepository equipmentContRepository;

	EquipmentRackRange equi;
	EquipmentRackRange equi1;
	EquipmentRackRange equi2;
	EquipmentRackRangeDTO equiDto;
	List<EquipmentRackRange> equiList;
	List<EquipmentRackRange> equiList1;
	List<EquipmentRackRangeDTO> equiDtoList;

	Map<String, String> header;

	BigDecimal eqNrLow = new BigDecimal(1000);
	BigDecimal eqNrHigh = new BigDecimal(2000);

	@SuppressWarnings("deprecation")
	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		equi = new EquipmentRackRange();
		equi1 = new EquipmentRackRange();
		equi2 = new EquipmentRackRange();
		
		equi.setEquipInd(null);
		
		equi.setAarType(null);
		equi.setEquipRackRangeId(444L);
		equi.setEquipInit("AAAA");
		
		equi.setEquipLowNbr(eqNrLow);
		equi.setEquipHighNbr(eqNrHigh);
		equi.setEquipType("C");

equi2.setEquipInd(null);
		
		equi2.setAarType(null);
		equi2.setEquipRackRangeId(444L);
		equi2.setEquipInit("AAAA");
		
		equi2.setEquipLowNbr(BigDecimal.valueOf(1L));
		equi2.setEquipHighNbr(BigDecimal.valueOf(2L));
		equi2.setEquipType("C");
		
		
		equi1.setEquipInd(null);
		;
		equi1.setAarType(null);
		equi1.setEquipRackRangeId(444L);
		equi1.setEquipInit("AAAA");
		;
		equi1.setEquipLowNbr(eqNrLow);
		equi1.setEquipHighNbr(eqNrHigh);
		equi1.setEquipType("C");

		equiList = new ArrayList<>();
		equiList1 = new ArrayList<>();

		equiList.add(equi);
		equiList.add(equi2);
		equiList1.add(equi1);

		equiDto = new EquipmentRackRangeDTO();
		equiDto.setEquipInd(null);
		;
		equiDto.setAarType(null);
		equiDto.setEquipRackRangeId(444L);
		equiDto.setEquipInit("AAAA");
		;
		equiDto.setEquipLowNbr(1L);
		equiDto.setEquipHighNbr(2L);
		equiDto.setEquipType("C");

		equiDtoList = new ArrayList<>();
		equiDtoList.add(equiDto);

		header = new HashMap<String, String>();
		header.put("userid", "Test");
		header.put("extensionschema", "Test");
	}

	@AfterEach
	void tearDown() throws Exception {
		equi = null;
		equiDtoList = null;
		equiDto = null;
		equiList = null;
	}
	
	@Test
	void testUpdateEquipmentRackRange() {
		
		when(equiRepository.existsById(Mockito.any())).thenReturn(true);
		when(equiRepository.existsByEquipInit(Mockito.any())).thenReturn(true);
		when(equiRepository.findByEquipInit(Mockito.any())).thenReturn(equiList);
		when(equipmentContRepository.existsByContainerInit(Mockito.any())).thenReturn(true);
		when(equiRepository.save(Mockito.any())).thenReturn(equi);
		EquipmentRackRange updatedEquipmentRackRange = equiService.updateEquipmentRackRange(equi, header);
		
		equiList = new ArrayList<>();
		equiList.add(equi);
		updatedEquipmentRackRange = equiService.updateEquipmentRackRange(equi, header);
	
	}
	

	@Test
	void testGetAllEquipmentRackRange() {
		when(equiRepository.findAll()).thenReturn(equiList);
		List<EquipmentRackRange> allEqui = equiService.getAllTables();
		assertNotNull(allEqui);
	}

	@Test
	void testDeleteEquipmentRackRange() {
		when(equiRepository.existsById(Mockito.anyLong())).thenReturn(true);
		when(equiRepository.findByEquipRackRangeId(Mockito.any())).thenReturn(equiList);
		equiRepository.deleteAll(Mockito.any());
		equiService.deleteEquipmentRackRange(equi);
	}

	@Test
	void testRecordNotFoundExceptionDeleteEquipmentRackRange() {
		when(equiRepository.existsById(Mockito.anyLong())).thenReturn(false);
		RecordNotDeletedException exception = assertThrows(RecordNotDeletedException.class,
				() -> equiService.deleteEquipmentRackRange(equi));
		assertEquals("Record Not Found!", exception.getMessage());
	}

	@Test
	void testAddEqRackRange() {
		when(equiRepository.existsByEquipRackRangeId(Mockito.any())).thenReturn(false);
		when(equipmentContRepository.existsByContainerInit(Mockito.any())).thenReturn(true);
		when(equiRepository.save(Mockito.any())).thenReturn(equi);
		EquipmentRackRange addedOverrideWeight = equiService.addEquipmentRackRange(equi, header);
		assertEquals(addedOverrideWeight, equi);
	}

	@Test
	void testAddNoRecordFoundException() {
		NoRecordsFoundException exception = assertThrows(NoRecordsFoundException.class,
				() -> when(equiService.addEquipmentRackRange(equi, header)));
		assertEquals("EQ_INIT needs to be defined as a container first", exception.getMessage());

		when(equiRepository.existsById(Mockito.any())).thenReturn(true);
		when(equiRepository.existsByEquipInit(Mockito.any())).thenReturn(true);
		when(equipmentContRepository.existsByContainerInit(Mockito.any())).thenReturn(false);
		NoRecordsFoundException exception1 = assertThrows(NoRecordsFoundException.class,
				() -> when(equiService.updateEquipmentRackRange(equi, header)));
		assertEquals("EQ_INIT needs to be defined as a container first", exception1.getMessage());

	}

	@Test
	void testAddRecordAlreadyExistsException() {
		when(equiRepository.existsByEquipRackRangeId(Mockito.any())).thenReturn(true);
		RecordAlreadyExistsException exception = assertThrows(RecordAlreadyExistsException.class,
				() -> when(equiService.addEquipmentRackRange(equi, header)));
		assertEquals("Record with EquipRackRangeId Already Exists!", exception.getMessage());
	}

	@Test
	void testEqRackRecordNotAddedException() {
		equi.setEquipInit("");
		RecordNotAddedException exception = assertThrows(RecordNotAddedException.class,
				() -> when(equiService.addEquipmentRackRange(equi, header)));
		assertEquals("Equipment Init value should be present!", exception.getMessage());

		equi.setEquipInit("");
		when(equiRepository.existsById(Mockito.any())).thenReturn(true);
		RecordNotAddedException exception11 = assertThrows(RecordNotAddedException.class,
				() -> when(equiService.updateEquipmentRackRange(equi, header)));
		assertEquals("Equipment Init value should be present!", exception11.getMessage());

		equi.setEquipInit("SNLU");
		equi.setEquipLowNbr(BigDecimal.valueOf(50));
		equi.setEquipHighNbr(BigDecimal.valueOf(49));
		RecordNotAddedException exception1 = assertThrows(RecordNotAddedException.class,
				() -> when(equiService.addEquipmentRackRange(equi, header)));
		assertEquals("Equipment Low Number: 50 should be less than or equals to Equipment High Number: 49",
				exception1.getMessage());

		equi.setEquipInit("SNLU");
		equi.setEquipLowNbr(BigDecimal.valueOf(50));
		equi.setEquipHighNbr(BigDecimal.valueOf(49));
		RecordNotAddedException exception12 = assertThrows(RecordNotAddedException.class,
				() -> when(equiService.updateEquipmentRackRange(equi, header)));
		assertEquals("Equipment Low Number: 50 should be less than or equals to Equipment High Number: 49",
				exception12.getMessage());

		equiList = new ArrayList<>();
		equi.setEquipRackRangeId(1234L);
		equi.setEquipLowNbr(BigDecimal.valueOf(49));
		equi.setEquipHighNbr(BigDecimal.valueOf(50));
		equi.setEquipInit("AAAA");
		equiList.add(equi);
		equi1.setEquipLowNbr(BigDecimal.valueOf(48));
		equi1.setEquipHighNbr(BigDecimal.valueOf(51));
		when(equiRepository.existsByEquipInit(Mockito.any())).thenReturn(true);
		when(equiRepository.findByEquipInit(Mockito.any())).thenReturn(equiList);
		RecordNotAddedException exception5 = assertThrows(RecordNotAddedException.class,
				() -> when(equiService.addEquipmentRackRange(equi1, header)));
		assertEquals("Equipment Number Range are overlapping with existing records", exception5.getMessage());

		equiList = new ArrayList<>();
		equi.setEquipRackRangeId(1234L);
		equi.setEquipLowNbr(BigDecimal.valueOf(11));
		equi.setEquipHighNbr(BigDecimal.valueOf(19));
		equi.setEquipInit("AAAA");
		equiList.add(equi);
		equi1.setEquipLowNbr(BigDecimal.valueOf(10));
		equi1.setEquipHighNbr(BigDecimal.valueOf(20));
		when(equiRepository.existsByEquipInit(Mockito.any())).thenReturn(true);
		when(equiRepository.findByEquipInit(Mockito.any())).thenReturn(equiList);
		exception5 = assertThrows(RecordNotAddedException.class,
				() -> when(equiService.addEquipmentRackRange(equi1, header)));

		equiList = new ArrayList<>();
		equi.setEquipRackRangeId(1234L);
		equi.setEquipLowNbr(BigDecimal.valueOf(9));
		equi.setEquipHighNbr(BigDecimal.valueOf(21));
		equi.setEquipInit("AAAA");
		equiList.add(equi);
		equi1.setEquipLowNbr(BigDecimal.valueOf(10));
		equi1.setEquipHighNbr(BigDecimal.valueOf(20));
		when(equiRepository.existsByEquipInit(Mockito.any())).thenReturn(true);
		when(equiRepository.findByEquipInit(Mockito.any())).thenReturn(equiList);
		exception5 = assertThrows(RecordNotAddedException.class,
				() -> when(equiService.addEquipmentRackRange(equi1, header)));
		assertEquals("Equipment Number Range are overlapping with existing records", exception5.getMessage());

		equiList = new ArrayList<>();
		equi.setEquipRackRangeId(1234L);
		equi.setEquipLowNbr(BigDecimal.valueOf(9));
		equi.setEquipHighNbr(BigDecimal.valueOf(11));
		equi.setEquipInit("AAAA");
		equiList.add(equi);
		equi1.setEquipLowNbr(BigDecimal.valueOf(10));
		equi1.setEquipHighNbr(BigDecimal.valueOf(20));
		when(equiRepository.existsByEquipInit(Mockito.any())).thenReturn(true);
		when(equiRepository.findByEquipInit(Mockito.any())).thenReturn(equiList);
		exception5 = assertThrows(RecordNotAddedException.class,
				() -> when(equiService.addEquipmentRackRange(equi1, header)));
		assertEquals("Equipment Number Range are overlapping with existing records", exception5.getMessage());

		equiList = new ArrayList<>();
		equi.setEquipRackRangeId(1234L);
		equi.setEquipLowNbr(BigDecimal.valueOf(15));
		equi.setEquipHighNbr(BigDecimal.valueOf(19));
		equi.setEquipInit("AAAA");
		equiList.add(equi);
		equi1.setEquipLowNbr(BigDecimal.valueOf(10));
		equi1.setEquipHighNbr(BigDecimal.valueOf(20));
		when(equiRepository.existsByEquipInit(Mockito.any())).thenReturn(true);
		when(equiRepository.findByEquipInit(Mockito.any())).thenReturn(equiList);
		exception5 = assertThrows(RecordNotAddedException.class,
				() -> when(equiService.addEquipmentRackRange(equi1, header)));
		assertEquals("Equipment Number Range are overlapping with existing records", exception5.getMessage());

		equiList = new ArrayList<>();
		equi.setEquipRackRangeId(1234L);
		equi.setEquipLowNbr(BigDecimal.valueOf(19));
		equi.setEquipHighNbr(BigDecimal.valueOf(211));
		equi.setEquipInit("AAAA");
		equiList.add(equi);
		equi1.setEquipLowNbr(BigDecimal.valueOf(10));
		equi1.setEquipHighNbr(BigDecimal.valueOf(20));
		when(equiRepository.existsByEquipInit(Mockito.any())).thenReturn(true);
		when(equiRepository.findByEquipInit(Mockito.any())).thenReturn(equiList);
		exception5 = assertThrows(RecordNotAddedException.class,
				() -> when(equiService.addEquipmentRackRange(equi1, header)));
		assertEquals("Equipment Number Range are overlapping with existing records", exception5.getMessage());

		equiList = new ArrayList<>();
		equi.setEquipRackRangeId(1234L);
		equi.setEquipLowNbr(BigDecimal.valueOf(5));
		equi.setEquipHighNbr(BigDecimal.valueOf(40));
		equi.setEquipInit("AAAA");
		equiList.add(equi);
		equi1.setEquipLowNbr(BigDecimal.valueOf(10));
		equi1.setEquipHighNbr(BigDecimal.valueOf(20));
		when(equiRepository.existsByEquipInit(Mockito.any())).thenReturn(true);
		when(equiRepository.findByEquipInit(Mockito.any())).thenReturn(equiList);
		exception5 = assertThrows(RecordNotAddedException.class,
				() -> when(equiService.addEquipmentRackRange(equi1, header)));

		equiList = new ArrayList<>();
		equi.setEquipRackRangeId(1234L);
		equi.setEquipLowNbr(BigDecimal.valueOf(15));
		equi.setEquipHighNbr(BigDecimal.valueOf(35));
		equi.setEquipInit("AAAA");
		equiList.add(equi);
		equi1.setEquipLowNbr(BigDecimal.valueOf(10));
		equi1.setEquipHighNbr(BigDecimal.valueOf(20));
		when(equiRepository.existsByEquipInit(Mockito.any())).thenReturn(true);
		when(equiRepository.findByEquipInit(Mockito.any())).thenReturn(equiList);
		exception5 = assertThrows(RecordNotAddedException.class,
				() -> when(equiService.addEquipmentRackRange(equi1, header)));
		assertEquals("Equipment Number Range are overlapping with existing records", exception5.getMessage());

		equiList = new ArrayList<>();
		equi.setEquipRackRangeId(1234L);
		equi.setEquipLowNbr(BigDecimal.valueOf(10));
		equi.setEquipHighNbr(BigDecimal.valueOf(20));
		equi.setEquipInit("AAAA");
		equiList.add(equi);
		equi1.setEquipLowNbr(BigDecimal.valueOf(10));
		equi1.setEquipHighNbr(BigDecimal.valueOf(20));
		when(equiRepository.existsByEquipInit(Mockito.any())).thenReturn(true);
		when(equiRepository.findByEquipInit(Mockito.any())).thenReturn(equiList);
		exception5 = assertThrows(RecordNotAddedException.class,
				() -> when(equiService.addEquipmentRackRange(equi1, header)));
		assertEquals("Equipment Number Range are overlapping with existing records", exception5.getMessage());

		equiList = new ArrayList<>();
		equi.setEquipRackRangeId(1234L);
		equi.setEquipLowNbr(BigDecimal.valueOf(20));
		equi.setEquipHighNbr(BigDecimal.valueOf(10));
		equi.setEquipInit("AAAA");
		equiList.add(equi);
		equi1.setEquipLowNbr(BigDecimal.valueOf(10));
		equi1.setEquipHighNbr(BigDecimal.valueOf(20));
		when(equiRepository.existsByEquipInit(Mockito.any())).thenReturn(true);
		when(equiRepository.findByEquipInit(Mockito.any())).thenReturn(equiList);
		exception5 = assertThrows(RecordNotAddedException.class,
				() -> when(equiService.addEquipmentRackRange(equi1, header)));
		assertEquals("Equipment Number Range are overlapping with existing records", exception5.getMessage());

		equiList = new ArrayList<>();
		equi.setEquipRackRangeId(1234L);
		equi.setEquipLowNbr(BigDecimal.valueOf(17));
		equi.setEquipHighNbr(BigDecimal.valueOf(40));
		equi.setEquipInit("AAAA");
		equiList.add(equi);
		equi1.setEquipLowNbr(BigDecimal.valueOf(10));
		equi1.setEquipHighNbr(BigDecimal.valueOf(20));
		when(equiRepository.existsByEquipInit(Mockito.any())).thenReturn(true);
		when(equiRepository.findByEquipInit(Mockito.any())).thenReturn(equiList);
		exception5 = assertThrows(RecordNotAddedException.class,
				() -> when(equiService.addEquipmentRackRange(equi1, header)));
		assertEquals("Equipment Number Range are overlapping with existing records", exception5.getMessage());

		equiList = new ArrayList<>();
		equi.setEquipRackRangeId(1234L);
		equi.setEquipLowNbr(BigDecimal.valueOf(11));
		equi.setEquipHighNbr(BigDecimal.valueOf(19));
		equi.setEquipInit("AAAA");
		equiList.add(equi);
		equi1.setEquipLowNbr(BigDecimal.valueOf(10));
		equi1.setEquipHighNbr(BigDecimal.valueOf(20));
		when(equiRepository.existsByEquipInit(Mockito.any())).thenReturn(true);
		when(equiRepository.findByEquipInit(Mockito.any())).thenReturn(equiList);
		exception5 = assertThrows(RecordNotAddedException.class,
				() -> when(equiService.updateEquipmentRackRange(equi1, header)));

		equiList = new ArrayList<>();
		equi.setEquipRackRangeId(1234L);
		equi.setEquipLowNbr(BigDecimal.valueOf(9));
		equi.setEquipHighNbr(BigDecimal.valueOf(21));
		equi.setEquipInit("AAAA");
		equiList.add(equi);
		equi1.setEquipLowNbr(BigDecimal.valueOf(10));
		equi1.setEquipHighNbr(BigDecimal.valueOf(20));
		when(equiRepository.existsByEquipInit(Mockito.any())).thenReturn(true);
		when(equiRepository.findByEquipInit(Mockito.any())).thenReturn(equiList);
		exception5 = assertThrows(RecordNotAddedException.class,
				() -> when(equiService.updateEquipmentRackRange(equi1, header)));
		assertEquals("Equipment Number Range are overlapping with existing records", exception5.getMessage());

		equiList = new ArrayList<>();
		equi.setEquipRackRangeId(1234L);
		equi.setEquipLowNbr(BigDecimal.valueOf(9));
		equi.setEquipHighNbr(BigDecimal.valueOf(11));
		equi.setEquipInit("AAAA");
		equiList.add(equi);
		equi1.setEquipLowNbr(BigDecimal.valueOf(10));
		equi1.setEquipHighNbr(BigDecimal.valueOf(20));
		when(equiRepository.existsByEquipInit(Mockito.any())).thenReturn(true);
		when(equiRepository.findByEquipInit(Mockito.any())).thenReturn(equiList);
		exception5 = assertThrows(RecordNotAddedException.class,
				() -> when(equiService.updateEquipmentRackRange(equi1, header)));
		assertEquals("Equipment Number Range are overlapping with existing records", exception5.getMessage());

		equiList = new ArrayList<>();
		equi.setEquipRackRangeId(1234L);
		equi.setEquipLowNbr(BigDecimal.valueOf(15));
		equi.setEquipHighNbr(BigDecimal.valueOf(19));
		equi.setEquipInit("AAAA");
		equiList.add(equi);
		equi1.setEquipLowNbr(BigDecimal.valueOf(10));
		equi1.setEquipHighNbr(BigDecimal.valueOf(20));
		when(equiRepository.existsByEquipInit(Mockito.any())).thenReturn(true);
		when(equiRepository.findByEquipInit(Mockito.any())).thenReturn(equiList);
		exception5 = assertThrows(RecordNotAddedException.class,
				() -> when(equiService.updateEquipmentRackRange(equi1, header)));
		assertEquals("Equipment Number Range are overlapping with existing records", exception5.getMessage());

		equiList = new ArrayList<>();
		equi.setEquipRackRangeId(1234L);
		equi.setEquipLowNbr(BigDecimal.valueOf(19));
		equi.setEquipHighNbr(BigDecimal.valueOf(211));
		equi.setEquipInit("AAAA");
		equiList.add(equi);
		equi1.setEquipLowNbr(BigDecimal.valueOf(10));
		equi1.setEquipHighNbr(BigDecimal.valueOf(20));
		when(equiRepository.existsByEquipInit(Mockito.any())).thenReturn(true);
		when(equiRepository.findByEquipInit(Mockito.any())).thenReturn(equiList);
		exception5 = assertThrows(RecordNotAddedException.class,
				() -> when(equiService.updateEquipmentRackRange(equi1, header)));
		assertEquals("Equipment Number Range are overlapping with existing records", exception5.getMessage());

		equiList = new ArrayList<>();
		equi.setEquipRackRangeId(1234L);
		equi.setEquipLowNbr(BigDecimal.valueOf(5));
		equi.setEquipHighNbr(BigDecimal.valueOf(40));
		equi.setEquipInit("AAAA");
		equiList.add(equi);
		equi1.setEquipLowNbr(BigDecimal.valueOf(10));
		equi1.setEquipHighNbr(BigDecimal.valueOf(20));
		when(equiRepository.existsByEquipInit(Mockito.any())).thenReturn(true);
		when(equiRepository.findByEquipInit(Mockito.any())).thenReturn(equiList);
		exception5 = assertThrows(RecordNotAddedException.class,
				() -> when(equiService.updateEquipmentRackRange(equi1, header)));

		equiList = new ArrayList<>();
		equi.setEquipRackRangeId(1234L);
		equi.setEquipLowNbr(BigDecimal.valueOf(15));
		equi.setEquipHighNbr(BigDecimal.valueOf(35));
		equi.setEquipInit("AAAA");
		equiList.add(equi);
		equi1.setEquipLowNbr(BigDecimal.valueOf(10));
		equi1.setEquipHighNbr(BigDecimal.valueOf(20));
		when(equiRepository.existsByEquipInit(Mockito.any())).thenReturn(true);
		when(equiRepository.findByEquipInit(Mockito.any())).thenReturn(equiList);
		exception5 = assertThrows(RecordNotAddedException.class,
				() -> when(equiService.updateEquipmentRackRange(equi1, header)));
		assertEquals("Equipment Number Range are overlapping with existing records", exception5.getMessage());

		equiList = new ArrayList<>();
		equi.setEquipRackRangeId(1234L);
		equi.setEquipLowNbr(BigDecimal.valueOf(10));
		equi.setEquipHighNbr(BigDecimal.valueOf(20));
		equi.setEquipInit("AAAA");
		equiList.add(equi);
		equi1.setEquipLowNbr(BigDecimal.valueOf(10));
		equi1.setEquipHighNbr(BigDecimal.valueOf(20));
		when(equiRepository.existsByEquipInit(Mockito.any())).thenReturn(true);
		when(equiRepository.findByEquipInit(Mockito.any())).thenReturn(equiList);
		exception5 = assertThrows(RecordNotAddedException.class,
				() -> when(equiService.updateEquipmentRackRange(equi1, header)));
		assertEquals("Equipment Number Range are overlapping with existing records", exception5.getMessage());

		equiList = new ArrayList<>();
		equi.setEquipRackRangeId(1234L);
		equi.setEquipLowNbr(BigDecimal.valueOf(20));
		equi.setEquipHighNbr(BigDecimal.valueOf(10));
		equi.setEquipInit("AAAA");
		equiList.add(equi);
		equi1.setEquipLowNbr(BigDecimal.valueOf(10));
		equi1.setEquipHighNbr(BigDecimal.valueOf(20));
		when(equiRepository.existsByEquipInit(Mockito.any())).thenReturn(true);
		when(equiRepository.findByEquipInit(Mockito.any())).thenReturn(equiList);
		exception5 = assertThrows(RecordNotAddedException.class,
				() -> when(equiService.updateEquipmentRackRange(equi1, header)));
		assertEquals("Equipment Number Range are overlapping with existing records", exception5.getMessage());

		equiList = new ArrayList<>();
		equi.setEquipRackRangeId(1234L);
		equi.setEquipLowNbr(BigDecimal.valueOf(17));
		equi.setEquipHighNbr(BigDecimal.valueOf(40));
		equi.setEquipInit("AAAA");
		equiList.add(equi);
		equi1.setEquipLowNbr(BigDecimal.valueOf(10));
		equi1.setEquipHighNbr(BigDecimal.valueOf(20));
		when(equiRepository.existsByEquipInit(Mockito.any())).thenReturn(true);
		when(equiRepository.findByEquipInit(Mockito.any())).thenReturn(equiList);
		exception5 = assertThrows(RecordNotAddedException.class,
				() -> when(equiService.updateEquipmentRackRange(equi1, header)));
		assertEquals("Equipment Number Range are overlapping with existing records", exception5.getMessage());

	}

	

	@Test
	void testEquipmentRackRangeNoRecordFoundException() {
		NoRecordsFoundException exception = assertThrows(NoRecordsFoundException.class,
				() -> when(equiService.updateEquipmentRackRange(equi, header)));
		assertEquals("Record Not Found!", exception.getMessage());

		NoRecordsFoundException exception1 = assertThrows(NoRecordsFoundException.class,
				() -> when(equiService.getAllTables()));
		assertEquals("No Records found", exception1.getMessage());
	}

	@Test
	void testEquipmentRackRangeUpdateRecordNotAddedException() {
		equiList = new ArrayList<>();
		equi.setEquipRackRangeId(1234L);
		equi1.setEquipRackRangeId(1234L);
		equi.setEquipLowNbr(BigDecimal.valueOf(11));
		equi.setEquipHighNbr(BigDecimal.valueOf(19));
		equi.setEquipInit("AAAA");
		equiList.add(equi);
		equi1.setEquipLowNbr(BigDecimal.valueOf(10));
		equi1.setEquipHighNbr(BigDecimal.valueOf(20));
		when(equiRepository.existsById(Mockito.any())).thenReturn(true);
		when(equiRepository.existsByEquipInit(Mockito.any())).thenReturn(true);
		when(equiRepository.findByEquipInit(Mockito.any())).thenReturn(equiList);
		RecordNotAddedException exception5 = assertThrows(RecordNotAddedException.class,
				() -> when(equiService.updateEquipmentRackRange(equi1, header)));

	}
}
