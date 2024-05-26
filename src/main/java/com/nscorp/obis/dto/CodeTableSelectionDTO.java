package com.nscorp.obis.dto;

import com.nscorp.obis.common.CommonConstants;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@EqualsAndHashCode(callSuper=false)
public class CodeTableSelectionDTO extends AuditInfoDTO {
	
//	@NotNull(message = "Table Name Should not be Null.")
	@NotBlank(message = "Table Name Should not be Blank or Null.")
	@Schema(required = true,description="The name of the table being used.", example="AAR_PTRN")
	@Size(min=CommonConstants.GEN_TBL_MIN_SIZE, max=CommonConstants.GEN_TBL_MAX_SIZE, 
	message="Table Name size should be between {min} and {max}")
	private String genericTable;
	
	@Schema(required = false,description="The description of the table being used.", example="GQL AAR CAR CATAGRYS")
	@Size(max=CommonConstants.GEN_TBLDESC_MAX_SIZE, message="Table Description size should not be more than {max}")
	private String genericTableDesc;
	
	@Schema(required = false,description="The owner of the table being used.", example="AAR_PTRN")
	@Size(max=CommonConstants.GEN_OWNR_GRP_MAX_SIZE
	, message="Owner Group size should not be more than {max}")
	private String genericOwnerGroup;
	
	@Schema(required = false,description="The code of the table being used.", example="4")
	@Range(min=1, max=10, message= "Code Size should be between {min} and {max}")
	private short genCdFldSize;
	
	@Schema(required = false,description="The resource name of the table being used.", example="DEV_CODES")
	@Size(max=CommonConstants.RESOURCE_NM_MAX_SIZE, message="Resource name size should not be more than {max}")
	private String resourceNm;

}
