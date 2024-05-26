package com.nscorp.obis.dto;

import javax.validation.constraints.*;

import io.swagger.v3.oas.annotations.media.Schema;
import org.hibernate.validator.constraints.Range;

public class DamageComponentSizeDTO extends AuditInfoDTO {

    @Digits(integer = 5, fraction = 0, message = "jobCode shouldn't have more than 5 digits")
    @NotNull(message = "jobCode can't be null")
	@Schema(required = true, description = "This gives the code and description of the damaged equipment part.")
	private Integer jobCode;
	
	@Digits(integer = 5, fraction = 0, message = "aarWhyMadeCode shouldn't have more than 5 digits") 
	@NotNull(message = "aarWhyMadeCode can't be null")
	@Schema(required = true, description = "This gives the code and description of the specific damage to the equipment part.")
	private Integer aarWhyMadeCode;

//	@NotNull(message = "componentSizeCode can't be null")
	@Schema(required = true, description = "Component Size Code", example = "3.000768653273E12")
	private Long componentSizeCode;

	@Pattern(regexp="^$|(A|B|C|D|E|)$",message="Only A, B, C, D, E & Null is allowed")
	@Size(max = 1, message = "Order Code length must be equals to 1")
	@Schema(required = false, description = "This indicates the alphabetic order in which the records are displayed.")
	private String orderCode;

	@Digits(integer=2, fraction=0, message="Damage Size should not be more than 2 digits")
	@Range(min = 0, max = 99)
    @Schema(required = false, description = "Damage Size Numeric field (0-99)")
	private Integer damageSize;

	@Pattern(regexp="^$|(IN|FT|)$",message="Only IN, FT & Null is allowed")
	@Size(max = 2, message = "Size Display Tp length must be equals to 2")
    @Schema(required = false, description = "Size Display Tp - â€œINâ€�, â€œFTâ€� ")
	private String sizeDisplayTp;

	@Pattern(regexp="^$|(<|>|)$",message="Only <, > & Null is allowed")
    @Schema(required = false, description = "Size Display Sign - <, > ")
	private String sizeDisplaySign;

    @Schema(required = false, description = "This indicates display code.")
	private String displayCode;

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

	public Long getComponentSizeCode() {
		return componentSizeCode;
	}

	public void setComponentSizeCode(Long componentSizeCode) {
		this.componentSizeCode = componentSizeCode;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public Integer getDamageSize() {
		return damageSize;
	}

	public void setDamageSize(Integer damageSize) {
		this.damageSize = damageSize;
	}

	public String getSizeDisplayTp() {
		return sizeDisplayTp;
	}

	public void setSizeDisplayTp(String sizeDisplayTp) {
		this.sizeDisplayTp = sizeDisplayTp;
	}

	public String getSizeDisplaySign() {
		return sizeDisplaySign;
	}

	public void setSizeDisplaySign(String sizeDisplaySign) {
		this.sizeDisplaySign = sizeDisplaySign;
	}

	public String getDisplayCode() {
		return displayCode;
	}

	public void setDisplayCode(String displayCode) {
		this.displayCode = displayCode;
	}

	@Override
	public String toString() {
		return "DamageComponentSizeDTO [jobCode=" + jobCode + ", aarWhyMadeCode=" + aarWhyMadeCode
				+ ", componentSizeCode=" + componentSizeCode + ", orderCode=" + orderCode + ", damageSize=" + damageSize
				+ ", sizeDisplayTp=" + sizeDisplayTp + ", sizeDisplaySign=" + sizeDisplaySign + ", displayCode="
				+ displayCode + "]";
	}

    

}
