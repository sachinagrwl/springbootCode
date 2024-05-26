package com.nscorp.obis.dto;

import com.nscorp.obis.common.CommonConstants;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@EqualsAndHashCode(callSuper = false)
public class TruckerGroupDTO extends AuditInfoDTO{

    @NotBlank(message = "'truckerGroupCode' should not be Blank or Null")
    @Schema(required = true,description="The Trucker Group Code being used.", example="EDFF")
    @Size(min= CommonConstants.TRUCKER_GROUP_CODE_MIN_SIZE, max=CommonConstants.TRUCKER_GROUP_CODE_MAX_SIZE, message="'truckerGroupCode' size should not be greater than {max}")
    private String truckerGroupCode;

    @Schema(required = false,description="The Trucker Group Description being used.", example="TIA/UIIA TRUCKER GROUP DDDD")
    @Size(min= CommonConstants.TRUCKER_GROUP_DESC_MIN_SIZE, max=CommonConstants.TRUCKER_GROUP_DESC_MAX_SIZE, message="'truckerGroupDesc' size should not be greater than {max}")
    private String truckerGroupDesc;

    @Schema(required = false,description="The Setup Schema being used.", example="IMS02554")
    @Size(min= CommonConstants.SETUP_SCHEMA_MIN_SIZE, max=CommonConstants.SETUP_SCHEMA_MAX_SIZE, message="'setupSchema' size should not be greater than {max}")
    private String setupSchema;
}
