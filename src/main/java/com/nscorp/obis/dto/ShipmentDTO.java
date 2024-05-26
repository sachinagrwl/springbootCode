package com.nscorp.obis.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = false)
public class ShipmentDTO {

	private Long svcId;
	private String loadEmptyInd;
	private Long onlDest;
	private String ntfyOvrdNm;
	private Integer ntfyOvrdAreaCd;
	private Integer ntfyOvrdExch;
	private Integer ntfyOvrdBase;
	private Integer ntfyOvrdExt;
	private String repoInd;
	private Long shipCust;
	private CustomerShipmentDTO customer;
	private StationShipmentDTO station;
	private BigDecimal wbSerNr;
	private Long customerToNotify;

}
