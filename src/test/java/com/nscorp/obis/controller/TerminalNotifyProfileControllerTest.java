package com.nscorp.obis.controller;

import com.nscorp.obis.domain.TerminalNotifyProfile;
import com.nscorp.obis.dto.TerminalNotifyProfileDTO;
import com.nscorp.obis.dto.mapper.TerminalNotifyProfileMapper;
import com.nscorp.obis.exception.*;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.response.data.ResponseStatusCode;
import com.nscorp.obis.services.TerminalNotifyProfileService;
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
class TerminalNotifyProfileControllerTest{
    @Mock
    TerminalNotifyProfileMapper terminalNotifyProfileMapper;
    @Mock
    TerminalNotifyProfileService terminalNotifyProfileService;
    
     
    @InjectMocks
    TerminalNotifyProfileController terminalNotifyProfileController;
    
    TerminalNotifyProfile terminalNotifyProfile;
    TerminalNotifyProfileDTO terminalNotifyProfileDto;
    List<TerminalNotifyProfile> terminalNotifyProfileList;
    List<TerminalNotifyProfileDTO> terminalNotifyProfileDtoList;
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
		terminalNotifyProfile = new TerminalNotifyProfile();
		terminalNotifyProfileDto = new TerminalNotifyProfileDTO();
		terminalNotifyProfileList = new ArrayList<>();
		terminalNotifyProfileDtoList = new ArrayList<>();
		terminalId=1700L;
		terminalNotifyProfile.setEventCode("SEAL");
		terminalNotifyProfile.setNotifyProfileId(2785460610983L);
		terminalNotifyProfileList.add(terminalNotifyProfile);
		terminalNotifyProfileDto.setTerminalId(terminalId);
		terminalNotifyProfileDto.setEventCode("SEAL");
		terminalNotifyProfileDto.setNotifyProfileId(2785460610983L);
		//terminalNotifyProfileDto = TerminalNotifyProfileMapper.INSTANCE.terminalNotifyProfileToTerminalNotifyProfileDTO(terminalNotifyProfile);
		terminalNotifyProfileDtoList.add(terminalNotifyProfileDto);
		responseObjectList.add(new ResponseObject(Arrays.asList("Records Deleted Successfully!"),ResponseStatusCode.SUCCESS.getStatusCode(), ResponseStatusCode.SUCCESS.toString()));
		header = new HashMap<String,String>();
		header.put("userid", "Test");
		header.put("extensionschema", "Test");
	}

	@AfterEach
	void tearDown() throws Exception {
		terminalNotifyProfile = null;
		terminalNotifyProfileDto = null;
		terminalNotifyProfileList = null;
		terminalNotifyProfileDtoList = null;
	}

	@Test
	void testGetTerminalProfilesByTermId() throws SQLException {

		when(terminalNotifyProfileService.fetchTerminalProfilesByTermId(Mockito.any())).thenReturn(terminalNotifyProfileList);
		ResponseEntity<APIResponse<List<TerminalNotifyProfileDTO>>> terminalNotifyProfilesDto = terminalNotifyProfileController.getTerminalProfilesByTermId(terminalId);
		assertEquals(terminalNotifyProfilesDto.getStatusCodeValue(),200);
		
	}
	@Test
	void testAddTerminalNotifyProfile() {
		when(terminalNotifyProfileMapper.terminalNotifyProfileToTerminalNotifyProfileDTO(Mockito.any())).thenReturn(terminalNotifyProfileDto);
    	when(terminalNotifyProfileMapper.terminalNotifyProfileDTOToTerminalNotifyProfile(Mockito.any())).thenReturn(terminalNotifyProfile);
    	when(terminalNotifyProfileService.insertTerminalNotifyProfile(Mockito.any(),Mockito.any(),Mockito.any())).thenReturn(terminalNotifyProfile);
		ResponseEntity<APIResponse<TerminalNotifyProfileDTO>> addedTerminalNotifyProfile = terminalNotifyProfileController.addTerminalNotifyProfile(terminalNotifyProfileDto, terminalId, header);
		assertEquals(addedTerminalNotifyProfile.getStatusCodeValue(),200);
	}
	@Test
	void testupdateTerminalNotifyProfileByProfileId() throws SQLException {
		
		when(terminalNotifyProfileService.updateTerminalProfileByProfileId(Mockito.any(),Mockito.any())).thenReturn(terminalNotifyProfile);
		ResponseEntity<APIResponse<TerminalNotifyProfileDTO>> codeUpdated = terminalNotifyProfileController.updateTerminalNotifyProfileByProfileId(terminalNotifyProfileDto, profileId, header);
		assertEquals(codeUpdated.getStatusCodeValue(),200);
	

	}
	@Test
	void testDeleteTerminalNotifyProfileByNotifyProfileId() {
		ResponseEntity<List<APIResponse<TerminalNotifyProfileDTO>>> response = terminalNotifyProfileController.deleteTerminalNotifyProfileByNotifyProfileId(terminalNotifyProfileDtoList);
	    assertEquals(response.getStatusCodeValue(),200);
	}
	@Test
	void testNoRecordsFoundException() throws SQLException {
		when(terminalNotifyProfileMapper.terminalNotifyProfileToTerminalNotifyProfileDTO(Mockito.any())).thenReturn(terminalNotifyProfileDto);
    	when(terminalNotifyProfileMapper.terminalNotifyProfileDTOToTerminalNotifyProfile(Mockito.any())).thenReturn(terminalNotifyProfile);
    	when(terminalNotifyProfileService.fetchTerminalProfilesByTermId(Mockito.any())).thenThrow(new NoRecordsFoundException());
		ResponseEntity<APIResponse<List<TerminalNotifyProfileDTO>>> response = terminalNotifyProfileController.getTerminalProfilesByTermId(terminalId);
		assertEquals(response.getStatusCodeValue(),404);
		when(terminalNotifyProfileService.insertTerminalNotifyProfile(Mockito.any(),Mockito.any(),Mockito.any())).thenThrow(new NoRecordsFoundException());
		ResponseEntity<APIResponse<TerminalNotifyProfileDTO>> response1 = terminalNotifyProfileController.addTerminalNotifyProfile(terminalNotifyProfileDto, terminalId, header);
		assertEquals(response1.getStatusCodeValue(),404);
		when(terminalNotifyProfileService.updateTerminalProfileByProfileId(Mockito.any(),Mockito.any())).thenThrow(new NoRecordsFoundException());
		ResponseEntity<APIResponse<TerminalNotifyProfileDTO>> response2 = terminalNotifyProfileController.updateTerminalNotifyProfileByProfileId(terminalNotifyProfileDto, terminalId, header);
		assertEquals(response2.getStatusCodeValue(),404);
	}
	@Test
	@DisplayName("NullPointerException")
	void testNullPointerException() throws SQLException {
		when(terminalNotifyProfileService.updateTerminalProfileByProfileId(Mockito.any(),Mockito.any())).thenThrow(new NullPointerException());
		ResponseEntity<APIResponse<TerminalNotifyProfileDTO>> response = terminalNotifyProfileController.updateTerminalNotifyProfileByProfileId(terminalNotifyProfileDto, terminalId, header);
		assertEquals(response.getStatusCodeValue(),400);
	}
	@Test
	@DisplayName("Internal Server Error")
	void testException() throws SQLException {
		when(terminalNotifyProfileMapper.terminalNotifyProfileToTerminalNotifyProfileDTO(Mockito.any())).thenReturn(terminalNotifyProfileDto);
    	when(terminalNotifyProfileMapper.terminalNotifyProfileDTOToTerminalNotifyProfile(Mockito.any())).thenReturn(terminalNotifyProfile);
    	when(terminalNotifyProfileService.fetchTerminalProfilesByTermId(Mockito.any())).thenThrow(new RuntimeException());
		ResponseEntity<APIResponse<List<TerminalNotifyProfileDTO>>> response = terminalNotifyProfileController.getTerminalProfilesByTermId(terminalId);
		assertEquals(response.getStatusCodeValue(),500);
		when(terminalNotifyProfileService.insertTerminalNotifyProfile(Mockito.any(),Mockito.any(),Mockito.any())).thenThrow(new RuntimeException());
		ResponseEntity<APIResponse<TerminalNotifyProfileDTO>> response1 = terminalNotifyProfileController.addTerminalNotifyProfile(terminalNotifyProfileDto, terminalId, header);
		assertEquals(response1.getStatusCodeValue(),500);
		when(terminalNotifyProfileService.updateTerminalProfileByProfileId(Mockito.any(),Mockito.any())).thenThrow(new RuntimeException());
		ResponseEntity<APIResponse<TerminalNotifyProfileDTO>> response2 = terminalNotifyProfileController.updateTerminalNotifyProfileByProfileId(terminalNotifyProfileDto, terminalId, header);
		assertEquals(response2.getStatusCodeValue(),500);
	}
	@Test
	void testRecordAlreadyExistsException() throws SQLException {
		when(terminalNotifyProfileMapper.terminalNotifyProfileToTerminalNotifyProfileDTO(Mockito.any())).thenReturn(terminalNotifyProfileDto);
    	when(terminalNotifyProfileMapper.terminalNotifyProfileDTOToTerminalNotifyProfile(Mockito.any())).thenReturn(terminalNotifyProfile);
    	when(terminalNotifyProfileService.insertTerminalNotifyProfile(Mockito.any(),Mockito.any(),Mockito.any())).thenThrow(new RecordAlreadyExistsException());
		ResponseEntity<APIResponse<TerminalNotifyProfileDTO>> response = terminalNotifyProfileController.addTerminalNotifyProfile(terminalNotifyProfileDto, terminalId, header);
		assertEquals(response.getStatusCodeValue(),208);
		
	}
	@Test
	void testSizeExceedException() throws SQLException {
		when(terminalNotifyProfileMapper.terminalNotifyProfileToTerminalNotifyProfileDTO(Mockito.any())).thenReturn(terminalNotifyProfileDto);
    	when(terminalNotifyProfileMapper.terminalNotifyProfileDTOToTerminalNotifyProfile(Mockito.any())).thenReturn(terminalNotifyProfile);
    	when(terminalNotifyProfileService.insertTerminalNotifyProfile(Mockito.any(),Mockito.any(),Mockito.any())).thenThrow(new SizeExceedException());
		ResponseEntity<APIResponse<TerminalNotifyProfileDTO>> response = terminalNotifyProfileController.addTerminalNotifyProfile(terminalNotifyProfileDto, terminalId, header);
		assertEquals(response.getStatusCodeValue(),411);
		when(terminalNotifyProfileService.updateTerminalProfileByProfileId(Mockito.any(),Mockito.any())).thenThrow(new SizeExceedException());
		ResponseEntity<APIResponse<TerminalNotifyProfileDTO>> response1 = terminalNotifyProfileController.updateTerminalNotifyProfileByProfileId(terminalNotifyProfileDto, terminalId, header);
		assertEquals(response1.getStatusCodeValue(),411);
	}
	@Test
	void testRecordNotAddedException() throws SQLException {
		when(terminalNotifyProfileMapper.terminalNotifyProfileToTerminalNotifyProfileDTO(Mockito.any())).thenReturn(terminalNotifyProfileDto);
    	when(terminalNotifyProfileMapper.terminalNotifyProfileDTOToTerminalNotifyProfile(Mockito.any())).thenReturn(terminalNotifyProfile);
    	when(terminalNotifyProfileService.insertTerminalNotifyProfile(Mockito.any(),Mockito.any(),Mockito.any())).thenThrow(new RecordNotAddedException());
		ResponseEntity<APIResponse<TerminalNotifyProfileDTO>> response = terminalNotifyProfileController.addTerminalNotifyProfile(terminalNotifyProfileDto, terminalId, header);
		assertEquals(response.getStatusCodeValue(),406);
		
	}
}
