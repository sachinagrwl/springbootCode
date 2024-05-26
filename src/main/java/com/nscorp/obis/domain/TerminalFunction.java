package com.nscorp.obis.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.persistence.Transient;

import java.util.Date;
import java.sql.Timestamp;

@Entity
@Table(name="TERMINAL_FUNCTION")
@IdClass(TerminalFunctionComposite.class)

public class TerminalFunction extends AuditInfo{
	@Id
	@Column(name = "TERM_ID", columnDefinition = "double(15)", nullable = false)
	Long terminalId;
	
	@Id
	@Column(name = "FUNCTION_NM", columnDefinition = "char(100)", nullable = false)
    private String functionName;

	@Column(name = "FUNCTION_DESC", columnDefinition = "char(255)", nullable = true)
	private String functionDesc;
    
	@Column(name = "STATUS_FLAG", columnDefinition = "char(4)", nullable = true)
	private String statusFlag;
	
	@Column(name = "EFF_DT", columnDefinition = "date", nullable = true)
    private Date effectiveDate;

	@Column(name = "END_DT", columnDefinition = "date", nullable = true)
	private Date endDate;
	
	@Transient
	private String terminalName;
	
	
	
	public String getTerminalName() {
		return terminalName;
	}
	public void setTerminalName(String terminalName) {
		this.terminalName = terminalName;
	}
	public Long getTerminalId() {
		return terminalId;
	}
	public void setTerminalId(Long terminalId) {
		this.terminalId = terminalId;
	}
	public String getFunctionName() {
		return functionName;
	}
	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}
	public String getFunctionDesc() {
		return functionDesc;
	}
	public void setFunctionDesc(String functionDesc) {
		this.functionDesc = functionDesc;
	}
	public String getStatusFlag() {
		if(statusFlag!=null) {
			return statusFlag.trim();
		}
		return statusFlag;
	}
	public void setStatusFlag(String statusFlag) {
		this.statusFlag = statusFlag;
	}
	public Date getEffectiveDate() {
		return effectiveDate;
	}
	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public TerminalFunction(String uversion, String createUserId, Timestamp createDateTime, String updateUserId,
			Timestamp updateDateTime, String updateExtensionSchema, Long terminalId, String functionName,
			String functionDesc, String statusFlag, Date effectiveDate, Date endDate, String terminalName) {
		super(uversion, createUserId, createDateTime, updateUserId, updateDateTime, updateExtensionSchema);
		this.terminalId = terminalId;
		this.functionName = functionName;
		this.functionDesc = functionDesc;
		this.statusFlag = statusFlag;
		this.effectiveDate = effectiveDate;
		this.endDate = endDate;
		this.terminalName = terminalName;
	}
	public TerminalFunction() {
		super();
		// TODO Auto-generated constructor stub
	}
	public TerminalFunction(String uversion, String createUserId, Timestamp createDateTime, String updateUserId,
			Timestamp updateDateTime, String updateExtensionSchema) {
		super(uversion, createUserId, createDateTime, updateUserId, updateDateTime, updateExtensionSchema);
		// TODO Auto-generated constructor stub
	}

    /*
    SELECT * FROM INTERMODAL.TERMINAL t LEFT JOIN intermodal.TERMINAL_FUNCTION tf
    ON t.TERM_ID = tf.TERM_ID
    WHERE tf.FUNCTION_NM = 'EXPRESS_NS' ORDER BY t.TERM_NM ;
     */
    
    
    
}