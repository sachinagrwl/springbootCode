package com.nscorp.obis.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.nscorp.obis.common.CommonConstants;
import com.nscorp.obis.domain.Terminal;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
@JsonIgnoreProperties(value = "true")
public class TermFreeDayDTO extends AuditInfoDTO{

//	@NotNull(message = "'termId' should not be Blank or Null.")
	@Schema(required = true,description="The Term Id of the station being used.", example="18245946233393")
//	@Digits(integer=CommonConstants.TERM_ID_MAX_SIZE, fraction = 0, message="Term Id cannot have more than 15 digits")
//	@Min(value = 1, message = "termId value must be greater than 0")
    private Long termId;

	private List<Terminal> terminalList;

	private String terminalName;

	@NotNull(message = "'closeDate' value should not be Blank or Null.")
	@Schema(required = true,description="The Close Date of the Term Day being used.", example="2014-02-17")
	private LocalDate closeDate;

	@NotNull(message = "'closeFromTime' value should not be Blank or Null.")
	@Schema(required = true,description="The Time Closed of the Term Day being used.", example="00:01")
	@DateTimeFormat(pattern = "HH-mm-ss")
    private LocalTime closeFromTime;

	@NotBlank(message = "'closeRsnCd' value should not be Null or Blank.")
	@Schema(required = true,description="The Close Code of the Term Day being used.", example="HOL")
	@Size(max=CommonConstants.CLOSE_RSN_CD_MAX_SIZE, message="'closeCode' size should not be more than {max}")
    private String closeRsnCd;

	@Schema(required = false,description="The Reason Description of the Term Day being used.", example="HOLIDAY")
	@Size(max=CommonConstants.CLOSE_RSN_DSC_MAX_SIZE, message="'reasonDescription' size should not be more than {max}")
    private String closeRsnDesc;

	@Schema(required = false,description="The Time Reopened of the Term Day being used.", example="23:59")
	@DateTimeFormat(pattern = "HH-mm-ss")
    private LocalTime closeToTime;

	@NotNull(message = "'freeDay' value should not be Blank or Null.")
	@Schema(required = true,description="The Free Day being used.", allowableValues = { "true", "false" })
    private String freeDay;

}
