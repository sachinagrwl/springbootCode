package com.nscorp.obis.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = false)
public class EquipmentCustomerRangeDTO extends AuditInfoDTO{

    private Long equipmentCustomerRangeId;

    private String equipmentInit;

    private BigDecimal equipmentLowNumber;

    private BigDecimal equipmentHighNumber;

    private String equipmentType;
}
