package com.nscorp.obis.dto;

import javax.validation.constraints.Digits;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import org.hibernate.validator.constraints.Length;

import java.sql.Timestamp;

@Data
@EqualsAndHashCode(callSuper = false)
public class NotifyQueueDTO extends AuditInfoDTO{
	
	private Long ntfyQueueId;

    @Digits(integer = 15, fraction = 0, message = "Event Log Id should not have more than 15 digits. ")
    @Schema(required = false, description = "This is the Event Log Id.", example = "2.1400850481162E13")
    private Long evtLogId;
	
    @Length(max=4, message ="notifyStat size should not be more than {max}")
    private String notifyStat;

    @Digits(integer = 15, fraction = 0, message = "Notify Method Id should not have more than 15 digits. ")
    @Schema(required = false, description = "This is the Notify Method Id.", example = "5.0579518012219E13")
    private Long notifyMethodId;

    @Digits(integer = 5, fraction =0, message ="Only 5 value is allowed")
    private Integer retryCount;

    @Digits(integer = 4, fraction =0, message ="Only 4 value is allowed")
    private Integer renotifyCnt;

    @Digits(integer = 15, fraction = 0, message = "Confirm Id should not have more than 15 digits. ")
    @Schema(required = false, description = "This is the Confirm Id.")
    private Long confirmId;

    private double termId;

    @Length(max=4, message ="trainNr size should not be more than {max}")
    private String trainNr;

    @Digits(integer = 15, fraction = 0, message = "Notify Cust Id should not have more than 15 digits. ")
    @Schema(required = false, description = "This is the Notify Cust Id.", example = "9.9990010119581E13")
    private Long notifyCustId;

    @Length(max=6, message ="notifyType size should not be more than {max}")
    private String notifyType;

    @Length(max=4, message ="eventCode size should not be more than {max}")
    private String eventCode;

    @Length(max=6, message ="notifyMethod size should not be more than {max}")
    private String notifyMethod;

    @Length(max=4, message ="trackId size should not be more than {max}")
    private String trackId;
    private Timestamp updateDateTime;

    @Length(max=30, message ="notifyReasonCode size should not be more than {max}")
    private String personNotified;

    @Length(max=20, message ="notifyReasonCode size should not be more than {max}")
    private String notifyReasonCode;
    
}