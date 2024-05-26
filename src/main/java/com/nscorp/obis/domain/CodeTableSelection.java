package com.nscorp.obis.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "GENERIC_TBL_LIST")
public class CodeTableSelection extends AuditInfo {
	
	@Id
	@Column(name = "GEN_TBL", columnDefinition = "char(8)", nullable = false)
	private String genericTable;
	
	@Column(name = "GEN_TBL_DESC", columnDefinition = "char(20)", nullable = true)
	private String genericTableDesc;
	
	@Column(name = "GEN_OWNR_GRP", columnDefinition = "char(8)", nullable = true)
	private String genericOwnerGroup;
	 
	@Column(name = "GEN_CD_FLD_SIZE", columnDefinition = "Smallint", nullable = true)
	private short genCdFldSize;

	@Column(name = "RESOURCE_NM", columnDefinition = "char(16)", nullable = true)
	private String resourceNm;
	
	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "GEN_TBL")
	private List<GenericCodeUpdate> genericCodeUpdate;

	public CodeTableSelection(@NotNull(message = "Table Name cannot be Null") String genericTable,
			String genericTableDesc, String genericOwnerGroup, short genCdFldSize, String resourceNm) {
		super();
		this.genericTable = genericTable;
		this.genericTableDesc = genericTableDesc;
		this.genericOwnerGroup = genericOwnerGroup;
		this.genCdFldSize = genCdFldSize;
		this.resourceNm = resourceNm;
	}
	
	public String getGenericTable() {
		if(genericTable != null) {
			return genericTable.trim();
		}
		else {
			return genericTable;
		}
	}

	public void setGenericTable(String genericTable) {
		this.genericTable = genericTable;
	}

	public String getGenericTableDesc() {
		if(genericTableDesc != null) {
			return genericTableDesc.trim();
		}
		else {
			return genericTableDesc;
		}
	}

	public void setGenericTableDesc(String genericTableDesc) {
		this.genericTableDesc = genericTableDesc;
	}

	public String getGenericOwnerGroup() {
		if(genericOwnerGroup != null) {
			return genericOwnerGroup.trim();
		}
		else {
			return genericOwnerGroup;
		}
	}

	public void setGenericOwnerGroup(String genericOwnerGroup) {
		this.genericOwnerGroup = genericOwnerGroup;
	}

	public short getGenCdFldSize() {
		return genCdFldSize;
	}

	public void setGenCdFldSize(short genCdFldSize) {
		this.genCdFldSize = genCdFldSize;
	}

	public String getResourceNm() {
		if(resourceNm != null) {
			return resourceNm.trim();
		}
		else {
			return resourceNm;
		}
	}

	public void setResourceNm(String resourceNm) {
		this.resourceNm = resourceNm;
	}

	public CodeTableSelection() {
		super();
		// TODO Auto-generated constructor stub
	}

}
