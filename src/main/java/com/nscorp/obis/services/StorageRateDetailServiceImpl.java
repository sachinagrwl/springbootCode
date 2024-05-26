package com.nscorp.obis.services;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nscorp.obis.domain.CustomerCharge;
import com.nscorp.obis.domain.StorageRates;
import com.nscorp.obis.domain.Terminal;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.repository.CustomerChargeRepository;
import com.nscorp.obis.repository.StorageRatesRepository;
import com.nscorp.obis.repository.TerminalRepository;

import java.sql.Timestamp;

@Service
public class StorageRateDetailServiceImpl implements StorageRateDetailService {

	@Autowired
	StorageRatesRepository storageRatesRepo;
	@Autowired
	CustomerChargeRepository customerChargeRepo;
	@Autowired
	TerminalRepository terminalRepo;

	CustomerCharge charge;
	StorageRates rates;
	StorageRates data = new StorageRates();

	public StorageRates getStorageRateDetail(@Valid Long chrgId) {
		charge = customerChargeRepo.findByChrgId(chrgId);
		if (charge != null) {
			rates = storageRatesRepo.findByStorageId(charge.getRateId());
			if (rates != null) {
				data = rates;
				data.setCustomerCharge(charge);
				data.setLastUpdated(rates.getUpdateDateTime().toString());
				Terminal terminal = terminalRepo.findByTerminalId(rates.getTermId());
				if (terminal != null) {
					data.setTerminal(terminal);
				} else {
					return data;
				}
			} else {
				data.setCustomerCharge(charge);
				return data;
			}
		} else {
			throw new NoRecordsFoundException("No Records Found!");
		}
		return data;
	}

}
