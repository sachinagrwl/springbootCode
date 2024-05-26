package com.nscorp.obis.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
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
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;

import com.nscorp.obis.domain.Block;
import com.nscorp.obis.domain.SortFilter;
import com.nscorp.obis.dto.BlockDTO;
import com.nscorp.obis.dto.mapper.BlockMapper;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.SizeExceedException;
import com.nscorp.obis.repository.BlockRepository;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.services.BlockService;

//@SpringBootTest
public class BlockControllerTest {
	@Mock
	BlockService blockService;

	@Mock
	BlockMapper blockMapper;
	
	@Mock
	BlockRepository blockRepo;

	@InjectMocks
	BlockController blockController;

	Block block;
	BlockDTO blockDto;
	List<BlockDTO> blockDtoList;
	List<BlockDTO> blockDTOList;
	ResponseEntity<Object> responseEntity;

	List<Block> blockList;
	Map<String, String> header;
	String trainNr;
	String[] sort;
	Long termId;

	@SuppressWarnings("deprecation")
	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		block = new Block();
		blockDto = new BlockDTO();
		blockDtoList = new ArrayList<>();
		blockList = new ArrayList<>();
		// block.setTrainNr("INT FLAT");
		block.setBlockId(114536627525278L);
		block.setBlockNm("The world limit ");
		block.setBlockOrder("13");
		block.setBlockPriority(6);
		block.setAllowSameCar("N");
		block.setBlockMon("Y");
		block.setBlockTue("Y");
		block.setBlockWed("Y");
		block.setBlockThu("Y");
		block.setBlockFri("Y");
		block.setBlockSat("Y");
		block.setBlockSun("Y");
		block.setHightFeet(20);
		block.setHightInches(3);

		blockDto.setBlockId(114536627525278L);
		blockDto.setBlockNm("The world limit ");
		blockDto.setBlockOrder("13");
		blockDto.setBlockPriority(6);
		blockDto.setAllowSameCar("N");
		// blockDto.getActiveDays();
		blockDto.setBlockMon("Y");
		blockDto.setBlockTue("Y");
		blockDto.setBlockWed("Y");
		blockDto.setBlockThu("Y");
		blockDto.setBlockFri("Y");
		blockDto.setBlockSat("Y");
		blockDto.setBlockSun("Y");
		blockDto.setHightFeet(20);
		blockDto.setHightInches(3);

		blockList.add(block);
		
		termId = 354223L;
		trainNr = "XYZ";
		header = new HashMap<String, String>();
		header.put("userid", "Test");
		header.put("extensionschema", "Test");
		sort = new String[] { "blockOrder, asc" };

	}

	@AfterEach
	void tearDown() throws Exception {

		blockDto = null;
		blockDtoList = null;
		blockDto = null;
		blockList = null;
	}

	@Test
	void testGetBlock() throws SQLException  {
		
		when(blockService.getAllBlocks(termId, trainNr)).thenReturn(null);
		ResponseEntity<APIResponse<List<BlockDTO>>> blockList1 = blockController.getBlocksByTermIdAndTrainNr(termId,
				trainNr);
		assertEquals(blockList1.getStatusCodeValue(), 200);
		
		when(blockService.getAllBlocks(termId, trainNr)).thenReturn(Collections.emptyList());
		 blockList1 = blockController.getBlocksByTermIdAndTrainNr(termId,
					trainNr);
			assertEquals(blockList1.getStatusCodeValue(), 200);
		
		when(blockService.getAllBlocks(termId, trainNr)).thenReturn(blockList);
		
		 blockList1 = blockController.getBlocksByTermIdAndTrainNr(termId,
				trainNr);
		assertEquals(blockList1.getStatusCodeValue(), 200);
		
	}

	@Test
	void testNullListGet() {
		blockList = Collections.emptyList();
		when(blockService.getAllBlocks(termId, trainNr)).thenReturn(blockList);
		assertEquals(Collections.EMPTY_LIST, blockList);

	}

	@Test
	void testBlockNoRecordsFoundException() {
		when(blockService.getAllBlocks(termId, trainNr)).thenThrow(new NoRecordsFoundException());
		when(blockService.updateBlock(Mockito.any(), Mockito.any())).thenThrow(new NoRecordsFoundException());
		when(blockService.addBlock(Mockito.any(), Mockito.any())).thenThrow(new NoRecordsFoundException());
		ResponseEntity<APIResponse<List<BlockDTO>>> blockList = blockController.getBlocksByTermIdAndTrainNr(termId,
				trainNr);
		ResponseEntity<APIResponse<BlockDTO>> blockUpdate = blockController.updateBlock(Mockito.any(), Mockito.any());
		ResponseEntity<APIResponse<BlockDTO>> addBlock = blockController.addBlocks(Mockito.any(), Mockito.any());

		assertEquals(blockList.getStatusCodeValue(), 404);
		assertEquals(blockUpdate.getStatusCodeValue(), 404);
		assertEquals(addBlock.getStatusCodeValue(), 404);
		
	    when(blockService.getAllBlockSort(termId, trainNr)).thenReturn(null);
	    when(blockController.getAllBlocksSort(termId, trainNr, sort)).thenThrow(new NoRecordsFoundException());
		ResponseEntity<APIResponse<List<BlockDTO>>> Blocksort =blockController.getAllBlocksSort(termId, trainNr, sort);
		assertEquals(Blocksort.getStatusCodeValue(),404);



	}

	@Test

	void testBlockSizeExceedException()

	{

		when(blockService.updateBlock(Mockito.any(), Mockito.any())).thenThrow(new SizeExceedException("Size Exceed"));

		when(blockService.addBlock(Mockito.any(), Mockito.any())).thenThrow(new SizeExceedException("Size Exceed"));

		ResponseEntity<APIResponse<BlockDTO>> blockUpdate = blockController.updateBlock(Mockito.any(), Mockito.any());

		ResponseEntity<APIResponse<BlockDTO>> addBlock = blockController.addBlocks(Mockito.any(), Mockito.any());

		assertEquals(blockUpdate.getStatusCodeValue(), 411);

		assertEquals(addBlock.getStatusCodeValue(), 411);

	}

	@Test

	void testBlockNullPointerException() {

		when(blockService.updateBlock(Mockito.any(), Mockito.any()))
				.thenThrow(new NullPointerException("Null pointer"));

		when(blockService.addBlock(Mockito.any(), Mockito.any())).thenThrow(new NullPointerException());

		ResponseEntity<APIResponse<BlockDTO>> blockUpdate = blockController.updateBlock(Mockito.any(), Mockito.any());

		ResponseEntity<APIResponse<BlockDTO>> addBlock = blockController.addBlocks(Mockito.any(), Mockito.any());

		assertEquals(blockUpdate.getStatusCodeValue(), 400);

		assertEquals(addBlock.getStatusCodeValue(), 400);

	}

	@Test

	void testBlockException() {

		when(blockService.getAllBlocks(termId, trainNr)).thenThrow(new RuntimeException());

		when(blockService.updateBlock(Mockito.any(), Mockito.any())).thenThrow(new RuntimeException());

		when(blockService.addBlock(Mockito.any(), Mockito.any())).thenThrow(new RuntimeException());
		
		when(blockService.deleteBlock(Mockito.any())).thenThrow(new RuntimeException());
		


		ResponseEntity<APIResponse<List<BlockDTO>>> blockList = blockController.getBlocksByTermIdAndTrainNr(termId,
				trainNr);

		ResponseEntity<APIResponse<BlockDTO>> blockUpdate = blockController.updateBlock(Mockito.any(), Mockito.any());

		ResponseEntity<APIResponse<BlockDTO>> addBlock = blockController.addBlocks(Mockito.any(), Mockito.any());
		
		ResponseEntity<APIResponse<BlockDTO>> deleteBlock = blockController.deleteBlock((Mockito.any()));

		
		assertEquals(blockList.getStatusCodeValue(), 500);

		assertEquals(blockUpdate.getStatusCodeValue(), 500);

		assertEquals(addBlock.getStatusCodeValue(), 500);

		assertEquals(deleteBlock.getStatusCodeValue(), 500);
		

		
		 when(blockService.getAllBlockSort(termId, trainNr)).thenReturn(null);
		    when(blockController.getAllBlocksSort(termId, trainNr, sort)).thenThrow(new RuntimeException());
			ResponseEntity<APIResponse<List<BlockDTO>>> Blocksort =blockController.getAllBlocksSort(termId, trainNr, sort);
			assertEquals(Blocksort.getStatusCodeValue(),500);

	}

	@Test
	void testUpdate() {
		when(blockMapper.blockDTOToBlock(Mockito.any())).thenReturn(block);
		when(blockService.updateBlock(Mockito.any(), Mockito.any())).thenReturn(block);
		when(blockMapper.blockToBlockDTO(Mockito.any())).thenReturn(blockDto);
		ResponseEntity<APIResponse<BlockDTO>> codeUpdated = blockController.updateBlock(blockDto, header);
		// assertEquals(codeUpdated.getBody().getData(),blockDto);
		assertNotNull(codeUpdated.getBody().getData());
	}

	@Test
	void testAddBlocks() {
		when(blockMapper.blockDTOToBlock(Mockito.any())).thenReturn(block);
		when(blockService.addBlock(Mockito.any(), Mockito.any())).thenReturn(block);
		when(blockMapper.blockToBlockDTO(Mockito.any())).thenReturn(blockDto);
		ResponseEntity<APIResponse<BlockDTO>> addedBlocks = blockController.addBlocks(blockDto, header);
		assertNotNull(addedBlocks.getBody());
	}

	@Test
	void testGetAllBlockSort() {
		when(blockRepo.findByTermIdAndTrainNr(termId, trainNr, Sort.by(SortFilter.sortOrder(sort)))).thenReturn(blockList);
		when(blockRepo.saveAndFlush(Mockito.any())).thenReturn(block);
		when(blockMapper.blockDTOToBlock(Mockito.any())).thenReturn(block);
		when(blockService.getAllBlockSort(Mockito.any(), Mockito.any())).thenReturn(blockList);
		when(blockMapper.blockToBlockDTO(Mockito.any())).thenReturn(blockDto);
		ResponseEntity<APIResponse<List<BlockDTO>>> blockSort = blockController.getAllBlocksSort(termId, trainNr, sort);
		//assertNotNull(blockSort.getBody());
		assertEquals(blockSort.getStatusCodeValue(), 200);

	}

	@Test
	void testBlockDelete() {
		when(blockMapper.blockDTOToBlock(Mockito.any())).thenReturn(block);
		when(blockService.deleteBlock(Mockito.any())).thenReturn(block);
		when(blockMapper.blockToBlockDTO(Mockito.any())).thenReturn(blockDto);
		ResponseEntity<APIResponse<BlockDTO>> deleteList = blockController.deleteBlock(blockDto);
		assertEquals(deleteList.getStatusCodeValue(), 200);
	}

}
