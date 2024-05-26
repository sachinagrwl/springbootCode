package com.nscorp.obis.dto;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import com.nscorp.obis.domain.MoneyReceived;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Size;

@Data
@EqualsAndHashCode(callSuper = false)
public class CustomerChargeDTO {
	@Digits(integer=15, fraction=0, message = "Charge ID should not be more than 15 Digit")
	private Long chrgId;
	@Size(min=1, max=4, message = "Equip Init should not be more than 4 Character")
	private String equipInit;
	@Digits(integer=6, fraction=0, message = "Equip Number should not be more than 6 Digit")
	private Integer equipNbr;
	@Size(min=1, max=1, message = "Equip Tp should not be more than 1 Character")
	private String equipType;
	@Size(min=1, max=4, message = "Equip Id should not be more than 4 Character")
	private String equipId;
	@Digits(integer=15, fraction=0, message = "BillCustId should not be more than 15 Character")
	private Long billCustId;
	@Size(min=1, max=3, message = "Charge Type should not be more than 3 Character")
	private String chrgTp;
	@Digits(integer=15, fraction=0, message = "Rate ID should not be more than 15 Character")
	private Long rateId;
	@Size(min=1, max=3, message = "Charge Code should not be more than 3 Character")
	private String chrgCd;
	@Digits(integer=17, fraction=2, message = "Charge Amount should not be more than 17 Digit")
	private BigDecimal chrgAmt;
	@Digits(integer=4, fraction=0, message = "Charge Base Days should not be more than 4 Digit")
	private Integer chrgBaseDays;
	@Digits(integer=4, fraction=0, message = "Charge Days should not be more than 4 Digit")
	private Integer chrgDays;
	@Size(min=1, max=4, message = "Begin Event Code should not be more than 4 Character")
	private String bgnEvtCd;
	private Timestamp bgnLclDtTm;
	private Timestamp endLclDtTm;
	private Long svcId;
	@Size(min=1, max=1, message = "Begin LE Ind should not be more than 1 Character")
	private String bgnLEInd;
	@Size(min=1, max=4, message = "End Evt Code should not be more than 4 Character")
	private String endEvtCd;
	@Size(min=1, max=1, message = "End LE Ind should not be more than 1 Character")
	private String endLEInd;
	private Date lastFreeDtTm;
	@Size(min=1, max=40, message = "Override Name should not be more than 40 Character")
	private String overrideNm;
	@Size(min=1, max=1, message = "Locally Billed Ind should not be more than 1 Character")
	private String locallyBilledInd;
	private Date billReleaseDate;
	@Size(min=1, max=1, message = "Bonded Ind should not be more than 1 Character")
	private String bondedInd;
	@Size(min=1, max=1, message = "Peak Rt Ind should not be more than 1 Character")
	private String peakRtInd;
	@Size(min=1, max=1, message = "Rate Type should not be more than 1 Character")
	private String rateType;
	private Long bgnTermId;
	@Size(min=1, max=20, message = "Override Title should not be more than 20 Character")
	private String overrideTitle;
	@Size(min=1, max=8, message = "Override UserId should not be more than 8 Character")
	private String overrideUserId;
	private Long cashExcpId;
	private Long endTermId;
	private String schdDeliveryDTM;
	private String deliverOrSailBy;
	private String deliverByDTM;
	private String sailByDTM;
	private String guarantee;
	private String beginTerminalName;
	private String endTerminalName;
	private String customerName;
	private String customerNbr;
	private boolean activeCharge;
	private boolean cifOverride;
	private boolean voidRecord;
	List<MoneyReceived> moneyReceivedList;
	private String chrgTypeCode;
	private String rateTypeCode;

	private Timestamp createDtTm;

	private BigDecimal wbSerNr;
	
	private boolean isLastFreeDayModified;


}
