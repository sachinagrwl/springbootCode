package com.nscorp.obis.dto;

import java.math.BigDecimal;
import java.util.List;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.UniqueElements;

import com.nscorp.obis.common.CommonConstants;
import com.nscorp.obis.domain.RestrictedWells;
import com.nscorp.obis.domain.Station;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class EquipmentEmbargoDTO extends AuditInfoDTO {

	@Schema(required = true, description="The Embargo Id of the Equipment being used.", example="99990010120629")
    private Long embargoId;

	@Schema(required = true,description="The Initializer value of the equipment being used.", example="TEST")
	@Size(min = CommonConstants.EQ_INIT_MIN_SIZE, max = CommonConstants.EQ_INIT_MAX_SIZE, message = "'equipmentInit' size should be between {min} and {max}")
	@Pattern(regexp="^[a-zA-Z]*$",message="'equipmentInit' should not contain Blank or Numbers or Special Characters")
	private String equipmentInit;

	@Schema(required = true,description="The Minimum height of the equipment being used.", example="62121")
	@Digits(integer=CommonConstants.EQ_NR_LOW_MAX_SIZE, fraction=0,message="'equipmentNumberLow' cannot have more than 6 digits")
	@Min(value = 1, message = "'equipmentNumberLow' value cannot be less than 1")
    private BigDecimal equipmentNumberLow;

	@Schema(required = true,description="The Maximum height of the equipment being used.", example="62200")
	@Digits(integer=CommonConstants.EQ_NR_HIGH_MAX_SIZE, fraction=0,message="'equipmentNumberHigh' cannot have more than 6 digits")
	@Min(value = 1, message = "'equipmentNumberHigh' value cannot be less than 1")
    private BigDecimal equipmentNumberHigh;

	@Schema(required = true,description="The type of the equipment being used.", example="C")
	@Size(max = CommonConstants.EQ_TYPE_MAX_SIZE, message="'equipmentType' size cannot have more than {max}")
	@Pattern(regexp="^(C|T|Z|F|G)$",message="'equipmentType' should not be Blank or should contain C, T, Z, F and G")
    private String equipmentType;

	@Schema(required = true,description="The AAR Type of the equipment being used.", example="C")
	@Size(max = CommonConstants.AAR_TP_MAX_SIZE, message="'equipmentAarType' size cannot have more than {max}")
    private String equipmentAarType;

    @Schema(required = true,description="The length of the equipment being used.", example="45")
    @Digits(integer=CommonConstants.EQ_LNGTH_MAX_SIZE, fraction=0, message="'equipmentLength' cannot have more than 5 digits")
    @Min(value = 0, message = "'equipmentLength' must be greater than or equals to 0")
    @Max(value = 32767, message = "'equipmentLength' should not be greater than 32767")
    private Integer equipmentLength;

    @Schema(required = true,description="The TOFC/COFC Indicator of the equipment being used.", example="C")
	@Size(max = CommonConstants.TOFC_COFC_IND_MAX_SIZE, message="'tofcCofcIndicator' size cannot have more than {max}")
    @Pattern(regexp="^(C|T)$",message="Only C,T and null value is allowed")
    private String tofcCofcIndicator;
    
    private Station destinationTerminal;

    private Station originTerminal;

    @Schema(required = true,description="The Road Name is being used.", example="TEST")
	@Size(max = CommonConstants.ROAD_NAME_MAX_SIZE, message="'roadName' size cannot have more than {max}")
    private String roadName;

    @NotBlank(message = "'restriction' should not be Blank or Null.")
    @Schema(required = true,description="The Restriction of the equipment being used.", example="C")
	@Size(max = CommonConstants.RESTRICTION_MAX_SIZE, message="'restriction' size cannot have more than {max}")
	@Pattern(regexp="^(Y|N|E)$",message="Only Y,N and E is allowed")
    private String restriction;

    @Schema(required = true,description="The Description is being used.", example="DO NOT LOAD 20'. NOT 20' CAPABLE")
   	@Size(max = CommonConstants.EMBARGO_DESCRIPTION_MAX_SIZE, message="'description' size cannot have more than {max}")
    private String description;

    @Schema(required = true,description="The Initializer value of the car being used.", example="TEST")
	@Size(min = CommonConstants.CAR_INIT_MIN_SIZE, max = CommonConstants.CAR_INIT_MAX_SIZE, message = "'carInit' size should be between {min} and {max}")
    private String carInit;

    @Schema(required = true,description="The Minimum height of the car being used.", example="62121")
	@Digits(integer=CommonConstants.EMB_CAR_NR_LOW_MAX_SIZE, fraction=0,message="'carNumberLow' cannot have more than 6 digits")
    @Min(value = 1, message = "'carNumberLow' value cannot be less than 1")
    private BigDecimal carNumberLow;

    @Schema(required = true,description="The Maximun height of the car being used.", example="62121")
	@Digits(integer=CommonConstants.EMB_CAR_NR_HIGH_MAX_SIZE, fraction=0,message="'carNumberHigh' cannot have more than 6 digits")
    @Min(value = 1, message = "'carNumberHigh' value cannot be less than 1")
    private BigDecimal carNumberHigh;

    @Schema(required = true,description="The car type of the equipment being used.", example="C")
	@Size(max = CommonConstants.CAR_EQ_TYPE_MAX_SIZE, message="'carEquipmentType' size cannot have more than {max}")
	@Pattern(regexp="^(C|T|Z|F|G)$",message="Only C,T,Z,F and G is allowed")
    private String carEquipmentType;

    @Schema(required = true,description="The AAR Type of the car being used.", example="TEST")
	@Size(min = CommonConstants.CAR_AAR_TP_MIN_SIZE, max = CommonConstants.CAR_AAR_TP_MAX_SIZE, message = "'carAarType' size should be between {min} and {max}")
    private String carAarType;

    @Schema(required = false,description="The Restricted Wells being used.", example = "WELL_A")
   	@UniqueElements(message = "'restrictedWells' must have unique values only.")
	private List<RestrictedWells> restrictedWells;

}
