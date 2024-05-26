package com.nscorp.obis.domain;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;

@SuppressWarnings("serial")
public class StationTermHandlePrimaryKeys implements Serializable {

	@Column(name = "HANDLE_TERM_ID", columnDefinition = "Double(15)", nullable = true)
	private Long handleTermId;

	@Column(name = "STN_XRF_ID ", columnDefinition = "Double(15)", nullable = true)
	private Long stationId;
	

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

	@Override
	public String toString() {
		return "StationTermHandlePrimaryKeys [handleTermId=" + handleTermId + ", stationId=" + stationId + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(handleTermId, stationId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;

		StationTermHandlePrimaryKeys stationTermHandlePrimaryKeys = (StationTermHandlePrimaryKeys) obj;

		return Objects.equals(handleTermId, stationTermHandlePrimaryKeys.handleTermId)
				&& Objects.equals(stationId, stationTermHandlePrimaryKeys.stationId);
	}

}
