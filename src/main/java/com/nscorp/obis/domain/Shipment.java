package com.nscorp.obis.domain;

import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "SHIPMENT")
public class Shipment extends AuditInfo {

	@Id
	@Column(name = "SVC_ID", length = 15, columnDefinition = "double", nullable = false)
	private Long svcId;

	@OneToOne(targetEntity = Customer.class, orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "SHIP_CUST", referencedColumnName = "CUST_ID", insertable = false, updatable = false)
	private Customer customer;

	@OneToOne(targetEntity = Station.class, orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "ONL_DEST", referencedColumnName = "TERM_ID", insertable = false, updatable = false)
	private Station station;

	@Column(name = "REQ_DLVY_ETA", columnDefinition = "TimeStamp")
	private Timestamp reqDlvyEta;

	@Column(name = "SHIP_STAT", columnDefinition = "char(1)", nullable = false)
	private String shipStat;

	@Column(name = "WB_SER_NR", columnDefinition = "decimal(19)", nullable = false)
	private BigDecimal wbSerNr;

	public BigDecimal getWbSerNr() {
		return wbSerNr;
	}

	public void setWbSerNr(BigDecimal wbSerNr) {
		this.wbSerNr = wbSerNr;
	}

	public String getShipStat() {
		return shipStat;
	}

	public void setShipStat(String shipStat) {
		this.shipStat = shipStat;
	}

	public Timestamp getReqDlvyEta() {
		return reqDlvyEta;
	}

	public void setReqDlvyEta(Timestamp reqDlvyEta) {
		this.reqDlvyEta = reqDlvyEta;
	}

	public Station getStation() {
		return station;
	}

	public void setStation(Station station) {
		this.station = station;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	@Column(name = "LOAD_EMPTY_CD", columnDefinition = "char(1)", nullable = false)
	private String loadEmptyInd;

	@Column(name = "ONL_DEST", length = 15, columnDefinition = "double", nullable = true)
	private Long onlDest;

	@Column(name = "OFFL_DEST", length = 15, columnDefinition = "double", nullable = true)
	private Long offlDest;

	@Column(name = "NTFY_OVRD_NM", columnDefinition = "char(35)", nullable = true)
	private String ntfyOvrdNm;

	@Column(name = "NTFY_OVRD_AREA_CD", columnDefinition = "Smallint", nullable = true)
	private Integer ntfyOvrdAreaCd;

	@Column(name = "NTFY_OVRD_EXCH", columnDefinition = "Smallint", nullable = true)
	private Integer ntfyOvrdExch;

	@Column(name = "NTFY_OVRD_BASE", columnDefinition = "Smallint", nullable = true)
	private Integer ntfyOvrdBase;

	@Column(name = "NTFY_OVRD_EXT", columnDefinition = "Smallint", nullable = true)
	private Integer ntfyOvrdExt;

	@Column(name = "REPO_IND", columnDefinition = "char(1)", nullable = true)
	private String repoInd;

	@Column(name = "CUST_NOTIFY", length = 15, columnDefinition = "double", nullable = true)
	private Long customerToNotify;

	public Long getCustomerToNotify() {
		return customerToNotify;
	}

	public void setCustomerToNotify(Long customerToNotify) {
		this.customerToNotify = customerToNotify;
	}

	public Long getSvcId() {
		return svcId;
	}

	public void setSvcId(Long svcId) {
		this.svcId = svcId;
	}

	public String getLoadEmptyInd() {
		return loadEmptyInd;
	}

	public void setLoadEmptyInd(String loadEmptyInd) {
		this.loadEmptyInd = loadEmptyInd;
	}

	public Long getOnlDest() {
		return onlDest;
	}

	public void setOnlDest(Long onlDest) {
		this.onlDest = onlDest;
	}

	public String getNtfyOvrdNm() {
		return ntfyOvrdNm;
	}

	public void setNtfyOvrdNm(String ntfyOvrdNm) {
		this.ntfyOvrdNm = ntfyOvrdNm;
	}

	public Integer getNtfyOvrdAreaCd() {
		return ntfyOvrdAreaCd;
	}

	public void setNtfyOvrdAreaCd(Integer ntfyOvrdAreaCd) {
		this.ntfyOvrdAreaCd = ntfyOvrdAreaCd;
	}

	public Integer getNtfyOvrdExch() {
		return ntfyOvrdExch;
	}

	public Long getOfflDest() {
		return offlDest;
	}

	public void setOfflDest(Long offlDest) {
		this.offlDest = offlDest;
	}

	public void setNtfyOvrdExch(Integer ntfyOvrdExch) {
		this.ntfyOvrdExch = ntfyOvrdExch;
	}

	public Integer getNtfyOvrdBase() {
		return ntfyOvrdBase;
	}

	public void setNtfyOvrdBase(Integer ntfyOvrdBase) {
		this.ntfyOvrdBase = ntfyOvrdBase;
	}

	public Integer getNtfyOvrdExt() {
		return ntfyOvrdExt;
	}

	public void setNtfyOvrdExt(Integer ntfyOvrdExt) {
		this.ntfyOvrdExt = ntfyOvrdExt;
	}

	public String getRepoInd() {
		return repoInd;
	}

	public void setRepoInd(String repoInd) {
		this.repoInd = repoInd;
	}

	public Shipment() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Shipment(String uversion, String createUserId, Timestamp createDateTime, String updateUserId,
			Timestamp updateDateTime, String updateExtensionSchema, Long svcId, Customer customer, Station station,
			Timestamp reqDlvyEta, String shipStat, String loadEmptyInd, Long onlDest, Long offlDest, String ntfyOvrdNm,
			Integer ntfyOvrdAreaCd, Integer ntfyOvrdExch, Integer ntfyOvrdBase, Integer ntfyOvrdExt, String repoInd,
					BigDecimal wbSerNr) {
		super(uversion, createUserId, createDateTime, updateUserId, updateDateTime, updateExtensionSchema);
		this.svcId = svcId;
		this.customer = customer;
		this.station = station;
		this.reqDlvyEta = reqDlvyEta;
		this.shipStat = shipStat;
		this.loadEmptyInd = loadEmptyInd;
		this.onlDest = onlDest;
		this.offlDest = offlDest;
		this.ntfyOvrdNm = ntfyOvrdNm;
		this.ntfyOvrdAreaCd = ntfyOvrdAreaCd;
		this.ntfyOvrdExch = ntfyOvrdExch;
		this.ntfyOvrdBase = ntfyOvrdBase;
		this.ntfyOvrdExt = ntfyOvrdExt;
		this.repoInd = repoInd;
		this.wbSerNr = wbSerNr;
	}

}
