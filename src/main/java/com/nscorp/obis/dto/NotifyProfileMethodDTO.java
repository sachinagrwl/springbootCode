package com.nscorp.obis.dto;

import com.nscorp.obis.domain.NotificationMethod;
import com.nscorp.obis.domain.NotificationOrder;
import com.nscorp.obis.domain.NotificationType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class NotifyProfileMethodDTO extends AuditInfoDTO {
	@Min(value = 1, message = "Notify Method Id value must be greater than 0")
    @Digits(integer=15, fraction=0, message="Notify Method Id should not have more than 15 digits. ")
	@Schema(required = true,description="The Id of the notify Profile.", example="39715249851407")
	private Long notifyMethodId;	
	
	@Schema(required = false,description="This value controls hoe frequently notifications from this profile will be sent. For FAX notifications, it also controls whether or not multiple notifications will be grouped together onto a single fax to a customer or sent as multiple faxes.", example="II")
	private NotificationType notificationType;
	
	@Schema(required = false,description="Notification method options: : EDI- Electronic, FAX- Facsimile, VOICE- Phone Call", example="FAX")
	private NotificationMethod notificationMethod;
	
	@Schema(required = false,description="This indicates the order of notification.", example="P")
	private NotificationOrder notificationOrder;
	
	@Size(min=0, max=30, message = "Notification name should not have more than 30 characters.")
	@Schema(required = false,description="This is a name of the person to be notified.", example="SEAL APPLIED ")
	private String notificationName;
	
	@Pattern(regexp="^(?:Y|N)$",message="Only 'Y' or 'N' allowed")
	@Schema(required = false,description="The auto Renotify of the notify Profile.")
	private String autoRenotify;
	
	@Size(min=0, max=15, message = "EDI Box should not have more than 15 characters.")
	@Schema(required = false,description="This is the name of the EDI computer name.")
	private String ediBox;
	
	@Pattern(regexp="^(?:Y|N)$",message="Only 'Y' or 'N' allowed")
	@Schema(required = false,description="This indicates if microwave is applicable. Y=Yes, N=No", example="Y")
	private String microwaveIndicator;
	
	@Min(value = 100, message = "Notify Area code must be in between 100 to 999")
	@Pattern(regexp = "[0-9]{3}",message = "Notify Area Code must be a 3-digit positive number. ")
	@Schema(required =false,description="The notify area code of the notify Profile.", example="904")
	private String notifyAreaCode;
	
	@Size(min=0, max=40, message = "Email should not have more than 40 characters.")
	@Schema(required = false,description="The notify email of the notify Profile.")
	private String notifyEmail;
	
	@Size(min=0, max=20, message = "Email id should not have more than 20 characters.")
	@Schema(required = false,description="The notify email Id of the notify Profile.")
	private String notifyEmailId;
	
	@Min(value = 0, message = "Notify Phone Extension value must be positive")
	@Max(value = 32767)
	@Digits(integer = 5, fraction = 0)
	@Schema(required = false,description="This is the extension of the notification phone number.")
	private Integer notifyPhoneExtension;
	
	@Min(value = 0,  message = "Notify prefix must be in between 0 to 999")
	@Pattern(regexp = "^\\d{3}$", message = "notify prefix should be 3 digit number")
	@Schema(required = false,description="This is the prefix of the notification phone number.", example="229")
	private String notifyPrefix;
	
	@Min(value = 0,  message = "Notify suffix must be in between 0 to 9999")
	@Pattern(regexp = "^\\d{4}$",message = "notify suffix should be 4 digit number")
	@Schema(required = false,description="This is the suffix of the notification phone number.", example="1421")
	private String notifySuffix;
}
