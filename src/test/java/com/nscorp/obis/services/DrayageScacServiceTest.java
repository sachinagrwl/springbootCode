package com.nscorp.obis.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.nscorp.obis.domain.CityState;
import com.nscorp.obis.repository.CityStateRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.jpa.domain.Specification;

import com.nscorp.obis.common.SpecificationGenerator;
import com.nscorp.obis.domain.DrayageScac;
import com.nscorp.obis.dto.DrayageScacDTO;
import com.nscorp.obis.exception.InvalidDataException;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.exception.RecordNotAddedException;
import com.nscorp.obis.repository.DrayageSCACRepository;
import com.nscorp.obis.response.data.PaginatedResponse;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class DrayageScacServiceTest {
	@InjectMocks
	DrayageScacServiceImpl drayageScacServiceImpl;

	@Mock
	DrayageSCACRepository drayageSCACRepository;

	@Mock
	CityStateRepository cityStateRepository;

	@Mock
	SpecificationGenerator specificationGenerator;

	DrayageScacDTO drayageScacDTO;
	DrayageScac drayageScac;
	Map<String, String> header;
	List<DrayageScac> drayageScacs;
	Optional<DrayageScac> optional;

	Page<DrayageScac> page;

	String[] sort = { "drayId", "asc" };

	Specification<DrayageScac> specification;

	String drayId;
	String carrierName;
	String carrierCity;
	String state;
	Integer pageNumber;
	Integer pageSize;

	List<CityState> stateList;


	@BeforeEach
	void setUp() {
		drayageScacDTO = new DrayageScacDTO();
		CityState cityState = new CityState();
		cityState.setStateAbbreviation("LA");
		stateList = new ArrayList<>();
		drayageScacDTO.setDrayId("001");
		drayageScacDTO.setCarrierName("carrierName");
		drayageScacDTO.setCarrierCity("MIAMI");
		drayageScacDTO.setState("LA");
		drayageScacDTO.setPhoneNumber("1001001001");
		drayageScacDTO.setUversion("!");
		MockitoAnnotations.openMocks(this);
		drayageScac = new DrayageScac();
		drayageScac.setDrayId("001");
		drayageScac.setCarrierName("carrierName");
		drayageScac.setCarrierCity("MIAMI");
		drayageScac.setState("LA");
		drayageScac.setPhoneNumber("1001001001");
		drayageScac.setUversion("!");
		drayageScacs = new ArrayList<DrayageScac>();
		stateList.add(cityState);
		drayageScacs.add(drayageScac);
		page = new PageImpl(drayageScacs);
		drayId = "Test";
		carrierName = "test";
		carrierCity = "city";
		state = "NA";
		pageNumber = 0;
		pageSize = 15;
		specification = new SpecificationGenerator().drayageScacSpecification(drayId, carrierName, carrierCity, state);
		header=new HashMap<>();
		header.put("userid", "test");
		header.put("extensionschema", "test");
		optional=Optional.of(drayageScac);
	}

	@AfterEach
	void tearDown() {
		drayageScacDTO = null;
		drayageScac = null;
		page = null;

	}

	@Test
	void testGetDrayageScac() {
		when(specificationGenerator.drayageScacSpecification(drayId, carrierName, carrierCity, state))
				.thenReturn(specification);
		when(drayageSCACRepository.findAll(Mockito.any(Specification.class), Mockito.any())).thenReturn(page);
		PaginatedResponse<DrayageScacDTO> response = drayageScacServiceImpl.getDrayageScac(drayId, carrierName,
				carrierCity, state, pageSize, pageNumber, sort);
		assertNotNull(response);
		drayageScacs = new ArrayList<>();
		page = new PageImpl(drayageScacs);
		when(specificationGenerator.drayageScacSpecification(drayId, carrierName, carrierCity, state))
				.thenReturn(specification);
		when(drayageSCACRepository.findAll(Mockito.any(Specification.class), Mockito.any())).thenReturn(page);
		assertThrows(NoRecordsFoundException.class, () -> drayageScacServiceImpl.getDrayageScac(drayId, carrierName,
				carrierCity, state, pageSize, pageNumber, sort));
	}

	@Test
	void testAddDrayageScac(){
		drayageScac.setPhoneNumber("1001001001");
		when(drayageSCACRepository.existsById(drayId)).thenReturn(false);
		when(cityStateRepository.findAllByStateAbbreviation(Mockito.any())).thenReturn(stateList);
		when(drayageSCACRepository.save(Mockito.any())).thenReturn(drayageScac);
		DrayageScac response = drayageScacServiceImpl.addDrayageScac(drayageScac,header);
		assertNotNull(response);

		drayageScac.setDrayId(null);
		NoRecordsFoundException exception1 = assertThrows(NoRecordsFoundException.class,
				() -> when(drayageScacServiceImpl.addDrayageScac(drayageScac,header)));
		assertEquals("Dray Id can't be null", exception1.getMessage());

		drayageScac.setDrayId("001");
		drayageScac.setCarrierName(null);
		NoRecordsFoundException exception2 = assertThrows(NoRecordsFoundException.class,
				() -> when(drayageScacServiceImpl.addDrayageScac(drayageScac,header)));
		assertEquals("Carrier Name can't be null", exception2.getMessage());

		drayageScac.setCarrierName("Carrier");
		drayageScac.setCarrierCity(null);
		NoRecordsFoundException exception3 = assertThrows(NoRecordsFoundException.class,
				() -> when(drayageScacServiceImpl.addDrayageScac(drayageScac,header)));
		assertEquals("Carrier City can't be null", exception3.getMessage());


		drayageScac.setCarrierCity("MIAMI");
		drayageScac.setState(null);
		NoRecordsFoundException exception4 = assertThrows(NoRecordsFoundException.class,
				() -> when(drayageScacServiceImpl.addDrayageScac(drayageScac,header)));
		assertEquals("State can't be null", exception4.getMessage());

		drayageScac.setState("IL");
		drayageScac.setPhoneNumber("0981011001");
		InvalidDataException exception5 = assertThrows(InvalidDataException.class,
				() -> when(drayageScacServiceImpl.addDrayageScac(drayageScac,header)));
		assertEquals("Area code for phone number must be positive and greater than 99", exception5.getMessage());

		drayageScac.setPhoneNumber("1001011001");
		stateList = new ArrayList<>();
		when(cityStateRepository.findAllByStateAbbreviation(Mockito.anyString())).thenReturn(stateList);
		assertThrows(NoRecordsFoundException.class,
				() -> drayageScacServiceImpl.addDrayageScac(drayageScac, header));


//		drayageScac.setPhoneNumber("09809");
//		InvalidDataException exception6 = assertThrows(InvalidDataException.class,
//				() -> when(drayageScacServiceImpl.addDrayageScac(drayageScac,header)));
//		assertEquals("Exchange for phone number should have 3 digits!", exception6.getMessage());
//
//		drayageScac.setPhoneNumber("1981980009");
//		InvalidDataException exception7 = assertThrows(InvalidDataException.class,
//				() -> when(drayageScacServiceImpl.addDrayageScac(drayageScac,header)));
//		assertEquals("Customer Base should be in the range 1000-9999 !", exception7.getMessage());

	}

	@Test
	void testUpdateDrayageScac(){
		when(drayageSCACRepository.findById(Mockito.any())).thenReturn(optional);
		when(cityStateRepository.findAllByStateAbbreviation(Mockito.any())).thenReturn(stateList);
		when(drayageSCACRepository.save(Mockito.any())).thenReturn(drayageScac);
		DrayageScacDTO response = drayageScacServiceImpl.updateDrayageScac(drayageScacDTO,header);
		assertEquals(response.getDrayId(), drayageScacDTO.getDrayId());
	}

	@Test
	void testUpdateExceptions(){
		drayageScacDTO.setPhoneNumber("1001001001");

		NoRecordsFoundException exception1 = assertThrows(NoRecordsFoundException.class,
				() -> when(drayageScacServiceImpl.updateDrayageScac(drayageScacDTO,header)));
		assertEquals("No Record Found For Given Dray Id", exception1.getMessage());

		when(drayageSCACRepository.findById(Mockito.any())).thenReturn(optional);
		drayageScacDTO.setDrayId("002");
		InvalidDataException exception2 = assertThrows(InvalidDataException.class,
				() -> when(drayageScacServiceImpl.updateDrayageScac(drayageScacDTO,header)));
		assertEquals("Dray Id is not editable", exception2.getMessage());

		drayageScacDTO.setDrayId("001");
		drayageScacDTO.setMcdIccNr("abcdef");

		drayageScacDTO.setCarrierName(null);
		NoRecordsFoundException exception4 = assertThrows(NoRecordsFoundException.class,
				() -> when(drayageScacServiceImpl.updateDrayageScac(drayageScacDTO,header)));
		assertEquals("Carrier Name can't be null", exception4.getMessage());

		drayageScacDTO.setCarrierName("carrierName");
		drayageScacDTO.setCarrierCity(null);
		NoRecordsFoundException exception5 = assertThrows(NoRecordsFoundException.class,
				() -> when(drayageScacServiceImpl.updateDrayageScac(drayageScacDTO,header)));
		assertEquals("Carrier City can't be null", exception5.getMessage());

		drayageScacDTO.setCarrierCity("MIAMI");
		drayageScacDTO.setState(null);
		NoRecordsFoundException exception6 = assertThrows(NoRecordsFoundException.class,
				() -> when(drayageScacServiceImpl.updateDrayageScac(drayageScacDTO,header)));
		assertEquals("State can't be null", exception6.getMessage());


		drayageScacDTO.setState("IL");
		drayageScacDTO.setPhoneNumber("0981011001");
		InvalidDataException exception7 = assertThrows(InvalidDataException.class,
				() -> when(drayageScacServiceImpl.updateDrayageScac(drayageScacDTO,header)));
		assertEquals("Area code for phone number must be positive and greater than 99", exception7.getMessage());

		drayageScacDTO.setPhoneNumber("1980981001");

		stateList = new ArrayList<>();
		when(cityStateRepository.findAllByStateAbbreviation(Mockito.anyString())).thenReturn(stateList);
		assertThrows(NoRecordsFoundException.class,
				() -> drayageScacServiceImpl.updateDrayageScac(drayageScacDTO, header));


//		InvalidDataException exception8 = assertThrows(InvalidDataException.class,
//				() -> when(drayageScacServiceImpl.updateDrayageScac(drayageScacDTO,header)));
//		assertEquals("Exchange for phone number must be greater than 99", exception8.getMessage());
//
//		drayageScacDTO.setPhoneNumber("1981980009");
//		InvalidDataException exception9 = assertThrows(InvalidDataException.class,
//				() -> when(drayageScacServiceImpl.updateDrayageScac(drayageScacDTO,header)));
//		assertEquals("Customer Base should be in the range 1000-9999 !", exception9.getMessage());
	}

	@Test
	void testRecordNotAddedException() {
		RecordNotAddedException exception5 = assertThrows(RecordNotAddedException.class,
				() -> when(drayageScacServiceImpl.addDrayageScac(drayageScac,header)));
		assertEquals("Record Not added to Database", exception5.getMessage());

		when(drayageSCACRepository.existsById(Mockito.any())).thenReturn(true);
		RecordAlreadyExistsException exception6 = assertThrows(RecordAlreadyExistsException.class,
				() -> when(drayageScacServiceImpl.addDrayageScac(drayageScac,header)));
		assertEquals("This Drayage SCAC already exist. 2006 - Illegal duplicate key", exception6.getMessage());
	}

	@Test
	void testDeleteDrayageScac() {
		when(drayageSCACRepository.getByDrayId(Mockito.anyString())).thenReturn(drayageScac);
		Mockito.doNothing().when(drayageSCACRepository).delete(Mockito.any());
		DrayageScacDTO response = drayageScacServiceImpl.deleteDrayageScac(drayageScacDTO);
		assertNotNull(response);
		when(drayageSCACRepository.getByDrayId(Mockito.anyString())).thenReturn(null);
		assertThrows(NoRecordsFoundException.class, () -> drayageScacServiceImpl.deleteDrayageScac(drayageScacDTO));
	}

}
