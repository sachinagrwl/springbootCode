package com.nscorp.obis.dto;

import java.math.BigDecimal;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class EquipmentCustomerLesseeRangeDTO extends AuditInfoDTO {
	
    private Long equipmentCustomerRangeId;

    private Long equipmentLesseeId;

    private CorporateCustomerDTO corporateCustomer;

    private String equipmentInit;

    private BigDecimal equipmentLowNumber;

    private BigDecimal equipmentHighNumber;

    private String equipmentType;

    private String equipmentOwnerType;

    private Long equipmentOwnerId;

}
