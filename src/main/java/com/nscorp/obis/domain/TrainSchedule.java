package com.nscorp.obis.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TRAIN_SCHEDULE")
public class TrainSchedule extends AuditInfo {

	@Id
	@Column(name = "TRAIN_NR", columnDefinition = "char(4)", nullable = false)
	private String trainNumber;

	public String getTrainNumber() {
		return trainNumber;
	}

	public void setTrainNumber(String trainNumber) {
		this.trainNumber = trainNumber;
	}

	@Override
	public String toString() {
		return "TrainSchedule [trainNumber=" + trainNumber + ", getUversion()=" + getUversion() + ", getCreateUserId()="
				+ getCreateUserId() + ", getCreateDateTime()=" + getCreateDateTime() + ", getUpdateUserId()="
				+ getUpdateUserId() + ", getUpdateDateTime()=" + getUpdateDateTime() + ", getUpdateExtensionSchema()="
				+ getUpdateExtensionSchema() + "]";
	}

}
