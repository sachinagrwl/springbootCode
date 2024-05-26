package com.nscorp.obis.dto;

import java.util.Date;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.nscorp.obis.common.CommonConstants;
import com.nscorp.obis.common.NullOrNotBlank;

import io.swagger.v3.oas.annotations.media.Schema;
import net.bytebuddy.implementation.bind.annotation.Empty;

public class DrayageScacDTO extends AuditInfoDTO {
	@NullOrNotBlank(min=1, max = 4, message = "Drayage Id length should be between 1 and 4.")
	@Schema(required=true,description = "This identifies this is trucker as defined in SCAC")
	private String drayId;

	@NullOrNotBlank(min=1, max = 99, message = "Carrier Name length should be between 1 and 99.")
	@Schema(required=false,description = "This is carrier�s name")
	private String carrierName;
	
	@NullOrNotBlank(min=6, max = 6, message = "MCN ICC Number length should be 6")
	@Schema(required=false,description = "This is the ICC docket number or mode description")
	private String mcdIccNr;

	@NullOrNotBlank(min=1, max = 50, message = "Carrier Address length should be between 1 and 50.")
	@Schema(required=false,description = "This is the street address of the carrier")
	private String carrierAddress;

	@NullOrNotBlank(min=1, max = 40, message = "Carrier City length should be between 1 and 40.")
	@Schema(required=false,description = "This is the city location of the carrier")
	private String carrierCity;

	@NullOrNotBlank(min=1, max = 2, message = "State length should be between 1 and 2.")
	@Schema(required=false,description = "The abbreviation of the state location")
	private String state;

	@NullOrNotBlank(min=1, max = 20, message = "Country length should be between 1 and 20.")
	@Schema(required=false,description = "This is the country for this carrier")
	private String country;

	@NullOrNotBlank(min=1, max = 10, message = "Zipcode length should be between 1 and 10.")
	@Schema(required=false,description = "This is the zipcode")
	private String zipCode;

	@NullOrNotBlank(min=1, max = 10, message = "Phone Number length should be between 1 and 10.")
	@Pattern(regexp = "^\\d{10}$", message = "Phone number should have 10 digits")
	@Schema(required=false,description = "This is the phone number of this carrier")
	private String phoneNumber;

	@Schema(required=false,description = "This is used to track last updated date")
	private Date lastUpdDate;

	public DrayageScacDTO() {
		super();
	}

	public String getDrayId() {
		return drayId;
	}

	public void setDrayId(String drayId) {
		this.drayId = drayId;
	}

	public String getCarrierName() {
		return carrierName;
	}

	public void setCarrierName(String carrierName) {
		this.carrierName = carrierName;
	}

	public String getMcdIccNr() {
		return mcdIccNr;
	}

	public void setMcdIccNr(String mcdIccNr) {
		this.mcdIccNr = mcdIccNr;
	}

	public String getCarrierAddress() {
		return carrierAddress;
	}

	public void setCarrierAddress(String carrierAddress) {
		this.carrierAddress = carrierAddress;
	}

	public String getCarrierCity() {
		return carrierCity;
	}

	public void setCarrierCity(String carrierCity) {
		this.carrierCity = carrierCity;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Date getLastUpdDate() {
		return lastUpdDate;
	}

	public void setLastUpdDate(Date lastUpdDate) {
		this.lastUpdDate = lastUpdDate;
	}

	@Override
	public String toString() {
		return "DrayageScacDTO [drayId=" + drayId + ", carrierName=" + carrierName + ", mcdIccNr=" + mcdIccNr
				+ ", carrierAddress=" + carrierAddress + ", carrierCity=" + carrierCity + ", state=" + state
				+ ", country=" + country + ", zipCode=" + zipCode + ", phoneNumber=" + phoneNumber + ", lastUpdDate="
				+ lastUpdDate + ", getUversion()=" + getUversion() + ", getCreateUserId()=" + getCreateUserId()
				+ ", getCreateDateTime()=" + getCreateDateTime() + ", getUpdateUserId()=" + getUpdateUserId()
				+ ", getUpdateDateTime()=" + getUpdateDateTime() + ", getUpdateExtensionSchema()="
				+ getUpdateExtensionSchema() + "]";
	}
	
}
