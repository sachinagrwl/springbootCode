package com.nscorp.obis.domain;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "SPEC_ACT_NTFY_2")
public class SpecialActivityNotifyProfile extends AuditInfo {
    
    @Id
	@Column(name="ACTIVITY_ID", columnDefinition = "smallint(5)", nullable =false) 
	private Integer activityId;	

    @Column(name="ACTIVITY_DESC", columnDefinition = "char(20)", nullable = true)
	private String activityDesc;

	@OneToMany( fetch = FetchType.LAZY)
	@JoinColumn(name="ACTIVITY_ID")
	private List<ActivityNotifyProfile> activityNotifyProfiles ;

	public Integer getActivityId() {
		return activityId;
	}

	public void setActivityId(Integer activityId) {
		this.activityId = activityId;
	}

	public String getActivityDesc() {
		return activityDesc;
	}

	public void setActivityDesc(String activityDesc) {
		this.activityDesc = activityDesc;
	}

	public List<ActivityNotifyProfile> getActivityNotifyProfiles() {
		return activityNotifyProfiles;
	}

	public void setActivityNotifyProfiles(List<ActivityNotifyProfile> activityNotifyProfiles) {
		this.activityNotifyProfiles = activityNotifyProfiles;
	}

	public SpecialActivityNotifyProfile(String uversion, String createUserId, Timestamp createDateTime,
			String updateUserId, Timestamp updateDateTime, String updateExtensionSchema, Integer activityId,
			String activityDesc, List<ActivityNotifyProfile> activityNotifyProfiles) {
		super(uversion, createUserId, createDateTime, updateUserId, updateDateTime, updateExtensionSchema);
		this.activityId = activityId;
		this.activityDesc = activityDesc;
		this.activityNotifyProfiles = activityNotifyProfiles;
	}

	public SpecialActivityNotifyProfile() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SpecialActivityNotifyProfile(String uversion, String createUserId, Timestamp createDateTime,
			String updateUserId, Timestamp updateDateTime, String updateExtensionSchema) {
		super(uversion, createUserId, createDateTime, updateUserId, updateDateTime, updateExtensionSchema);
		// TODO Auto-generated constructor stub
	}

	
	

    
    
    

}
