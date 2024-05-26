package com.nscorp.obis.domain;

import javax.persistence.*;

@Entity
@Table(name = "GENERIC_CD_LIST")
@IdClass(GenericCodeUpdatePrimaryKeys.class)
public class GenericCodeUpdate extends AuditInfo{
	
	@Id
	@Column(name = "GEN_TBL", columnDefinition = "char(8)", nullable = false)
	private String genericTable;

	@Id
	@Column(name = "GEN_TBL_CD" ,columnDefinition = "char(8)", nullable = false)
	private String genericTableCode;
	
	@Column(name = "GEN_SHORT_DSC", columnDefinition = "char(11)", nullable = true)
	private String genericShortDescription;
	
	@Column(name = "GEN_LONG_DSC", columnDefinition = "char(45)", nullable = true)
	private String genericLongDescription;
	
	@Column(name = "ADD_SHORT_DSC", columnDefinition = "char(10)", nullable = true)
	private String addShortDescription;
	
	@Column(name = "ADD_LONG_DSC", columnDefinition = "char(45)", nullable = true)
	private String addLongDescription;
	
	@Column(name = "GEN_FLAG", columnDefinition = "char(1)", nullable = true)
	private String genericFlag;
	
	//@ManyToOne
	//private CodeTableSelection codeTableSelection;

	public GenericCodeUpdate(String genericTable, String genericTableCode, String genericShortDescription,
			String genericLongDescription, String addShortDescription, String addLongDescription, String genericFlag
			 /*CodeTableSelection codeTableSelection*/) {
		super();
		this.genericTable = genericTable;
		this.genericTableCode = genericTableCode;
		this.genericShortDescription = genericShortDescription;
		this.genericLongDescription = genericLongDescription;
		this.addShortDescription = addShortDescription;
		this.addLongDescription = addLongDescription;
		this.genericFlag = genericFlag;
		//this.codeTableSelection = codeTableSelection;
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
		if(genericTable != null) {
			this.genericTable = genericTable.toUpperCase();
		}
		else
			this.genericTable = genericTable;
	}

	public String getGenericTableCode() {
		if(genericTableCode != null) {
			return genericTableCode.trim();
		}
		else {
			return genericTableCode;
		}
	}

	public void setGenericTableCode(String genericTableCode) {
		if(genericTableCode != null) {
			this.genericTableCode = genericTableCode.toUpperCase();
		}
		else
			this.genericTableCode = genericTableCode;

	}

	public String getGenericShortDescription() {
		if(genericShortDescription != null) {
			return genericShortDescription.trim();
		}
		else {
			return genericShortDescription;
		}
	}

	public void setGenericShortDescription(String genericShortDescription) {

		if(genericShortDescription != null) {
			this.genericShortDescription = genericShortDescription.toUpperCase();
		}
		else
			this.genericShortDescription = genericShortDescription;

	}

	public String getGenericLongDescription() {
		if(genericLongDescription != null) {
			return genericLongDescription.trim();
		}
		else {
			return genericLongDescription;
		}
	}

	public void setGenericLongDescription(String genericLongDescription) {
		if(genericLongDescription != null) {
			this.genericLongDescription = genericLongDescription.toUpperCase();
		}
		else {
			this.genericLongDescription = genericLongDescription;
		}
	}

	public String getAddShortDescription() {
		if(addShortDescription != null) {
			return addShortDescription.trim();
		}
		else {
			return addShortDescription;
		}
	}

	public void setAddShortDescription(String addShortDescription) {
		if(addShortDescription != null) {
			this.addShortDescription = addShortDescription.toUpperCase();
		}
		else {
			this.addShortDescription = addShortDescription;
		}
	}

	public String getAddLongDescription() {
		if(addLongDescription != null) {
			return addLongDescription.trim();
		}
		else {
			return addLongDescription;
		}
	}

	public void setAddLongDescription(String addLongDescription) {
		if(addLongDescription != null) {
			this.addLongDescription = addLongDescription.toUpperCase();
		}
		else {
			this.addLongDescription = addLongDescription;
		}
	}

	public String getGenericFlag() {
		if(genericFlag != null) {
			return genericFlag.trim();
		}
		else {
			return genericFlag;
		}
	}

	public void setGenericFlag(String genericFlag) {
		if(genericFlag != null) {
			this.genericFlag = genericFlag.toUpperCase();
		}
		else {
			this.genericFlag = genericFlag;
		}
	}

	/*public CodeTableSelection getCodeTableSelection() {
		return codeTableSelection;
	}

	public void setCodeTableSelection(CodeTableSelection codeTableSelection) {
		this.codeTableSelection = codeTableSelection;
	}*/
	
	public GenericCodeUpdate() {
	    super();
		
	}
	
} 

