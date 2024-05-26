package com.nscorp.obis.domain;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@MappedSuperclass
public abstract class NotifyProfile extends AuditInfo{
 	
	@Id
	@Column(name="NTFY_PRF_ID", length = 15, columnDefinition = "Double", nullable=false)
	private Long notifyProfileId;
	
	@Column(name="EVT_CD",columnDefinition = "char(4)")
	String eventCode;
	
	@Convert(converter = DaysOfWeekConverter.class)
	@Column(name="DAY_OF_WEEK",columnDefinition = "char(7)", nullable = true)
	List<DayOfWeek> dayOfWeek;
	
	@Convert(converter=ShiftConverter.class)
	@Column(name="SHIFT",columnDefinition = "char(3)", nullable = true)
	List<Shift> shift;
   
	@OneToMany(cascade = {CascadeType.PERSIST,CascadeType.REFRESH})
	@JoinTable(name="NTFY_PRF_XRF",
	joinColumns=@JoinColumn(name="NTFY_PRF_ID"),
	inverseJoinColumns=@JoinColumn(name="NTFY_MTHD_ID")
	)
	List<NotifyProfileMethod> notifyProfileMethods;

	public Long getNotifyProfileId() {
		return notifyProfileId;
	}
	public void setNotifyProfileId(Long notifyProfileId) {
		this.notifyProfileId = notifyProfileId;
	}
	public String getEventCode() {
		return eventCode;
	}
	public void setEventCode(String eventCode) {
		this.eventCode = eventCode;
	}
	public List<DayOfWeek> getDayOfWeek() {
		return dayOfWeek;
	}
	public void setDayOfWeek(List<DayOfWeek> dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
	}
	public List<Shift> getShift() {
		return shift;
	}
	public void setShift(List<Shift> shift) {
		this.shift = shift;
	}
	public List<NotifyProfileMethod> getNotifyProfileMethods() {
		return notifyProfileMethods;
	}
	public void setNotifyProfileMethods(List<NotifyProfileMethod> notifyProfileMethods) {
		this.notifyProfileMethods = notifyProfileMethods;
	}
	public NotifyProfile(String uversion, String createUserId, Timestamp createDateTime, String updateUserId,
			Timestamp updateDateTime, String updateExtensionSchema, long notifyProfileId, String eventCode,
			List<DayOfWeek> dayOfWeek, List<Shift> shift, List<NotifyProfileMethod> notifyProfileMethods) {
		super(uversion, createUserId, createDateTime, updateUserId, updateDateTime, updateExtensionSchema);
		this.notifyProfileId = notifyProfileId;
		this.eventCode = eventCode;
		this.dayOfWeek = dayOfWeek;
		this.shift = shift;
		this.notifyProfileMethods = notifyProfileMethods;
	}
	public NotifyProfile() {
		super();
	}

	public NotifyProfile(String uversion, String createUserId, Timestamp createDateTime, String updateUserId,
			Timestamp updateDateTime, String updateExtensionSchema) {
		super(uversion, createUserId, createDateTime, updateUserId, updateDateTime, updateExtensionSchema);
		
	}
	
}
