package com.nscorp.obis.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@EqualsAndHashCode(callSuper=false)
public class DamageAreaDTO {
    @Size(max= 1, message="Damage Area Code size should not be more than 1 character")
    @NotEmpty(message = "W-AREA_CD Required")
    private String areaCd;
    private String areaDscr;
    private String displayCd;
}
