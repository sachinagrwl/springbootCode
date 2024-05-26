package com.nscorp.obis.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nscorp.obis.repository.TerminalTrainRepository;
import com.nscorp.obis.repository.TrainRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import com.nscorp.obis.domain.Block;
import com.nscorp.obis.dto.BlockDTO;
import com.nscorp.obis.exception.InvalidDataException;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.exception.RecordNotAddedException;
import com.nscorp.obis.exception.RecordNotDeletedException;
import com.nscorp.obis.repository.BlockRepository;

public class BlockServiceTest {

	@Mock
	BlockRepository blockRepository;

	@Mock
	TrainRepository trainRepository;

	@Mock
	TerminalTrainRepository termTrainRepository;

	@InjectMocks
	BlockServiceImpl blockService;

	Block block;
	Block blockResource;
	Block blockUpdated;
	Block blockAdded;

	BlockDTO blockDto;
	List<BlockDTO> blockDtoList;
	List<BlockDTO> blockDTOList;
	ResponseEntity<Object> responseEntity;
	String url;
	List<Block> blockList;
	Map<String, String> header;
	String type, trainNr;
	Long termId;

	@SuppressWarnings("deprecation")
	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		block = new Block();
		blockList = new ArrayList<>();
		blockDto = new BlockDTO();
		blockDtoList = new ArrayList<>();
		block.setTermId(61110L);
		block.setTrainNr("20E");
		block.setBlockId(1144165810076L);
		block.setBlockNm("This should not be null");
		block.setParantBlockId(null);
		block.setBlockOrder("4");
		block.setBlockPriority(2);
		block.setSwInterchange("Y");
		block.setAllowSameCar("Y");
		block.setBlockMon("Y");
		block.setBlockTue("Y");
		block.setBlockWed("Y");
		block.setBlockThu("Y");
		block.setBlockFri("Y");
		block.setBlockSat("Y");
		block.setBlockSun("Y");
		block.setHightFeet(20);
		block.setHightInches(3);
		block.setWeight(125);

		blockDto.setTermId(61110L);
		blockDto.setTrainNr("20E");
		blockDto.setBlockId(1144165810076L);
		blockDto.setBlockNm("This should not be 0");
		blockDto.setParantBlockId(null);
		blockDto.setBlockOrder("34");
		blockDto.setBlockPriority(3);
		blockDto.setSwInterchange("Y");
		blockDto.setAllowSameCar("Y");
		blockDto.setBlockMon("Y");
		blockDto.setBlockTue("Y");
		blockDto.setBlockWed("Y");
		blockDto.setBlockThu("Y");
		blockDto.setBlockFri("Y");
		blockDto.setBlockSat("Y");
		blockDto.setBlockSun("Y");
		blockDto.setHightFeet(20);
		blockDto.setHightInches(3);
		blockDto.setWeight(125);
		blockList.add(block);
		// blockDtoList.add(blockDto);

		termId = 6000L;
		trainNr = "20E";
		header = new HashMap<String, String>();
		header.put("userid", "Test");
		header.put("extensionschema", "Test");

	}

	@AfterEach
	void tearDown() throws Exception {

		blockDto = null;
		blockDtoList = null;
		block = null;
		blockList = null;
	}

	@Test
	void testGetBlocks() {
		when(blockRepository.findByTermIdAndTrainNr(Mockito.any(), Mockito.anyString())).thenReturn(blockList);
		List<Block> getBlock = blockService.getAllBlocks(termId, trainNr);
		assertEquals(getBlock, blockList);

	}

	@Test
	void testGetBlockException() {

		NoRecordsFoundException exception = assertThrows(NoRecordsFoundException.class,
				() -> when(blockService.getAllBlocks(termId, trainNr)));
		assertEquals("No records found", exception.getMessage());

	}

	@Test
	void testUpdateBlockNoRecordsFoundException() {
		NoRecordsFoundException exception = assertThrows(NoRecordsFoundException.class,
				() -> blockService.updateBlock(block, header));
		assertEquals("Record with BlockID " + block.getBlockId() + " Not Found!", exception.getMessage());

		NoRecordsFoundException blocksort = assertThrows(NoRecordsFoundException.class,
				() -> blockService.getAllBlockSort(termId, trainNr));
		assertEquals("No records found", blocksort.getMessage());

	}
	
	@Test
	void testAddRedorder() {
		List<Block> blockList = new ArrayList<>();
		block.setBlockOrder("N");
		blockList.add(block);
		
		
		Block block1 = new Block();
		block1 = block;
		block1.setBlockOrder("2");
		blockList.add(block1);
		when(blockRepository.findByTermIdAndTrainNrOrderByBlockOrderAsc(Mockito.any(),Mockito.any())).thenReturn(blockList);
		block.setBlockOrder("1");
		when(blockRepository.existsByBlockId(Mockito.any())).thenReturn(false);
		when(blockRepository.save(Mockito.any())).thenReturn(block);
		blockAdded = blockService.addBlock(block, header);	
		
	}
	
	@Test
	void testAddRecordNotAddedException(){
		block.setActiveDays(Collections.emptyList());
		when(blockRepository.existsByBlockId(Mockito.any())).thenReturn(false);
		when(blockRepository.findByBlockId(block.getBlockId())).thenReturn(block);

	}

	@Test
	void testUpdateBlock() {

		when(blockRepository.existsByBlockId(block.getBlockId())).thenReturn(true);
		when(blockRepository.findByBlockId(block.getBlockId())).thenReturn(block);
		when(blockRepository.save(block)).thenReturn(block);
		Block blockUpdated = blockService.updateBlock(block, header);

		block.setBlockOrder("N");
		blockUpdated = blockService.updateBlock(block, header);

	}

	@Test
	void testAddBlocks() {
		when(blockRepository.existsByBlockId(Mockito.any())).thenReturn(false);
		when(blockRepository.save(Mockito.any())).thenReturn(block);

		when(trainRepository.existsByTrainNumber(Mockito.any())).thenReturn(true);
		when(termTrainRepository.save(Mockito.any())).thenReturn(true);

		blockAdded = blockService.addBlock(block, header);
		assertNotNull(blockAdded);
		
		block.setBlockOrder("N");
		when(blockRepository.existsByBlockId(Mockito.any())).thenReturn(false);
		when(blockRepository.save(Mockito.any())).thenReturn(block);


		blockAdded = blockService.addBlock(block, header);

	}



	@Test
	void testAddBlocksRecordAlreadyExistsException() {
		Block b1 = new Block();
		b1.setWeight(125);
		b1.setBlockId(11441658100764L);
		b1.setBlockNm("Hello");
		when(blockRepository.existsByBlockNm(Mockito.any())).thenReturn(true);
		RecordAlreadyExistsException exception1 = assertThrows(RecordAlreadyExistsException.class,
				() -> when(blockService.addBlock(block, header)));
		assertEquals("This block name already exists for this train and terminal", exception1.getMessage());

		Block b = new Block();
		b.setWeight(125);
		b.setBlockId(11441658100764L);
		when(blockRepository.existsByBlockId(Mockito.any())).thenReturn(true);

		RecordAlreadyExistsException exception = assertThrows(RecordAlreadyExistsException.class,
				() -> when(blockService.addBlock(block, header)));
		assertEquals("Block is already exists:1144165810076", exception.getMessage());

	}

	@Test
	void testAddBlocksInvalidDataException() {
		when(blockRepository.existsByBlockId(Mockito.any())).thenReturn(false);

		block.setHightInches(null);
		block.setHightFeet(13);
		InvalidDataException exception = assertThrows(InvalidDataException.class,
				() -> when(blockService.addBlock(block, header)));
		assertEquals("Inches should between 6 to 9 ", exception.getMessage());

		block.setHightFeet(17);
		block.setHightInches(null);
		InvalidDataException exception1 = assertThrows(InvalidDataException.class,
				() -> when(blockService.addBlock(block, header)));
		assertEquals("Inches should be less than 6 ", exception1.getMessage());

		block.setHightFeet(15);
		block.setHightInches(null);
		InvalidDataException exception3 = assertThrows(InvalidDataException.class,
				() -> when(blockService.addBlock(block, header)));
		assertEquals("Inches should between 0 to 9 ", exception3.getMessage());

		block.setHightInches(1);
		block.setHightFeet(null);
		InvalidDataException exception4 = assertThrows(InvalidDataException.class,
				() -> when(blockService.addBlock(block, header)));
		assertEquals("Only null value accepted for Inches ", exception4.getMessage());

		block.setHightFeet(15);
		block.setHightInches(10);
		InvalidDataException exception5 = assertThrows(InvalidDataException.class,
				() -> when(blockService.addBlock(block, header)));
		assertEquals("Inches should between 0 to 9 ", exception5.getMessage());

		block.setHightFeet(13);
		block.setHightInches(10);
		InvalidDataException exception7 = assertThrows(InvalidDataException.class,
				() -> when(blockService.addBlock(block, header)));
		assertEquals("Inches should between 6 to 9 ", exception7.getMessage());

		block.setHightFeet(17);
		block.setHightInches(10);
		InvalidDataException exception8 = assertThrows(InvalidDataException.class,
				() -> when(blockService.addBlock(block, header)));
		assertEquals("Inches should be less than 6 ", exception8.getMessage());

		block.setHightFeet(21);
		block.setHightInches(3);
		InvalidDataException exception9 = assertThrows(InvalidDataException.class,
				() -> when(blockService.addBlock(block, header)));
		assertEquals("Height Feet should between 13 to 20 ", exception9.getMessage());

		block.setHightFeet(20);
		block.setHightInches(null);
		InvalidDataException exception92 = assertThrows(InvalidDataException.class,
				() -> when(blockService.addBlock(block, header)));
		assertEquals("Inches should be 3 ", exception92.getMessage());
		
		block.setHightFeet(20);
		block.setHightInches(4);
		InvalidDataException exception91 = assertThrows(InvalidDataException.class,
				() -> when(blockService.addBlock(block, header)));
		assertEquals("Inches should be 3 ", exception91.getMessage());
		
		block.getBlockOrder().isEmpty();
		block.setBlockOrder("100");
		InvalidDataException exception11 = assertThrows(InvalidDataException.class,
				() -> when(blockService.addBlock(block, header)));
		assertEquals("Block Order should be between 1 to 99, Only N or Blank is allowed", exception11.getMessage());

	}
	
	@Test
	void testUpdateBlocksInvalidDataException() {
		when(blockRepository.existsByBlockId(block.getBlockId())).thenReturn(true);
		when(blockRepository.findByBlockId(block.getBlockId())).thenReturn(block);
		when(blockRepository.save(block)).thenReturn(block);
		block.setHightInches(null);
		block.setHightFeet(13);
		InvalidDataException exception = assertThrows(InvalidDataException.class,
				() -> when(blockService.updateBlock(block, header)));
		assertEquals("Inches should between 6 to 9 ", exception.getMessage());

		block.setHightFeet(17);
		block.setHightInches(null);
		InvalidDataException exception1 = assertThrows(InvalidDataException.class,
				() -> when(blockService.updateBlock(block, header)));
		assertEquals("Inches should be less than 6 ", exception1.getMessage());

		block.setHightFeet(15);
		block.setHightInches(null);
		InvalidDataException exception3 = assertThrows(InvalidDataException.class,
				() -> when(blockService.updateBlock(block, header)));
		assertEquals("Inches should between 0 to 9 ", exception3.getMessage());

		block.setHightInches(1);
		block.setHightFeet(null);
		InvalidDataException exception4 = assertThrows(InvalidDataException.class,
				() -> when(blockService.updateBlock(block, header)));
		assertEquals("Only null value accepted for Inches ", exception4.getMessage());

		block.setHightFeet(15);
		block.setHightInches(10);
		InvalidDataException exception5 = assertThrows(InvalidDataException.class,
				() -> when(blockService.updateBlock(block, header)));
		assertEquals("Inches should between 0 to 9 ", exception5.getMessage());

		block.setHightFeet(13);
		block.setHightInches(10);
		InvalidDataException exception7 = assertThrows(InvalidDataException.class,
				() -> when(blockService.updateBlock(block, header)));
		assertEquals("Inches should between 6 to 9 ", exception7.getMessage());

		block.setHightFeet(17);
		block.setHightInches(10);
		InvalidDataException exception8 = assertThrows(InvalidDataException.class,
				() -> when(blockService.updateBlock(block, header)));
		assertEquals("Inches should be less than 6 ", exception8.getMessage());

		block.setHightFeet(21);
		block.setHightInches(3);
		InvalidDataException exception9 = assertThrows(InvalidDataException.class,
				() -> when(blockService.updateBlock(block, header)));
		assertEquals("Height Feet should between 13 to 20 ", exception9.getMessage());

		block.setHightFeet(20);
		block.setHightInches(null);
		InvalidDataException exception92 = assertThrows(InvalidDataException.class,
				() -> when(blockService.updateBlock(block, header)));
		assertEquals("Inches should be 3 ", exception92.getMessage());
		
		block.setHightFeet(20);
		block.setHightInches(4);
		InvalidDataException exception91 = assertThrows(InvalidDataException.class,
				() -> when(blockService.updateBlock(block, header)));
		assertEquals("Inches should be 3 ", exception91.getMessage());
		
		block.getBlockOrder().isEmpty();
		block.setBlockOrder("100");
		InvalidDataException exception11 = assertThrows(InvalidDataException.class,
				() -> when(blockService.updateBlock(block, header)));
		assertEquals("Block Order should be between 1 to 99, Only N or Blank is allowed", exception11.getMessage());
		
		

	}

	@Test
	void testgetAllBlockSort() {
		when(blockRepository.findByTermIdAndTrainNr(Mockito.any(), Mockito.anyString())).thenReturn(blockList);
		List<Block> getBlocksort = blockService.getAllBlockSort(termId, trainNr);
		assertEquals(getBlocksort, blockList);

	}

	@Test
	void testDeleteBlock() {
		when(blockRepository.existsByBlockId(Mockito.any())).thenReturn(true);
		doNothing().when(blockRepository).deleteByBlockId(block.getBlockId());
		blockService.deleteBlock(block);
		verify(blockRepository, times(1)).deleteByBlockId(block.getBlockId());
	}

	@Test
	void testDeleteException() {
		when(blockRepository.existsByBlockId(Mockito.any())).thenReturn(false);
		RecordNotDeletedException exception = assertThrows(RecordNotDeletedException.class,
				() -> when(blockService.deleteBlock(block)));
		assertEquals(block.getBlockId() + " Record Not Found!", exception.getMessage());

	}

}
