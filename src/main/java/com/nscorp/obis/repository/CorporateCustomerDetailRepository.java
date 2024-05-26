package com.nscorp.obis.repository;

import java.util.List;

import javax.validation.Valid;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.nscorp.obis.domain.CorporateCustomerDetail;
import com.nscorp.obis.domain.CorporateCustomerDetailComposite;


public interface CorporateCustomerDetailRepository
		extends JpaRepository<CorporateCustomerDetail, CorporateCustomerDetailComposite> {

	List<CorporateCustomerDetail> findByCorpCustId(@Valid Long corpCustId);

	boolean existsByCorpCustId(@Valid Long corpCustId);

	boolean existsByCorpCust6(@Valid String corpCust6);

	CorporateCustomerDetail findByCorpCust6(@Valid String corpCust6);

	CorporateCustomerDetail findByCorpCustIdAndCorpCust6(Long corpCustId, String corpCust6);

//	@Transactional
//	@Procedure(procedureName = "INTERMODAL.CORP_CUST_DTL_UI", outputParameterName = "V_OUTPUT")
//	short inOnlyTest(@Param("CUST_ID") Long inParam1, @Param("CORP_PRIMARY_6") String string);

	void deleteByCorpCustIdAndCorpCust6(Long corpCustId, String corpCust6);

}
