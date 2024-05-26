package com.nscorp.obis.dto;

import com.nscorp.obis.common.CommonConstants;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)

public class HazRestrictionDTO  extends AuditInfoDTO  {
    @NotBlank(message = "'W-UN_CD' should not be Blank or Null.")
    @Size(min= CommonConstants.UNCD_MIN_SIZE, max=CommonConstants.UNCD_MAX_SIZE,
            message="UN CD size should be between {min} and {max}")
    private String unCd;
    @NotBlank(message = "'W-RESTR_CLS' should not be Blank or Null.")
    @Pattern(regexp="^(A|B|C|E|F|I|P|R|Q)$",message="W-Valid values are A, B, C, E, F, I, P, R, and Q")
    private String restoreClass;



}
