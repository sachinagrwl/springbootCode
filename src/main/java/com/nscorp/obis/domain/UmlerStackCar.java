package com.nscorp.obis.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "UMLER_STACK_CAR")
@EqualsAndHashCode(callSuper = false)
public class UmlerStackCar extends BaseUmlerCar {

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "UMLER_ID", nullable = false, insertable = false, updatable = false)
	private List<StackWellLength> stackWellLengthList;

	@Transient
	private List<StackEquipmentWidth> stackEquipmentWidthList;

	@Column(name = "LD_WR_IND", columnDefinition = "char(1)", nullable = true)
	private String lengthDeterminedWidthRestrictionsInd;

	@Column(name = "STACK_CAR_TP", columnDefinition = "char(8)", nullable = true)
	private String numberOfPlatform;

	@Column(name = "END_WELL_LGTH", columnDefinition = "smallint", nullable = true)
	private Integer endWellLength;

	@Column(name = "MED_WELL_LGTH", columnDefinition = "smallint", nullable = true)
	private Integer medWellLength;

	@Column(name = "MIN_EQ_LGTH", columnDefinition = "smallint", nullable = true)
	private Integer minEqLength;

	@Column(name = "MAX_EQ_LGTH", columnDefinition = "smallint", nullable = true)
	private Integer maxEqLength;

	@Column(name = "TOP_MIN_EQ_LGTH", columnDefinition = "smallint", nullable = true)
	private Integer topMinEqLength;

	@Column(name = "TOP_MAX_EQ_LGTH", columnDefinition = "smallint", nullable = true)
	private Integer topMaxEqLength;

	@Column(name = "MED_MIN_EQ_LGTH", columnDefinition = "smallint", nullable = true)
	private Integer medMinEqLength;

	@Column(name = "MED_MAX_EQ_LGTH", columnDefinition = "smallint", nullable = true)
	private Integer medMaxEqLength;

	@Column(name = "MED_TOP_MIN_EQ_LGTH", columnDefinition = "smallint", nullable = true)
	private Integer medTopMinEqLength;

	@Column(name = "MED_TOP_MAX_EQ_LGTH", columnDefinition = "smallint", nullable = true)
	private Integer medTopMaxEqLength;

	@Column(name = "MED2_MIN_EQ_LGTH", columnDefinition = "smallint", nullable = true)
	private Integer med2MinEqLength;

	@Column(name = "MED2_MAX_EQ_LGTH", columnDefinition = "smallint", nullable = true)
	private Integer med2MaxEqLength;

	@Column(name = "MED2_TOP_MIN_EQ_LGTH", columnDefinition = "smallint", nullable = true)
	private Integer med2TopMinEqLength;

	@Column(name = "MED2_TOP_MAX_EQ_LGTH", columnDefinition = "smallint", nullable = true)
	private Integer med2TopMaxEqLength;

	@Column(name = "NO_MED_LGTH_ON_TOP_IND", columnDefinition = "char(1)", nullable = true)
	private String noMedLengthOnTopInd;

	@Column(name = "COND_MAX_EQ_LGTH", columnDefinition = "smallint", nullable = true)
	private Integer condMaxEqLength;

	@Column(name = "EQ_PAIRS_WELL_IND", columnDefinition = "char(1)", nullable = true)
	private String eqPairsWellInd;

	@Column(name = "EQ_PAIRS_MIN_LGTH", columnDefinition = "smallint", nullable = true)
	private Integer eqPairsMinLength;

	@Column(name = "EQ_PAIRS_MAX_LGTH", columnDefinition = "smallint", nullable = true)
	private Integer eqPairsMaxLength;

	@Column(name = "ACCEPT_CNT_PAIRS_ON_TOP", columnDefinition = "char(1)", nullable = true)
	private String acceptCntPairsOnTop;

	@Column(name = "TOP_CNT_PAIRS_MIN_LGTH", columnDefinition = "smallint", nullable = true)
	private Integer topCntPairsMinLength;

	@Column(name = "TOP_CNT_PAIRS_MAX_LGTH", columnDefinition = "smallint", nullable = true)
	private Integer topCntPairsMaxLength;

	@Column(name = "MIN_TRLR_LGTH", columnDefinition = "smallint", nullable = true)
	private Integer minTrlrLength;

	@Column(name = "MAX_TRLR_LGTH", columnDefinition = "smallint", nullable = true)
	private Integer maxTrlrLength;

	@Column(name = "ACCEPT_TRLR_PAIRS_IND", columnDefinition = "char(1)", nullable = true)
	private String acceptTrlrPairsInd;

	@Column(name = "TRLR_PAIRS_MIN_LGTH", columnDefinition = "smallint", nullable = true)
	private Integer trlrPairsMinLength;

	@Column(name = "TRLR_PAIRS_MAX_LGTH", columnDefinition = "smallint", nullable = true)
	private Integer trlrPairsMaxLength;

	@Column(name = "ACCEPT_NOSE_MOUNTED_REEFER", columnDefinition = "char(1)", nullable = true)
	private String acceptNoseMountedReefer;

	@Column(name = "ACCEPT_CHAS_BUMPER", columnDefinition = "char(1)", nullable = true)
	private String acceptChasBumper;

	@Column(name = "MAX_LOAD_WGT", columnDefinition = "integer", nullable = true)
	private Integer maxLoadWeight;

	@Column(name = "C20_MAX_WGT", columnDefinition = "integer", nullable = true)
	private Integer c20MaxWeight;

}