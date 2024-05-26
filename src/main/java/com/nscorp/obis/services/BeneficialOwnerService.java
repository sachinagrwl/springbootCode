package com.nscorp.obis.services;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import com.nscorp.obis.domain.BeneficialOwner;
import com.nscorp.obis.dto.BeneficialOwnerDTO;

public interface BeneficialOwnerService {

    BeneficialOwnerDTO updateBeneficialOwner(@Valid BeneficialOwnerDTO beneficialOwnerDTO, Map<String, String> headers);
	List<BeneficialOwner> fetchBeneficialCustomer(@Valid String bnfLongName, @Valid String bnfShortName)
			throws SQLException;

	BeneficialOwnerDTO addBeneficialCustomer(BeneficialOwnerDTO beneficialOwnerDTO, Map<String, String> headers)
			throws SQLException;

	public BeneficialOwner deleteBeneficialCustomers(BeneficialOwner beneficialOwner);

}
