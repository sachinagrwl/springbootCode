package com.nscorp.obis.services;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import com.nscorp.obis.domain.BeneficialOwnerDetail;
import com.nscorp.obis.dto.BeneficialOwnerDetailDTO;

public interface BeneficialOwnerDetailService {
    public List<BeneficialOwnerDetail> fetchBeneficialOwnerDetails(@Valid Long bnfCustId, @Valid String bnfOwnerNumber) throws SQLException;

    BeneficialOwnerDetail deleteBeneficialDetails(BeneficialOwnerDetail bnfOwnerDetails, Map<String, String> headers);
    
    BeneficialOwnerDetailDTO addBeneficialOwnerDetail(BeneficialOwnerDetailDTO beneficialOwnerDetail, Map<String, String> headers);
}
