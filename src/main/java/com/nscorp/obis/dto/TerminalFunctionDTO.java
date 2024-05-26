package com.nscorp.obis.dto;

import java.sql.Date;

import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class TerminalFunctionDTO extends AuditInfoDTO{

	Long terminalId;

    private String functionName;
    private String functionDesc;
    
    @Size(max=4,message = "Status Flag cannot be more than 4 characters")

    private String statusFlag;

    private Date effectiveDate;
    private Date endDate;
    
    private String terminalName;
}
