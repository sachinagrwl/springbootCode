package com.nscorp.obis.domain;

import javax.persistence.*;
import java.sql.Time;
import java.sql.Timestamp;


@Entity
@Table(name = "TERM_TRAIN")
@IdClass(TermTrainComposite.class)
public class TerminalTrain extends AuditInfo{
			
	  @Id
	  @Column(name = "TERM_ID",length = 15, columnDefinition = "double", nullable = false)
	    private Long termId;
	  
	  @Id
	  @Column(name = "TRAIN_NR", columnDefinition = "char(4)", nullable = false)
	    private String trainNr;
	  
	
	  @Column(name = "TRAIN_DESC", columnDefinition = "char(15)", nullable = true)
	    private String trainDesc;
	  
	  @Column(name = "CUTOFF_DEFAULT", columnDefinition = "Date", nullable = true)
	    private Time cutoffDefault;
	
	  @Column(name = "CUTOFF_MON", columnDefinition = "Date", nullable = true)
	    private Time cutoffMon;
	  
	  @Column(name = "CUTOFF_TUE", columnDefinition = "Date", nullable = true)
	    private Time cutoffTue;
	
	  @Column(name = "CUTOFF_WED", columnDefinition = "Date", nullable = true)
	    private Time cutoffWed;
	  
	  @Column(name = "CUTOFF_THU", columnDefinition = "Date", nullable = true)
	    private Time cutoffThu;
	
	  @Column(name = "CUTOFF_FRI", columnDefinition = "Date", nullable = true)
	    private Time cutoffFri;
	  
	  @Column(name = "CUTOFF_SAT", columnDefinition = "Date", nullable = true)
	    private Time cutoffSat;
	
	  @Column(name = "CUTOFF_SUN", columnDefinition = "Date", nullable = true)
	    private Time cutoffSun;
	  
	  @Column(name = "TRAIN_DIR", columnDefinition = "char(1)", nullable = true)
	    private String trainDir;
	  
	  @Column(name = "MAX_FOOTAGE", columnDefinition = "Smallint", nullable = true)
	    private Integer maxFootage;

	  @Transient
	  private String oldTrainNr;


	public String getOldTrainNr() {
		return oldTrainNr;
	}

	public void setOldTrainNr(String oldTrainNr) {
		this.oldTrainNr = oldTrainNr;
	}

	public Long getTermId() {
		return termId;
	}

	public void setTermId(Long termId) {
		this.termId = termId;
	}

	public String getTrainNr() {
		if(trainNr != null) {
			return trainNr.trim();
		}
		else {
			return trainNr;
		}
	}

	public void setTrainNr(String trainNr) {
		this.trainNr = trainNr;
	}


	public String getTrainDesc() {
		if(trainDesc != null) {
			return trainDesc.trim();
		}
		else {
			return trainDesc ;
		}
	}
	

	public void setTrainDesc(String trainDesc) {
		this.trainDesc = trainDesc;
	}

	public Time getCutoffDefault() {
		return cutoffDefault;
	}

	public void setCutoffDefault(Time cutoffDefault) {
		this.cutoffDefault = cutoffDefault;
	}

	public Time getCutoffMon() {
		return cutoffMon;
	}

	public void setCutoffMon(Time cutoffMon) {
		this.cutoffMon = cutoffMon;
	}

	public Time getCutoffTue() {
		return cutoffTue;
	}

	public void setCutoffTue(Time cutoffTue) {
		this.cutoffTue = cutoffTue;
	}

	public Time getCutoffWed() {
		return cutoffWed;
	}

	public void setCutoffWed(Time cutoffWed) {
		this.cutoffWed = cutoffWed;
	}

	public Time getCutoffThu() {
		return cutoffThu;
	}

	public void setCutoffThu(Time cutoffThu) {
		this.cutoffThu = cutoffThu;
	}

	public Time getCutoffFri() {
		return cutoffFri;
	}

	public void setCutoffFri(Time cutoffFri) {
		this.cutoffFri = cutoffFri;
	}

	public Time getCutoffSat() {
		return cutoffSat;
	}

	public void setCutoffSat(Time cutoffSat) {
		this.cutoffSat = cutoffSat;
	}

	public Time getCutoffSun() {
		return cutoffSun;
	}

	public void setCutoffSun(Time cutoffSun) {
		this.cutoffSun = cutoffSun;
	}

	
	public String getTrainDir() {
		if(trainDir != null) {
			return trainDir.trim();
		}
		else {
			return trainDir ;
		}
	}
	

	public void setTrainDir(String trainDir) {
		this.trainDir = trainDir;
	}

	public Integer getMaxFootage() {
		return maxFootage;
	}

	public void setMaxFootage(Integer maxFootage) {
		this.maxFootage = maxFootage;
	}

	public TerminalTrain(String uversion, String createUserId, Timestamp createDateTime, String updateUserId,
			Timestamp updateDateTime, String updateExtensionSchema, Long termId, String trainNr, String trainDesc,
			Time cutoffDefault, Time cutoffMon, Time cutoffTue, Time cutoffWed, Time cutoffThu, Time cutoffFri,
			Time cutoffSat, Time cutoffSun, String trainDir, Integer maxFootage) {
		super(uversion, createUserId, createDateTime, updateUserId, updateDateTime, updateExtensionSchema);
		this.termId = termId;
		this.trainNr = trainNr;
		this.trainDesc = trainDesc;
		this.cutoffDefault = cutoffDefault;
		this.cutoffMon = cutoffMon;
		this.cutoffTue = cutoffTue;
		this.cutoffWed = cutoffWed;
		this.cutoffThu = cutoffThu;
		this.cutoffFri = cutoffFri;
		this.cutoffSat = cutoffSat;
		this.cutoffSun = cutoffSun;
		this.trainDir = trainDir;
		this.maxFootage = maxFootage;
	}

	public TerminalTrain() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TerminalTrain(String uversion, String createUserId, Timestamp createDateTime, String updateUserId,
			Timestamp updateDateTime, String updateExtensionSchema) {
		super(uversion, createUserId, createDateTime, updateUserId, updateDateTime, updateExtensionSchema);
		// TODO Auto-generated constructor stub
	}

	
}
