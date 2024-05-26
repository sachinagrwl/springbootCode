package com.nscorp.obis.dto;

import java.sql.Date;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Range;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.nscorp.obis.common.CommonConstants;
import com.nscorp.obis.common.NullOrNotBlank;
import com.nscorp.obis.domain.CustomerCountry;

import io.swagger.v3.oas.annotations.media.Schema;

public class CustomerInfoDTO extends AuditInfoDTO {

	@Digits(integer = CommonConstants.CUST_ID_MAX_SIZE, fraction = 0, message = "Customer id cannot be more than 15")
	@Schema(required = true, description = "The Customer Id being used.", example = "3.000768653273E12")
	private long customerId;

	@NullOrNotBlank(min = CommonConstants.CUST_NR_MIN_SIZE, max = CommonConstants.CUST_NR_MAX_SIZE, message = "customerNumber field length should be between 1 and 10.")
//	@Pattern(regexp = "^[016].*", message = "Number should start either with 0 or 1 or 6")
	@Schema(required = false, description = "The field designates the customer number.")
	private String customerNumber;

	@NotNull
	@NullOrNotBlank(min = CommonConstants.CUST_NM_MIN_SIZE, max = CommonConstants.CUST_NM_MAX_SIZE, message = "customerName field length should be between 1 and 35.")
	@Schema(required = false, description = "The name of the customer to be notified.")
	private String customerName;

	@NullOrNotBlank(min = CommonConstants.CUST_OWNR_IND_MIN_SIZE, max = CommonConstants.CUST_OWNR_IND_MAX_SIZE, message = "Equipment Owner field must be length equals to 1.")
	@Schema(required = false, description = "This field indicates the equipment owner")
	private String equipmentOwner;

	@NullOrNotBlank(min = CommonConstants.CUST_CR_VALID_IND_MIN_SIZE, max = CommonConstants.CUST_CR_VALID_IND_MAX_SIZE, message = "credit field must be length equals to 1.")
	@Pattern(regexp = "^[YN]{1}", message = "credit should be either Y or N !")
	@Schema(required = false, description = "This indicator defines whether credit is allowed for this customer")
	private String credit;

	@NullOrNotBlank(min = CommonConstants.CUST_ACTY_STAT_MIN_SIZE, max = CommonConstants.CUST_ACTY_STAT_MAX_SIZE, message = "activity status field must be length equals to 1..")
	@Pattern(regexp = "^[AI]{1}", message = "activity status should be either A or I !")
	@Schema(required = false, description = "This field indicates whether the customer is active or inactive")
	private String activityStatus;

	@NullOrNotBlank(min = CommonConstants.CUST_ADDR_1_MIN_SIZE, max = CommonConstants.CUST_ADDR_1_MAX_SIZE, message = "Customer Address 1 length should be between 1 and 35.")
	@Schema(required = false, description = "This is the address of the customer")
	private String address1;

	@NullOrNotBlank(min = CommonConstants.CUST_ADDR_2_MIN_SIZE, max = CommonConstants.CUST_ADDR_2_MAX_SIZE, message = "Customer Address 2 length should be between 1 and 35.")
	@Schema(required = false, description = "This is the continuation of the address of the customer")
	private String address2;

	@NotNull
	@NullOrNotBlank(min = CommonConstants.CUST_CTY_MIN_SIZE, max = CommonConstants.CUST_CTY_MAX_SIZE, message = "Customer City length should be between 1 and 19.")
	@Schema(required = false, description = "This is the city location for the customer")
	private String city;

	@NullOrNotBlank(min = CommonConstants.CUST_ST_PV_MIN_SIZE, max = CommonConstants.CUST_ST_PV_MAX_SIZE, message = "Customer State or Province length should be between 1 and 2.")
	@Schema(required = false, description = "State or province abbreviation of customers location")
	private String state;

	@NullOrNotBlank(min = CommonConstants.CUST_ZIP_CD_MIN_SIZE, max = CommonConstants.CUST_ZIP_CD_MAX_SIZE, message = "Customer Zip code length should be between 1 and 10.")
	@Schema(required = false, description = "The zip code for the customer location")
	private String zipcode;

	@Schema(required = false, description = "This is the country of the customer location, either USA, MEX, or CAN")
	private CustomerCountry country;

	@NullOrNotBlank(min = CommonConstants.CUST_PRIME_CONTACT_MIN_SIZE, max = CommonConstants.CUST_PRIME_CONTACT_MAX_SIZE, message = "Customer Prime contact length should be between 1 and 30.")
	@Schema(required = false, description = "This is the name of the coordinator to contact for this customer")
	private String primeContact;

//	@Min(value = CommonConstants.CUST_FAX_AREA_MIN_SIZE, message = "Area code for fax value must be greater than 99")
//	@Max(value = CommonConstants.CUST_FAX_AREA_MAX_SIZE, message = "Area code for fax value must be less than 1000")
//	@Digits(integer = CommonConstants.CUST_FAX_AREA_DIGIT_SIZE, fraction = CommonConstants.CUST_FAX_AREA_FRACTION)
	@Schema(required = false, description = "This is the area code of the FAX Number of the coordinator or account manager")
	private Integer faxAreaCode;

	@Min(value = CommonConstants.CUST_FAX_EXCH_MIN_SIZE, message = "Exchange for fax number must be greater than 0")
	@Max(value = CommonConstants.CUST_FAX_EXCH_MAX_SIZE, message = "Exchange for fax number must be less than 1000")
	@Digits(integer = CommonConstants.CUST_FAX_EXCH_DIGIT_SIZE, fraction = CommonConstants.CUST_FAX_EXCH_FRACTION)
	@Schema(required = false, description = "The exchange for the FAX of the coordinator or account manager")
	private Integer faxExchange;

	@Min(value = CommonConstants.CUST_FAX_EXT_MIN_SIZE, message = "Extension for fax number must be greater than 0")
	@Max(value = CommonConstants.CUST_FAX_EXT_MAX_SIZE, message = "Extension for fax number must be less than 10000")
	@Digits(integer = CommonConstants.CUST_FAX_EXT_DIGIT_SIZE, fraction = CommonConstants.CUST_FAX_EXT_FRACTION)
	@Schema(required = false, description = "The number for the FAX of the coordinator or account manager")
	private Integer faxNumber;

//	@Min(value = 100, message = "Area code for phone number must be positive and greater than 99")
//	@Digits(integer = CommonConstants.CUST_AREA_DIGIT_SIZE, fraction = CommonConstants.CUST_AREA_FRACTION)
//	@Max(value = 999, message = "Area code for phone number must be less than 1000")
	@Schema(required = false, description = "This is the area code of the contact")
	private Integer customerAreaCode;

	@Min(value = CommonConstants.CUST_EXCH_MIN_SIZE, message = "Exchange for phone number must be greater than 99")
	@Digits(integer = CommonConstants.CUST_EXCH_DIGIT_SIZE, fraction = CommonConstants.CUST_EXCH_FRACTION)
	@Max(value = 999, message = "Customer Exchange Number should be less than 1000")
	@Schema(required = false, description = "This is the phone exchange number of the contact")
	private Integer customerExchangeNumber;

	@Min(value = CommonConstants.CUST_EXT_MIN_SIZE, message = "Extension for phone number must be positive")
	@Max(value = CommonConstants.CUST_EXT_MAX_SIZE, message = "Extension Can't be greater than "+CommonConstants.CUST_EXT_MAX_SIZE)
	@Digits(integer = CommonConstants.CUST_EXT_DIGIT_SIZE, fraction = CommonConstants.CUST_EXT_FRACTION)
	@Schema(required = false, description = "This is the phone extension number of the contact")
	private Integer customerExtension;

	@NullOrNotBlank(min = CommonConstants.CUST_BASE_MIN_SIZE, max = CommonConstants.CUST_BASE_MAX_SIZE, message = "Customer Base length should be between 1 and 4.")
	@Pattern(regexp = "^\\d{4}$", message = "Customer Base should have 4 digits!")
	@Schema(required = false, description = "The number for the contact")
	private String customerBase;

	@Range(min = CommonConstants.CUST_IM_REG_MIN_RANGE, max = CommonConstants.CUST_IM_REG_MAX_RANGE, message = "Customer territory must be in the range of 0-99")
	@Schema(required = false, description = "The marketing territory where this customer is located")
	private Integer territory;

	@Range(min = CommonConstants.CUST_IM_REG_MIN_RANGE, max = CommonConstants.CUST_IM_REG_MAX_RANGE, message = "Customer Region must be in the range of 0-99")
	@Schema(required = false, description = "The marketing region where this customer is located")
	private Integer region;

	@NullOrNotBlank(min = CommonConstants.CUST_DISTRICT_MIN_SIZE, max = CommonConstants.CUST_DISTRICT_MAX_SIZE, message = "Customer District field must be length should be between 1 to 2.")
	@Pattern(regexp = "[0-9][0-9]", message = "district should be in the range 00-99 !")
	@Schema(required = false, description = "The marketing district where this customer is located")
	private String district;

	@NullOrNotBlank(min = CommonConstants.CUST_CIF_NR_MIN_SIZE, max = CommonConstants.CUST_CIF_NR_MAX_SIZE, message = "Customer Cif length should be between 1 and 15.")
	@Schema(required = false, description = "This field is reserved for future use with CIF")
	private String cifNumber;

	@NullOrNotBlank(min = CommonConstants.CUST_ACCT_DSC_MIN_SIZE, max = CommonConstants.CUST_ACCT_DSC_MAX_SIZE, message = "Customer Account Description length should be between 1 and 30.")
	@Schema(required = false, description = "This is informational text describing this customer")
	private String accountDescription;

	@NullOrNotBlank(min = CommonConstants.CUST_NM_GS_MIN_SIZE, max = CommonConstants.CUST_NM_GS_MAX_SIZE, message = "Customer Spelling name length should be between 1 and 10.")
	@Schema(required = false, description = "This is the good spelling of the customer name")
	private String goodSpellingName;

	@NullOrNotBlank(min = CommonConstants.CUST_SHIP_PRTY_MIN_SIZE, max = CommonConstants.CUST_SHIP_PRTY_MAX_SIZE, message = "shipment priority field must be length should be equal to 1.")
	@Pattern(regexp = "^[1-5]?$", message = "shipment priority should be in the range 1-5 !")
	@Schema(required = false, description = "This field defines the priority for this shipment")
	private String shipmentPriority;

	@NullOrNotBlank(min = CommonConstants.CUST_INT_IND_MIN_SIZE, max = CommonConstants.CUST_INT_IND_MAX_SIZE, message = "Intermodal identification field must be length equals to 1.")
	@Pattern(regexp = "^[OB]{1}", message = "Intermodal identification should be either O or B !")
	@Schema(required = false, description = "This field identifies an Intermodal customer")
	private String intermodalIdentification;

	@NullOrNotBlank(min = CommonConstants.CUST_SC_MIN_SIZE, max = CommonConstants.CUST_SC_MAX_SIZE, message = "Salesman Code length should be between 1 and 4.")
	@Schema(required = false, description = "The initials of NS Salesperson")
	private String salesmanCode;

	@Schema(required = false, description = "expired date", example = "12/21/2022")
	@JsonFormat(shape = Shape.STRING,pattern = "yyyy-MM-dd")
	private Date expiredDate;

	@NullOrNotBlank(min = CommonConstants.CUST_NS_ACCT_DSC_MIN_SIZE, max = CommonConstants.CUST_NS_ACCT_DSC_MAX_SIZE, message = "Ns Account Description length should be between 1 and 30.")
	@Schema(required = false, description = "This screen details the various types of Norfolk Southern customers")
	private String nsAccoutDescription;

	@NullOrNotBlank(min = CommonConstants.CUST_TEAM_AUD_CD_MIN_SIZE, max = CommonConstants.CUST_TEAM_AUD_CD_MAX_SIZE, message = "team Aud cd length should be between 1 and 3.")
	@Schema(required = false, description = "This screen details the various types of Norfolk Southern customers")
	private String teamAudCd;

	@Digits(integer = CommonConstants.CUST_ID_MAX_SIZE, fraction = 0, message = "Customer id cannot be more than 15")
	@Schema(required = false, description = "ID of the Customer paying the bill.", example = "3.000768653273E12")
	private Long billToCustomerId;

	@NullOrNotBlank(min = CommonConstants.CUST_NR_MIN_SIZE, max = CommonConstants.CUST_NR_MAX_SIZE, message = "customerNumber field length should be between 1 and 10.")
//	@Pattern(regexp = "^[016].*", message = "Number should start either with 0 or 1 or 6")
	@Schema(required = false, description = "Number of the Customer paying the bill.")
	private String billToCustomerNumber;

	@NullOrNotBlank(min = CommonConstants.CUST_NM_MIN_SIZE, max = CommonConstants.CUST_NM_MAX_SIZE, message = "customerName field length should be between 1 and 35.")
	@Schema(required = false, description = "Customer Name of the Customer paying the bill")
	private String billToCustomerName;
	
	@Schema(required = false, description = "SCAC/DrayAge Id..")
	private String scac;

	public long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}

	public String getCustomerNumber() {
		return customerNumber != null ? customerNumber.trim() : customerName;
	}

	public void setCustomerNumber(String customerNumber) {
		this.customerNumber = customerNumber;
	}

	public String getCustomerName() {
		return customerName != null ? customerName.trim() : customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getEquipmentOwner() {
		return equipmentOwner != null ? equipmentOwner.trim() : equipmentOwner;
	}

	public void setEquipmentOwner(String equipmentOwner) {
		this.equipmentOwner = equipmentOwner;
	}

	public String getCredit() {
		return credit != null ? credit.trim() : credit;
	}

	public void setCredit(String credit) {
		this.credit = credit;
	}

	public String getActivityStatus() {
		return activityStatus != null ? activityStatus.trim() : activityStatus;
	}

	public void setActivityStatus(String activityStatus) {
		this.activityStatus = activityStatus;
	}

	public String getAddress1() {
		return address1 != null ? address1.trim() : address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2 != null ? address2.trim() : address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getCity() {
		return city != null ? city.trim() : city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state != null ? state.trim() : state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZipcode() {
		return zipcode != null ? zipcode.trim() : zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public CustomerCountry getCountry() {
		return country;
	}

	public void setCountry(CustomerCountry country) {
		this.country = country;
	}

	public String getPrimeContact() {
		return primeContact != null ? primeContact.trim() : primeContact;
	}

	public void setPrimeContact(String primeContact) {
		this.primeContact = primeContact;
	}

	public Integer getFaxAreaCode() {
		return faxAreaCode;
	}

	public void setFaxAreaCode(Integer faxAreaCode) {
		this.faxAreaCode = faxAreaCode;
	}

	public Integer getFaxExchange() {
		return faxExchange;
	}

	public void setFaxExchange(Integer faxExchange) {
		this.faxExchange = faxExchange;
	}

	public Integer getFaxNumber() {
		return faxNumber;
	}

	public void setFaxNumber(Integer faxNumber) {
		this.faxNumber = faxNumber;
	}

	public Integer getCustomerAreaCode() {
		return customerAreaCode;
	}

	public void setCustomerAreaCode(Integer customerAreaCode) {
		this.customerAreaCode = customerAreaCode;
	}

	public Integer getCustomerExchangeNumber() {
		return customerExchangeNumber;
	}

	public void setCustomerExchangeNumber(Integer customerExchangeNumber) {
		this.customerExchangeNumber = customerExchangeNumber;
	}

	public Integer getCustomerExtension() {
		return customerExtension;
	}

	public void setCustomerExtension(Integer customerExtension) {
		this.customerExtension = customerExtension;
	}

	public String getCustomerBase() {
		return customerBase;
	}

	public void setCustomerBase(String customerBase) {
		this.customerBase = customerBase;
	}

	public Integer getTerritory() {
		return territory;
	}

	public void setTerritory(Integer territory) {
		this.territory = territory;
	}

	public Integer getRegion() {
		return region;
	}

	public void setRegion(Integer region) {
		this.region = region;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getCifNumber() {
		return cifNumber != null ? cifNumber.trim() : cifNumber;
	}

	public void setCifNumber(String cifNumber) {
		this.cifNumber = cifNumber;
	}

	public String getAccountDescription() {
		return accountDescription != null ? accountDescription.trim() : accountDescription;
	}

	public void setAccountDescription(String accountDescription) {
		this.accountDescription = accountDescription;
	}

	public String getGoodSpellingName() {
		return goodSpellingName != null ? goodSpellingName.trim() : goodSpellingName;
	}

	public void setGoodSpellingName(String goodSpellingName) {
		this.goodSpellingName = goodSpellingName;
	}

	public String getShipmentPriority() {
		return shipmentPriority != null ? shipmentPriority.trim() : shipmentPriority;
	}

	public void setShipmentPriority(String shipmentPriority) {
		this.shipmentPriority = shipmentPriority;
	}

	public String getIntermodalIdentification() {
		return intermodalIdentification != null ? intermodalIdentification.trim() : intermodalIdentification;
	}

	public void setIntermodalIdentification(String intermodalIdentification) {
		this.intermodalIdentification = intermodalIdentification;
	}

	public String getSalesmanCode() {
		return salesmanCode != null ? salesmanCode.trim() : salesmanCode;
	}

	public void setSalesmanCode(String salesmanCode) {
		this.salesmanCode = salesmanCode;
	}

	public Date getExpiredDate() {
		return expiredDate;
	}

	public void setExpiredDate(Date expiredDate) {
		this.expiredDate = expiredDate;
	}

	public String getNsAccoutDescription() {
		return nsAccoutDescription != null ? nsAccoutDescription.trim() : nsAccoutDescription;
	}

	public void setNsAccoutDescription(String nsAccoutDescription) {
		this.nsAccoutDescription = nsAccoutDescription;
	}

	public String getTeamAudCd() {
		return teamAudCd != null ? teamAudCd.trim() : teamAudCd;
	}

	public void setTeamAudCd(String teamAudCd) {
		this.teamAudCd = teamAudCd;
	}

	public Long getBillToCustomerId() {
		return billToCustomerId;
	}

	public void setBillToCustomerId(Long billToCustomerId) {
		this.billToCustomerId = billToCustomerId;
	}

	public String getBillToCustomerNumber() {
		return billToCustomerNumber != null ? billToCustomerNumber.trim() : billToCustomerNumber;
	}

	public void setBillToCustomerNumber(String billToCustomerNumber) {
		this.billToCustomerNumber = billToCustomerNumber;
	}

	public String getBillToCustomerName() {
		return billToCustomerName != null ? billToCustomerName.trim() : billToCustomerName;
	}

	public void setBillToCustomerName(String billTocustomerName) {
		this.billToCustomerName = billTocustomerName;
	}
	
	public String getScac() {
		return scac;
	}

	public void setScac(String scac) {
		this.scac = scac;
	}

	@Override
	public String toString() {
		return "CustomerInfoDTO [customerId=" + customerId + ", customerNumber=" + customerNumber + ", customerName="
				+ customerName + ", equipmentOwner=" + equipmentOwner + ", credit=" + credit + ", activityStatus="
				+ activityStatus + ", address1=" + address1 + ", address2=" + address2 + ", city=" + city + ", state="
				+ state + ", zipcode=" + zipcode + ", country=" + country + ", primeContact=" + primeContact
				+ ", faxAreaCode=" + faxAreaCode + ", faxExchange=" + faxExchange + ", faxNumber=" + faxNumber
				+ ", customerAreaCode=" + customerAreaCode + ", customerExchangeNumber=" + customerExchangeNumber
				+ ", customerExtension=" + customerExtension + ", customerBase=" + customerBase + ", territory="
				+ territory + ", region=" + region + ", district=" + district + ", cifNumber=" + cifNumber
				+ ", accountDescription=" + accountDescription + ", goodSpellingName=" + goodSpellingName
				+ ", shipmentPriority=" + shipmentPriority + ", intermodalIdentification=" + intermodalIdentification
				+ ", salesmanCode=" + salesmanCode + ", expiredDate=" + expiredDate + ", nsAccoutDescription="
				+ nsAccoutDescription + ", teamAudCd=" + teamAudCd + ", billToCustomerId=" + billToCustomerId
				+ ", billToCustomerNumber=" + billToCustomerNumber + ", billTocustomerName=" + billToCustomerName
				+ ", getUversion()=" + getUversion() + ", getCreateUserId()=" + getCreateUserId()
				+ ", getCreateDateTime()=" + getCreateDateTime() + ", getUpdateUserId()=" + getUpdateUserId()
				+ ", getUpdateDateTime()=" + getUpdateDateTime() + ", getUpdateExtensionSchema()="
				+ getUpdateExtensionSchema() + "]";
	}
	
	

}
