package com.nscorp.obis.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class NotifyProfileMethodResponseDTO {
	
	@Schema(required = false,description="This is a name of the person to be notified.", example="SEAL APPLIED ")
	private String notificationName;
	
	@Schema(required = false,description="Notification method options: : EDI- Electronic, FAX- Facsimile, VOICE- Phone Call", example="FAX")
	private String notificationMethod;
	
	@Schema(required = false,description="This value controls hoe frequently notifications from this profile will be sent. For FAX notifications, it also controls whether or not multiple notifications will be grouped together onto a single fax to a customer or sent as multiple faxes.", example="II")
	private String notificationType;
	
	@Schema(required = false,description="This is the name of the EDI computer name.")
	private String ediBox;
	
	@Schema(required =false,description="The notify area code of the notify Profile.", example="904")
	private Integer notifyAreaCode;

	@Schema(required = false,description="This is the prefix of the notification phone number.", example="229")
	private Integer notifyPrefix;
	
	@Schema(required = false,description="This is the suffix of the notification phone number.", example="1421")
	private Integer notifySuffix;
	
	@Schema(required = false,description="This is the extension of the notification phone number.")
	private Integer notifyExit;
	
	@Schema(required = false,description="This indicates the order of notification.", example="P")
	private String notificationOrder;
	
	@Schema(required = false,description="This indicates if microwave is applicable. Y=Yes, N=No", example="Y")
	private Character microwaveIndicator;

}
