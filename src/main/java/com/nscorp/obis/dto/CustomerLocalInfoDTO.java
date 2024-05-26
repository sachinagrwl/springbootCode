package com.nscorp.obis.dto;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Range;

import com.nscorp.obis.common.CommonConstants;
import com.nscorp.obis.domain.CustomerCountry;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class CustomerLocalInfoDTO extends AuditInfoDTO {
	
	@Digits(integer=CommonConstants.CUST_ID_MAX_SIZE, fraction=0, message= "Customer id cannot be more than 15")
	@Min(value = 1, message = "Customer id Length must be greater than 0")
	@Schema(required = true,description="The Customer Id being used.")
	@NotNull
	@Range(min = 1,max = Long.MAX_VALUE)
	private Long customerId;

	@Digits(integer=CommonConstants.TERM_ID_MAX_SIZE, fraction=0, message= "Terminal id cannot be more than 15")
	@Min(value = 1, message = "Terminal id Length must be greater than 0")
	@Schema(required = true,description="The Terminal Id being used.")
	@NotNull
	@Range(min = 1,max = Long.MAX_VALUE)
	private Long terminalId;

	@Size(min=0, max=35, message = "Customer name should not have more than 35 characters.")
	@Schema(required=false, description="The name of the customer to be notified.")
	private String customerName;

	@Size(min=0, max=35, message = "Customer Address 1 should not have more than 35 characters.")
	@Schema(required=false, description="The address 1 of the customer to be notified.")
	private String custAddr1;

	@Size(min=0, max=35, message = "Customer Address 2 should not have more than 35 characters.")
	@Schema(required=false, description="The address 2 of the customer to be notified.")
	private String custAddr2;

	@Size(min=0, max=19, message = "Customer City should not have more than 19 characters.")
	@Schema(required=false, description="The city of the customer to be notified.")
	private String customerCity;

	@Size(min=0, max=10, message = "Customer Zip code should not have more than 10 characters.")
	@Schema(required=false, description="The zip code of the customer to be notified.")
	private String custZipCd;

	@Size(min=0, max=2, message = "Customer State or Province should not have more than 2 characters.")
	@Schema(required=false, description="The State or Province of the customer to be notified.")
	private String customerState;

	@Schema(required=false, description="The Country of the customer to be notified.")
	private CustomerCountry custCountry;

	@Size(min=0, max=30, message = "Customer Country should not have more than 30 characters.")
	@Schema(required=false, description="Full name of the person to be contacted")
	private String localContact;

	@Min(value = CommonConstants.CUST_FAX_AREA_MIN_SIZE, message = "Area code for fax value must be greater than 99")
	@Max(value = CommonConstants.CUST_FAX_AREA_MAX_SIZE, message = "Area code for fax value must be less than 1000")
	@Digits(integer = CommonConstants.CUST_FAX_AREA_DIGIT_SIZE, fraction = CommonConstants.CUST_FAX_AREA_FRACTION)
	@Schema(required =false,description="Area code for fax number of the contact", example="904")
	private Integer faxAreaCd;

	@Min(value = CommonConstants.CUST_FAX_EXCH_MIN_SIZE, message = "Exchange for fax number must be greater than 0")
	@Max(value = CommonConstants.CUST_FAX_EXCH_MAX_SIZE, message = "Exchange for fax number must be less than 1000")
	@Digits(integer = CommonConstants.CUST_FAX_EXCH_DIGIT_SIZE, fraction = CommonConstants.CUST_FAX_EXCH_FRACTION)
	@Schema(required = false,description="Exchange for fax number", example="229")
	private Integer faxExch;

	@Min(value = CommonConstants.CUST_FAX_EXT_MIN_SIZE, message = "Extension for fax number must be greater than 0")
	@Max(value = CommonConstants.CUST_FAX_EXT_MAX_SIZE, message = "Extension for fax number must be less than 10000")
	@Digits(integer = CommonConstants.CUST_FAX_EXT_DIGIT_SIZE, fraction = CommonConstants.CUST_FAX_EXT_FRACTION)
	@Schema(required = false,description="Extension for fax number", example="1421")
	private Integer faxExt;

	@Min(value = 100, message = "Area code for phone number must be positive and greater than 99")
	@Digits(integer = CommonConstants.CUST_AREA_DIGIT_SIZE, fraction = CommonConstants.CUST_AREA_FRACTION)
	@Max(value = 999, message = "Area code for phone number must be less than 1000")
	@Schema(required =false,description="The Customer area code.", example="904")
	private Integer custAreaCd;

	@Min(value = CommonConstants.CUST_EXCH_MIN_SIZE, message = "Exchange for phone number must be positive")
	@Digits(integer = CommonConstants.CUST_EXCH_DIGIT_SIZE, fraction = CommonConstants.CUST_EXCH_FRACTION)
	@Max(value = 999, message = "Customer Exchange Number should be less than 1000")
	@Schema(required = false,description="Exchange for phone number.", example="229")
	private Integer custExch;

	@Min(value = 0, message = "Extension for phone number must be positive")
	@Max(value = 32767)
	@Digits(integer = 5, fraction = 0)
	@Schema(required = false,description="Extension for phone number.")
	private Integer custExt;

	@Size(min=0, max=30, message = "Any other pertinent information should not have more than 30 characters.")
	@Schema(required=false, description="Any other pertinent information about the contact")
	private String addtlInfo;

	@Pattern(regexp = "^\\d{4}$", message = "Base for phone number must be a 4-digit positive number.")
	@Schema(required = false,description="Base for phone number.", example="1421")
	private String phoneBase;

	@Override
	public String toString() {
		return "CustomerLocalInfoDTO [customerId=" + customerId + ", terminalId=" + terminalId + ", customerName="
				+ customerName + ", custAddr1=" + custAddr1 + ", custAddr2=" + custAddr2 + ", customerCity="
				+ customerCity + ", custZipCd=" + custZipCd + ", customerState=" + customerState + ", custCountry="
				+ custCountry + ", localContact=" + localContact + ", faxAreaCd=" + faxAreaCd + ", faxExch=" + faxExch
				+ ", faxExt=" + faxExt + ", custAreaCd=" + custAreaCd + ", custExch=" + custExch + ", custExt="
				+ custExt + ", addtlInfo=" + addtlInfo + ", phoneBase=" + phoneBase + ", getUversion()=" + getUversion()
				+ ", getCreateUserId()=" + getCreateUserId() + ", getCreateDateTime()=" + getCreateDateTime()
				+ ", getUpdateUserId()=" + getUpdateUserId() + ", getUpdateDateTime()=" + getUpdateDateTime()
				+ ", getUpdateExtensionSchema()=" + getUpdateExtensionSchema() + "]";
	}
	
	
	
}
