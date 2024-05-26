package com.nscorp.obis.dto;

import java.math.BigDecimal;
import java.sql.Timestamp;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class NorfolkSouthernEventLogDTO {
	private Long eventLogId;

	private String equipmentInit;

	private BigDecimal equipmentNumber;

	private String equipmentType;

	private String equipmentId;

	private String toEquipmentInit;

	private BigDecimal toEquipmentNumber;

	private String toEquipmentType;

	private String toEquipmentId;

	private String leInd;

	private String intchInd;

	private String hazInd;

	private Long driverId;

	private String evtCd;

	private Timestamp eventDateTime;

	private Long customerId;

	private Long serviceId;

	private Long terminalId;

	private String tofcCofcFg;

	private String reasonCd;

	private String trainNumber;

	private Integer trainOrigDay;

	private String trainInSegment;

	private Long drayCustId;

	private Timestamp rpDateTime;

	private String messagePriority;

	private String damageType;

	private String bondInd;

	private Timestamp inspDateTime;

	private String inspWorkerId;

	private String inspTemp;

	private String inspFlLvl;

	private String inspRunInd;

	private String setTemp;

	private String dmgeInd;

	private Long inspEvtLogId;

	private String craneId;

	private String maintTpCd;

	private String carInit;

	private BigDecimal carNumber;

	private String carType;

	private String chasInit;

	private BigDecimal chasNumber;

	private String chasType;

	private String chasId;

	private Long lotAreaId;

	private Long eqDmgeId;

	private String trackId;

	private String spPgmActCd;

	private String spPgmAmt;

	private String spPgmAmtTp;

	private String spPgmAmtAct;

	private String programName;

	private String ichgRoadName;

	private String ichgIchgName;

	private String carRoute;

	private Integer swListNr;

	private Integer lineNr;

	private Long blockId;

	private String carLdPos;

	private Long chrgId;

	private String movedWithBillingInd;

	private String passToLegacy;

	private Timestamp lclDateTime;

	private Timestamp stdDateTime;

	private String genFreightId;

	private String lsLvlCd;

	private Integer wbVersion;

	private Long switchId;

	private String tkrCd;
}
