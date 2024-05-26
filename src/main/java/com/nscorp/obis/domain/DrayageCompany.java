package com.nscorp.obis.domain;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;

@SuppressWarnings("serial")
@Entity
@Table(name = "DRAYAGE_COMPANY")
public class DrayageCompany extends AuditInfo implements Serializable {

	@Id
	@Column(name = "DRAY_ID", columnDefinition = "char(4)", nullable = false)
	private String drayageId;

	@Column(name = "TIA_IND", columnDefinition = "char(1)", nullable = false)
	private String tiaInd;

	@Column(name = "BONDED_CARRIER", columnDefinition = "char(1)", nullable = false)
	private String bondedCarrier;

	@Column(name = "BONDED_AUTH_ID", columnDefinition = "char(9)", nullable = false)
	private String bondedAuthId;

	@Column(name = "TIA_SUSPEND_DT", columnDefinition = "Date", nullable = true)
	private Date tiaSuspendDate;

	@Column(name = "EQ_AUTH_IND", columnDefinition = "char(1)", nullable = false)
	private String eqAuthInd;

	public String getDrayageId() {
		if (drayageId != null) {
			return drayageId.trim();
		}
		return drayageId;
	}

	public void setDrayageId(String drayId) {
		this.drayageId = drayId;
	}

	public String getTiaInd() {
		if (tiaInd != null) {
			return tiaInd.trim();
		}
		return tiaInd;
	}

	public void setTiaInd(String tiaInd) {
		this.tiaInd = tiaInd;
	}

	public String getBondedCarrier() {
		if (bondedCarrier != null) {
			return bondedCarrier.trim();
		}
		return bondedCarrier;
	}

	public void setBondedCarrier(String bondedCarrier) {
		this.bondedCarrier = bondedCarrier;
	}

	public String getBondedAuthId() {
		if (bondedAuthId != null) {
			return bondedAuthId.trim();
		}
		return bondedAuthId;
	}

	public void setBondedAuthId(String bondedAuthId) {
		this.bondedAuthId = bondedAuthId;
	}

	public Date getTiaSuspendDate() {
		return tiaSuspendDate;
	}

	public void setTiaSuspendDate(Date tiaSuspendDate) {
		this.tiaSuspendDate = tiaSuspendDate;
	}

	public String getEqAuthInd() {
		if (eqAuthInd != null) {
			return eqAuthInd.trim();
		}
		return eqAuthInd;
	}

	public void setEqAuthInd(String eqAuthInd) {
		this.eqAuthInd = eqAuthInd;
	}

	public DrayageCompany() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DrayageCompany(String drayId, String tiaInd) {
		super();
		this.drayageId = drayId;
		this.tiaInd = tiaInd;
	}

	public DrayageCompany(String drayId, String tiaInd, String bondedCarrier, String customerName, Date tiaSuspendDate, String eqAuthInd) {
		super();
		this.drayageId = drayId;
		this.tiaInd = tiaInd;
		this.bondedCarrier = bondedCarrier;
		this.bondedAuthId = bondedAuthId;
		this.tiaSuspendDate = tiaSuspendDate;
		this.eqAuthInd = eqAuthInd;
	}

	public DrayageCompany(String uversion, String createUserId, Timestamp createDateTime, String updateUserId,
			Timestamp updateDateTime, String updateExtensionSchema) {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "DrayageCompany [drayageId=" + drayageId + ", tiaInd=" + tiaInd + ", bondedCarrier=" + bondedCarrier
				+ ", bondedAuthId=" + bondedAuthId + ", tiaSuspendDate=" + tiaSuspendDate + ", eqAuthInd=" + eqAuthInd
				+ ", getUversion()=" + getUversion() + ", getCreateUserId()=" + getCreateUserId()
				+ ", getCreateDateTime()=" + getCreateDateTime() + ", getUpdateUserId()=" + getUpdateUserId()
				+ ", getUpdateDateTime()=" + getUpdateDateTime() + ", getUpdateExtensionSchema()="
				+ getUpdateExtensionSchema() + "]";
	}
}
