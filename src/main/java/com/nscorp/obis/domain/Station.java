package com.nscorp.obis.domain;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "STATION_XRF")
public class Station extends AuditInfo{

    @Id
    @Column(name = "TERM_ID",length = 15, columnDefinition = "double", nullable = false)
    private Long termId;

    @Column(name = "ROAD_NR", columnDefinition = "char(4)", nullable = true)
    private String roadNumber;

    @Column(name = "FSAC", columnDefinition = "char(6)", nullable = true)
    private String FSAC;

    @Column(name = "STN_NM", columnDefinition = "char(19)", nullable = true)
    private String stationName;

    @Column(name = "STATE", columnDefinition = "char(2)", nullable = true)
    private String state;

    @Column(name = "BILL_AT_FSAC", columnDefinition = "char(6)", nullable = true)
    private String billAtFsac;

    @Column(name = "ROAD_NM", columnDefinition = "char(4)", nullable = true)
    private String roadName;

    @Column(name = "OP_STN", columnDefinition = "char(5)", nullable = true)
    private String operationStation;

    @Column(name = "SPLC", columnDefinition = "char(9)", nullable = true)
    private String splc;

    @Column(name = "RULE_260_STN", columnDefinition = "char(5)", nullable = true)
    private String rule260Station;
    
    @Column(name = "INTERMODAL_IND", columnDefinition = "char(1)", nullable = true)
    private String intermodalIndicator;

    @Column(name = "OP_STA_5_SPELL", columnDefinition = "char(5)", nullable = true)
    private String char5Spell;

    @Column(name = "OP_STA_ALIAS", columnDefinition = "char(5)", nullable = true)
    private String char5Alias;

    @Column(name = "OP_STN_8_SPELL", columnDefinition = "char(8)", nullable = true)
    private String char8Spell;

    @Column(name = "DIV_CD", columnDefinition = "char(2)", nullable = true)
    private String division;

    @Column(name = "STN_EXP_DATE", columnDefinition = "Date(10)", nullable = true)
    private LocalDate expirationDate;
    
    @Column(name = "BILLING_IND", columnDefinition = "char(1)", nullable = true)
    private String billingInd;
    
    @Column(name = "EXPIRED_DT", columnDefinition = "Timestamp", nullable = true)
    private Timestamp expiredDate;
    
    @Column(name = "TOP_PICK", columnDefinition = "char(1)", nullable = true)
    private String topPick;
    
    @Column(name = "BOTTOM_PICK", columnDefinition = "char(1)", nullable = true)
    private String bottomPick;
    
   @OneToMany(targetEntity = StationRestriction.class, cascade = CascadeType.ALL, orphanRemoval=true, fetch = FetchType.LAZY)
   @JoinColumn(name = "STN_XRF_ID", referencedColumnName="TERM_ID", insertable = false, updatable = false)

    private List<StationRestriction> stationRestriction = new ArrayList<>();

    public Station() {
        super();
    }

    public String getIntermodalIndicator() {
    	if(intermodalIndicator != null) {
            return intermodalIndicator.trim();
        }
        else {
            return null;
        }
    }

    public void setIntermodalIndicator(String intermodalIndicator) {
    	if(intermodalIndicator != null) {
    		this.intermodalIndicator = intermodalIndicator.toUpperCase();
    	} else {
    		this.intermodalIndicator = intermodalIndicator;
    	}
    }

    public String getRoadNumber() {
        if(roadNumber != null) {
            return roadNumber.trim();
        }
        else {
            return roadNumber;
        }
    }

    public void setRoadNumber(String roadNumber) {
        if(roadNumber != null) {
            this.roadNumber = roadNumber.toUpperCase();
        }
        else {
            this.roadNumber = roadNumber;
        }
    }

    public String getFSAC() {
        if(FSAC != null) {
            return FSAC.trim();
        }
        else {
            return FSAC;
        }
    }

    public void setFSAC(String FSAC) {
        if(FSAC != null) {
            this.FSAC = FSAC.toUpperCase();
        }
        else {
            this.FSAC = FSAC;
        }
    }

    public String getStationName() {
        if(stationName != null) {
            return stationName.trim();
        }
        else {
            return stationName;
        }
    }

    public void setStationName(String stationName) {
        if(stationName != null) {
            this.stationName = stationName.toUpperCase();
        }
        else {
            this.stationName = stationName;
        }
    }

    public String getState() {
        if(state != null) {
            return state.trim();
        }
        else {
            return state;
        }
    }

    public void setState(String state) {
        if(state != null) {
            this.state = state.toUpperCase();
        }
        else {
            this.state = state;
        }
    }

    public String getBillAtFsac() {
        if(billAtFsac != null) {
            return billAtFsac.trim();
        }
        else {
            return billAtFsac;
        }
    }

    public void setBillAtFsac(String billAtFsac) {
        if(billAtFsac != null) {
            this.billAtFsac = billAtFsac.toUpperCase();
        }
        else {
            this.billAtFsac = billAtFsac;
        }
    }

    public String getRoadName() {
        if(roadName != null) {
            return roadName.trim();
        }
        else {
            return roadName;
        }
    }

    public void setRoadName(String roadName) {
        if(roadName != null) {
            this.roadName = roadName.toUpperCase();
        }
        else {
            this.roadName = roadName;
        }
    }

    public String getOperationStation() {
        if(operationStation != null) {
            return operationStation.trim();
        }
        else {
            return operationStation;
        }
    }

    public void setOperationStation(String operationStation) {
        if(operationStation != null) {
            this.operationStation = operationStation.toUpperCase();
        }
        else {
            this.operationStation = operationStation;
        }
    }

    public String getSplc() {
        if(splc != null) {
            return splc.trim();
        }
        else {
            return splc;
        }
    }

    public void setSplc(String splc) {
        if(splc != null) {
            this.splc = splc.toUpperCase();
        }
        else {
            this.splc = splc;
        }
    }

    public String getRule260Station() {
        if(rule260Station != null) {
            return rule260Station.trim();
        }
        else {
            return rule260Station;
        }
    }

    public void setRule260Station(String rule260Station) {
        if(rule260Station != null) {
            this.rule260Station = rule260Station.toUpperCase();
        }
        else {
            this.rule260Station = rule260Station;
        }
    }

    public Long getTermId() {
        return termId;
    }

    public void setTermId(Long termId) {
        this.termId = termId;
    }

    public String getChar5Spell() {
        if(char5Spell != null) {
            return char5Spell.trim();
        }
        else {
            return char5Spell;
        }
    }

    public void setChar5Spell(String char5Spell) {
        if(char5Spell != null) {
            this.char5Spell = char5Spell.toUpperCase();
        }
        else {
            this.char5Spell = char5Spell;
        }
    }

    public String getChar5Alias() {
        if(char5Alias != null) {
            return char5Alias.trim();
        }
        else {
            return char5Alias;
        }
    }

    public void setChar5Alias(String char5Alias) {
        if(char5Alias != null) {
            this.char5Alias = char5Alias.toUpperCase();
        }
        else {
            this.char5Alias = char5Alias;
        }
    }

    public String getChar8Spell() {
        if(char8Spell != null) {
            return char8Spell.trim();
        }
        else {
            return char8Spell;
        }
    }

    public void setChar8Spell(String char8Spell) {
        if(char8Spell != null) {
            this.char8Spell = char8Spell.toUpperCase();
        }
        else {
            this.char8Spell = char8Spell;
        }
    }

    public String getDivision() {
        if(division != null) {
            return division.trim();
        }
        else {
            return division;
        }
    }

    public void setDivision(String division) {
        if(division != null) {
            this.division = division.toUpperCase();
        }
        else {
            this.division = division;
        }
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getBillingInd() {
    	if(billingInd != null) {
            return billingInd.trim();
    	}
        else {
            return billingInd;
        }
	}

	public void setBillingInd(String billingInd) {
		if(billingInd != null) {
            this.billingInd = billingInd.toUpperCase();
		}
        else {
            this.billingInd = billingInd;
        }
	}

	public Timestamp getExpiredDate() {
		return expiredDate;
	}

	public void setExpiredDate(Timestamp expiredDate) {
		this.expiredDate = expiredDate;
	}

	public String getTopPick() {
		if(topPick != null) {
            return topPick.trim();
    	}
        else {
            return topPick;
        }
	}

	public void setTopPick(String topPick) {
		if(topPick != null) {
            this.topPick = topPick.toUpperCase();
		}
        else {
            this.topPick = topPick;
        }
	}

	public String getBottomPick() {
		if(bottomPick != null) {
            return bottomPick.trim();
    	}
        else {
            return bottomPick;
        }
	}

	public void setBottomPick(String bottomPick) {
		if(bottomPick != null) {
            this.bottomPick = bottomPick.toUpperCase();
		}
        else {
            this.bottomPick = bottomPick;
        }
	}


	public void setTermId(long termId) {
		this.termId = termId;
	}

//	public List<StationRestriction> getStationRestriction() {
//		return stationRestriction;
//	}

//	public void setStationRestriction(List<StationRestriction> stationRestriction) {
//		this.stationRestriction = stationRestriction;
//	}

	public Station(String uversion, String createUserId, Timestamp createDateTime, String updateUserId, Timestamp updateDateTime, String updateExtensionSchema) {
        super(uversion, createUserId, createDateTime, updateUserId, updateDateTime, updateExtensionSchema);
    }


    public Station(String uversion, String createUserId, Object createDateTime, String updateUserId,
                   Object updateDateTime, String updateExtensionSchema,
                   String stationName, String roadNumber,long termId, String FSAC, String state,
                   String billAtFsac, String roadName, String operationStation, String splc,
                   String rule260Station, String intermodalIndicator, String char5Spell,
                   String char5Alias, String char8Spell, String division,
                   String topPick, String bottomPick, Object expirationDate) {
        super(uversion, createUserId, (Timestamp) createDateTime, updateUserId, (Timestamp) updateDateTime, updateExtensionSchema);

        this.termId = termId;
        this.roadNumber = roadNumber;
        this.FSAC = FSAC;
        this.stationName = stationName;
        this.state = state;
        this.billAtFsac = billAtFsac;
        this.roadName = roadName;
        this.operationStation = operationStation;
        this.splc = splc;
        this.intermodalIndicator = intermodalIndicator;
        this.rule260Station = rule260Station;
        this.char5Spell = char5Spell;
        this.char5Alias = char5Alias;
        this.char8Spell = char8Spell;
        this.division = division;
        this.expirationDate = (LocalDate) expirationDate;
        this.topPick = topPick;
        this.bottomPick = bottomPick;
    }

}
