package com.nscorp.obis.domain;

import java.sql.Time;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name="DEST_SET")
public class DestinationSetting  extends AuditInfo {
	@Id
	@Column(name = "DEST_ID", columnDefinition = "double", nullable = false)
	private Double destId;

	@ManyToOne
	@JoinColumn(name = "NS_DEST_TERM_ID", columnDefinition = "double", referencedColumnName = "TERM_ID", nullable = true, insertable = true, updatable = true)
	private Station nsDestTermId;

	@ManyToOne
	@JoinColumn(name = "OFFLINE_DEST", columnDefinition = "double", referencedColumnName = "TERM_ID", nullable = true, insertable = true, updatable = true)
	private Station offlineDest;
	
	@Column(name = "TOFC_ALLOWED_IND", columnDefinition = "char(1)", nullable = false)
	private String tofcAllowedInd;
	
	@Column(name = "COFC_ALLOWED_IND", columnDefinition = "char(1)", nullable = false)
	private String cofcAllowedInd;
	
	@Column(name = "ONLINE_MILEAGE", length = 5 , columnDefinition = "Smallint", nullable = false)
	private Integer onlineMileage; 
	
	@Column(name = "OFFLINE_MILEAGE", length = 5 , columnDefinition = "Smallint", nullable = false)
	private Integer offlineMileage;
	
	@Column(name = "BLOCK_ID", columnDefinition = "double", nullable = false)
	private Long blockId;
	
	@Column(name = "ROUTE", columnDefinition = "char(110)", nullable = false)
	@Convert(converter=ListToStringConverter.class)
	private List<String> route;
	
	@Column(name = "TRANSIT_DAYS", columnDefinition = "char(2)", nullable = false)
	private String transitDays;
	
	@Column(name = "PLACE_TM", columnDefinition = "time", nullable = false)
	private Time placeTm; 
	
	@Column(name = "INCLUDE_EXCLUDE", columnDefinition = "char(1)", nullable = false)
	private String includeExclude;

	public DestinationSetting() {
		super();
	}

	public DestinationSetting(Double destId, Station nsDestTermId, Station offlineDest, String tofcAllowedInd,
			String cofcAllowedInd, Integer onlineMileage, Integer offlineMileage, Long blockId, List<String> route,
			String transitDays, Time placeTm, String includeExclude) {
		super();
		this.destId = destId;
		this.nsDestTermId = nsDestTermId;
		this.offlineDest = offlineDest;
		this.tofcAllowedInd = tofcAllowedInd;
		this.cofcAllowedInd = cofcAllowedInd;
		this.onlineMileage = onlineMileage;
		this.offlineMileage = offlineMileage;
		this.blockId = blockId;
		this.route = route;
		this.transitDays = transitDays;
		this.placeTm = placeTm;
		this.includeExclude = includeExclude;
	}

	public Double getDestId() {
		return destId;
	}

	public void setDestId(Double destId) {
		this.destId = destId;
	}

	public Station getNsDestTermId() {
		return nsDestTermId;
	}

	public void setNsDestTermId(Station nsDestTermId) {
		this.nsDestTermId = nsDestTermId;
	}

	public Station getOfflineDest() {
		return offlineDest;
	}

	public void setOfflineDest(Station offlineDest) {
		this.offlineDest = offlineDest;
	}

	public String getTofcAllowedInd() {
		return tofcAllowedInd;
	}

	public void setTofcAllowedInd(String tofcAllowedInd) {
		this.tofcAllowedInd = tofcAllowedInd;
	}

	public String getCofcAllowedInd() {
		return cofcAllowedInd;
	}

	public void setCofcAllowedInd(String cofcAllowedInd) {
		this.cofcAllowedInd = cofcAllowedInd;
	}

	public Integer getOnlineMileage() {
		return onlineMileage;
	}

	public void setOnlineMileage(Integer onlineMileage) {
		this.onlineMileage = onlineMileage;
	}

	public List<String> getRoute() {
		return route;
	}

	public Integer getOfflineMileage() {
		return offlineMileage;
	}

	public void setOfflineMileage(Integer offlineMileage) {
		this.offlineMileage = offlineMileage;
	}

	public Long getBlockId() {
		return blockId;
	}

	public void setBlockId(Long blockId) {
		this.blockId = blockId;
	}
	public void setRoute(List<String> route) {
		this.route = route;
	}

	public String getTransitDays() {
		return transitDays;
	}

	public void setTransitDays(String transitDays) {
		this.transitDays = transitDays;
	}

	public Time getPlaceTm() {
		return placeTm;
	}

	public void setPlaceTm(Time placeTm) {
		this.placeTm = placeTm;
	}

	public String getIncludeExclude() {
		return includeExclude;
	}

	public void setIncludeExclude(String includeExclude) {
		this.includeExclude = includeExclude;
	}
}
