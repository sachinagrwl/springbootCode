package com.nscorp.obis.dto;

import com.nscorp.obis.domain.Pool;
import com.nscorp.obis.domain.Terminal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class PoolEquipmentConflictRangeDTO {
    private Pool pool;

    private Terminal terminal;

    private String rangeType;

    private String rangeInit;

    private BigDecimal rangeLowNumber;

    private BigDecimal rangeHighNumber;

    private Long poolRangeId;
}
