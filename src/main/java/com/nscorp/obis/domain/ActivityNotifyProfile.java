package com.nscorp.obis.domain;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Table;

// @MappedSuperclass
@Entity
@Table(name = "ACT_NTFY_PRF")
public class ActivityNotifyProfile extends NotifyProfile {

	@Id
	@Column(name = "NTFY_PRF_ID", columnDefinition = "Double(15)", nullable = false)
	private Long notifyProfileId;

	@Column(name = "ACTIVITY_ID", columnDefinition = "smallint(5)", nullable = true)
	private Integer activityId;

	@Column(name = "NTFY_TERM_ID", length = 15, columnDefinition = "Double", nullable = true)
	private Long notifyTerminalId;

	public Long getNotifyProfileId() {
		return notifyProfileId;
	}

	public void setNotifyProfileId(Long notifyProfileId) {
		this.notifyProfileId = notifyProfileId;
	}

	public Integer getActivityId() {
		return activityId;
	}

	public void setActivityId(Integer activityId) {
		this.activityId = activityId;
	}

	public Long getNotifyTerminalId() {
		return notifyTerminalId;
	}

	public void setNotifyTerminalId(Long notifyTerminalId) {
		this.notifyTerminalId = notifyTerminalId;
	}

	public ActivityNotifyProfile(String uversion, String createUserId, Timestamp createDateTime, String updateUserId,
			Timestamp updateDateTime, String updateExtensionSchema, long notifyProfileId, String eventCode,
			List<DayOfWeek> dayOfWeek, List<Shift> shift, List<NotifyProfileMethod> notifyProfileMethods,
			Long notifyProfileId2, Integer activityId) {
		super(uversion, createUserId, createDateTime, updateUserId, updateDateTime, updateExtensionSchema,
				notifyProfileId, eventCode, dayOfWeek, shift, notifyProfileMethods);
		notifyProfileId = notifyProfileId2;
		this.activityId = activityId;
	}

	public ActivityNotifyProfile() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ActivityNotifyProfile(String uversion, String createUserId, Timestamp createDateTime, String updateUserId,
			Timestamp updateDateTime, String updateExtensionSchema, long notifyProfileId, String eventCode,
			List<DayOfWeek> dayOfWeek, List<Shift> shift, List<NotifyProfileMethod> notifyProfileMethods) {
		super(uversion, createUserId, createDateTime, updateUserId, updateDateTime, updateExtensionSchema,
				notifyProfileId, eventCode, dayOfWeek, shift, notifyProfileMethods);
		// TODO Auto-generated constructor stub
	}

	public ActivityNotifyProfile(String uversion, String createUserId, Timestamp createDateTime, String updateUserId,
			Timestamp updateDateTime, String updateExtensionSchema) {
		super(uversion, createUserId, createDateTime, updateUserId, updateDateTime, updateExtensionSchema);
		// TODO Auto-generated constructor stub
	}

}
