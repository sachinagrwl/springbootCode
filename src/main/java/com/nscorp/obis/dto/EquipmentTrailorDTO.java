package com.nscorp.obis.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = false)
public class EquipmentTrailorDTO extends AuditInfoDTO {

    private String trailorInit;

    private BigDecimal trailorNumber;

    private String trailorEquipType;

    private String trailorId;
}
