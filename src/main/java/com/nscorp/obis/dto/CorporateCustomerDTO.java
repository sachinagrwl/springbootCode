package com.nscorp.obis.dto;

import java.util.List;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.nscorp.obis.common.CommonConstants;
import com.nscorp.obis.domain.CorporateCustomerDetail;
import com.nscorp.obis.domain.Customer;
import com.nscorp.obis.domain.StorageOverrideBillToParty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class CorporateCustomerDTO {

//	@NotNull(message = "Corporate Customer Id Should not be Null.")
//	@NotBlank(message = "Corporate Customer Id Should not be Blank or Null.")
	@Schema(required = true,description="The Corporate Customer Id being used.", example="1.639857332557E12")
	@Digits(integer=15, fraction=0, message= "Corporate Customer id cannot be more than 15")
	@Min(value = 1, message = "Corporate Customer id Length must be greater than 0")
	private Long corporateCustomerId;

	@NotBlank(message = "Corporate Long Name Should not be Null.")
	@Schema(required = true,description="The Corporate Long Name being used.", example="KLINE-THE RAILBRIDGE CORP")
	@Size(max=CommonConstants.CORP_LONG_NAME_MAX_SIZE)
	private String corporateLongName;
	
	@NotNull(message = "Corporate Short Name Should not be Null.")
	@Schema(required = true,description="The Corporate Short Name being used.", example="KLNC")
	@Size(max=CommonConstants.CORP_SHORT_NAME_MAX_SIZE)
	private String corporateShortName;
	
	@NotNull(message = "Corporate Id Should not be Null.")
	@Schema(required = true,description="The Corporate Id being used.", example="6.2839942823086E13")
	@Digits(integer=CommonConstants.CUST_ID_MAX_SIZE, fraction=0, message= "Customer id cannot be more than 15")
	@Min(value = 1, message = "Customer id Length must be greater than 0")
	private Long customerId;
	
	@Schema(required = false,description="The ICGH Code being used.")
	@Size(max=CommonConstants.ICGH_CD_MAX_SIZE)
	private String icghCd;
	
	@Schema(required = false,description="The Primary Line Of Business being used.")
	@Size(max=CommonConstants.PRIMARY_LOB_MAX_SIZE)
	private String primaryLob;

	@Schema(required = false,description="The Secondary Line Of Business being used.")
	@Size(max=CommonConstants.SECONDARY_LOB_MAX_SIZE)
	private String secondaryLob;

	@Schema(required = false,description="The SCAC Trucking Company being used.")
	@Size(max=CommonConstants.SCAC_MAX_SIZE)
	private String scac;

	@Schema(required = false,description="The Terminal Field Status being used.")
	@Size(max=CommonConstants.TERMINAL_FEED_ENABLED_MAX_SIZE)
	@Pattern(regexp="^(Y|N|)$",message="Only Y, N & Null is allowed")
	private String terminalFeedEnabled;

	@Schema(required = false,description="The Account Manager being used.")
	@Size(max=CommonConstants.ACCOUNT_MANAGER_MAX_SIZE)
	private String accountManager;
	
//	private Customer customer;
	
	@Size(min =1,message="Corporate Customer must have at least one Primary Six")
	private List<CorporateCustomerDetail> corporateCustomerDetail;
	
	private StorageOverrideBillToParty storageOverrideBillToParty;

}
