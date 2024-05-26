package com.nscorp.obis.domain;

import java.io.Serializable;

@SuppressWarnings("serial")
public class BlockComposite implements Serializable {
	private Long blockId;
	
	public BlockComposite() {
		super();
	}
	public BlockComposite(Long blockId) {
		this.setBlockId(blockId);
	}
	public Long getBlockId() {
		return blockId;
	}
	public void setBlockId(Long blockId) {
		this.blockId = blockId;
	}
}
