package com.nscorp.obis.dto;

import java.util.Objects;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Range;
import org.springframework.web.bind.annotation.RequestParam;

import com.nscorp.obis.domain.AARWhyMadeCodes;
import com.nscorp.obis.domain.DamageComponent;

import io.swagger.v3.oas.annotations.media.Schema;

public class DamageComponentReasonDTO extends AuditInfoDTO {
	@NotNull(message = "jobCode can't be null")
	@Digits(integer = 5, fraction = 0, message = "jobCode shouldn't have more than 5 digits")
	@Schema(required = true, description = "This gives the code and description of the damaged equipment part.")
	private Integer jobCode;

	@Digits(integer = 5, fraction = 0, message = "aarWhyMadeCode shouldn't have more than 5 digits")
	@NotNull(message = "aarWhyMadeCode can't be null")
	@Schema(required = false, description = "This gives the code and description of the specific damage to the equipment part.")
	private Integer aarWhyMadeCode;

	@Pattern(regexp = "^[A-Z]{1}", message = "orderCode must be in alphabets A-Z or null")
	@Size(min = 1, max = 1, message = "orderCode length must be equal to 1")
	@Schema(required = false, description = "This indicates the alphabetic order in which the records are displayed.")
	private String orderCode;

	@NotNull(message = "maxQuantity can't be null")
	@Range(min = 1, max = 9, message = "maxQuantity must be between 1 to 9.")
	@Digits(integer = 1, fraction = 0, message = "maxQuantity shouldn't have more than 1 digit")
	@Schema(required = false, description = "This gives the maximum quantity for the specific damage.")
	private Integer maxQuantity;

	@NotNull
	@Pattern(regexp = "^[YN]{1}", message = "sizeRequired should be either Y or N !")
	@Size(min = 1, max = 1, message = "sizeRequired length must be equal to 1")
	@Schema(required = false, description = "This indicates whether size dimensions are needed for the damage reason.")
	private String sizeRequired;

	@NotBlank(message = "badOrder can't be null or empty")
	@Pattern(regexp = "^[NAR]{1}", message = "badOrder should be either N or A or R !")
	@Size(min = 1, max = 1, message = "badOrder length must be equal to 1")
	@Schema(required = false, description = "This indicates whether bad order is associated with this specific damage.")
	private String badOrder;

	@Size(min = 1, max = 1, message = "displayCode length must be equal to 1")
	@Schema(required = false, description = "This indicates display code.")
	private String displayCode;

	@Schema(required = false, description = "This indicates Job Code Description.")
	private String jobCodeDescription;

	@Schema(required = false, description = "This indicates Why made Description.")
	private String whyMadeDescription;

	public Integer getJobCode() {
		return jobCode;
	}

	public void setJobCode(Integer jobCode) {
		this.jobCode = jobCode;
	}

	public Integer getAarWhyMadeCode() {
		return aarWhyMadeCode;
	}

	public void setAarWhyMadeCode(Integer aarWhyMadeCode) {
		this.aarWhyMadeCode = aarWhyMadeCode;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public Integer getMaxQuantity() {
		return maxQuantity;
	}

	public void setMaxQuantity(Integer maxQuantity) {
		this.maxQuantity = maxQuantity;
	}

	public String getSizeRequired() {
		return sizeRequired;
	}

	public void setSizeRequired(String sizeRequired) {
		this.sizeRequired = sizeRequired;
	}

	public String getBadOrder() {
		return badOrder;
	}

	public void setBadOrder(String badOrder) {
		this.badOrder = badOrder;
	}

	public String getDisplayCode() {
		return displayCode;
	}

	public void setDisplayCode(String displayCode) {
		this.displayCode = displayCode;
	}

	public String getJobCodeDescription() {
		return jobCodeDescription;
	}

	public void setJobCodeDescription(String jobCodeDescription) {
		this.jobCodeDescription = jobCodeDescription;
	}

	public String getWhyMadeDescription() {
		return whyMadeDescription;
	}

	public void setWhyMadeDescription(String whyMadeDescription) {
		this.whyMadeDescription = whyMadeDescription;
	}

	public void setDamageComponent(DamageComponent damageComponent) {
		if (damageComponent != null) {
			this.setJobCodeDescription(damageComponent.getCompDscr());
		}
	}

	public void setAarWhyMadeCodes(AARWhyMadeCodes aarWhyMadeCodes) {
		if (aarWhyMadeCodes != null) {
			this.setWhyMadeDescription(aarWhyMadeCodes.getAarDesc());
		}
	}

	@Override
	public String toString() {
		return "DamageComponentReasonDTO [jobCode=" + jobCode + ", aarWhyMadeCode=" + aarWhyMadeCode + ", orderCode="
				+ orderCode + ", maxQuantity=" + maxQuantity + ", sizeRequired=" + sizeRequired + ", badOrder="
				+ badOrder + ", displayCode=" + displayCode + ", jobCodeDescription=" + jobCodeDescription
				+ ", whyMadeDescription=" + whyMadeDescription + ", getUversion()=" + getUversion()
				+ ", getCreateUserId()=" + getCreateUserId() + ", getCreateDateTime()=" + getCreateDateTime()
				+ ", getUpdateUserId()=" + getUpdateUserId() + ", getUpdateDateTime()=" + getUpdateDateTime()
				+ ", getUpdateExtensionSchema()=" + getUpdateExtensionSchema() + ", toString()=" + super.toString()
				+ "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(jobCode, aarWhyMadeCode);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DamageComponentReasonDTO other = (DamageComponentReasonDTO) obj;
		return Objects.equals(obj, other);
	}

}
