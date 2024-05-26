package com.nscorp.obis.services;

import java.util.Map;

import com.nscorp.obis.domain.PayGuarantee;

public interface PayGuaranteeService {

	PayGuarantee getPayGuarantee(Long chrgId);

	PayGuarantee addPayGuarantee(PayGuarantee payGuarantee, Map<String, String> headers);

	PayGuarantee updatePayGuarantee(PayGuarantee payGuarantee, Map<String, String> headers);


}
