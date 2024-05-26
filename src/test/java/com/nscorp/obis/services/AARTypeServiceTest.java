package com.nscorp.obis.services;

import com.nscorp.obis.domain.AARType;
import com.nscorp.obis.dto.AARTypeDTO;
import com.nscorp.obis.exception.InvalidDataException;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.exception.RecordNotDeletedException;
import com.nscorp.obis.repository.AARTypeRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import java.util.*;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.when;

class AARTypeServiceTest  {

	@InjectMocks
	AARTypeServiceImpl aarTypeService;

	@Mock
	AARTypeRepository aarTypeRepository;
	AARType aarType;
	AARTypeDTO aarTypeDto;
	List<AARType> aarTypeList;
	List<AARTypeDTO> aarTypeDTOList;
	List<String> search;
	AARType addedTable;
	AARType tableUpdated;
	AARType aarTypeAdded;
	Map<String, String> header;
	String type;
	String type2;
	List<String> type1;
	List<String> description1;
	List<Integer> capacity1;
	List<AARType> aarTypes;
	@SuppressWarnings("deprecation")
	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		aarTypes=new ArrayList<>();
		aarType = new AARType();
		aarType.setAarCapacity(1);
		aarType.setAarType("P110");
		aarType.setAarDescription("DEMO");
		aarType.setImDescription("Demo");
		aarType.setStandardAarType("Y");
		aarType.setCreateUserId("Qwe");
		aarTypeList = new ArrayList<>();
		aarTypeList.add(aarType);
		aarTypeDto = new AARTypeDTO();
		aarTypeDto.setAarCapacity(1);
		aarTypeDto.setAarType("P110");
		aarTypeDto.setAarDescription("DEMO");
		aarTypeDto.setImDescription("Demo");
		aarTypeDto.setStandardAarType("Y");
		aarTypeDTOList = new ArrayList<>();
		aarTypeDTOList.add(aarTypeDto);
		type = "car";
		type2 = "freight";
		search = new ArrayList<>();
		search.add("P");
		search.add("Q");
		search.add("S");
		type1 = new ArrayList<>();
		type1.add("P110");
		type1.add("P111");
		description1= new ArrayList<>();
		description1.add("145300");
		description1.add("145200");
		capacity1 = new ArrayList<>();
		capacity1.add(11);
		capacity1.add(0);
		header = new HashMap<String, String>();
		header.put("userid", "Test");
		header.put("extensionschema", "Test");
	}

	@AfterEach
	void tearDown() throws Exception {
		aarType = null;
		aarTypeDto = null;
		aarTypeDTOList = null;
		aarTypeList = null;
	}

	@Test
	void testGetAllAARTypes() {
		when(aarTypeRepository.findByAarTypeStartsWith("P")).thenReturn(aarTypeList);
		List<AARType> aarTypes = aarTypeService.getAllAARTypes(type);
		assertEquals(aarTypes,aarTypeList);
	}

	@Test
	void testGetFreightAARTypes() {
		when(aarTypeRepository.findByAarTypeStartsWith("U")).thenReturn(aarTypeList);
		List<AARType> aarTypes = aarTypeService.getAllAARTypes(type2);
		assertEquals(aarTypes,aarTypeList);
	}


	@Test
	void testGetAllAARTypesException(){
		NoRecordsFoundException exception = assertThrows(NoRecordsFoundException.class,
				() -> when(aarTypeService.getAllAARTypes(Mockito.anyString())));
		Assertions.assertEquals("No Records found for AAR Type!", exception.getMessage());

		NoRecordsFoundException exception1 = assertThrows(NoRecordsFoundException.class,
				() -> when(aarTypeService.updateAARType(aarType, header)));
		Assertions.assertEquals("Record with AARType " + aarType.getAarType()+ " Not Found!", exception1.getMessage());
	}

	@Test
	void testgetAllAARTypesList() {
		when(aarTypeRepository.findAllByOrderByAarType()).thenReturn(aarTypeList);
		List<AARType> aarTypes2 = aarTypeService.getAllAARTypesList(null,null,null);
		assertEquals(aarTypes2,aarTypeList);

		when(aarTypeRepository.findByAarTypeInOrderByAarTypeAsc(type1)).thenReturn(aarTypeList);
		List<AARType>  aarTypes3 = aarTypeService.getAllAARTypesList(type1,null,null);
		assertEquals(aarTypes3,aarTypeList);

		when(aarTypeRepository.findByAarTypeInAndAarDescriptionInOrderByAarTypeAsc(type1,description1)).thenReturn(aarTypeList);
		List<AARType> aarTypes7 = aarTypeService.getAllAARTypesList(type1,description1,null);
		assertEquals(aarTypes7,aarTypeList);

		when(aarTypeRepository.findByAarTypeInAndAarDescriptionInAndAarCapacityInOrderByAarTypeAsc(type1,description1,capacity1)).thenReturn(aarTypeList);
		List<AARType> aarTypes1 = aarTypeService.getAllAARTypesList(type1,description1,capacity1);
		assertEquals(aarTypes1,aarTypeList);

		when(aarTypeRepository.findByAarDescriptionInOrderByAarTypeAsc(description1)).thenReturn(aarTypeList);
		List<AARType>  aarTypes4 = aarTypeService.getAllAARTypesList(null,description1,null);
		assertEquals(aarTypes4,aarTypeList);

		when(aarTypeRepository.findByAarDescriptionInAndAarCapacityInOrderByAarTypeAsc(description1,capacity1)).thenReturn(aarTypeList);
		List<AARType> aarTypes6 = aarTypeService.getAllAARTypesList(null,description1,capacity1);
		assertEquals(aarTypes6,aarTypeList);

		when(aarTypeRepository.findByAarCapacityInOrderByAarTypeAsc(capacity1)).thenReturn(aarTypeList);
		List<AARType> aarTypes5 = aarTypeService.getAllAARTypesList(null,null,capacity1);
		assertEquals(aarTypes5,aarTypeList);

		when(aarTypeRepository.findByAarTypeInAndAarCapacityInOrderByAarTypeAsc(type1,capacity1)).thenReturn(aarTypeList);
		List<AARType> aarTypes8 = aarTypeService.getAllAARTypesList(type1,null,capacity1);
		assertEquals(aarTypes8,aarTypeList);
	}

	@Test
	void testGetAllAARTypesListException(){
		when(isNullAndEmptyCheck(aarTypeRepository.findByAarTypeInAndAarDescriptionInAndAarCapacityInOrderByAarTypeAsc(type1,description1,capacity1))).thenThrow(new NoRecordsFoundException("No Records found !"));
		NoRecordsFoundException exception = assertThrows(NoRecordsFoundException.class,
				() -> when(aarTypeService.getAllAARTypesList(type1,description1,capacity1)));
		Assertions.assertEquals("No Records found !", exception.getMessage());

		exception = assertThrows(NoRecordsFoundException.class,
				() -> when(aarTypeService.getAllAARTypesList(Collections.emptyList(),Collections.emptyList(),Collections.emptyList())));
		Assertions.assertEquals("No Records found !", exception.getMessage());
		exception = assertThrows(NoRecordsFoundException.class,
				() -> when(aarTypeService.getAllAARTypesList(Collections.emptyList(),null,null)));
		Assertions.assertEquals("No Records found !", exception.getMessage());
		exception = assertThrows(NoRecordsFoundException.class,
				() -> when(aarTypeService.getAllAARTypesList(null,Collections.emptyList(),null)));
		Assertions.assertEquals("No Records found !", exception.getMessage());
		exception = assertThrows(NoRecordsFoundException.class,
				() -> when(aarTypeService.getAllAARTypesList(null,null,Collections.emptyList())));
		Assertions.assertEquals("No Records found !", exception.getMessage());

		exception = assertThrows(NoRecordsFoundException.class,
				() -> when(aarTypeService.getAllAARTypesList(Collections.emptyList(),Collections.emptyList(),null)));
		Assertions.assertEquals("No Records found !", exception.getMessage());

		exception = assertThrows(NoRecordsFoundException.class,
				() -> when(aarTypeService.getAllAARTypesList(null,Collections.emptyList(),Collections.emptyList())));
		Assertions.assertEquals("No Records found !", exception.getMessage());

		exception = assertThrows(NoRecordsFoundException.class,
				() -> when(aarTypeService.getAllAARTypesList(Collections.emptyList(),null,Collections.emptyList())));
		Assertions.assertEquals("No Records found !", exception.getMessage());

		when(isNullAndEmptyCheck(aarTypeRepository.findAllByOrderByAarType())).thenThrow(new NoRecordsFoundException("No Records found !"));
		exception = assertThrows(NoRecordsFoundException.class,
				() -> when(aarTypeService.getAllAARTypesList(null,null,null)));
		Assertions.assertEquals("No Records found !", exception.getMessage());

		when(isNullAndEmptyCheck(aarTypeRepository.findByAarTypeInOrderByAarTypeAsc(type1))).thenThrow(new NoRecordsFoundException("No Records found !"));
		exception = assertThrows(NoRecordsFoundException.class,
				() -> when(aarTypeService.getAllAARTypesList(type1,null,null)));
		Assertions.assertEquals("No Records found !", exception.getMessage());

		when(isNullAndEmptyCheck(aarTypeRepository.findByAarDescriptionInOrderByAarTypeAsc(description1))).thenThrow(new NoRecordsFoundException("No Records found !"));
		exception = assertThrows(NoRecordsFoundException.class,
				() -> when(aarTypeService.getAllAARTypesList(null,description1,null)));
		Assertions.assertEquals("No Records found !", exception.getMessage());

		when(isNullAndEmptyCheck(aarTypeRepository.findByAarCapacityInOrderByAarTypeAsc(capacity1))).thenThrow(new NoRecordsFoundException("No Records found !"));
		exception = assertThrows(NoRecordsFoundException.class,
				() -> when(aarTypeService.getAllAARTypesList(null,null,capacity1)));
		Assertions.assertEquals("No Records found !", exception.getMessage());

		when(isNullAndEmptyCheck(aarTypeRepository.findByAarDescriptionInAndAarCapacityInOrderByAarTypeAsc(description1,capacity1))).thenThrow(new NoRecordsFoundException("No Records found !"));
		exception = assertThrows(NoRecordsFoundException.class,
				() -> when(aarTypeService.getAllAARTypesList(null,description1,capacity1)));
		Assertions.assertEquals("No Records found !", exception.getMessage());

		when(isNullAndEmptyCheck(aarTypeRepository.findByAarTypeInAndAarDescriptionInOrderByAarTypeAsc(type1,description1))).thenThrow(new NoRecordsFoundException("No Records found !"));
		exception = assertThrows(NoRecordsFoundException.class,
				() -> when(aarTypeService.getAllAARTypesList(type1,description1,null)));
		Assertions.assertEquals("No Records found !", exception.getMessage());

		when(isNullAndEmptyCheck(aarTypeRepository.findByAarTypeInAndAarCapacityInOrderByAarTypeAsc(type1,capacity1))).thenThrow(new NoRecordsFoundException("No Records found !"));
		exception = assertThrows(NoRecordsFoundException.class,
				() -> when(aarTypeService.getAllAARTypesList(type1,null,capacity1)));
		Assertions.assertEquals("No Records found !", exception.getMessage());
	}

	private boolean isNullAndEmptyCheck(List<?> type1) {
		if(type1==null && type1.isEmpty())
			return true;
		else if(type1!=null && !type1.isEmpty())
			return false;
		else
			return true;
	}

	@Test
	void testAddAARType() {
		when(aarTypeRepository.existsByAarType(Mockito.any())).thenReturn(false);
		when(aarTypeRepository.save(Mockito.any())).thenReturn(aarType);
		aarTypeAdded = aarTypeService.insertAARType(aarType, header);
		assertEquals(aarTypeAdded,aarType);
	}

	@Test
	void testAARTypeRecordAlreadyExistsException() {
		when(aarTypeRepository.existsByAarType(Mockito.any())).thenReturn(true);
		RecordAlreadyExistsException exception = assertThrows(RecordAlreadyExistsException.class,
				() -> when(aarTypeService.insertAARType(aarType, header)));
		assertEquals("Record with AARType Already Exists!", exception.getMessage());
	}



	@Test
	void testUpdateAARType() {
		when(aarTypeRepository.existsByAarType(aarType.getAarType())).thenReturn(true);
		when(aarTypeRepository.findByAarType(aarType.getAarType())).thenReturn(aarType);
		when(aarTypeRepository.save(aarType)).thenReturn(aarType);
		AARType aarTypeAdded = aarTypeService.updateAARType(aarType, header);
		assertEquals(aarTypeAdded,aarType);
	}

	@Test
	void testNoRecordFoundException() {
		when(aarTypeRepository.existsByAarType(Mockito.any())).thenReturn(false);
		NoRecordsFoundException exception = assertThrows(NoRecordsFoundException.class,
				() -> when(aarTypeService.updateAARType(aarType, header)));
		assertEquals("Record with AARType " + aarType.getAarType()+ " Not Found!", exception.getMessage());
	}

	@Test
	void testDeleteAARType() {
		when(aarTypeRepository.existsByAarType(Mockito.anyString())).thenReturn(true);
		aarTypeRepository.deleteByAarType(Mockito.any());
		aarTypeService.deleteAARType(aarType);
	}
	@Test
	void testDeleteAARTypeException() {
		when(aarTypeRepository.existsByAarType(Mockito.anyString())).thenReturn(false);
		RecordNotDeletedException exception = assertThrows(RecordNotDeletedException.class,
				() -> aarTypeService.deleteAARType(aarType));
		assertEquals("Record Not Found!", exception.getMessage());
	}
}

