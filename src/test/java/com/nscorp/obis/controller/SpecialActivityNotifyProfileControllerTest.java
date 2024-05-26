package com.nscorp.obis.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.sql.SQLException;
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
import org.springframework.http.ResponseEntity;

import com.nscorp.obis.domain.ActivityNotifyProfile;
import com.nscorp.obis.domain.SpecialActivityNotifyProfile;
import com.nscorp.obis.dto.ActivityNotifyProfileDTO;
import com.nscorp.obis.dto.CustomerNotifyProfileDTO;
import com.nscorp.obis.dto.SpecialActivityNotifyDTO;
import com.nscorp.obis.dto.SpecialActivityNotifyProfileDTO;
import com.nscorp.obis.dto.mapper.SpecialActivityNotifyProfileMapper;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.repository.SpecialActivityNotifyProfileRepository;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.services.SpecialActivityNotifyProfileService;

class SpecialActivityNotifyProfileControllerTest {

	@Mock
	SpecialActivityNotifyProfileService specialActivityNotifyProfileService;

	@Mock
	SpecialActivityNotifyProfileMapper specialActivityNotifyProfileMapper;

	@Mock
	SpecialActivityNotifyProfileRepository specialActivityNotifyProfileRepository;

	@InjectMocks
	SpecialActivityNotifyProfileController specialActivityNotifyProfileController;

	SpecialActivityNotifyProfile specialActivityNotifyProfile;
	SpecialActivityNotifyProfileDTO specialActivityNotifyProfileDTO;
	SpecialActivityNotifyDTO specialActivityNotifyDTO;
	ActivityNotifyProfileDTO activityNotifyProfileDTO;
	ActivityNotifyProfile activityNotifyProfile;
	List<ActivityNotifyProfile> activityNotifyProfileList;
	List<ActivityNotifyProfileDTO> activityNotifyProfileDTOList;
	List<SpecialActivityNotifyProfile> specialActivityNotifyProfileList;
	List<SpecialActivityNotifyDTO> specialActivityNotifyDTOList;

	Map<String, String> headers;

	Integer activityId = 12345;

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
		specialActivityNotifyProfile = new SpecialActivityNotifyProfile();
		specialActivityNotifyProfileDTO = new SpecialActivityNotifyProfileDTO();
		specialActivityNotifyDTO = new SpecialActivityNotifyDTO();
		specialActivityNotifyProfileList = new ArrayList<>();
		specialActivityNotifyDTOList = new ArrayList<>();

		specialActivityNotifyProfile.setActivityId(activityId);
		specialActivityNotifyProfileDTO.setActivityId(activityId);
		specialActivityNotifyDTO.setActivityId(activityId);
		specialActivityNotifyProfileList.add(specialActivityNotifyProfile);
		specialActivityNotifyDTOList.add(specialActivityNotifyDTO);

		activityNotifyProfile = new ActivityNotifyProfile();
		activityNotifyProfile.setEventCode("SEAL");
		activityNotifyProfileDTO = new ActivityNotifyProfileDTO();
		activityNotifyProfileDTO.setNotifyProfileId(6788L);
		activityNotifyProfileDTO.setEventCode("SEAL");
		activityNotifyProfileList = new ArrayList<>();
		activityNotifyProfileDTOList = new ArrayList<>();
		activityNotifyProfileDTOList.add(activityNotifyProfileDTO);
		activityNotifyProfileList.add(activityNotifyProfile);
		headers = new HashMap<>();
		headers.put("userid", "test");
	}

	@AfterEach
	void tearDown() throws Exception {
		specialActivityNotifyProfileDTO = null;
		specialActivityNotifyDTO = null;
		activityNotifyProfileDTOList = null;

	}

	@Test
	void testGetActivityDetails() throws SQLException {

		when(specialActivityNotifyProfileService.fetchActivityDetails()).thenReturn(specialActivityNotifyProfileList);
		ResponseEntity<APIResponse<List<SpecialActivityNotifyDTO>>> customerProfiles = specialActivityNotifyProfileController
				.getActivityDetails();
		assertEquals(customerProfiles.getStatusCodeValue(), 200);

	}

	@Test
	void testGetActivityProfiles() throws SQLException {

		when(specialActivityNotifyProfileService.fetchActivityProfiles(activityId))
				.thenReturn(specialActivityNotifyProfileList);
		ResponseEntity<APIResponse<List<SpecialActivityNotifyProfileDTO>>> customerProfiles = specialActivityNotifyProfileController
				.getActivityProfiles(activityId);
		assertEquals(customerProfiles.getStatusCodeValue(), 200);

	}

	@Test
	void testAddActivityNotifyProfile() throws SQLException {

		when(specialActivityNotifyProfileService.addActivityNotifyProfile(Mockito.any(), Mockito.anyMap()))
				.thenReturn(activityNotifyProfileDTO);
		ResponseEntity<APIResponse<ActivityNotifyProfileDTO>> actEntity = specialActivityNotifyProfileController
				.addActivityProfile(activityNotifyProfileDTO, headers);
		assertEquals(actEntity.getStatusCodeValue(), 200);

	}

	@Test
	void testUpdateActivityNotifyProfile() throws SQLException {

		when(specialActivityNotifyProfileService.updateActivityNotifyProfile(Mockito.any(), Mockito.anyMap()))
				.thenReturn(specialActivityNotifyProfile);
		ResponseEntity<APIResponse<SpecialActivityNotifyProfileDTO>> actEntity = specialActivityNotifyProfileController
				.updateActivityProfile(specialActivityNotifyProfileDTO, headers);
		assertEquals(actEntity.getStatusCodeValue(), 200);

	}

	@Test
	void testDeleteActivityNotifyProfile() throws SQLException {

		ResponseEntity<List<APIResponse<ActivityNotifyProfileDTO>>> response = specialActivityNotifyProfileController
				.deleteActivityProfile(activityNotifyProfileDTOList);
		assertEquals(response.getStatusCodeValue(), 200);

		activityNotifyProfileDTOList = new ArrayList<>();
		ResponseEntity<List<APIResponse<ActivityNotifyProfileDTO>>> response2 = specialActivityNotifyProfileController
				.deleteActivityProfile(activityNotifyProfileDTOList);
		assertEquals(response2.getStatusCodeValue(), 120);
	}

	@Test
	void testNoRecordsFoundException() throws SQLException {

		when(specialActivityNotifyProfileService.fetchActivityDetails()).thenThrow(new NoRecordsFoundException());
		ResponseEntity<APIResponse<List<SpecialActivityNotifyDTO>>> customerProfiles = specialActivityNotifyProfileController
				.getActivityDetails();
		assertEquals(customerProfiles.getStatusCodeValue(), 404);

		when(specialActivityNotifyProfileService.fetchActivityProfiles(activityId))
				.thenThrow(new NoRecordsFoundException());
		ResponseEntity<APIResponse<List<SpecialActivityNotifyProfileDTO>>> activityProfiles = specialActivityNotifyProfileController
				.getActivityProfiles(activityId);
		assertEquals(activityProfiles.getStatusCodeValue(), 404);

		when(specialActivityNotifyProfileService.addActivityNotifyProfile(Mockito.any(), Mockito.anyMap()))
				.thenThrow(NoRecordsFoundException.class);
		ResponseEntity<APIResponse<ActivityNotifyProfileDTO>> actEntity = specialActivityNotifyProfileController
				.addActivityProfile(activityNotifyProfileDTO, headers);
		assertEquals(actEntity.getStatusCodeValue(), 404);

		when(specialActivityNotifyProfileService.updateActivityNotifyProfile(Mockito.any(), Mockito.anyMap()))
				.thenThrow(NoRecordsFoundException.class);
		ResponseEntity<APIResponse<SpecialActivityNotifyProfileDTO>> updateEntity = specialActivityNotifyProfileController
				.updateActivityProfile(specialActivityNotifyProfileDTO, headers);
		assertEquals(updateEntity.getStatusCodeValue(), 404);
	}

	@Test
	void testException() throws SQLException {

		when(specialActivityNotifyProfileService.fetchActivityDetails()).thenThrow(new RuntimeException());
		ResponseEntity<APIResponse<List<SpecialActivityNotifyDTO>>> customerProfiles = specialActivityNotifyProfileController
				.getActivityDetails();
		assertEquals(customerProfiles.getStatusCodeValue(), 500);

		when(specialActivityNotifyProfileService.fetchActivityProfiles(activityId)).thenThrow(new RuntimeException());
		ResponseEntity<APIResponse<List<SpecialActivityNotifyProfileDTO>>> activityProfiles = specialActivityNotifyProfileController
				.getActivityProfiles(activityId);
		assertEquals(activityProfiles.getStatusCodeValue(), 500);

		when(specialActivityNotifyProfileService.addActivityNotifyProfile(Mockito.any(), Mockito.anyMap()))
				.thenThrow(RuntimeException.class);
		ResponseEntity<APIResponse<ActivityNotifyProfileDTO>> actEntity = specialActivityNotifyProfileController
				.addActivityProfile(activityNotifyProfileDTO, headers);
		assertEquals(actEntity.getStatusCodeValue(), 500);

		when(specialActivityNotifyProfileService.updateActivityNotifyProfile(Mockito.any(), Mockito.anyMap()))
				.thenThrow(RuntimeException.class);
		ResponseEntity<APIResponse<SpecialActivityNotifyProfileDTO>> updateEntity = specialActivityNotifyProfileController
				.updateActivityProfile(specialActivityNotifyProfileDTO, headers);
		assertEquals(updateEntity.getStatusCodeValue(), 500);

		activityNotifyProfileDTOList.remove(activityNotifyProfileDTO);
		ResponseEntity<List<APIResponse<ActivityNotifyProfileDTO>>> deleteEntity = specialActivityNotifyProfileController
				.deleteActivityProfile(activityNotifyProfileDTOList);
		assertEquals(deleteEntity.getStatusCodeValue(), 500);

	}
}
