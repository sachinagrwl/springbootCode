package com.nscorp.obis.domain;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "CASH_EXCP ")
public class CashException extends AuditInfo {
	@Id
	@Column(name = "CASH_EXCP_ID", columnDefinition = "Double(15)", nullable = false)
	private Long cashExceptionId;

	@Column(name = "EQ_INIT", columnDefinition = "char(4)", nullable = false)
	private String equipInit;

	@Column(name = "EQ_NR", columnDefinition = "decimal", nullable = false)
	private Integer equipNbr;

	@Column(name = "EQ_TP", columnDefinition = "char(1)", nullable = false)
	private String equipType;

	@Column(name = "EQ_ID", columnDefinition = "char(4)", nullable = false)
	private String equipId;

	@Column(name = "TERM_ID", columnDefinition = "double(15)", nullable = false)
	private Long termId;

	@Column(name = "EFF_DT", columnDefinition = "Date", nullable = false)
	private LocalDate effectiveDate;

	@Column(name = "END_DATE", columnDefinition = "Date", nullable = false)
	private LocalDate endDate;

	@Column(name = "BNF_PRIM_SIX", columnDefinition = "char(6)", nullable = false)
	private String bnfPrimarySix;

	@Column(name = "BNF_CUST_NR", columnDefinition = "char(10)", nullable = false)
	private String bnfCustomerNumber;

	@Column(name = "CUST_PRIM_SIX", columnDefinition = "char(6)", nullable = false)
	private String customerPrimarySix;

	@Column(name = "CUST_NM", columnDefinition = "char(35)", nullable = true)
	private String customerName;

	@Column(name = "LD_EMPTY_CD", columnDefinition = "char(1)", nullable = false)
	private String loadedOrEmpty;

	public Long getCashExceptionId() {
		return cashExceptionId;
	}

	public void setCashExceptionId(Long cashExceptionId) {
		this.cashExceptionId = cashExceptionId;
	}

	public String getEquipInit() {
		return equipInit != null ? equipInit.trim() : equipInit;
	}

	public void setEquipInit(String equipInit) {
		this.equipInit = equipInit;
	}

	public Integer getEquipNbr() {
		return equipNbr;
	}

	public void setEquipNbr(Integer equipNbr) {
		this.equipNbr = equipNbr;
	}

	public String getEquipType() {
		return equipType != null ? equipType.trim() : equipType;
	}

	public void setEquipType(String equipType) {
		this.equipType = equipType;
	}

	public String getEquipId() {
		return equipId != null ? equipId.trim() : equipId;
	}

	public void setEquipId(String equipId) {
		this.equipId = equipId;
	}

	public Long getTermId() {
		return termId;
	}

	public void setTermId(Long termId) {
		this.termId = termId;
	}

	public LocalDate getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(LocalDate effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public String getBnfPrimarySix() {
		return bnfPrimarySix != null ? bnfPrimarySix.trim() : bnfPrimarySix;
	}

	public void setBnfPrimarySix(String bnfPrimarySix) {
		this.bnfPrimarySix = bnfPrimarySix;
	}

	public String getCustomerPrimarySix() {
		return customerPrimarySix != null ? customerPrimarySix.trim() : customerPrimarySix;
	}

	public void setCustomerPrimarySix(String customerPrimarySix) {
		this.customerPrimarySix = customerPrimarySix;
	}

	public String getCustomerName() {
		return customerName != null ? customerName.trim() : customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getLoadedOrEmpty() {
		return loadedOrEmpty != null ? loadedOrEmpty.trim() : loadedOrEmpty;
	}

	public void setLoadedOrEmpty(String loadedOrEmpty) {
		this.loadedOrEmpty = loadedOrEmpty;
	}

	public String getBnfCustomerNumber() {
		return bnfCustomerNumber != null ? bnfCustomerNumber.trim() : bnfCustomerNumber;
	}

	public void setBnfCustomerNumber(String bnfCustomerNumber) {
		this.bnfCustomerNumber = bnfCustomerNumber;
	}

	public CashException() {
		super();
	}

	@Override
	public String toString() {
		return "CashException [cashExceptionId=" + cashExceptionId + ", equipInit=" + equipInit + ", equipNbr="
				+ equipNbr + ", equipType=" + equipType + ", equipId=" + equipId + ", termId=" + termId
				+ ", effectiveDate=" + effectiveDate + ", endDate=" + endDate + ", bnfPrimarySix=" + bnfPrimarySix
				+ ", bnfCustomerNumber=" + bnfCustomerNumber + ", customerPrimarySix=" + customerPrimarySix
				+ ", customerName=" + customerName + ", loadedOrEmpty=" + loadedOrEmpty + ", getUversion()="
				+ getUversion() + ", getCreateUserId()=" + getCreateUserId() + ", getCreateDateTime()="
				+ getCreateDateTime() + ", getUpdateUserId()=" + getUpdateUserId() + ", getUpdateDateTime()="
				+ getUpdateDateTime() + ", getUpdateExtensionSchema()=" + getUpdateExtensionSchema() + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(cashExceptionId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CashException cashException = (CashException) obj;
		return Objects.equals(cashExceptionId, cashException.cashExceptionId);
	}
}
