package com.nscorp.obis.domain;

import java.sql.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "CUSTOMER")
public class CustomerIndex extends AuditInfo{
	@Id
	@Column(name = "CUST_ID", columnDefinition = "Double", nullable = false)
	private Long customerId;
	@Column(name = "CUST_NR", columnDefinition = "char(10)", nullable = true)
	private String customerNumber;
	@Column(name = "CUST_NM", columnDefinition = "char(35)", nullable = true)
	private String customerName;
	@Column(name = "CUST_ACTY_STAT", columnDefinition = "char(1)", nullable = true)
	private String activityStatus;
	@Column(name = "EXPIRED_DT", columnDefinition = "date(10)", nullable = true)
	private Date expiredDate;
	@Column(name = "CORP_CUST_ID", columnDefinition = "Double(15)", nullable = false)
	private Long corporateCustomerId;
	@Column(name = "CUST_CTY", columnDefinition = "char(19)", nullable = true)
	private String city;
	@Column(name = "CUST_ST_PV", columnDefinition = "char(2)", nullable = true)
	private String state;

	public CustomerIndex() {
		super();
	}

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

	public String getActivityStatus() {
		return activityStatus != null ? activityStatus.trim() : activityStatus;
	}

	public void setActivityStatus(String activityStatus) {
		this.activityStatus = activityStatus;
	}

	public Date getExpiredDate() {
		return expiredDate;
	}

	public void setExpiredDate(Date expiredDate) {
		this.expiredDate = expiredDate;
	}

	public Long getCorporateCustomerId() {
		return corporateCustomerId;
	}

	public void setCorporateCustomerId(Long corporateCustomerId) {
		this.corporateCustomerId = corporateCustomerId;
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

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	
//    public CustomerIndex(String customerNumber, String customerName, String city, String state) {
//		super();
//		this.customerNumber = customerNumber;
//		this.customerName = customerName;
//		this.city = city;
//		this.state = state;
//	}
//
//	@Override
//    public boolean equals(Object obj) {
//    	if(obj == null)return false;
//    	if(!(obj instanceof CustomerIndex)) return false;
//    	CustomerIndex other = (CustomerIndex) obj;
//    	return Objects.equals(this.customerName, other.customerName)&&
//    			Objects.equals(this.customerNumber, other.customerNumber) &&
//    			Objects.equals(this.city, other.city)&&
//    			Objects.equals(this.state, other.state);
//    }
//    @Override
//    public int hashCode() {
//    	return Objects.hash(this.customerName, this.customerNumber,this.city, this.state);
//    }
	@Override
	public String toString() {
		return "CustomerIndex [customerId=" + customerId + ", customerNumber=" + customerNumber + ", customerName="
				+ customerName + ", activityStatus=" + activityStatus + ", expiredDate=" + expiredDate
				+ ", corporateCustomerId=" + corporateCustomerId + ", city=" + city + ", state=" + state + "]";
	}
}
