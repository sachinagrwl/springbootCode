package com.nscorp.obis.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ACT_NTFY_PRF")
public class BaseActivityNotifyProfile {
	@Id
	@Column(name = "NTFY_PRF_ID", columnDefinition = "Double(15)", nullable = false)
	private Long notifyProfileId;

	@Column(name = "ACTIVITY_ID", columnDefinition = "smallint(5)", nullable = true)
	private Integer activityId;

	@Column(name = "NTFY_TERM_ID", length = 15, columnDefinition = "Double", nullable = true)
	private Long notifyTerminalId;

	@Column(name = "EVT_CD", columnDefinition = "char(4)")
	String eventCode;

	@Column(name = "DAY_OF_WEEK", columnDefinition = "char(7)", nullable = true)
	String dayOfWeek;

	@Column(name = "SHIFT", columnDefinition = "char(3)", nullable = true)
	String shift;

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

	public String getEventCode() {
		return eventCode;
	}

	public void setEventCode(String eventCode) {
		this.eventCode = eventCode;
	}

	public String getDayOfWeek() {
		return dayOfWeek;
	}

	public void setDayOfWeek(String dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
	}

	public String getShift() {
		return shift;
	}

	public void setShift(String shift) {
		this.shift = shift;
	}

	@Override
	public String toString() {
		return "BaseActivityNotifyProfile [notifyProfileId=" + notifyProfileId + ", activityId=" + activityId
				+ ", notifyTerminalId=" + notifyTerminalId + ", eventCode=" + eventCode + ", dayOfWeek=" + dayOfWeek
				+ ", shift=" + shift + "]";
	}

}
