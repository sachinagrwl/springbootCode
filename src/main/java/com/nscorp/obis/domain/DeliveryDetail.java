package com.nscorp.obis.domain;

import org.springframework.stereotype.Component;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

@Entity
@Component
@Table(name="CUST_NTFY_DETG")
public class DeliveryDetail extends AuditInfo{
	@Id
	@Column(name="CUST_ID", columnDefinition = "Double(15)", nullable = false, updatable=false)
	Long customerId;
	
	@Column(name="SEND_SETG", columnDefinition="char(1)" , nullable=true)
	String sendSETG;	
	@Column(name="SEND_DETG", columnDefinition="char(1)" , nullable=true)
	String sendDETG;	
	@Column(name="CONF_TIME_INTERVAL", columnDefinition="Integer", nullable=true)
	Integer confirmationTimeInterval;
	public long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}
	public String getSendSETG() {
		return sendSETG;
	}
	public void setSendSETG(String sendSETG) {
		this.sendSETG = sendSETG;
	}
	public String getSendDETG() {
		return sendDETG;
	}
	public void setSendDETG(String sendDETG) {
		this.sendDETG = sendDETG;
	}
	public Integer getConfirmationTimeInterval() {
		return confirmationTimeInterval;
	}
	public void setConfirmationTimeInterval(Integer confirmationTimeInterval) {
		this.confirmationTimeInterval = confirmationTimeInterval;
	}
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	public DeliveryDetail(String uversion, String createUserId, Timestamp createDateTime, String updateUserId,
			Timestamp updateDateTime, String updateExtensionSchema, Long customerId, String sendSETG, String sendDETG,
			Integer confirmationTimeInterval) {
		super(uversion, createUserId, createDateTime, updateUserId, updateDateTime, updateExtensionSchema);
		this.customerId = customerId;
		this.sendSETG = sendSETG;
		this.sendDETG = sendDETG;
		this.confirmationTimeInterval = confirmationTimeInterval;
	}
	public DeliveryDetail() {
		super();
		// TODO Auto-generated constructor stub
	}
	public DeliveryDetail(String uversion, String createUserId, Timestamp createDateTime, String updateUserId,
			Timestamp updateDateTime, String updateExtensionSchema) {
		super(uversion, createUserId, createDateTime, updateUserId, updateDateTime, updateExtensionSchema);
		// TODO Auto-generated constructor stub
	}
	
}