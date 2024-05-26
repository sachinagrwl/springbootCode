package com.nscorp.obis.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class DeliveryDetailDTO extends AuditInfoDTO {
	
	@Schema(required=false, description="Send SETG")
	Boolean sendSETG;	
	@Schema(required=false, description="Send DETG")
	Boolean sendDETG;	
	@Schema(required=false, description="Confirmation Time Interval of Delivery Detail.")
	Integer confirmationTimeInterval;

}
