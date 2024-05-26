package com.nscorp.obis.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "DRAYAGE_SCAC")
public class DrayageScac extends AuditInfo implements Serializable {
	@Id
	@Column(name = "DRAY_ID", columnDefinition = "char(4)", nullable = false)
	private String drayId;

	@Column(name = "CARRIER_NM", columnDefinition = "char(99)", nullable = true)
	private String carrierName;

	@Column(name = "MCN_ICC_NR", columnDefinition = "char(6)", nullable = true)
	private String mcdIccNr;

	@Column(name = "CARRIER_ADDR", columnDefinition = "char(50)", nullable = true)
	private String carrierAddress;

	@Column(name = "CARRIER_CITY", columnDefinition = "char(40)", nullable = true)
	private String carrierCity;

	@Column(name = "STATE", columnDefinition = "char(2)", nullable = true)
	private String state;

	@Column(name = "CNTRY", columnDefinition = "char(20)", nullable = true)
	private String country;

	@Column(name = "ZIP_CD", columnDefinition = "char(10)", nullable = true)
	private String zipCode;

	@Column(name = "PHONE_NR", columnDefinition = "char(10)", nullable = true)
	private String phoneNumber;

	@Column(name = "LAST_UPD_DT", columnDefinition = "Date", nullable = true)
	private Date lastUpdDate;

	public String getDrayId() {
		if (drayId != null) {
			return drayId.trim();
		}
		return drayId;
	}

	public void setDrayId(String drayId) {
		this.drayId = drayId;
	}

	public String getCarrierName() {
		if (carrierName != null) {
			return carrierName.trim();
		}
		return carrierName;
	}

	public void setCarrierName(String carrierName) {
		this.carrierName = carrierName;
	}

	public String getMcdIccNr() {
		if (mcdIccNr != null) {
			return mcdIccNr.trim();
		}
		return mcdIccNr;
	}

	public void setMcdIccNr(String mcdIccNr) {
		this.mcdIccNr = mcdIccNr;
	}

	public String getCarrierAddress() {
		if (carrierAddress != null) {
			return carrierAddress.trim();
		}
		return carrierAddress;
	}

	public void setCarrierAddress(String carrierAddress) {
		this.carrierAddress = carrierAddress;
	}

	public String getCarrierCity() {
		if (carrierCity != null) {
			return carrierCity.trim();
		}
		return carrierCity;
	}

	public void setCarrierCity(String carrierCity) {
		this.carrierCity = carrierCity;
	}

	public String getCountry() {
		if (country != null) {
			return country.trim();
		}
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getZipCode() {
		if (zipCode != null) {
			return zipCode.trim();
		}
		return zipCode;
	}

	public void setZipCode(String zipCd) {
		this.zipCode = zipCd;
	}

	public String getPhoneNumber() {
		if (phoneNumber != null) {
			return phoneNumber.trim();
		}
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

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public DrayageScac() {
		super();
	}
	
	

	@Override
	public String toString() {
		return "DrayageSCAC [drayId=" + drayId + ", carrierName=" + carrierName + ", mcdIccNr=" + mcdIccNr
				+ ", carrierAddr=" + carrierAddress + ", carrierCity=" + carrierCity + ", state=" + state + ", country="
				+ country + ", zipCd=" + zipCode + ", phoneNr=" + phoneNumber + ", lastUpdDate=" + lastUpdDate
				+ ", getUversion()=" + getUversion() + ", getCreateUserId()=" + getCreateUserId()
				+ ", getCreateDateTime()=" + getCreateDateTime() + ", getUpdateUserId()=" + getUpdateUserId()
				+ ", getUpdateDateTime()=" + getUpdateDateTime() + ", getUpdateExtensionSchema()="
				+ getUpdateExtensionSchema() + "]";
	}

}
