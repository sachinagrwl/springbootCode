package com.nscorp.obis.domain;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "BLOCK")
@IdClass(BlockComposite.class)
public class Block extends AuditInfo {

	@Column(name = "TERM_ID", length = 15, columnDefinition = "double", nullable = false)
	private Long termId;

	@Column(name = "TRAIN_NR", columnDefinition = "char(4)", nullable = true)
	private String trainNr;

	@Id
	@Column(name = "BLOCK_ID", columnDefinition = "double", nullable = false)
	private Long blockId;

	@Column(name = "BLOCK_NM", columnDefinition = "char(15)", nullable = true)
	private String blockNm;

	@Column(name = "PARENT_BLOCK_ID", columnDefinition = "double", nullable = true)
	private Long parantBlockId;

	@Column(name = "BLOCK_ORDER", columnDefinition = "char(2)", nullable = true)
	private String blockOrder;

	@Column(name = "BLOCK_PRIORITY", length = 1, columnDefinition = "Smallint", nullable = true)
	private Integer blockPriority;

	@Column(name = "SW_INTERCHANGE", columnDefinition = "char(1)", nullable = true)
	private String swInterchange;

	@Column(name = "ALLOW_SAME_CAR", columnDefinition = "char(1)", nullable = true)
	private String allowSameCar;

	@Column(name = "HEIGHT_FEET", length = 4, columnDefinition = "Smallint", nullable = true)
	private Integer hightFeet;

	@Column(name = "HEIGHT_INCHES", length = 4, columnDefinition = "Smallint", nullable = true)
	private Integer hightInches;

	@Column(name = "WEIGHT", length = 4, columnDefinition = "Smallint", nullable = true)
	private Integer weight;
	
	@Column(name = "BLOCK_MON", columnDefinition = "char(1)", nullable = true)
	private String blockMon;

	@Column(name = "BLOCK_TUE", columnDefinition = "char(1)", nullable = true)
	private String blockTue ;

	@Column(name = "BLOCK_WED", columnDefinition = "char(1)", nullable = true)
	private String blockWed ;

	@Column(name = "BLOCK_THU", columnDefinition = "char(1)", nullable = true)
	private String blockThu ;

	@Column(name = "BLOCK_FRI", columnDefinition = "char(1)", nullable = true)
	private String blockFri ;

	@Column(name = "BLOCK_SAT", columnDefinition = "char(1)", nullable = true)
	private String blockSat ;

	@Column(name = "BLOCK_SUN", columnDefinition = "char(1)", nullable = true)
	private String blockSun ;
	
	@Transient
	private String trainNbr;
	
	@Transient
	private String trainDesc;
	
	
	@Transient
    @Convert(converter = DaysOfWeekConverter.class)
    List<DayOfWeek> activeDays;

    public List<DayOfWeek> getActiveDays() {
        this.activeDays = new ArrayList<DayOfWeek>();
        if ("Y".equalsIgnoreCase(this.blockSun))
            this.activeDays.add(DayOfWeek.SUN);
        if ("Y".equalsIgnoreCase(this.blockMon))
            this.activeDays.add(DayOfWeek.MON);
        if ("Y".equalsIgnoreCase(this.blockTue))
            this.activeDays.add(DayOfWeek.TUE);
        if ("Y".equalsIgnoreCase(this.blockWed))
            this.activeDays.add(DayOfWeek.WED);
        if ("Y".equalsIgnoreCase(this.blockThu))
            this.activeDays.add(DayOfWeek.THU);
        if ("Y".equalsIgnoreCase(this.blockFri))
            this.activeDays.add(DayOfWeek.FRI);
        if ("Y".equalsIgnoreCase(this.blockSat))
            this.activeDays.add(DayOfWeek.SAT);
        return activeDays;
    }
    
    
	@OneToMany(targetEntity = DestinationSetting.class, orphanRemoval = true ,cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name="BLOCK_ID", referencedColumnName = "BLOCK_ID")
	List<DestinationSetting> destinationSettings = new ArrayList<>();	
	
	public Block() {
		super();
	}

	

	public Block(String uversion, String createUserId, Timestamp createDateTime, String updateUserId,
			Timestamp updateDateTime, String updateExtensionSchema, Long termId, String trainNr, Long blockId,
			String blockNm, Long parantBlockId, String blockOrder, Integer blockPriority, String swInterchange,
			String allowSameCar, Integer hightFeet, Integer hightInches, Integer weight, String blockMon,
			String blockTue, String blockWed, String blockThu, String blockFri, String blockSat, String blockSun,
			String trainNbr, String trainDesc, List<DayOfWeek> activeDays, List<DestinationSetting> destinationSettings) {
		super(uversion, createUserId, createDateTime, updateUserId, updateDateTime, updateExtensionSchema);
		this.termId = termId;
		this.trainNr = trainNr;
		this.blockId = blockId;
		this.blockNm = blockNm;
		this.parantBlockId = parantBlockId;
		this.blockOrder = blockOrder;
		this.blockPriority = blockPriority;
		this.swInterchange = swInterchange;
		this.allowSameCar = allowSameCar;
		this.hightFeet = hightFeet;
		this.hightInches = hightInches;
		this.weight = weight;
		this.blockMon = blockMon;
		this.blockTue = blockTue;
		this.blockWed = blockWed;
		this.blockThu = blockThu;
		this.blockFri = blockFri;
		this.blockSat = blockSat;
		this.blockSun = blockSun;
		this.trainNbr = trainNbr;
		this.trainDesc = trainDesc;
		this.activeDays = activeDays;
		this.destinationSettings = destinationSettings;
	}



	public String getTrainNbr() {
		return trainNbr;
	}



	public void setTrainNbr(String trainNbr) {
		this.trainNbr = trainNbr;
	}



	public String getTrainDesc() {
		return trainDesc;
	}



	public void setTrainDesc(String trainDesc) {
		this.trainDesc = trainDesc;
	}



	public Long getTermId() {
		return termId;
	}

	public void setTermId(Long termId) {
		this.termId = termId;
	}

	public String getTrainNr() {
		if(trainNr !=null) {
		
			return trainNr.trim();
		}
		else {
			return trainNr;
			
		}
	}

	public void setTrainNr(String trainNr) {
		this.trainNr = trainNr;
	}

	public Long getBlockId() {
		return blockId;
	}

	public void setBlockId(Long blockId) {
		this.blockId = blockId;
	}

	public String getBlockNm() {
		if(blockNm != null) {
			return blockNm.trim().toUpperCase();
		}
		else {
			return blockNm.toUpperCase();
		}
	}

	public void setBlockNm(String blockNm) {
		this.blockNm = blockNm;
	}

	public Long getParantBlockId() {
		return parantBlockId;
	}

	public void setParantBlockId(Long parantBlockId) {
		this.parantBlockId = parantBlockId;
	}

	public String getBlockOrder() {
		if(blockOrder != null) {
			return blockOrder.trim();
		}
		else {
			return blockOrder;
		}
	}

	public void setBlockOrder(String blockOrder) {
		this.blockOrder = blockOrder;
	}

	public Integer getBlockPriority() {
		return blockPriority;
	}

	public void setBlockPriority(Integer blockPriority) {
		this.blockPriority = blockPriority;
	}

	public String getSwInterchange() {
		return swInterchange;
	}

	public void setSwInterchange(String swInterchange) {
		this.swInterchange = swInterchange;
	}

	public String getAllowSameCar() {
		return allowSameCar;
	}

	public void setAllowSameCar(String allowSameCar) {
		this.allowSameCar = allowSameCar;
	}

	public String getBlockMon() {
		return blockMon;
	}

	public void setBlockMon(String blockMon) {
		this.blockMon = blockMon;
	}

	public String getBlockTue() {
		return blockTue;
	}

	public void setBlockTue(String blockTue) {
		this.blockTue = blockTue;
	}

	public String getBlockWed() {
		return blockWed;
	}

	public void setBlockWed(String blockWed) {
		this.blockWed = blockWed;
	}

	public String getBlockThu() {
		return blockThu;
	}

	public void setBlockThu(String blockThu) {
		this.blockThu = blockThu;
	}

	public String getBlockFri() {
		return blockFri;
	}

	public void setBlockFri(String blockFri) {
		this.blockFri = blockFri;
	}

	public String getBlockSat() {
		return blockSat;
	}

	public void setBlockSat(String blockSat) {
		this.blockSat = blockSat;
	}

	public String getBlockSun() {
		return blockSun;
	}

	public void setBlockSun(String blockSun) {
		this.blockSun = blockSun;
	}

	public Integer getHightFeet() {
		return hightFeet;
	}

	public void setHightFeet(Integer hightFeet) {
		this.hightFeet = hightFeet;
	}

	public Integer getHightInches() {
		return hightInches;
	}

	public void setHightInches(Integer hightInches) {
		this.hightInches = hightInches;
	}

	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	public List<DestinationSetting> getDestinationSettings() {
		return destinationSettings;
	}

	public void setDestinationSettings(List<DestinationSetting> destinationSettings) {
		this.destinationSettings = destinationSettings;
	}

	public void setActiveDays(List<DayOfWeek> activeDays) {
		this.activeDays = activeDays;
	}
	

}
