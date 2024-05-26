package com.nscorp.obis.dto;

import java.util.List;

import com.nscorp.obis.common.NullOrNotBlank;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class DrayageCustomerListDTO extends AuditInfoDTO{
	@NullOrNotBlank(min=1, max = 4, message = "Drayage Id length should be between 1 and 4.")
    @Schema(required = true, description = "This identifies this is trucker as defined in SCAC.")
	private String drayageId;
	
	private List<DrayageCustomerInfoDTO> drayageCustomerList;

	
}
