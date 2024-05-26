package com.nscorp.obis.dto;

import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import com.nscorp.obis.domain.Customer;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class MoneyReceivedDTO {
	private Long moneyTdrId;
	@NotNull(message = "TermId should not be null")
	private Long termId;
	@Size(min=1, max=1, message = "MoneyChkInd should not be more than 1 Character")
	private String moneyChkInd;
	@NotNull(message = "W-EQ_INIT Required")
	@Size(min=1, max=4, message = "Equip Init should not be more than 4 Character")
	private String equipInit;
	@NotNull(message = "W-EQ_NR Required")
	@Digits(integer=6, fraction=0, message = "Equip Number should not be more than 6 Digit")
	private Integer equipNbr;
	@NotNull(message = "W-EQ_TP Required")
	@Size(min=1, max=1, message = "Equip Tp should not be more than 1 Character")
	private String equipType;
	@Size(min=1, max=4, message = "Equip Id should not be more than 4 Character")
	private String equipId;
	private Double chrgId;
	@NotNull(message = "CustomerId should not be null")
	private Long customerId;
	@DateTimeFormat(pattern = "MM-dd-yyyy")
	private Timestamp paidDtTm;
	@NotNull(message = "Must enter paid amount")
	@Min(value = 0,message = "Must enter valid Amount")
	@Digits(integer=15, fraction=2, message = "Amount should not be more than 15 Digit")
	private BigDecimal amount;
	@NotNull(message = "Must select Payment Type code")
	@Size(min=1, max=2, message = "TpPayment should not be more than 2 Character")
	private String tpPayment;
	@NotNull(message = "Must select Service Type code")
	@Size(min=1, max=3, message = "TpSvcCd should not be more than 3 Character")
	private String tpSvcCd;
	@Size(min=0, max=15, message = "PaymentRefNr should not be more than 15 Character")
	private String paymentRefNr;
	@Size(min=1, max=1, message = "AmountAppliedInd should not be more than 1 Character")
	private String amountAppliedInd;
	@Size(min=1, max=1, message = "TermChkInd should not be more than 1 Character")
	private String termChkInd;
	private Long paidByCustId;
	@Size(min=1, max=30, message = "Payee should not be more than 30 Character")
	private String payee;
	
	private CustomerDTO customer;

	private CustomerDTO paidByCustomer;
	private String paidDtTmStr;

}
