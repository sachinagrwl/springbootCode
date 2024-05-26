package com.nscorp.obis.dto;

import javax.validation.constraints.*;

import com.nscorp.obis.common.NullOrNotBlank;
import com.nscorp.obis.domain.DamageArea;
import com.nscorp.obis.domain.DamageCompLoc;
import com.nscorp.obis.domain.DamageComponent;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class DamageCompLocDTO extends AuditInfoDTO {


    @Size(max = 3, message = "Component Location Code size should not be more than {max} character")
    @NotEmpty(message = "Component Location Code Required")
    @Schema(required = true)
    private String compLocCode;

    @NotNull
    @Min(value = 1000, message = "Job Code should be 4 digits")
    @Max(value = 9999, message = "Job Code should be 4 digits")
    @Schema(required = true, description = "The DamageComponent Id of the RailCar being used.", example = "1111")
    private Integer jobCode;

    @NullOrNotBlank(message = "Component Description Should not be Blank.")
    @Schema(required = false, description = "Component Description being used.", example = "Flat Tire")
    @Size(max = 16, message = "Component Description  size should not be more than 16")
    private String compDscr;

    @Size(max = 1, message = "Damage Area Code size should not be more than 1 character")
    @NotEmpty(message = "Area Code Required")
    @Schema(required = true)
    private String areaCd;

    @NullOrNotBlank(message = "Damage Area Description Should not be Blank.")
    @Schema(required = false, description = "Damage Area Description being used.", example = "Left Side")
    @Size(max = 16, message = "Damage Area Description  size should not be more than 16")
    private String areaDscr;

    @Size(max = 1, message = "Location Display Code size should not be more than 1 character")
    @NullOrNotBlank(message = "Location Display should not be Blank.")
    @Schema(required = false, description = "")
    private String locDisplayCode;

    @Size(max = 1, message = "Tire IO Code size should not be more than 1 character")
    @Schema(required = false, description = "")
    @Pattern(regexp="^$|(I|O|)$",message="Only I, O & Null is allowed")
    private String tireIoCode;

    @Size(max = 1, message = "Display Code size should not be more than 1 character")
    @NullOrNotBlank(message = "Display Code should not be blank.")
    @Schema(required = false, description = "")
    private String displayCode;

    public void setDamageComponent(DamageComponentDTO damageComponent) {
        if (damageComponent != null) {
            this.jobCode = damageComponent.getJobCode();
            this.compDscr = damageComponent.getCompDscr();
        }
        if(this.compDscr!=null){
            this.compDscr = this.compDscr.trim();
        }
    }

    public void setDamageArea(DamageAreaDTO damageArea) {
        if (damageArea != null) {
            this.areaCd = damageArea.getAreaCd();
            this.areaDscr = damageArea.getAreaDscr();
        }
        if(this.areaCd!=null){
            this.areaCd = this.areaCd.trim();
        }
        if(this.areaDscr!=null){
            this.areaDscr = this.areaDscr.trim();
        }
    }

}
