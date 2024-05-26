package com.nscorp.obis.domain;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import org.junit.platform.commons.util.StringUtils;

@Entity
@IdClass(ConventionalEquipmentWidthPrimaryKey.class)
@Table(name = "CONV_EQ_WDTH")
public class ConventionalEquipmentWidth extends AuditInfo {
	
	@Id
    @Column(name = "UMLER_ID", columnDefinition = "double", nullable = false)
    private Long umlerId;

    @Id
    @Column(name = "AAR_1ST_NR", columnDefinition = "char(1)", nullable = false)
    private String aar1stNr;

    @Column(name = "MIN_EQ_WDTH", columnDefinition = "smallint", nullable = false)
    private Integer minEqWidth;

    @Column(name = "MAX_EQ_WDTH", columnDefinition = "smallint", nullable = false)
    private Integer maxEqWidth;

	public Long getUmlerId() {
		return umlerId;
	}

	public void setUmlerId(Long umlerId) {
		this.umlerId = umlerId;
	}

	public String getAar1stNr() {
		if(StringUtils.isNotBlank(aar1stNr)) {
			return aar1stNr.trim();
		}
		else {
			return aar1stNr;
		}
	}

	public void setAar1stNr(String aar1stNr) {
		if(StringUtils.isNotBlank(aar1stNr))
			this.aar1stNr = aar1stNr.toUpperCase();
		else 
			this.aar1stNr = aar1stNr;
	}

	public Integer getMinEqWidth() {
		return minEqWidth;
	}

	public void setMinEqWidth(Integer minEqWidth) {
		this.minEqWidth = minEqWidth;
	}

	public Integer getMaxEqWidth() {
		return maxEqWidth;
	}

	public void setMaxEqWidth(Integer maxEqWidth) {
		this.maxEqWidth = maxEqWidth;
	}

	public ConventionalEquipmentWidth(String uversion, String createUserId, Timestamp createDateTime,
			String updateUserId, Timestamp updateDateTime, String updateExtensionSchema, Long umlerId, String aar1stNr,
			Integer minEqWidth, Integer maxEqWidth) {
		super(uversion, createUserId, createDateTime, updateUserId, updateDateTime, updateExtensionSchema);
		this.umlerId = umlerId;
		this.aar1stNr = aar1stNr;
		this.minEqWidth = minEqWidth;
		this.maxEqWidth = maxEqWidth;
	}

	public ConventionalEquipmentWidth() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ConventionalEquipmentWidth(String uversion, String createUserId, Timestamp createDateTime,
			String updateUserId, Timestamp updateDateTime, String updateExtensionSchema) {
		super(uversion, createUserId, createDateTime, updateUserId, updateDateTime, updateExtensionSchema);
		// TODO Auto-generated constructor stub
	}

}
