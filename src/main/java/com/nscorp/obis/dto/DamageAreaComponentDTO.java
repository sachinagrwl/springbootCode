package com.nscorp.obis.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.validation.constraints.*;

@Data
@EqualsAndHashCode(callSuper = false)
public class DamageAreaComponentDTO extends AuditInfoDTO {

    @Size(max = 1, message = "Damage Area Code size should not be more than 1 character")
    @NotEmpty(message = "W-AREA_CD Required")
    @Schema(required = true)
    private String areaCd;

    @NotNull(message = "W-JOB_CODE Required")
    @Min(value = 1000, message = "Job Code should be 4 digits")
    @Max(value = 9999, message = "Job Code should be 4 digits")
    @Schema(required = true, description="The DamageComponent Id of the RailCar being used.", example="1111")
    private Integer jobCode;

    @Pattern(regexp = "^[a-zA-Z]*$", message = "order code must contain alphabets only")
    @Size(max = 1, message = "ORDER_CD size should not be more than one character long")
    private String orderCode;

    @Size(max = 1, message = "DISPLAY_CD size should not be more than one character long")
    private String displayCd;

    //for display additional details
    private String areaDscr;
    private String compDscr;
    private String tInd;
    private String cInd;
    private String zInd;
    private String reasonTp;




}
