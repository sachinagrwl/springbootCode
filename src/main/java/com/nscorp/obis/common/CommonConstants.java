package com.nscorp.obis.common;

import java.time.LocalTime;

public class CommonConstants {


    private CommonConstants() {
		    throw new IllegalStateException("Common Constant class");
		  }

	/* Header Constants */
	public static final String USER_ID = "userid";
	public static final String EXTENSION_SCHEMA = "extensionschema";
	public static final int GEN_TBL_MAX_SIZE = 8;
	public static final int GEN_TBL_MIN_SIZE = 1;
	public static final int GEN_CODE_SIZE_MAX_SIZE = 2;
	public static final int GEN_TBLDESC_MAX_SIZE = 20;
	public static final int	RESOURCE_NM_MAX_SIZE = 16;
	public static final int GEN_OWNR_GRP_MAX_SIZE = 8;
	public static final int GTN_CD_MIN_SIZE = 1;
	public static final int GTN_CD_MAX_SIZE = 10;
	public static final String EDI_RSN_TABLE = "EDI_RSN";
	public static final String INREJECT_TABLE = "INREJECT";
	public static final int AAR_WHY_MADE_SIZE = 30;

	public static final String INRTRJCT_TABLE = "INRTRJCT";
	public static final String LP_CFG_TABLE = "LP_CFG";


	public static final int U_VERSION_MAX_SIZE = 1;
	public static final int CREATE_USER_ID_MAX_SIZE = 8;
	public static final int CREATE_DT_TM_MAX_SIZE = 26;
	public static final int UPD_USER_ID_MAX_SIZE = 8;
	public static final int UPD_DT_TM_MAX_SIZE = 26;
	public static final int UPD_EXTN_SCHEMA_MAX_SIZE = 16;

	public static final int EQ_TYPE_MAX_SIZE = 1;
	public static final int EQ_TYPE_MIN_SIZE = 1;
	public static final int EQ_LENGTH_MAX_SIZE = 4;
	public static final int EQ_LENGTH_MIN_SIZE = 1;
	public static final int TARE_WEIGHT_MAX_SIZE = 6;

	public static final int GEN_TABLE_MAX_SIZE = 8;
	public static final int GEN_TABLE_MIN_SIZE = 1;
	public static final int GEN_TABLE_CODE_MAX_SIZE = 10;
	public static final int GEN_TABLE_CODE_MIN_SIZE = 1;
	public static final int GEN_SHORT_DESC_MAX_SIZE = 10;
	public static final int GEN_LONG_DESC_MAX_SIZE = 45;
	public static final int GEN_ADD_SHORT_MAX_SIZE = 10;
	public static final int GEN_ADD_LONG_MAX_SIZE = 45;
	public static final int GEN_FLAG_MAX_SIZE = 1;

	public static final int CAR_INIT_MAX_SIZE = 4;
	public static final int CAR_INIT_MIN_SIZE = 1;
	public static final int CAR_NR_LOW_MAX_SIZE = 6;
	public static final int CAR_NR_HIGH_MAX_SIZE = 6;
	public static final int CAR_EQ_TYPE_MAX_SIZE = 1;
	public static final int CAR_EQ_TYPE_MIN_SIZE = 1;
	public static final int AAR_TP_MIN_SIZE = 1;
	public static final int UNCD_MIN_SIZE = 1;

	public static final int UNCD_MAX_SIZE = 6;
	public static final int AAR_TP_MAX_SIZE = 4;
	public static final int CAR_OWNER_MIN_SIZE = 1;
	public static final int CAR_OWNER_MAX_SIZE = 4;
	public static final int C20_MAXWEIGHT_MAX_SIZE = 6;
	public static final int CAR_DESC_MAX_SIZE = 58;

	public static final int SHIPLINE_NUMBER_MIN_SIZE = 1;
	public static final int SHIPLINE_NUMBER_MAX_SIZE = 7;
	public static final int DESCRIPTION_MAX_SIZE = 35;

	public static final int CORP_CUST_MIN_SIZE = 1;
	public static final int CORP_CUST_MAX_SIZE = 15;
	public static final int CORP_LONG_NAME_MAX_SIZE = 30;
	public static final int CORP_SHORT_NAME_MAX_SIZE = 10;
	public static final int CUST_ID_MAX_SIZE = 15;
	public static final int ICGH_CD_MAX_SIZE = 4;
	public static final int PRIMARY_LOB_MAX_SIZE = 1;
	public static final int SECONDARY_LOB_MAX_SIZE = 10;
	public static final int SCAC_MAX_SIZE = 4;
	public static final int TERMINAL_FEED_ENABLED_MAX_SIZE = 1;
	public static final int ACCOUNT_MANAGER_MAX_SIZE = 30;

	public static final int CAR_TYPE_MIN_SIZE = 1;
	public static final int CAR_TYPE_MAX_SIZE = 4;
	public static final int FREIGHT_TYPE_MIN_SIZE = 1;
	public static final int FREIGHT_TYPE_MAX_SIZE = 4;

	public static final int AAR_TYPE_MIN_SIZE = 1;
	public static final int AAR_TYPE_MAX_SIZE = 4;
	public static final int AAR_DESC_TYPE_MAX_SIZE = 30;
	public static final int AAR_CAPACITY_MAX_SIZE = 4;
	public static final int IM_DESC_TYPE_MAX_SIZE = 30;
	public static final int STANDARD_AAR_TYPE_MAX_SIZE = 1;

	public static final int TERM_ID_MIN_SIZE = 1;
	public static final int TERM_ID_MAX_SIZE = 15;
	public static final int ROAD_NUMBER_MAX_SIZE = 4;
	public static final int FSAC_MAX_SIZE = 6;
	public static final int STATION_NAME_MAX_SIZE = 19;
	public static final int STATE_MAX_SIZE = 2;
	public static final int BILL_AT_FSAC_MAX_SIZE = 6;
	public static final int ROAD_NAME_MAX_SIZE = 4;
	public static final int OP_STN_MAX_SIZE = 5;
	public static final int SPLC_MAX_SIZE = 9;
	public static final int RULE_260_STN_MAX_SIZE = 5;
	public static final int INTERMODAL_IND_MAX_SIZE = 1;
	public static final int OP_STA_5_SPELL_MAX_SIZE = 5;
	public static final int OP_STA_ALIAS_MAX_SIZE = 5;
	public static final int OP_STN_8_SPELL_MAX_SIZE = 8;
	public static final int DIV_CD_MAX_SIZE = 2;
	public static final int STN_EXP_DATE_MAX_SIZE = 10;
	public static final int BILLING_IND_MAX_SIZE = 1;
	public static final int EXPIRED_DT_MAX_SIZE = 26;
	public static final int TOP_PICK_MAX_SIZE = 1;
	public static final int BOTTOM_PICK_MAX_SIZE = 1;

	/* EMS Ingate Restrictions */
	public static final int RESTRICT_ID_MIN_SIZE = 1;
	public static final long NON_TERMINAL_SPECIFIC = 99999999999999L;
	public static final int RESTRICT_ID_MAX_SIZE = 15;
	public static final int INGT_TERM_MIN_SIZE = 1;
	public static final int INGT_TERM_MAX_SIZE = 15;
	public static final int ONL_ORIG_MAX_SIZE = 15;
	public static final int ONL_DEST_MAX_SIZE = 15;
	public static final int OFFL_DEST_MAX_SIZE = 15;
	public static final int EQ_INIT_MAX_SIZE = 4;
	public static final int EQ_LOW_NR_MAX_SIZE = 6;
	public static final int EQ_HIGH_NR_MAX_SIZE = 6;
	public static final int CORP_CUST_ID_MAX_SIZE = 15;
	public static final int PRIMARY_LOB_EMS_MAX_SIZE = 1;
	public static final int EQ_TP_MAX_SIZE = 1;
	public static final int LOAD_EMPTY_CD_MAX_SIZE = 1;
	public static final int EQ_LGTH_MAX_SIZE = 10;
	public static final int GROSS_WEIGHT_MAX_SIZE = 10;
	public static final int WB_ROUTE_MAX_SIZE = 110;
	public static final int HAZ_IND_MAX_SIZE = 1;
	public static final int WT_QUAL_MAX_SIZE = 2;
	public static final int ACTIVE_MAX_SIZE = 1;
	public static final int ST_DATE_MAX_SIZE = 10;
	public static final int ST_TIME_MAX_SIZE = 8;
	public static final int END_DATE_MAX_SIZE = 10;
	public static final int END_TIME_MAX_SIZE = 8;
	public static final int CREATE_EXTN_SCHEMA_MAX_SIZE = 32;
	public static final int EMS_UPD_EXTN_SCHEMA_MAX_SIZE = 32;
	public static final int IS_REEFER_MAX_SIZE = 1;
	public static final int TEMP_IND_MAX_SIZE = 1;
	public static final int ONL_ORIG_MIN_SIZE = 1;
	public static final int EQ_HIGH_NR_MIN_SIZE = 1;
	public static final int EQ_LOW_NR_MIN_SIZE = 1;
	public static final int EQ_LGTH_MIN_SIZE = 1;
	public static final int GROSS_WEIGHT_MIN_SIZE = 1;
	public static final int TERM_TBL_MIN_SIZE = 1;
	public static final int TERM_TBL_MAX_SIZE = 15;
	public static final int BLK_TBL_MIN_SIZE = 01;
	public static final int BLK_TBL_MAX_SIZE = 99;

	/*EMSIngateAllocation*/
	public static final int TIMS_ID_MAX_SIZE = 15;
	public static final int TOTAL_INGATES_ALLOWED_MAX_SIZE = 10;
	public static final int NUMBER_INGATED_MAX_SIZE = 10;
	public static final int TOTAL_INGATES_ALLOWED_MIN_SIZE = 1;
	public static final int NUMBER_INGATED_MIN_SIZE = 0;
	public static final int TWENTY_FEET_MAX_SIZE = 1;
	public static final int FORTY_FEET_MAX_SIZE = 1;
	public static final int FORTY_FIVE_FEET_MAX_SIZE = 1;
	public static final int FIFTY_THREE_FEET_MAX_SIZE = 1;
	public static final int ALL_LENGTHS_MAX_SIZE = 1;
	public static final String REGEX = "[0-9]+";

	public static final String STN_REGEX = "[0-9_]*$";

	/*Term Free Day*/
	public static final int CLOSE_RSN_CD_MAX_SIZE = 3;
	public static final int CLOSE_RSN_DSC_MAX_SIZE = 30;
	public static final LocalTime ONE_MINUTE_AFTER_MIDNIGHT = LocalTime.of(0, 1, 0);

	/*AAR HITCH MAINTENANCE*/
	public static final int HCH_LOC_MIN_SIZE = 1;
	public static final int HCH_LOC_MAX_SIZE = 2;

	/*EQUIPMENT SPEED INIT CODE*/
	public static final int EQ_INIT_SHORT_MIN_SIZE = 1;
	public static final int EQ_INIT_SHORT_MAX_SIZE = 4;
	public static final int EQ_INIT_MIN_SIZE = 1;

	/*EQUIPMENT 600 CONT*/
	public static final int BEG_EQ_NM_MAX_SIZE = 6;
	public static final int END_EQ_NM_MAX_SIZE = 6;

	/*RULES CIRCULAR*/
	public static final int[] CONTAINER_LENGTH = {0, 20, 28, 40, 45, 48, 53, 57};
	public static final int[] TRAILER_LENGTH = {0, 28, 40, 45, 48, 53, 57};
	public static final int[] CHASIS_LENGTH = {0, 20, 28, 40, 45, 53, 57};
	public static final int MAXIMUM_SHIP_WEIGHT_MAX_SIZE = 9;
	public static final int EQ_LENGTH_RULES_CIRCULAR_MAX_SIZE = 2;
	public static final String EQUIPMENT_LENGTH = "Equipment Length: ";
	public static final String EQUIPMENT_TYPE = " is not valid for Equipment Type: ";

	/*EQUIPMENT OVERRIDE TARE WEIGHT*/
	public static final int OVERRIDE_ID_MAX_SIZE = 53;
	public static final int EQ_NR_LOW_MAX_SIZE = 6;
	public static final int EQ_NR_HIGH_MAX_SIZE = 6;
	public static final int EQ_LNGTH_MAX_SIZE = 5;
	public static final int OVERRIDE_TARE_WEIGHT_MAX_SIZE = 6;

	/* EQUIPMENT RESTRICT */

	public static final int RESTRICTION_ID_MAX_SIZE = 53;
	public static final int EQUIPMENT_INIT_MIN_SIZE = 1;
	public static final int EQUIPMENT_INIT_MAX_SIZE = 4;
	public static final int EQUIPMENT_TYPE_MIN_SIZE = 1;
	public static final int EQUIPMENT_TYPE_MAX_SIZE = 1;
	public static final int EQUIPMENT_NR_LOW_MIN_SIZE = 1;
	public static final int EQUIPMENT_NR_LOW_MAX_SIZE = 6;
	public static final int EQUIPMENT_NR_HIGH_MIN_SIZE = 1;
	public static final int EQUIPMENT_NR_HIGH_MAX_SIZE = 6;
	public static final int EQUIPMENT_RESTRICT_TYPE_MIN_SIZE = 1;
	public static final int EQUIPMENT_RESTRICT_TYPE_MAX_SIZE = 1;

	/*EQUIPMENT EMBARGO*/
	public static final int EMBARGO_TERM_ID_MAX_SIZE = 53;
	public static final int ORIGIN_TERM_ID_MAX_SIZE = 53;
	public static final int TOFC_COFC_IND_MAX_SIZE = 1;
	public static final int RESTRICTION_MAX_SIZE = 1;
	public static final int EMBARGO_DESCRIPTION_MAX_SIZE = 50;
	public static final int EMB_CAR_NR_LOW_MAX_SIZE = 6;
	public static final int EMB_CAR_NR_HIGH_MAX_SIZE = 6;
	public static final int CAR_AAR_TP_MIN_SIZE = 1;
	public static final int CAR_AAR_TP_MAX_SIZE = 4;
	/* CUSTOMER */
	public static final int CUST_TEAM_AUD_CD_MIN_SIZE = 1;
	public static final int CUST_TEAM_AUD_CD_MAX_SIZE = 3;
	public static final int CUST_NS_ACCT_DSC_MIN_SIZE = 1;
	public static final int CUST_NS_ACCT_DSC_MAX_SIZE = 30;
	public static final int CUST_SC_MIN_SIZE = 1;
	public static final int CUST_SC_MAX_SIZE = 4;
	public static final int CUST_INT_IND_MIN_SIZE = 1;
	public static final int CUST_INT_IND_MAX_SIZE = 1;
	public static final int CUST_SHIP_PRTY_MIN_SIZE = 1;
	public static final int CUST_SHIP_PRTY_MAX_SIZE = 1;
	public static final int CUST_NM_GS_MIN_SIZE = 1;
	public static final int CUST_NM_GS_MAX_SIZE = 10;
	public static final int CUST_ACCT_DSC_MIN_SIZE = 1;
	public static final int CUST_ACCT_DSC_MAX_SIZE = 30;
	public static final int CUST_CIF_NR_MIN_SIZE = 1;
	public static final int CUST_CIF_NR_MAX_SIZE = 15;
	public static final int CUST_DISTRICT_MIN_SIZE = 1;
	public static final int CUST_DISTRICT_MAX_SIZE = 2;
	public static final int CUST_IM_REG_MIN_RANGE = 0;
	public static final int CUST_IM_REG_MAX_RANGE = 99;
	public static final int CUST_BASE_MIN_SIZE = 1;
	public static final int CUST_BASE_MAX_SIZE = 4;
	public static final int CUST_EXT_FRACTION = 0;
	public static final int CUST_EXT_MIN_SIZE = 0;
	public static final int CUST_EXT_MAX_SIZE = 32767;
	public static final int CUST_EXT_DIGIT_SIZE = 5;
	public static final int CUST_EXCH_FRACTION = 0;
	public static final int CUST_EXCH_MIN_SIZE = 0;
	public static final int CUST_EXCH_DIGIT_SIZE = 3;
	public static final int CUST_AREA_FRACTION = 0;
	public static final int CUST_AREA_MIN_SIZE = 0;
	public static final int CUST_AREA_DIGIT_SIZE = 3;
	public static final int CUST_FAX_AREA_FRACTION = 0;
	public static final int CUST_FAX_AREA_MIN_SIZE = 100;
	public static final int CUST_FAX_AREA_MAX_SIZE = 999;
	public static final int CUST_FAX_AREA_DIGIT_SIZE = 5;
	public static final int CUST_FAX_EXCH_FRACTION = 0;
	public static final int CUST_FAX_EXCH_MIN_SIZE = 0;
	public static final int CUST_FAX_EXCH_MAX_SIZE = 999;
	public static final int CUST_FAX_EXCH_DIGIT_SIZE = 5;
	public static final int CUST_FAX_EXT_FRACTION = 0;
	public static final int CUST_FAX_EXT_MIN_SIZE = 0;
	public static final int CUST_FAX_EXT_MAX_SIZE = 9999;
	public static final int CUST_FAX_EXT_DIGIT_SIZE = 4;
	public static final int CUST_PRIME_CONTACT_MIN_SIZE = 1;
	public static final int CUST_PRIME_CONTACT_MAX_SIZE = 30;
	public static final int CUST_ZIP_CD_MIN_SIZE = 1;
	public static final int CUST_ZIP_CD_MAX_SIZE = 10;
	public static final int CUST_ST_PV_MIN_SIZE = 1;
	public static final int CUST_ST_PV_MAX_SIZE = 2;
	public static final int CUST_CTY_MIN_SIZE = 1;
	public static final int CUST_CTY_MAX_SIZE = 19;
	public static final int CUST_ADDR_1_MIN_SIZE = 1;
	public static final int CUST_ADDR_1_MAX_SIZE = 35;
	public static final int CUST_ADDR_2_MIN_SIZE = 1;
	public static final int CUST_ADDR_2_MAX_SIZE = 35;
	public static final int CUST_ACTY_STAT_MIN_SIZE = 1;
	public static final int CUST_ACTY_STAT_MAX_SIZE = 1;
	public static final int CUST_CR_VALID_IND_MIN_SIZE = 1;
	public static final int CUST_CR_VALID_IND_MAX_SIZE = 1;
	public static final int CUST_OWNR_IND_MIN_SIZE = 1;
	public static final int CUST_OWNR_IND_MAX_SIZE = 1;
	public static final int CUST_NM_MIN_SIZE = 1;
	public static final int CUST_NM_MAX_SIZE = 35;
	public static final int CUST_NR_MIN_SIZE = 1;
	public static final int CUST_NR_MAX_SIZE = 10;
	
	public static final int DRAY_ID_MIN_SIZE = 1;
	public static final int DRAY_ID_MAX_SIZE = 4;
	
	public static final int RT_TP_MIN_SIZE = 1;
	public static final int RT_TP_MAX_SIZE = 1;
	public static final int CNT_WK_MIN_SIZE = 1;
	public static final int CNT_WK_MAX_SIZE = 1;
	public static final int END_DESC_TYPE_MAX_SIZE = 25;
	public static final int END_CD_TYPE_MAX_SIZE = 2;


	/* Container Chassis Association */

	// CONTAINER
	public static final int CONTAINER_INIT_MIN_SIZE = 1;
	public static final int CONTAINER_INIT_MAX_SIZE = 4;
	public static final int CONTAINER_NR_LOW_MIN_SIZE = 1;
	public static final int CONTAINER_NR_LOW_MAX_SIZE = 6;
	public static final int CONTAINER_NR_HIGH_MIN_SIZE = 1;
	public static final int CONTAINER_NR_HIGH_MAX_SIZE = 6;
	public static final int CONTAINER_LENGTH_MIN_SIZE = 1;
	public static final int CONTAINER_LENGTH_MAX_SIZE = 5;

	// CHASSIS
	public static final int CHASSIS_INIT_MIN_SIZE = 1;
	public static final int CHASSIS_INIT_MAX_SIZE = 4;
	public static final int CHASSIS_NR_LOW_MIN_SIZE = 1;
	public static final int CHASSIS_NR_LOW_MAX_SIZE = 6;
	public static final int CHASSIS_NR_HIGH_MIN_SIZE = 1;
	public static final int CHASSIS_NR_HIGH_MAX_SIZE = 6;
	public static final int CHASSIS_LENGTH_MIN_SIZE = 1;
	public static final int CHASSIS_LENGTH_MAX_SIZE = 5;

	public static final int ALLOW_IND_MIN_SIZE = 1;
	public static final int ALLOW_IND_MAX_SIZE = 1;

	public static final int NOTEPAD_ID_MAX_SIZE = 15;
	public static final int SVC_ID_MAX_SIZE = 15;
	public static final int DRIVER_ID_MAX_SIZE = 15;
	public static final int NOTEPAD_ENTRY_TEXT_MAX_SIZE = 255;

	public static final Integer[] validLengths = {20, 40, 45, 48, 53};
	public static final int TIA_END_MIN_SIZE = 1;
	public static final int TIA_END_MAX_SIZE = 1;
	public static final int BONDED_AUTH_ID_MAX_SIZE = 9;


	
	public static final int ROAD_NUM_MIN_SIZE = 1;
	public static final int ROAD_NUM_MAX_SIZE = 4;
	public static final int ROAD_NAME_MIN_SIZE = 1;
	public static final int ROAD_FULL_NAME_MAX_SIZE = 30;

	/* CAR MAINTENANCE */
	public static final int CAR_NBR_MIN_SIZE = 1;
	public static final int CAR_NBR_MAX_SIZE = 6;
	public static final int CAR_EQUIP_TYPE_MIN_SIZE = 1;
	public static final int CAR_EQUIP_TYPE_MAX_SIZE = 1;
	public static final int CAR_LENGTH_MIN_SIZE = 1;
	public static final int CAR_LENGTH_MAX_SIZE = 5;
	public static final int CAR_TARE_WEIGHT_MIN_SIZE = 1;
	public static final int CAR_TARE_WEIGHT_MAX_SIZE = 5;
	public static final int CAR_TARE_WEIGHT_MIN_VALUE = 1;
	public static final int DAMAGE_IND_MIN_SIZE = 1;
	public static final int DAMAGE_IND_MAX_SIZE = 1;
	public static final int BAD_ORDER_IND_MIN_SIZE = 1;
	public static final int BAD_ORDER_IND_MAX_SIZE = 1;
	public static final int PLATFORM_HEIGHT_MIN_SIZE = 1;
	public static final int PLATFORM_HEIGHT_MIN_VALUE = 0;
	public static final int PLATFORM_HEIGHT_MAX_SIZE = 5;
	public static final int ARTICULATE_MIN_SIZE = 1;
	public static final int ARTICULATE_MAX_SIZE = 2;
	public static final int NR_OF_AXLES_MIN_SIZE = 1;
	public static final int NR_OF_AXLES_MAX_SIZE = 5;
	public static final int CAR_LOAD_LIMIT_MIN_SIZE = 1;
	public static final int CAR_LOAD_LIMIT_MAX_SIZE = 5;
	public static final int CAR_LOAD_LIMIT_MIN_VALUE = 1;
	public static final int CAR_OWNER_TYPE_MIN_SIZE = 1;
	public static final int CAR_OWNER_TYPE_MAX_SIZE = 1;
	public static final int PLATFORM_HEIGHT_MAX_VALUE = 120;
	public static final int CORP_PRIMARY_6_MAX_SIZE = 6;

	public static final int CUST_PRIMARY_6_MIN_SIZE = 6;

	public static final int CUST_PRIMARY_6_MAX_SIZE = 6;

	public static final int Load_Empty_Status_MIN_SIZE = 1;
	public static final int Load_Empty_Status_MAX_SIZE = 1;


	/* TRUCKER GROUP */

	public static final int TRUCKER_GROUP_CODE_MIN_SIZE = 1;
	public static final int TRUCKER_GROUP_CODE_MAX_SIZE = 10;
	public static final int TRUCKER_GROUP_DESC_MIN_SIZE = 1;
	public static final int TRUCKER_GROUP_DESC_MAX_SIZE = 40;
	public static final int SETUP_SCHEMA_MIN_SIZE = 1;
	public static final int SETUP_SCHEMA_MAX_SIZE = 8;
	
	/* Pool Terminal Setup */
	public static final int POOL_NAME_MAX_SIZE = 10;
	public static final int POOL_DESC_MAX_SIZE = 30;
	public static final int POOL_RSRV_TP_MAX_SIZE = 2;
	public static final int POOL_AGREEMENT_REQ_MAX_SIZE = 1;

	/*Storage Bill To Party*/
	public static final int OVERRIDE_IND_MAX_SIZE = 1;

	/* Ports */
	public static final int PORT_CODE_MAX_SIZE = 3;
	public static final int PORT_NAME_MAX_SIZE = 19;
	public static final int PORT_CITY_MAX_SIZE = 19;
	public static final int PORT_CITY_GOOD_SPELL_MAX_SIZE = 10;
	public static final int PORT_STATE_MAX_SIZE = 2;
	public static final int PORT_COUNTRY_MAX_SIZE = 3;
	
	/*Pool Equip controller*/
	public static final int CUST_PRI_6_MAX_SIZE = 6;

	/* Pool Equipment range */
	
	/* Reservation Type Selection */
	public static final int RSRV_TP_MAX_SIZE = 20;
	public static final int IND_MAX_SIZE = 1;

	public static final int POOL_ID_MAX_SIZE = 15;

	public static final String MAPPER_COMPONENT_MODEL="spring";
	public static final String ADD_SUCCESS_MESSAGE="Successfully added data!";
	public static final String DELETE_SUCCESS_MESSAGE="Successfully deleted data!";
	public static final String RETRIVED_SUCCESS_MESSAGE="Successfully retrieved data!";
	public static final String NO_RECORD_FOUND_MESSAGE="No records found!";
	public static final String UPDATE_FAILED_MESSAGE="Update failed!";
	public static final String VALIDATED_SUCCESS_MESSAGE="Successfully validated data!";

	public static final String EXTENSION_SCHEMA_ERROR_MESSAGE="Extension Schema should not be null, empty or blank";
	
	public static final int SMALL_INT_DATA_TYPE_MAX_VALUE = 32767;
	
	public static final String TRAILER_RSRV_TYPE = "RR";
	
	public static final String UPDATE_SUCCESS_MESSAGE="Successfully updated data!";
	public static final String EXTENSION_SCHEMA_EXCEPTION_MESSAGE="Extension Schema should not be null, empty or blank";

	/* Environment Variables - application.properties */

	public static final String DATASOURCE_URL= "spring.datasource.url";
	public static final String APP_NAME = "OBIS BUSINESS SERVICES";
	public static final int CUST_ID_MIN_SIZE = 1;

	public static final int[] EQ_LENGTH_ARRAY = {20,28,40,45,48,53};

	/* Base Umler Car */
	public static final int SIN_MUL_AAR_IND_MIN = 1;
	public static final int SIN_MUL_AAR_IND_MAX = 1;
	public static final int AAR_TP_MIN = 1;
	public static final int AAR_TP_MAX = 4;
	public static final int AAR_CD_MIN = 1;
	public static final int AAR_CD_MAX = 1;
	public static final int AAR_1ST_NO_LOW_MIN = 1;
	public static final int AAR_1ST_NO_LOW_MAX = 1;
	public static final int AAR_1ST_NO_HIGH_MIN = 1;
	public static final int AAR_1ST_NO_HIGH_MAX = 1;
	public static final int AAR_2ND_NO_LOW_MIN = 1;
	public static final int AAR_2ND_NO_LOW_MAX = 1;
	public static final int AAR_2ND_NO_HIGH_MIN = 1;
	public static final int AAR_2ND_NO_HIGH_MAX = 1;
	public static final int AAR_3RD_NO_MIN = 1;
	public static final int AAR_3RD_NO_MAX = 1;
	public static final int CONV_CAR_OWNER_MIN_SIZE = 1;
	public static final int CONV_CAR_OWNER_MAX_SIZE = 8;
	public static final int TOFC_COFC_IND_MIN_SIZE = 1;
	public static final int MIN_EQ_WIDTH_MAX_SIZE = 5;
	public static final int MAX_EQ_WIDTH_MAX_SIZE = 5;
	public static final int SIN_DBLE_WELL_IND_MIN_SIZE = 1;
	public static final int SIN_DBLE_WELL_IND_MAX_SIZE = 1;
	public static final int MIN_EQ_LEN_MAX_SIZE = 5;
	public static final int MAX_EQ_LEN_MAX_SIZE = 5;
	public static final int MIN_TRLR_LEN_MAX_SIZE = 4;
	public static final int MAX_TRLR_LEN_MAX_SIZE = 4;
	public static final int AGGRGTE_LEN_MAX_SIZE = 5;
	public static final int TOT_COFC_LEN_MAX_SIZE = 5;
	public static final int ACCPT_2_20_C_IND_MIN_SIZE = 1;
	public static final int ACCPT_2_20_C_IND_MAX_SIZE = 1;
	public static final int ACCPT_3_28_T_IND_MIN_SIZE = 1;
	public static final int ACCPT_3_28_T_IND_MAX_SIZE = 1;
	public static final int ACCPT_NOSE_MNT_REEFER_MIN_SIZE = 1;
	public static final int ACCPT_NOSE_MNT_REEFER_MAX_SIZE = 1;
	public static final int REEFER_A_WELL_IND_MIN_SIZE = 1;
	public static final int REEFER_A_WELL_IND_MAX_SIZE = 1;
	public static final int REEFER_TOFC_IND_MIN_SIZE = 1;
	public static final int REEFER_TOFC_IND_MAX_SIZE = 1;
	public static final int REEFER_T40_IND_MIN_SIZE = 1;
	public static final int REEFER_T40_IND_MAX_SIZE = 1;
	public static final int MAX_LOAD_WGT_MAX_SIZE = 6;
	public static final Integer[] VALID_CONT_LENGTHS = {20, 40, 45, 48, 53};
	public static final Integer[] VALID_TRLR_LENGTHS = {28, 40, 45, 48, 53};

	/* Umler Low Profile */
	public static final int NR_OF_PLATFORM_MAX_SIZE = 5;
	public static final int PLATFORM_CAR_IND_MIN_SIZE = 1;
	public static final int PLATFORM_CAR_IND_MAX_SIZE = 1;
	public static final int T_WELL_IND_MIN_SIZE = 1;
	public static final int T_WELL_IND_MAX_SIZE = 1;
	public static final int Q_WELL_IND_MIN_SIZE = 1;
	public static final int Q_WELL_IND_MAX_SIZE = 1;
	public static final int L3_MIN_EQ_LENGTH_MAX_SIZE = 5;
	public static final int L3_MAX_EQ_LENGTH_MAX_SIZE = 5;
	public static final int L3_CENTER_MIN_LENGTH_MAX_SIZE = 5;
	public static final int L4_MIN_EQ_LENGTH_MAX_SIZE = 5;
	public static final int L4_MAX_EQ_LENGTH_MAX_SIZE = 5;
	public static final int L4_2_UNITS_TOTAL_LENGTH_MAX_SIZE = 5;
	public static final int CONT_PAIRS_WELL_IND_MIN_SIZE = 1;
	public static final int CONT_PAIRS_WELL_IND_MAX_SIZE = 1;
	public static final int CONT_PAIRS_MIN_LENGTH_MIN_SIZE = 1;
	public static final int CONT_PAIRS_MIN_LENGTH_MAX_SIZE = 4;
	public static final int CONT_PAIRS_MAX_LENGTH_MAX_SIZE = 4;
	public static final int ACCEPT_TRAILER_PAIR_IND_MIN_SIZE = 1;
	public static final int ACCEPT_TRAILER_PAIR_IND_MAX_SIZE = 1;
	public static final int TRAILER_PAIR_MIN_LENGTH_MAX_SIZE = 4;
	public static final int TRAILER_PAIR_MAX_LENGTH_MAX_SIZE = 4;
	public static final int REEFER_WELL_IND_MIN_SIZE = 1;
	public static final int REEFER_WELL_IND_MAX_SIZE = 1;
	public static final int ACCEPT_CHASSIS_BUMPER_MIN_SIZE = 1;
	public static final int ACCEPT_CHASSIS_BUMPER_MAX_SIZE = 1;

	/* Conventional Equipment Width */
	public static final int AAR_1ST_NO_MIN_SIZE = 1;
	public static final int AAR_1ST_NO_MAX_SIZE = 1;
	public static final int CONV_CAR_MIN_EQ_WIDTH_MAX_SIZE = 4;
	public static final int CONV_CAR_MAX_EQ_WIDTH_MAX_SIZE = 4;
	
	/*STACK CAR*/
	public static final int LR_IND_MAX_SIZE = 1;
	public static final int LR_IND_MIN_SIZE = 1;
	public static final int PLAT_CAR_IND_MIN_SIZE = 1;
	public static final int PLAT_CAR_IND_MAX_SIZE = 8;
	public static final int END_EQ_LEN_MIN_SIZE = 1;
	public static final int END_EQ_LEN_MAX_SIZE = 5;
	public static final int TOP_MIN_EQ_LGTH_SIZE = 5;
	public static final int TOP_MAX_EQ_LGTH_SIZE = 5;
	public static final int NO_IND_MAX_SIZE = 1;
	public static final int NO_IND_MIN_SIZE = 1;
	public static final int EQ_PRS_IND_MAX_SIZE = 1;
	public static final int EQ_PRS_MAX_SIZE = 5;
	public static final int EQ_PRS_IND_MIN_SIZE = 1;
	public static final int EQ_PRS_MIN_SIZE = 5;
	public static final int TOP_PRS_IND_MIN_SIZE = 5;
	public static final int TOP_PRS_IND_MAX_SIZE = 5;
	public static final int COND_MAX_EQ_LGTH_SIZE = 5;
	public static final int EQ_PRS_LGTH_MIN_SIZE = 1;
	public static final int EQ_PRS_LGTH_MAX_SIZE = 1;
	public static final int ACPT_TRLR_IND_MAX_SIZE = 1;
	public static final int ACPT_TRLR_IND_MIN_SIZE = 1;
	public static final int TRLR_PRS_MIN_SIZE = 5;
	public static final int TRLR_PRS_MAX_SIZE = 5;
	public static final int ACCPT_CHAS_MAX_SIZE = 1;
	public static final int ACCPT_CHAS_MIN_SIZE = 1;
	public static final int END_WELL_LGTH_MAX_SIZE = 5;
	public static final int MED_WELL_LGTH_MAX_SIZE = 5;
	public static final int MAX_EQ_LGTH_MAX_SIZE = 5;
	public static final int MIN_EQ_LGTH_MAX_SIZE = 5;
	
}
