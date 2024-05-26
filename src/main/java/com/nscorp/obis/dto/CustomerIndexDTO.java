package com.nscorp.obis.dto;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

import io.swagger.v3.oas.annotations.media.Schema;

public class CustomerIndexDTO {
	@Schema(required = true, description = "The Customer Id being used.", example = "3.000768653273E12")
	private long customerId;

	@Schema(required = false, description = "The field designates the customer number.")
	private String customerNumber;

	@Schema(required = false, description = "The name of the customer to be notified.")
	private String customerName;

	@Schema(required = false, description = "This field indicates whether the customer is active or inactive")
	private String activityStatus;

	@Schema(required = false, description = "expired date", example = "12/21/2022")
	@JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd")
	private Date expiredDate;

	@Schema(required = true, description = "The Corporate Customer Id being used.", example = "1.639857332557E12")
	private Long corporateCustomerId;
	
	@Schema(required = false, description = "This is the city location for the customer")
	private String city;
	
	@Schema(required = false, description = "State or province abbreviation of customers location")
	private String state;

	public CustomerIndexDTO() {
		super();
	}

	public long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}

	public String getCustomerNumber() {
		return customerNumber;
	}

	public void setCustomerNumber(String customerNumber) {
		this.customerNumber = customerNumber;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getActivityStatus() {
		return activityStatus;
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
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
    
	public CustomerIndexDTO(String customerName, String customerNumber, String city, String state) {
		super();
		this.customerName = customerName;
		this.customerNumber = customerNumber;
		this.city = city;
		this.state = state;
	}

	@Override
	public String toString() {
		return "CustomerIndexDTO [customerId=" + customerId + ", customerNumber=" + customerNumber + ", customerName="
				+ customerName + ", activityStatus=" + activityStatus + ", expiredDate=" + expiredDate
				+ ", corporateCustomerId=" + corporateCustomerId + ", city=" + city + ", state=" + state + "]";
	}
}
