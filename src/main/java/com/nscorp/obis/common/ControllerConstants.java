package com.nscorp.obis.common;

public class ControllerConstants {

	public static final String URI = "obis/business/services/intermodal";
	public static final String GENERIC_TABLE_LIST = URI + "/generictable";
	public static final String GET_GENERIC_CODE_TABLE = URI + "/genericcodelist/table/{tableName}";
	public static final String EQUIPMENT_TARE_WEIGHTS = URI + "/equipmenttareweights";
	public static final String POSITIONAL_WEIGHT_LIMITS = URI + "/positionalloadlimits";
	public static final String GET_SEC_RESOURCE = URI + "/resourcelist";
	public static final String ADD_GENERIC_TABLE = URI + "/generictablelist/add";

	public static final String ROAD_MAINTENANCE = URI + "/dest-set";
	public static final String STEAMSHIP_CUSTOMERS = URI + "/steamshipcustomers";
	public static final String ADD_STEAMSHIP_CUSTOMERS = URI + "/steamshipcustomers/add";
	public static final String GET_CORPORATE_CUSTOMERS = URI + "/corporatecustomers";

	public static final String DELETE_STEAMSHIP_CUSTOMERS = URI + "/steamshipcustomers/delete";

	public static final String TERMINAL_NOTIFY_PROFILES = URI + "/terminalNotifyProfile";
	public static final String UPDATE_TERMINAL_NOTIFY_PROFILES = URI + "/terminalNotifyProfile/update";
	public static final String GET_CUSTOMER_NOTIFY_PROFILES = URI + "/customerNotifyProfile";
	public static final String STATION_RESTRICTIONS = URI + "/station/{term-id}/station-restrictions";

	public static final String AAR_TYPE_LIST = URI + "/aar-types";

	public static final String NOTIFY_PROFILE_METHOD = URI + "/notify-profile-methods";
	public static final String CUSTOMER_NOTIFY_PROFILE = URI + "/customer-notify-profile";
	public static final String STATION = URI + "/stations";

	public static final String EMS_INGATE_RESTRICTION = URI + "/ems-ingate-restrictions";

	public static final String STATION_CROSS = URI + "/stationxref";
	public static final String DESTINATION_TERMINAL_NOTIFY_PROFILE = URI + "/destination-terminal-notify-profiles";

	public static final String GET_TERMINAL_TRAINS = URI + "/terminal-trains";

	public static final String NOTIFICATION_TYPES = URI + "/notification_types";

	public static final String TERMINAL = URI + "/terminals";
	public static final String GET_BLOCKS = URI + "/blocks";
	public static final String UPDATE_TERM_TRAIN = URI + "/term-trains";
	public static final String DELETE_TERMINAL_TRAINS = URI + "/term-trains";
	public static final String DELETE_BLOCKS = URI + "/blocks";
	public static final String EMS_INGATE_ALLOCATION = URI + "/ems-ingate-allocation";

	public static final String TERM_FREE_DAY = URI + "/term-free-day";

	public static final String CUSTOMER_TERM = URI + "/customer-list";
	public static final String CUSTOMER_TERMINAL = URI + "/customer-terminals";

	public static final String GET_TERMINAL = URI + "/terminal";
	public static final String AAR_TYPE = URI + "/aar-type";

	public static final String AAR_HITCH = URI + "/aar-hitch";

	public static final String EQUIPMENT_AAR_600_CONT = URI + "/eq-aar-cont-maint";
	public static final String EQ_OWNR_PREFIX = URI + "/eq-owner";
	public static final String EQ_INIT_SPEED_CODE = URI + "/eq-init-speed-cd";

	public static final String RULES_CIRCULAR = URI + "/rules-circular";

	public static final String CUSTOMER_NICKNAME = URI + "/customer-nickname";
	public static final String CUSTOMER = URI + "/customer";
	public static final String CUSTOMER_INDEX = URI + "/customer-index";
	public static final String ICHG_PARTY = URI + "/ichg-party";
	public static final String CUSTOMER_LOCALINFO = URI + "/customer-localinfo";
	public static final String CASH_EXCEPTION = URI + "/cash-exception";
	public static final String TRAIN = URI + "/train-number";
	public static final String TRAIN_CHANGE = URI + "/term-trains-change";

	public static final String EQ_OVERRIDE_TARE_WGT = URI + "/eq-override-tare-wgt";

	public static final String EQUIPMENT_RESTRICT = URI + "/restrict-equipment-stacking";

	public static final String EQ_RACK_RANGE = URI + "/equipment-rack-range";

	public static final String FMCSA_EXPIRATION = URI + "/fmcsa-expiration";

	public static final String EQUIPMENT = URI + "/equipment";

	public static final String AAR_TYPE_LISTS = URI + "/aar-type";
	public static final String POOL = URI + "/pool";
	public static final String POOL_TERMINAL = URI + "/pool-terminal";
	public static final String EQ_EMBARGO = URI + "/eq-embargo";
	public static final String GET_TIA_TKR = URI + "/tia-tkr";
	public static final String CUSTOMER_POOL = URI + "/customer-pool";
	public static final String NOTEPAD_ENTRY = URI + "/notepad-entry";
	public static final String CONTAINER_CHASSIS_ASSOC = URI + "/container-chassis";

	public static final String CASH_IN_FIST = URI + "/cif-excp";
	public static final String NOTIFY_ACTIVE_SHIPMENT = URI + "/ntfyActiveShipment";

	public static final String GET_RENOTIFY = URI + "/renotify";
	public static final String DRAYAGE_CUSTOMER = URI + "/drayage-cust";
	public static final String DRAYAGE_CUSTOMER_PRIMARY_SIX = URI + "/drayage-cust-primsix";
	public static final String DRAYAGE_COMPANY = URI + "/drayage-company";
	public static final String DRAYAGE_SCAC = URI + "/drayage-scac";

	public static final String CAR_EMBARGO = URI + "/railcar-embargo";

	public static final String DAMAGE_COMPONENT = URI + "/dmg-component";

	public static final String DAMAGE_CODE_CONVERSION = URI + "/dmge-code-conv";
	public static final String DAMAGE_COMP_LOC = URI + "/damage-comp-loc";
	public static final String HAZ_RESTRICTION = URI + "/haz-restriction";
	public static final String ROAD = URI + "/road";
	public static final String NOTIFY_DETAILS = URI + "/notify-details";

	public static final String CAR = URI + "/car";
	public static final String CORPORATE_CUSTOMER_MAINTENANCE = URI + "/corpCustMaint";
	public static final String CORP_CUST_DTL = URI + "/corpCustDtl";
	public static final String TRUCKER_GROUP = URI + "/trucker-group";
	public static final String STORAGECHARGE = URI + "/storageCharge";
	public static final String STORAGE_RATES = URI + "/storageRates";

	public static final String PER_DIEM_RATES = URI + "/perdiem-rates";
	public static final String MONEY_RCD = URI + "/paymentReceived";

	public static final String POOL_STORAGE_EXEMPT = URI + "/pool-storage-exempt";

	public static final String POOL_TERMINAL_CONFLICT = URI + "/pool-terminal-conflict";
	
	public static final String STORAGE_OVERRIDE_BILL_TO_PARTY = URI + "/storage-override-bill-to-party";
	
	public static final String STATION_TERMINAL_HANDLE = URI + "/station-handle";

	public static final String DISPLAY_POOL_CUSTOMER = URI + "/display-pool-customer";
	public static final String PORTS = URI + "/ports";
	public static final String PAY_GUARANTEE =URI+ "/paymentGuarantee";
	public static final String MERGE_STATION_TERMINAL_HANDLE = URI + "/merge-station-handle";
	public static final String POOL_EQUIPMENT_CONTROLLER = URI + "/pool-equipment-controller";
	public static final String POOL_EQUIPMENT_RANGE = URI + "/pool-equipment-range";
	public static final String ENDORSEMENT_CODE =  URI + "/endorsement-cd";
	public static final String SPECIAL_ACTIVITY = URI + "/special_act_ntfy";
	public static final String SPECIAL_ACTIVITY_PROFILE = URI + "/special-activity-profile";
	public static final String SPECIAL_ACTIVITY_NOTIFY_PROFILE = URI + "/special-activity-notify-profile";

	public static final String HAZ_RESTR_PERMIT = URI + "/haz-restr-permit";

	public static final String GUARANTEE_CUSTOMER_CROSS_REF = URI + "/guar-cust";
	public static final String COMMODITY = URI + "/commodity";
	public static final String Un_Cd =  URI + "/un-cd";
	
	public static final String PLACARD_TP = URI + "/placard-tp";
	public static final String RSRV_TP_SELEC = URI + "/reservation-type-selection";

	public static final String BENEFICIAL_OWNER = URI + "/benfCustomer";

	public static final String BENEFICIAL_CUSTOMER_DETAILS_MAINTENANCE = URI + "/benfCustomerDtl";
	public static final String NS_EVENT_LOG = URI + "/ns-event-log";
	public static final String VOICE_NOTIFICATION_LIST = URI + "/voice-notify";
	public static final String CUST_INDEX = URI + "/cust-index";
	public static final String ARR_LOC_CODE = URI + "/aar-loc-cd";
	public static final String DAMAGE_CATEGORY = URI + "/damage-category";
	public static final String DAMAGE_LOCATION = URI + "/damage-location";
	public static final String DAMAGE_REASON = URI + "/damage-reason";
	public static final String STATE = URI + "/state";
	public static final String STATE_CITY = URI + "/state-city";

	public static final String HEALTH = URI + "/health";
	public static final String NOTIFY_RETRY = URI + "/notify-retry";
	public static final String NOTIFY_QUEUE_RETRY = URI + "/notify-queue-retry";
	
	public static final String TERMINAL_FUNCTION = URI + "/terminalFunction";
	
	public static final String DAMAGE_AREA = URI + "/damage-area";
	public static final String AAR_WHY_MADE_CODE = URI + "/aar-why-md-cd";

	public static final String DVIR_CODES = URI + "/dvir-code";
	public static final String NOTIFY_CUST_INIT = URI + "/notify-customer-init";
	public static final String NOTIFY_CUST_INIT_VIEW = URI + "/notify-customer-init-view";
	public static final String EQUIPMENT_CUST_RANGE = URI + "/eqpt-cust-range";
	public static final String STORAGE_RATE = URI + "/storage-rates";
	public static final String CONV_LOAD_CPBLTS = URI + "/conventional-load-capabilities";
	public static final String LOW_PROFILE_LOAD_CAPABILITIES = URI + "/low-profile-load-capabilities";
	public static final String DAMAGE_LOCATION_CONVERSIOND = URI + "/dmge_loc_conv";
	public static final String TERM_FREE_DAY_REASON_DESC =  "/term-free-reason-desc";
	public static final String DAMAGE_COMPONENT_REASON = URI + "/dmg-comp-reason";
	public static final String AAR_DAMAGE = URI + "/aar-damage";
	public static final String STACK_LOAD_CAPABILITIES = URI + "/stack-load-capabilities";

	public static final String DAMAGE_COMPONENT_SIZE= URI + "/damage-comp-size";
	public static final String DAMAGE_AREA_COMPONENT = URI + "/damage-area-comp";
	public static final String EQUIP_LESSEE_RANGE = URI + "/equipment-lessee-range";
	
	public static final String EQUIP_INIT =  "/equip-init";
	public static final String CORP_LONG_NAME =  "/corp-long-name";

}
