
package com.nscorp.obis.domain;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;

import lombok.Data;

@Data
@SuppressWarnings("serial")
public class EquipLocPrimaryKey implements Serializable{
	
	@Column(name = "EQ_INIT", columnDefinition = "char(4)", nullable = false)
    private String equipInit;
	
	@Column(name = "EQ_TP", columnDefinition = "char(1)", nullable = false)
    private String equipTp;
	
	@Column(name = "EQ_NR", length = 19,columnDefinition = "decimal", nullable = false)
    private BigDecimal equipNbr;

	@Column(name = "EQ_ID", columnDefinition = "char(4)", nullable = false)
    private String equipId;

	public EquipLocPrimaryKey(String equipInit, String equipTp, BigDecimal equipNbr, String equipId) {
		super();
		this.equipInit = equipInit;
		this.equipTp = equipTp;
		this.equipNbr = equipNbr;
		this.equipId = equipId;
	}

	public EquipLocPrimaryKey() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

}
