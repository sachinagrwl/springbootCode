package com.nscorp.obis.dto;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@JsonIgnoreProperties(value = "true")
public class TempEVTDTO extends AuditInfoDTO{

	
	private Long evtlogId; 

	private String queStat;
	
	@Size(min=1, max=4, message = "Equip Init should not be more than 4 Character")
	private String equipInit;
	
	@Digits(integer=6, fraction=0, message = "Equip Number should not be more than 6 Digit")
	private BigDecimal equipNbr;

	@Size(min=1, max=1, message = "Equip Tp should not be more than 1 Character")
	private String equipTp;
	
	@Size(min=1, max=4, message = "Equip Id should not be more than 4 Character")
	private String equipId; 
	
	private String leInd;
	
	private String hazInd;
	
	private String evtCd;
	
	@JsonFormat(pattern="MM/dd/yyyy HH:mm")
	private Date evtdtTm;
	
	private Long svcId;
	
	private Long termId; 
	
	private String chasInit;
	
	private BigDecimal chasNr;
	
	private String chasTp;
	
	private String chasId;
	
	private Long lotareaId;
	
	private Timestamp lccdtTm;
	
	private Timestamp stddtTm;

	private Long custId;

	private String reasonCode;
}
