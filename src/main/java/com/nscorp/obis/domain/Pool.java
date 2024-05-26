package com.nscorp.obis.domain;

import java.sql.Timestamp;
import java.util.Set;

import javax.persistence.*;

import static javax.persistence.CascadeType.*;

@Entity
@Table(name = "POOL")
public class Pool extends AuditInfo {
	@Id
	@Column(name = "POOL_ID", length = 15, columnDefinition = "double", nullable = false)
	private Long poolId;

	@ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	@JoinTable(name = "POOL_TERM", joinColumns = @JoinColumn(name = "POOL_ID"), inverseJoinColumns = @JoinColumn(name = "TERM_ID"))
	Set<Terminal> terminals;

	@OneToMany(orphanRemoval = false, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "POOL_ID", referencedColumnName = "POOL_ID", insertable = false, updatable = false)
	Set<PoolEquipmentController> controllers;

	@OneToMany(orphanRemoval = false, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "POOL_ID", referencedColumnName = "POOL_ID", insertable = false, updatable = false)
	Set<PoolEquipmentRange> equipmentRanges;

	@Column(name = "POOL_NM", columnDefinition = "char(10)", nullable = false)
	private String poolName;
	@Column(name = "POOL_DESC", columnDefinition = "char(30)", nullable = false)
	private String description;

	@Column(name = "POOL_RSRV_TP", columnDefinition = "char(2)")
	private String poolReservationType;

	@OneToOne(orphanRemoval = false, fetch = FetchType.EAGER)
	@JoinColumn(name = "TKR_GRP_CD", referencedColumnName = "TKR_GRP_CD")
	private TruckerGroup truckerGroup;

	@Column(name = "AGREEMENT_RQD", length = 1, columnDefinition = "char")
	private String agreementRequired;

	@Column(name = "CHECK_TKR", length = 1, columnDefinition = "char")
	private String checkTrucker;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "POOL_RSRV_TP", referencedColumnName = "POOL_TP", nullable = true, insertable = false, updatable = false)
	private PoolType poolType;

	@ManyToMany(cascade = { CascadeType.ALL}, fetch = FetchType.LAZY)
	@JoinTable(name = "POOL_CUST", joinColumns = @JoinColumn(name = "POOL_ID"), inverseJoinColumns = @JoinColumn(name = "CUST_ID"))
	Set<Customer> customers;

	public Set<Customer> getCustomers() {
		return customers;
	}

	public void setCustomers(Set<Customer> customers) {
		this.customers = customers;
	}

	public PoolType getPoolType() {
		return poolType;
	}

	public void setPoolType(PoolType poolType) {
		this.poolType = poolType;
	}

	public Long getPoolId() {
		return poolId;
	}

	public void setPoolId(Long poolId) {
		this.poolId = poolId;
	}

	public String getPoolName() {
		if (poolName != null) {
			poolName = poolName.trim();
		}
		return poolName;
	}

	public Set<Terminal> getTerminals() {
		return terminals;
	}

	public void setTerminals(Set<Terminal> terminals) {
		this.terminals = terminals;
	}

	public void setPoolName(String poolName) {
		this.poolName = poolName;
	}

	public String getDescription() {
		if (description != null) {
			description = description.trim();
		}
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPoolReservationType() {
		return poolReservationType;
	}

	public void setPoolReservationType(String poolReservationType) {
		this.poolReservationType = poolReservationType;
	}

	public Set<PoolEquipmentController> getControllers() {
		return controllers;
	}

	public void setControllers(Set<PoolEquipmentController> controllers) {
		this.controllers = controllers;
	}

	public Set<PoolEquipmentRange> getEquipmentRanges() {
		return equipmentRanges;
	}

	public void setEquipmentRanges(Set<PoolEquipmentRange> equipmentRanges) {
		this.equipmentRanges = equipmentRanges;
	}

	public TruckerGroup getTruckerGroup() {
		return truckerGroup;
	}

	public void setTruckerGroup(TruckerGroup truckerGroup) {
		this.truckerGroup = truckerGroup;
	}

	public String getAgreementRequired() {
		return agreementRequired;
	}

	public void setAgreementRequired(String agreementRequired) {
		this.agreementRequired = agreementRequired;
	}

	public String getCheckTrucker() {
		return checkTrucker;
	}

	public void setCheckTrucker(String checkTrucker) {
		this.checkTrucker = checkTrucker;
	}

	public Pool(String uversion, String createUserId, Timestamp createDateTime, String updateUserId,
			Timestamp updateDateTime, String updateExtensionSchema, Long poolId, Set<Terminal> terminals, Set<PoolEquipmentController> controllers, Set<PoolEquipmentRange> equipmentRanges, String poolName, String description,
			String poolReservationType, TruckerGroup truckerGroup, String agreementRequired, String checkTrucker) {
		super(uversion, createUserId, createDateTime, updateUserId, updateDateTime, updateExtensionSchema);
		this.poolId = poolId;
		this.terminals = terminals;
		this.controllers = controllers;
		this.equipmentRanges = equipmentRanges;
		this.poolName = poolName;
		this.description = description;
		this.poolReservationType = poolReservationType;
		this.truckerGroup = truckerGroup;
		this.agreementRequired = agreementRequired;
		this.checkTrucker = checkTrucker;
	}

	public Pool() {
		super();
	}

	public Pool(String uversion, String createUserId, Timestamp createDateTime, String updateUserId,
			Timestamp updateDateTime, String updateExtensionSchema) {
		super(uversion, createUserId, createDateTime, updateUserId, updateDateTime, updateExtensionSchema);
	}

	@Override
	public String toString() {
		return "Pool{" +
				"poolId=" + poolId +
				", terminals=" + terminals +
				", controllers=" + controllers +
				", poolName='" + poolName + '\'' +
				", description='" + description + '\'' +
				", poolReservationType='" + poolReservationType + '\'' +
				", truckerGroup=" + truckerGroup +
				", agreementRequired='" + agreementRequired + '\'' +
				", checkTrucker='" + checkTrucker + '\'' +
				", poolType=" + poolType +
				", customers=" + customers +
				'}';
	}
}
