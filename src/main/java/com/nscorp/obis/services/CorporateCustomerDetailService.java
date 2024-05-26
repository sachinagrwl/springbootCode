package com.nscorp.obis.services;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import com.nscorp.obis.domain.CorporateCustomerDetail;

public interface CorporateCustomerDetailService {

	 List<CorporateCustomerDetail> getCorporateCustomerDetails(Long corpCustId, String corpCust6);

	// CorporateCustomerDetailDTO deleteCorpCustDetail(@Valid
	// CorporateCustomerDetailDTO customerCustomerDtlDTO);

//	List<CorporateCustomerDetailDTO> deleteCorpCustDetail(CorporateCustomerDetailDTO corporateCustomerDetailDTO,
//			Map<String, String> headers);

	 CorporateCustomerDetail addPrimary6(@Valid CorporateCustomerDetail corporateCustomerDetail,
			Map<String, String> headers);

	public CorporateCustomerDetail deleteCorpCustDetail(CorporateCustomerDetail corporateCustomerDetail);


}
