package com.nscorp.obis.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

@Entity
@Table(name="NTFY_QUEUE")
public class NotifyQueue extends AuditInfo {
    @Id
    @Column(name = "NTFY_QUEUE_ID", columnDefinition = "double", nullable = false)
    private Long ntfyQueueId;
	@Column(name = "EVT_LOG_ID", columnDefinition = "double", nullable = true)
	private Long evtLogId;
	@Column(name = "NTFY_STAT", columnDefinition = "char(4)", nullable = true)
	private String notifyStat;
	@Column(name = "NTFY_MTHD_ID", columnDefinition = "double", nullable = true)
	private Long notifyMethodId;
	@Column(name = "RETRY_CNT", columnDefinition = "smallint", nullable = true)
	private Integer retryCount;
	@Column(name = "RENOT_CNT", columnDefinition = "integer(10)", nullable = true)
	private Integer renotifyCnt;
	@Column(name = "CONFIRM_ID", columnDefinition = "double", nullable = true)
	private Long confirmId;
    @Column(name = "TERM_ID", columnDefinition = "double", nullable = true)
    private double termId;
	@Column(name = "TRAIN_NR", columnDefinition = "char(4)", nullable = true)
	private String trainNr;
	@Column(name = "NTFY_CUST_ID", columnDefinition = "double", nullable = true)
	private Long notifyCustId;
	@Column(name = "NTFY_TP", columnDefinition = "char(6)", nullable = true)
	private String notifyType;
    @Column(name = "EVT_CD", columnDefinition = "char(4)", nullable = true)
    private String eventCode;
	@Column(name = "NTFY_MTHD", columnDefinition = "char(6)", nullable = true)
	private String notifyMethod;
	@Column(name = "TRACK_ID", columnDefinition = "char(4)", nullable = true)
	private String trackId;
    @Column(name = "UPD_DT_TM", columnDefinition = "TIMESTAMP", nullable = true)
    private Timestamp updateDateTime;
	@Column(name = "PERSON_NTFIED", columnDefinition = "char(30)", nullable = true)
	private String personNotified;
	@Column(name = "NTFY_REASON_CD", columnDefinition = "char(20)", nullable = true)
	private String notifyReasonCode;

	public String getPersonNotified() {
		return personNotified;
	}

	public void setPersonNotified(String personNotified) {
		this.personNotified = personNotified;
	}

	public Long getNtfyQueueId() {
		return ntfyQueueId;
	}

	public void setNtfyQueueId(Long ntfyQueueId) {
		this.ntfyQueueId = ntfyQueueId;
	}

	public Long getEvtLogId() {
		return evtLogId;
	}

	public void setEvtLogId(Long evtLogId) {
		this.evtLogId = evtLogId;
	}

	public Long getNotifyMethodId() {
		return notifyMethodId;
	}

	public void setNotifyMethodId(Long notifyMethodId) {
		this.notifyMethodId = notifyMethodId;
	}

	public Integer getRetryCount() {
		return retryCount;
	}

	public void setRetryCount(Integer retryCount) {
		this.retryCount = retryCount;
	}

	public Long getConfirmId() {
		return confirmId;
	}

	public void setConfirmId(Long confirmId) {
		this.confirmId = confirmId;
	}

	public String getTrainNr() {
		return trainNr;
	}

	public void setTrainNr(String trainNr) {
		this.trainNr = trainNr;
	}

	public Long getNotifyCustId() {
		return notifyCustId;
	}

	public void setNotifyCustId(Long notifyCustId) {
		this.notifyCustId = notifyCustId;
	}

	public String getNotifyType() {
		return notifyType;
	}

	public void setNotifyType(String notifyType) {
		this.notifyType = notifyType;
	}

	public String getNotifyMethod() {
		return notifyMethod;
	}

	public void setNotifyMethod(String notifyMethod) {
		this.notifyMethod = notifyMethod;
	}

	public String getTrackId() {
		return trackId;
	}

	public void setTrackId(String trackId) {
		this.trackId = trackId;
	}

	public Long getNotifyQueueId() {
		return ntfyQueueId;
	}
	public void setNotifyQueueId(Long notifyQueueId) {
		this.ntfyQueueId = notifyQueueId;
	}
	public String getNotifyStat() {
		return notifyStat;
	}
	public void setNotifyStat(String notifyStat) {
		this.notifyStat = notifyStat;
	}
	public double getTermId() {
		return termId;
	}
	public void setTermId(double termId) {
		this.termId = termId;
	}
	public Integer getRenotifyCnt() {
		return renotifyCnt;
	}
	public void setRenotifyCnt(Integer renotifyCnt) {
		this.renotifyCnt = renotifyCnt;
	}
	public String getEventCode() {
		return eventCode;
	}
	public void setEventCode(String eventCode) {
		this.eventCode = eventCode;
	}
	public Timestamp getUpdateDateTime() {
		return updateDateTime;
	}
	public void setUpdateDateTime(Timestamp updateDateTime) {
		this.updateDateTime = updateDateTime;
	}

	public String getNotifyReasonCode() {
		return notifyReasonCode;
	}

	public void setNotifyReasonCode(String notifyReasonCode) {
		this.notifyReasonCode = notifyReasonCode;
	}

	public NotifyQueue(String uversion, String createUserId, Timestamp createDateTime, String updateUserId,
					   Timestamp updateDateTime, String updateExtensionSchema, Long notifyQueueId, double termId,
					   String notifyStat, Integer renotifyCnt, String eventCode, Timestamp updateDateTime1) {
		super(uversion, createUserId, createDateTime, updateUserId, updateDateTime1, updateExtensionSchema);
		this.ntfyQueueId = notifyQueueId;
		this.termId = termId;
		this.notifyStat = notifyStat;
		this.renotifyCnt = renotifyCnt;
		this.eventCode = eventCode;
		this.updateDateTime = updateDateTime1;
	}
	public NotifyQueue() {
		super();
		// TODO Auto-generated constructor stub
	}
	public NotifyQueue(String uversion, String createUserId, Timestamp createDateTime, String updateUserId,
			Timestamp updateDateTime, String updateExtensionSchema) {
		super(uversion, createUserId, createDateTime, updateUserId, updateDateTime, updateExtensionSchema);
		// TODO Auto-generated constructor stub
	}

	public NotifyQueue(String uversion, String createUserId, Timestamp createDateTime, String updateUserId, Timestamp updateDateTime, String updateExtensionSchema, Long ntfyQueueId, Long evtLogId, String notifyStat, Long notifyMethodId, Integer retryCount, Integer renotifyCnt, Long confirmId, double termId, String trainNr, Long notifyCustId, String notifyType, String eventCode, String notifyMethod, String trackId, Timestamp updateDateTime1, String personNotified, String notifyReasonCode) {
		super(uversion, createUserId, createDateTime, updateUserId, updateDateTime, updateExtensionSchema);
		this.ntfyQueueId = ntfyQueueId;
		this.evtLogId = evtLogId;
		this.notifyStat = notifyStat;
		this.notifyMethodId = notifyMethodId;
		this.retryCount = retryCount;
		this.renotifyCnt = renotifyCnt;
		this.confirmId = confirmId;
		this.termId = termId;
		this.trainNr = trainNr;
		this.notifyCustId = notifyCustId;
		this.notifyType = notifyType;
		this.eventCode = eventCode;
		this.notifyMethod = notifyMethod;
		this.trackId = trackId;
		this.updateDateTime = updateDateTime1;
		this.personNotified = personNotified;
		this.notifyReasonCode = notifyReasonCode;
	}

	public NotifyQueue(String uversion, String createUserId, Timestamp createDateTime, String updateUserId, Timestamp updateDateTime, String updateExtensionSchema, Long ntfyQueueId, double termId, String notifyStat, Integer renotifyCnt, String eventCode, Timestamp updateDateTime1, String personNotified, String notifyReasonCode) {
		super(uversion, createUserId, createDateTime, updateUserId, updateDateTime, updateExtensionSchema);
		this.ntfyQueueId = ntfyQueueId;
		this.termId = termId;
		this.notifyStat = notifyStat;
		this.renotifyCnt = renotifyCnt;
		this.eventCode = eventCode;
		this.updateDateTime = updateDateTime1;
		this.personNotified = personNotified;
		this.notifyReasonCode = notifyReasonCode;
	}

	@Override
	public String toString() {
		return "NotifyQueue{" +
				"ntfyQueueId=" + ntfyQueueId +
				", evtLogId=" + evtLogId +
				", notifyStat='" + notifyStat + '\'' +
				", notifyMethodId=" + notifyMethodId +
				", retryCount=" + retryCount +
				", renotifyCnt=" + renotifyCnt +
				", confirmId=" + confirmId +
				", termId=" + termId +
				", trainNr='" + trainNr + '\'' +
				", notifyCustId=" + notifyCustId +
				", notifyType='" + notifyType + '\'' +
				", eventCode='" + eventCode + '\'' +
				", notifyMethod='" + notifyMethod + '\'' +
				", trackId='" + trackId + '\'' +
				", updateDateTime=" + updateDateTime +
				", personNotified='" + personNotified + '\'' +
				", notifyReasonCode='" + notifyReasonCode + '\'' +
				'}';
	}


}