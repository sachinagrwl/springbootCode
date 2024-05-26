package com.nscorp.obis.domain;

import java.io.Serializable;

@SuppressWarnings("serial")
public class EquipmentContPrimaryKeys implements Serializable  {

	
    private String containerInit;	
	private String containerEquipType;
    private Long containerNbr;
    private String containerId;
    
    
	public String getContainerId() {
		return containerId;
	}


	public void setContainerId(String containerId) {
		this.containerId = containerId;
	}


	public String getContainerInit() {
		return containerInit;
	}


	public void setContainerInit(String containerInit) {
		this.containerInit = containerInit;
	}


	public String getContainerEquipType() {
		return containerEquipType;
	}


	public void setContainerEquipType(String containerEquipType) {
		this.containerEquipType = containerEquipType;
	}


	public Long getContainerNbr() {
		return containerNbr;
	}


	public void setContainerNbr(Long containerNbr) {
		this.containerNbr = containerNbr;
	}


	public EquipmentContPrimaryKeys(String containerInit, String containerEquipType, Long containerNbr, String containerId) {
		super();
		this.containerInit = containerInit;
		this.containerEquipType = containerEquipType;
		this.containerNbr = containerNbr;
		this.containerId = containerId;
	}


	public EquipmentContPrimaryKeys() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
