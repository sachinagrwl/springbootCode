package com.nscorp.obis.domain;

import javax.persistence.*;

@Entity
@Table(name = "MRG_STN_TERM_HND")
@IdClass(MergeStationTermHandlePrimaryKeys.class)
public class MergeStationTermHandle extends AuditInfo {

	@Id
	@Column(name = "HANDLE_TERM_ID", columnDefinition = "Double(15)", nullable = true)
	private Long handleTermId;

	@Id
	@Column(name = "STN_XRF_ID", columnDefinition = "Double(15)", nullable = false)
	private Long stationId;

	@ManyToOne
	@JoinColumn(name = "STN_XRF_ID", nullable = false, insertable = false, updatable = false)
	private Station station;

	public MergeStationTermHandle() {
		super();
	}

	public Long getHandleTermId() {
		return handleTermId;
	}

	public void setHandleTermId(Long handleTermId) {
		this.handleTermId = handleTermId;
	}

	public Long getStationId() {
		return stationId;
	}

	public void setStationId(Long stationId) {
		this.stationId = stationId;
	}

	public Station getStation() {
		return station;
	}

	public void setStation(Station station) {
		this.station = station;
	}

	@Override
	public String toString() {
		return "MergeStationTermHandle [handleTermId=" + handleTermId + ", stationId=" + stationId + ", station=" + station
				+ ", getUversion()=" + getUversion() + ", getCreateUserId()=" + getCreateUserId()
				+ ", getCreateDateTime()=" + getCreateDateTime() + ", getUpdateUserId()=" + getUpdateUserId()
				+ ", getUpdateDateTime()=" + getUpdateDateTime() + ", getUpdateExtensionSchema()="
				+ getUpdateExtensionSchema() + "]";
	}

}
