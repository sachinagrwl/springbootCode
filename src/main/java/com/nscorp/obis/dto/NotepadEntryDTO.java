package com.nscorp.obis.dto;

import com.nscorp.obis.common.CommonConstants;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class NotepadEntryDTO extends AuditInfoDTO {

    @Schema(required = true,description="The Notepad Id being used.", example="3.000768653273E12")
    @Digits(integer= CommonConstants.NOTEPAD_ID_MAX_SIZE, fraction=0, message= "Notepad id cannot be more than 15")
    @Min(value = 1, message = "Notepad id Length must be greater than 0")
    private Double notepadId;

    @Schema(required = true,description="The Customer Id being used.", example="3.000768653273E12")
    @Digits(integer=CommonConstants.CUST_ID_MAX_SIZE, fraction=0, message= "Customer id length cannot be more than 15")
    @Min(value = 1, message = "Customer id must be greater than 0")
    private Long customerId;

    @Schema(required = false, description="The Terminal Id being used.", example="3.000768653273E12")
    @Digits(integer=CommonConstants.TERM_ID_MAX_SIZE, fraction=0, message= "Terminal id length cannot be more than 15")
    @Min(value = 1, message = "Terminal id must be greater than 0")
    private Long terminalId;

    @Schema(required = false, description="The Shipment Id being used.", example="3.000768653273E12")
    @Digits(integer=CommonConstants.SVC_ID_MAX_SIZE, fraction=0, message= "Shipment id length cannot be more than 15")
    @Min(value = 1, message = "Shipment id must be greater than 0")
    private Long svcId;

    @Schema(required = false, description="The Driver Id being used.", example="3.000768653273E12")
    @Digits(integer=CommonConstants.DRIVER_ID_MAX_SIZE, fraction=0, message= "Driver id length cannot be more than 15")
    @Min(value = 1, message = "Driver id must be greater than 0")
    private Long driverId;

    @Schema(required = false,description="", example="")
    private String drayId;

    @Schema(required = false,description="The type of the equipment being used.", example="C")
    @Size(max = CommonConstants.EQ_TYPE_MAX_SIZE, message="'equipmentType' size cannot have more than {max}")
    @Pattern(regexp="^(C|T|Z|F|G)$",message="Only C,T,Z,F and G is allowed")
    private String equipmentType;

    @Schema(required = false,description="The Initializer value of the equipment being used.", example="TEST")
    @Size(min = CommonConstants.EQ_INIT_MIN_SIZE, max = CommonConstants.EQ_INIT_MAX_SIZE, message = "'equipmentInit' size should be between {min} and {max}")
    @Pattern(regexp="^[a-zA-Z]*$",message="Only Alphabets is allowed.")
    private String equipmentInit;

    @Schema(required = false,description="", example="")
    private Integer equipmentNumber;

    @Schema(required = false,description="", example="")
    private String equipmentId;

    @Schema(required = false,description="", example="")
    private String chassisInit;

    @Schema(required = false,description="", example="")
    private Integer chassisNumber;

    @Schema(required = false,description="", example="")
    private String chassisId;

    @Schema(required = false,description="Notepad entry text being used.", example="")
    @Size(max=CommonConstants.NOTEPAD_ENTRY_TEXT_MAX_SIZE, message="Notepad entry text size should not be more than {max}")
    private String notepadText;

}
