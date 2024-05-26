package com.nscorp.obis.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@SuppressWarnings("serial")
@Entity
@Table(name = "MONEY_RCVD")
public class MoneyReceived extends AuditInfo implements Serializable {
	
	@Id
	@Column(name = "MONEY_TDR_ID", columnDefinition = "Double(15)", nullable = false)
    private Long moneyTdrId;
	
	@Column(name = "TERM_ID", columnDefinition = "Double(15)", nullable = false)
    private Long termId;
	
	@Column(name = "MONEY_CHK_IND", columnDefinition = "char(1)", nullable = false)
    private String moneyChkInd;
	
	@Column(name = "EQ_INIT", columnDefinition = "char(4)", nullable = false)
    private String equipInit;
	
	@Column(name = "EQ_NR", columnDefinition = "decimal(19)", nullable = false)
    private Integer equipNbr;
	
	@Column(name = "EQ_TP", columnDefinition = "char(1)", nullable = false)
    private String equipType;
	
	@Column(name = "EQ_ID", columnDefinition = "char(4)", nullable = false)
    private String equipId;
	
	@Column(name = "CHRG_ID", columnDefinition = "Double(8)", nullable = false)
    private Long chrgId;
	
	@Column(name = "CUST_ID", columnDefinition = "Double(15)", nullable = true)
    private Long customerId;
	
	@Column(name = "PAID_DT_TM", columnDefinition = "timestamp", nullable = false)
    private Date paidDtTm;
	
	@Column(name = "AMT", columnDefinition = "Decimal(19)", nullable = false)
    private BigDecimal amount;
	
	@Column(name = "TP_PAYMENT", columnDefinition = "char(2)", nullable = false)
    private String tpPayment;
	
	@Column(name = "TP_SVC_CD", columnDefinition = "char(3)", nullable = false)
    private String tpSvcCd;
	
	@Column(name = "PAYMENT_REF_NR", columnDefinition = "char(15)", nullable = false)
    private String paymentRefNr;
	
	@Column(name = "AMT_APPLIED_IND", columnDefinition = "char(1)", nullable = false)
    private String amountAppliedInd;
	
	@Column(name = "TERM_CHK_IND", columnDefinition = "char(1)", nullable = false)
    private String termChkInd;
	
	@Column(name = "PAIDBY_CUST_ID", columnDefinition = "Double(8)", nullable = false)
    private Long paidByCustId;
	
	@Column(name = "PAYEE", columnDefinition = "char(30)", nullable = false)
    private String payee;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="CUST_ID", referencedColumnName = "CUST_ID", insertable = false, updatable = false)
	@JsonIgnoreProperties(value = {"applications", "hibernateLazyInitializer"})
	private Customer customer;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="PAIDBY_CUST_ID", referencedColumnName = "CUST_ID", insertable = false, updatable = false)
	@JsonIgnoreProperties(value = {"applications", "hibernateLazyInitializer"})
	private Customer paidByCustomer;

	@Transient
	private String paidDtTmStr;

	public Customer getPaidByCustomer() {
		return paidByCustomer;
	}

	public void setPaidByCustomer(Customer paidByCustomer) {
		this.paidByCustomer = paidByCustomer;
	}

	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public Long getMoneyTdrId() {
		return moneyTdrId;
	}
	public void setMoneyTdrId(Long moneyTdrId) {
		this.moneyTdrId = moneyTdrId;
	}
	public Long getTermId() {
		return termId;
	}
	public void setTermId(Long termId) {
		this.termId = termId;
	}
	public String getMoneyChkInd() {
		return moneyChkInd;
	}
	public void setMoneyChkInd(String moneyChkInd) {
		this.moneyChkInd = moneyChkInd;
	}
	public String getEquipInit() {
		return equipInit;
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
		return equipType;
	}
	public void setEquipType(String equipType) {
		this.equipType = equipType;
	}
	public String getEquipId() {
		if(equipId!=null) {
			return equipId.trim();
		}
		return equipId;
	}
	public void setEquipId(String equipId) {
		this.equipId = equipId;
	}
	public Long getChrgId() {
		return chrgId;
	}
	public void setChrgId(Long chrgId) {
		this.chrgId = chrgId;
	}
	public Long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	public Date getPaidDtTm() {
		return paidDtTm;
	}
	public void setPaidDtTm(Date paidDtTm) {
		this.paidDtTm = paidDtTm;
		if(paidDtTm!=null)
			this.paidDtTmStr= paidDtTm.toString();
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public String getTpPayment() {
		return tpPayment;
	}
	public void setTpPayment(String tpPayment) {
		this.tpPayment = tpPayment;
	}
	public String getTpSvcCd() {
		return tpSvcCd;
	}
	public void setTpSvcCd(String tpSvcCd) {
		this.tpSvcCd = tpSvcCd;
	}
	public String getPaymentRefNr() {
		if(paymentRefNr!=null) {
			return paymentRefNr.trim();
		}
		return paymentRefNr;
	}
	public void setPaymentRefNr(String paymentRefNr) {
		this.paymentRefNr = paymentRefNr;
	}
	public String getAmountAppliedInd() {
		return amountAppliedInd;
	}
	public void setAmountAppliedInd(String amountAppliedInd) {
		this.amountAppliedInd = amountAppliedInd;
	}
	public String getTermChkInd() {
		return termChkInd;
	}
	public void setTermChkInd(String termChkInd) {
		this.termChkInd = termChkInd;
	}
	public Long getPaidByCustId() {
		return paidByCustId;
	}
	public void setPaidByCustId(Long paidByCustId) {
		this.paidByCustId = paidByCustId;
	}
	public String getPayee() {
		if(payee!=null) {
			return payee.trim();
		}
		return payee;
	}
	public void setPayee(String payee) {
		this.payee = payee;
	}

	public String getPaidDtTmStr() {return paidDtTmStr;}
	public void setPaidDtTmStr(String paidDtTmStr) {
		this.paidDtTmStr = paidDtTmStr;
	}
	public MoneyReceived(String uversion, String createUserId, Timestamp createDateTime, String updateUserId,
			Timestamp updateDateTime, String updateExtensionSchema, Long moneyTdrId, Long termId, String moneyChkInd,
			String equipInit, Integer equipNbr, String equipType, String equipId, Long chrgId, Long customerId,
			Timestamp paidDtTm, BigDecimal amount, String tpPayment, String tpSvcCd, String paymentRefNr,
			String amountAppliedInd, String termChkInd, Long paidByCustId, String payee,Customer customer,String paidDtTmStr) {
		super(uversion, createUserId, createDateTime, updateUserId, updateDateTime, updateExtensionSchema);
		this.moneyTdrId = moneyTdrId;
		this.termId = termId;
		this.moneyChkInd = moneyChkInd;
		this.equipInit = equipInit;
		this.equipNbr = equipNbr;
		this.equipType = equipType;
		this.equipId = equipId;
		this.chrgId = chrgId;
		this.customerId = customerId;
		this.paidDtTm = paidDtTm;
		this.amount = amount;
		this.tpPayment = tpPayment;
		this.tpSvcCd = tpSvcCd;
		this.paymentRefNr = paymentRefNr;
		this.amountAppliedInd = amountAppliedInd;
		this.termChkInd = termChkInd;
		this.paidByCustId = paidByCustId;
		this.payee = payee;
		this.customer=customer;
		this.paidDtTmStr = paidDtTmStr;
	}
	public MoneyReceived() {
		super();
		// TODO Auto-generated constructor stub
	}
	public MoneyReceived(String uversion, String createUserId, Timestamp createDateTime, String updateUserId,
			Timestamp updateDateTime, String updateExtensionSchema) {
		super(uversion, createUserId, createDateTime, updateUserId, updateDateTime, updateExtensionSchema);
		// TODO Auto-generated constructor stub
	}
    
    

}
