package com.nscorp.obis.domain;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.StringUtils;

@Entity
@Table(name = "POOL_TP")
public class PoolType extends AuditInfo {

	@Id
	@Column(name = "POOL_TP", length = 2, columnDefinition = "char(2)", nullable = false)
	private String poolTp;

	@Column(name = "POOL_TP_DESC", columnDefinition = "char(20)", nullable = false)
	private String poolTpDesc;

	@Column(name = "RSRV_IND", columnDefinition = "char(1)", nullable = false)
	private String rsrvInd;

	@Column(name = "ADV_RQD_IND", columnDefinition = "char(1)", nullable = false)
	private String advRqdInd;

	@Column(name = "ADV_ALLOW_IND", columnDefinition = "char(1)", nullable = false)
	private String advAllowInd;

	@Column(name = "ADV_OVERRIDE", columnDefinition = "char(1)", nullable = false)
	private String advOverride;

	@Column(name = "PU_RQD_IND", columnDefinition = "char(1)", nullable = false)
	private String puRqdInd;

	@Column(name = "PU_ALLOW_IND", columnDefinition = "char(1)", nullable = false)
	private String puAllowInd;

	@Column(name = "AGREEMENT_RQD", columnDefinition = "char(1)", nullable = false)
	private String agreementRqd;

	@Column(name = "UDF_PARAM_1", columnDefinition = "char(1)", nullable = false)
	private String udfParam1;

	@Column(name = "UDF_PARAM_2", columnDefinition = "char(1)", nullable = false)
	private String udfParam2;

	@Column(name = "UDF_PARAM_3", columnDefinition = "char(1)", nullable = false)
	private String udfParam3;

	@Column(name = "UDF_PARAM_4", columnDefinition = "char(1)", nullable = false)
	private String udfParam4;

	@Column(name = "UDF_PARAM_5", columnDefinition = "char(1)", nullable = false)
	private String udfParam5;

	@Column(name = "MULTI_RSRV_IND", columnDefinition = "char(1)", nullable = false)
	private String multiRsrvInd;

	public String getPoolTp() {
		if(StringUtils.isNotBlank(poolTp)) {
			return poolTp.trim();
		}
		else {
			return poolTp;
		}
		
	}

	public void setPoolTp(String poolTp) {
		if(StringUtils.isNotBlank(poolTp)){
			this.poolTp = poolTp.toUpperCase();
		}
		else {
			this.poolTp = poolTp;
		}
		
	}

	public String getPoolTpDesc() {
		if (StringUtils.isNotBlank(poolTpDesc))
			poolTpDesc = poolTpDesc.trim();
		return poolTpDesc;
	}

	public void setPoolTpDesc(String poolTpDesc) {
		if(StringUtils.isNotBlank(poolTpDesc)) {
			this.poolTpDesc = poolTpDesc.toUpperCase();
		}
		else {
			this.poolTpDesc = poolTpDesc;
		}
	}

	public String getRsrvInd() {
		return rsrvInd;
	}

	public void setRsrvInd(String rsrvInd) {
		this.rsrvInd = rsrvInd;
	}

	public String getAdvRqdInd() {
		return advRqdInd;
	}

	public void setAdvRqdInd(String advRqdInd) {
		this.advRqdInd = advRqdInd;
	}

	public String getAdvAllowInd() {
		return advAllowInd;
	}

	public void setAdvAllowInd(String advAllowInd) {
		this.advAllowInd = advAllowInd;
	}

	public String getAdvOverride() {
		return advOverride;
	}

	public void setAdvOverride(String advOverride) {
		this.advOverride = advOverride;
	}

	public String getPuRqdInd() {
		return puRqdInd;
	}

	public void setPuRqdInd(String puRqdInd) {
		this.puRqdInd = puRqdInd;
	}

	public String getPuAllowInd() {
		return puAllowInd;
	}

	public void setPuAllowInd(String puAllowInd) {
		this.puAllowInd = puAllowInd;
	}

	public String getAgreementRqd() {
		return agreementRqd;
	}

	public void setAgreementRqd(String agreementRqd) {
		this.agreementRqd = agreementRqd;
	}

	public String getUdfParam1() {
		return udfParam1;
	}

	public void setUdfParam1(String udfParam1) {
		this.udfParam1 = udfParam1;
	}

	public String getUdfParam2() {
		return udfParam2;
	}

	public void setUdfParam2(String udfParam2) {
		this.udfParam2 = udfParam2;
	}

	public String getUdfParam3() {
		return udfParam3;
	}

	public void setUdfParam3(String udfParam3) {
		this.udfParam3 = udfParam3;
	}

	public String getUdfParam4() {
		return udfParam4;
	}

	public void setUdfParam4(String udfParam4) {
		this.udfParam4 = udfParam4;
	}

	public String getUdfParam5() {
		return udfParam5;
	}

	public void setUdfParam5(String udfParam5) {
		this.udfParam5 = udfParam5;
	}

	public String getMultiRsrvInd() {
		return multiRsrvInd;
	}

	public void setMultiRsrvInd(String multiRsrvInd) {
		this.multiRsrvInd = multiRsrvInd;
	}

	public PoolType(String uversion, String createUserId, Timestamp createDateTime, String updateUserId,
			Timestamp updateDateTime, String updateExtensionSchema, String poolTp, String poolTpDesc, String rsrvInd,
			String advRqdInd, String advAllowInd, String advOverride, String puRqdInd, String puAllowInd,
			String agreementRqd, String udfParam1, String udfParam2, String udfParam3, String udfParam4,
			String udfParam5, String multiRsrvInd) {
		super(uversion, createUserId, createDateTime, updateUserId, updateDateTime, updateExtensionSchema);
		this.poolTp = poolTp;
		this.poolTpDesc = poolTpDesc;
		this.rsrvInd = rsrvInd;
		this.advRqdInd = advRqdInd;
		this.advAllowInd = advAllowInd;
		this.advOverride = advOverride;
		this.puRqdInd = puRqdInd;
		this.puAllowInd = puAllowInd;
		this.agreementRqd = agreementRqd;
		this.udfParam1 = udfParam1;
		this.udfParam2 = udfParam2;
		this.udfParam3 = udfParam3;
		this.udfParam4 = udfParam4;
		this.udfParam5 = udfParam5;
		this.multiRsrvInd = multiRsrvInd;
	}

	public PoolType() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PoolType(String uversion, String createUserId, Timestamp createDateTime, String updateUserId,
			Timestamp updateDateTime, String updateExtensionSchema) {
		super(uversion, createUserId, createDateTime, updateUserId, updateDateTime, updateExtensionSchema);
		// TODO Auto-generated constructor stub
	}

}