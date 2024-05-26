package com.nscorp.obis.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.nscorp.obis.domain.EMSIngateAllocation;
import com.nscorp.obis.domain.Station;

public interface EMSIngateAllocationService {
	Page<EMSIngateAllocation> searchIngateAllocation(Long ingateTerminalId, Station onlineOriginStation,
			Station onlineDestinationStation, Station offlineDestinationStation, Long corporateCustomerId,
			List<String> lineOfBusinesses, String wayBillRoute, List<String> trafficTypes, LocalDate st_date, LocalDate end_date, LocalDate currentDate,
													 Boolean includeInactive, Boolean includeExpired, Boolean includePermanent,Pageable pageable);

	EMSIngateAllocation insertAllocation(EMSIngateAllocation allocations, Map<String,String> headers);

	EMSIngateAllocation updateAllocation(EMSIngateAllocation allocations, Map<String,String> headers);

}
