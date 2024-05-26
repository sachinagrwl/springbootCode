package com.nscorp.obis.dto;

import java.util.Objects;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Range;

import io.swagger.v3.oas.annotations.media.Schema;

public class DamageReasonDTO extends AuditInfoDTO {

	@Digits(integer = 3, fraction = 0, message = "category code shouldn't have more than 3 digits")
	@Range(min = 1, max = 127, message = "category code must be in between 1 and 127")
	@NotNull(message = "Category can't be null")
	@Schema(required = true, description = "damage category code")
	private Integer catCd;

	@Pattern(regexp = "^[a-zA-Z]*$", message = "reason code must contain alphabets only")
	@Size(max = 1, message = "Reason code length must be equal to 1")
	@NotBlank(message = "Reason code can't be null or empty")
	@Schema(required = true, description = "damage reason code")
	private String reasonCd;

	@Size(max = 20, message = "Reason description length must not be greater than 20")
	@NotBlank(message = "Reason description can't be null or empty")
	@Schema(required = true, description = "damage reason description")
	private String reasonDscr;

	@Digits(integer = 2, fraction = 0, message = "print order shouldn't have more than 2 digits")
	@Schema(required = true, description = "print order")
	private Integer prtOrder;

	@Pattern(regexp = "^[YN]{1}", message = "bordInd should be either Y or N")
	@Size(max = 1, message = "bordInd length must be equals to 1")
	@Schema(required = true, description = "BORD Indentification")
	private String bordInd;

	public Integer getCatCd() {
		return catCd;
	}

	public void setCatCd(Integer catCd) {
		this.catCd = catCd;
	}

	public String getReasonCd() {
		return reasonCd != null ? reasonCd.trim().toUpperCase() : reasonCd;
	}

	public void setReasonCd(String reasonCd) {
		this.reasonCd = reasonCd;
	}

	public String getReasonDscr() {
		return reasonDscr != null ? reasonDscr.trim().toUpperCase() : reasonDscr;
	}

	public void setReasonDscr(String reasonDscr) {
		this.reasonDscr = reasonDscr;
	}

	public Integer getPrtOrder() {
		return prtOrder;
	}

	public void setPrtOrder(Integer prtOrder) {
		this.prtOrder = prtOrder;
	}

	public String getBordInd() {
		return bordInd != null ? bordInd.trim().toUpperCase() : bordInd;
	}

	public void setBordInd(String bordInd) {
		this.bordInd = bordInd;
	}

	@Override
	public String toString() {
		return "DamageReasonDTO [catCd=" + catCd + ", reasonCd=" + reasonCd + ", reasonDscr=" + reasonDscr
				+ ", prtOrder=" + prtOrder + ", bordInd=" + bordInd + ", getUversion()=" + getUversion()
				+ ", getCreateUserId()=" + getCreateUserId() + ", getCreateDateTime()=" + getCreateDateTime()
				+ ", getUpdateUserId()=" + getUpdateUserId() + ", getUpdateDateTime()=" + getUpdateDateTime()
				+ ", getUpdateExtensionSchema()=" + getUpdateExtensionSchema() + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(catCd, reasonCd);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DamageReasonDTO other = (DamageReasonDTO) obj;
		return Objects.equals(catCd, other.catCd) && Objects.equals(reasonCd, other.reasonCd);
	}

}
