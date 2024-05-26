package com.nscorp.obis.domain;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "STN_RESTRICT")
@IdClass(StationRestrictionPrimaryKeys.class)
public class StationRestriction extends AuditInfo {

	@Id
	@Column(name = "STN_XRF_ID", length = 15, columnDefinition = "double", nullable = false)
	private long stationCrossReferenceId;
	
	@Id
	@Column(name = "CAR_TP", columnDefinition = "char(4)", nullable = false)
	private String carType;
	
	@Id
	@Column(name = "FREIGHT_TP", columnDefinition = "char(4)", nullable = false)
	private String freightType;
	
//	@ManyToOne(fetch = FetchType.LAZY, optional=false)
//	@ManyToOne(optional = false)
//	@JoinColumn(name = "STN_XRF_ID", insertable = false, updatable = false)
//	@JoinColumn(name = "TERM_ID", nullable = false)
//	private Station station;

	public StationRestriction() {
		super();
		// TODO Auto-generated constructor stub
	}

	public StationRestriction(String uversion, String createUserId, Timestamp createDateTime, String updateUserId,
			Timestamp updateDateTime, String updateExtensionSchema) {
		super(uversion, createUserId, createDateTime, updateUserId, updateDateTime, updateExtensionSchema);
		// TODO Auto-generated constructor stub
	}

	public StationRestriction(String uversion, String createUserId, Timestamp createDateTime, String updateUserId,
			Timestamp updateDateTime, String updateExtensionSchema, long stationCrossReferenceId, String carType,
			String freightType/*, Station station*/) {
		super(uversion, createUserId, createDateTime, updateUserId, updateDateTime, updateExtensionSchema);
		this.stationCrossReferenceId = stationCrossReferenceId;
		this.carType = carType;
		this.freightType = freightType;
//		this.station = station;
	}

	public long getStationCrossReferenceId() {
		return stationCrossReferenceId;
	}

	public void setStationCrossReferenceId(long stationCrossReferenceId) {
		this.stationCrossReferenceId = stationCrossReferenceId;
	}

	public String getCarType() {
		if(carType != null) {
			return carType.trim();
		}
		else {
			return carType;
		}
	}

	public void setCarType(String carType) {
		this.carType = carType;
	}

	public String getFreightType() {
		if(freightType != null) {
			return freightType.trim();
		}
		else {
			return freightType;
		}
	}

	public void setFreightType(String freightType) {
		this.freightType = freightType;
	}

//	public Station getStation() {
//		return station;
//	}
//
//	public void setStation(Station station) {
//		this.station = station;
//	}
	
	
}
