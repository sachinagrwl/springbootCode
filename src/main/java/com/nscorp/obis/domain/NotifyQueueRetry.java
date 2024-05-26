package com.nscorp.obis.domain;

import java.util.List;

public class NotifyQueueRetry {

	private NotifyQueue notifyQueue;
	
	private List<NotifyQueue> notifyQueueList;

	private NorfolkSouthernEventLog norfolkSouthernEventLog;

	private Shipment shipment;

	private CustomerIndex customerIndex;

	private CustomerNotifyProfile customerNotifyProfile;

	private CustomerNickname customerNickname;

	private List<CustomerNotifyProfile> CustomerNotifyProfileList;
	
	public List<CustomerNotifyProfile> getCustomerNotifyProfileList() {
		return CustomerNotifyProfileList;
	}

	public List<NotifyQueue> getNotifyQueueList() {
		return notifyQueueList;
	}

	public void setNotifyQueueList(List<NotifyQueue> notifyQueueList) {
		this.notifyQueueList = notifyQueueList;
	}

	public void setCustomerNotifyProfileList(List<CustomerNotifyProfile> customerNotifyProfileList) {
		CustomerNotifyProfileList = customerNotifyProfileList;
	}

	public NotifyQueue getNotifyQueue() {
		return notifyQueue;
	}

	public void setNotifyQueue(NotifyQueue notifyQueue) {
		this.notifyQueue = notifyQueue;
	}

	public NorfolkSouthernEventLog getNorfolkSouthernEventLog() {
		return norfolkSouthernEventLog;
	}

	public void setNorfolkSouthernEventLog(NorfolkSouthernEventLog norfolkSouthernEventLog) {
		this.norfolkSouthernEventLog = norfolkSouthernEventLog;
	}

	public Shipment getShipment() {
		return shipment;
	}

	public void setShipment(Shipment shipment) {
		this.shipment = shipment;
	}

	public CustomerIndex getCustomerIndex() {
		return customerIndex;
	}

	public void setCustomerIndex(CustomerIndex customerIndex) {
		this.customerIndex = customerIndex;
	}

	public CustomerNotifyProfile getCustomerNotifyProfile() {
		return customerNotifyProfile;
	}

	public void setCustomerNotifyProfile(CustomerNotifyProfile customerNotifyProfile) {
		this.customerNotifyProfile = customerNotifyProfile;
	}

	public CustomerNickname getCustomerNickname() {
		return customerNickname;
	}

	public void setCustomerNickname(CustomerNickname customerNickname) {
		this.customerNickname = customerNickname;
	}

	public NotifyQueueRetry(NotifyQueue notifyQueue, List<NotifyQueue> notifyQueueList,
			NorfolkSouthernEventLog norfolkSouthernEventLog, Shipment shipment, CustomerIndex customerIndex,
			CustomerNotifyProfile customerNotifyProfile, CustomerNickname customerNickname,
			List<CustomerNotifyProfile> customerNotifyProfileList) {
		super();
		this.notifyQueue = notifyQueue;
		this.notifyQueueList = notifyQueueList;
		this.norfolkSouthernEventLog = norfolkSouthernEventLog;
		this.shipment = shipment;
		this.customerIndex = customerIndex;
		this.customerNotifyProfile = customerNotifyProfile;
		this.customerNickname = customerNickname;
		CustomerNotifyProfileList = customerNotifyProfileList;
	}

	public NotifyQueueRetry() {
		super();
		// TODO Auto-generated constructor stub
	}

}
