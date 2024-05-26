package com.nscorp.obis.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import com.nscorp.obis.domain.TerminalTrain;
import com.nscorp.obis.repository.TerminalTrainRepository;
import com.nscorp.obis.repository.TrainRepository;
import io.micrometer.core.instrument.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nscorp.obis.common.CommonConstants;
import com.nscorp.obis.common.UserId;
import com.nscorp.obis.domain.Block;
import com.nscorp.obis.exception.InvalidDataException;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.exception.RecordNotAddedException;
import com.nscorp.obis.exception.RecordNotDeletedException;
import com.nscorp.obis.repository.BlockRepository;

@Service
@Transactional
public class BlockServiceImpl implements BlockService {
	
	@Autowired
	private TerminalTrainRepository termTrainRepo;

	@Autowired
	private TrainRepository trainRepository;
	
	@Autowired(required = true)
	private BlockRepository blockRepo;

	public List<Block> getAllBlocks(Long termId, String trainNr) {
		List<Block> blockList = blockRepo.findByTermIdAndTrainNr(termId, trainNr);
		if (blockList.isEmpty()) {
			throw new NoRecordsFoundException("No records found");
		}
		return blockList;
	}

	@Override
	public List<Block> getAllBlockSort(Long termId, String trainNr) {

		List<Block> blockList = blockRepo.findByTermIdAndTrainNr(termId, trainNr);
		if (blockList.isEmpty()) {
			throw new NoRecordsFoundException("No records found");
		}
		return blockList;

	}

	@Override
	public Block updateBlock(Block blockObj, Map<String, String> headers) {

		List<Block> blockList = blockRepo.findByTermIdAndTrainNrOrderByBlockOrderAsc(blockObj.getTermId(),
				blockObj.getTrainNr());
		int existingBlockOrder = 0;
		
		UserId.headerUserID(headers);
		if (blockRepo.existsByBlockId(blockObj.getBlockId())) {
			Block block = blockRepo.findByBlockId(blockObj.getBlockId());
			if (!block.getBlockOrder().equalsIgnoreCase("N")) {
				existingBlockOrder = Integer.parseInt(block.getBlockOrder());

			}
			if (block.getBlockOrder().equalsIgnoreCase("N") && !blockObj.getBlockOrder().matches("^[A-Za-z!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]*$")) {
				NorderBlock(blockList, Integer.parseInt(blockObj.getBlockOrder())); 
			}
			else if(!blockObj.getBlockOrder().matches("^[A-Za-z!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]*$") &&(Integer.parseInt(blockObj.getBlockOrder()) > 99))

				throw new InvalidDataException("Block Order should be between 1 to 99, Only N or Blank is allowed");

			
			block.setUpdateUserId(headers.get("userid"));
			block.setBlockNm(blockObj.getBlockNm());
			block.setAllowSameCar(blockObj.getAllowSameCar());
			block.setBlockPriority(blockObj.getBlockPriority());
			block.setAllowSameCar(blockObj.getAllowSameCar());

			if (blockObj.getBlockOrder().matches("^[A-Za-z!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]*$")
					|| blockObj.getBlockOrder().isEmpty()) {
				if (blockObj.getBlockOrder().isEmpty() || (blockObj.getBlockOrder().equals("n"))
						|| (blockObj.getBlockOrder().equals("N"))) {
					block.setBlockOrder("N");

				} else
					throw new InvalidDataException("Block Order should be between 1 to 99, Only N or Blank is allowed");

			} else {
				if (Integer.parseInt(blockObj.getBlockOrder()) >= 0
						&& Integer.parseInt(blockObj.getBlockOrder()) <= 99) {
					block.setBlockOrder(blockObj.getBlockOrder());

				}

				else
					throw new InvalidDataException("Block Order should be between 1 to 99, Only N or Blank is allowed");
			}
			// Height Feet & Inches Validation
			if (blockObj.getHightFeet() != null) {

				if (blockObj.getHightFeet() >= 13 && blockObj.getHightFeet() <= 20) {

					if (blockObj.getHightFeet() >= 13 && blockObj.getHightFeet() <= 17) {

						if (blockObj.getHightFeet() == 13) {
							if (blockObj.getHightInches() == null) {
								throw new InvalidDataException("Inches should between 6 to 9 ");
							} else if (blockObj.getHightInches() >= 6 && blockObj.getHightInches() <= 9) {
								block.setHightFeet(blockObj.getHightFeet());
								block.setHightInches(blockObj.getHightInches());
							}

							else
								throw new InvalidDataException("Inches should between 6 to 9 ");

						} else if (blockObj.getHightFeet() >= 14 && blockObj.getHightFeet() <= 16) {
							if (blockObj.getHightInches() == null) {
								throw new InvalidDataException("Inches should between 0 to 9 ");

							} else if (blockObj.getHightInches() >= 0 && blockObj.getHightInches() <= 9) {
								block.setHightFeet(blockObj.getHightFeet());
								block.setHightInches(blockObj.getHightInches());
							}

							else
								throw new InvalidDataException("Inches should between 0 to 9 ");
						}

						else if (blockObj.getHightFeet() == 17) {
							if (blockObj.getHightInches() == null) {
								throw new InvalidDataException("Inches should be less than 6 ");

							} else if (blockObj.getHightInches() <= 6) {
								block.setHightFeet(blockObj.getHightFeet());
								block.setHightInches(blockObj.getHightInches());
							}

							else
								throw new InvalidDataException("Inches should be less than 6 ");
						}
					} else if (blockObj.getHightFeet() == 18 || blockObj.getHightFeet() == 19
							|| blockObj.getHightFeet() == 20) {
						if (blockObj.getHightInches() == null) {
							throw new InvalidDataException("Inches should be 3 ");

						} else if ((blockObj.getHightInches() == 3)) {
							block.setHightFeet(blockObj.getHightFeet());
							block.setHightInches(blockObj.getHightInches());
						} else
							throw new InvalidDataException("Inches should be 3 ");
					}

				} else {
					throw new InvalidDataException("Height Feet should between 13 to 20 ");
				}
			} else if (blockObj.getHightFeet() == null) {
				if (blockObj.getHightInches() == null) {
					block.setHightInches(blockObj.getHightInches());
					block.setHightFeet(blockObj.getHightFeet());
				} else

					throw new InvalidDataException("Only null value accepted for Inches ");
			} else

				throw new InvalidDataException("Enter the values correctly ");

			block.setBlockMon(blockObj.getBlockMon());
			block.setBlockTue(blockObj.getBlockTue());
			block.setBlockWed(blockObj.getBlockWed());
			block.setBlockThu(blockObj.getBlockThu());
			block.setBlockFri(blockObj.getBlockFri());
			block.setBlockSat(blockObj.getBlockSat());
			block.setBlockSun(blockObj.getBlockSun());
			if (blockObj.getActiveDays().isEmpty()) {
				throw new RecordNotAddedException("User must select at least one day of the week for each block");
			}
			if (existingBlockOrder > 0 && !blockObj.getBlockOrder().equalsIgnoreCase("N")) {
				reorderBlock(blockList, existingBlockOrder, Integer.parseInt(blockObj.getBlockOrder()),
						block.getTermId(), block.getTrainNr());

			}
			blockRepo.saveAll(blockList);

			//blockRepo.save(block);

			return block;
		} else {
			throw new NoRecordsFoundException("Record with BlockID " + blockObj.getBlockId() + " Not Found!");

		}

	}

	private void NorderBlock(List<Block> blockList, int blockOrder) {
		// TODO Auto-generated method stub
		List<Block> NblkList = new ArrayList<>();
		int count = 0;
		NblkList = blockList;
		for(int i=0;i<NblkList.size();i++) {
			if(!NblkList.get(i).getBlockOrder().equalsIgnoreCase("N"))
				count++;
		}
		for(int i= blockOrder;i<count;i++) {
			if(i<9) {
			NblkList.get(i-1).setBlockOrder("0"+String.valueOf(i+1));
		}
			else
				NblkList.get(i-1).setBlockOrder(String.valueOf(i+1));
		}
		blockRepo.saveAll(NblkList);

	}

	private void reorderBlock(List<Block> blockList2, int existingBlockOrder, int blockOrder, Long termId,
			String trainNr) {
		// TODO Auto-generated method stub
		List<Block> blkList = new ArrayList<>();
		blkList = blockList2;

		if (existingBlockOrder < blockOrder) {
			for (int i = existingBlockOrder; i < blockOrder; i++) {
				//blkList.get(i).setBlockOrder(String.valueOf(i));

				if(i<10) {
					blkList.get(i).setBlockOrder("0" + String.valueOf(i));
				}
				else
					blkList.get(i).setBlockOrder(String.valueOf(i));


			}
		} else {
			for (int i = blockOrder; i < existingBlockOrder; i++) {
			//	blkList.get(i - 1).setBlockOrder(String.valueOf(i+1));

				if (i > 0)
					if(i<9) {
						blkList.get(i - 1).setBlockOrder("0" + String.valueOf(i+1));
					}
					else
						blkList.get(i - 1).setBlockOrder(String.valueOf(i+1));


			}

		}

		blockRepo.saveAll(blkList);
	}

	@Override
	public Block deleteBlock(Block block) {
		// TODO Auto-generated method stub
		if (blockRepo.existsByBlockId(block.getBlockId())) {
			blockRepo.deleteByBlockId(block.getBlockId());

		} else {
			String rep = block.getBlockId() + " Record Not Found!";
			throw new RecordNotDeletedException(rep);
		}
		return block;
	}

	private void addreorderBlock(List<Block> block, String blockOrder) {
		// TODO Auto-generated method stub
		int order = Integer.parseInt(blockOrder);
		for (int i = order - 1; i < block.size(); i++) {

			if (!block.get(i).getBlockOrder().equals("N")) {
				// block.get(i+1).setBlockOrder(Integer.parseInt(i));
				int blkOrder = Integer.parseInt(block.get(i).getBlockOrder());
				blkOrder += 1;
				block.get(i).setBlockOrder(String.valueOf(blkOrder));
			}
			else
				block.get(i).setBlockOrder("N");

		}

		blockRepo.saveAll(block);
	}

	@Override
	public Block addBlock(@Valid Block blockObj, Map<String, String> headers) {
		List<Block> block = blockRepo.findByTermIdAndTrainNrOrderByBlockOrderAsc(blockObj.getTermId(),
				blockObj.getTrainNr());
		if(!blockObj.getBlockOrder().equals("N"))
		addreorderBlock(block, blockObj.getBlockOrder());
		UserId.headerUserID(headers);

		if (blockRepo.existsByBlockId(blockObj.getBlockId())) {
			throw new RecordAlreadyExistsException("Block is already exists:" + blockObj.getBlockId());
		}
		if (blockRepo.existsByBlockNm(blockObj.getBlockNm())) {
			throw new RecordAlreadyExistsException("This block name already exists for this train and terminal");
		}
		Long blockId = blockRepo.SGK();
		blockObj.setBlockId(blockId);
		blockObj.setCreateUserId(headers.get(CommonConstants.USER_ID));
		blockObj.setUpdateExtensionSchema(headers.get(CommonConstants.EXTENSION_SCHEMA));
		blockObj.setUversion("!");
		blockObj.setBlockNm(blockObj.getBlockNm());
		blockObj.setBlockPriority(blockObj.getBlockPriority());
		blockObj.setSwInterchange(blockObj.getSwInterchange());
		blockObj.setAllowSameCar(blockObj.getAllowSameCar());

		if (blockObj.getBlockOrder().matches("^[A-Za-z!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]*$")
				|| blockObj.getBlockOrder().isEmpty()) {
			if (blockObj.getBlockOrder().isEmpty() || (blockObj.getBlockOrder().equals("n"))
					|| (blockObj.getBlockOrder().equals("N"))) {
				blockObj.setBlockOrder("N");

			} else
				throw new InvalidDataException("Block Order should be between 1 to 99, Only N or Blank is allowed");

		} else {
			if (Integer.parseInt(blockObj.getBlockOrder()) >= 0 && Integer.parseInt(blockObj.getBlockOrder()) <= 99) {
				blockObj.setBlockOrder(blockObj.getBlockOrder());
			}

			else
				throw new InvalidDataException("Block Order should be between 1 to 99, Only N or Blank is allowed");
		}

		if (blockObj.getHightFeet() != null) {

			if (blockObj.getHightFeet() >= 13 && blockObj.getHightFeet() <= 20) {

				if (blockObj.getHightFeet() >= 13 && blockObj.getHightFeet() <= 17) {

					if (blockObj.getHightFeet() == 13) {
						if (blockObj.getHightInches() == null) {
							throw new InvalidDataException("Inches should between 6 to 9 ");
						} else if (blockObj.getHightInches() >= 6 && blockObj.getHightInches() <= 9) {
							blockObj.setHightFeet(blockObj.getHightFeet());
							blockObj.setHightInches(blockObj.getHightInches());

						}

						else
							throw new InvalidDataException("Inches should between 6 to 9 ");

					} else if (blockObj.getHightFeet() >= 14 && blockObj.getHightFeet() <= 16) {
						if (blockObj.getHightInches() == null) {
							throw new InvalidDataException("Inches should between 0 to 9 ");

						} else if (blockObj.getHightInches() >= 0 && blockObj.getHightInches() <= 9) {
							blockObj.setHightFeet(blockObj.getHightFeet());
							blockObj.setHightInches(blockObj.getHightInches());
						}

						else
							throw new InvalidDataException("Inches should between 0 to 9 ");
					}

					else if (blockObj.getHightFeet() == 17) {
						if (blockObj.getHightInches() == null) {
							throw new InvalidDataException("Inches should be less than 6 ");

						} else if (blockObj.getHightInches() <= 6) {
							blockObj.setHightFeet(blockObj.getHightFeet());
							blockObj.setHightInches(blockObj.getHightInches());
						}

						else
							throw new InvalidDataException("Inches should be less than 6 ");
					}
				} else if (blockObj.getHightFeet() == 18 || blockObj.getHightFeet() == 19
						|| blockObj.getHightFeet() == 20) {
					if (blockObj.getHightInches() == null) {
						throw new InvalidDataException("Inches should be 3 ");

					} else if ((blockObj.getHightInches() == 3)) {
						blockObj.setHightFeet(blockObj.getHightFeet());
						blockObj.setHightInches(blockObj.getHightInches());
					} else
						throw new InvalidDataException("Inches should be 3 ");
				}

			} else {
				throw new InvalidDataException("Height Feet should between 13 to 20 ");
			}
		} else if (blockObj.getHightFeet() == null) {
			if (blockObj.getHightInches() == null) {
				blockObj.setHightInches(blockObj.getHightInches());
				blockObj.setHightFeet(blockObj.getHightFeet());
			} else

				throw new InvalidDataException("Only null value accepted for Inches ");
		} else

			throw new InvalidDataException("Enter the values correctly ");

		blockObj.setBlockMon(blockObj.getBlockMon());
		blockObj.setBlockTue(blockObj.getBlockTue());
		blockObj.setBlockWed(blockObj.getBlockWed());
		blockObj.setBlockThu(blockObj.getBlockThu());
		blockObj.setBlockFri(blockObj.getBlockFri());
		blockObj.setBlockSat(blockObj.getBlockSat());
		blockObj.setBlockSun(blockObj.getBlockSun());
		blockObj.setWeight(125);
		if (blockObj.getActiveDays().isEmpty()) {
			throw new RecordNotAddedException("User must select at least one day of the week for each block");
		}
	
		// block.add(blockObj);
		blockRepo.save(blockObj);
		
		//1. Validate the Train Nbr with the TRAIN table for existing record else throw "Invalid Train Nbr"
				//2. Check if a record exist in TERM_TRAIN table if yes proceed with next step else insert a new record into TERM_TRAIN table
				if (StringUtils.isNotBlank(blockObj.getTrainNbr())) {
					if (trainRepository.existsByTrainNumber(blockObj.getTrainNbr())) {
						if(!termTrainRepo.existsByTermIdAndTrainNr(blockObj.getTermId(), blockObj.getTrainNbr())) {
							TerminalTrain termTrain = new TerminalTrain();
							termTrain.setTrainNr(blockObj.getTrainNr());
							termTrain.setTrainDesc(blockObj.getTrainDesc());
							termTrain.setTermId(blockObj.getTermId());
							termTrain.setCreateUserId(headers.get(CommonConstants.USER_ID));
							termTrain.setUpdateExtensionSchema(headers.get(CommonConstants.EXTENSION_SCHEMA));
							termTrain.setUversion("!");
							termTrainRepo.save(termTrain);
						}
					} else
						throw new NoRecordsFoundException("Invalid Train Number");

				}
				
		return blockObj;
	}
}
