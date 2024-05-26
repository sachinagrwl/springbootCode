package com.nscorp.obis.domain;

import java.util.Objects;

import javax.persistence.*;

@Entity
@Table(name = "DAMAGE_REASON_CD")
@IdClass(DamageReasonPrimaryKey.class)
public class DamageReason extends AuditInfo {

	@Id
	@Column(name = "CAT_CD", columnDefinition = "SMALLINT(5)", nullable = false)
	private Integer catCd;

	@Id
	@Column(name = "REASON_CD", columnDefinition = "CHAR(1)", nullable = false)
	private String reasonCd;

	@Column(name = "REASON_DSCR", columnDefinition = "CHAR(20)")
	private String reasonDscr;

	@Column(name = "PRT_ORDER", columnDefinition = "SMALLINT(5)")
	private Integer prtOrder;

	@Column(name = "BORD_IND", columnDefinition = "CHAR(1)")
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
		if (reasonDscr != null) {
			return reasonDscr.trim().toUpperCase();
		} else {
			return reasonDscr;
		}
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
		if (bordInd != null) {
			return bordInd.trim().toUpperCase();
		} else {
			return bordInd;
		}
	}

	public void setBordInd(String bordInd) {
		this.bordInd = bordInd;
	}

	@Override
	public String toString() {
		return "damageLocation " + "[ catCd =" + catCd + " reasonCd =" + reasonCd + " reasonDscr =" + reasonDscr
				+ " prtOrder =" + prtOrder + " bordInd =" + bordInd + ", getUversion()=" + getUversion()
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
		DamageReason other = (DamageReason) obj;
		return Objects.equals(catCd, other.catCd) && Objects.equals(reasonCd, other.reasonCd);
	}

}