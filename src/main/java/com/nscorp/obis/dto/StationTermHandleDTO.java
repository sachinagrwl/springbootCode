package com.nscorp.obis.dto;

import javax.validation.constraints.NotNull;

import io.swagger.v3.oas.annotations.media.Schema;

public class StationTermHandleDTO extends AuditInfoDTO {

	@NotNull
	@Schema(required = true, description = "Defined within the INTERMODAL.STATION_XRF.TERM_ID field ")
	private Long handleTermId;
	@NotNull
	@Schema(required = true, description = "Defined within the INTERMODAL.TERMINAL.TERM_ID field ")
	private Long stationId;
	
	private StationDTO station;
	

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
	
	public StationDTO getStation() {
		return station;
	}

	public void setStation(StationDTO station) {
		this.station = station;
	}

	@Override
	public String toString() {
		return "StationTermHandleDTO [handleTermId=" + handleTermId + ", stationId=" + stationId + ", station="
				+ station + ", getUversion()=" + getUversion() + ", getCreateUserId()=" + getCreateUserId()
				+ ", getCreateDateTime()=" + getCreateDateTime() + ", getUpdateUserId()=" + getUpdateUserId()
				+ ", getUpdateDateTime()=" + getUpdateDateTime() + ", getUpdateExtensionSchema()="
				+ getUpdateExtensionSchema() + "]";
	}

	

}
