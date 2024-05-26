package com.nscorp.obis.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

@Entity
@Table(name = "ROAD")
public class Road extends AuditInfo {

	@Id
	@Column(name = "ROAD_NR", columnDefinition = "char(4)", nullable = false)
	private String roadNumber;

	@Column(name = "ROAD_NM", columnDefinition = "char(4)", nullable = false)
	private String roadName;

	@Column(name = "ROAD_FULL_NM", columnDefinition = "char(30)", nullable = false)
	private String roadFullName;

	@Column(name = "ROAD_TP", columnDefinition = "char(2)", nullable = false)
	private String roadType;

	public String getRoadNumber() {
		if(roadNumber != null) {
			return roadNumber.trim();
		}
		else {
			return roadNumber;
		}
	}

	public void setRoadNumber(String roadNumber) {
		if(roadNumber != null) {
			this.roadNumber = roadNumber.toUpperCase();
		} else{
			this.roadNumber = roadNumber;
		}
	}

	public String getRoadName() {
		if(roadName != null) {
			return roadName.trim();
		}
		else {
			return roadName;
		}
	}

	public void setRoadName(String roadName) {
		if(roadName != null) {
			this.roadName = roadName.toUpperCase();
		} else{
			this.roadName = roadName;
		}
	}

	public String getRoadFullName() {
		if(roadFullName != null) {
			return roadFullName.trim();
		}
		else {
			return roadFullName;
		}
	}

	public void setRoadFullName(String roadFullName) {
		if(roadFullName != null) {
			this.roadFullName = roadFullName.toUpperCase();
		} else{
			this.roadFullName = roadFullName;
		}
	}

	public String getRoadType() {
		if(roadType != null) {
			return roadType.trim();
		}
		else {
			return roadType;
		}
	}

	public void setRoadType(String roadType) {
		if(roadType != null) {
			this.roadType = roadType.toUpperCase();
		} else{
			this.roadType = roadType;
		}
	}

	public Road(String uversion, String createUserId, Timestamp createDateTime, String updateUserId, Timestamp updateDateTime, String updateExtensionSchema, String roadNumber, String roadName, String roadFullName, String roadType) {
		super(uversion, createUserId, createDateTime, updateUserId, updateDateTime, updateExtensionSchema);
		this.roadNumber = roadNumber;
		this.roadName = roadName;
		this.roadFullName = roadFullName;
		this.roadType = roadType;
	}

	public Road() {
	}
}
  
  
 