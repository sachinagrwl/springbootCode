package com.nscorp.obis.dto;

import com.nscorp.obis.domain.Pool;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class PoolTerminalDTO extends UnversionedAuditInfoDTO {

    private Long poolId;

    private Pool poolDetails;

    Long terminalId;
}
