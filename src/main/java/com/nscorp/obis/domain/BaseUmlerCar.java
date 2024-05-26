package com.nscorp.obis.domain;

import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.junit.platform.commons.util.StringUtils;

@MappedSuperclass
public class BaseUmlerCar extends AuditInfo {
	
	@Id
    @Column(name = "UMLER_ID", columnDefinition = "double", nullable = false)
    private Long umlerId;

    @Column(name = "S_M_AAR_IND", columnDefinition = "char(1)", nullable = true)
    private String singleMultipleAarInd;

    @Column(name = "AAR_TP", columnDefinition = "char(4)", nullable = true)
    private String aarType;

    @Column(name = "AAR_CD", columnDefinition = "char(4)", nullable = true)
    private String aarCd;

    @Column(name = "AAR_1ST_NR_LOW", columnDefinition = "char(1)", nullable = true)
    private String aar1stNoLow;

    @Column(name = "AAR_1ST_NR_HIGH", columnDefinition = "char(1)", nullable = true)
    private String aar1stNoHigh;

    @Column(name = "AAR_2ND_NR_LOW", columnDefinition = "char(1)", nullable = true)
    private String aar2ndNoLow;

    @Column(name = "AAR_2ND_NR_HIGH", columnDefinition = "char(1)", nullable = true)
    private String aar2ndNoHigh;

    @Column(name = "AAR_3RD_NR", columnDefinition = "char(1)", nullable = true)
    private String aar3rdNo;

    @Column(name = "CAR_INIT", columnDefinition = "char(4)", nullable = true)
    private String carInit;

    @Column(name = "CAR_LOW_NR", columnDefinition = "decimal(19)", nullable = true)
    private BigDecimal carLowNr;

    @Column(name = "CAR_HIGH_NR", columnDefinition = "decimal(19)", nullable = true)
    private BigDecimal carHighNr;

    @Column(name = "CAR_OWNER", columnDefinition = "char(8)", nullable = true)
    private String carOwner;

    @Column(name = "TOFC_COFC_IND", columnDefinition = "char(1)", nullable = true)
    private String tofcCofcInd;

    @Column(name = "MIN_EQ_WDTH", columnDefinition = "smallint", nullable = true)
    private Integer minEqWidth;

    @Column(name = "MAX_EQ_WDTH", columnDefinition = "smallint", nullable = true)
    private Integer maxEqWidth;

	public Long getUmlerId() {
		return umlerId;
	}

	public void setUmlerId(Long umlerId) {
		this.umlerId = umlerId;
	}

	public String getSingleMultipleAarInd() {
		if(StringUtils.isNotBlank(singleMultipleAarInd)) {
			return singleMultipleAarInd.trim();
		}
		else {
			return singleMultipleAarInd;
		}
	}

	public void setSingleMultipleAarInd(String singleMultipleAarInd) {
		if(StringUtils.isNotBlank(singleMultipleAarInd))
			this.singleMultipleAarInd = singleMultipleAarInd.toUpperCase();
		else 
			this.singleMultipleAarInd = singleMultipleAarInd;
	}

	public String getAarType() {
		if(StringUtils.isNotBlank(aarType)) {
			return aarType.trim();
		}
		else {
			return aarType;
		}
	}

	public void setAarType(String aarType) {
		if(StringUtils.isNotBlank(aarType))
			this.aarType = aarType.toUpperCase();
		else 
			this.aarType = aarType;
	}

	public String getAarCd() {
		if(StringUtils.isNotBlank(aarCd)) {
			return aarCd.trim();
		}
		else {
			return aarCd;
		}
	}

	public void setAarCd(String aarCd) {
		if(StringUtils.isNotBlank(aarCd))
			this.aarCd = aarCd.toUpperCase();
		else 
			this.aarCd = aarCd;
	}

	public String getAar1stNoLow() {
		if(StringUtils.isNotBlank(aar1stNoLow)) {
			return aar1stNoLow.trim();
		}
		else {
			return aar1stNoLow;
		}
	}

	public void setAar1stNoLow(String aar1stNoLow) {
		if(StringUtils.isNotBlank(aar1stNoLow))
			this.aar1stNoLow = aar1stNoLow.toUpperCase();
		else 
			this.aar1stNoLow = aar1stNoLow;
	}

	public String getAar1stNoHigh() {
		if(StringUtils.isNotBlank(aar1stNoHigh)) {
			return aar1stNoHigh.trim();
		}
		else {
			return aar1stNoHigh;
		}
	}

	public void setAar1stNoHigh(String aar1stNoHigh) {
		if(StringUtils.isNotBlank(aar1stNoHigh))
			this.aar1stNoHigh = aar1stNoHigh.toUpperCase();
		else 
			this.aar1stNoHigh = aar1stNoHigh;
	}

	public String getAar2ndNoLow() {
		if(StringUtils.isNotBlank(aar2ndNoLow)) {
			return aar2ndNoLow.trim();
		}
		else {
			return aar2ndNoLow;
		}
	}

	public void setAar2ndNoLow(String aar2ndNoLow) {
		if(StringUtils.isNotBlank(aar2ndNoLow))
			this.aar2ndNoLow = aar2ndNoLow.toUpperCase();
		else 
			this.aar2ndNoLow = aar2ndNoLow;
	}

	public String getAar2ndNoHigh() {
		if(StringUtils.isNotBlank(aar2ndNoHigh)) {
			return aar2ndNoHigh.trim();
		}
		else {
			return aar2ndNoHigh;
		}
	}

	public void setAar2ndNoHigh(String aar2ndNoHigh) {
		if(StringUtils.isNotBlank(aar2ndNoHigh))
			this.aar2ndNoHigh = aar2ndNoHigh.toUpperCase();
		else 
			this.aar2ndNoHigh = aar2ndNoHigh;
	}

	public String getAar3rdNo() {
		if(StringUtils.isNotBlank(aar3rdNo)) {
			return aar3rdNo.trim();
		}
		else {
			return aar3rdNo;
		}
	}

	public void setAar3rdNo(String aar3rdNo) {
		if(StringUtils.isNotBlank(aar3rdNo))
			this.aar3rdNo = aar3rdNo.toUpperCase();
		else 
			this.aar3rdNo = aar3rdNo;
	}

	public String getCarInit() {
		if(StringUtils.isNotBlank(carInit)) {
			return carInit.trim();
		}
		else {
			return carInit;
		}
	}

	public void setCarInit(String carInit) {
		if(StringUtils.isNotBlank(carInit))
			this.carInit = carInit.toUpperCase();
		else 
			this.carInit = carInit;
	}

	public BigDecimal getCarLowNr() {
		return carLowNr;
	}

	public void setCarLowNr(BigDecimal carLowNr) {
		this.carLowNr = carLowNr;
	}

	public BigDecimal getCarHighNr() {
		return carHighNr;
	}

	public void setCarHighNr(BigDecimal carHighNr) {
		this.carHighNr = carHighNr;
	}

	public String getCarOwner() {
		if(StringUtils.isNotBlank(carOwner)) {
			return carOwner.trim();
		}
		else {
			return carOwner;
		}
	}

	public void setCarOwner(String carOwner) {
		if(StringUtils.isNotBlank(carOwner))
			this.carOwner = carOwner.toUpperCase();
		else 
			this.carOwner = carOwner;
	}

	public String getTofcCofcInd() {
		if(StringUtils.isNotBlank(tofcCofcInd)) {
			return tofcCofcInd.trim();
		}
		else {
			return tofcCofcInd;
		}
	}

	public void setTofcCofcInd(String tofcCofcInd) {
		if(StringUtils.isNotBlank(tofcCofcInd))
			this.tofcCofcInd = tofcCofcInd.toUpperCase();
		else 
			this.tofcCofcInd = tofcCofcInd;
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

	public BaseUmlerCar(String uversion, String createUserId, Timestamp createDateTime, String updateUserId,
			Timestamp updateDateTime, String updateExtensionSchema, Long umlerId, String singleMultipleAarInd,
			String aarType, String aarCd, String aar1stNoLow, String aar1stNoHigh, String aar2ndNoLow,
			String aar2ndNoHigh, String aar3rdNo, String carInit, BigDecimal carLowNr, BigDecimal carHighNr,
			String carOwner, String tofcCofcInd, Integer minEqWidth, Integer maxEqWidth) {
		super(uversion, createUserId, createDateTime, updateUserId, updateDateTime, updateExtensionSchema);
		this.umlerId = umlerId;
		this.singleMultipleAarInd = singleMultipleAarInd;
		this.aarType = aarType;
		this.aarCd = aarCd;
		this.aar1stNoLow = aar1stNoLow;
		this.aar1stNoHigh = aar1stNoHigh;
		this.aar2ndNoLow = aar2ndNoLow;
		this.aar2ndNoHigh = aar2ndNoHigh;
		this.aar3rdNo = aar3rdNo;
		this.carInit = carInit;
		this.carLowNr = carLowNr;
		this.carHighNr = carHighNr;
		this.carOwner = carOwner;
		this.tofcCofcInd = tofcCofcInd;
		this.minEqWidth = minEqWidth;
		this.maxEqWidth = maxEqWidth;
	}

	public BaseUmlerCar() {
		super();
		// TODO Auto-generated constructor stub
	}

	public BaseUmlerCar(String uversion, String createUserId, Timestamp createDateTime, String updateUserId,
			Timestamp updateDateTime, String updateExtensionSchema) {
		super(uversion, createUserId, createDateTime, updateUserId, updateDateTime, updateExtensionSchema);
		// TODO Auto-generated constructor stub
	}

}
