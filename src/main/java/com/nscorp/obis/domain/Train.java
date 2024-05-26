package com.nscorp.obis.domain;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TRAIN")
public class Train extends AuditInfo {

	@Id
	@Column(name = "TRAIN_NR", columnDefinition = "char(4)", nullable = false)
	private String trainNumber;

	@Column(name = "TRAIN_DESC", columnDefinition = "char(24)", nullable = true)
	private String trainDesc;

	public String getTrainNumber() {
		return trainNumber != null ? trainNumber.trim() : trainNumber;
	}

	public void setTrainNumber(String trainNumber) {
		this.trainNumber = trainNumber;
	}

	public String getTrainDesc() {
		return trainDesc != null ? trainDesc.trim() : trainDesc;
	}

	public void setTrainDesc(String trainDesc) {
		this.trainDesc = trainDesc;
	}

	@Override
	public String toString() {
		return "Train [trainNumber=" + trainNumber + ", trainDesc=" + trainDesc + ", getUversion()=" + getUversion()
				+ ", getCreateUserId()=" + getCreateUserId() + ", getCreateDateTime()=" + getCreateDateTime()
				+ ", getUpdateUserId()=" + getUpdateUserId() + ", getUpdateDateTime()=" + getUpdateDateTime()
				+ ", getUpdateExtensionSchema()=" + getUpdateExtensionSchema() + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(trainNumber);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Train other = (Train) obj;
		return Objects.equals(trainNumber, other.getTrainNumber());
	}

}
