package com.nscorp.obis.domain;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Table(name = "EQ_AAR_600_CONT")
@IdClass(EquipmentAAR600ContPrimaryKeys.class)
public class EquipmentAAR600Cont extends AuditInfo {

    @Id
    @Column(name = "EQ_INIT", columnDefinition = "char(4)", nullable = false)
    private String equipInit;

    @Id
    @Column(name = "BEG_EQ_NR", columnDefinition = "decimal(19)", nullable = false)
    private BigDecimal beginningEqNr;

    @Id
    @Column(name = "END_EQ_NR", columnDefinition = "decimal(19)", nullable = false)
    private BigDecimal endEqNbr;

    public EquipmentAAR600Cont() {
    }


    public String getEquipInit() {
    	if(equipInit != null) {
			return equipInit.trim();
		}
		else {
			return equipInit;
		}
    }

    public void setEquipInit(String equipInit) {
    	if (equipInit != null) {
			this.equipInit = equipInit.toUpperCase();
		} else {
			this.equipInit = equipInit;
		}
    }

    public BigDecimal getBeginningEqNr() {
        return beginningEqNr;
    }

    public void setBeginningEqNr(BigDecimal beginningEqNr) {
        this.beginningEqNr = beginningEqNr;
    }

    public BigDecimal getEndEqNbr() {
        return endEqNbr;
    }

    public void setEndEqNbr(BigDecimal endEqNbr) {
        this.endEqNbr = endEqNbr;
    }

    public EquipmentAAR600Cont(String uversion, String createUserId, Timestamp createDateTime, String updateUserId, Timestamp updateDateTime, String updateExtensionSchema, String equipInit, BigDecimal beginningEqNr, BigDecimal endEqNbr) {
        super(uversion, createUserId, createDateTime, updateUserId, updateDateTime, updateExtensionSchema);
        this.equipInit = equipInit;
        this.beginningEqNr = beginningEqNr;
        this.endEqNbr = endEqNbr;
    }
}
