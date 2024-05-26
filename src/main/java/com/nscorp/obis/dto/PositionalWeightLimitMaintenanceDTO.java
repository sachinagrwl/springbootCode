package com.nscorp.obis.dto;

import com.nscorp.obis.common.CommonConstants;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.*;

@Data
@EqualsAndHashCode(callSuper=false)
public class PositionalWeightLimitMaintenanceDTO extends AuditInfoDTO {
	
	@NotBlank(message = "CAR INIT value should not be Blank or Null.")
//	@NotNull(message = "Car Initializer value should not be Null.")
	@Schema(required = true,description="The Initializer value of the car being used.", example="DTTX")
	@Size(min=CommonConstants.CAR_INIT_MIN_SIZE, max=CommonConstants.CAR_INIT_MAX_SIZE, message = "CAR INIT length should be between {min} and {max}")
	private String carInit;
	
//	@NotBlank(message = "Car Lower Height value should not be Blank or Null.")
	@NotNull(message = "LOW NR value should not be Null.")
	@Schema(required = true,description="The Minimum height of the car being used.", example="62121")
	@Digits(integer=CommonConstants.CAR_NR_LOW_MAX_SIZE, fraction=0,message="LOW NR cannot have more than 6 digits")
	@Min(value = 0, message = "LOW NR value must be greater than or equal to 0")

	private Long carNrLow;
	
	//@NotBlank(message = "Car Higher Height value should not be Blank.")
	@NotNull(message = "HIGH NR value should not be Null.")
	@Schema(required = true,description="The Maximum height of the car being used.", example="62179")
	@Digits(integer=CommonConstants.CAR_NR_HIGH_MAX_SIZE, fraction=0,message="HIGH NR cannot have more than 6 digits")
	@Min(value = 0, message = "HIGH NR value must be greater than or equal to 0")

	private Long carNrHigh;
	
	@NotBlank(message = "Car Higher Equipment Type should not be Blank or Null.")
//	@NotNull(message = "Car Higher Equipment Type should not be Null.")
	@Schema(required = true,description="The Equipment Type of the car being used.", example="F")
	@Size(min=CommonConstants.CAR_EQ_TYPE_MIN_SIZE, max=CommonConstants.CAR_EQ_TYPE_MAX_SIZE, message = "CarEquipmentType size should be between {min} and {max}")
	private String carEquipmentType;
	
	@Schema(required = false,description="The AAR Type of the car being used.", example="S159")
	@Size(max=CommonConstants.AAR_TP_MAX_SIZE, message = "AarType size cannot have more than {max}")
	private String aarType;
	
	@Schema(required = false,description="The Owner of the car being used.", example="TTX")
	@Size(max=CommonConstants.CAR_OWNER_MAX_SIZE, message = "CarOwner size cannot have more than {max}")
	private String carOwner;
	
	@Schema(required = false,description="The Maximum Weight of the car being used.", example="44800")
	@Digits(integer=CommonConstants.C20_MAXWEIGHT_MAX_SIZE, fraction=0,message="MAX WEIGHT cannot have more than 6 digits")
	@Min(value = 0, message = "MAX WEIGHT value must not be negative")

	private Integer c20MaxWeight;
	
	@Schema(required = false,description="The Description of the car being used.", example="5 unit articulated 100 ton well car")
	@Size(max=CommonConstants.CAR_DESC_MAX_SIZE, message = "CarDescription size cannot have more than {max}")
	private String carDescription;

	public String getCarInit() {
		return carInit;
	}

	public void setCarInit(String carInit) {
		this.carInit = carInit;
	}

	public Long getCarNrLow() {
		return carNrLow;
	}

	public void setCarNrLow(Long carNrLow) {
		this.carNrLow = carNrLow;
	}

	public Long getCarNrHigh() {
		return carNrHigh;
	}

	public void setCarNrHigh(Long carNrHigh) {
		this.carNrHigh = carNrHigh;
	}

	public String getCarEquipmentType() {
		return carEquipmentType;
	}

	public void setCarEquipmentType(String carEquipmentType) {
		this.carEquipmentType = carEquipmentType;
	}

	public String getAarType() {
		return aarType;
	}

	public void setAarType(String aarType) {
		this.aarType = aarType;
	}

	public String getCarOwner() {
		return carOwner;
	}

	public void setCarOwner(String carOwner) {
		this.carOwner = carOwner;
	}

	public Integer getC20MaxWeight() {
		return c20MaxWeight;
	}

	public void setC20MaxWeight(Integer c20MaxWeight) {
		this.c20MaxWeight = c20MaxWeight;
	}

	public String getCarDescription() {
		return carDescription;
	}

	public void setCarDescription(String carDescription) {
		this.carDescription = carDescription;
	}


}

	
	
	
	