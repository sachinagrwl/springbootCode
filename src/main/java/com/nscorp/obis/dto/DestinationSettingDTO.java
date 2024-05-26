package com.nscorp.obis.dto;

import java.sql.Time;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
@Data
@EqualsAndHashCode(callSuper = false)
public class DestinationSettingDTO extends AuditInfoDTO {
	private Double destId; 
	private StationDTO NsDestTermId;
	private StationDTO offlineDest;
	private String TofcAllowedInd;
	private String cofcAllowedInd;
	private Integer onlineMileage; 
	private Integer offlineMileage;
	private Long blockId; 
	private List<String> route;
	private String transitDays;
	private Time placeTm; 
	private String includeExclude;
}
