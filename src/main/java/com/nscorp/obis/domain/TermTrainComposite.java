package com.nscorp.obis.domain;

import lombok.Data;

import java.io.Serializable;

@SuppressWarnings("serial")
@Data
public class TermTrainComposite implements Serializable {
	
	 private long termId;
     private String trainNr;

	 public TermTrainComposite() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TermTrainComposite(long termId ,String trainNr) {
		super();
		this.termId = termId;
		this.trainNr =trainNr;
	}

}
