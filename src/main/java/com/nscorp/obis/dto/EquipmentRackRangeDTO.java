package com.nscorp.obis.dto;

import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)

public class EquipmentRackRangeDTO extends AuditInfoDTO{

	
	private Long equipRackRangeId;
    private String equipInit;
    
    @NotNull(message = "equipLowNbr Should not be Blank or Null.")
    @Digits(integer =6, fraction = 0, message = "EquipLowNbr must be less than 6")
    @Min(value = 1, message = "equipLowNbr value must be greater than 0")
    //@Size(max=CommonConstants.CONTAINER_NR_LOW_MAX_SIZE, message="EquipLowNbr size should not be more than {max}")
    private Long equipLowNbr;
    
    @NotNull(message = "equipHighNbr Should not be Blank or Null.")
    @Digits(integer =6, fraction = 0, message = "EquipHighNbr must be less than 6")
    @Min(value = 1, message = "equipHighNbr value must be greater than 0")
    //@Size(max=CommonConstants.CONTAINER_NR_HIGH_MAX_SIZE, message="EquipHighNbr size should not be more than {max}")
    private Long equipHighNbr;
    
    @Valid
    @NotEmpty
    private String equipType;
    private String aarType;
    private String equipInd;
}
