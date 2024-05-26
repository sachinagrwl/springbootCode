package com.nscorp.obis.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nscorp.obis.exception.InvalidDataException;
import com.nscorp.obis.response.data.APIResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.nscorp.obis.domain.DamageReason;
import com.nscorp.obis.dto.DamageReasonDTO;
import com.nscorp.obis.dto.mapper.DamageReasonMapper;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.repository.DamageCategoryRepository;
import com.nscorp.obis.repository.DamageReasonRepository;
import org.springframework.http.ResponseEntity;

public class DamageReasonServiceImplTest {

	@Mock
	DamageReasonRepository damageReasonRepository;

	@Mock
	DamageCategoryRepository damageCategoryRepository;

	@Mock
	DamageReasonMapper damageReasonMapper;

	@InjectMocks
	DamageReasonServiceImpl damageReasonServiceImpl;

	DamageReasonDTO damageReasonDTO;

	DamageReason damageReason;
	List<DamageReason> damageReasonList;

	Map<String, String> headers;
	Integer catCd;
	String reasonCd;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		damageReasonDTO = new DamageReasonDTO();
		damageReason = new DamageReason();
		headers = new HashMap<>();
		headers.put("userid", "test");
		catCd = 3;
		reasonCd="a";
		when(damageReasonMapper.damageReasonDTOToDamageReason(Mockito.any())).thenReturn(damageReason);
		when(damageReasonMapper.damageReasonToDamageReasonDTO(Mockito.any())).thenReturn(damageReasonDTO);
		damageReasonList= Arrays.asList(damageReason);
	}

	@AfterEach
	void tearDown() {
		damageReasonDTO = null;
		damageReason = null;
		headers = null;
	}

	@Test
	void testAddDamageReason() {
		when(damageReasonRepository.existsByCatCdAndReasonCd(Mockito.any(), Mockito.any())).thenReturn(false);
		when(damageCategoryRepository.existsByCatCd(Mockito.any())).thenReturn(true);
		DamageReasonDTO response = damageReasonServiceImpl.addDamageReason(damageReasonDTO, headers);
		assertNotNull(response);
		when(damageReasonRepository.existsByCatCdAndReasonCd(Mockito.any(), Mockito.any())).thenReturn(true);
		assertThrows(RecordAlreadyExistsException.class,
				() -> damageReasonServiceImpl.addDamageReason(damageReasonDTO, headers));
		when(damageReasonRepository.existsByCatCdAndReasonCd(Mockito.any(), Mockito.any())).thenReturn(true);
		when(damageCategoryRepository.existsByCatCd(Mockito.any())).thenReturn(false);
		assertThrows(NoRecordsFoundException.class,
				() -> damageReasonServiceImpl.addDamageReason(damageReasonDTO, headers));
	}

	@Test
	void testUpdateDamageReason() {
		when(damageReasonRepository.findByCatCdAndReasonCd(Mockito.any(), Mockito.any())).thenReturn(damageReason);
		DamageReasonDTO response = damageReasonServiceImpl.updateDamageReason(damageReasonDTO, headers);
		assertNotNull(response);
		when(damageReasonRepository.findByCatCdAndReasonCd(Mockito.any(), Mockito.any())).thenReturn(null);
		assertThrows(NoRecordsFoundException.class,
				() -> damageReasonServiceImpl.updateDamageReason(damageReasonDTO, headers));
	}
		@Test
	void testDeleteDamageReason() throws SQLException {

		damageReason.setCatCd(catCd);
        damageReason.setReasonCd(reasonCd);
		when(damageReasonRepository.existsByCatCdAndReasonCd(Mockito.any(),Mockito.any())).thenReturn(true);
		damageReasonServiceImpl.deleteDamageReasons(damageReason);
		//works
	}
	@Test
	void testDeleteDamageReasonException() throws SQLException{
		damageReason.setCatCd(catCd);
		damageReason.setReasonCd(reasonCd);
		//damageReason.setReasonCd(reasonCd);
		when(damageReasonRepository.existsByCatCdAndReasonCd(Mockito.any(),Mockito.any())).thenReturn(false);


		NoRecordsFoundException exception = assertThrows(NoRecordsFoundException.class,
				() -> when(damageReasonServiceImpl.deleteDamageReasons(damageReason)));
		assertEquals("No Damage Reason found with given category and reason code", exception.getMessage());

		damageReason.setCatCd(null);
		InvalidDataException exception1 = assertThrows(InvalidDataException.class,
				() -> when(damageReasonServiceImpl.deleteDamageReasons(damageReason)));
		assertEquals("Damage Reason CatCd Can't Be Null", exception1.getMessage());

		damageReason.setCatCd(catCd);
		damageReason.setReasonCd(null);
		InvalidDataException exception2 = assertThrows(InvalidDataException.class,
				() -> when(damageReasonServiceImpl.deleteDamageReasons(damageReason)));
		assertEquals("Damage Reason Code Can't Be Null", exception2.getMessage());

	}
	/*
		public DamageReason deleteDamageReasons(DamageReason damageReason) {
		if(damageReason.getCatCd() ==null){
			throw new InvalidDataException("Damage Reason CatCd Can't Be Null");
		}
		if(damageReason.getReasonCd() ==null){
			throw new InvalidDataException("Damage Reason Code Can't Be Null");
		}
		if(!damageReasonRepo.existsByCatCdAndReasonCd(damageReason.getCatCd(),damageReason.getReasonCd())){
			throw new NoRecordsFoundException(
					"No Damage Reason found with given category and reason code");

		}
		damageReasonRepo.delete(damageReason);
		return damageReason;
	}
	 */
	@Test
	void getDamageReason() throws SQLException{
		when(damageReasonRepository.findByCatCd(Mockito.any())).thenReturn(damageReasonList);
		damageReasonServiceImpl.getDamageReason(catCd);



	}
	@Test
	void testNoRecordFoundException() throws SQLException{
		damageReasonList = null;
		NoRecordsFoundException exception = assertThrows(NoRecordsFoundException.class,
				() -> when(damageReasonServiceImpl.getDamageReason(catCd)));

	}

}
