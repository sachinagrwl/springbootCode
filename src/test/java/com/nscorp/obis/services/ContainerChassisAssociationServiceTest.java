package com.nscorp.obis.services;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.nscorp.obis.domain.ContainerChassisAssociation;
import com.nscorp.obis.dto.ContainerChassisAssociationDTO;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.exception.RecordNotAddedException;
import com.nscorp.obis.repository.ContainerChassisAssociationRepository;

public class ContainerChassisAssociationServiceTest {

    @InjectMocks
    ContainerChassisAssociationServiceImpl containerChassisAssociationService;

    @Mock
    ContainerChassisAssociationRepository containerChassisAssociationRepo;

    ContainerChassisAssociation containerChassisAssociation;
    ContainerChassisAssociationDTO containerChassisAssociationDto;
    List<ContainerChassisAssociation> containerChassisAssociationList;
    List<ContainerChassisAssociationDTO> containerChassisAssociationDTOList;
    ContainerChassisAssociation addedContChassisAssoc;
    ContainerChassisAssociation updatedContChassisAssoc;
    Map<String, String> header;

    @BeforeEach
    void setUp() throws Exception {

        MockitoAnnotations.initMocks(this);
        containerChassisAssociation = new ContainerChassisAssociation();
        containerChassisAssociation.setAssociationId(41931813532L);
        containerChassisAssociation.setContainerInit("Test");
        containerChassisAssociation.setContainerLowNumber(BigDecimal.valueOf(100));
        containerChassisAssociation.setContainerHighNumber(BigDecimal.valueOf(200));
        containerChassisAssociation.setContainerLength(20);

        containerChassisAssociation.setChassisInit("Test");
        containerChassisAssociation.setChassisLowNumber(BigDecimal.valueOf(100));
        containerChassisAssociation.setChassisHighNumber(BigDecimal.valueOf(200));
        containerChassisAssociation.setChassisLength(20);

        containerChassisAssociation.setAllowIndicator("Y");
        containerChassisAssociation.setExpiredDateTime(null);

        containerChassisAssociationList = new ArrayList<>();
        containerChassisAssociationList.add(containerChassisAssociation);

        containerChassisAssociationDto = new ContainerChassisAssociationDTO();
        containerChassisAssociationDto.setAssociationId(41931813532L);
        containerChassisAssociationDto.setContainerInit("Test");
        containerChassisAssociationDto.setContainerLowNumber(BigDecimal.valueOf(100));
        containerChassisAssociationDto.setContainerHighNumber(BigDecimal.valueOf(200));
        containerChassisAssociationDto.setContainerLength(20);

        containerChassisAssociationDto.setChassisInit("Test");
        containerChassisAssociationDto.setChassisLowNumber(BigDecimal.valueOf(100));
        containerChassisAssociationDto.setChassisHighNumber(BigDecimal.valueOf(200));
        containerChassisAssociationDto.setChassisLength(20);

        containerChassisAssociationDto.setAllowIndicator("Y");
        containerChassisAssociationDto.setExpiredDateTime(null);

        containerChassisAssociationDTOList = new ArrayList<>();
        containerChassisAssociationDTOList.add(containerChassisAssociationDto);

        header = new HashMap<String, String>();
        header.put("userid", "Test");
        header.put("extensionschema", "Test");
    }

    @AfterEach
    void tearDown() throws Exception {

        containerChassisAssociationDto = null;
        containerChassisAssociation = null;
        containerChassisAssociationList = null;
        containerChassisAssociationDTOList = null;
    }

    @Test
    void testGetAllContainerChassisAssociation() {
        when(containerChassisAssociationRepo.findAll()).thenReturn(containerChassisAssociationList);
        List<ContainerChassisAssociation> getAssociations = containerChassisAssociationService.getAllControllerChassisAssociations();
        assertEquals(getAssociations, containerChassisAssociationList);
    }

    @Test
    void testGetAllContainerChassisAssociationException() {
        NoRecordsFoundException exception = assertThrows(NoRecordsFoundException.class,
                () -> when(containerChassisAssociationService.getAllControllerChassisAssociations()));
        Assertions.assertEquals("No Records are found for Container Chassis Association", exception.getMessage());
    }

    @Test
    void testAddEquipRestrictions() {
        when(containerChassisAssociationRepo.save(Mockito.any())).thenReturn(containerChassisAssociation);
        addedContChassisAssoc = containerChassisAssociationService.addContainerChassisAssociation(containerChassisAssociation, header);
        assertNotNull(addedContChassisAssoc);
    }

    @Test
    void testAddContainerInitRecordNotAddedException() {
        containerChassisAssociation.setContainerInit("Tes*");
        when(containerChassisAssociationRepo.save(Mockito.any())).thenReturn(containerChassisAssociation);
        RecordNotAddedException exception = assertThrows(RecordNotAddedException.class,
                () -> when(containerChassisAssociationService.addContainerChassisAssociation(containerChassisAssociation, header)));
        assertEquals("'containerInit' value should have only alphabets", exception.getMessage());
    }

    @Test
    void testAddChassisInitRecordNotAddedException() {
        containerChassisAssociation.setChassisInit("Tes*");
        when(containerChassisAssociationRepo.save(Mockito.any())).thenReturn(containerChassisAssociation);
        RecordNotAddedException exception = assertThrows(RecordNotAddedException.class,
                () -> when(containerChassisAssociationService.addContainerChassisAssociation(containerChassisAssociation, header)));
        assertEquals("'chassisInit' value should have only alphabets", exception.getMessage());
    }

    @Test
    void testAddContNrLowHigherThanContNrHighRecordNotAddedException() {
        containerChassisAssociation.setContainerLowNumber(BigDecimal.valueOf(200));
        containerChassisAssociation.setContainerHighNumber(BigDecimal.valueOf(100));
        when(containerChassisAssociationRepo.save(Mockito.any())).thenReturn(containerChassisAssociation);
        RecordNotAddedException exception = assertThrows(RecordNotAddedException.class,
                () -> when(containerChassisAssociationService.addContainerChassisAssociation(containerChassisAssociation, header)));
        assertEquals("Container Low Number: " + containerChassisAssociation.getContainerLowNumber() + " should be less than or equal to Container High Number: "
                + containerChassisAssociation.getContainerHighNumber(), exception.getMessage());
    }

    @Test
    void testAddChassisNrLowHigherThanChassisNrHighRecordNotAddedException() {
        containerChassisAssociation.setChassisLowNumber(BigDecimal.valueOf(200));
        containerChassisAssociation.setChassisHighNumber(BigDecimal.valueOf(100));
        when(containerChassisAssociationRepo.save(Mockito.any())).thenReturn(containerChassisAssociation);
        RecordNotAddedException exception = assertThrows(RecordNotAddedException.class,
                () -> when(containerChassisAssociationService.addContainerChassisAssociation(containerChassisAssociation, header)));
        assertEquals("Chassis Low Number: " + containerChassisAssociation.getChassisLowNumber() + " should be less than or equal to Chassis High Number: "
                + containerChassisAssociation.getChassisHighNumber(), exception.getMessage());
    }

    @Test
    void testAddContainerLengthRecordNotAddedException() {
        containerChassisAssociation.setContainerLength(10);
        when(containerChassisAssociationRepo.save(Mockito.any())).thenReturn(containerChassisAssociation);
        RecordNotAddedException exception = assertThrows(RecordNotAddedException.class,
                () -> when(containerChassisAssociationService.addContainerChassisAssociation(containerChassisAssociation, header)));
        assertEquals("'containerLength' should be 20, 40, 45, 48 & 53", exception.getMessage());
    }

    @Test
    void testAddChassisLengthRecordNotAddedException() {
        containerChassisAssociation.setChassisLength(10);
        when(containerChassisAssociationRepo.save(Mockito.any())).thenReturn(containerChassisAssociation);
        RecordNotAddedException exception = assertThrows(RecordNotAddedException.class,
                () -> when(containerChassisAssociationService.addContainerChassisAssociation(containerChassisAssociation, header)));
        assertEquals("'chassisLength' should be 20, 40, 45, 48 & 53", exception.getMessage());
    }
    
    
    @ParameterizedTest
	@ValueSource(strings = {"!",""," "})
	void testUpdateAndExpireChassisAssociation(String uVersion) {
		when(containerChassisAssociationRepo.existsByAssociationIdAndUversion(Mockito.any(),Mockito.any())).thenReturn(true);
		containerChassisAssociation.setUversion(uVersion);
		containerChassisAssociation.setExpiredDateTime(null);
		when(containerChassisAssociationRepo.findById(Mockito.any())).thenReturn(Optional.of(containerChassisAssociation));
		when(containerChassisAssociationRepo.save(Mockito.any())).thenReturn(containerChassisAssociation);
		ContainerChassisAssociation updateChassisAssoc = containerChassisAssociationService.updateContainerChassisAssociation(containerChassisAssociation, header);
		assertNotNull(updateChassisAssoc);
		ContainerChassisAssociation expireChassisAssoc = containerChassisAssociationService.expireContainerChassisAssociation(containerChassisAssociation, header);
		assertNotNull(expireChassisAssoc);
	}
    
    @Test
	void testUpdateAndExpireChassisAssociationNoRecordsFoundException() {
    	when(containerChassisAssociationRepo.existsByAssociationIdAndUversion(Mockito.any(),Mockito.any())).thenReturn(false);
		assertThrows(NoRecordsFoundException.class,
				() -> when(containerChassisAssociationService.updateContainerChassisAssociation(containerChassisAssociation, header)));
		assertThrows(NoRecordsFoundException.class,
				() -> when(containerChassisAssociationService.expireContainerChassisAssociation(containerChassisAssociation, header)));
	}
    
    @Test
	void testUpdateAndExpireChassisAssociationRecordAlreadyExistsException() {
    	when(containerChassisAssociationRepo.existsByAssociationIdAndUversion(Mockito.any(),Mockito.any())).thenReturn(true);
    	containerChassisAssociation.setExpiredDateTime(new Timestamp(System.currentTimeMillis()));
		when(containerChassisAssociationRepo.findById(Mockito.any())).thenReturn(Optional.of(containerChassisAssociation));
		assertThrows(RecordAlreadyExistsException.class,
				() -> when(containerChassisAssociationService.updateContainerChassisAssociation(containerChassisAssociation, header)));
		assertThrows(RecordAlreadyExistsException.class,
				() -> when(containerChassisAssociationService.expireContainerChassisAssociation(containerChassisAssociation, header)));
	}
    
    @Test
    void testAddChassisAssociationValidation() {
		when(containerChassisAssociationRepo.existsByContainerInitAndChassisInitAndContainerLengthAndChassisLength(Mockito.any(),Mockito.any(),Mockito.any(),Mockito.any())).thenReturn(true);
		ContainerChassisAssociation containerChassisAssociation1 = containerChassisAssociationList.get(0);
		containerChassisAssociation1.setExpiredDateTime(new Timestamp(System.currentTimeMillis()));
		containerChassisAssociationList.add(containerChassisAssociation1);
		when(containerChassisAssociationRepo.findByContainerInitAndChassisInitAndContainerLengthAndChassisLength(Mockito.any(),Mockito.any(),Mockito.any(),Mockito.any())).thenReturn(containerChassisAssociationList);
		when(containerChassisAssociationRepo.save(Mockito.any())).thenReturn(containerChassisAssociation);
		ContainerChassisAssociation addChassisAssoc = containerChassisAssociationService.addContainerChassisAssociation(containerChassisAssociation, header);
		assertNotNull(addChassisAssoc);
	}
    
    @Test
    @DisplayName("Test case for Nr High & Low overlap validations")
    void testAddChasisAssociationRecordNotAddedExComparisions() {
    	 when(containerChassisAssociationRepo.existsByContainerInitAndChassisInitAndContainerLengthAndChassisLength(Mockito.any(),Mockito.any(),Mockito.any(),Mockito.any())).thenReturn(true);
    	 List<ContainerChassisAssociation> validationList = new ArrayList<>();
    	 ContainerChassisAssociation validation1 = new ContainerChassisAssociation();
    	 validation1.setAssociationId(1234567L);
    	 validation1.setContainerInit("Tes1");
    	 validation1.setContainerLowNumber(BigDecimal.valueOf(110));
    	 validation1.setContainerHighNumber(BigDecimal.valueOf(190));
         validation1.setContainerLength(20);
         validation1.setChassisInit("Test");
         validation1.setChassisLowNumber(BigDecimal.valueOf(100));
         validation1.setChassisHighNumber(BigDecimal.valueOf(200));
         validation1.setChassisLength(20);
         validation1.setAllowIndicator("Y");
         validation1.setExpiredDateTime(null);
    	 validationList.add(validation1);
         assertThrows(RecordNotAddedException.class,
 				() -> when(containerChassisAssociationService.addContainerChassisAssociation(validation1, header)));
         validationList.remove(0);
         validation1.setContainerLowNumber(BigDecimal.valueOf(90));
    	 validation1.setContainerHighNumber(BigDecimal.valueOf(210));
    	 validationList.add(validation1);
    	 when(containerChassisAssociationRepo.findByContainerInitAndChassisInitAndContainerLengthAndChassisLength(Mockito.any(),Mockito.any(),Mockito.any(),Mockito.any())).thenReturn(validationList);
         assertThrows(RecordNotAddedException.class,
 				() -> when(containerChassisAssociationService.addContainerChassisAssociation(containerChassisAssociation, header)));
 		
         validationList.remove(0);
         validation1.setContainerLowNumber(BigDecimal.valueOf(90));
    	 validation1.setContainerHighNumber(BigDecimal.valueOf(190));
    	 validationList.add(validation1);
    	 when(containerChassisAssociationRepo.findByContainerInitAndChassisInitAndContainerLengthAndChassisLength(Mockito.any(),Mockito.any(),Mockito.any(),Mockito.any())).thenReturn(validationList);
         assertThrows(RecordNotAddedException.class,
 				() -> when(containerChassisAssociationService.addContainerChassisAssociation(containerChassisAssociation, header)));
 		
         validationList.remove(0);
         validation1.setContainerLowNumber(BigDecimal.valueOf(100));
    	 validation1.setContainerHighNumber(BigDecimal.valueOf(200));
    	 validation1.setChassisLowNumber(BigDecimal.valueOf(90));
    	 validation1.setChassisHighNumber(BigDecimal.valueOf(210));
    	 validationList.add(validation1);
    	 when(containerChassisAssociationRepo.findByContainerInitAndChassisInitAndContainerLengthAndChassisLength(Mockito.any(),Mockito.any(),Mockito.any(),Mockito.any())).thenReturn(validationList);
         assertThrows(RecordNotAddedException.class,
 				() -> when(containerChassisAssociationService.addContainerChassisAssociation(containerChassisAssociation, header)));
    
         validationList.remove(0);
    	 validation1.setChassisLowNumber(BigDecimal.valueOf(90));
    	 validation1.setChassisHighNumber(BigDecimal.valueOf(190));
    	 validationList.add(validation1);
    	 when(containerChassisAssociationRepo.findByContainerInitAndChassisInitAndContainerLengthAndChassisLength(Mockito.any(),Mockito.any(),Mockito.any(),Mockito.any())).thenReturn(validationList);
         assertThrows(RecordNotAddedException.class,
 				() -> when(containerChassisAssociationService.addContainerChassisAssociation(containerChassisAssociation, header)));
         
         validationList.remove(0);
    	 validation1.setChassisLowNumber(BigDecimal.valueOf(110));
    	 validation1.setChassisHighNumber(BigDecimal.valueOf(210));
    	 validationList.add(validation1);
    	 when(containerChassisAssociationRepo.findByContainerInitAndChassisInitAndContainerLengthAndChassisLength(Mockito.any(),Mockito.any(),Mockito.any(),Mockito.any())).thenReturn(validationList);
         assertThrows(RecordNotAddedException.class,
 				() -> when(containerChassisAssociationService.addContainerChassisAssociation(containerChassisAssociation, header)));

    }
}
