package com.nscorp.obis.services;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

import com.nscorp.obis.common.CommonConstants;
import com.nscorp.obis.exception.InvalidDataException;
import com.nscorp.obis.repository.CustomerInfoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.nscorp.obis.common.SpecificationGenerator;
import com.nscorp.obis.common.UserId;
import com.nscorp.obis.domain.EquipmentCustomerRange;
import com.nscorp.obis.domain.SortFilter;
import com.nscorp.obis.domain.StorageRates;
import com.nscorp.obis.domain.Terminal;
import com.nscorp.obis.dto.EquipmentCustomerRangeDTO;
import com.nscorp.obis.dto.StorageRatesDTO;
import com.nscorp.obis.dto.StorageRatesListDTO;
import com.nscorp.obis.dto.mapper.EquipmentCustomerRangeMapper;
import com.nscorp.obis.dto.mapper.StorageRateDetailMapper;
import com.nscorp.obis.dto.mapper.StorageRatesMapper;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordNotAddedException;
import com.nscorp.obis.repository.EquipmentCustomerRangeRepository;
import com.nscorp.obis.repository.StorageRatesRepository;
import com.nscorp.obis.repository.TerminalRepository;
import com.nscorp.obis.response.data.PaginatedResponse;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import com.nscorp.obis.domain.SortFilter;

@Service
@Transactional
@Slf4j
public class StorageRatesServiceImpl implements StorageRatesService {

	@Autowired
	EquipmentCustomerRangeRepository equipmentCustomerRangeRepository;

	@Autowired
	TerminalRepository terminalRepository;

	@Autowired
	StorageRatesRepository storageRatesRepository;

	@Autowired
	SpecificationGenerator specificationGenerator;

	@Autowired
	CustomerInfoRepository customerInfoRepository;

	@Autowired
	StorageRatesMapper mapper;

	@Override
	public PaginatedResponse<EquipmentCustomerRangeDTO> fetchEquipmentCustomerRange(Integer pageSize,
			Integer pageNumber) throws SQLException {

		Page<EquipmentCustomerRange> page = equipmentCustomerRangeRepository
				.findAll(PageRequest.of(pageNumber, pageSize));
		if (page.isEmpty()) {
			throw new NoRecordsFoundException("No records found");
		}
		List<EquipmentCustomerRangeDTO> EquipmentDTOs = new ArrayList<EquipmentCustomerRangeDTO>();
		page.stream().forEach((temp) -> {
			EquipmentDTOs
					.add(EquipmentCustomerRangeMapper.INSTANCE.EquipmentCustomerRangeToEquipmentCustomerRangeDTO(temp));
		});
		PaginatedResponse<EquipmentCustomerRangeDTO> paginatedResponse = PaginatedResponse.of(EquipmentDTOs, page);
		return paginatedResponse;
	}

	public PaginatedResponse<StorageRatesDTO> fetchStorageRates(String selectRateType, String incExpDate,
			String shipPrimSix, String customerPrimSix, String bnfPrimSix, String[] termId, String[] equipInit,
			String equipLgth, Integer pageSize, Integer pageNumber, String[] sort, String[] filter) throws SQLException {

		String Expired = "Y";
		Page<StorageRates> page;
		
		String[] defaultSort = {sort[0]+","+sort[1],"effectiveDate,asc" };

		page = storageRatesRepository.findAll(
				specificationGenerator.storageRatesSpecificationExpired(selectRateType, shipPrimSix, bnfPrimSix,
						customerPrimSix, equipInit, equipLgth, termId,filter),
				PageRequest.of(pageNumber, pageSize, Sort.by(SortFilter.sortOrder(defaultSort))));

		if (incExpDate != null) {
			if (incExpDate.equals(Expired)) {
				page = storageRatesRepository.findAll(
						specificationGenerator.storageRatesSpecification(selectRateType, shipPrimSix, bnfPrimSix,
								customerPrimSix, equipInit, equipLgth, termId,filter),
						PageRequest.of(pageNumber, pageSize, Sort.by(SortFilter.sortOrder(defaultSort))));
			}
		}

		if (page.isEmpty()) {
			throw new NoRecordsFoundException("No records found");
		}
		List<StorageRatesDTO> fetchStorageRatesDTOs = new ArrayList<StorageRatesDTO>();
		page.stream().forEach((temp) -> {
			fetchStorageRatesDTOs.add(StorageRateDetailMapper.INSTANCE.storageRatesToStorageRatesDTO(temp));
		});
		PaginatedResponse<StorageRatesDTO> paginatedResponse = PaginatedResponse.of(fetchStorageRatesDTOs, page);
		return paginatedResponse;
	}

	@Override
	public StorageRatesDTO updateStorageRate(StorageRatesDTO storageRatesDTO, Map<String, String> headers) {

		if (storageRatesDTO.getStorageId() == null)
			throw new InvalidDataException("Storage Id can't be null");

		StorageRates storageRates;
		storageRates = storageRatesRepository.findByStorageId(storageRatesDTO.getStorageId());
		if (storageRates == null)
			throw new NoRecordsFoundException("No record found for given Storage Rate Id");

		UserId.headerUserID(headers);
		String userId = headers.get(CommonConstants.USER_ID);
		storageRates.setUpdateUserId(userId.toUpperCase());
		storageRates.setUversion(storageRatesDTO.getUversion());
		storageRates.setNotepadTxt(storageRatesDTO.getNotepadTxt());
		Integer eqlength = null;
		if (storageRatesDTO.getEquipLgth() != null) {
			if (!(Arrays.stream((CommonConstants.EQ_LENGTH_ARRAY))
					.anyMatch(v -> v == storageRatesDTO.getEquipLgth()))) {
				throw new InvalidDataException("A Valid Eq Lgth must be provided");
			}
			eqlength = storageRatesDTO.getEquipLgth() * 12;
		}
		storageRates.setEquipLgth(eqlength);
		if (!Objects.equals(storageRates.getEndDate(), storageRatesDTO.getEndDate())) {
			if (storageRates.getEffectiveDate() != null && storageRatesDTO.getEndDate() != null
					&& (storageRates.getEffectiveDate().compareTo(storageRatesDTO.getEndDate()) > 0)) {
				throw new InvalidDataException("Ending date must be greater than effective date");
			}
			storageRates.setEndDate(storageRatesDTO.getEndDate());
		}
		if (storageRates.getEndDate() != null && storageRates.getEffectiveDate() == null) {
			throw new InvalidDataException("Effective date is mandatory for having end date!");
		}
		if (headers.get("extensionschema") != null)
			storageRates.setUpdateExtensionSchema(headers.get("extensionschema"));

		storageRates = storageRatesRepository.save(storageRates);
		StorageRatesDTO responseDTO = StorageRateDetailMapper.INSTANCE.storageRatesToStorageRatesDTO(storageRates);
		if(responseDTO.getEquipLgth()!=null){
			responseDTO.setEquipLgth(responseDTO.getEquipLgth()*12);
		}
		return responseDTO;
	}

	@Override
	public StorageRatesDTO addStorageRates(String selectRateType, String forceAdd, StorageRatesDTO storageRatesDTO,
			Map<String, String> headers) {

		UserId.headerUserID(headers);

		StorageRates entity = new StorageRates();

		String rateType1 = "Shipper";
		String rateType2 = "BenfCargoOwner";
		String rateType3 = "Equipment";
		String rateType4 = "EqptLength";
		String rateType5 = "NotifyParty";
		String rateType6 = "Terminal";
		if (selectRateType.equals(rateType1)) {
			entity = this.addShipper(storageRatesDTO, entity);
		}
		if (selectRateType.equals(rateType2)) {
			entity = this.addBeneficialCustomer(storageRatesDTO, entity);
		}
		if (selectRateType.equals(rateType5)) {
			entity = this.addNotifyParty(storageRatesDTO, entity);
		}

		if (selectRateType.equals(rateType3)) {
			String equipInit = storageRatesDTO.getEquipInit();
			if (equipInit != null) {

				if (!equipmentCustomerRangeRepository.existsByEquipmentInit(equipInit)) {
					throw new NoRecordsFoundException("No records found for given Equipment Init: " + equipInit);
				}
			} else {
				throw new InvalidDataException("Equipment is required on the Equipment tab");
			}
			entity.setEquipInit(equipInit);

		}

		if (selectRateType.equals(rateType4)) {
			Integer temp = storageRatesDTO.getEquipLgth();
			if (storageRatesDTO.getEquipLgth() == null) {
				throw new InvalidDataException("A Valid Eq Lgth must be selected from the list");
			} else {
				if (!(Arrays.stream((CommonConstants.EQ_LENGTH_ARRAY)).anyMatch(v -> v == temp))) {
					throw new InvalidDataException("A Valid Eq Lgth must be provided");
				}
			}
			entity.setEquipLgth(storageRatesDTO.getEquipLgth() * 12);
			entity.setShipPrimSix(storageRatesDTO.getShipPrimSix());
		}

		if (selectRateType.equals(rateType6)) {
			Long termId = storageRatesDTO.getTermId();
			if (termId != null) {

				if (!terminalRepository.existsByTerminalId(termId)) {
					throw new NoRecordsFoundException("Invalid terminal name removed from list : " + termId);
				}
			} else {
				throw new InvalidDataException("Terminal is required on the Terminal Tab");
			}
			entity.setTermId(termId);
		}

		entity = this.addCommonRules(storageRatesDTO, entity);
		
		if(selectRateType.equals(rateType3) || selectRateType.equals(rateType4)) {
			entity.setRrInd(null);
		}
		
		if (forceAdd.equals("N")) {
			Specification<StorageRates> specification = specificationGenerator.storageRatesDuplicateCheck(
					entity.getShipPrimSix(), entity.getBnfPrimSix(),
					entity.getCustomerPrimSix(), entity.getEquipInit(),
					entity.getEquipLgth(), entity.getTermId(), entity.getLclInterInd(),
					entity.getGateInd(), entity.getEquipTp(), entity.getLdEmptyCd(),
					entity.getRrInd(), entity.getEffectiveDate(), entity.getEndDate());
			List<StorageRates> storageRatesList = storageRatesRepository.findAll(specification);
			if (!storageRatesList.isEmpty()) {
				throw new NoRecordsFoundException(
						"One or More active storage rates with the given characteristics already exists");
			}
		}

		entity.setCreateUserId(headers.get("userid").toUpperCase());
		entity.setUpdateUserId(headers.get("userid").toUpperCase());
		entity.setUpdateExtensionSchema(headers.get("extensionschema"));
		entity.setUversion("!");
		entity.setStorageId(storageRatesRepository.SGK());
		entity = storageRatesRepository.save(entity);
		if (entity == null) {
			throw new RecordNotAddedException("Record Not added to Database");
		}
		storageRatesDTO = mapper.storageRatesToStorageRatesDTO(entity);
		return storageRatesDTO;
	}

	StorageRates addShipper(StorageRatesDTO storageRatesDTO, StorageRates entity) {
		log.info("selected tab : Shipper");
		String shipperNm = storageRatesDTO.getShipCustomerNm();
		String shipperPrimSix = storageRatesDTO.getShipPrimSix();
		String benfNm = storageRatesDTO.getBnfCustomerNm();
		String benfPrimSix = storageRatesDTO.getBnfPrimSix();
		String custNm = storageRatesDTO.getCustomerNm();
		String custPrimSix = storageRatesDTO.getCustomerPrimSix();
		if (shipperNm == null || shipperPrimSix == null) {
			throw new InvalidDataException("A Shipper is required on the shipper tab");
		}
		if (!this.checkCustomer(shipperNm, shipperPrimSix)) {
			throw new NoRecordsFoundException(
					"No customer record found with given Shipper Customer Name and Shipper Primary six!");
		}
		entity.setShipCustomerNm(shipperNm);
		entity.setShipPrimSix(shipperPrimSix);
		if (custNm != null) {
			if (benfNm != null) {
				throw new InvalidDataException("couldn't set both Beneficial and notify party on shipper tab");
			}
			if (!this.checkCustomer(custNm, custPrimSix)) {
				throw new NoRecordsFoundException("A Valid Notify Party must be selected from the list");
			}
			entity.setCustomerNm(custNm);
			entity.setCustomerPrimSix(custPrimSix);
		}
		if (benfNm != null) {
			if (custNm != null) {
				throw new InvalidDataException("couldn't set both Beneficial and notify party on shipper tab");
			}
			if (!this.checkCustomer(benfNm, benfPrimSix)) {
				throw new NoRecordsFoundException("A Valid Beneficial Owner must be selected from the list");
			}
			entity.setBnfCustomerNm(benfNm);
			entity.setBnfPrimSix(benfPrimSix);
		}
		if (storageRatesDTO.getEquipInit() != null) {
			if (benfNm != null || custNm != null) {
				throw new InvalidDataException(
						"couldn't set Beneficial and notify party with equipment initial on shipper tab");
			}
			if (!equipmentCustomerRangeRepository.existsByEquipmentInit(storageRatesDTO.getEquipInit())) {
				throw new NoRecordsFoundException("No records found for given Equipment Init: " + storageRatesDTO.getEquipInit());
			}
			entity.setEquipInit(storageRatesDTO.getEquipInit());
		}
		return entity;
	}

	StorageRates addBeneficialCustomer(StorageRatesDTO storageRatesDTO, StorageRates entity) {
		log.info("selected tab : Beneficial Cargo Owner");
		String benfNm = storageRatesDTO.getBnfCustomerNm();
		String benfPrimSix = storageRatesDTO.getBnfPrimSix();
		if (benfNm == null || benfPrimSix == null) {
			throw new InvalidDataException("A Beneficial Owner is required on the Benf Owner tab");
		}
		if (!this.checkCustomer(benfNm, benfPrimSix)) {
			throw new NoRecordsFoundException(
					"No customer record found with given Beneficial Customer Name and Beneficial Primary six!");
		}
		entity.setBnfCustomerNm(benfNm);
		entity.setBnfPrimSix(benfPrimSix);
		if (storageRatesDTO.getEquipInit() != null) {
			if (!equipmentCustomerRangeRepository.existsByEquipmentInit(storageRatesDTO.getEquipInit())) {
				throw new NoRecordsFoundException("Invalid equipment : " + storageRatesDTO.getEquipInit());
			}
			entity.setEquipInit(storageRatesDTO.getEquipInit());
		}
		return entity;
	}

	StorageRates addNotifyParty(StorageRatesDTO storageRatesDTO, StorageRates entity) {
		String custNm = storageRatesDTO.getCustomerNm();
		String custPrimSix = storageRatesDTO.getCustomerPrimSix();
		if (custNm == null || custPrimSix == null) {
			throw new InvalidDataException("Notify Party is required on the Ntfy Pty tab");
		}
		if (!this.checkCustomer(custNm, custPrimSix)) {
			throw new NoRecordsFoundException(
					"No customer record found with given notify party Customer Name and notify Primary six!");
		}
		entity.setCustomerNm(custNm);
		entity.setCustomerPrimSix(custPrimSix);
		return entity;
	}

	StorageRates addCommonRules(StorageRatesDTO storageRatesDTO, StorageRates entity) {
		entity.setEffectiveDate(LocalDate.now());
		if (storageRatesDTO.getEndDate() != null) {
			if (storageRatesDTO.getEndDate().isBefore(entity.getEffectiveDate())) {
				throw new InvalidDataException("Ending date must be greater than effective date");
			}
			entity.setEndDate(storageRatesDTO.getEndDate());
		}
		if (storageRatesDTO.getGateInd() != null && storageRatesDTO.getGateInd().equals("C")
				&& (storageRatesDTO.getEquipTp() != null && (!storageRatesDTO.getEquipTp().equals("C")))) {
			throw new InvalidDataException("The equipment type container must be specified for On car !");
		}
		entity.setGateInd(storageRatesDTO.getGateInd());
		entity.setEquipTp(storageRatesDTO.getEquipTp());
		entity.setLclInterInd(storageRatesDTO.getLclInterInd());
		entity.setLdEmptyCd(storageRatesDTO.getLdEmptyCd());
		if (storageRatesDTO.getTermId() != null) {
			if (!terminalRepository.existsByTerminalId(storageRatesDTO.getTermId())) {
				throw new NoRecordsFoundException(
						"Invalid terminal name removed from list : " + storageRatesDTO.getTermId());
			}
			entity.setTermId(storageRatesDTO.getTermId());
		}
		if (storageRatesDTO.getFreeDDLmt() == null) {
			if(!storageRatesDTO.getRateTp().equals("M"))
				throw new InvalidDataException("Please set storage rate free day limit");
			else
				throw new InvalidDataException("Monthly rates do not allow free time");
		}

		if ((storageRatesDTO.getRate1Amt() == null) && (storageRatesDTO.getPeakRt1Amt() == null))
			throw new InvalidDataException("Please set starting rate amount");

		if (storageRatesDTO.getCntWeekend() == null)
			throw new InvalidDataException("Please set count weekend free");

		if (storageRatesDTO.getCntWeekend() != null && storageRatesDTO.getCntWeekend().equals("Y")) {
			if ((storageRatesDTO.getCntSaturday() == null) && (storageRatesDTO.getCntSunday() == null))
				throw new InvalidDataException("Please set count Sat/Sun free");
		}
		entity.setRrInd(storageRatesDTO.getRrInd());
		entity.setNotepadTxt(storageRatesDTO.getNotepadTxt());
		entity.setFreeDDLmt(storageRatesDTO.getFreeDDLmt());
		entity.setRateDDLmt(storageRatesDTO.getRateDDLmt());
		entity.setRate2DDLmt(storageRatesDTO.getRate2DDLmt());
		if (storageRatesDTO.getBondFreeDD() != null) {
			entity.setBondFreeDD(storageRatesDTO.getBondFreeDD());
		} else {
			entity.setBondFreeDD(0);
		}
		if (storageRatesDTO.getRateTp() != null) {
			entity.setRateTp(storageRatesDTO.getRateTp());
		} else {
			entity.setRateTp("D");
		}
		if (storageRatesDTO.getCntWeekend() != null) {
			entity.setCntWeekend(storageRatesDTO.getCntWeekend());
		} else {
			entity.setCntWeekend("Y");
		}
		if (entity.getRateTp() != null && entity.getRateTp().equals("M")) {
			if (entity.getCntWeekend() != null && entity.getCntWeekend().equals("Y")) {
				throw new InvalidDataException("Monthly rates do not allow free time");
			}
			if (entity.getTermId() == null) {
				throw new InvalidDataException(
						"Monthly rates apply only at specific terminals, Cancel and re-enter terminal");
			}
			if (entity.getRateDDLmt() != null) {
				throw new InvalidDataException("Monthly rates do not allow free time");
			}
			if (entity.getRate2DDLmt() != null) {
				throw new InvalidDataException("Monthly rates do not allow free time");
			}
			if (entity.getBondFreeDD() != null) {
				throw new InvalidDataException("Bond Free Days does not allow for monthly rates");
			}
		}

		if (entity.getBondFreeDD() != null && (entity.getGateInd() != null && entity.getBondFreeDD()!=0 && (!entity.getGateInd().equals("O")))) {
			throw new InvalidDataException("Bond Free days only allowed for destination charges");
		}

		if (entity.getRateDDLmt() != null && entity.getFreeDDLmt() != null
				&& entity.getRateDDLmt() <= entity.getFreeDDLmt()) {
			throw new InvalidDataException("Higher rate occurs before free time expires");
		}

		if (entity.getRate2DDLmt() != null && entity.getFreeDDLmt() != null
				&& entity.getRate2DDLmt() <= entity.getFreeDDLmt()) {
			throw new InvalidDataException("Highest rate occurs before free time expires");
		}

		if (storageRatesDTO.getSchedDelRateInd() != null) {
			entity.setSchedDelRateInd(storageRatesDTO.getSchedDelRateInd());
		} else {
			entity.setSchedDelRateInd("N");
		}
		if (entity.getSchedDelRateInd() != null && entity.getSchedDelRateInd().equals("Y")) {
			entity.setSchedDelAllowance(storageRatesDTO.getSchedDelAllowance());
			entity.setSchedDelFail(storageRatesDTO.getSchedDelFail());
			if (entity.getSchedDelFail() == null) {
				throw new InvalidDataException("For scheduled delivery failure extra days are required");
			}
			if (entity.getSchedDelAllowance() == null) {
				throw new InvalidDataException("For scheduled delivery allowance hrs are required");
			}
		}
		if (storageRatesDTO.getCntWeekend() != null) {
			entity.setCntWeekend(storageRatesDTO.getCntWeekend());
		} else {
			entity.setCntWeekend("Y");
		}
		if (storageRatesDTO.getCntSaturday() != null) {
			entity.setCntSaturday(storageRatesDTO.getCntSaturday());
		} else {
			entity.setCntSaturday("Y");
		}
		if (storageRatesDTO.getCntSunday() != null) {
			entity.setCntSunday(storageRatesDTO.getCntSunday());
		} else {
			entity.setCntSunday("Y");
		}
		if (storageRatesDTO.getCntAfternoon() != null) {
			entity.setCntAfternoon(storageRatesDTO.getCntAfternoon());
		} else {
			entity.setCntAfternoon("N");
		}
		entity.setPmNopaStartTm(storageRatesDTO.getPmNopaStartTm());
		entity.setPeakBgnDtTm(storageRatesDTO.getPeakBgnDtTm());
		entity.setPeakEndDtTm(storageRatesDTO.getPeakEndDtTm());
		entity.setPeakRt1Amt(storageRatesDTO.getPeakRt1Amt());
		entity.setPeakRt2Amt(storageRatesDTO.getPeakRt2Amt());
		entity.setOffPeakRt1Amt(storageRatesDTO.getOffPeakRt1Amt());
		entity.setOffPeakRate2Amt(storageRatesDTO.getOffPeakRate2Amt());
		if (entity.getPeakRt1Amt() != null && entity.getPeakRt2Amt() != null
				&& entity.getPeakRt1Amt().compareTo(entity.getPeakRt2Amt()) == 1) {
			throw new InvalidDataException("Higher Peak Rate cannot be less than Starting Peak Rate");
		}
		if (entity.getOffPeakRt1Amt() != null && entity.getOffPeakRate2Amt() != null
				&& entity.getOffPeakRt1Amt().compareTo(entity.getOffPeakRate2Amt()) == 1) {
			throw new InvalidDataException("Higher Off-Peak Rate cannot be less than Starting Off-Peak Rate");
		}
		if (entity.getOffPeakRt1Amt() != null && entity.getPeakRt1Amt() != null
				&& entity.getOffPeakRt1Amt().compareTo(entity.getPeakRt1Amt()) == 1) {
			throw new InvalidDataException("Higher Off-Peak Rate cannot be greater than Higher Peak Rate");
		}
		entity.setAddlFreeHR(storageRatesDTO.getAddlFreeHR());
		entity.setRate1Amt(storageRatesDTO.getRate1Amt());
		entity.setRate2Amt(storageRatesDTO.getRate2Amt());
		entity.setRate3Amt(storageRatesDTO.getRate3Amt());
		if(storageRatesDTO.getBillCustId()!=null ) {
			if(!customerInfoRepository.existsById(storageRatesDTO.getBillCustId())) {
				throw new NoRecordsFoundException("No Customer Record found with given billtoCustId");
			}
			entity.setBillCustId(storageRatesDTO.getBillCustId());
		}
		entity.setAddlFreeHR(storageRatesDTO.getAddlFreeHR());
		entity.setBondFreeDD(storageRatesDTO.getBondFreeDD());
		return entity;
	}

	Boolean checkCustomer(String customerName, String customerPrimSix) {
		return customerInfoRepository.checkByCustomerNameAndCustomerPrimarySix(customerName, customerPrimSix)
				.size() != 0;
	}
}
