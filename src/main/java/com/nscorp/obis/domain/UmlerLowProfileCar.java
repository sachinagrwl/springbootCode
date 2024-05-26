package com.nscorp.obis.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "UMLER_LOWP_CAR")
@EqualsAndHashCode(callSuper = false)
public class UmlerLowProfileCar extends BaseUmlerCar {

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "UMLER_ID", nullable = false, insertable = false, updatable = false)
	private List<LowProfileEquipmentWidth> lowProfileEquipmentWidth;

	@Column(name = "NR_OF_PLATFORM", columnDefinition = "smallint", nullable = true)
	private Integer numberOfPlatform;

	@Column(name = "PLAT_CAR_IND", columnDefinition = "char(1)", nullable = true)
	private String platCarInd;

	@Column(name = "T_WELL_IND", columnDefinition = "char(1)", nullable = true)
	private String tWellInd;

	@Column(name = "Q_WELL_IND", columnDefinition = "char(1)", nullable = true)
	private String qWellInd;

	@Column(name = "L3_MIN_EQ_LGTH", columnDefinition = "smallint", nullable = true)
	private Integer l3MinEqLength;

	@Column(name = "L3_MAX_EQ_LGTH", columnDefinition = "smallint", nullable = true)
	private Integer l3MaxEqLength;

	@Column(name = "L3_CENTER_MIN_LGTH", columnDefinition = "smallint", nullable = true)
	private Integer l3CenterMinLength;

	@Column(name = "L4_MIN_EQ_LGTH", columnDefinition = "smallint", nullable = true)
	private Integer l4MinEqLength;

	@Column(name = "L4_2UNITS_TOT_LGTH", columnDefinition = "smallint", nullable = true)
	private Integer l42UnitsTotLgth;

	@Column(name = "L4_MAX_EQ_LGTH", columnDefinition = "smallint", nullable = true)
	private Integer l4MaxEqLength;

	@Column(name = "ACCEPT_2C20_IND", columnDefinition = "char(1)", nullable = true)
	private String accept2c20Ind;

	@Column(name = "ACCEPT_3T28_IND", columnDefinition = "char(1)", nullable = true)
	private String accept3t28Ind;

	@Column(name = "MIN_EQ_LGTH", columnDefinition = "smallint", nullable = true)
	private Integer minEqLength;

	@Column(name = "MAX_EQ_LGTH", columnDefinition = "smallint", nullable = true)
	private Integer maxEqLength;

	@Column(name = "MIN_TRLR_LGTH", columnDefinition = "smallint", nullable = true)
	private Integer minTrailerLength;

	@Column(name = "MAX_TRLR_LGTH", columnDefinition = "smallint", nullable = true)
	private Integer maxTrailerLength;

	@Column(name = "CNT_PAIRS_WELL_IND", columnDefinition = "char(1)", nullable = true)
	private String cntPairsWellInd;

	@Column(name = "CNT_PAIRS_MIN_LGTH", columnDefinition = "smallint", nullable = true)
	private Integer cntPairsMinLength;

	@Column(name = "CNT_PAIRS_MAX_LGTH", columnDefinition = "smallint", nullable = true)
	private Integer cntPairsMaxLength;

	@Column(name = "ACCEPT_TRLR_PAIRS_IND", columnDefinition = "char(1)", nullable = true)
	private String acceptTrailerPairsInd;

	@Column(name = "TRLR_PAIRS_MIN_LGTH", columnDefinition = "smallint", nullable = true)
	private Integer trailerPairsMinLength;

	@Column(name = "TRLR_PAIRS_MAX_LGTH", columnDefinition = "smallint", nullable = true)
	private Integer trailerPairsMaxLength;

	@Column(name = "ACCEPT_NOSE_MOUNTED_REEFER", columnDefinition = "char(1)", nullable = true)
	private String acceptNoseMountedReefer;

	@Column(name = "REEFER_WELL_IND", columnDefinition = "char(1)", nullable = true)
	private String reeferWellInd;

	@Column(name = "NO_REEFER_T40_IND", columnDefinition = "char(1)", nullable = true)
	private String noReeferT40Ind;

	@Column(name = "ACCEPT_CHAS_BUMPER", columnDefinition = "char(1)", nullable = true)
	private String acceptChasBumper;

	@Column(name = "MAX_LOAD_WGT", columnDefinition = "integer", nullable = true)
	private Integer maxLoadWeight;

	@Column(name = "C20_MAX_WGT", columnDefinition = "integer", nullable = true)
	private Integer c20MaxWeight;

}