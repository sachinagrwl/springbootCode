package com.nscorp.obis.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.nscorp.obis.common.CommonConstants;
import com.nscorp.obis.common.NullOrNotBlank;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.sql.Timestamp;


@Data
@EqualsAndHashCode(callSuper=false)
public class CommodityDTO extends AuditInfoDTO {

    @NotNull
    @Schema(required = true,description="First five digits of unique STCC code defined by AAR. ")
    @Digits(integer= 5, fraction=0, message= "Commodity Code 5 length cannot be more than 5")
    @Min(value = 0, message = "Commodity Code 5 value must be greater than or equal to 0")
    private Integer commodityCode5;

    @NotNull
    @Schema(required = true,description="Last two digits of unique STCC code defined by AAR . ")
    @Digits(integer= 2, fraction=0, message= "Commodity Code 2 length cannot be more than 2")
    @Min(value = 0, message = "Commodity Code 2 value must be greater than or equal to 0")
    private Integer commodityCode2;

    @NotNull
    @Schema(required = true,description="Subtype grouping associated to this commodity STCC code . ")
    @Digits(integer= 2, fraction=0, message= "Commodity Sub Code length cannot be more than 2")
    @Min(value = 0, message = "Commodity Sub Code value must be greater than or equal to 0")
    private Integer commoditySubCode;

    @Size(max=60,message="Commodity Code Long Name cannot be more than 60 ")
    @Schema(required = false,description="Long description of general type of good being moved ")
    private String commodityCodeLongName;

    @Size(max=15,message="Commodity Code Short Name cannot be more than 15 ")
    @Schema(required = false,description="Short description of general type of good being moved ")
    private String commodityCodeShortName;

    @Size(max=1, message="Hazard Indicator should be length of 1")
    @Schema(required = false,description="Indicates whether hazmat good/product being shipped ")
    @Pattern(regexp="^$|(Y|N|D|)$",message="Only Y, N, D & Null is allowed")
    private String hazardIndicator;

    @Size(max=1,message="Prime Indicator should be length of 1")
    @Schema(required = false,description="")
    private String primeIndicator;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM-dd-yyyy HH:mm:ss")
    @Schema(required = false ,description="", example="MM/DD/YYYY HH:mm:ss")
    private Timestamp expiredDateTime;
}