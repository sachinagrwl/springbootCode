package com.nscorp.obis.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

@Entity
@Table(name = "NOTEPAD_ENTRY")
public class NotepadEntry extends AuditInfo {

    @Id
    @Column(name = "NOTEPAD_ID", columnDefinition = "Double(15)", nullable = false)
    private Double notepadId;

    @Column(name = "CUST_ID", columnDefinition = "Double(15)", nullable = true)
    private Long customerId;

    @Column(name = "TERM_ID", columnDefinition = "Double(15)", nullable = true)
    private Long terminalId;

    @Column(name = "SVC_ID", columnDefinition = "Double(15)", nullable = true)
    private Long svcId;

    @Column(name = "DRIVER_ID", columnDefinition = "Double(15)", nullable = true)
    private Long driverId;

    @Column(name = "DRAY_ID", columnDefinition = "char(4)", nullable = true)
    private String drayId;

    @Column(name = "EQ_TP", columnDefinition = "char(1)", nullable = true)
    private String equipmentType;

    @Column(name = "EQ_INIT", columnDefinition = "char(4)", nullable = true)
    private String equipmentInit;

    @Column(name = "EQ_NR", columnDefinition = "decimal(19)", nullable = true)
    private Integer equipmentNumber;

    @Column(name = "EQ_ID", columnDefinition = "char(4)", nullable = true)
    private String equipmentId;

    @Column(name = "CHAS_INIT", columnDefinition = "char(4)", nullable = true)
    private String chassisInit;

    @Column(name = "CHAS_NR", columnDefinition = "decimal(19)", nullable = true)
    private Integer chassisNumber;

    @Column(name = "CHAS_ID", columnDefinition = "char(4)", nullable = true)
    private String chassisId;

    @Column(name = "NOTEPAD_TXT", columnDefinition = "char(255)", nullable = true)
    private String notepadText;


    public Double getNotepadId() {
        return notepadId;
    }

    public void setNotepadId(Double notepadId) {
        this.notepadId = notepadId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(Long terminalId) {
        this.terminalId = terminalId;
    }

    public Long getSvcId() {
        return svcId;
    }

    public void setSvcId(Long svcId) {
        this.svcId = svcId;
    }

    public Long getDriverId() {
        return driverId;
    }

    public void setDriverId(Long driverId) {
        this.driverId = driverId;
    }

    public String getDrayId() {
        return drayId;
    }

    public void setDrayId(String drayId) {
        this.drayId = drayId;
    }

    public String getEquipmentType() {
        return equipmentType;
    }

    public void setEquipmentType(String equipmentType) {
        this.equipmentType = equipmentType;
    }

    public String getEquipmentInit() {
        return equipmentInit;
    }

    public void setEquipmentInit(String equipmentInit) {
        this.equipmentInit = equipmentInit;
    }

    public Integer getEquipmentNumber() {
        return equipmentNumber;
    }

    public void setEquipmentNumber(Integer equipmentNumber) {
        this.equipmentNumber = equipmentNumber;
    }

    public String getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(String equipmentId) {
        this.equipmentId = equipmentId;
    }

    public String getChassisInit() {
        return chassisInit;
    }

    public void setChassisInit(String chassisInit) {
        this.chassisInit = chassisInit;
    }

    public Integer getChassisNumber() {
        return chassisNumber;
    }

    public void setChassisNumber(Integer chassisNumber) {
        this.chassisNumber = chassisNumber;
    }

    public String getChassisId() {
        return chassisId;
    }

    public void setChassisId(String chassisId) {
        this.chassisId = chassisId;
    }

    public String getNotepadText() {
        if (notepadText != null) {
            notepadText = notepadText.trim();
        }
        return notepadText;
    }

    public void setNotepadText(String notepadText) {
        this.notepadText = notepadText;
    }

    public NotepadEntry(String uversion, String createUserId, Timestamp createDateTime, String updateUserId, Timestamp updateDateTime, String updateExtensionSchema,
                        Double notepadId, Long customerId, Long terminalId, Long svcId, Long driverId, String drayId, String equipmentType, String equipmentInit, Integer equipmentNumber, String equipmentId, String chassisInit, Integer chassisNumber, String chassisId, String notepadText) {
        super(uversion, createUserId, createDateTime, updateUserId, updateDateTime, updateExtensionSchema);
        this.notepadId = notepadId;
        this.customerId = customerId;
        this.terminalId = terminalId;
        this.svcId = svcId;
        this.driverId = driverId;
        this.drayId = drayId;
        this.equipmentType = equipmentType;
        this.equipmentInit = equipmentInit;
        this.equipmentNumber = equipmentNumber;
        this.equipmentId = equipmentId;
        this.chassisInit = chassisInit;
        this.chassisNumber = chassisNumber;
        this.chassisId = chassisId;
        this.notepadText = notepadText;
    }

    public NotepadEntry() {
        super();
        // TODO Auto-generated constructor stub
    }

    public NotepadEntry(String uversion, String createUserId, Timestamp createDateTime, String updateUserId, Timestamp updateDateTime, String updateExtensionSchema) {
        super(uversion, createUserId, createDateTime, updateUserId, updateDateTime, updateExtensionSchema);
        // TODO Auto-generated constructor stub

    }


}
