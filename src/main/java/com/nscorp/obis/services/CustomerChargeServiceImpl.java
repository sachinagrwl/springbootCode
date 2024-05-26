package com.nscorp.obis.services;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nscorp.obis.common.CommonConstants;
import com.nscorp.obis.common.UserId;
import com.nscorp.obis.domain.CashException;
import com.nscorp.obis.domain.Customer;
import com.nscorp.obis.domain.CustomerCharge;
import com.nscorp.obis.domain.GenericCodeUpdate;
import com.nscorp.obis.domain.MoneyReceived;
import com.nscorp.obis.domain.PayGuarantee;
import com.nscorp.obis.domain.SecUser;
import com.nscorp.obis.domain.ShipVessel;
import com.nscorp.obis.domain.Shipment;
import com.nscorp.obis.domain.ShipmentDlvyDate;
import com.nscorp.obis.domain.ShipmentExt;
import com.nscorp.obis.domain.StorageRates;
import com.nscorp.obis.domain.Terminal;
import com.nscorp.obis.dto.CustomerChargeDTO;
import com.nscorp.obis.dto.mapper.CustomerChargeMapper;
import com.nscorp.obis.exception.InvalidDataException;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.repository.CashExceptionRepository;
import com.nscorp.obis.repository.CustomerChargeRepository;
import com.nscorp.obis.repository.CustomerRepository;
import com.nscorp.obis.repository.GenericCodeUpdateRepository;
import com.nscorp.obis.repository.MoneyReceivedRepository;
import com.nscorp.obis.repository.PayGuaranteeRepository;
import com.nscorp.obis.repository.SecUserRepository;
import com.nscorp.obis.repository.ShipEntityRepo;
import com.nscorp.obis.repository.ShipVesselRepository;
import com.nscorp.obis.repository.ShipmentDlvyDateRepository;
import com.nscorp.obis.repository.ShipmentExtRepository;
import com.nscorp.obis.repository.ShipmentRepository;
import com.nscorp.obis.repository.StorageRatesRepository;
import com.nscorp.obis.repository.TerminalRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public class CustomerChargeServiceImpl implements CustomerChargeService {

	@Autowired
	CustomerChargeRepository customerChargeRepository;

	@Autowired
	MoneyReceivedRepository moneyReceivedRepo;

	@Autowired
	StorageRatesRepository storageRatesRepo;

	@Autowired
	CustomerRepository customerRepo;

	@Autowired
	GenericCodeUpdateRepository genericCodeUpdateRepo;

	@Autowired
	ShipmentRepository shipmentRepo;

	@Autowired
	ShipVesselRepository shipVesselRepo;

	@Autowired
	ShipmentExtRepository shipmentExtRepo;

	@Autowired
	TerminalRepository terminalRepo;

	@Autowired
	ShipmentDlvyDateRepository shipmentDlvyDateRepo;

	@Autowired
	PayGuaranteeRepository payGuaranteeRepo;

	@Autowired
	SecUserRepository secUserRepo;
	@Autowired
	ShipEntityRepo shipEntityRepo;

	@Autowired
	CashExceptionRepository cashExceptionRepository;

	public List<CustomerChargeDTO> getStorageCharge(String equipInit, Integer equipNbr) {
		log.info("CustomerChargeServiceImpl : fetchCustomers : Method Starts");
		List<CustomerCharge> customerCharges = new ArrayList<>();
		if (equipInit != null && equipNbr != null) {
			customerCharges = customerChargeRepository.findByEquipInitAndEquipNbrOrderByBgnLclDtTmDesc(equipInit,
					equipNbr);
		}
		if (customerCharges.isEmpty()) {
			throw new NoRecordsFoundException("No records found under this search!");
		}
		List<CustomerChargeDTO> customerChargeDTOs = new ArrayList<>();
		customerCharges.stream().forEach(customerCharge -> {
			CustomerChargeDTO customerChargesDto = CustomerChargeMapper.INSTANCE
					.CustomerChargeToCustomerChargeDTO(customerCharge);
			Customer customer = new Customer();
			StorageRates strRate = new StorageRates();
			if (customerChargesDto.getChrgTp().equalsIgnoreCase("STO")
					|| customerChargesDto.getChrgTp().equalsIgnoreCase("CAR")) {
				strRate = storageRatesRepo.findByStorageId(customerChargesDto.getRateId());
				if (strRate != null && strRate.getBillCustId() != null) {
					customer = customerRepo.findByCustomerId(strRate.getBillCustId());

				} else {
					customer = customerRepo.findByCustomerId(customerChargesDto.getBillCustId());
				}

			} else {
				customer = customerRepo.findByCustomerId(customerChargesDto.getBillCustId());
			}
			if (customer != null) {
				customerChargesDto.setCustomerName(customer.getCustomerName());
				customerChargesDto.setCustomerNbr(customer.getCustomerNumber());
			}

			GenericCodeUpdate generic = genericCodeUpdateRepo
					.findByGenericTableCodeAndGenericTable(customerChargesDto.getChrgTp(), "TP_SVC");
			customerChargesDto.setChrgTypeCode(generic.getGenericLongDescription());

			if (customerChargesDto.getChrgTp().equalsIgnoreCase("PDM")) {
				if (customerChargesDto.getEquipType().equalsIgnoreCase("C")) {
					customerChargesDto.setChrgCd("235");
				} else {
					customerChargesDto.setChrgCd("233");
				}
			}
			Terminal bgnTerminal = terminalRepo.findByTerminalId(customerChargesDto.getBgnTermId());
			if (bgnTerminal != null)
				customerChargesDto.setBeginTerminalName(bgnTerminal.getTerminalName());

			Terminal endTerminal = terminalRepo.findByTerminalId(customerChargesDto.getEndTermId());
			if (endTerminal != null)
				customerChargesDto.setEndTerminalName(endTerminal.getTerminalName());

			if (customerChargesDto.getChrgAmt() == null) {
				if (strRate != null) {
					if (strRate.getPeakRt1Amt() != null) {
						if (System.currentTimeMillis() < strRate.getPeakBgnDtTm().getTime()
								|| System.currentTimeMillis() >= strRate.getPeakEndDtTm().getTime()) {
							customerChargesDto.setRateTypeCode("OFF-PEAK");
						} else {
							customerChargesDto.setRateTypeCode("PEAK");
						}
					} else {
						customerChargesDto.setRateTypeCode("SINGLE");
					}
				} else {
					customerChargesDto.setRateTypeCode("SINGLE");

				}

			} else if (customerChargesDto.getPeakRtInd() != null
					&& customerChargesDto.getPeakRtInd().equalsIgnoreCase("P")) {
				customerChargesDto.setRateTypeCode("PEAK");
			} else if (customerChargesDto.getPeakRtInd() != null
					&& customerChargesDto.getPeakRtInd().equalsIgnoreCase("O"))
				customerChargesDto.setRateTypeCode("OFF-PEAK");
			else {
				customerChargesDto.setRateTypeCode("SINGLE");
			}
			ShipVessel shipVessel = new ShipVessel();
			ShipmentExt shipmentExt = new ShipmentExt();

			ShipmentDlvyDate shipmentDlvy = shipmentDlvyDateRepo.findBySvcId(customerChargesDto.getSvcId());
			Shipment shipment = null;
			boolean specEndorCd = false;
			shipment = shipmentRepo.findBySvcId(customerChargesDto.getSvcId());

			if (shipment != null) {
				customerChargesDto.setWbSerNr(shipment.getWbSerNr());
			}
			if (strRate != null && strRate.getSchedDelRateInd() != null
					&& strRate.getSchedDelRateInd().equalsIgnoreCase("Y")) {

				if (shipment != null && shipment.getReqDlvyEta() != null)
					customerChargesDto.setSchdDeliveryDTM(shipment.getReqDlvyEta().toString());
			}
			shipVessel = shipVesselRepo.findBySvcIdAndVesselDirCd(customerChargesDto.getSvcId(), "I");
			if (shipVessel != null && shipVessel.getVesselSailDt() != null) {
				shipmentExt = shipmentExtRepo.findBySvcId(customerChargesDto.getSvcId());
				if (shipmentExt != null) {
					if (shipmentExt.getSpecEndorCd1().equalsIgnoreCase("XP")
							|| shipmentExt.getSpecEndorCd2().equalsIgnoreCase("XP")
							|| shipmentExt.getSpecEndorCd3().equalsIgnoreCase("XP")
							|| shipmentExt.getSpecEndorCd4().equalsIgnoreCase("XP")
							|| shipmentExt.getSpecEndorCd5().equalsIgnoreCase("XP")
							|| shipmentExt.getSpecEndorCd6().equalsIgnoreCase("XP")
							|| shipmentExt.getSpecEndorCd7().equalsIgnoreCase("XP")
							|| shipmentExt.getSpecEndorCd8().equalsIgnoreCase("XP")
							|| shipmentExt.getSpecEndorCd9().equalsIgnoreCase("XP")
							|| shipmentExt.getSpecEndorCd10().equalsIgnoreCase("XP")
							|| shipmentExt.getSpecEndorCd11().equalsIgnoreCase("XP"))
						specEndorCd = true;
				}

			}
			if (shipmentDlvy != null && shipmentDlvy.getDlvyByDtTm() != null) {
				customerChargesDto.setDeliverByDTM(shipmentDlvy.getDlvyByDtTm().toString());
			} else if (shipment != null && shipVessel != null && (shipment.getShipStat() != null
					&& (shipment.getShipStat().equalsIgnoreCase("P") && specEndorCd)
					|| ((shipment.getShipStat().equalsIgnoreCase("A") || shipment.getShipStat().equalsIgnoreCase("C"))
							&& (shipVessel.getSbValidInd() != null && (shipVessel.getSbValidInd().equalsIgnoreCase("V")
									|| shipVessel.getSbValidInd().equalsIgnoreCase("U")
									|| shipVessel.getSbValidInd().equalsIgnoreCase("I") || specEndorCd))))) {
				customerChargesDto.setSailByDTM(shipVessel.getVesselSailDt().toString());
			} else {
				customerChargesDto.setDeliverOrSailBy(null);
			}

			PayGuarantee payGuarantee = payGuaranteeRepo.findByChrgId(customerChargesDto.getChrgId());
			if (payGuarantee != null) {
				customerChargesDto.setGuarantee("Y");
				customerChargesDto.setCreateDtTm(payGuarantee.getCreateDateTime());
			} else
				customerChargesDto.setGuarantee("N");
			List<MoneyReceived> moneyReceivedList = moneyReceivedRepo.findByChrgId(customerChargesDto.getChrgId());
			customerChargesDto.setMoneyReceivedList(moneyReceivedList);
			customerChargeDTOs.add(customerChargesDto);
		});
		return customerChargeDTOs;
	}

	@Override
	public CustomerCharge updateCustomerCharge(CustomerChargeDTO customerChargeDTO, Map<String, String> headers) {
		UserId.headerUserID(headers);
		if (!customerChargeRepository.existsById(customerChargeDTO.getChrgId())) {
			throw new NoRecordsFoundException("No Record Found!");
		}

		CustomerCharge customerCharge = CustomerChargeMapper.INSTANCE
				.CustomerChargeDTOToCustomerCharge(customerChargeDTO);
		customerCharge.setUpdateDateTime(new Timestamp(System.currentTimeMillis()));
		customerCharge.setUpdateUserId(headers.get(CommonConstants.USER_ID).toUpperCase());
		customerCharge.setUpdateExtensionSchema(headers.get(CommonConstants.EXTENSION_SCHEMA).toUpperCase());
		if (StringUtils.isNotEmpty(customerCharge.getUversion())) {
			customerCharge.setUversion(
					Character.toString((char) ((((int) customerCharge.getUversion().charAt(0) - 32) % 94) + 33)));
		}
		if(customerCharge.getLastFreeDtTm()!=null){
		if (customerCharge.getLastFreeDtTm().before(customerCharge.getBgnLclDtTm())) {
			throw new InvalidDataException("Last Free Day is less than Beginning Event Date");
		}}
		if (customerChargeDTO.isActiveCharge()) {// Adjust Charge
			if (customerCharge.getBillReleaseDate() == null) {
				if (customerCharge.getRateType().equalsIgnoreCase("M")) {
					throw new InvalidDataException("Cannot alter monthly charges");
				} else {
					SecUser secUser = secUserRepo.findBySecUserId(headers.get("userid"));
					if (secUser != null) {
						customerCharge.setOverrideNm(secUser.getSecUserName().trim());
						customerCharge.setOverrideUserId(secUser.getSecUserId().trim());
						customerCharge.setOverrideTitle(secUser.getSecUserTitle().trim());
					}
					customerCharge = customerChargeRepository.save(customerCharge);
					return customerCharge;
				}
			} else {
				throw new InvalidDataException("Cannot alter charges � charge has been sent to waybilling");
			}
		}
		if (customerChargeDTO.isCifOverride()) {// CIF Override
			if (customerCharge.getBillReleaseDate() == null) {
				if (customerCharge.getRateType().equalsIgnoreCase("M")) {
					throw new InvalidDataException("Cannot alter monthly charges");
				} else {
					if (customerCharge.getSvcId() != null) {
						int count = shipEntityRepo.findAllBySegTypeAndSvcId("BN", customerChargeDTO.getSvcId()).size();
						if (count > 1) {
							throw new InvalidDataException("multiple bnf owners");
						} else {
							String entCustNr = shipEntityRepo.getEntityCustomerNr(customerCharge.getSvcId());
							String customerName = customerRepo.findByCustomerId(customerCharge.getBillCustId())
									.getCustomerName();
							CashException cashException = new CashException();
							cashException.setCashExceptionId(cashExceptionRepository.SGK());
							cashException.setEquipId(customerCharge.getEquipId());
							cashException.setEquipNbr(customerCharge.getEquipNbr());
							cashException.setEquipInit(customerCharge.getEquipInit());
							cashException.setEquipType(customerCharge.getEquipType());
							cashException.setCustomerName(customerName);
							if(entCustNr!=null)
								cashException.setCustomerPrimarySix(entCustNr.substring(0, 5));
							cashException.setEffectiveDate(customerCharge.getBgnLclDtTm().toLocalDateTime().toLocalDate());
							cashException.setEndDate(customerCharge.getBgnLclDtTm().toLocalDateTime().plusHours(15).toLocalDate());
							cashException.setUpdateDateTime(new Timestamp(System.currentTimeMillis()));
							cashException.setUpdateUserId(headers.get(CommonConstants.USER_ID).toUpperCase());
							cashException.setUpdateExtensionSchema(
									headers.get(CommonConstants.EXTENSION_SCHEMA).toUpperCase());
							cashException.setUversion("!");
							cashException.setCreateDateTime(new Timestamp(System.currentTimeMillis()));
							cashException.setCreateUserId(headers.get(CommonConstants.USER_ID).toUpperCase());
							CashException cashException1 = cashExceptionRepository.save(cashException);
							customerCharge.setCashExcpId(cashException1.getCashExceptionId());
							customerCharge = customerChargeRepository.save(customerCharge);
							return customerCharge;
						}
					}
				}
			} else {
				throw new InvalidDataException("Cannot alter charges � charge has been sent to waybilling");
			}
		}
		if (customerChargeDTO.isVoidRecord()) {// Void
			if (customerCharge.getBillReleaseDate() == null) {
				SecUser secUser = secUserRepo.findBySecUserId(headers.get("userid"));
				if (secUser != null) {
					customerCharge.setOverrideNm(secUser.getSecUserName().trim());
					customerCharge.setOverrideUserId(secUser.getSecUserId().trim());
					customerCharge.setOverrideTitle(secUser.getSecUserTitle().trim());
				}
				customerCharge.setEndTermId(customerCharge.getBgnTermId());
				customerCharge.setEndEvtCd("VOID");
				customerCharge.setEndLclDtTm(new Timestamp(System.currentTimeMillis()));
				customerCharge.setChrgAmt(BigDecimal.valueOf(0));
				customerCharge.setChrgBaseDays(0);
				customerCharge.setChrgDays(0);
				customerCharge = customerChargeRepository.save(customerCharge);
				return customerCharge;
			} else {
				throw new InvalidDataException("Cannot void - charge has been sent to waybilling");
			}
		}

		if (customerChargeDTO.isLastFreeDayModified()) {
			if (customerCharge.getBillReleaseDate() == null ) {
				if(customerCharge.getLastFreeDtTm() == null){
					throw new InvalidDataException("Last Free Day is less than Beginning Event Date");
				}
				int baseDays = (int) TimeUnit.MILLISECONDS
						.toDays(System.currentTimeMillis() - customerCharge.getBgnLclDtTm().getTime());
				customerCharge.setChrgBaseDays(baseDays);
				if (customerCharge.getLastFreeDtTm().getTime() >= System.currentTimeMillis()) {
					customerCharge.setChrgDays(0);
					customerCharge.setChrgAmt(new BigDecimal("0.00"));
					customerCharge = customerChargeRepository.save(customerCharge);
					return customerCharge;
				}
				else {
					int days = (int) TimeUnit.MILLISECONDS
							.toDays((System.currentTimeMillis() - customerCharge.getLastFreeDtTm().getTime()));
					customerCharge.setChrgDays(days);
					StorageRates strRate = new StorageRates();
					strRate = storageRatesRepo.findByStorageId(customerCharge.getRateId());
					if (customerCharge.getRateType().equalsIgnoreCase("D")
							|| customerCharge.getRateType().equalsIgnoreCase("H")) {
						if (strRate != null && (strRate.getRateDDLmt() == null) || ((strRate.getRateDDLmt() != null)
								&& (days <= (strRate.getRateDDLmt() - strRate.getFreeDDLmt())))) {
							int chrgAmt = strRate.getRate1Amt().intValue() * days;
							customerCharge.setChrgAmt(BigDecimal.valueOf(chrgAmt));
						} else if ((strRate.getRateDDLmt() != null)
								&& (days > (strRate.getRateDDLmt() - strRate.getFreeDDLmt()))) {
							int chrgAmt = (strRate.getRate1Amt().intValue()
									* (strRate.getRateDDLmt() - strRate.getFreeDDLmt()))
									+ (strRate.getRate2Amt().intValue()
											* (days - (strRate.getRateDDLmt() - strRate.getFreeDDLmt())));
						}

					}
					customerCharge = customerChargeRepository.save(customerCharge);
					return customerCharge;
				}
			} else {
				throw new InvalidDataException("Cannot void - charge has been sent to waybilling");
			}
		}

		return customerCharge;
	}
}
