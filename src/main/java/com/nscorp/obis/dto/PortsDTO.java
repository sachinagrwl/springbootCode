package com.nscorp.obis.dto;

import com.nscorp.obis.common.CommonConstants;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.sql.Timestamp;

@Data
@EqualsAndHashCode(callSuper = false)
public class PortsDTO extends AuditInfoDTO{
    @Schema(required = true, description = "The Port Id of the Ports being used.", example = "2.376524965511E12")
    private Long portId;

    @NotBlank(message = "'portCode' should not be Blank or Null.")
    @Schema(required = false,description="This gives the port code of the Ports.", example="SP")
    @Size(max = CommonConstants.PORT_CODE_MAX_SIZE, message = "'portCode' size should be max of {max}")
    @Pattern(regexp="^[a-zA-Z0-9]*$",message="Only Alpha Numeric values is allowed.")
    private String portCode;

    @NotBlank(message = "'portName' should not be Blank or Null.")
    @Schema(required = false,description="This gives the port name of the Ports.", example="SOUTH PORT")
    @Size(max = CommonConstants.PORT_NAME_MAX_SIZE, message = "'portName' size should be max of {max}")
//    @Pattern(regexp="^[a-zA-Z0-9 ]*$",message="Only Alpha Numeric values is allowed.")
    private String portName;

    @NotBlank(message = "'portCity' should not be Blank or Null.")
    @Schema(required = false,description="This gives the port city of the Ports.", example="CHARLESTON, SC.")
    @Size(max = CommonConstants.PORT_CITY_MAX_SIZE, message = "'portCity' size should be max of {max}")
//    @Pattern(regexp="^[a-zA-Z0-9 ]*$",message="Only Alpha Numeric values is allowed.")
    private String portCity;

    @Schema(required = false,description="This gives the port city good spell of the Ports.", example="CHARLESTON")
    @Size(max = CommonConstants.PORT_CITY_GOOD_SPELL_MAX_SIZE, message = "'portCityGoodSpell' size should be max of {max}")
//    @Pattern(regexp="^[a-zA-Z0-9 ]*$",message="Only Alpha Numeric values is allowed.")
    private String portCityGoodSpell;

    @Schema(required = false,description="This gives the port state or province of the Ports.", example="SC")
    @Size(max = CommonConstants.PORT_STATE_MAX_SIZE, message = "'porStateOrProvince' size should be max of {max}")
//    @Pattern(regexp="^[a-zA-Z0-9]*$",message="Only Alpha Numeric values is allowed.")
    private String portStateOrProvince;

    @Schema(required = false,description="This gives the port country of the Ports.", example="USA")
    @Size(max = CommonConstants.PORT_COUNTRY_MAX_SIZE, message = "'portCountry' size should be max of {max}")
    @Pattern(regexp="^(USA|MEX|CAN)$",message="Only USA,MEX,CAN and null is allowed.")
    private String portCountry;

    @Schema(required = false ,description="This gives the expired of the Ports.", example="1997-10-13 00:00:00")
    private Timestamp expiredDate;
}
