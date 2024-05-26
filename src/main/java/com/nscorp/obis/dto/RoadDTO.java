package com.nscorp.obis.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.nscorp.obis.common.CommonConstants;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class RoadDTO extends AuditInfoDTO {
	
	@NotBlank(message = "'roadNumber' should not be Blank or Null")
	@Schema(required = true,description="This gives the railRoad number code", example="TEST")
	@Size(min = CommonConstants.ROAD_NUM_MIN_SIZE, max = CommonConstants.ROAD_NUM_MAX_SIZE, message = "'roadNumber' size should be between {min} and {max}")
	@Pattern(regexp="^[0-9]*$",message="Only Numeric values is allowed.")
	private String roadNumber;
	
	@NotBlank(message = "'roadName' should not be Blank or Null")
	@Schema(required = true,description="This gives the name initials of the railroad associated with the terminal.", example="TEST")
	@Size(min = CommonConstants.ROAD_NAME_MIN_SIZE, max = CommonConstants.ROAD_NAME_MAX_SIZE, message = "'roadName' size should be between {min} and {max}")
	@Pattern(regexp="^[a-zA-Z0-9]*$",message="Only Alpha Numeric values is allowed.")
	private String roadName;
	
	@Schema(required = true,description="This gives the full name of the railroad associated with the terminal.", example="TEST")
	@Size(max = CommonConstants.ROAD_FULL_NAME_MAX_SIZE, message = "'roadFullName' size should be max of {max}")
	private String roadFullName;

	@Schema(required = true,description="This gives the type of railroad.", example="IS")
	@Pattern(regexp="^(IS|JS|OS|R|RR|TS|SW)$",message="'roadType' should contain IS,JS,OS,R,RR,TS,SW or can be null ")
	private String roadType;

}
