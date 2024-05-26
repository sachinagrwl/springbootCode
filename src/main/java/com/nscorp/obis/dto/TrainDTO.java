package com.nscorp.obis.dto;

import java.util.Objects;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.nscorp.obis.common.NullOrNotBlank;
import com.nscorp.obis.domain.Train;

import io.swagger.v3.oas.annotations.media.Schema;

public class TrainDTO extends AuditInfoDTO {

	@NotBlank
	@Size(min = 1, max = 4, message = "train number length should be between 1 and 4")
	@Schema(required = true, description = "This is the Numeric or Alpha code used to identify to identify the direction of this movement of cars")
	private String trainNumber;
	
	@NullOrNotBlank(min = 1, max = 24, message = "train desc length should be between 1 and 24")
	@Schema(required = false, description = "Description of a specific train.")
	private String trainDesc;

	public String getTrainNumber() {
		return trainNumber;
	}

	public void setTrainNumber(String trainNumber) {
		this.trainNumber = trainNumber;
	}

	public String getTrainDesc() {
		return trainDesc;
	}

	public void setTrainDesc(String trainDesc) {
		this.trainDesc = trainDesc;
	}

	@Override
	public String toString() {
		return "TrainDTO [trainNumber=" + trainNumber + ", trainDesc=" + trainDesc + ", getUversion()=" + getUversion()
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
		TrainDTO other = (TrainDTO) obj;
		return Objects.equals(trainNumber, other.getTrainNumber());
	}
}
