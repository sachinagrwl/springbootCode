package com.nscorp.obis.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

@Entity
@Table(name = " NTFY_PRF_MTHD")
public class NotifyProfileMethod extends AuditInfo {
	@Id
	@Column(name="NTFY_MTHD_ID", columnDefinition = "double(8)", nullable =false) 
	private Long notifyMethodId;	
	
	@Column(name="NTFY_TP", columnDefinition = "char(2)", nullable = true)
	private NotificationType notificationType;

	
	@Column(name="NTFY_MTHD", columnDefinition = "char(6)", nullable = true)
	private NotificationMethod notificationMethod;
	
	@Column(name="NTFY_ORDER", columnDefinition = "char(1)", nullable = true)
	private NotificationOrder notificationOrder;
	
	
	@Column(name="NTFY_NM", columnDefinition = "char(30)", nullable = true)
	private String notificationName;
	
	
	@Column(name="AUTO_RNTFY_IND", columnDefinition = "char", nullable = true)
	private Character autoRenotify;
	
	@Column(name="EDI_BOX", columnDefinition = "char(15)", nullable = true)
	private String ediBox;
	
	@Column(name="MICROWAVE_IND", columnDefinition = "char", nullable = true)
	private Character microwaveIndicator;
	
	@Column(name="NTFY_AREA_CD", columnDefinition = "smallint(5)", nullable = true)
	private Integer notifyAreaCode;
	
	@Column(name="NTFY_EMAIL", columnDefinition = "char(40)", nullable = true)
	private String notifyEmail;
	
	@Column(name="NTFY_EMAIL_ID", columnDefinition = "char(20)", nullable = true)
	private String notifyEmailId;
	
	@Column(name="NTFY_EXT", columnDefinition = "smallint(5)", nullable = true)
	private Integer notifyPhoneExtension;
	
	@Column(name="NTFY_PREFIX", columnDefinition = "smallint(5)", nullable = true)
	private Integer notifyPrefix;
	
	@Column(name="NTFY_SUFFIX", columnDefinition = "smallint(5)", nullable = true)
	private Integer notifySuffix;

	public Long getNotifyMethodId() {
		return notifyMethodId;
	}

	public void setNotifyMethodId(Long notifyMethodId) {
		this.notifyMethodId = notifyMethodId;
	}

	public NotificationType getNotificationType() {
		return notificationType;
	}

	public void setNotificationType(NotificationType notificationType) {
		this.notificationType = notificationType;
	}

	public NotificationMethod getNotificationMethod() {
		return notificationMethod;
	}

	public void setNotificationMethod(NotificationMethod notificationMethod) {
		this.notificationMethod = notificationMethod;
	}

	public NotificationOrder getNotificationOrder() {
		return notificationOrder;
	}

	public void setNotificationOrder(NotificationOrder notificationOrder) {
		this.notificationOrder = notificationOrder;
	}

	public String getNotificationName() {
		if(notificationName!=null) {
			return notificationName.trim();
		}
		return notificationName;
	}

	public void setNotificationName(String notificationName) {
		this.notificationName = notificationName;
	}

	public Character getAutoRenotify() {
		return autoRenotify;
	}

	public void setAutoRenotify(Character autoRenotify) {
		this.autoRenotify = autoRenotify;
	}

	public String getEdiBox() {
		if(ediBox!=null) {
			return ediBox.trim();
		}
		return ediBox;
	}

	public void setEdiBox(String ediBox) {
		this.ediBox = ediBox;
	}

	public Character getMicrowaveIndicator() {
		return microwaveIndicator;
	}

	public void setMicrowaveIndicator(Character microwaveIndicator) {
		this.microwaveIndicator = microwaveIndicator;
	}

	public Integer getNotifyAreaCode() {
		return notifyAreaCode;
	}

	public void setNotifyAreaCode(Integer notifyAreaCode) {
		this.notifyAreaCode = notifyAreaCode;
	}

	public String getNotifyEmail() {
		if(notifyEmail!=null) {
			return notifyEmail.trim();
		}
		return notifyEmail;
	}

	public void setNotifyEmail(String notifyEmail) {
		this.notifyEmail = notifyEmail;
	}

	public String getNotifyEmailId() {
		if(notifyEmailId!=null) {
			return notifyEmailId.trim();
		}
		return notifyEmailId;
	}

	public void setNotifyEmailId(String notifyEmailId) {
		this.notifyEmailId = notifyEmailId;
	}

	public Integer getNotifyPhoneExtension() {
		return notifyPhoneExtension;
	}

	public void setNotifyPhoneExtension(Integer notifyPhoneExtension) {
		this.notifyPhoneExtension = notifyPhoneExtension;
	}

	public Integer getNotifyPrefix() {
		return notifyPrefix;
	}

	public void setNotifyPrefix(Integer notifyPrefix) {
		this.notifyPrefix = notifyPrefix;
	}

	public Integer getNotifySuffix() {
		return notifySuffix;
	}

	public void setNotifySuffix(Integer notifySuffix) {
		this.notifySuffix = notifySuffix;
	}

	public NotifyProfileMethod(String uversion, String createUserId, Timestamp createDateTime, String updateUserId,
			Timestamp updateDateTime, String updateExtensionSchema, Long notifyMethodId, NotificationType notificationType,
			NotificationMethod notificationMethod, NotificationOrder notificationOrder, String notificationName, Character autoRenotify,
			String ediBox, Character microwaveIndicator, Integer notifyAreaCode, String notifyEmail, String notifyEmailId,
			Integer notifyPhoneExtension, Integer notifyPrefix, Integer notifySuffix) {
		super(uversion, createUserId, createDateTime, updateUserId, updateDateTime, updateExtensionSchema);
		this.notifyMethodId = notifyMethodId;
		this.notificationType = notificationType;
		this.notificationMethod = notificationMethod;
		this.notificationOrder = notificationOrder;
		this.notificationName = notificationName;
		this.autoRenotify = autoRenotify;
		this.ediBox = ediBox;
		this.microwaveIndicator = microwaveIndicator;
		this.notifyAreaCode = notifyAreaCode;
		this.notifyEmail = notifyEmail;
		this.notifyEmailId = notifyEmailId;
		this.notifyPhoneExtension = notifyPhoneExtension;
		this.notifyPrefix = notifyPrefix;
		this.notifySuffix = notifySuffix;
	}

	public NotifyProfileMethod() {
		super();
	}

	public NotifyProfileMethod(String uversion, String createUserId, Timestamp createDateTime, String updateUserId,
			Timestamp updateDateTime, String updateExtensionSchema) {
		super(uversion, createUserId, createDateTime, updateUserId, updateDateTime, updateExtensionSchema);
		
	}
	
	
}