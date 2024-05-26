package com.nscorp.obis.dto;

import com.nscorp.obis.common.CommonConstants;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class StationDTO extends AuditInfoDTO {

	@NotNull(message = "Term Id Should not be Blank or Null.")
	@Schema(required = true,description="The Term Id of the station being used.", example="18245946233393")
	@Digits(integer=CommonConstants.TERM_ID_MAX_SIZE, fraction = 0, message="Term Id cannot have more than 15 digits")
	@Min(value = 1, message = "termId value must be greater than 0")
    private Long termId;

	@Schema(required = false,description="The Road number of the station being used.", example="0978")
	@Size(max=CommonConstants.ROAD_NUMBER_MAX_SIZE, message="roadNumber field size should not be more than {max}")
    private String roadNumber;

	@Schema(required = false,description="The Freight Station Accounting Code being used.", example="092457")
	@Size(max=CommonConstants.FSAC_MAX_SIZE, message="FSAC size field size should not be more than {max}")
    private String FSAC;

	@Schema(required = false,description="The Station Name being used.", example="092457")
	@Size(max=CommonConstants.STATION_NAME_MAX_SIZE, message="stationName field size should not be more than {max}")
    private String stationName;

	@Schema(required = false,description="The State of the Station being used.", example="SL")
	@Size(max=CommonConstants.STATE_MAX_SIZE, message="state field size should not be more than {max}")
    private String state;

	@Schema(required = false,description="The Billing Freight Station Accounting Code being used.", example="075355")
	@Size(max=CommonConstants.BILL_AT_FSAC_MAX_SIZE, message="billAtFsac field size should not be more than {max}")
    private String billAtFsac;

	@Schema(required = false,description="The Road Name being used.", example="075355")
	@Size(max=CommonConstants.ROAD_NAME_MAX_SIZE, message="roadName field size should not be more than {max}")
    private String roadName;

	@Schema(required = false,description="The Road Name being used.", example="92457")
	@Size(max=CommonConstants.OP_STN_MAX_SIZE, message="operationStation field size should not be more than {max}")
    private String operationStation;

	@Schema(required = false,description="The Standard Point Location Code being used.", example="919426000")
	@Size(max=CommonConstants.SPLC_MAX_SIZE, message="SPLC field size should not be more than {max}")
    private String splc;

	@Schema(required = false,description="The Railroad junction station being used.", example="CNTRA")
	@Size(max=CommonConstants.RULE_260_STN_MAX_SIZE, message="rule260Station field size should not be more than {max}")
    private String rule260Station;

	@Schema(required = false,description="The Intermodal Indicator being used.", example="O")
	@Size(max=CommonConstants.INTERMODAL_IND_MAX_SIZE, message="intermodalIndicator field size should not be more than {max}")
    private String intermodalIndicator;
	
	@Schema(required = false,description="The Operating Station – 5 Spell being used.", example="LOGIS")
	@Size(max=CommonConstants.OP_STA_5_SPELL_MAX_SIZE, message="char5Spell field size should not be more than {max}")
    private String char5Spell;

	@Schema(required = false,description="The Operating Station Name Alias being used.")
	@Size(max=CommonConstants.OP_STA_ALIAS_MAX_SIZE, message="char5Alias field size should not be more than {max}")
    private String char5Alias;

	@Schema(required = false,description="The Operating Station – 8 Spell being used.", example="LOGISTIK")
	@Size(max=CommonConstants.OP_STN_8_SPELL_MAX_SIZE, message="char8Spell field size should not be more than {max}")
    private String char8Spell;

	@Schema(required = false,description="The Division / Division Code being used.", example="94")
	@Size(max=CommonConstants.DIV_CD_MAX_SIZE, message="division field size should not be more than {max}")
    private String division;

	@Schema(required = false,description="The Station Expired Date being used.", example="2022-02-21")
    private LocalDate expirationDate;
    
	@Schema(required = false,description="The Billing Indicator being used.")
	@Size(max=CommonConstants.BILLING_IND_MAX_SIZE, message="billingInd field size should not be more than {max}")
    private String billingInd;
    
	@Schema(required = false,description="The Expired Date being used.", example="2022-04-03 00:00:00")
    @DateTimeFormat
    private Timestamp expiredDate;
    
	@Schema(required = false,description="The Top Pick Capability being used.", example="Y")
	@Size(max=CommonConstants.TOP_PICK_MAX_SIZE, message="topPick field size should not be more than {max}")
    private String topPick;
    
	@Schema(required = false,description="The Bottom Pick Capability being used.", example="Y")
	@Size(max=CommonConstants.BOTTOM_PICK_MAX_SIZE, message="bottomPick field size should not be more than {max}")
    private String bottomPick;
    
}
