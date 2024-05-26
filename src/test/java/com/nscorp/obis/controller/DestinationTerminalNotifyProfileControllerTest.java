package com.nscorp.obis.controller;

import com.nscorp.obis.domain.DestinationTerminalNotifyProfile;
import com.nscorp.obis.dto.DestinationTerminalNotifyProfileDTO;
import com.nscorp.obis.dto.mapper.DestinationTerminalNotifyProfileMapper;
import com.nscorp.obis.exception.*;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.response.data.ResponseStatusCode;
import com.nscorp.obis.services.DestinationTerminalNotifyProfileService;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.sql.SQLException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

//@SpringBootTest
class DestinationdestTerminalNotifyProfileControllerTest{
    @Mock
    DestinationTerminalNotifyProfileMapper destTerminalNotifyProfileMapper;
    @Mock
    DestinationTerminalNotifyProfileService destTerminalNotifyProfileService;
    
    @InjectMocks
    DestinationTerminalNotifyProfileController destTerminalNotifyProfileController;
    
    DestinationTerminalNotifyProfile destTerminalNotifyProfile;
    DestinationTerminalNotifyProfileDTO destTerminalNotifyProfileDto;
    List<DestinationTerminalNotifyProfile> destTerminalNotifyProfileList;
    List<DestinationTerminalNotifyProfileDTO> destTerminalNotifyProfileDtoList;
    List<ResponseObject> responseObjectList = new ArrayList<ResponseObject>();
    Map<String, String> header;
    Long terminalId;
    long profileId;
    
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}
	
	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
		destTerminalNotifyProfile = new DestinationTerminalNotifyProfile();
		destTerminalNotifyProfileDto = new DestinationTerminalNotifyProfileDTO();
		destTerminalNotifyProfileList = new ArrayList<>();
		destTerminalNotifyProfileDtoList = new ArrayList<>();
		terminalId=1700L;
		destTerminalNotifyProfile.setEventCode("SEAL");
		destTerminalNotifyProfile.setNotifyProfileId(2785460610983L);
		destTerminalNotifyProfileList.add(destTerminalNotifyProfile);
		destTerminalNotifyProfileDto.setEventCode("SEAL");
		destTerminalNotifyProfileDto.setNotifyProfileId(2785460610983L);
		//destTerminalNotifyProfileDto = destTerminalNotifyProfileMapper.INSTANCE.destTerminalNotifyProfileTodestTerminalNotifyProfileDTO(destTerminalNotifyProfile);
		destTerminalNotifyProfileDtoList.add(destTerminalNotifyProfileDto);
		responseObjectList.add(new ResponseObject(Arrays.asList("Records Deleted Successfully!"),ResponseStatusCode.SUCCESS.getStatusCode(), ResponseStatusCode.SUCCESS.toString()));
		header = new HashMap<String,String>();
		header.put("userid", "Test");
		header.put("extensionschema", "Test");
	}

	@AfterEach
	void tearDown() throws Exception {
		destTerminalNotifyProfile = null;
		destTerminalNotifyProfileDto = null;
		destTerminalNotifyProfileList = null;
		destTerminalNotifyProfileDtoList = null;
	}

	@Test
	void testGetTerminalProfilesByTermId() throws SQLException {

		when(destTerminalNotifyProfileService.fetchDestinationTerminalProfilesByTermId(Mockito.any())).thenReturn(destTerminalNotifyProfileList);
		ResponseEntity<APIResponse<List<DestinationTerminalNotifyProfileDTO>>> destTerminalNotifyProfilesDto = destTerminalNotifyProfileController.getDestinationTerminalProfilesByTermId(terminalId);
		assertEquals(destTerminalNotifyProfilesDto.getStatusCodeValue(),200);
		
	}
	
	@Test
	void testAdddestTerminalNotifyProfile() {
		when(destTerminalNotifyProfileMapper.destinationTerminalNotifyProfileToDestinationTerminalNotifyProfileDTO(Mockito.any())).thenReturn(destTerminalNotifyProfileDto);
    	when(destTerminalNotifyProfileMapper.destinationTerminalNotifyProfileDTOToDestinationTerminalNotifyProfile(Mockito.any())).thenReturn(destTerminalNotifyProfile);
    	when(destTerminalNotifyProfileService.insertDestinationTerminalNotifyProfile(Mockito.any(),Mockito.any(),Mockito.any())).thenReturn(destTerminalNotifyProfile);
		ResponseEntity<APIResponse<DestinationTerminalNotifyProfileDTO>> addeddestTerminalNotifyProfile = destTerminalNotifyProfileController.addDestinationTerminalNotifyProfile(destTerminalNotifyProfileDto, terminalId, header);
		assertEquals(addeddestTerminalNotifyProfile.getStatusCodeValue(),200);
	}
	@Test
	void testupdatedestTerminalNotifyProfileByProfileId() throws SQLException {
		
		when(destTerminalNotifyProfileService.updateDestinationTerminalProfile(Mockito.any(),Mockito.any())).thenReturn(destTerminalNotifyProfile);
		ResponseEntity<APIResponse<DestinationTerminalNotifyProfileDTO>> codeUpdated = destTerminalNotifyProfileController.updateDestinationTerminalNotifyProfile(destTerminalNotifyProfileDto, header);
		assertEquals(codeUpdated.getStatusCodeValue(),200);
	

	}
	@Test
	void testDeletedestTerminalNotifyProfileByNotifyProfileId() {
		ResponseEntity<List<APIResponse<DestinationTerminalNotifyProfileDTO>>> response = destTerminalNotifyProfileController.deleteDestinationTerminalNotifyProfileByNotifyProfileId(destTerminalNotifyProfileDtoList);
	    assertEquals(response.getStatusCodeValue(),200);
	}
	@Test
	void testNoRecordsFoundException() throws SQLException {
		when(destTerminalNotifyProfileMapper.destinationTerminalNotifyProfileToDestinationTerminalNotifyProfileDTO(Mockito.any())).thenReturn(destTerminalNotifyProfileDto);
    	when(destTerminalNotifyProfileMapper.destinationTerminalNotifyProfileDTOToDestinationTerminalNotifyProfile(Mockito.any())).thenReturn(destTerminalNotifyProfile);
    	when(destTerminalNotifyProfileService.insertDestinationTerminalNotifyProfile(Mockito.any(),Mockito.any(),Mockito.any())).thenThrow(new NoRecordsFoundException());
    	when(destTerminalNotifyProfileService.fetchDestinationTerminalProfilesByTermId(Mockito.any())).thenThrow(new NoRecordsFoundException());
    	when(destTerminalNotifyProfileService.updateDestinationTerminalProfile(Mockito.any(),Mockito.any())).thenThrow(new NoRecordsFoundException());
		ResponseEntity<APIResponse<List<DestinationTerminalNotifyProfileDTO>>> response = destTerminalNotifyProfileController.getDestinationTerminalProfilesByTermId(terminalId);
		assertEquals(response.getStatusCodeValue(),404);
		ResponseEntity<APIResponse<DestinationTerminalNotifyProfileDTO>> response1 = destTerminalNotifyProfileController.addDestinationTerminalNotifyProfile(destTerminalNotifyProfileDto, terminalId, header);
		assertEquals(response1.getStatusCodeValue(),404);
		ResponseEntity<APIResponse<DestinationTerminalNotifyProfileDTO>> response2 = destTerminalNotifyProfileController.updateDestinationTerminalNotifyProfile(destTerminalNotifyProfileDto,  header);
		assertEquals(response2.getStatusCodeValue(),404);
	}
	@Test
	@DisplayName("NullPointerException")
	void testNullPointerException() throws SQLException {
		when(destTerminalNotifyProfileService.updateDestinationTerminalProfile(Mockito.any(),Mockito.any())).thenThrow(new NullPointerException());
		ResponseEntity<APIResponse<DestinationTerminalNotifyProfileDTO>> response = destTerminalNotifyProfileController.updateDestinationTerminalNotifyProfile(destTerminalNotifyProfileDto, header);
		assertEquals(response.getStatusCodeValue(),400);
	}
	@Test
	@DisplayName("Internal Server Error")
	void testException() throws SQLException {
		when(destTerminalNotifyProfileMapper.destinationTerminalNotifyProfileToDestinationTerminalNotifyProfileDTO(Mockito.any())).thenReturn(destTerminalNotifyProfileDto);
    	when(destTerminalNotifyProfileMapper.destinationTerminalNotifyProfileDTOToDestinationTerminalNotifyProfile(Mockito.any())).thenReturn(destTerminalNotifyProfile);
    	when(destTerminalNotifyProfileService.insertDestinationTerminalNotifyProfile(Mockito.any(),Mockito.any(),Mockito.any())).thenThrow(new RuntimeException());
    	when(destTerminalNotifyProfileService.fetchDestinationTerminalProfilesByTermId(Mockito.any())).thenThrow(new RuntimeException());
    	when(destTerminalNotifyProfileService.updateDestinationTerminalProfile(Mockito.any(),Mockito.any())).thenThrow(new RuntimeException());
		ResponseEntity<APIResponse<List<DestinationTerminalNotifyProfileDTO>>> response = destTerminalNotifyProfileController.getDestinationTerminalProfilesByTermId(terminalId);
		assertEquals(response.getStatusCodeValue(),500);
		ResponseEntity<APIResponse<DestinationTerminalNotifyProfileDTO>> response1 = destTerminalNotifyProfileController.addDestinationTerminalNotifyProfile(destTerminalNotifyProfileDto, terminalId, header);
		assertEquals(response1.getStatusCodeValue(),500);
		ResponseEntity<APIResponse<DestinationTerminalNotifyProfileDTO>> response2 = destTerminalNotifyProfileController.updateDestinationTerminalNotifyProfile(destTerminalNotifyProfileDto, header);
		assertEquals(response2.getStatusCodeValue(),500);
	}
	@Test
	void testRecordAlreadyExistsException() throws SQLException {
		when(destTerminalNotifyProfileMapper.destinationTerminalNotifyProfileToDestinationTerminalNotifyProfileDTO(Mockito.any())).thenReturn(destTerminalNotifyProfileDto);
    	when(destTerminalNotifyProfileMapper.destinationTerminalNotifyProfileDTOToDestinationTerminalNotifyProfile(Mockito.any())).thenReturn(destTerminalNotifyProfile);
    	when(destTerminalNotifyProfileService.insertDestinationTerminalNotifyProfile(Mockito.any(),Mockito.any(),Mockito.any())).thenThrow(new RecordAlreadyExistsException());
    	ResponseEntity<APIResponse<DestinationTerminalNotifyProfileDTO>> response = destTerminalNotifyProfileController.addDestinationTerminalNotifyProfile(destTerminalNotifyProfileDto, terminalId, header);
		assertEquals(response.getStatusCodeValue(),208);
		
	}
	@Test
	void testSizeExceedException() throws SQLException {
		when(destTerminalNotifyProfileMapper.destinationTerminalNotifyProfileToDestinationTerminalNotifyProfileDTO(Mockito.any())).thenReturn(destTerminalNotifyProfileDto);
    	when(destTerminalNotifyProfileMapper.destinationTerminalNotifyProfileDTOToDestinationTerminalNotifyProfile(Mockito.any())).thenReturn(destTerminalNotifyProfile);
    	when(destTerminalNotifyProfileService.insertDestinationTerminalNotifyProfile(Mockito.any(),Mockito.any(),Mockito.any())).thenThrow(new SizeExceedException());
    	ResponseEntity<APIResponse<DestinationTerminalNotifyProfileDTO>> response = destTerminalNotifyProfileController.addDestinationTerminalNotifyProfile(destTerminalNotifyProfileDto, terminalId, header);
		assertEquals(response.getStatusCodeValue(),411);
		when(destTerminalNotifyProfileService.updateDestinationTerminalProfile(Mockito.any(),Mockito.any())).thenThrow(new SizeExceedException());
		ResponseEntity<APIResponse<DestinationTerminalNotifyProfileDTO>> response1 = destTerminalNotifyProfileController.updateDestinationTerminalNotifyProfile(destTerminalNotifyProfileDto, header);
		assertEquals(response1.getStatusCodeValue(),411);
	}
	@Test
	void testRecordNotAddedException() throws SQLException {
		when(destTerminalNotifyProfileMapper.destinationTerminalNotifyProfileToDestinationTerminalNotifyProfileDTO(Mockito.any())).thenReturn(destTerminalNotifyProfileDto);
    	when(destTerminalNotifyProfileMapper.destinationTerminalNotifyProfileDTOToDestinationTerminalNotifyProfile(Mockito.any())).thenReturn(destTerminalNotifyProfile);
    	when(destTerminalNotifyProfileService.insertDestinationTerminalNotifyProfile(Mockito.any(),Mockito.any(),Mockito.any())).thenThrow(new RecordNotAddedException());
    	ResponseEntity<APIResponse<DestinationTerminalNotifyProfileDTO>> response = destTerminalNotifyProfileController.addDestinationTerminalNotifyProfile(destTerminalNotifyProfileDto, terminalId, header);
		assertEquals(response.getStatusCodeValue(),406);
	}
}
