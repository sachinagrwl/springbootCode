package com.nscorp.obis.dto;

import java.util.List;

import javax.persistence.Transient;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.data.annotation.ReadOnlyProperty;

import com.nscorp.obis.domain.DayOfWeek;
import com.nscorp.obis.domain.DestinationSetting;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class BlockDTO extends AuditInfoDTO{
	private Long termId;
	private String trainNr;
	@Digits(integer=15,fraction=0,  message = "Block Id should not be more than 15 Number")
	private Long blockId;
	
	@NotBlank(message="Block Name Should not be empty")
	@Size(min=1, max=15, message = "Block Name should not be more than 15 Character")
	private String blockNm;
	private Long parantBlockId;
	
//	@NotBlank(message="Block Order Should not be empty")
//	@Range(min=01, max=99, message="Block Order should be between 1 to 99")
	@Schema(required = false,description="This tells the Order of the Block" )
	private String blockOrder;
	
	@Range(min=1, max=9,message="Block Priority should be between 1 to 9") 
	@Schema(required = false,description="This tells the Priority" )
	private Integer blockPriority;
	
	@ReadOnlyProperty()
	private String swInterchange;
	
	@Schema(required = false,description="Type 'Y'-Yes and 'N'-No")
	@Pattern(regexp="^(Y|N)$",message="Only Y and N is allowed")
	private String allowSameCar;
	
	@Pattern(regexp="^(Y|N)$",message="Only Y and N is allowed")
	private String blockMon;
	
	@Pattern(regexp="^(Y|N)$",message="Only Y and N is allowed")
	private String blockTue;
	
	@Pattern(regexp="^(Y|N)$",message="Only Y and N is allowed")
	private String blockWed;
	
	@Pattern(regexp="^(Y|N)$",message="Only Y and N is allowed")
	private String blockThu;
	
	@Pattern(regexp="^(Y|N)$",message="Only Y and N is allowed")
	private String blockFri;
	
	@Pattern(regexp="^(Y|N)$",message="Only Y and N is allowed")
	private String blockSat;
	
	@Pattern(regexp="^(Y|N)$",message="Only Y and N is allowed")
	private String blockSun;

	@Schema(required = false,description="Defines the height Feet")
	private Integer hightFeet;
	
	@Schema(required = false,description="Defines the height Inches")
	private Integer hightInches;
	
	@ReadOnlyProperty
	private Integer weight;
	
	@Size(min=0, max=7)
    @UniqueElements(message = "Day Of Week must have unique values only.")
    @Schema(required = false,description="This defines the day of the week.")
    private List<DayOfWeek> activeDays;

	private String trainNbr;
	private String trainDesc;

   private List<DestinationSetting> destinationSettings;
}
