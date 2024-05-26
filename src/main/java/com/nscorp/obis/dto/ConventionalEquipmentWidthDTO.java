package com.nscorp.obis.dto;

import com.nscorp.obis.common.CommonConstants;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@EqualsAndHashCode(callSuper = false)
public class ConventionalEquipmentWidthDTO extends AuditInfoDTO {

    @Schema(required = true,description="The Umler Id of the Railcar being used.", example="1.4163614650827E13")
    private Long umlerId;

    @NotBlank(message = "'aar1stNr' should not be Blank or Null.")
    @Schema(required = true,description="The AAR 1st Number of the Conventional Car being used.", example="P")
    @Size(min= CommonConstants.AAR_1ST_NO_MIN_SIZE, max=CommonConstants.AAR_1ST_NO_MAX_SIZE, message = "'aar1stNr' length should be between {min} and {max}")
    private String aar1stNr;

    @Schema(required = false,description="The Minimum Equipment Width of the Railcar being used.", example="40")
    @Digits(integer=CommonConstants.CONV_CAR_MIN_EQ_WIDTH_MAX_SIZE, fraction=0,message="'minEqWidth' cannot have more than 4 digits")
    @Min(value = 0, message = "'minEqWidth' value must be greater than or equal to 0")
    private Integer minEqWidth;

    @Schema(required = false,description="The Maximum Equipment Width of the Railcar being used.", example="53")
    @Digits(integer=CommonConstants.CONV_CAR_MAX_EQ_WIDTH_MAX_SIZE, fraction=0,message="'maxEqWidth' cannot have more than 4 digits")
    @Min(value = 0, message = "'maxEqWidth' value must be greater than or equal to 0")
    private Integer maxEqWidth;

}
