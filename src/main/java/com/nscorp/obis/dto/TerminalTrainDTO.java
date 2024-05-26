package com.nscorp.obis.dto;


import com.nscorp.obis.common.CommonConstants;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.sql.Time;

@Data
@EqualsAndHashCode(callSuper=false)
public class TerminalTrainDTO extends AuditInfoDTO{


	private Long termId;
	@NotBlank(message="TrainNr Should not be empty")
	@Size(min=1, max=4, message = "TrainNr should not be more than 4 Character")
	private String trainNr;

	@NotBlank(message="oldTrainNr Should not be empty")
	@Size(min=1, max=4, message = "oldTrainNr should not be more than 4 Character")
	private String oldTrainNr;
	
	
	@Size(min=CommonConstants.TERM_TBL_MIN_SIZE, max=CommonConstants.TERM_TBL_MAX_SIZE, 
	message="Train Description should be between {min} and {max}")
	private String trainDesc;
	private Time cutoffDefault;
	private Time cutoffMon;
	private Time cutoffTue;
	private Time cutoffWed;
	private Time cutoffThu;
	private Time cutoffFri;
	private Time cutoffSat;
	private Time cutoffSun;
	private String trainDir;
	private Integer maxFootage;

}
