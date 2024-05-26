package com.nscorp.obis.dto;
import java.util.List;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class SpecialActivityNotifyDTO extends AuditInfoDTO{

    @Min(value = 1, message = "Activity Id value must be greater than 0")
    @Digits(integer=5, fraction=0, message="Activity Id should not have more than 5 digits. ")
	@Schema(required = true,description="Activity Id.", example="39715")
	private Integer activityId;

    @Size(min=0, max=20, message = "Activity Description should not have more than 20 characters.")
	@Schema(required = false,description="Activity Description", example="SEAL APPLIED ")
	private String activityDesc;
    
}
