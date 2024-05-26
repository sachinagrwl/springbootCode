package com.nscorp.obis.dto;

import com.nscorp.obis.common.CommonConstants;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class AARWhyMadeCodesDTO extends AuditInfoDTO{

    @NotNull(message = "W-AAR_WHY_MADE_CD Required' should not be Blank or Null.")
    @Range(min = 1, max = 127, message = "aarWhyMadeCd code must be in between 1 and 127")
    //@Pattern(regexp = "\\d+", message = "aarWhyMadeCd must be a numeric value")
    private int aarWhyMadeCd;
    @Size(max=CommonConstants.AAR_WHY_MADE_SIZE,
            message="AAR Why Code Des should not be more than {max} Character")
    private String aarDesc;

}
