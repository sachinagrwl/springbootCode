package com.nscorp.obis.dto;

import java.sql.Timestamp;

import javax.persistence.MappedSuperclass;
import javax.validation.constraints.Size;

import com.nscorp.obis.common.CommonConstants;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@MappedSuperclass
@Data
@EqualsAndHashCode(callSuper=false)
public class UnversionedAuditInfoDTO {
	
	@Schema(required = false ,description="The user id being used.", example="MST0U")
	@Size(max=CommonConstants.CREATE_USER_ID_MAX_SIZE)
	private String createUserId;
	
	@Schema(required = false ,description="The time at which the record was added.", example="1996-08-16 12:10:25")
//	@Size(max=CommonConstants.CREATE_DT_TM_MAX_SIZE)
	private Timestamp createDateTime;
	
	@Schema(required = false ,description="The user id of the user who updated the record.", example="MST0U")
	@Size(max=CommonConstants.UPD_USER_ID_MAX_SIZE)
	private String updateUserId;
	
	@Schema(required = false ,description="The time at which the record was updated.", example="1996-08-16 12:10:25")
//	@Size(max=CommonConstants.UPD_DT_TM_MAX_SIZE)
	private Timestamp updateDateTime;
	
	@Schema(required = false ,description="The name or number of the screen being used.", example="IMS02660")
	@Size(max=CommonConstants.UPD_EXTN_SCHEMA_MAX_SIZE)
	private String updateExtensionSchema;

}
