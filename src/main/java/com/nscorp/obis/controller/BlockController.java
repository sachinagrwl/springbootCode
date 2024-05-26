package com.nscorp.obis.controller;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.nscorp.obis.common.ControllerConstants;
import com.nscorp.obis.domain.Block;
import com.nscorp.obis.domain.SortFilter;
import com.nscorp.obis.dto.BlockDTO;
import com.nscorp.obis.dto.mapper.BlockMapper;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.SizeExceedException;
import com.nscorp.obis.repository.BlockRepository;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.response.data.ResponseStatusCode;
import com.nscorp.obis.services.BlockService;

@Validated
@RestController
@CrossOrigin
@RequestMapping("/")
public class BlockController {

	@Autowired(required = true)
	BlockService blockService;

	@Autowired
	BlockRepository blockRepo;

	@GetMapping(ControllerConstants.GET_BLOCKS)
	public ResponseEntity<APIResponse<List<BlockDTO>>> getBlocksByTermIdAndTrainNr(
			@NotNull(message = "Term Id should not be NULL.") @Min(value = 0, message = "Term Id value must be greater than or equal to 0") @Digits(integer = 15, fraction = 0, message = "Term Id should have 15 digits") @RequestParam("term-id") Long termId,

			@NotEmpty(message = "Train Nr should not be NULL.") @Size(min = 3, max = 4, message = "Train Nr should be between 3 and 4 Char") @RequestParam("train-nr") String trainNr) {
		try {
			List<BlockDTO> blockDtoList = Collections.emptyList();
			List<Block> blockList = blockService.getAllBlocks(termId, trainNr);
			if (blockList != null && !blockList.isEmpty()) {
				blockDtoList = blockList.stream().map(BlockMapper.INSTANCE::blockToBlockDTO)
						.collect(Collectors.toList());
			}
			APIResponse<List<BlockDTO>> responseObj = new APIResponse<>(Arrays.asList("Successfully retrieved data!"),
					blockDtoList, ResponseStatusCode.SUCCESS);
			return ResponseEntity.status(HttpStatus.OK).body(responseObj);
		} catch (NoRecordsFoundException e) {
			APIResponse<List<BlockDTO>> responseObj = new APIResponse<>(Arrays.asList("No Records Found"),
					ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
		} catch (Exception e) {
			APIResponse<List<BlockDTO>> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.FAILURE);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
		}
	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	@PostMapping(value = ControllerConstants.GET_BLOCKS)
	public ResponseEntity<APIResponse<BlockDTO>> addBlocks(@Valid @NotNull @RequestBody BlockDTO BlockDto,
			@RequestHeader Map<String, String> headers) {
		try {
			Block block = BlockMapper.INSTANCE.blockDTOToBlock(BlockDto);
			Block blockAdded = blockService.addBlock(block, headers);
			BlockDTO addBlock = BlockMapper.INSTANCE.blockToBlockDTO(blockAdded);
			APIResponse<BlockDTO> responseObj = new APIResponse<>(Arrays.asList("Successfully added data!"), addBlock,
					ResponseStatusCode.SUCCESS);
			return ResponseEntity.status(HttpStatus.OK).body(responseObj);
		} catch (NoRecordsFoundException e) {
			APIResponse<BlockDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
		} catch (NullPointerException e) {
			APIResponse<BlockDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObj);
		} catch (SizeExceedException e) {
			APIResponse<BlockDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.FAILURE);
			return ResponseEntity.status(HttpStatus.LENGTH_REQUIRED).body(responseObj);
		} catch (Exception e) {
			APIResponse<BlockDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.FAILURE);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
		}
	}

	@PutMapping(value = ControllerConstants.GET_BLOCKS)
	public ResponseEntity<APIResponse<BlockDTO>> updateBlock(@Valid @NotNull @RequestBody BlockDTO blockDTO,
			@RequestHeader Map<String, String> headers) {
		try {
			Block block = BlockMapper.INSTANCE.blockDTOToBlock(blockDTO);
			Block blockAdded = blockService.updateBlock(block, headers);
			BlockDTO updateBlock = BlockMapper.INSTANCE.blockToBlockDTO(blockAdded);
			APIResponse<BlockDTO> responseObj = new APIResponse<>(Arrays.asList("Successfully updated data!"),
					updateBlock, ResponseStatusCode.SUCCESS);
			return ResponseEntity.status(HttpStatus.OK).body(responseObj);
		} catch (NoRecordsFoundException e) {
			APIResponse<BlockDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
		} catch (NullPointerException e) {
			APIResponse<BlockDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObj);
		} catch (SizeExceedException e) {
			APIResponse<BlockDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.FAILURE);
			return ResponseEntity.status(HttpStatus.LENGTH_REQUIRED).body(responseObj);
		} catch (Exception e) {
			APIResponse<BlockDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.FAILURE);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
		}
	}

	@DeleteMapping(value = ControllerConstants.DELETE_BLOCKS)
	public ResponseEntity<APIResponse<BlockDTO>> deleteBlock(@RequestBody BlockDTO blockDTO) {
		APIResponse<BlockDTO> singleDtoDelResponse;
		AtomicInteger errorCount = new AtomicInteger();
		try {
			Block block = BlockMapper.INSTANCE.blockDTOToBlock(blockDTO);
			blockService.deleteBlock(block);
			singleDtoDelResponse = new APIResponse<>(Arrays.asList("Successfully deleted data!"), blockDTO,
					ResponseStatusCode.SUCCESS);
		} catch (Exception e) {
			errorCount.incrementAndGet();
			if (e instanceof JpaSystemException) {
				singleDtoDelResponse = new APIResponse<>(
						Arrays.asList("block still has restricted links to term_track_car"),
						ResponseStatusCode.INFORMATION);
			} else
				singleDtoDelResponse = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.FAILURE);
		}

		if (errorCount.get() == 0) { // No errors and atleast 1 success
			return ResponseEntity.status(HttpStatus.OK).body(singleDtoDelResponse);
		} else {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(singleDtoDelResponse);
		}

	}

	@GetMapping(ControllerConstants.GET_BLOCKS + "/sort")
	public ResponseEntity<APIResponse<List<BlockDTO>>> getAllBlocksSort(

			@RequestParam("term-id") Long termId, @RequestParam("train-nr") String trainNr,
			@RequestParam(defaultValue = "blockOrder, asc") String[] sort) {

		try {

			int i = 01;
			List<Block> blockList = blockRepo.findByTermIdAndTrainNr(termId, trainNr,
					Sort.by(SortFilter.sortOrder(sort)));

			for (Block block : blockList) {
				if (block.getBlockOrder().matches("^[a-zA-Z]*$")) {
					block.setBlockOrder("N");
				}

				else {
					if (block.getBlockOrder() != null) {
						if (i < 10) {
							block.setBlockOrder("0" + Integer.toString(i));
							i = i + 1;
						} else {
							block.setBlockOrder(Integer.toString(i));
							i = i + 1;
						}

					}
					blockRepo.saveAndFlush(block);
				}
			}

			List<BlockDTO> blockDtoList = Collections.emptyList();

			if (blockList != null && !blockList.isEmpty()) {
				blockDtoList = blockList.stream().map(BlockMapper.INSTANCE::blockToBlockDTO)
						.collect(Collectors.toList());
			}

			APIResponse<List<BlockDTO>> responseObj = new APIResponse<>(Arrays.asList("Successfully retrieved data!"),
					blockDtoList, ResponseStatusCode.SUCCESS);
			return ResponseEntity.status(HttpStatus.OK).body(responseObj);
		} catch (NoRecordsFoundException e) {
			APIResponse<List<BlockDTO>> responseObj = new APIResponse<>(Arrays.asList("No Records Found"),
					ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
		} catch (Exception e) {
			APIResponse<List<BlockDTO>> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.FAILURE);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
		}

	}
}
