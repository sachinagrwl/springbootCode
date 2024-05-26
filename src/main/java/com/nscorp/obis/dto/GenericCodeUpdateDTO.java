package com.nscorp.obis.dto;

import com.nscorp.obis.common.CommonConstants;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class GenericCodeUpdateDTO extends AuditInfoDTO{

    @NotBlank(message = "Table Name should not be Blank or Null")
//	@NotNull(message = "Table Name Should not be Null")
	@Schema(required = true,description="The generic table being used.", example="AAR_PTRN")
	@Size(min=CommonConstants.GEN_TABLE_MIN_SIZE, max=CommonConstants.GEN_TABLE_MAX_SIZE, message = "Table Name Size must be between 1 to 8")
	private String genericTable;

    @NotBlank(message = "Table Code Should not be Blank or Null")
//    @NotNull(message = "Table Code Should not be Null")
	@Schema(required = true,description="The generic table code being used.", example="P0__")
	@Size(max = CommonConstants.GEN_TABLE_CODE_MAX_SIZE , message = "Generic Table Code maximum size must be 10")
	private String genericTableCode;
	
	@Schema(required = false,description="The short description being used.", example="CONV")
	@Size(max=CommonConstants.GEN_SHORT_DESC_MAX_SIZE, message = "Generic Short Description maximum size must be 10")
	private String genericShortDescription;
	
	@Schema(required = false,description="The long description being used.", example="SGL")
	@Size(max=CommonConstants.GEN_LONG_DESC_MAX_SIZE, message = "Generic Long Description maximum size must be 45")
	private String genericLongDescription;
	
	@Schema(required = false,description="The additional short description being used.", example="")
	@Size(max=CommonConstants.GEN_ADD_SHORT_MAX_SIZE, message = "Generic Short Description maximum size must be 10")
	private String addShortDescription;
	
	@Schema(required = false,description="The additional long description being used.", example="")
	@Size(max=CommonConstants.GEN_ADD_LONG_MAX_SIZE, message = "Generic Long Description maximum size must be 45")
	private String addLongDescription;
	
	@Schema(required = false,description="The generic flag being used.", example="")
	@Size(max=CommonConstants.GEN_FLAG_MAX_SIZE, message = "Generic Flag maximum size must be 1")
	private String genericFlag;
}
