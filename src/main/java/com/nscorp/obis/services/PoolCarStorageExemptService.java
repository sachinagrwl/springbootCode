package com.nscorp.obis.services;

import java.util.List;
import java.util.Map;

import com.nscorp.obis.domain.PoolCarStorageExempt;

public interface PoolCarStorageExemptService {

	List<PoolCarStorageExempt> getAllPoolCarStorageExempts();

	PoolCarStorageExempt addPoolCarStorageExempt(PoolCarStorageExempt poolCarStorageExempt, Map<String, String> headers);

	PoolCarStorageExempt deletePoolCarStorageExempt(PoolCarStorageExempt poolCarStorageExempt);

}
