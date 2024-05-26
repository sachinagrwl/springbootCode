package com.nscorp.obis.domain;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.NoArgsConstructor;

@Entity(name = "customer")
@Table(name = "CUSTOMER")
@NoArgsConstructor
public class CustomerInfo extends AuditInfo {
	@Id
	@Column(name = "CUST_ID", columnDefinition = "Double", nullable = false)
	private long customerId;
	@Column(name = "CUST_NR", columnDefinition = "char(10)", nullable = true)
	private String customerNumber;
	@Column(name = "CUST_NM", columnDefinition = "char(35)", nullable = true)
	private String customerName;
	@Column(name = "OWNR_IND", columnDefinition = "char(1)", nullable = true)
	private String equipmentOwner;
	@Column(name = "CR_VALID_IND", columnDefinition = "char(1)", nullable = true)
	private String credit;
	@Column(name = "CUST_ACTY_STAT", columnDefinition = "char(1)", nullable = true)
	private String activityStatus;
	@Column(name = "CUST_ADDR_1", columnDefinition = "char(35)", nullable = true)
	private String address1;
	@Column(name = "CUST_ADDR_2", columnDefinition = "char(35)", nullable = true)
	private String address2;
	@Column(name = "CUST_CTY", columnDefinition = "char(19)", nullable = true)
	private String city;
	@Column(name = "CUST_ST_PV", columnDefinition = "char(2)", nullable = true)
	private String state;
	@Column(name = "CUST_ZIP_CD", columnDefinition = "char(10)", nullable = true)
	private String zipcode;
	@Column(name = "CUST_CNTRY", columnDefinition = "char(3)", nullable = true)
	private CustomerCountry country;
	@Column(name = "PRIME_CONTACT", columnDefinition = "char(30)", nullable = true)
	private String primeContact;
	@Column(name = "FAX_AREA_CD", columnDefinition = "smallint(5)", nullable = true)
	private Integer faxAreaCode;
	@Column(name = "FAX_EXCH", columnDefinition = "smallint(5)", nullable = true)
	private Integer faxExchange;
	@Column(name = "FAX_EXT", columnDefinition = "smallint(5)", nullable = true)
	private Integer faxNumber;
	@Column(name = "CUST_AREA_CD", columnDefinition = "smallint(5)", nullable = true)
	private Integer customerAreaCode;
	@Column(name = "CUST_EXCH", columnDefinition = "smallint(5)", nullable = true)
	private Integer customerExchangeNumber;
	@Column(name = "CUST_EXT", columnDefinition = "smallint(5)", nullable = true)
	private Integer customerExtension;
	@Column(name = "CUST_BASE", columnDefinition = "char(4)", nullable = true)
	private String customerBase;
	@Column(name = "CUST_IM_TERR", columnDefinition = "smallint(5)", nullable = true)
	private Integer territory;
	@Column(name = "CUST_IM_REG", columnDefinition = "smallint(5)", nullable = true)
	private Integer region;
	@Column(name = "CUST_DISTRICT", columnDefinition = "char(2)", nullable = true)
	private String district;
	@Column(name = "CUST_CIF_NR", columnDefinition = "char(15)", nullable = true)
	private String cifNumber;
	@Column(name = "ACCT_DSC", columnDefinition = "char(30)", nullable = true)
	private String accountDescription;
	@Column(name = "CUST_NM_GS", columnDefinition = "char(10)", nullable = true)
	private String goodSpellingName;
	@Column(name = "CUST_SHIP_PRTY", columnDefinition = "char(1)", nullable = true)
	private String shipmentPriority;
	@Column(name = "INT_IND", columnDefinition = "char(1)", nullable = true)
	private String intermodalIdentification;
	@Column(name = "CUST_SC", columnDefinition = "char(4)", nullable = true)
	private String salesmanCode;
	@Column(name = "EXPIRED_DT", columnDefinition = "date(10)", nullable = true)
	private Date expiredDate;
	@Column(name = "NS_ACCT_DSC", columnDefinition = "char(30)", nullable = true)
	private String nsAccoutDescription;
	@Column(name = "TEAM_AUD_CD", columnDefinition = "char(3)", nullable = true)
	private String teamAudCd;
	@Column(name = "CUST_BILL_TO", columnDefinition = "Double", nullable = true)
	private Long billToCustomerId;
	@Column(name = "CUST_NR_BILL_TO", columnDefinition = "char(10)", nullable = true)
	private String billToCustomerNumber;
	@OneToOne
	@JoinTable(name = "DRAYAGE_CUST", joinColumns = {
			@JoinColumn(name = "CUST_ID", referencedColumnName = "CUST_ID") }, inverseJoinColumns = {
					@JoinColumn(name = "DRAY_ID", referencedColumnName = "DRAY_ID") })
	private DrayageScac drayageScac;

	public long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}

	public String getCustomerNumber() {
		return customerNumber != null ? customerNumber.trim() : customerNumber;
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
		return customerBase != null ? customerBase.trim() : customerBase;
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
		return district != null ? district.trim() : district;
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

	public DrayageScac getDrayageScac() {
		return drayageScac;
	}

	@Override
	public String toString() {
		return "CustomerInfo [customerId=" + customerId + ", customerNumber=" + customerNumber + ", customerName="
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
				+ ", billToCustomerNumber=" + billToCustomerNumber + ", drayageScac=" + drayageScac + ", getUversion()="
				+ getUversion() + ", getCreateUserId()=" + getCreateUserId() + ", getCreateDateTime()="
				+ getCreateDateTime() + ", getUpdateUserId()=" + getUpdateUserId() + ", getUpdateDateTime()="
				+ getUpdateDateTime() + ", getUpdateExtensionSchema()=" + getUpdateExtensionSchema() + "]";
	}

}
