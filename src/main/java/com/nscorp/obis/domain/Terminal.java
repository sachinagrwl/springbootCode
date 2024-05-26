package com.nscorp.obis.domain;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

@Entity
@Table(name="TERMINAL")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Terminal extends AuditInfo{
	@Id
	@Column(name="TERM_ID", length = 15, columnDefinition = "Double", nullable=false)
    Long terminalId;
	
	@OneToOne(targetEntity = TerminalInd.class, orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "TERM_ID", referencedColumnName = "TERM_ID", insertable = false, updatable = false)
	private TerminalInd terminalInd;
	
	@ManyToOne
	@JoinColumn(name = "STN_XRF_ID", referencedColumnName = "TERM_ID", insertable = false, updatable = false)
	private Station station;

	public TerminalInd getTerminalInd() {
		return terminalInd;
	}

	public void setTerminalInd(TerminalInd terminalInd) {
		this.terminalInd = terminalInd;
	}
	 

	@Column(name = "TERM_NM", columnDefinition = "char(30)", nullable = false)
	private String terminalName;

	@Column(name = "STN_XRF_ID", columnDefinition = "Double", nullable = true)
	private Long stnXrfId;
	
	@Column(name = "EXPIRED_DT", columnDefinition = "Date", nullable = true)
	private Date expiredDate;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "TERM_CNTRY", columnDefinition = "char(3)", nullable = true)
	private NSCountry terminalCountry;
	
	@Column(name = "TM_ZN_PLUS",columnDefinition = "Smallint", nullable = true)
	private Integer terminalZnPlus;
	
	@Convert(converter = NSTimeZoneConverter.class)
	@Column(name = "TM_ZN_OFFSET",columnDefinition = "Smallint", nullable = true)
	private NSTimeZone terminalZnOffset;
	
	@Column(name = "TERM_ADDR_1", columnDefinition = "char(30)", nullable = true)
	private String terminalAddress1;
	
	@Column(name = "TERM_CTY_1", columnDefinition = "char(19)", nullable = true)
	private String terminalCity1;
	
	@Column(name = "TERM_ST_1", columnDefinition = "char(2)", nullable = true)
	private String terminalState1;
	
	@Column(name = "TERM_ZIP_CD_1", columnDefinition = "char(10)", nullable = true)
	private String terminalZipCode1;
	
	@Column(name = "TERM_ADDR_2", columnDefinition = "char(30)", nullable = true)
	private String terminalAddress2;
	
	@Column(name = "TERM_CTY_2", columnDefinition = "char(19)", nullable = true)
	private String terminalCity2;
	
	@Column(name = "TERM_ST_2", columnDefinition = "char(2)", nullable = true)
	private String terminalState2;
	
	@Column(name = "TERM_ZIP_CD_2", columnDefinition = "char(10)", nullable = true)
	private String terminalZipCode2;
	
	@Column(name = "EXTN_AREA_CD_1",columnDefinition = "Smallint", nullable = true)
	private Integer externalAreaCd1;
	
	@Column(name = "EXTN_EXCH_1",columnDefinition = "Smallint", nullable = true)
	private Integer externalExchange1;
	
	@Column(name = "EXTN_EXT_1",columnDefinition = "Smallint", nullable = true)
	private Integer externalExtension1;
	
	@Column(name = "EXTN_AREA_CD_2",columnDefinition = "Smallint", nullable = true)
	private Integer externalAreaCd2;
	
	@Column(name = "EXTN_EXCH_2",columnDefinition = "Smallint", nullable = true)
	private Integer externalExchange2;
	
	@Column(name = "EXTN_EXT_2",columnDefinition = "Smallint", nullable = true)
	private Integer externalExtension2;
	
	@Column(name = "EXTN_AREA_CD_3",columnDefinition = "Smallint", nullable = true)
	private Integer externalAreaCd3;
	
	@Column(name = "EXTN_EXCH_3",columnDefinition = "Smallint", nullable = true)
	private Integer externalExchange3;
	
	@Column(name = "EXTN_EXT_3",columnDefinition = "Smallint", nullable = true)
	private Integer externalExtension3;
	
	@Column(name = "INTN_AREA_CD_1",columnDefinition = "Smallint", nullable = true)
	private Integer internalAreaCd1;
	
	@Column(name = "INTN_EXCH_1",columnDefinition = "Smallint", nullable = true)
	private Integer internalExchange1;
	
	@Column(name = "INTN_EXT_1",columnDefinition = "Smallint", nullable = true)
	private Integer internalExtension1;
	
	@Column(name = "INTN_AREA_CD_2",columnDefinition = "Smallint", nullable = true)
	private Integer internalAreaCd2;
	
	@Column(name = "INTN_EXCH_2",columnDefinition = "Smallint", nullable = true)
	private Integer internalExchange2;
	
	@Column(name = "INTN_EXT_2",columnDefinition = "Smallint", nullable = true)
	private Integer internalExtension2;
	
	@Column(name = "INTN_AREA_CD_3",columnDefinition = "Smallint", nullable = true)
	private Integer internalAreaCd3;
	
	@Column(name = "INTN_EXCH_3",columnDefinition = "Smallint", nullable = true)
	private Integer internalExchange3;
	
	@Column(name = "INTN_EXT_3",columnDefinition = "Smallint", nullable = true)
	private Integer internalExtension3;
	
	@Column(name = "EXTN_FAX_AREA_1",columnDefinition = "Smallint", nullable = true)
	private Integer externalFaxArea1;
	
	@Column(name = "EXTN_FAX_EXCH_1",columnDefinition = "Smallint", nullable = true)
	private Integer externalFaxExchange1;
	
	@Column(name = "EXTN_FAX_EXT_1",columnDefinition = "Smallint", nullable = true)
	private Integer externalFaxExtension1;
	
	@Column(name = "EXTN_FAX_AREA_2",columnDefinition = "Smallint", nullable = true)
	private Integer externalFaxArea2;
	
	@Column(name = "EXTN_FAX_EXCH_2",columnDefinition = "Smallint", nullable = true)
	private Integer externalFaxExchange2;
	
	@Column(name = "EXTN_FAX_EXT_2",columnDefinition = "Smallint", nullable = true)
	private Integer externalFaxExtension2;
	
	@Column(name = "EXTN_FAX_AREA_3",columnDefinition = "Smallint", nullable = true)
	private Integer externalFaxArea3;
	
	@Column(name = "EXTN_FAX_EXCH_3",columnDefinition = "Smallint", nullable = true)
	private Integer externalFaxExchange3;
	
	@Column(name = "EXTN_FAX_EXT_3",columnDefinition = "Smallint", nullable = true)
	private Integer externalFaxExtension3;
	
	@Column(name = "INTN_FAX_AREA_1",columnDefinition = "Smallint", nullable = true)
	private Integer internalFaxArea1;
	
	@Column(name = "INTN_FAX_EXCH_1",columnDefinition = "Smallint", nullable = true)
	private Integer internalFaxExchange1;
	
	@Column(name = "INTN_FAX_EXT_1",columnDefinition = "Smallint", nullable = true)
	private Integer internalFaxExtension1;
	
	@Column(name = "INTN_FAX_AREA_2",columnDefinition = "Smallint", nullable = true)
	private Integer internalFaxArea2;
	
	@Column(name = "INTN_FAX_EXCH_2",columnDefinition = "Smallint", nullable = true)
	private Integer internalFaxExchange2;
	
	@Column(name = "INTN_FAX_EXT_2",columnDefinition = "Smallint", nullable = true)
	private Integer internalFaxExtension2;
	
	@Column(name = "INTN_FAX_AREA_3",columnDefinition = "Smallint", nullable = true)
	private Integer internalFaxArea3;
	
	@Column(name = "INTN_FAX_EXCH_3",columnDefinition = "Smallint", nullable = true)
	private Integer internalFaxExchange3;
	
	@Column(name = "INTN_FAX_EXT_3",columnDefinition = "Smallint", nullable = true)
	private Integer internalFaxExtension3;
	
	@Column(name = "DAYLGHT_SAVE_IND", columnDefinition = "char(1)", nullable = true)
	private String dayLightSaveIndicator;
	
	@Column(name = "RNTFY_TM", columnDefinition = "char(4)", nullable = true)
	private String renotifyTime;
	
	@Convert(converter = DaysOfWeekConverter.class)
	@Column(name="RNTFY_DAYS",columnDefinition = "char(7)", nullable = true)
	List<DayOfWeek> renotifyDays;
	
	@Column(name = "DEFERRED_TM", columnDefinition = "char(4)", nullable = true)
	private String deferredTime;
	
	@Column(name = "NS_TERM_ID",columnDefinition = "Smallint", nullable = true)
	private Integer nsTerminalId;
	
	@Convert(converter = TerminalTypeConverter.class)
	@Column(name = "TERM_TP", columnDefinition = "char(1)", nullable = false)
	private TerminalType terminalType;
	
	@JsonFormat(pattern="HH")
	@Column(name = "TERM_CLOSEOUT_TM", columnDefinition = "time", nullable = true)
	private Time terminalCloseOutTime; 
	
	@Column(name = "HAULAGE_IND", columnDefinition = "char(1)", nullable = true)
	private String haulageIndicator;
	
	@Column(name = "IDCS_IND", columnDefinition = "char(1)", nullable = true)
	private String idcsTerminalIndicator;
		
	@Column(name = "SSW_TERM_IND", columnDefinition = "char(1)", nullable = true)
	private String sswTerminalIndicator;
	
	@Column(name = "HITCH_CK_IND", columnDefinition = "char(1)", nullable = true)
	private String hitchCheckIndicator;



	public NSCountry getTerminalCountry() {
		return terminalCountry;
	}

	public void setTerminalCountry(NSCountry terminalCountry) {
		this.terminalCountry = terminalCountry;
	}

	public Integer getTerminalZnPlus() {
		return terminalZnPlus;
	}

	public void setTerminalZnPlus(Integer terminalZnPlus) {
		this.terminalZnPlus = terminalZnPlus;
	}

	public NSTimeZone getTerminalZnOffset() {
		return terminalZnOffset;
	}

	public void setTerminalZnOffset(NSTimeZone terminalZnOffset) {
		this.terminalZnOffset = terminalZnOffset;
	}

	public String getTerminalAddress1() {
		if(terminalAddress1 !=null)
			return terminalAddress1.trim();
		else
		return terminalAddress1;
	}

	public void setTerminalAddress1(String terminalAddress1) {
		this.terminalAddress1 = terminalAddress1;
	}

	public String getTerminalCity1() {
		if(terminalCity1 != null)
		return terminalCity1.trim();
		else
			return terminalCity1;
	}

	public void setTerminalCity1(String terminalCity1) {
		this.terminalCity1 = terminalCity1;
	}

	public String getTerminalState1() {
		return terminalState1;
	}

	public void setTerminalState1(String terminalState1) {
		this.terminalState1 = terminalState1;
	}

	public String getTerminalZipCode1() {
		if(terminalZipCode1 != null)
			return terminalZipCode1.trim();
		else
		return terminalZipCode1;
	}

	public void setTerminalZipCode1(String terminalZipCode1) {
		this.terminalZipCode1 = terminalZipCode1;
	}

	public String getTerminalAddress2() {
		if(terminalAddress2 !=null)
			return terminalAddress2.trim();
		else
		return terminalAddress2;
	}

	public void setTerminalAddress2(String terminalAddress2) {
		this.terminalAddress2 = terminalAddress2;
	}

	public String getTerminalCity2() {
		if(terminalCity2 !=null)
			return terminalCity2.trim();
		else
		return terminalCity2;
	}

	public void setTerminalCity2(String terminalCity2) {
		this.terminalCity2 = terminalCity2;
	}

	public String getTerminalState2() {
		if(terminalState2 !=null)
			return terminalState2.trim();
		else
		return null;
	}

	public void setTerminalState2(String terminalState2) {
		this.terminalState2 = terminalState2;
	}

	public String getTerminalZipCode2() {
		if(terminalZipCode2 !=null)
			return terminalZipCode2.trim();
		else
		return terminalZipCode2;
	}

	public void setTerminalZipCode2(String terminalZipCode2) {
		this.terminalZipCode2 = terminalZipCode2;
	}

	public Integer getExternalAreaCd1() {
		return externalAreaCd1;
	}

	public void setExternalAreaCd1(Integer externalAreaCd1) {
		this.externalAreaCd1 = externalAreaCd1;
	}

	public Integer getExternalExchange1() {
		return externalExchange1;
	}

	public void setExternalExchange1(Integer externalExchange1) {
		this.externalExchange1 = externalExchange1;
	}

	public Integer getExternalExtension1() {
		return externalExtension1;
	}

	public void setExternalExtension1(Integer externalExtension1) {
		this.externalExtension1 = externalExtension1;
	}

	public Integer getExternalAreaCd2() {
		return externalAreaCd2;
	}

	public void setExternalAreaCd2(Integer externalAreaCd2) {
		this.externalAreaCd2 = externalAreaCd2;
	}

	public Integer getExternalExchange2() {
		return externalExchange2;
	}

	public void setExternalExchange2(Integer externalExchange2) {
		this.externalExchange2 = externalExchange2;
	}

	public Integer getExternalExtension2() {
		return externalExtension2;
	}

	public void setExternalExtension2(Integer externalExtension2) {
		this.externalExtension2 = externalExtension2;
	}

	public Integer getExternalAreaCd3() {
		return externalAreaCd3;
	}

	public void setExternalAreaCd3(Integer externalAreaCd3) {
		this.externalAreaCd3 = externalAreaCd3;
	}

	public Integer getExternalExchange3() {
		return externalExchange3;
	}

	public void setExternalExchange3(Integer externalExchange3) {
		this.externalExchange3 = externalExchange3;
	}

	public Integer getExternalExtension3() {
		return externalExtension3;
	}

	public void setExternalExtension3(Integer externalExtension3) {
		this.externalExtension3 = externalExtension3;
	}

	public Integer getInternalAreaCd1() {
		return internalAreaCd1;
	}

	public void setInternalAreaCd1(Integer internalAreaCd1) {
		this.internalAreaCd1 = internalAreaCd1;
	}

	public Integer getInternalExchange1() {
		return internalExchange1;
	}

	public void setInternalExchange1(Integer internalExchange1) {
		this.internalExchange1 = internalExchange1;
	}

	public Integer getInternalExtension1() {
		return internalExtension1;
	}

	public void setInternalExtension1(Integer internalExtension1) {
		this.internalExtension1 = internalExtension1;
	}

	public Integer getInternalAreaCd2() {
		return internalAreaCd2;
	}

	public void setInternalAreaCd2(Integer internalAreaCd2) {
		this.internalAreaCd2 = internalAreaCd2;
	}

	public Integer getInternalExchange2() {
		return internalExchange2;
	}

	public void setInternalExchange2(Integer internalExchange2) {
		this.internalExchange2 = internalExchange2;
	}

	public Integer getInternalExtension2() {
		return internalExtension2;
	}

	public void setInternalExtension2(Integer internalExtension2) {
		this.internalExtension2 = internalExtension2;
	}

	public Integer getInternalAreaCd3() {
		return internalAreaCd3;
	}

	public void setInternalAreaCd3(Integer internalAreaCd3) {
		this.internalAreaCd3 = internalAreaCd3;
	}

	public Integer getInternalExchange3() {
		return internalExchange3;
	}

	public void setInternalExchange3(Integer internalExchange3) {
		this.internalExchange3 = internalExchange3;
	}

	public Integer getInternalExtension3() {
		return internalExtension3;
	}

	public void setInternalExtension3(Integer internalExtension3) {
		this.internalExtension3 = internalExtension3;
	}

	public Integer getExternalFaxArea1() {
		return externalFaxArea1;
	}

	public void setExternalFaxArea1(Integer externalFaxArea1) {
		this.externalFaxArea1 = externalFaxArea1;
	}

	public Integer getExternalFaxExchange1() {
		return externalFaxExchange1;
	}

	public void setExternalFaxExchange1(Integer externalFaxExchange1) {
		this.externalFaxExchange1 = externalFaxExchange1;
	}

	public Integer getExternalFaxExtension1() {
		return externalFaxExtension1;
	}

	public void setExternalFaxExtension1(Integer externalFaxExtension1) {
		this.externalFaxExtension1 = externalFaxExtension1;
	}

	public Integer getExternalFaxArea2() {
		return externalFaxArea2;
	}

	public void setExternalFaxArea2(Integer externalFaxArea2) {
		this.externalFaxArea2 = externalFaxArea2;
	}

	public Integer getExternalFaxExchange2() {
		return externalFaxExchange2;
	}

	public void setExternalFaxExchange2(Integer externalFaxExchange2) {
		this.externalFaxExchange2 = externalFaxExchange2;
	}

	public Integer getExternalFaxExtension2() {
		return externalFaxExtension2;
	}

	public void setExternalFaxExtension2(Integer externalFaxExtension2) {
		this.externalFaxExtension2 = externalFaxExtension2;
	}

	public Integer getExternalFaxArea3() {
		return externalFaxArea3;
	}

	public void setExternalFaxArea3(Integer externalFaxArea3) {
		this.externalFaxArea3 = externalFaxArea3;
	}

	public Integer getExternalFaxExchange3() {
		return externalFaxExchange3;
	}

	public void setExternalFaxExchange3(Integer externalFaxExchange3) {
		this.externalFaxExchange3 = externalFaxExchange3;
	}

	public Integer getExternalFaxExtension3() {
		return externalFaxExtension3;
	}

	public void setExternalFaxExtension3(Integer externalFaxExtension3) {
		this.externalFaxExtension3 = externalFaxExtension3;
	}

	public Integer getInternalFaxArea1() {
		return internalFaxArea1;
	}

	public void setInternalFaxArea1(Integer internalFaxArea1) {
		this.internalFaxArea1 = internalFaxArea1;
	}

	public Integer getInternalFaxExchange1() {
		return internalFaxExchange1;
	}

	public void setInternalFaxExchange1(Integer internalFaxExchange1) {
		this.internalFaxExchange1 = internalFaxExchange1;
	}

	public Integer getInternalFaxExtension1() {
		return internalFaxExtension1;
	}

	public void setInternalFaxExtension1(Integer internalFaxExtension1) {
		this.internalFaxExtension1 = internalFaxExtension1;
	}

	public Integer getInternalFaxArea2() {
		return internalFaxArea2;
	}

	public void setInternalFaxArea2(Integer internalFaxArea2) {
		this.internalFaxArea2 = internalFaxArea2;
	}

	public Integer getInternalFaxExchange2() {
		return internalFaxExchange2;
	}

	public void setInternalFaxExchange2(Integer internalFaxExchange2) {
		this.internalFaxExchange2 = internalFaxExchange2;
	}

	public Integer getInternalFaxExtension2() {
		return internalFaxExtension2;
	}

	public void setInternalFaxExtension2(Integer internalFaxExtension2) {
		this.internalFaxExtension2 = internalFaxExtension2;
	}

	public Integer getInternalFaxArea3() {
		return internalFaxArea3;
	}

	public void setInternalFaxArea3(Integer internalFaxArea3) {
		this.internalFaxArea3 = internalFaxArea3;
	}

	public Integer getInternalFaxExchange3() {
		return internalFaxExchange3;
	}

	public void setInternalFaxExchange3(Integer internalFaxExchange3) {
		this.internalFaxExchange3 = internalFaxExchange3;
	}

	public Integer getInternalFaxExtension3() {
		return internalFaxExtension3;
	}

	public void setInternalFaxExtension3(Integer internalFaxExtension3) {
		this.internalFaxExtension3 = internalFaxExtension3;
	}

	public String getDayLightSaveIndicator() {
		if(dayLightSaveIndicator != null)
		return dayLightSaveIndicator.trim();
		else
			return null;
	}

	public void setDayLightSaveIndicator(String dayLightSaveIndicator) {
		this.dayLightSaveIndicator = dayLightSaveIndicator;
	}

	public String getRenotifyTime() {
		if(renotifyTime !=null)
			return renotifyTime.trim();
		else
		return renotifyTime;
	}

	public void setRenotifyTime(String renotifyTime) {
		this.renotifyTime = renotifyTime;
	}

	public List<DayOfWeek> getRenotifyDays() {
		return renotifyDays;
	}

	public void setRenotifyDays(List<DayOfWeek> renotifyDays) {
		this.renotifyDays = renotifyDays;
	}

	
	public String getDeferredTime() {
		if(deferredTime != null)
		return deferredTime.trim();
		else
			return deferredTime;
	}

	public void setDeferredTime(String deferredTime) {
		this.deferredTime = deferredTime;
	}

	public Integer getNsTerminalId() {
		if(nsTerminalId == null)
			return null;
		else
		return nsTerminalId;
	}

	public void setNsTerminalId(Integer nsTerminalId) {
		this.nsTerminalId = nsTerminalId;
	}

	public TerminalType getTerminalType() {
		return terminalType;
	}

	public void setTerminalType(TerminalType terminalType) {
		this.terminalType = terminalType;
	}

	public Time getTerminalCloseOutTime() {
		return terminalCloseOutTime;
	}

	public void setTerminalCloseOutTime(Time terminalCloseOutTime) {
		this.terminalCloseOutTime = terminalCloseOutTime;
	}

	public String getHaulageIndicator() {
		return haulageIndicator;
	}

	public void setHaulageIndicator(String haulageIndicator) {
		this.haulageIndicator = haulageIndicator;
	}

	public String getIdcsTerminalIndicator() {
		return idcsTerminalIndicator;
	}

	public void setIdcsTerminalIndicator(String idcsTerminalIndicator) {
		this.idcsTerminalIndicator = idcsTerminalIndicator;
	}

	public String getSswTerminalIndicator() {
		return sswTerminalIndicator;
	}

	public void setSswTerminalIndicator(String sswTerminalIndicator) {
		this.sswTerminalIndicator = sswTerminalIndicator;
	}

	public String getHitchCheckIndicator() {
		return hitchCheckIndicator;
	}

	public void setHitchCheckIndicator(String hitchCheckIndicator) {
		this.hitchCheckIndicator = hitchCheckIndicator;
	}

	public Date getExpiredDate() {
		return expiredDate;
	}

	public void setExpiredDate(Date endDate) {
		this.expiredDate = endDate;
	}

	

	public Long getStnXrfId() {
		if(stnXrfId != null)
			return stnXrfId;
		else
			return null;
	}

	public void setStnXrfId(Long stnXrfId) {
		this.stnXrfId = stnXrfId;
	}

	public Long getTerminalId() {
		return terminalId;
	}

	public void setTerminalId(Long terminalId) {
		this.terminalId = terminalId;
	}

	public String getTerminalName() {
		if(terminalName != null) {
			return terminalName.trim();
		} else {
			return terminalName;
		}
	}

	public void setTerminalName(String terminalName) {
		this.terminalName = terminalName;
	}
	


	public Station getStation() {
		return station;
	}

	public void setStation(Station station) {
		this.station = station;
	}

	public Terminal(Long terminalId, String terminalName, Long stnXrfId, Date expiredDate) {
		super();
		this.terminalId = terminalId;
		this.terminalName = terminalName;
		this.stnXrfId = stnXrfId;
		this.expiredDate = expiredDate;
	}	
	
	

	

	public Terminal(String uversion, String createUserId, Timestamp createDateTime, String updateUserId,
			Timestamp updateDateTime, String updateExtensionSchema, Long terminalId, String terminalName, Long stnXrfId,
			Date expiredDate, NSCountry terminalCountry, Integer terminalZnPlus, NSTimeZone terminalZnOffset,
			String terminalAddress1, String terminalCity1, String terminalState1, String terminalZipCode1,
			String terminalAddress2, String terminalCity2, String terminalState2, String terminalZipCode2,
			Integer externalAreaCd1, Integer externalExchange1, Integer externalExtension1, Integer externalAreaCd2,
			Integer externalExchange2, Integer externalExtension2, Integer externalAreaCd3, Integer externalExchange3,
			Integer externalExtension3, Integer internalAreaCd1, Integer internalExchange1, Integer internalExtension1,
			Integer internalAreaCd2, Integer internalExchange2, Integer internalExtension2, Integer internalAreaCd3,
			Integer internalExchange3, Integer internalExtension3, Integer externalFaxArea1,
			Integer externalFaxExchange1, Integer externalFaxExtension1, Integer externalFaxArea2,
			Integer externalFaxExchange2, Integer externalFaxExtension2, Integer externalFaxArea3,
			Integer externalFaxExchange3, Integer externalFaxExtension3, Integer internalFaxArea1,
			Integer internalFaxExchange1, Integer internalFaxExtension1, Integer internalFaxArea2,
			Integer internalFaxExchange2, Integer internalFaxExtension2, Integer internalFaxArea3,
			Integer internalFaxExchange3, Integer internalFaxExtension3, String dayLightSaveIndicator,
			String renotifyTime, List<DayOfWeek> renotifyDays, String deferredTime, Integer nsTerminalId,
			TerminalType terminalType, Time terminalCloseOutTime, String haulageIndicator, String idcsTerminalIndicator,
			String sswTerminalIndicator, String hitchCheckIndicator,Station station) {
		super();
		this.terminalId = terminalId;
		this.terminalName = terminalName;
		this.stnXrfId = stnXrfId;
		this.expiredDate = expiredDate;
		this.terminalCountry = terminalCountry;
		this.terminalZnPlus = terminalZnPlus;
		this.terminalZnOffset = terminalZnOffset;
		this.terminalAddress1 = terminalAddress1;
		this.terminalCity1 = terminalCity1;
		this.terminalState1 = terminalState1;
		this.terminalZipCode1 = terminalZipCode1;
		this.terminalAddress2 = terminalAddress2;
		this.terminalCity2 = terminalCity2;
		this.terminalState2 = terminalState2;
		this.terminalZipCode2 = terminalZipCode2;
		this.externalAreaCd1 = externalAreaCd1;
		this.externalExchange1 = externalExchange1;
		this.externalExtension1 = externalExtension1;
		this.externalAreaCd2 = externalAreaCd2;
		this.externalExchange2 = externalExchange2;
		this.externalExtension2 = externalExtension2;
		this.externalAreaCd3 = externalAreaCd3;
		this.externalExchange3 = externalExchange3;
		this.externalExtension3 = externalExtension3;
		this.internalAreaCd1 = internalAreaCd1;
		this.internalExchange1 = internalExchange1;
		this.internalExtension1 = internalExtension1;
		this.internalAreaCd2 = internalAreaCd2;
		this.internalExchange2 = internalExchange2;
		this.internalExtension2 = internalExtension2;
		this.internalAreaCd3 = internalAreaCd3;
		this.internalExchange3 = internalExchange3;
		this.internalExtension3 = internalExtension3;
		this.externalFaxArea1 = externalFaxArea1;
		this.externalFaxExchange1 = externalFaxExchange1;
		this.externalFaxExtension1 = externalFaxExtension1;
		this.externalFaxArea2 = externalFaxArea2;
		this.externalFaxExchange2 = externalFaxExchange2;
		this.externalFaxExtension2 = externalFaxExtension2;
		this.externalFaxArea3 = externalFaxArea3;
		this.externalFaxExchange3 = externalFaxExchange3;
		this.externalFaxExtension3 = externalFaxExtension3;
		this.internalFaxArea1 = internalFaxArea1;
		this.internalFaxExchange1 = internalFaxExchange1;
		this.internalFaxExtension1 = internalFaxExtension1;
		this.internalFaxArea2 = internalFaxArea2;
		this.internalFaxExchange2 = internalFaxExchange2;
		this.internalFaxExtension2 = internalFaxExtension2;
		this.internalFaxArea3 = internalFaxArea3;
		this.internalFaxExchange3 = internalFaxExchange3;
		this.internalFaxExtension3 = internalFaxExtension3;
		this.dayLightSaveIndicator = dayLightSaveIndicator;
		this.renotifyTime = renotifyTime;
		this.renotifyDays = renotifyDays;
		this.deferredTime = deferredTime;
		this.nsTerminalId = nsTerminalId;
		this.terminalType = terminalType;
		this.terminalCloseOutTime = terminalCloseOutTime;
		this.haulageIndicator = haulageIndicator;
		this.idcsTerminalIndicator = idcsTerminalIndicator;
		this.sswTerminalIndicator = sswTerminalIndicator;
		this.hitchCheckIndicator = hitchCheckIndicator;
		this.station = station;

	}

	public Terminal(TerminalInd terminalInd) {
		super();
		this.terminalInd = terminalInd;
	}

	public Terminal() {
		super();
	}

	
}
