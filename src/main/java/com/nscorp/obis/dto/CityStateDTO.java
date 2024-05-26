package com.nscorp.obis.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;


@Data
@EqualsAndHashCode(callSuper = false)
public class CityStateDTO extends AuditInfoDTO implements Serializable {

    @Schema(required = true, description = "")
    private String stateAbbreviation;

    @Schema(required = true, description = "")
    private String city;
}