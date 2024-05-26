package com.nscorp.obis.dto;

import java.sql.Timestamp;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.nscorp.obis.common.CommonConstants;
import com.nscorp.obis.common.NullOrNotBlank;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@EqualsAndHashCode(callSuper=false)
public class DrayageCompanyDTO extends AuditInfoDTO{
	@NullOrNotBlank(min=1, max = 4, message = "Drayage Id length should be between 1 and 4.")
    @Schema(required = true, description = "This identifies this is trucker as defined in SCAC.")
	private String drayageId;

    @NotNull(message = "TIA ind Should not be Null.")
    @NullOrNotBlank(min = CommonConstants.TIA_END_MIN_SIZE, max = CommonConstants.TIA_END_MAX_SIZE, message = "tiaInd field length should be {max}")
    @Schema(required = true,description="The TIA ind being used.")
    @Pattern(regexp="^(A|S|N|)$",message="Only A, S & N is allowed")
	private String tiaInd;

    @Schema(required = false,description="The Bonded Carrier being used.")
    @Pattern(regexp="^(Y|N|)$",message="Only Y, N & Null is allowed")
	private String bondedCarrier;

    @Schema(required = false,description="Bonded Auth Id text being used.", example="")
    @Size(max=CommonConstants.BONDED_AUTH_ID_MAX_SIZE, message="Bonded Auth Id text size should not be more than {max}")
    private String bondedAuthId;

    @Schema(required = false,description="The Tia suspend Date  being used.", example="02-26-1995")
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd")
    private Date tiaSuspendDate;

    @Schema(required = false,description="The eqAuth ind id being used.")
    @Pattern(regexp="^(Y|N|)$",message="Only Y, N & Null is allowed")
    private String eqAuthInd;	
}
