package com.nscorp.obis.domain;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.junit.platform.commons.util.StringUtils;

@Entity
@Table(name = "UMLER_CONV_CAR")
public class UmlerConventionalCar extends BaseUmlerCar {
	
	@OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "UMLER_ID", nullable = true, insertable = false, updatable = false)
    private List<ConventionalEquipmentWidth> conventionalEquipmentWidth;

    @Column(name = "S_D_WELL_IND", columnDefinition = "char(1)", nullable = true)
    private String singleDoubleWellInd;

    @Column(name = "MIN_EQ_LGTH", columnDefinition = "smallint", nullable = true)
    private Integer minEqLength;

    @Column(name = "MAX_EQ_LGTH", columnDefinition = "smallint", nullable = true)
    private Integer maxEqLength;

    @Column(name = "MIN_TRLR_LGTH", columnDefinition = "smallint", nullable = true)
    private Integer minTrailerLength;

    @Column(name = "MAX_TRLR_LGTH", columnDefinition = "smallint", nullable = true)
    private Integer maxTrailerLength;

    @Column(name = "AGGREGATE_LGTH", columnDefinition = "smallint", nullable = true)
    private Integer aggregateLength;

    @Column(name = "TOT_COFC_LGTH", columnDefinition = "smallint", nullable = true)
    private Integer totCofcLength;

    @Column(name = "ACCEPT_2C20_IND", columnDefinition = "char(1)", nullable = true)
    private String accept2c20Ind;

    @Column(name = "ACCEPT_3T28_IND", columnDefinition = "char(1)", nullable = true)
    private String accept3t28Ind;

    @Column(name = "ACCEPT_NOSE_MOUNTED_REEFER", columnDefinition = "char(1)", nullable = true)
    private String acceptNoseMountedReefer;

    @Column(name = "REEFER_AWELL_IND", columnDefinition = "char(1)", nullable = true)
    private String reeferAwellInd;

    @Column(name = "REEFER_TOFC_IND", columnDefinition = "char(1)", nullable = true)
    private String reeferTofcInd;

    @Column(name = "NO_REEFER_T40_IND", columnDefinition = "char(1)", nullable = true)
    private String noReeferT40Ind;

    @Column(name = "MAX_LOAD_WGT", columnDefinition = "integer", nullable = true)
    private Integer maxLoadWeight;

    @Column(name = "C20_MAX_WGT", columnDefinition = "integer", nullable = true)
    private Integer c20MaxWeight;

	public List<ConventionalEquipmentWidth> getConventionalEquipmentWidth() {
		return conventionalEquipmentWidth;
	}

	public void setConventionalEquipmentWidth(List<ConventionalEquipmentWidth> conventionalEquipmentWidth) {
		this.conventionalEquipmentWidth = conventionalEquipmentWidth;
	}

	public String getSingleDoubleWellInd() {
		if(StringUtils.isNotBlank(singleDoubleWellInd)) {
			return singleDoubleWellInd.trim();
		}
		else {
			return singleDoubleWellInd;
		}
	}

	public void setSingleDoubleWellInd(String singleDoubleWellInd) {
		if(StringUtils.isNotBlank(singleDoubleWellInd))
			this.singleDoubleWellInd = singleDoubleWellInd.toUpperCase();
		else 
			this.singleDoubleWellInd = singleDoubleWellInd;
	}

	public Integer getMinEqLength() {
		return minEqLength;
	}

	public void setMinEqLength(Integer minEqLength) {
		this.minEqLength = minEqLength;
	}

	public Integer getMaxEqLength() {
		return maxEqLength;
	}

	public void setMaxEqLength(Integer maxEqLength) {
		this.maxEqLength = maxEqLength;
	}

	public Integer getMinTrailerLength() {
		return minTrailerLength;
	}

	public void setMinTrailerLength(Integer minTrailerLength) {
		this.minTrailerLength = minTrailerLength;
	}

	public Integer getMaxTrailerLength() {
		return maxTrailerLength;
	}

	public void setMaxTrailerLength(Integer maxTrailerLength) {
		this.maxTrailerLength = maxTrailerLength;
	}

	public Integer getAggregateLength() {
		return aggregateLength;
	}

	public void setAggregateLength(Integer aggregateLength) {
		this.aggregateLength = aggregateLength;
	}

	public Integer getTotCofcLength() {
		return totCofcLength;
	}

	public void setTotCofcLength(Integer totCofcLength) {
		this.totCofcLength = totCofcLength;
	}

	public String getAccept2c20Ind() {
		if(StringUtils.isNotBlank(accept2c20Ind)) {
			return accept2c20Ind.trim();
		}
		else {
			return accept2c20Ind;
		}
	}

	public void setAccept2c20Ind(String accept2c20Ind) {
		if(StringUtils.isNotBlank(accept2c20Ind))
			this.accept2c20Ind = accept2c20Ind.toUpperCase();
		else 
			this.accept2c20Ind = accept2c20Ind;
	}

	public String getAccept3t28Ind() {
		if(StringUtils.isNotBlank(accept3t28Ind)) {
			return accept3t28Ind.trim();
		}
		else {
			return accept3t28Ind;
		}
	}

	public void setAccept3t28Ind(String accept3t28Ind) {
		if(StringUtils.isNotBlank(accept3t28Ind))
			this.accept3t28Ind = accept3t28Ind.toUpperCase();
		else 
			this.accept3t28Ind = accept3t28Ind;
	}

	public String getAcceptNoseMountedReefer() {
		if(StringUtils.isNotBlank(acceptNoseMountedReefer)) {
			return acceptNoseMountedReefer.trim();
		}
		else {
			return acceptNoseMountedReefer;
		}
	}

	public void setAcceptNoseMountedReefer(String acceptNoseMountedReefer) {
		if(StringUtils.isNotBlank(acceptNoseMountedReefer))
			this.acceptNoseMountedReefer = acceptNoseMountedReefer.toUpperCase();
		else 
			this.acceptNoseMountedReefer = acceptNoseMountedReefer;
	}

	public String getReeferAwellInd() {
		if(StringUtils.isNotBlank(reeferAwellInd)) {
			return reeferAwellInd.trim();
		}
		else {
			return reeferAwellInd;
		}
	}

	public void setReeferAwellInd(String reeferAwellInd) {
		if(StringUtils.isNotBlank(reeferAwellInd))
			this.reeferAwellInd = reeferAwellInd.toUpperCase();
		else 
			this.reeferAwellInd = reeferAwellInd;
	}

	public String getReeferTofcInd() {
		if(StringUtils.isNotBlank(reeferTofcInd)) {
			return reeferTofcInd.trim();
		}
		else {
			return reeferTofcInd;
		}
	}

	public void setReeferTofcInd(String reeferTofcInd) {
		if(StringUtils.isNotBlank(reeferTofcInd))
			this.reeferTofcInd = reeferTofcInd.toUpperCase();
		else 
			this.reeferTofcInd = reeferTofcInd;
	}

	public String getNoReeferT40Ind() {
		if(StringUtils.isNotBlank(noReeferT40Ind)) {
			return noReeferT40Ind.trim();
		}
		else {
			return noReeferT40Ind;
		}
	}

	public void setNoReeferT40Ind(String noReeferT40Ind) {
		if(StringUtils.isNotBlank(noReeferT40Ind))
			this.noReeferT40Ind = noReeferT40Ind.toUpperCase();
		else 
			this.noReeferT40Ind = noReeferT40Ind;
	}

	public Integer getMaxLoadWeight() {
		return maxLoadWeight;
	}

	public void setMaxLoadWeight(Integer maxLoadWeight) {
		this.maxLoadWeight = maxLoadWeight;
	}

	public Integer getC20MaxWeight() {
		return c20MaxWeight;
	}

	public void setC20MaxWeight(Integer c20MaxWeight) {
		this.c20MaxWeight = c20MaxWeight;
	}
	
	

	public UmlerConventionalCar(String uversion, String createUserId, Timestamp createDateTime, String updateUserId,
		Timestamp updateDateTime, String updateExtensionSchema, Long umlerId, String singleMultipleAarInd,
		String aarTyp, String aarCd, String aar1stNoLow, String aar1stNoHigh, String aar2ndNoLow,
		String aar2ndNoHigh, String aar3rdNo, String carInit, BigDecimal carLowNr, BigDecimal carHighNr,
		String carOwner, String tofcCofcInd, Integer minEqWidth, Integer maxEqWidth,
		List<ConventionalEquipmentWidth> conventionalEquipmentWidth, String singleDoubleWellInd,
		Integer minEqLength, Integer maxEqLength, Integer minTrailerLength, Integer maxTrailerLength,
		Integer aggregateLength, Integer totCofcLength, String accept2c20Ind, String accept3t28Ind,
		String acceptNoseMountedReefer, String reeferAwellInd, String reeferTofcInd, String noReeferT40Ind,
		Integer maxLoadWeight, Integer c20MaxWeight) {
	super(uversion, createUserId, createDateTime, updateUserId, updateDateTime, updateExtensionSchema, umlerId,
			singleMultipleAarInd, aarTyp, aarCd, aar1stNoLow, aar1stNoHigh, aar2ndNoLow, aar2ndNoHigh, aar3rdNo,
			carInit, carLowNr, carHighNr, carOwner, tofcCofcInd, minEqWidth, maxEqWidth);
	this.conventionalEquipmentWidth = conventionalEquipmentWidth;
	this.singleDoubleWellInd = singleDoubleWellInd;
	this.minEqLength = minEqLength;
	this.maxEqLength = maxEqLength;
	this.minTrailerLength = minTrailerLength;
	this.maxTrailerLength = maxTrailerLength;
	this.aggregateLength = aggregateLength;
	this.totCofcLength = totCofcLength;
	this.accept2c20Ind = accept2c20Ind;
	this.accept3t28Ind = accept3t28Ind;
	this.acceptNoseMountedReefer = acceptNoseMountedReefer;
	this.reeferAwellInd = reeferAwellInd;
	this.reeferTofcInd = reeferTofcInd;
	this.noReeferT40Ind = noReeferT40Ind;
	this.maxLoadWeight = maxLoadWeight;
	this.c20MaxWeight = c20MaxWeight;
	}

	public UmlerConventionalCar() {
		super();
		// TODO Auto-generated constructor stub
	}

}
