package com.nscorp.obis.dto;


import java.sql.Time;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import com.nscorp.obis.domain.DayOfWeek;
import com.nscorp.obis.domain.NSCountry;
import com.nscorp.obis.domain.NSTimeZone;
import com.nscorp.obis.domain.Station;
import com.nscorp.obis.domain.TerminalInd;
import com.nscorp.obis.domain.TerminalType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class TerminalDTO extends AuditInfoDTO {
	
	private Long terminalId;
	
	@Length(max=30, message ="Length can't be more than 30")
	private String terminalName;

	private Long stnXrfId;
	
    @DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date expiredDate;
	
	private NSCountry terminalCountry;
	
	private Integer terminalZnPlus;
	
	private NSTimeZone terminalZnOffset;
	
	@Length(max=30, message ="Length can't be more than 30")
	private String terminalAddress1;
	
	@Length(max=19, message ="Length can't be more than 19")
	private String terminalCity1;
	
	@Length(max=2, message = "Only 2 Character are required ")
	private String terminalState1;
	
	@Pattern(regexp = "[0-9]{5}", message = "Max 5 Character is allowed ")
	private String terminalZipCode1;
	
	@Length(max=30, message ="Length can't be more than 30")
	private String terminalAddress2;
	
	@Length(max=19, message ="Length cant be more than 19")
	private String terminalCity2;

	@Length(max=2, message = "Only 2 Character are required ")
	private String terminalState2;
	
	@Pattern(regexp = "[0-9]{5}", message = "Max 5 Character is allowed ")
	private String terminalZipCode2;
	
	@Digits(integer = 3, fraction =0, message ="Only 3 value is allowed")
	@Schema(required = false,description="This is the prefix of the notification phone number.", example="229")
	private Integer externalAreaCd1;
	
	@Digits(integer = 3, fraction =0, message ="Only 3 value is allowed")
	@Schema(required = false,description="This is the prefix of the notification phone number.", example="229")
	private Integer externalExchange1;
	
	@Digits(integer = 4, fraction =0, message ="Only 4 value is allowed")
	@Schema(required = false,description="This is the suffix of the notification phone number.", example="1421")
	private Integer externalExtension1;
	
	@Digits(integer = 3, fraction =0, message ="Only 3 value is allowed")
	@Schema(required = false,description="This is the prefix of the notification phone number.", example="229")
	private Integer externalAreaCd2;
	
	@Digits(integer = 3, fraction =0, message ="Only 3 value is allowed")
	@Schema(required = false,description="This is the prefix of the notification phone number.", example="229")
	private Integer externalExchange2;
	
	@Digits(integer = 4, fraction =0, message ="Only 4 value is allowed")
	@Schema(required = false,description="This is the suffix of the notification phone number.", example="1421")
	private Integer externalExtension2;
	
	@Digits(integer = 3, fraction =0, message ="Only 3 value is allowed")
	@Schema(required = false,description="This is the prefix of the notification phone number.", example="229")
	private Integer externalAreaCd3;
	
	@Digits(integer = 3, fraction =0, message ="Only 3 value is allowed")
	@Schema(required = false,description="This is the prefix of the notification phone number.", example="229")
	private Integer externalExchange3;
	
	@Digits(integer = 4, fraction =0, message ="Only 4 value is allowed")
	@Schema(required = false,description="This is the suffix of the notification phone number.", example="1421")
	private Integer externalExtension3;
	
	@Digits(integer = 3, fraction =0, message ="Only 3 value is allowed")
	@Schema(required = false,description="This is the prefix of the notification phone number.", example="229")
	private Integer internalAreaCd1;
	
	@Digits(integer = 3, fraction =0, message ="Only 3 value is allowed")
	@Schema(required = false,description="This is the prefix of the notification phone number.", example="229")
	private Integer internalExchange1;
	
	@Digits(integer = 4, fraction =0, message ="Only 4 value is allowed")
	@Schema(required = false,description="This is the suffix of the notification phone number.", example="1421")
	private Integer internalExtension1;
	
	@Digits(integer = 3, fraction =0, message ="Only 3 value is allowed")
	@Schema(required = false,description="This is the prefix of the notification phone number.", example="229")
	private Integer internalAreaCd2;
	
	@Digits(integer = 3, fraction =0, message ="Only 3 value is allowed")
	@Schema(required = false,description="This is the prefix of the notification phone number.", example="229")
	private Integer internalExchange2;
	
	@Digits(integer = 4, fraction =0, message ="Only 4 value is allowed")
	@Schema(required = false,description="This is the suffix of the notification phone number.", example="1421")
	private Integer internalExtension2;
	
	@Digits(integer = 3, fraction =0, message ="Only 3 value is allowed")
	@Schema(required = false,description="This is the prefix of the notification phone number.", example="229")
	private Integer internalAreaCd3;
	
	@Digits(integer = 3, fraction =0, message ="Only 3 value is allowed")
	@Schema(required = false,description="This is the prefix of the notification phone number.", example="229")
	private Integer internalExchange3;
	
	@Digits(integer = 4, fraction =0, message ="Only 4 value is allowed")
	@Schema(required = false,description="This is the suffix of the notification phone number.", example="1421")
	private Integer internalExtension3;

	@Digits(integer = 3, fraction =0, message ="Only 3 value is allowed")
	@Schema(required = false,description="This is the prefix of the notification phone number.", example="229")
	private Integer externalFaxArea1;
	
	@Digits(integer = 4, fraction =0, message ="Only 4 value is allowed")
	@Schema(required = false,description="This is the prefix of the notification phone number.", example="2249")
	private Integer externalFaxExtension1;
	
	@Digits(integer = 3, fraction =0, message ="Only 3 value is allowed")
	@Schema(required = false,description="This is the prefix of the notification phone number.", example="229")
	private Integer externalFaxExchange1;

	@Digits(integer = 3, fraction =0, message ="Only 3 value is allowed")
	@Schema(required = false,description="This is the prefix of the notification phone number.", example="229")
	private Integer externalFaxArea2;
	
	@Digits(integer = 4, fraction =0, message ="Only 4 value is allowed")
	@Schema(required = false,description="This is the prefix of the notification phone number.", example="2294")
	private Integer externalFaxExtension2;
	
	@Digits(integer = 3, fraction =0, message ="Only 3 value is allowed")
	@Schema(required = false,description="This is the prefix of the notification phone number.", example="229")
	private Integer externalFaxExchange2;

	@Digits(integer = 3, fraction =0, message ="Only 3 value is allowed")
	@Schema(required = false,description="This is the prefix of the notification phone number.", example="229")
	private Integer externalFaxArea3;
	
	@Digits(integer = 4, fraction =0, message ="Only 4 value is allowed")
	@Schema(required = false,description="This is the prefix of the notification phone number.", example="2249")
	private Integer externalFaxExtension3;
	
	@Digits(integer = 3, fraction =0, message ="Only 3 value is allowed")
	@Schema(required = false,description="This is the prefix of the notification phone number.", example="229")
	private Integer externalFaxExchange3;

	@Digits(integer = 3, fraction =0, message ="Only 3 value is allowed")
	@Schema(required = false,description="This is the prefix of the notification phone number.", example="229")
	private Integer internalFaxArea1;
	
	@Digits(integer = 4, fraction =0, message ="Only 4 value is allowed")
	@Schema(required = false,description="This is the prefix of the notification phone number.", example="2449")
	private Integer internalFaxExtension1;
	
	@Digits(integer = 3, fraction =0, message ="Only 3 value is allowed")
	@Schema(required = false,description="This is the prefix of the notification phone number.", example="229")
	private Integer internalFaxExchange1;

	@Digits(integer = 3, fraction =0, message ="Only 3 value is allowed")
	@Schema(required = false,description="This is the prefix of the notification phone number.", example="229")
	private Integer internalFaxArea2;
	
	@Digits(integer = 4, fraction =0, message ="Only 4 value is allowed")
	@Schema(required = false,description="This is the prefix of the notification phone number.", example="2249")
	private Integer internalFaxExtension2;
	
	@Digits(integer = 3, fraction =0, message ="Only 3 value is allowed")
	@Schema(required = false,description="This is the prefix of the notification phone number.", example="229")
	private Integer internalFaxExchange2;

	@Digits(integer = 3, fraction =0, message ="Only 3 value is allowed")
	@Schema(required = false,description="This is the prefix of the notification phone number.", example="229")
	private Integer internalFaxArea3;
	
	@Digits(integer = 4, fraction =0, message ="Only 4 value is allowed")
	@Schema(required = false,description="This is the prefix of the notification phone number.", example="2529")
	private Integer internalFaxExtension3;
	
	@Digits(integer = 3, fraction =0, message ="Only 3 value is allowed")
	@Schema(required = false,description="This is the prefix of the notification phone number.", example="229")
	private Integer internalFaxExchange3;

	@Schema(required = false,description="Type 'Y'-Yes and 'N'-No")
	@Pattern(regexp="^(Y|N|null|)$",message="Only Y, N & Null is allowed")
	private String dayLightSaveIndicator;
		
	@Schema(required = false,description="The Renotify Time being used.", example="19")
	private String renotifyTime;
	
	private List<DayOfWeek> renotifyDays;
	
	@Schema(required = false,description="The Close Out Time being used.", example="19")
	private String deferredTime;
	
	@Digits(integer = 2, fraction = 0, message = " Only 2 integer is allowed")
	@Schema(required = false,description="This is a 2-digit code used by Norfolk Southern to identify a terminal.")
	private Integer nsTerminalId;
	
	private TerminalType terminalType;

	@Pattern(regexp="^(Y|N|)$",message="Only Y, N & Null is allowed")
	private String haulageIndicator;
	
	@Pattern(regexp="^(Y|N|)$",message="Only Y, N & Null is allowed")
	private String idcsTerminalIndicator;
	
	@Pattern(regexp="^(Y|N|)$",message="Only Y, N & Null is allowed")
	private String sswTerminalIndicator;
	
	@Schema(required = false,description="The Close Out Time being used.", example="19")
	@DateTimeFormat(pattern = "HH")
	private Time terminalCloseOutTime;
	 
	@Pattern(regexp="^(Y|N|)$",message="Only Y, N & Null is allowed")
	private String hitchCheckIndicator;
	
	private TerminalInd terminalInd;
	
	private Station station;

}
