package com.nscorp.obis.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.nscorp.obis.domain.NotepadEntry;
import com.nscorp.obis.domain.Station;
import com.nscorp.obis.domain.VoiceNotify2;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class VoiceNotifyDTO {

	private Long notifyQueueId;
	private Long notifyCustId;
	private Long termId;
	private String eventCd;
	private String notifyStat;
	private String equipId;
	private String equipTp;
	private BigDecimal equipNbr;
	private String equipInit;
	private String chasInit;
	private BigDecimal chasNbr;
	private String chasTp;
	private String chasId;
	private String dmgInd;
	private String evtDesc;
	private String customerName;
	private List<NotepadEntry> note;

	private String personField;

	private String notifyMethod;

	private Integer renotCount;

	private String notifyName;

	private String notifyArea;

	private Integer notifyPrefix;

	private Integer notifySufix;

	private Integer notifyExit;

	private Long driverId;

	private String trainName;

	private Long drayCust;

	@JsonFormat(pattern="MM-dd-yyyy HH:mm:ss")
	private LocalDateTime localDateTm;

	private String emptyCd;

	private String shipVesselName;

	private String bookNr;

	private String bondedInd;

	private Integer landingWeight;

	private String hazShip;

	private String notifyOvrdName;

	private Integer notifyOvrdExchange;

	private Integer notifyOvrdBase;

	private Integer notifyOvrdArea;

	private Integer notifyOvrdExt;

	private String opSpell;

	private String notifyReason;
	private Station stationName;
	
	@JsonFormat(pattern="MM-dd-yyyy HH:mm:ss")
	private LocalDateTime updateDtTm;

	private String updateExtSchema;
	private String pickupNr;
	private Long shipCust;
	private VoiceNotify2 voiceNotify2;
	private GenericCodeUpdateDTO genericCodeUpdate;
}
