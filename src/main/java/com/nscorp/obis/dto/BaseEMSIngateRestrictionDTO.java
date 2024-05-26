package com.nscorp.obis.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.nscorp.obis.common.CommonConstants;
import com.nscorp.obis.domain.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
@JsonIgnoreProperties(value = "true")
public class BaseEMSIngateRestrictionDTO {
	
	@NotNull(message = "'ingateTerminalId' should not be null.")
	@Schema(required = true, description="The Terminal Id of the Ingate Terminal being used.", example="99990010120629")
	@Digits(integer=CommonConstants.INGT_TERM_MAX_SIZE, fraction = 0, message="Ingate terminal Id cannot have more than 15 digits")
	@Min(value =CommonConstants.INGT_TERM_MIN_SIZE , message = "Ingate terminal Length must be greater than 0")
	private Long ingateTerminalId;
	
	private Station onlineOriginStation;
	
	private Station onlineDestinationStation;

	private Station offlineDestinationStation;

    @Schema(required = false,description="The Equipment Initializer being used.", example="JBHU")
	@Size(max=CommonConstants.EQ_INIT_MAX_SIZE, message="equipmentInit size should not be more than {max}")
    private String equipmentInit;

    @Schema(required = false,description="The Equipment Lowest Number being used.")
	@Digits(integer=CommonConstants.EQ_LOW_NR_MAX_SIZE, fraction=0, message="equipmentLowestNumber length cannot have more than 6 digits")
    @Min(value =CommonConstants.EQ_LOW_NR_MIN_SIZE , message = "equipmentLowestNumber length must be greater than 0")
    private Integer equipmentLowestNumber;

    @Schema(required = false,description="The Equipment Highest Number being used.")
	@Digits(integer=CommonConstants.EQ_HIGH_NR_MAX_SIZE, fraction=0, message="equipmentHighestNumber length cannot have more than 6 digits")
    @Min(value =CommonConstants.EQ_HIGH_NR_MIN_SIZE , message = "equipmentHighestNumber Length must be greater than 0")
    private Integer equipmentHighestNumber;
    
    @Schema(required = false, description="The Corporate Customer Id of the EMS Ingate Restriction being used.", example="45376610352587")
	@Digits(integer=CommonConstants.CORP_CUST_ID_MAX_SIZE, fraction = 0, message="'corporateCustomerId' cannot have more than 15 digits")
    private Long corporateCustomerId;

    @NotBlank(message = "'loadEmptyCode' must not be null, empty or blank")
    @Schema(required = false,description="The Load Empty Code being used.", example="E")
	@Size(max=CommonConstants.LOAD_EMPTY_CD_MAX_SIZE, message="'loadEmptyCode' size should not be more than {max}")
    private String loadEmptyCode;

    @Schema(required = false,description="The Equipment Length being used.", example="240")
	@Digits(integer=CommonConstants.EQ_LGTH_MAX_SIZE, fraction=0, message="'equipmentLength' length cannot have more than 10 digits")
    @Min(value =CommonConstants.EQ_LGTH_MIN_SIZE , message = "'equipmentLength' length must be greater than 0")
    private Integer equipmentLength;

    @Schema(required = false,description="The Gross Weight being used.")
	@Digits(integer=CommonConstants.GROSS_WEIGHT_MAX_SIZE, fraction=0, message="'grossWeight' length cannot have more than 10 digits")
    @Min(value =CommonConstants.GROSS_WEIGHT_MIN_SIZE , message = "'grossWeight' length must be greater than 0")
    private Integer grossWeight;
    
    @Schema(required = false,description="The Line Of Businesses being used.", example = "DOMESTIC")
	@UniqueElements(message = "'lineOfBusinesses' must have unique values only.")
	private List<LineOfBusiness> lineOfBusinesses;
    
    @Schema(required = false,description="The Equipment Types being used.", example = "CONTAINER")
	@UniqueElements(message = "'equipmentTypes' must have unique values only.")
	private List<EquipmentType> equipmentTypes;

    @Schema(required = false,description="The Way Bill Route being used.", example="NS")
	@Size(max=CommonConstants.WB_ROUTE_MAX_SIZE, message="'wayBillRoute' size should not be more than {max}")
    private String wayBillRoute;

    @Schema(required = false,description="The Hazardous Indicator being used.")
    private Boolean hazardousIndicator;
    
    @Schema(required = false,description="The Weight Qualifier being used.")
    private WeightQualifier weightQualifier;

    @NotBlank(message = "'active' must not be null, empty or blank")
    @Schema(required = false,description="The Status(Active/Inactive) being used.", example="T")
	@Size(max=CommonConstants.ACTIVE_MAX_SIZE, message="'active' size should not be more than {max}")
    private String active;

    @Schema(required = false,description="The Start Date being used.", example="2019-09-17")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @NotNull(message = "Start Time should not be null, blank or empty")
    @Schema(required = true,description="The Start Time being used.", example="19:00:00")
    @DateTimeFormat(pattern = "HH-mm-ss")
    private LocalTime startTime;

    @Schema(required = false,description="The Start Date being used.", example="2019-09-23")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    @NotNull(message = "End Time should not be null, blank or empty")
    @Schema(required = true,description="The End Time being used.", example="23:59:59")
    private LocalTime endTime;

    @Schema(required = false,description="The Extension Schema being used.", example="IMS08121")
	@Size(max=CommonConstants.CREATE_EXTN_SCHEMA_MAX_SIZE, message="'createExtensionSchema' size should not be more than {max}")
    private String createExtensionSchema;
    
    @Schema(required = false,description="The Restricted Days being used.", example = "SUNDAY")
	@UniqueElements(message = "'restrictedDays' must have unique values only.")
    private List<DayOfWeek> restrictedDays;
    
    @Schema(required = false,description="The Traffic Types being used.", example = "STEEL_WHEEL")
	@UniqueElements(message = "'trafficTypes' must have unique values only.")
    private List<TrafficType> trafficTypes;

    @Schema(required = false,description="The Reefer Indicator being used.")
    private Boolean reeferIndicator;
    
    @NotBlank(message = "'temporaryIndicator' must not be null, empty or blank")
    @Schema(required = false,description="The Temperory Indicator being used.")
	@Size(max=CommonConstants.TEMP_IND_MAX_SIZE, message="'temporaryIndicator' size should not be more than {max}")
    private String temporaryIndicator;
    
//    @Schema(required = false,description="The Twenty Feet being used.", example="F")
//    @Size(max=CommonConstants.TWENTY_FEET_MAX_SIZE, message="'twentyFeet' size should not be more than {max}")
//	private String twentyFeet;
//
//    @Schema(required = false,description="The Forty Feet being used.", example="F")
//    @Size(max=CommonConstants.FORTY_FEET_MAX_SIZE, message="'fortyFeet' size should not be more than {max}")
//	private String fortyFeet;
//
//    @Schema(required = false,description="The Forty Five Feet Code being used.", example="F")
//    @Size(max=CommonConstants.FORTY_FIVE_FEET_MAX_SIZE, message="'fortyFiveFeet' size should not be more than {max}")
//	private String fortyFiveFeet;
//
//    @Schema(required = false,description="The Fifty Three Feet being used.", example="F")
//    @Size(max=CommonConstants.FIFTY_THREE_FEET_MAX_SIZE, message="'fiftyThreeFeet' size should not be more than {max}")
//	private String fiftyThreeFeet;
//
//    @Schema(required = false,description="The All Lengths being used.", example="F")
//    @Size(max=CommonConstants.ALL_LENGTHS_MAX_SIZE, message="'allLengths' size should not be more than {max}")
//	private String allLengths;
    
    @NotNull(message="'emsEquipmentLengthRestrictions' cannot be null")
    @Schema(required = true,description="The EMS Equipment Length Restrictions being used.", example = "RESTRICT_40_FT")
	@UniqueElements(message = "'emsEquipmentLengthRestrictions' must have unique values only.")
    private List<EMSEquipmentLengthRestriction> emsEquipmentLengthRestrictions;
    
    @Schema(required = false ,description="The uversion being used.", example="!")
	@Size(max=CommonConstants.U_VERSION_MAX_SIZE)
    private String uversion;
    
    @Schema(required = true ,description="The user id being used.", example="MST0U")
    @Size(max=CommonConstants.CREATE_USER_ID_MAX_SIZE)
    private String createUserId;

    @Schema(required = true ,description="The time at which the record was added.", example="1996-08-16 12:10:25")
    private Timestamp createDateTime;
    
    @Schema(required = true ,description="The user id of the user who updated the record.", example="MST0U")
    @Size(max=CommonConstants.UPD_USER_ID_MAX_SIZE)
    private String updateUserId;
    
    @Schema(required = true ,description="The time at which the record was updated.", example="1996-08-16 12:10:25")
    private Timestamp updateDateTime;
    
    @Schema(required = true ,description="The name or number of the screen being used.", example="IMS08121")
	@Size(max=CommonConstants.EMS_UPD_EXTN_SCHEMA_MAX_SIZE)
    private String updateExtensionSchema;
}
