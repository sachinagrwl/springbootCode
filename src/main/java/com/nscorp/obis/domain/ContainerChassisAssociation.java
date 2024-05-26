package com.nscorp.obis.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Table(name = "CONT_CHAS_ASSOC")
public class ContainerChassisAssociation extends AuditInfo {

    @Id
    @Column(name = "ASSOC_ID", length = 15, columnDefinition = "double", nullable = false)
    private Long associationId;

    @Column(name = "CONT_INIT", columnDefinition = "char(4)", nullable = true)
    private String containerInit;

    @Column(name = "CONT_LOW_NR", columnDefinition = "decimal(19)", nullable = true)
    private BigDecimal containerLowNumber;

    @Column(name = "CONT_HIGH_NR", columnDefinition = "decimal(19)", nullable = true)
    private BigDecimal containerHighNumber;

    @Column(name = "CONT_LENGTH", columnDefinition = "smallint", nullable = true)
    private Integer containerLength;

    @Column(name = "CHAS_INIT", columnDefinition = "char(4)", nullable = true)
    private String chassisInit;

    @Column(name = "CHAS_LOW_NR", columnDefinition = "decimal(19)", nullable = true)
    private BigDecimal chassisLowNumber;

    @Column(name = "CHAS_HIGH_NR", columnDefinition = "decimal(19)", nullable = true)
    private BigDecimal chassisHighNumber;

    @Column(name = "CHAS_LENGTH", columnDefinition = "smallint", nullable = true)
    private Integer chassisLength;

    @Column(name = "ALLOW_IND", columnDefinition = "char(1)", nullable = true)
    private String allowIndicator;

    @Column(name = "EXPIRED_DT_TM", columnDefinition = "timestamp", nullable = true)
    private Timestamp expiredDateTime;

    public ContainerChassisAssociation(String uversion, String createUserId, Timestamp createDateTime,
                                       String updateUserId, Timestamp updateDateTime, String updateExtensionSchema,
                                               Long associationId, String containerInit, BigDecimal containerLowNumber,
                                       BigDecimal containerHighNumber, Integer containerLength, String chassisInit, BigDecimal chassisLowNumber,
                                       BigDecimal chassisHighNumber, Integer chassisLength, String allowIndicator, Timestamp expiredDateTime) {
        super(uversion, createUserId, createDateTime, updateUserId, updateDateTime, updateExtensionSchema);
        this.associationId = associationId;
        this.containerInit = containerInit;
        this.containerLowNumber = containerLowNumber;
        this.containerHighNumber = containerHighNumber;
        this.containerLength = containerLength;
        this.chassisInit = chassisInit;
        this.chassisLowNumber = chassisLowNumber;
        this.chassisHighNumber = chassisHighNumber;
        this.chassisLength = chassisLength;
        this.allowIndicator = allowIndicator;
        this.expiredDateTime = expiredDateTime;
    }

    public ContainerChassisAssociation() {
        super();
    }

    public Long getAssociationId() {
        return associationId;
    }

    public void setAssociationId(Long associationId) {
        this.associationId = associationId;
    }

    public String getContainerInit() {
        if(containerInit != null){
            return containerInit.trim();
        } else{
            return containerInit;
        }
    }

    public void setContainerInit(String containerInit) {
        if(containerInit != null) {
            this.containerInit = containerInit.toUpperCase();
        } else{
            this.containerInit = containerInit;
        }
    }

    public BigDecimal getContainerLowNumber() {
        return containerLowNumber;
    }

    public void setContainerLowNumber(BigDecimal containerLowNumber) {
        this.containerLowNumber = containerLowNumber;
    }

    public BigDecimal getContainerHighNumber() {
        return containerHighNumber;
    }

    public void setContainerHighNumber(BigDecimal containerHighNumber) {
        this.containerHighNumber = containerHighNumber;
    }

    public Integer getContainerLength() {
        return containerLength;
    }

    public void setContainerLength(Integer containerLength) {
        this.containerLength = containerLength;
    }

    public String getChassisInit() {
        if(chassisInit != null){
            return chassisInit.trim();
        } else{
            return chassisInit;
        }
    }

    public void setChassisInit(String chassisInit) {
        if(chassisInit != null){
            this.chassisInit = chassisInit.toUpperCase();
        } else{
            this.chassisInit = chassisInit;
        }
    }

    public BigDecimal getChassisLowNumber() {
        return chassisLowNumber;
    }

    public void setChassisLowNumber(BigDecimal chassisLowNumber) {
        this.chassisLowNumber = chassisLowNumber;
    }

    public BigDecimal getChassisHighNumber() {
        return chassisHighNumber;
    }

    public void setChassisHighNumber(BigDecimal chassisHighNumber) {
        this.chassisHighNumber = chassisHighNumber;
    }

    public Integer getChassisLength() {
        return chassisLength;
    }

    public void setChassisLength(Integer chassisLength) {
        this.chassisLength = chassisLength;
    }

    public String getAllowIndicator() {
        if(allowIndicator != null){
            return allowIndicator.trim();
        } else{
            return allowIndicator;
        }
    }

    public void setAllowIndicator(String allowIndicator) {
        if(allowIndicator != null){
            this.allowIndicator = allowIndicator.toUpperCase();
        } else{
            this.allowIndicator = allowIndicator;
        }
    }

    public Timestamp getExpiredDateTime() {
        return expiredDateTime;
    }

    public void setExpiredDateTime(Timestamp expiredDateTime) {
        this.expiredDateTime = expiredDateTime;
    }
}
