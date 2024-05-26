package com.nscorp.obis.dto;

import java.math.BigDecimal;
import java.sql.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class HoldOrdersDTO extends AuditInfoDTO{

	private Long holdOrderId;

	private String equipmentInit;

	private BigDecimal equipmentNbr;

	private String equipmentType;

	private Long svcId;

	private String holdReasonCd;

	private Date holdUnitDt;

	private Long workQueueLogId;

	private Long assocTermId;
}
