package com.nscorp.obis.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class PoolCarStorageExemptDTO extends AuditInfoDTO {

	@Schema(required = true, description = "The Pool Id of the Car Storage Exempt being used.", example = "1.0030614902021E13")
	private Long poolId;

	private PoolDTO pool;

}
