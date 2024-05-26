package com.nscorp.obis.dto;

import javax.validation.constraints.*;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;


@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class DamageComponentDTO extends AuditInfoDTO{


    @Min(value = 1000, message = "Job Code should be 4 digits")
    @Max(value = 9999, message = "Job Code should be 4 digits")
    @Schema(required = true, description="The DamageComponent Id of the RailCar being used.", example="1111")
    private Integer jobCode;

    @NotBlank(message = "Component Description Should not be Null or Blank.")
    @Schema(required = false,description="Component Description being used.", example="TANK FRAME")
    @Size(max= 16, message="Component Description  size should not be more than 16")
    private String compDscr;

    @Pattern(regexp="^(Y|N|)$",message="Only Y, N & Null is allowed in tIND")
    private String tInd;

    @Pattern(regexp="^(Y|N|)$",message="Only Y, N & Null is allowed in cInd")
    private String cInd;

    @Pattern(regexp="^(Y|N|)$",message="Only Y, N & Null is allowed in zInd")
    private String zInd;

    @NotBlank(message = "Reason Type Should not be Null or Blank.")
    @Pattern(regexp="^(M|T)$",message="Only M & T is allowed in reasonTp")
    private String reasonTp;
}
