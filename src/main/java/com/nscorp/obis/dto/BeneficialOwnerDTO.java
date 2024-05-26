package com.nscorp.obis.dto;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.nscorp.obis.common.NullOrNotBlank;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class BeneficialOwnerDTO extends AuditInfoDTO {
    
//    @NotNull
//    @Digits(integer=15, fraction=0, message="Beneficial Customer should not have more than 15 digits.")
//	@Min(value = 1, message = "Beneficial Customer value must be greater than 0")
	@Schema(required = true,description="Beneficial Customer", example="978284241407")
    private Long bnfCustomerId;

    @Size(min=0, max=30, message = "Beneficial Long Name should not have more than 30 characters.")
    @Schema(required = false,description="Beneficial Long Name", example="Longname")
    @NotBlank(message = "Beneficial long name should not be empty or null")
	private String bnfLongName;
    
    @NullOrNotBlank(min=1, max=10, message = "Beneficial Short Name length should be between 1 and 10.")
    @Schema(required = false,description="Beneficial Short Name", example="Shortname")	
	private String bnfShortName;

    @Digits(integer=15, fraction=0, message="Customer Id should not have more than 15 digits.")
	@Min(value = 1, message = "Customer Id value must be greater than 0")
	@Schema(required = true,description="Customer Id", example="978284241407")
    @NotNull(message = "Customer shouldn't be null")
    private Long customerId;

    @NullOrNotBlank(min=1, max=30, message = "Category length should be between 1 and 30.")
    @Schema(required = false,description="Category", example="category")	
	private String category;

    @NullOrNotBlank(min=1, max=30, message = "Sub Category length should be between 1 and 30.")
    @Schema(required = false,description="Sub Category", example="sub category")	
	private String subCategory;
	
    @NullOrNotBlank(min=1, max=30, message = "Account Manager length should be between 1 and 30.")
    @Schema(required = false,description="Account Manager", example="account manager")	
	private String accountManager;
    
    @Valid
//    @Size(min =1,message="beneficial owner must have at least one Primary Six")
    @Schema(required = false, description="customer primary sixes associated with the beneficial owner")	
	List<BeneficialOwnerDetailDTO> beneficialOwnerDetails;

	public Long getBnfCustomerId() {
		return bnfCustomerId;
	}

	public void setBnfCustomerId(Long bnfCustomerId) {
		this.bnfCustomerId = bnfCustomerId;
	}

	public String getBnfLongName() {
		return bnfLongName!=null?bnfLongName.trim().toUpperCase():bnfLongName;
	}

	public void setBnfLongName(String bnfLongName) {
		this.bnfLongName = bnfLongName;
	}

	public String getBnfShortName() {
		return bnfShortName!=null?bnfShortName.trim().toUpperCase():bnfShortName;
	}

	public void setBnfShortName(String bnfShortName) {
		this.bnfShortName = bnfShortName;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public String getCategory() {
		return category!=null?category.trim().toUpperCase():category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getSubCategory() {
		return subCategory!=null?subCategory.trim().toUpperCase():subCategory;
	}

	public void setSubCategory(String subCategory) {
		this.subCategory = subCategory;
	}

	public String getAccountManager() {
		return accountManager!=null?accountManager.trim().toUpperCase():accountManager;
	}

	public void setAccountManager(String accountManager) {
		this.accountManager = accountManager;
	}

	public List<BeneficialOwnerDetailDTO> getBeneficialOwnerDetails() {
		return beneficialOwnerDetails;
	}

	public void setBeneficialOwnerDetails(List<BeneficialOwnerDetailDTO> beneficialOwnerDetails) {
		this.beneficialOwnerDetails = beneficialOwnerDetails;
	}

	@Override
	public String toString() {
		return "BeneficialOwnerDTO [bnfCustomerId=" + bnfCustomerId + ", bnfLongName=" + bnfLongName + ", bnfShortName="
				+ bnfShortName + ", customerId=" + customerId + ", category=" + category + ", subCategory="
				+ subCategory + ", accountManager=" + accountManager + ", beneficialOwnerDetails="
				+ beneficialOwnerDetails + ", getUversion()=" + getUversion() + ", getCreateUserId()="
				+ getCreateUserId() + ", getCreateDateTime()=" + getCreateDateTime() + ", getUpdateUserId()="
				+ getUpdateUserId() + ", getUpdateDateTime()=" + getUpdateDateTime() + ", getUpdateExtensionSchema()="
				+ getUpdateExtensionSchema() + "]";
	}
    
}
