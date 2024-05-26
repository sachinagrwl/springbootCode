package com.nscorp.obis.dto;

import java.sql.Date;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.nscorp.obis.common.NullOrNotBlank;

import io.swagger.v3.oas.annotations.media.Schema;

public class CustomerScacDTO {
	
	@Schema(required = true, description = "The Customer Id being used.", example = "3.000768653273E12")
	private long customerId;

	@Schema(required = false, description = "The field designates the customer number.")
	private String customerNumber;

	@Schema(required = false, description = "The name of the customer to be notified.")
	private String customerName;
	
//	@Schema(required = false, description = "expired date", example = "12/21/2022")
//	@JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd")
//	private Date expiredDate;
	
	private String drayageId;
	
	
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
		return customerName!=null?customerName.trim():null;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

//	public Date getExpiredDate() {
//		return expiredDate;
//	}
//
//	public void setExpiredDate(Date expiredDate) {
//		this.expiredDate = expiredDate;
//	}

	public void setScac(DrayageScacDTO scac) {
		if(scac!=null) {
			setDrayageId(scac.getDrayId());
		}
	}

	public String getDrayageId() {
		return drayageId!=null?drayageId.trim():null;
	}

	public void setDrayageId(String drayageId) {
		this.drayageId = drayageId;
	}
	
	@Override
	public String toString() {
		return "CustomerScacDTO [customerId=" + customerId + ", customerNumber=" + customerNumber + ", customerName="
				+ customerName + ",  drayageId=" + drayageId + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(customerId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CustomerScacDTO other = (CustomerScacDTO) obj;
		return Objects.equals(customerId, other.customerId);
	}

}
