package com.nscorp.obis.services;

import com.nscorp.obis.domain.Block;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

public interface BlockService {
	public List<Block> getAllBlocks( Long termId, String trainNr );
	Block updateBlock(@Valid Block blockObj, Map<String ,String> headers);

	public Block deleteBlock(Block block);

	public Block addBlock(@Valid Block blockObj, Map<String, String> headers);
	public List<Block> getAllBlockSort(Long termId, String trainNr);

}
