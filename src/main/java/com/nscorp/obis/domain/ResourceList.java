package com.nscorp.obis.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table( name = "SEC_RESOURCE")
public class ResourceList extends AuditInfo{
	@Id
	@Column(name = "NM", columnDefinition = "char(16)", nullable = false)
	@NotNull( message = "Table Number can not be null")
	private String resourceName;
	
	@Column(name = "DSC", columnDefinition = "char(40)", nullable = true)
	private String resourceDescription;

	public ResourceList(
			@NotNull(message = "Table Number can not be null") String resourceName, String resourceDescription) {
		super();
		this.resourceName = resourceName;
		this.resourceDescription = resourceDescription;
	}
	
	public String getResourceName() {
		if(resourceName != null) {
			return resourceName.trim();
		}
		else {
			return resourceName;
		}
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	public String getResourceDescription() {
		if(resourceDescription != null) {
			return resourceDescription.trim();
		}
		else {
			return resourceDescription;
		}
		
	}

	public void setResourceDescription(String resourceDescription) {
		this.resourceDescription = resourceDescription;
	}

	public ResourceList() {
		
	}

}
