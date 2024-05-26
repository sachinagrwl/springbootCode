package com.nscorp.obis.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@SuppressWarnings("serial")
@Entity
@Table(name = "PER_DIEM_RATES")
public class CustomerPerDiemRateSelection extends AuditInfo implements Serializable {
    
    @Id
	@Column(name = "PER_DIEM_ID", columnDefinition = "Double", nullable = false)
	private Long perDiemId;
	@Column(name = "TERM_ID", columnDefinition = "Double", nullable = true)
	private Long terminalId;
	@Column(name = "EQ_INIT", columnDefinition = "char(4)", nullable = true)
	private String equipInit;
	@Column(name = "EQ_NR_LOW", columnDefinition = "decimal(19)", nullable = true)
	private BigDecimal equipNrLow;
	@Column(name = "EQ_NR_HIGH", columnDefinition = "decimal(19)", nullable = true)
	private BigDecimal equipNrHigh;
	@Column(name = "EQ_TP", columnDefinition = "char(1)", nullable = true)
	private String equipTp;
	@Column(name = "OUT_LD_EMPTY_CD", columnDefinition = "char(1)", nullable = true)
	private String outgateLoadEmptyStatus;
	@Column(name = "IN_LD_EMPTY_CD", columnDefinition = "char(1)", nullable = true)
	private String ingateLoadEmptyStatus;
	@Column(name = "SVC_CD", columnDefinition = "char(2)", nullable = true)
	private String svcCd;
	@Column(name = "CNT_WEEKEND", columnDefinition = "char(1)", nullable = true)
	private String countWeekend;
	@Column(name = "RT_TP", columnDefinition = "char(1)", nullable = true)
	private String rateType;
	@Column(name = "FREE_DD_LMT", columnDefinition = "smallint", nullable = true)
	private Integer freeDayLimit;
	@Column(name = "RT1_DD_LMT", columnDefinition = "smallint", nullable = true)
	private Integer rateDayLimit ;
	@Column(name = "RT1_AMT", columnDefinition = "decimal", nullable = true)
	private BigDecimal initialRate;
	@Column(name = "RT2_AMT", columnDefinition = "decimal", nullable = true)
	private BigDecimal secondaryRate;
	@Column(name = "AVG_UNITS_LMT", columnDefinition = "smallint", nullable = true)
	private Integer averageUnitLimit;
	@Column(name = "MM_DD_LMT", columnDefinition = "smallint", nullable = true)
	private Integer mmDayLimit;
	@Column(name = "EFF_DT", columnDefinition = "date", nullable = true)
	private Date effectiveDate ;
	@Column(name = "END_DT", columnDefinition = "date", nullable = true)
	private Date endDate;
	@Column(name = "BNF_PRIM_SIX", columnDefinition = "char(6)", nullable = true)
	private String beneficialPrimSix;
	@Column(name = "CUST_PRIM_SIX", columnDefinition = "char(6)", nullable = true)
	private String custPrimSix;
	@Column(name = "CUST_NM", columnDefinition = "char(35)", nullable = true)
	private String customerName;
	@Column(name = "BNF_CUST_NM", columnDefinition = "char(35)", nullable = true)
	private String beneficialCustomerName;
	@Column(name = "SHIP_PRIM_SIX", columnDefinition = "char(6)", nullable = true)
	private String shipPrimSix;
	@Column(name = "SHIP_CUST_NM", columnDefinition = "char(35)", nullable = true)
	private String shipCustomerName;
	@Column(name = "BILL_CUST_ID", columnDefinition = "double", nullable = true)
	private Long billCustomerId;
	
	public Long getPerDiemId() {
		return perDiemId;
	}
	public void setPerDiemId(Long perDiemId) {
		this.perDiemId = perDiemId;
	}
	public Long getTerminalId() {
		return terminalId;
	}
	public void setTerminalId(Long terminalId) {
		this.terminalId = terminalId;
	}
	public String getEquipInit() {
		return equipInit;
	}
	public void setEquipInit(String equipInit) {
		this.equipInit = equipInit;
	}
	public BigDecimal getEquipNrLow() {
		return equipNrLow;
	}
	public void setEquipNrLow(BigDecimal equipNrLow) {
		this.equipNrLow = equipNrLow;
	}
	public BigDecimal getEquipNrHigh() {
		return equipNrHigh;
	}
	public void setEquipNrHigh(BigDecimal equipNrHigh) {
		this.equipNrHigh = equipNrHigh;
	}
	public String getEquipTp() {
		return equipTp;
	}
	public void setEquipTp(String equipTp) {
		this.equipTp = equipTp;
	}
	public String getOutgateLoadEmptyStatus() {
		return outgateLoadEmptyStatus;
	}
	public void setOutgateLoadEmptyStatus(String outgateLoadEmptyStatus) {
		this.outgateLoadEmptyStatus = outgateLoadEmptyStatus;
	}
	public String getIngateLoadEmptyStatus() {
		return ingateLoadEmptyStatus;
	}
	public void setIngateLoadEmptyStatus(String ingateLoadEmptyStatus) {
		this.ingateLoadEmptyStatus = ingateLoadEmptyStatus;
	}
	public String getSvcCd() {
		return svcCd;
	}
	public void setSvcCd(String svcCd) {
		this.svcCd = svcCd;
	}
	public String getCountWeekend() {
		return countWeekend;
	}
	public void setCountWeekend(String countWeekend) {
		this.countWeekend = countWeekend;
	}
	public String getRateType() {
		return rateType;
	}
	public void setRateType(String rateType) {
		this.rateType = rateType;
	}
	public Integer getFreeDayLimit() {
		return freeDayLimit;
	}
	public void setFreeDayLimit(Integer freeDayLimit) {
		this.freeDayLimit = freeDayLimit;
	}
	public Integer getRateDayLimit() {
		return rateDayLimit;
	}
	public void setRateDayLimit(Integer rateDayLimit) {
		this.rateDayLimit = rateDayLimit;
	}
	public BigDecimal getInitialRate() {
		return initialRate;
	}
	public void setInitialRate(BigDecimal initialRate) {
		this.initialRate = initialRate;
	}
	public BigDecimal getSecondaryRate() {
		return secondaryRate;
	}
	public void setSecondaryRate(BigDecimal secondaryRate) {
		this.secondaryRate = secondaryRate;
	}
	public Integer getAverageUnitLimit() {
		return averageUnitLimit;
	}
	public void setAverageUnitLimit(Integer averageUnitLimit) {
		this.averageUnitLimit = averageUnitLimit;
	}
	public Integer getMmDayLimit() {
		return mmDayLimit;
	}
	public void setMmDayLimit(Integer mmDayLimit) {
		this.mmDayLimit = mmDayLimit;
	}
	public Date getEffectiveDate() {
		return effectiveDate;
	}
	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getBeneficialPrimSix() {
		return beneficialPrimSix;
	}
	public void setBeneficialPrimSix(String beneficialPrimSix) {
		this.beneficialPrimSix = beneficialPrimSix;
	}
	public String getCustPrimSix() {
		return custPrimSix;
	}
	public void setCustPrimSix(String custPrimSix) {
		this.custPrimSix = custPrimSix;
	}
	public String getCustomerName() {
		if(customerName != null)
			return customerName.trim();
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getBeneficialCustomerName() {
		if(beneficialCustomerName != null)
			return beneficialCustomerName.trim();
		return beneficialCustomerName;
	}
	public void setBeneficialCustomerName(String beneficialCustomerName) {
		this.beneficialCustomerName = beneficialCustomerName;
	}
	public String getShipPrimSix() {
		return shipPrimSix;
	}
	public void setShipPrimSix(String shipPrimSix) {
		this.shipPrimSix = shipPrimSix;
	}
	public String getShipCustomerName() {
		if(shipCustomerName != null)
			return shipCustomerName.trim();
		return shipCustomerName;
	}
	public void setShipCustomerName(String shipCustomerName) {
		this.shipCustomerName = shipCustomerName;
	}
	public Long getBillCustomerId() {
		return billCustomerId;
	}
	public void setBillCustomerId(Long billCustomerId) {
		this.billCustomerId = billCustomerId;
	}
	@Override
	public String toString() {
		return "CustomerPerDiemRateSelection [perDiemId=" + perDiemId + ", terminalId=" + terminalId + ", equipInit="
				+ equipInit + ", equipNrLow=" + equipNrLow + ", equipNrHigh=" + equipNrHigh + ", equipTp=" + equipTp
				+ ", outgateLoadEmptyStatus=" + outgateLoadEmptyStatus + ", ingateLoadEmptyStatus="
				+ ingateLoadEmptyStatus + ", svcCd=" + svcCd + ", countWeekend=" + countWeekend + ", rateType="
				+ rateType + ", freeDayLimit=" + freeDayLimit + ", rateDayLimit=" + rateDayLimit + ", initialRate="
				+ initialRate + ", secondaryRate=" + secondaryRate + ", averageUnitLimit=" + averageUnitLimit
				+ ", mmDayLimit=" + mmDayLimit + ", effectiveDate=" + effectiveDate + ", endDate=" + endDate
				+ ", beneficialPrimSix=" + beneficialPrimSix + ", custPrimSix=" + custPrimSix + ", customerName="
				+ customerName + ", beneficialCustomerName=" + beneficialCustomerName + ", shipPrimSix=" + shipPrimSix
				+ ", shipCustomerName=" + shipCustomerName + ", billCustomerId=" + billCustomerId + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((perDiemId == null) ? 0 : perDiemId.hashCode());
		result = prime * result + ((terminalId == null) ? 0 : terminalId.hashCode());
		result = prime * result + ((equipInit == null) ? 0 : equipInit.hashCode());
		result = prime * result + ((equipNrLow == null) ? 0 : equipNrLow.hashCode());
		result = prime * result + ((equipNrHigh == null) ? 0 : equipNrHigh.hashCode());
		result = prime * result + ((equipTp == null) ? 0 : equipTp.hashCode());
		result = prime * result + ((outgateLoadEmptyStatus == null) ? 0 : outgateLoadEmptyStatus.hashCode());
		result = prime * result + ((ingateLoadEmptyStatus == null) ? 0 : ingateLoadEmptyStatus.hashCode());
		result = prime * result + ((svcCd == null) ? 0 : svcCd.hashCode());
		result = prime * result + ((countWeekend == null) ? 0 : countWeekend.hashCode());
		result = prime * result + ((rateType == null) ? 0 : rateType.hashCode());
		result = prime * result + ((freeDayLimit == null) ? 0 : freeDayLimit.hashCode());
		result = prime * result + ((rateDayLimit == null) ? 0 : rateDayLimit.hashCode());
		result = prime * result + ((initialRate == null) ? 0 : initialRate.hashCode());
		result = prime * result + ((secondaryRate == null) ? 0 : secondaryRate.hashCode());
		result = prime * result + ((averageUnitLimit == null) ? 0 : averageUnitLimit.hashCode());
		result = prime * result + ((mmDayLimit == null) ? 0 : mmDayLimit.hashCode());
		result = prime * result + ((effectiveDate == null) ? 0 : effectiveDate.hashCode());
		result = prime * result + ((endDate == null) ? 0 : endDate.hashCode());
		result = prime * result + ((beneficialPrimSix == null) ? 0 : beneficialPrimSix.hashCode());
		result = prime * result + ((custPrimSix == null) ? 0 : custPrimSix.hashCode());
		result = prime * result + ((customerName == null) ? 0 : customerName.hashCode());
		result = prime * result + ((beneficialCustomerName == null) ? 0 : beneficialCustomerName.hashCode());
		result = prime * result + ((shipPrimSix == null) ? 0 : shipPrimSix.hashCode());
		result = prime * result + ((shipCustomerName == null) ? 0 : shipCustomerName.hashCode());
		result = prime * result + ((billCustomerId == null) ? 0 : billCustomerId.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CustomerPerDiemRateSelection other = (CustomerPerDiemRateSelection) obj;
		if (perDiemId == null) {
			if (other.perDiemId != null)
				return false;
		} else if (!perDiemId.equals(other.perDiemId))
			return false;
		if (terminalId == null) {
			if (other.terminalId != null)
				return false;
		} else if (!terminalId.equals(other.terminalId))
			return false;
		if (equipInit == null) {
			if (other.equipInit != null)
				return false;
		} else if (!equipInit.equals(other.equipInit))
			return false;
		if (equipNrLow == null) {
			if (other.equipNrLow != null)
				return false;
		} else if (!equipNrLow.equals(other.equipNrLow))
			return false;
		if (equipNrHigh == null) {
			if (other.equipNrHigh != null)
				return false;
		} else if (!equipNrHigh.equals(other.equipNrHigh))
			return false;
		if (equipTp == null) {
			if (other.equipTp != null)
				return false;
		} else if (!equipTp.equals(other.equipTp))
			return false;
		if (outgateLoadEmptyStatus == null) {
			if (other.outgateLoadEmptyStatus != null)
				return false;
		} else if (!outgateLoadEmptyStatus.equals(other.outgateLoadEmptyStatus))
			return false;
		if (ingateLoadEmptyStatus == null) {
			if (other.ingateLoadEmptyStatus != null)
				return false;
		} else if (!ingateLoadEmptyStatus.equals(other.ingateLoadEmptyStatus))
			return false;
		if (svcCd == null) {
			if (other.svcCd != null)
				return false;
		} else if (!svcCd.equals(other.svcCd))
			return false;
		if (countWeekend == null) {
			if (other.countWeekend != null)
				return false;
		} else if (!countWeekend.equals(other.countWeekend))
			return false;
		if (rateType == null) {
			if (other.rateType != null)
				return false;
		} else if (!rateType.equals(other.rateType))
			return false;
		if (freeDayLimit == null) {
			if (other.freeDayLimit != null)
				return false;
		} else if (!freeDayLimit.equals(other.freeDayLimit))
			return false;
		if (rateDayLimit == null) {
			if (other.rateDayLimit != null)
				return false;
		} else if (!rateDayLimit.equals(other.rateDayLimit))
			return false;
		if (initialRate == null) {
			if (other.initialRate != null)
				return false;
		} else if (!initialRate.equals(other.initialRate))
			return false;
		if (secondaryRate == null) {
			if (other.secondaryRate != null)
				return false;
		} else if (!secondaryRate.equals(other.secondaryRate))
			return false;
		if (averageUnitLimit == null) {
			if (other.averageUnitLimit != null)
				return false;
		} else if (!averageUnitLimit.equals(other.averageUnitLimit))
			return false;
		if (mmDayLimit == null) {
			if (other.mmDayLimit != null)
				return false;
		} else if (!mmDayLimit.equals(other.mmDayLimit))
			return false;
		if (effectiveDate == null) {
			if (other.effectiveDate != null)
				return false;
		} else if (!effectiveDate.equals(other.effectiveDate))
			return false;
		if (endDate == null) {
			if (other.endDate != null)
				return false;
		} else if (!endDate.equals(other.endDate))
			return false;
		if (beneficialPrimSix == null) {
			if (other.beneficialPrimSix != null)
				return false;
		} else if (!beneficialPrimSix.equals(other.beneficialPrimSix))
			return false;
		if (custPrimSix == null) {
			if (other.custPrimSix != null)
				return false;
		} else if (!custPrimSix.equals(other.custPrimSix))
			return false;
		if (customerName == null) {
			if (other.customerName != null)
				return false;
		} else if (!customerName.equals(other.customerName))
			return false;
		if (beneficialCustomerName == null) {
			if (other.beneficialCustomerName != null)
				return false;
		} else if (!beneficialCustomerName.equals(other.beneficialCustomerName))
			return false;
		if (shipPrimSix == null) {
			if (other.shipPrimSix != null)
				return false;
		} else if (!shipPrimSix.equals(other.shipPrimSix))
			return false;
		if (shipCustomerName == null) {
			if (other.shipCustomerName != null)
				return false;
		} else if (!shipCustomerName.equals(other.shipCustomerName))
			return false;
		if (billCustomerId == null) {
			if (other.billCustomerId != null)
				return false;
		} else if (!billCustomerId.equals(other.billCustomerId))
			return false;
		return true;
	}
	@Override
	public Timestamp getCreateDateTime() {
		// TODO Auto-generated method stub
		return super.getCreateDateTime();
	}
	@Override
	public String getCreateUserId() {
		// TODO Auto-generated method stub
		return super.getCreateUserId();
	}
	@Override
	public Timestamp getUpdateDateTime() {
		// TODO Auto-generated method stub
		return super.getUpdateDateTime();
	}
	@Override
	public String getUpdateExtensionSchema() {
		// TODO Auto-generated method stub
		return super.getUpdateExtensionSchema();
	}
	@Override
	public String getUpdateUserId() {
		// TODO Auto-generated method stub
		return super.getUpdateUserId();
	}
	@Override
	public String getUversion() {
		// TODO Auto-generated method stub
		return super.getUversion();
	}
	@Override
	public void setCreateDateTime(Timestamp createDateTime) {
		// TODO Auto-generated method stub
		super.setCreateDateTime(createDateTime);
	}
	@Override
	public void setCreateUserId(String createUserId) {
		// TODO Auto-generated method stub
		super.setCreateUserId(createUserId);
	}
	@Override
	public void setUpdateDateTime(Timestamp updateDateTime) {
		// TODO Auto-generated method stub
		super.setUpdateDateTime(updateDateTime);
	}
	@Override
	public void setUpdateExtensionSchema(String updateExtensionSchema) {
		// TODO Auto-generated method stub
		super.setUpdateExtensionSchema(updateExtensionSchema);
	}
	@Override
	public void setUpdateUserId(String updateUserId) {
		// TODO Auto-generated method stub
		super.setUpdateUserId(updateUserId);
	}
	@Override
	public void setUversion(String uversion) {
		// TODO Auto-generated method stub
		super.setUversion(uversion);
	}
	@Override
	protected Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}
	@Override
	protected void finalize() throws Throwable {
		// TODO Auto-generated method stub
		super.finalize();
	}

	
}
