package com.nscorp.obis.dto;

import java.util.List;

import com.nscorp.obis.domain.CustomerIndex;
import com.nscorp.obis.domain.CustomerNickname;
import com.nscorp.obis.domain.CustomerNotifyProfile;
import com.nscorp.obis.domain.NorfolkSouthernEventLog;
import com.nscorp.obis.domain.NotifyQueue;
import com.nscorp.obis.domain.Shipment;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class NotifyQueueRetryDTO {

	private NotifyQueue notifyQueue;
	
	private List<NotifyQueue> notifyQueueList;
	
	private NorfolkSouthernEventLog norfolkSouthernEventLog;
	
	private Shipment shipment;
	
	private CustomerIndex customerIndex;
	
	private CustomerNotifyProfile customerNotifyProfile;
	
	private CustomerNickname customerNickname;
	
	private List<CustomerNotifyProfileDTO> customerNotifyProfileList;
}
