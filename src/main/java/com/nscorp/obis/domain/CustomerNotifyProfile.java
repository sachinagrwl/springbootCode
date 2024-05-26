package com.nscorp.obis.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Component
@Table(name = "CUST_NTFY_PRF")
public class CustomerNotifyProfile extends NotifyProfile {
	@Column(name="NTFY_TERM_ID", columnDefinition = "Double(15)", nullable =true) 
	private Long notifyTerminalId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CUST_ID")
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	Customer customer;	
		
	
	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public Long getNotifyTerminalId() {
		return notifyTerminalId;
	}

	public void setNotifyTerminalId(Long notifyTerminalId) {
		this.notifyTerminalId = notifyTerminalId;
	}

	public CustomerNotifyProfile(String uversion, String createUserId, Timestamp createDateTime, String updateUserId,
			Timestamp updateDateTime, String updateExtensionSchema, long notifyProfileId, String eventCode,
			List<DayOfWeek> dayOfWeek, List<Shift> shift, List<NotifyProfileMethod> notifyProfileMethods,
			Customer customer,  Long notifyTerminalId) {
		super(uversion, createUserId, createDateTime, updateUserId, updateDateTime, updateExtensionSchema,
				notifyProfileId, eventCode, dayOfWeek, shift, notifyProfileMethods);
		this.customer = customer;
		
		this.notifyTerminalId = notifyTerminalId;
	}

	public CustomerNotifyProfile() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CustomerNotifyProfile(String uversion, String createUserId, Timestamp createDateTime, String updateUserId,
			Timestamp updateDateTime, String updateExtensionSchema, long notifyProfileId, String eventCode,
			List<DayOfWeek> dayOfWeek, List<Shift> shift, List<NotifyProfileMethod> notifyProfileMethods) {
		super(uversion, createUserId, createDateTime, updateUserId, updateDateTime, updateExtensionSchema, notifyProfileId,
				eventCode, dayOfWeek, shift, notifyProfileMethods);
		// TODO Auto-generated constructor stub
	}

	public CustomerNotifyProfile(String uversion, String createUserId, Timestamp createDateTime, String updateUserId,
			Timestamp updateDateTime, String updateExtensionSchema) {
		super(uversion, createUserId, createDateTime, updateUserId, updateDateTime, updateExtensionSchema);
		// TODO Auto-generated constructor stub
	}

	
}