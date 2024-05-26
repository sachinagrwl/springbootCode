package com.nscorp.obis.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name = "TERM_FREE_DAY")
@IdClass(TermFreeDayPrimaryKeys.class)
public class TermFreeDay extends AuditInfo {

    @Id
    @Column(name = "TERM_ID", columnDefinition = "double", nullable = false)
    private Long termId;

    @Transient
    private List<Terminal> terminalList;

    @Transient
    private String terminalName;

    @Id
    @Column(name = "CLOSE_DT", columnDefinition = "DATE(10)", nullable = false)
    private LocalDate closeDate;

    @Id
    @Column(name = "CLOSE_FR_TM", columnDefinition = "TIME(8)", nullable = false)
    private LocalTime closeFromTime;

    @Column(name = "CLOSE_RSN_CD", columnDefinition = "char(3)", nullable = true)
    private String closeRsnCd;

    @Column(name = "CLOSE_RSN_DSC", columnDefinition = "char(30)", nullable = true)
    private String closeRsnDesc;

    @Column(name = "CLOSE_TO_TM", columnDefinition = "TIME(8)", nullable = true)
    private LocalTime closeToTime;

    @Column(name = "FREE_DAY_ALLOWNCE", columnDefinition = "smallint(5)", nullable = true)
    private Integer freeDayAllowance;

    @Transient
    private String freeDay;

    @ManyToOne
    @JoinColumn(name = "TERM_ID", referencedColumnName = "TERM_ID", insertable = false, updatable = false)
    @JsonIgnore
    private Terminal terminal;

	public TermFreeDay(){
    }

    public TermFreeDay(String uversion, String createUserId, Object createDateTime, String updateUserId,
                       Object updateDateTime, String updateExtensionSchema,
                       Object termId, Object closeDate, Object closeFromTime, String closeRsnCd, String closeRsnDesc,
                       Object closeToTime, Integer freeDayAllowance, String terminalName, Terminal terminal) {
        super(uversion, createUserId, (Timestamp) createDateTime, updateUserId, (Timestamp) updateDateTime, updateExtensionSchema);
        this.termId = (Long) termId;
        this.closeDate = (LocalDate) closeDate;
        this.closeFromTime = (LocalTime) closeFromTime;
        this.closeRsnCd = closeRsnCd;
        this.closeRsnDesc = closeRsnDesc;
        this.closeToTime = (LocalTime) closeToTime;
        this.freeDayAllowance = freeDayAllowance;
        this.terminalName = terminalName;
        this.terminal = terminal;
    }

    public Long getTermId() {
            return termId;
    }

    public Terminal getTerminal() {
        return terminal;
    }

    public void setTerminal(Terminal terminal) {
        this.terminal = terminal;
    }

    public List<Terminal> getTerminalList() {
        return terminalList;
    }

    public void setTerminalList(List<Terminal> terminalList) {
        this.terminalList = terminalList;
    }

    public String getTerminalName(){
        if(terminal != null) {
            return terminal.getTerminalName().trim();
        } else{
            return terminalName;
        }
    }
    public void setTerminalName(String terminalName){
            this.terminalName = terminalName;
    }

    public void setTermId(Long termId) {
        this.termId = termId;
    }

    public LocalDate getCloseDate() {
        return closeDate;
    }

    public void setCloseDate(LocalDate closeDate) {
        this.closeDate = closeDate;
    }

    public LocalTime getCloseFromTime() {
        return closeFromTime;
    }

    public void setCloseFromTime(LocalTime closeFromTime) {
        this.closeFromTime = closeFromTime;
    }

    public LocalTime getCloseToTime() {
        return closeToTime;
    }

    public void setCloseToTime(LocalTime closeToTime) {
        this.closeToTime = closeToTime;
    }

    public String getCloseRsnCd() {
        if(closeRsnCd != null)
            return closeRsnCd.trim();
        else
            return closeRsnCd;
    }

    public void setCloseRsnCd(String closeRsnCd) {
        if(closeRsnCd != null)
            this.closeRsnCd = closeRsnCd.toUpperCase();
        else
        	this.closeRsnCd = closeRsnCd;
    }

    public String getCloseRsnDesc() {
        if(closeRsnDesc != null)
            return closeRsnDesc.trim();
        else
            return closeRsnDesc;
    }

    public void setCloseRsnDesc(String closeRsnDesc) {
        if(closeRsnDesc != null)
            this.closeRsnDesc = closeRsnDesc.toUpperCase();
        else
        	this.closeRsnDesc = closeRsnDesc;
    }

    public String getFreeDay(){
        if(this.freeDayAllowance != null && this.freeDayAllowance == 24)
            this.freeDay = "Y";
        if(this.freeDayAllowance != null && this.freeDayAllowance == 0)
            this.freeDay = "N";
        return freeDay;
    }

    public void setFreeDay(String freeDay) {
        if(freeDay != null && (freeDay.equals("Y") || freeDay.equals("y")))
            this.freeDayAllowance = 24;
        else if(freeDay != null && (freeDay.equals("N") || freeDay.equals("n")))
            this.freeDayAllowance = 0;
    }

    public Integer getFreeDayAllowance() {
        return freeDayAllowance;
    }

    public void setFreeDayAllowance(Integer freeDayAllowance) {
        this.freeDayAllowance = freeDayAllowance;
    }
}
