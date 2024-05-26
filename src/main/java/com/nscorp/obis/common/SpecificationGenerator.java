package com.nscorp.obis.common;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import com.nscorp.obis.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import com.nscorp.obis.exception.InvalidDataException;

@Component
public class SpecificationGenerator {
	
	public static String ESCAPE_CHAR="\\";
	
	private static String replaceAll(String value) {
        return value
                .replace("\\",  ESCAPE_CHAR + "\\")
                .replace("_",   ESCAPE_CHAR + "_")
                .replace("%",   ESCAPE_CHAR + "%");

    }

	public Specification<NotifyProfileMethod> notifyProfileMethodSpecification(String notificationName,
			String notificationMethod, String notificationType, String ediBox, Integer notifyAreaCode,
			Integer notifyPrefix, Integer notifySuffix, Integer notifyPhoneExtension, String notificationOrder,
			Character microwaveIndicator) {
		return new Specification<NotifyProfileMethod>() {

			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<NotifyProfileMethod> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {

				List<Predicate> predicates = new ArrayList<>();
				if (Objects.nonNull(notificationName)) {
					predicates.add(criteriaBuilder
							.and(criteriaBuilder.like(criteriaBuilder.upper(root.get("notificationName")),
									"%" + notificationName.toUpperCase() + "%")));
				}
				if (Objects.nonNull(notificationMethod)) {
					predicates.add(criteriaBuilder.and(
							criteriaBuilder.like(criteriaBuilder.upper(root.get("notificationMethod").as(String.class)),
									"%" + notificationMethod.toUpperCase() + "%")));
				}
				if (Objects.nonNull(notificationType)) {
					predicates.add(criteriaBuilder.and(
							criteriaBuilder.like(criteriaBuilder.upper(root.get("notificationType").as(String.class)),
									"%" + notificationType.toUpperCase() + "%")));
				}
				if (Objects.nonNull(ediBox)) {
					predicates.add(criteriaBuilder.and(criteriaBuilder.like(criteriaBuilder.upper(root.get("ediBox")),
							"%" + ediBox.toUpperCase() + "%")));
				}
				if (Objects.nonNull(notifyAreaCode)) {
					predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("notifyAreaCode").as(String.class),
							"%" + notifyAreaCode + "%")));
				}
				if (Objects.nonNull(notifyPrefix)) {
					predicates.add(criteriaBuilder.and(
							criteriaBuilder.like(root.get("notifyPrefix").as(String.class), "%" + notifyPrefix + "%")));
				}
				if (Objects.nonNull(notifySuffix)) {
					predicates.add(criteriaBuilder.and(
							criteriaBuilder.like(root.get("notifySuffix").as(String.class), "%" + notifySuffix + "%")));
				}
				if (Objects.nonNull(notifyPhoneExtension)) {
					predicates.add(criteriaBuilder.and(
							criteriaBuilder.like(root.get("notifyPhoneExtention"), "%" + notifyPhoneExtension + "%")));
				}
				if (Objects.nonNull(notificationOrder)) {
					predicates.add(criteriaBuilder.and(
							criteriaBuilder.like(criteriaBuilder.upper(root.get("notificationOrder").as(String.class)),
									"%" + notificationOrder.toUpperCase() + "%")));
				}
				if (Objects.nonNull(microwaveIndicator)) {
					predicates.add(criteriaBuilder.and(criteriaBuilder
							.like(root.get("microwaveIndicator").as(String.class), "%" + microwaveIndicator + "%")));
				}

				return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
			}
		};
	}

	@SuppressWarnings("serial")
	public Specification<CustomerTerm> customerTermSpecification(Long terminalId, String customerName) {
		return new Specification<CustomerTerm>() {

			@Override
			public Predicate toPredicate(Root<CustomerTerm> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {

				List<Predicate> predicates = new ArrayList<>();
				if (Objects.nonNull(terminalId)) {
					predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("terminalId"), terminalId)));
				}
				if (Objects.nonNull(customerName)) {
					predicates.add(criteriaBuilder.and(criteriaBuilder.like(
							criteriaBuilder.upper(root.get("customerName")), "%" + customerName.toUpperCase() + "%")));
				}
				return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
			}
		};
	}

	@SuppressWarnings("serial")
	public Specification<BeneficialOwner> beneficialOwnerSpecification(String bnfLongName, String bnfShortName) {
		return new Specification<BeneficialOwner>() {

			@Override
			public Predicate toPredicate(Root<BeneficialOwner> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {

				List<Predicate> predicates = new ArrayList<>();
				if (Objects.nonNull(bnfLongName)) {
					predicates.add(criteriaBuilder.and(criteriaBuilder.like(
							criteriaBuilder.upper(root.get("bnfLongName")), "%" + bnfLongName.toUpperCase() + "%")));
				}
				if (Objects.nonNull(bnfShortName)) {
					predicates.add(criteriaBuilder.and(criteriaBuilder.like(
							criteriaBuilder.upper(root.get("bnfShortName")), "%" + bnfShortName.toUpperCase() + "%")));
				}
				return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
			}
		};
	}

	@SuppressWarnings("serial")
	public Specification<BeneficialOwnerDetail> beneficialOwnerDetailSpecification(Long bnfCustId, String bnfOwnerNumber) {
		return new Specification<BeneficialOwnerDetail>() {

			@Override
			public Predicate toPredicate(Root<BeneficialOwnerDetail> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {

				List<Predicate> predicates = new ArrayList<>();
				if (Objects.nonNull(bnfCustId)) {
					predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("bnfCustId"), bnfCustId)));
				}
				if (Objects.nonNull(bnfOwnerNumber)) {
					predicates.add(criteriaBuilder.and(criteriaBuilder.like(
							criteriaBuilder.upper(root.get("bnfOwnerNumber")), "%" + bnfOwnerNumber.toUpperCase() + "%")));
				}
				return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
			}
		};
	}



	@SuppressWarnings("serial")
	public Specification<CustomerNickname> customerNicknameSpecification(Long customerId, Long terminalId,
			String customerNickname) {
		return new Specification<CustomerNickname>() {

			@Override
			public Predicate toPredicate(Root<CustomerNickname> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {

				List<Predicate> predicates = new ArrayList<>();
				if (Objects.nonNull(customerId)) {
					predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("customerId"), customerId)));
				}
				if (Objects.nonNull(terminalId)) {
					predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("terminalId"), terminalId)));
				}
				if (Objects.nonNull(customerNickname)) {
					predicates.add(criteriaBuilder
							.and(criteriaBuilder.like(criteriaBuilder.upper(root.get("customerNickname")),
									"%" + customerNickname.toUpperCase() + "%")));
				}
				return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
			}
		};
	}

	@SuppressWarnings("serial")
	public Specification<NotepadEntry> notepadEntrySpecification(Long customerId, Long terminalId) {
		return new Specification<NotepadEntry>() {

			@Override
			public Predicate toPredicate(Root<NotepadEntry> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {

				List<Predicate> predicates = new ArrayList<>();
				if (Objects.nonNull(customerId)) {
					predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("customerId"), customerId)));
				}
				if (Objects.nonNull(terminalId)) {
					predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("terminalId"), terminalId)));
				}

				return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
			}
		};
	}

	public Specification<CustomerInfo> customerInfoSpecification(Long customerId, String customerName,
			String customerNumber, String[] filter,String fetchExpired) {
		return (Root<CustomerInfo> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
			List<Predicate> predicates = new ArrayList<>();
			if (customerId != null) {
				predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("customerId"), customerId)));
			}
			if (customerName != null) {
				predicates.add(criteriaBuilder.and(criteriaBuilder.like(criteriaBuilder.upper(root.get("customerName")),
						"%" + replaceAll(customerName).toUpperCase() + "%",'\\')));
			}
			if (customerNumber != null) {
				predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("customerNumber"), customerNumber)));

			}
			if (filter != null) {
				Map<String, List<String>> temp = ColumnFilter.filterFields(filter);
				for (Map.Entry<String, List<String>> field : temp.entrySet()) {
					List<String> fieldValues = field.getValue();
					String key = field.getKey();
					try {
						fieldValues.stream().forEach(value -> {
							predicates.add(criteriaBuilder.like(criteriaBuilder.upper(root.get(key)),
									"%" + value.toUpperCase() + "%"));
						});
					} catch (IllegalArgumentException err) {
						throw new InvalidDataException("Invalid field name : " + field.getKey());
					}
				}
			}
			if (fetchExpired.equals("N")) {
				predicates.add(criteriaBuilder.and(criteriaBuilder.isNull(root.get("expiredDate"))));
				predicates
						.add(criteriaBuilder.and(criteriaBuilder.or(criteriaBuilder.isNull(root.get("activityStatus")),
								criteriaBuilder.equal(root.get("activityStatus"), "a"),
								criteriaBuilder.equal(root.get("activityStatus"), "A"))));
			}
			return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));

		};
	}

	public Specification<CustomerIndex> customerIndexSpecification(String customerName, String customerNumber,
			Long corporateId, String fetchExpired) {
		return (Root<CustomerIndex> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
			List<Predicate> predicates = new ArrayList<>();
			if (customerName != null) {
				predicates.add(criteriaBuilder.and(criteriaBuilder.like(criteriaBuilder.upper(root.get("customerName")),
						"%" + replaceAll(customerName).toUpperCase() + "%",'\\')));
			}
			if (customerNumber != null) {
				predicates
						.add(criteriaBuilder.and(criteriaBuilder.like(criteriaBuilder.upper(root.get("customerNumber")),
								"%" + customerNumber.toUpperCase() + "%")));

			}
			if (corporateId != null) {
				predicates
						.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("corporateCustomerId"), corporateId)));
			}
			if (fetchExpired.equals("N")) {
				predicates.add(criteriaBuilder.and(criteriaBuilder.isNull(root.get("expiredDate"))));
				predicates
						.add(criteriaBuilder.and(criteriaBuilder.or(criteriaBuilder.isNull(root.get("activityStatus")),
								criteriaBuilder.equal(root.get("activityStatus"), "a"),
								criteriaBuilder.equal(root.get("activityStatus"), "A"))));
			}
			predicates.add(criteriaBuilder
					.and(criteriaBuilder.notLike(criteriaBuilder.upper(root.get("customerNumber")), "S%")));
			predicates.add(criteriaBuilder
					.and(criteriaBuilder.notLike(criteriaBuilder.trim(root.get("customerNumber")), "%0000")));

			return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));

		};

	}

	public Specification<CustomerIndex> customerIndexUniqueSpecification(String customerName, String customerNumber,
			Long corporateId, String fetchExpired) {
		return (Root<CustomerIndex> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
			List<Predicate> predicates = new ArrayList<>();
			if (customerName != null) {
				predicates.add(
						cb.and(cb.like(cb.upper(root.get("customerName")), "%" + replaceAll(customerName).toUpperCase() + "%",'\\')));
			}
			if (customerNumber != null) {
				predicates.add(cb.and(cb.like(root.get("customerNumber"), customerNumber.toUpperCase() + "%")));

			}
			if (corporateId != null) {
				predicates.add(cb.and(cb.equal(root.get("corporateCustomerId"), corporateId)));
			}
			Subquery<Timestamp> maxUpdateDateTimeSubquery = query.subquery(Timestamp.class);
			Root<CustomerIndex> maxUpdateDateTimeRoot = maxUpdateDateTimeSubquery.from(CustomerIndex.class);
			maxUpdateDateTimeSubquery.select(cb.greatest(maxUpdateDateTimeRoot.<Timestamp>get("updateDateTime")));
			maxUpdateDateTimeSubquery.groupBy(maxUpdateDateTimeRoot.get("customerName"),
					maxUpdateDateTimeRoot.get("customerNumber"), maxUpdateDateTimeRoot.get("city"),
					maxUpdateDateTimeRoot.get("state"));
			predicates.add(cb.in(root.get("updateDateTime")).value(maxUpdateDateTimeSubquery));
			if (fetchExpired.equals("N")) {
				predicates.add(cb.and(cb.isNull(root.get("expiredDate"))));
				predicates.add(cb.and(cb.or(cb.isNull(root.get("activityStatus")),
						cb.equal(root.get("activityStatus"), "a"), cb.equal(root.get("activityStatus"), "A"))));
			}
			query.orderBy(cb.asc(root.get("customerName")), cb.asc(root.get("customerNumber")),
					cb.asc(root.get("city")), cb.asc(root.get("state")));
			predicates.add(cb.and(cb.notLike(cb.upper(root.get("customerNumber")), "S%")));
			predicates.add(cb.and(cb.notLike(cb.trim(root.get("customerNumber")), "%0000")));
			return cb.and(predicates.toArray(new Predicate[predicates.size()]));

		};

	}

	public Specification<CustomerIndex> customerIndexLatestSpecification(String customerName, String customerNumber,
			String city, String state, Long corporateId, String fetchExpired) {
		return (Root<CustomerIndex> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
			List<Predicate> predicates = new ArrayList<>();
			if (customerName != null) {
				predicates.add(criteriaBuilder.and(criteriaBuilder.like(criteriaBuilder.upper(root.get("customerName")),
						"%" + replaceAll(customerName).toUpperCase() + "%",'\\')));
			}
			if (customerNumber != null) {
				predicates.add(criteriaBuilder
						.and(criteriaBuilder.like(root.get("customerNumber"), customerNumber.toUpperCase() + "%")));
			}
			if (city != null) {
				predicates.add(criteriaBuilder.and(
						criteriaBuilder.like(criteriaBuilder.upper(root.get("city")), "%" + city.toUpperCase() + "%")));
			}
			if (state != null) {
				predicates.add(criteriaBuilder.and(criteriaBuilder.like(criteriaBuilder.upper(root.get("state")),
						"%" + state.toUpperCase() + "%")));
			}
			if (corporateId != null) {
				predicates
						.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("corporateCustomerId"), corporateId)));
			}
			if (fetchExpired.equals("N")) {
				predicates.add(criteriaBuilder.and(criteriaBuilder.isNull(root.get("expiredDate"))));
				predicates
						.add(criteriaBuilder.and(criteriaBuilder.or(criteriaBuilder.isNull(root.get("activityStatus")),
								criteriaBuilder.equal(root.get("activityStatus"), "a"),
								criteriaBuilder.equal(root.get("activityStatus"), "A"))));
			}
			predicates.add(criteriaBuilder
					.and(criteriaBuilder.notLike(criteriaBuilder.upper(root.get("customerNumber")), "S%")));
			predicates.add(criteriaBuilder
					.and(criteriaBuilder.notLike(criteriaBuilder.trim(root.get("customerNumber")), "%0000")));
			return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));

		};

	}

	public Specification<DrayageScac> drayageScacSpecification(String drayId, String carrierName, String carrierCity,
			String state) {
		return (Root<DrayageScac> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
			List<Predicate> predicates = new ArrayList<>();
			if (drayId != null) {
				predicates.add(criteriaBuilder.and(criteriaBuilder.like(criteriaBuilder.upper(root.get("drayId")),
						"%" + drayId.toUpperCase() + "%")));
			}
			if (carrierName != null) {
				predicates.add(criteriaBuilder.and(criteriaBuilder.like(criteriaBuilder.upper(root.get("carrierName")),
						"%" + carrierName.toUpperCase() + "%")));
			}
			if (carrierCity != null) {
				predicates.add(criteriaBuilder.and(criteriaBuilder.like(criteriaBuilder.upper(root.get("carrierCity")),
						"%" + carrierCity.toUpperCase() + "%")));

			}
			if (state != null) {
				predicates.add(criteriaBuilder.and(criteriaBuilder.like(criteriaBuilder.upper(root.get("state")),
						"%" + state.toUpperCase() + "%")));

			}
			return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));

		};

	}

	public Specification<Pool> poolSpecification(Long poolId, String poolName) {
		// TODO Auto-generated method stub
		return (Root<Pool> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
			List<Predicate> predicates = new ArrayList<>();
			if (poolId != null) {
				predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("poolId"), poolId)));
			}
			if (poolName != null) {
				predicates.add(criteriaBuilder.and(criteriaBuilder.like(criteriaBuilder.upper(root.get("poolName")),
						"%" + poolName.toUpperCase() + "%")));
			}

			return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));

		};
	}

	public Specification<CashException> cashExceptionSpecification(String custName, String custPrimSix) {
		// TODO Auto-generated method stub
		return (Root<CashException> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
			List<Predicate> predicates = new ArrayList<>();
			if (custName != null) {
				predicates.add(criteriaBuilder.and(criteriaBuilder.like(criteriaBuilder.upper(root.get("customerName")),
						"%" + custName.toUpperCase() + "%")));
			}
			if (custPrimSix != null) {
				predicates.add(criteriaBuilder.and(criteriaBuilder
						.equal(criteriaBuilder.upper(root.get("customerPrimarySix")), custPrimSix.toUpperCase())));
			}
			return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));

		};
	}

	public Specification<Commodity> commoditySpecification(String longName, String hazardIndicator,
			Integer commodityCode5, Integer commodityCode2, Integer commoditySubCode) {
		return (Root<Commodity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
			List<Predicate> predicates = new ArrayList<>();
			if (longName != null) {
				predicates.add(criteriaBuilder.and(criteriaBuilder.like(
						criteriaBuilder.upper(root.get("commodityCodeLongName")), "%" + longName.toUpperCase() + "%")));
			}
			if (hazardIndicator != null) {
				predicates.add(
						criteriaBuilder.and(criteriaBuilder.like(criteriaBuilder.upper(root.get("hazardIndicator")),
								"%" + hazardIndicator.toUpperCase() + "%")));
			}
			if (commodityCode5 != null) {
				predicates.add(criteriaBuilder
						.and(criteriaBuilder.equal(criteriaBuilder.upper(root.get("commodityCode5")), commodityCode5)));
			}
			if (commodityCode2 != null) {
				predicates.add(criteriaBuilder
						.and(criteriaBuilder.equal(criteriaBuilder.upper(root.get("commodityCode2")), commodityCode2)));
			}
			if (commoditySubCode != null) {
				predicates.add(criteriaBuilder.and(
						criteriaBuilder.equal(criteriaBuilder.upper(root.get("commoditySubCode")), commoditySubCode)));
			}
			return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));

		};
	}

	public Specification<StorageRates> storageRatesSpecification(String selectRateType, String shipPrimSix, String bnfPrimSix, String customerPrimSix, 
							String[] equipInit, String equipLgth, String[] termId, String[] filter) {
		return (Root<StorageRates> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
			List<Predicate> predicates = new ArrayList<>();

			String rateType1 = "Shipper";
        	String rateType2 = "Benf Cargo Owner";
			String rateType3 = "Equipment";
			String rateType4 = "Eqpt Length";
			String rateType5 = "Notify Party";
			String rateType6 = "Terminal";
			String rateType7 = "Default";

			if(selectRateType.equals(rateType1) ){
				if (shipPrimSix == null) {
					predicates.add(criteriaBuilder.and(criteriaBuilder.isNotNull(root.get("shipPrimSix"))));
				}
			}
			if(selectRateType.equals(rateType2) ){
				if (bnfPrimSix == null) {
					predicates.add(criteriaBuilder.and(criteriaBuilder.isNotNull(root.get("bnfPrimSix"))));
					predicates.add(criteriaBuilder.and(criteriaBuilder.isNull(root.get("shipPrimSix"))));
				}
			}
			if(selectRateType.equals(rateType3) ){
				if (equipInit == null) {
					predicates.add(criteriaBuilder.and(criteriaBuilder.isNotNull(root.get("equipInit"))));
					predicates.add(criteriaBuilder.and(criteriaBuilder.isNull(root.get("bnfPrimSix"))));
					predicates.add(criteriaBuilder.and(criteriaBuilder.isNull(root.get("shipPrimSix"))));
				}
			}
			if(selectRateType.equals(rateType4) ){
				if (equipLgth == null) {
					predicates.add(criteriaBuilder.and(criteriaBuilder.isNotNull(root.get("equipLgth"))));
				}
			}
			if(selectRateType.equals(rateType5) ){
				if (customerPrimSix == null) {
					predicates.add(criteriaBuilder.and(criteriaBuilder.isNotNull(root.get("customerPrimSix"))));
					predicates.add(criteriaBuilder.and(criteriaBuilder.isNull(root.get("shipPrimSix"))));
				}
			}
			if(selectRateType.equals(rateType6) ){
				if (termId == null) {
					predicates.add(criteriaBuilder.and(criteriaBuilder.isNotNull(root.get("termId"))));
					predicates.add(criteriaBuilder.and(criteriaBuilder.isNull(root.get("shipPrimSix"))));
					predicates.add(criteriaBuilder.and(criteriaBuilder.isNull(root.get("bnfPrimSix"))));
					predicates.add(criteriaBuilder.and(criteriaBuilder.isNull(root.get("customerPrimSix"))));
					predicates.add(criteriaBuilder.and(criteriaBuilder.isNull(root.get("equipInit"))));
				}
			}
			if(selectRateType.equals(rateType7) ){
				predicates.add(criteriaBuilder.and(criteriaBuilder.isNull(root.get("shipPrimSix"))));
				predicates.add(criteriaBuilder.and(criteriaBuilder.isNull(root.get("bnfPrimSix"))));
				predicates.add(criteriaBuilder.and(criteriaBuilder.isNull(root.get("customerPrimSix"))));
				predicates.add(criteriaBuilder.and(criteriaBuilder.isNull(root.get("equipInit"))));
				predicates.add(criteriaBuilder.and(criteriaBuilder.isNull(root.get("equipLgth"))));
				predicates.add(criteriaBuilder.and(criteriaBuilder.isNull(root.get("termId"))));
			}

			if (shipPrimSix != null) {
				predicates.add(
						criteriaBuilder.and(criteriaBuilder.like(criteriaBuilder.upper(root.get("shipPrimSix")),
								 "%" + shipPrimSix.toUpperCase() + "%")));
			}
			if (bnfPrimSix != null) {
				predicates.add(
						criteriaBuilder.and(criteriaBuilder.like(criteriaBuilder.upper(root.get("bnfPrimSix")),
								"%" + bnfPrimSix.toUpperCase() + "%")));
			}
			if (customerPrimSix != null) {
				predicates.add(
						criteriaBuilder.and(criteriaBuilder.like(criteriaBuilder.upper(root.get("customerPrimSix")),
								"%" + customerPrimSix.toUpperCase() + "%")));
			}
			if (equipInit != null) {
				List<Predicate> predicatesNames = new ArrayList<Predicate>();
				for (String name : equipInit) {
                    predicatesNames.add(criteriaBuilder.equal(root.<String>get("equipInit"), name));
					
                }
				predicates.add(criteriaBuilder.or(predicatesNames.toArray(new Predicate[] {})));        
            
			}
			if (equipLgth != null) {
				predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("equipLgth"), equipLgth)));
			}
			if (termId != null) {
				List<Predicate> predicatesNames1 = new ArrayList<Predicate>();
				for (String name : termId) {
                    predicatesNames1.add(criteriaBuilder.equal(root.<String>get("termId"), name));
                }
				predicates.add(criteriaBuilder.or(predicatesNames1.toArray(new Predicate[] {}))); 

			}
			
			if (filter != null) {
				Map<String, List<String>> temp = ColumnFilter.filterFields(filter);
				for (Map.Entry<String, List<String>> field : temp.entrySet()) {
					List<String> fieldValues = field.getValue();
					String key = field.getKey();
					String type;
					try {
						type = StorageRates.class.getDeclaredField(key).getType().getSimpleName();
					} catch (NoSuchFieldException | SecurityException e) {
						throw new InvalidDataException("Invalid field name : " + field.getKey());
					}
					try {
						fieldValues.stream().forEach(value -> {
							if (type.equals("Integer")) {
								predicates.add(criteriaBuilder.equal(root.get(key), Integer.valueOf(value)));
							} else if (type.equals("String")) {
								predicates.add(criteriaBuilder.like(criteriaBuilder.upper(root.get(key)),
										"%" + value.toUpperCase() + "%"));
							} else if (type.equals("Long")) {
								predicates.add(criteriaBuilder.equal(root.get(key), Long.valueOf(value)));
							} else if (type.equals("BigDecimal")) {
								predicates.add(criteriaBuilder.equal(root.get(key), new BigDecimal(value)));
							} else if (type.equals("LocalDate")) {
								predicates.add(criteriaBuilder.equal(root.<LocalDate>get(key),
										LocalDate.parse(value, DateTimeFormatter.ofPattern("MM-dd-yyyy"))));
							} else {
								throw new InvalidDataException("Invalid filter field name or value");
							}
						});
					} catch (IllegalArgumentException err) {
						throw new InvalidDataException("Invalid field value : " + field.getValue());
					}
				}
			}
			return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));

		};
	}

	public Specification<StorageRates> storageRatesSpecificationExpired(String selectRateType, String shipPrimSix, String bnfPrimSix, String customerPrimSix, String[] equipInit , String equipLgth, String[] termId, String[] filter) {
		return (Root<StorageRates> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
			List<Predicate> predicates = new ArrayList<>();

			String rateType1 = "Shipper";
        	String rateType2 = "Benf Cargo Owner";
			String rateType3 = "Equipment";
			String rateType4 = "Eqpt Length";
			String rateType5 = "Notify Party";
			String rateType6 = "Terminal";
			String rateType7 = "Default";

			if(selectRateType.equals(rateType1) ){
				if (shipPrimSix == null) {
					predicates.add(criteriaBuilder.and(criteriaBuilder.isNotNull(root.get("shipPrimSix"))));
				}
			}
			if(selectRateType.equals(rateType2) ){
				if (bnfPrimSix == null) {
					predicates.add(criteriaBuilder.and(criteriaBuilder.isNotNull(root.get("bnfPrimSix"))));
					predicates.add(criteriaBuilder.and(criteriaBuilder.isNull(root.get("shipPrimSix"))));
				}
			}
			if(selectRateType.equals(rateType3) ){
				if (equipInit == null) {
					predicates.add(criteriaBuilder.and(criteriaBuilder.isNotNull(root.get("equipInit"))));
					predicates.add(criteriaBuilder.and(criteriaBuilder.isNull(root.get("bnfPrimSix"))));
					predicates.add(criteriaBuilder.and(criteriaBuilder.isNull(root.get("shipPrimSix"))));
				}
			}
			if(selectRateType.equals(rateType4) ){
				if (equipLgth == null) {
					predicates.add(criteriaBuilder.and(criteriaBuilder.isNotNull(root.get("equipLgth"))));
				}
			}
			if(selectRateType.equals(rateType5) ){
				if (customerPrimSix == null) {
					predicates.add(criteriaBuilder.and(criteriaBuilder.isNotNull(root.get("customerPrimSix"))));
					predicates.add(criteriaBuilder.and(criteriaBuilder.isNull(root.get("shipPrimSix"))));
				}
			}
			if(selectRateType.equals(rateType6) ){
				if (termId == null) {
					predicates.add(criteriaBuilder.and(criteriaBuilder.isNotNull(root.get("termId"))));
					predicates.add(criteriaBuilder.and(criteriaBuilder.isNull(root.get("shipPrimSix"))));
					predicates.add(criteriaBuilder.and(criteriaBuilder.isNull(root.get("bnfPrimSix"))));
					predicates.add(criteriaBuilder.and(criteriaBuilder.isNull(root.get("customerPrimSix"))));
					predicates.add(criteriaBuilder.and(criteriaBuilder.isNull(root.get("equipInit"))));
				}
			}
			if(selectRateType.equals(rateType7) ){
				predicates.add(criteriaBuilder.and(criteriaBuilder.isNull(root.get("shipPrimSix"))));
				predicates.add(criteriaBuilder.and(criteriaBuilder.isNull(root.get("bnfPrimSix"))));
				predicates.add(criteriaBuilder.and(criteriaBuilder.isNull(root.get("customerPrimSix"))));
				predicates.add(criteriaBuilder.and(criteriaBuilder.isNull(root.get("equipInit"))));
				predicates.add(criteriaBuilder.and(criteriaBuilder.isNull(root.get("equipLgth"))));
				predicates.add(criteriaBuilder.and(criteriaBuilder.isNull(root.get("termId"))));
			}
			if (shipPrimSix != null) {
				predicates.add(
						criteriaBuilder.and(criteriaBuilder.like(criteriaBuilder.upper(root.get("shipPrimSix")),
								 "%" + shipPrimSix.toUpperCase() + "%")));
			}
			if (bnfPrimSix != null) {
				predicates.add(
						criteriaBuilder.and(criteriaBuilder.like(criteriaBuilder.upper(root.get("bnfPrimSix")),
								"%" + bnfPrimSix.toUpperCase() + "%")));
			}
			if (customerPrimSix != null) {
				predicates.add(
						criteriaBuilder.and(criteriaBuilder.like(criteriaBuilder.upper(root.get("customerPrimSix")),
								"%" + customerPrimSix.toUpperCase() + "%")));
			}
			if (equipInit != null) {
				List<Predicate> predicatesNames = new ArrayList<Predicate>();
				for (String name : equipInit) {
                    predicatesNames.add(criteriaBuilder.equal(root.<String>get("equipInit"), name));
					
                }
				predicates.add(criteriaBuilder.or(predicatesNames.toArray(new Predicate[] {})));        
            
			}
			if (equipLgth != null) {
				predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("equipLgth"), equipLgth)));
			}
			if (termId != null) {
				List<Predicate> predicatesNames1 = new ArrayList<Predicate>();
				for (String name : termId) {
                    predicatesNames1.add(criteriaBuilder.equal(root.<String>get("termId"), name));
                }
				predicates.add(criteriaBuilder.or(predicatesNames1.toArray(new Predicate[] {}))); 

			}
			
			Predicate endDate1 = criteriaBuilder.and(criteriaBuilder.isNull(root.get("endDate")));
			Predicate endDate2 = criteriaBuilder.lessThanOrEqualTo( criteriaBuilder.currentDate(), root.<Date>get("endDate"));
			predicates.add(
						criteriaBuilder.or(endDate1,endDate2));
			if (filter != null) {
				Map<String, List<String>> temp = ColumnFilter.filterFields(filter);
				for (Map.Entry<String, List<String>> field : temp.entrySet()) {
					List<String> fieldValues = field.getValue();
					String key = field.getKey();
					String type;
					try {
						type = StorageRates.class.getDeclaredField(key).getType().getSimpleName();
					} catch (NoSuchFieldException | SecurityException e) {
						throw new InvalidDataException("Invalid field name : " + field.getKey());
					}
					try {
						fieldValues.stream().forEach(value -> {
							if (type.equals("Integer")) {
								predicates.add(criteriaBuilder.equal(root.get(key), Integer.valueOf(value)));
							} else if (type.equals("String")) {
								predicates.add(criteriaBuilder.like(criteriaBuilder.upper(root.get(key)),
										"%" + value.toUpperCase() + "%"));
							} else if (type.equals("Long")) {
								predicates.add(criteriaBuilder.equal(root.get(key), Long.valueOf(value)));
							} else if (type.equals("BigDecimal")) {
								predicates.add(criteriaBuilder.equal(root.get(key), new BigDecimal(value)));
							} else if (type.equals("LocalDate")) {
								predicates.add(criteriaBuilder.equal(root.<LocalDate>get(key),
										LocalDate.parse(value, DateTimeFormatter.ofPattern("MM-dd-yyyy"))));
							} else {
								throw new InvalidDataException("Invalid filter field name or value");
							}
						});
					} catch (IllegalArgumentException err) {
						throw new InvalidDataException("Invalid field value : " + field.getValue());
					}
				}
			}

			return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));

		};
	}

	public Specification<StorageRates> storageRatesDuplicateCheck(String shipPrimSix, String bnfPrimSix,
			String customerPrimSix, String equipInit, Integer equipLgth, Long termId, String lclInterInd,
			String gateInd, String equipTp, String ldEmptyCd, String rrInd, LocalDate effectiveDate,
			LocalDate endDate) {
		return (Root<StorageRates> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
			List<Predicate> predicates = new ArrayList<>();
			if (termId != null) {
				predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("termId"), termId)));
			} else {
				predicates.add(criteriaBuilder.and(criteriaBuilder.isNull(root.get("termId"))));
			}
			if (shipPrimSix != null) {
				predicates.add(criteriaBuilder.and(criteriaBuilder.equal(criteriaBuilder.upper(root.get("shipPrimSix")),
						shipPrimSix.toUpperCase())));
			} else {
				predicates.add(criteriaBuilder.and(criteriaBuilder.isNull(root.get("shipPrimSix"))));
			}
			if (bnfPrimSix != null) {
				predicates.add(criteriaBuilder.and(criteriaBuilder.equal(criteriaBuilder.upper(root.get("bnfPrimSix")),
						bnfPrimSix.toUpperCase())));
			} else {
				predicates.add(criteriaBuilder.and(criteriaBuilder.isNull(root.get("bnfPrimSix"))));
			}
			if (customerPrimSix != null) {
				predicates.add(
						criteriaBuilder.and(criteriaBuilder.equal(criteriaBuilder.upper(root.get("customerPrimSix")),
								customerPrimSix.toUpperCase())));
			} else {
				predicates.add(criteriaBuilder.and(criteriaBuilder.isNull(root.get("customerPrimSix"))));
			}
			if (equipInit != null) {
				predicates.add(criteriaBuilder.and(criteriaBuilder.equal(criteriaBuilder.upper(root.get("equipInit")),
						equipInit.toUpperCase())));
			} else {
				predicates.add(criteriaBuilder.and(criteriaBuilder.isNull(root.get("equipInit"))));
			}
			if (equipLgth != null) {
				predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("equipLgth"), equipLgth)));
			} else {
				predicates.add(criteriaBuilder.and(criteriaBuilder.isNull(root.get("equipLgth"))));
			}
			if (lclInterInd != null) {
				predicates.add(criteriaBuilder.and(criteriaBuilder.equal(criteriaBuilder.upper(root.get("lclInterInd")),
						lclInterInd.toUpperCase())));
			} else {
				predicates.add(criteriaBuilder.and(criteriaBuilder.isNull(root.get("lclInterInd"))));
			}
			if (gateInd != null) {
				predicates.add(criteriaBuilder.and(criteriaBuilder.equal(criteriaBuilder.upper(root.get("gateInd")),
						gateInd.toUpperCase())));
			} else {
				predicates.add(criteriaBuilder.and(criteriaBuilder.isNull(root.get("gateInd"))));
			}
			if (equipTp != null) {
				predicates.add(criteriaBuilder.and(criteriaBuilder.equal(criteriaBuilder.upper(root.get("equipTp")),
						equipTp.toUpperCase())));
			} else {
				predicates.add(criteriaBuilder.and(criteriaBuilder.isNull(root.get("equipTp"))));
			}
			if (ldEmptyCd != null) {
				predicates.add(criteriaBuilder.and(criteriaBuilder.equal(criteriaBuilder.upper(root.get("ldEmptyCd")),
						ldEmptyCd.toUpperCase())));
			} else {
				predicates.add(criteriaBuilder.and(criteriaBuilder.isNull(root.get("ldEmptyCd"))));
			}
			if (rrInd != null) {
				predicates.add(criteriaBuilder.and(criteriaBuilder.equal(criteriaBuilder.upper(root.get("rrInd")),
						rrInd.toUpperCase())));
			} else {
				predicates.add(criteriaBuilder.and(criteriaBuilder.isNull(root.get("rrInd"))));
			}
			if (endDate != null) {
				Predicate endDate1 = criteriaBuilder.and(criteriaBuilder.isNotNull(root.get("endDate")));
				Predicate endDate2 = criteriaBuilder.lessThanOrEqualTo(root.<Date>get("effectiveDate"),
						root.<Date>get("endDate"));
				Predicate endDate3 = criteriaBuilder.and(endDate1, endDate2);
				Predicate endDate4 = criteriaBuilder.and(criteriaBuilder.isNull(root.get("endDate")));
				predicates.add(criteriaBuilder.or(endDate3, endDate4));
			} else {
				predicates.add(criteriaBuilder.and(criteriaBuilder.isNull(root.get("endDate"))));

			}

			return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));

		};
	}

	public Specification<Train> trainSpecification(String trainNumber, String trainDesc) {
		return (Root<Train> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
			List<Predicate> predicates = new ArrayList<>();
			if (trainNumber != null) {
				predicates.add(criteriaBuilder.and(criteriaBuilder.like(criteriaBuilder.upper(root.get("trainNumber")),
						"%" + trainNumber.toUpperCase() + "%")));
			}
			if (trainDesc != null) {
				predicates.add(criteriaBuilder.and(criteriaBuilder.like(criteriaBuilder.upper(root.get("trainDesc")),
						"%" + trainDesc.toUpperCase() + "%")));
			}
			return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));

		};
	}
	
	public Specification<CustomerScac> customerScacSpecification(String customerNumber) {
		return (Root<CustomerScac> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
			List<Predicate> predicates = new ArrayList<>();
			if (customerNumber != null) {
				predicates.add(criteriaBuilder.and(criteriaBuilder
						.like(criteriaBuilder.upper(root.get("customerNumber")), customerNumber.toUpperCase() + "%")));
			}
			predicates.add(criteriaBuilder.and(criteriaBuilder.isNull(root.get("expiredDate"))));
			predicates.add(criteriaBuilder
					.and(criteriaBuilder.notLike(criteriaBuilder.trim(root.get("customerNumber")), "%0000")));

			return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));

		};
	}
	
	public Specification<DamageComponentReason> damageComponentReasonSpecification(Integer jobCode,
			Integer aarWhyMadeCode, String orderCode, String sizeRequired) {
		return (Root<DamageComponentReason> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
			List<Predicate> predicates = new ArrayList<>();
			if (jobCode != null) {
				predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("jobCode"), jobCode)));
			}
			if (aarWhyMadeCode != null) {
				predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("aarWhyMadeCode"), aarWhyMadeCode)));
			}
			if (orderCode != null) {
				predicates.add(criteriaBuilder.and(criteriaBuilder.like(criteriaBuilder.upper(root.get("orderCode")),
						orderCode.toUpperCase() + "%")));
			}
			if (sizeRequired != null) {
				predicates.add(criteriaBuilder.and(criteriaBuilder.like(criteriaBuilder.upper(root.get("sizeRequired")),
						sizeRequired.toUpperCase() + "%")));
			}
			return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));

		};
	}

	public Specification<DamageComponentSize> damageComponentSizeSpecification(Integer jobCode,Integer aarWhyMadeCode, Long componentSizeCode) {
		return (Root<DamageComponentSize> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
			List<Predicate> predicates = new ArrayList<>();
			if (jobCode != null) {
				predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("jobCode"), jobCode)));
			}
			if (aarWhyMadeCode != null) {
				predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("aarWhyMadeCode"), aarWhyMadeCode)));
			}
			if (componentSizeCode != null) {
				predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("componentSizeCode"), componentSizeCode)));
			}
			
			return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));

		};
	}
}
