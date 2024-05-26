package com.nscorp.obis.dto;

import java.math.BigDecimal;
import java.util.List;

import javax.validation.constraints.*;

import com.nscorp.obis.common.CommonConstants;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class EquipmentCarDTO extends AuditInfoDTO {

	@NotBlank(message = "'carInit' should not be Blank or Null")
	@Schema(required = true,description="The Container Initializer being used.", example="ABFU")
	@Size(min= CommonConstants.CAR_INIT_MIN_SIZE, max=CommonConstants.CAR_INIT_MAX_SIZE, message="'carInit' size should not be greater than {max}")
	@Pattern(regexp="^[a-zA-Z0-9]*$",message="'carInit' should not contain Blank or Numbers or Special Characters")
	private String carInit;

	@NotNull(message = "'carNbr' should not be null")
	@Schema(required = true,description="The Car Number being used.", example="300502")
	@Digits(integer= CommonConstants.CAR_NBR_MAX_SIZE, fraction=0,message="'carNbr' cannot have more than 6 digits")
	@Min(value = CommonConstants.CAR_NBR_MIN_SIZE, message = "'carNbr' value cannot be less than 1")
	private BigDecimal carNbr;

	@NotBlank(message = "'carEquipType' should not be Blank or Null")
	@Schema(required = true,description="The Car Equipment Type being used.", example="F")
	@Size(min= CommonConstants.CAR_EQUIP_TYPE_MIN_SIZE, max=CommonConstants.CAR_EQUIP_TYPE_MAX_SIZE, message="'carEquipType' size should not be greater than {max}")
	@Pattern(regexp="^(F)$",message="'carEquipType' should only be 'F'")
	private String carEquipType;

	@Schema(required = false,description="The AAR Type being used.", example="S566")
	@Size(max=CommonConstants.AAR_TYPE_MAX_SIZE, message="'aarType' size should not be greater than {max}")
	private String aarType;

	@NotNull(message="'carLgth' should not be null")
	@Schema(required = false,description="The Car Length being used.", example="2268")
	@Digits(integer=CommonConstants.CAR_LENGTH_MAX_SIZE, fraction=0,message="'carLgth' cannot have more than 5 digits")
	@Min(value = CommonConstants.CAR_LENGTH_MIN_SIZE, message = "'carLgth' value cannot be less than 1")
	@Max(value = CommonConstants.SMALL_INT_DATA_TYPE_MAX_VALUE, message ="'carLgth' value cannot be more than 32,767" )
	private Integer carLgth;

	@Schema(required = false,description="The Car Length being used.", example="2268")
	@Digits(integer=CommonConstants.CAR_TARE_WEIGHT_MAX_SIZE, fraction=0,message="'carTareWgt' cannot have more than 5 digits")
	@Min(value = CommonConstants.CAR_TARE_WEIGHT_MIN_VALUE, message = "'carTareWgt' value cannot be less than 1")
	@Max(value = CommonConstants.SMALL_INT_DATA_TYPE_MAX_VALUE, message ="'carTareWgt' value cannot be more than 32,767" )
	private Integer carTareWgt;

	@Schema(required = false,description="The Damage Indicator being used.", example="N")
	@Size(min= CommonConstants.DAMAGE_IND_MIN_SIZE, max=CommonConstants.DAMAGE_IND_MAX_SIZE, message="'damageInd' size should not be greater than {max}")
	@Pattern(regexp="^(Y|N)$",message="'damageInd' should not be Blank or should contain 'Y' & 'N'")
	private String damageInd;

	@Schema(required = false,description="The Bad Order Indicator being used.", example="N")
	@Size(min= CommonConstants.BAD_ORDER_IND_MIN_SIZE, max=CommonConstants.BAD_ORDER_IND_MAX_SIZE, message="'badOrderInd' size should not be greater than {max}")
	@Pattern(regexp="^(Y|N)$",message="'badOrderInd' should not be Blank or should contain 'Y' & 'N'")
	private String badOrderInd;

	@Schema(required = false,description="The Car SA being used.", example="null")
	private Long carSa;

	@Schema(required = false,description="The Previous Commodity If Hazardous being used.", example="null")
//	@Digits(integer=CommonConstants.CAR_LENGTH_MAX_SIZE, fraction=0,message="'prevStcc' cannot have more than 5 digits")
//	@Min(value = CommonConstants.CAR_LENGTH_MIN_SIZE, message = "'carLgth' value cannot be less than 1")
	private Integer prevStcc;

	@Schema(required = false,description="The Car Owner Type being used.", example="L")
	@Size(min= CommonConstants.CAR_OWNER_TYPE_MIN_SIZE, max=CommonConstants.CAR_OWNER_TYPE_MAX_SIZE, message="'badOrderInd' size should not be greater than {max}")
	@Pattern(regexp="^(F|L|P|S)$",message="'carOwnerType' should not be Blank or should contain 'F', 'L', 'P' & 'S'")
	private String carOwnerType;

	@Schema(required = false,description="The Platform Height being used.", example="42")
	@Digits(integer=CommonConstants.PLATFORM_HEIGHT_MAX_SIZE, fraction=0,message="'platformHeight' cannot have more than 5 digits")
	@Min(value = CommonConstants.PLATFORM_HEIGHT_MIN_VALUE, message = "'platformHeight' value cannot be less than 0")
	@Max(value = CommonConstants.PLATFORM_HEIGHT_MAX_VALUE, message = "'platformHeight' value cannot be more than 120 inches or 10 feets")
	private Integer platformHeight_inches;

	@Schema(required = false,description="The Number of Articulated Cars being used.", example="5")
	@Size(max=CommonConstants.ARTICULATE_MAX_SIZE, message="'articulate' size should not be greater than {max}")
	private String articulate;

	@Schema(required = false,description="The Number Of Axles being used.", example="12")
	@Digits(integer=CommonConstants.NR_OF_AXLES_MAX_SIZE, fraction=0,message="'nrOfAxles' cannot have more than 5 digits")
	@Min(value = CommonConstants.NR_OF_AXLES_MIN_SIZE, message = "'nrOfAxles' value cannot be less than 1")
	@Max(value = CommonConstants.SMALL_INT_DATA_TYPE_MAX_VALUE, message ="'nrOfAxles' value cannot be more than 32,767" )
	private Integer nrOfAxles;

	@Schema(required = false,description="The Car Load Limit being used.", example="6025")
	@Digits(integer=CommonConstants.CAR_LOAD_LIMIT_MAX_SIZE, fraction=0,message="'carLoadLimit' cannot have more than 5 digits")
	@Min(value = CommonConstants.CAR_LOAD_LIMIT_MIN_VALUE, message = "'carLoadLimit' value cannot be less than 1")
	@Max(value = CommonConstants.SMALL_INT_DATA_TYPE_MAX_VALUE, message ="'carLoadLimit' value cannot be more than 32,767" )
	private Integer carLoadLimit;

	private List<EquipmentHitchDTO> equipmentHitch;

	private List<EquipmentLocationDTO> equipmentLocation;

	private List<HoldOrdersDTO> holdOrders;

}