package com.nscorp.obis.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class AARDamageDTO extends AuditInfoDTO {

    private Integer jobCode;

    private String dscr;

    private String chassisInd;

    private Integer familyCode;
}
