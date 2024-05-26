package com.nscorp.obis.dto;

import com.nscorp.obis.domain.NotificationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class NotificationTypesResponseDTO {

	private String code;
	
	private NotificationType description;
	
	private List<String> invalidEventCodes;
	
}
