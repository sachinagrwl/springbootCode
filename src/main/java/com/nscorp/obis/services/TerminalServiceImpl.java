
package com.nscorp.obis.services;

import java.util.Map;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nscorp.obis.common.CommonConstants;
import com.nscorp.obis.common.UserId;
import com.nscorp.obis.domain.Station;
import com.nscorp.obis.domain.Terminal;
import com.nscorp.obis.domain.TerminalInd;
import com.nscorp.obis.exception.InvalidDataException;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.repository.StationRepository;
import com.nscorp.obis.repository.TerminalRepository;

@Service

@Transactional
public class TerminalServiceImpl implements TerminalService {

	@Autowired(required = true)
	TerminalRepository terminalRepo;

	@Autowired
	StationRepository stationRepo;

	@Override
	public Terminal updateTerminal(Terminal terminalObj, Map<String, String> headers) {

		UserId.headerUserID(headers);
		Terminal termName = terminalRepo.findByTerminalId(terminalObj.getTerminalId());
		if(termName.getTerminalName().equalsIgnoreCase(terminalObj.getTerminalName())){
			terminalObj.setTerminalName(terminalObj.getTerminalName());
		}
		else if (terminalObj.getTerminalName() != null && terminalRepo.existsByTerminalName(terminalObj.getTerminalName())) {
			throw new RecordAlreadyExistsException("Terminal Name already exists:" + terminalObj.getTerminalName());
		}
		else
			terminalObj.setTerminalName(terminalObj.getTerminalName());


		TerminalInd terminalIndObj = new TerminalInd();
		if (terminalRepo.existsByTerminalId(terminalObj.getTerminalId())) {
			Station station = stationRepo.findByTermId(terminalObj.getStnXrfId());


			terminalObj.setUpdateUserId(headers.get(CommonConstants.USER_ID));
			terminalObj.setCreateUserId(headers.get(CommonConstants.USER_ID));
			if (StringUtils.isNotEmpty(terminalObj.getUversion())) {
				terminalObj.setUversion(
						Character.toString((char) ((((int) terminalObj.getUversion().charAt(0) - 32) % 94) + 33)));
			}

			terminalObj.setUpdateExtensionSchema(headers.get(CommonConstants.EXTENSION_SCHEMA));
			terminalObj.setNsTerminalId(terminalObj.getNsTerminalId());
			terminalObj.setTerminalCountry(terminalObj.getTerminalCountry());
			terminalObj.setTerminalZnOffset(terminalObj.getTerminalZnOffset());
			terminalObj.setDayLightSaveIndicator(terminalObj.getDayLightSaveIndicator());


			if(termName.getStnXrfId()!=null && terminalObj.getStnXrfId()!=null) {
				if (termName.getStnXrfId().equals(terminalObj.getStnXrfId())) {
					terminalObj.setStnXrfId(terminalObj.getStnXrfId());

				} else if (terminalRepo.existsByStnXrfId(terminalObj.getStnXrfId())) {
					throw new RecordAlreadyExistsException("Duplicate Station for Terminal");

				} else
					terminalObj.setStnXrfId(terminalObj.getStnXrfId());
			}
			else if (termName.getStnXrfId()==null && terminalObj.getStnXrfId()!=null){
				if(terminalRepo.existsByStnXrfId(terminalObj.getStnXrfId())){
					throw new RecordAlreadyExistsException("Duplicate Station for Terminal");
				}
				else
					terminalObj.setStnXrfId(terminalObj.getStnXrfId());
			}

			terminalObj.setExpiredDate(terminalObj.getExpiredDate());
			terminalObj.setTerminalType(terminalObj.getTerminalType());
			terminalObj.setHitchCheckIndicator(terminalObj.getHitchCheckIndicator());
			terminalObj.setHaulageIndicator(terminalObj.getHaulageIndicator());
			terminalObj.setIdcsTerminalIndicator(terminalObj.getIdcsTerminalIndicator());
			terminalObj.setSswTerminalIndicator(terminalObj.getSswTerminalIndicator());

			terminalObj.setTerminalAddress1(terminalObj.getTerminalAddress1());
			terminalObj.setTerminalAddress2(terminalObj.getTerminalAddress2());
			terminalObj.setTerminalCity1(terminalObj.getTerminalCity1());
			terminalObj.setTerminalCity2(terminalObj.getTerminalCity2());
			terminalObj.setTerminalZipCode1(terminalObj.getTerminalZipCode1());
			terminalObj.setTerminalZipCode2(terminalObj.getTerminalZipCode2());
			terminalObj.setTerminalState1(terminalObj.getTerminalState1());
			terminalObj.setTerminalState2(terminalObj.getTerminalState2());

			terminalIndObj.setTerminalId(terminalObj.getTerminalId());
			terminalIndObj.setAgsIndicator(terminalObj.getTerminalInd().getAgsIndicator());
			terminalIndObj.setPrivateInd(terminalObj.getTerminalInd().getPrivateInd());
			terminalIndObj.setLastMovNSNotOK(terminalObj.getTerminalInd().getLastMovNSNotOK());
			terminalObj.setTerminalInd(terminalIndObj);

			terminalObj.setExternalAreaCd1(terminalObj.getExternalAreaCd1());
			terminalObj.setExternalAreaCd2(terminalObj.getExternalAreaCd2());
			terminalObj.setExternalAreaCd3(terminalObj.getExternalAreaCd3());
			terminalObj.setExternalExchange1(terminalObj.getExternalExchange1());
			terminalObj.setExternalExchange2(terminalObj.getExternalExchange2());
			terminalObj.setExternalExchange3(terminalObj.getExternalExchange3());
			terminalObj.setExternalExtension1(terminalObj.getExternalExtension1());
			terminalObj.setExternalExtension2(terminalObj.getExternalExtension2());
			terminalObj.setExternalExtension3(terminalObj.getExternalExtension3());

			terminalObj.setInternalAreaCd1(terminalObj.getInternalAreaCd1());
			terminalObj.setInternalAreaCd2(terminalObj.getInternalAreaCd2());
			terminalObj.setInternalAreaCd3(terminalObj.getInternalAreaCd3());
			terminalObj.setInternalExchange1(terminalObj.getInternalExchange1());
			terminalObj.setInternalExchange2(terminalObj.getInternalExchange2());
			terminalObj.setInternalExchange3(terminalObj.getInternalExchange3());
			terminalObj.setInternalExtension1(terminalObj.getInternalExtension1());
			terminalObj.setInternalExtension2(terminalObj.getInternalExtension2());
			terminalObj.setInternalExtension3(terminalObj.getInternalExtension3());

			terminalObj.setExternalFaxArea1(terminalObj.getExternalFaxArea1());
			terminalObj.setExternalFaxArea2(terminalObj.getExternalFaxArea2());
			terminalObj.setExternalFaxArea3(terminalObj.getExternalFaxArea3());
			terminalObj.setExternalFaxExchange1(terminalObj.getExternalFaxExchange1());
			terminalObj.setExternalFaxExchange2(terminalObj.getExternalFaxExchange2());
			terminalObj.setExternalFaxExchange3(terminalObj.getExternalFaxExchange3());
			terminalObj.setExternalFaxExtension1(terminalObj.getExternalFaxExtension1());
			terminalObj.setExternalFaxExtension2(terminalObj.getExternalFaxExtension2());
			terminalObj.setExternalFaxExtension3(terminalObj.getExternalFaxExtension3());

			terminalObj.setInternalFaxArea1(terminalObj.getInternalFaxArea1());
			terminalObj.setInternalFaxArea2(terminalObj.getInternalFaxArea2());
			terminalObj.setInternalFaxArea3(terminalObj.getInternalFaxArea3());
			terminalObj.setInternalFaxExchange1(terminalObj.getInternalFaxExchange1());
			terminalObj.setInternalFaxExchange2(terminalObj.getInternalFaxExchange2());
			terminalObj.setInternalFaxExchange3(terminalObj.getInternalFaxExchange3());
			terminalObj.setInternalFaxExtension1(terminalObj.getInternalFaxExtension1());
			terminalObj.setInternalFaxExtension2(terminalObj.getInternalFaxExtension2());
			terminalObj.setInternalFaxExtension3(terminalObj.getInternalFaxExtension3());

			if ((terminalObj.getDeferredTime()) == null)
				terminalObj.setDeferredTime(null);
			else {
				if (Integer.parseInt(terminalObj.getDeferredTime()) < 24)
					terminalObj.setDeferredTime(terminalObj.getDeferredTime());
				else
					throw new InvalidDataException("Deferred Time can't be more than 23 ");

			}
			if (terminalObj.getRenotifyTime() == null)
				terminalObj.setRenotifyTime(null);
			else {
				if (Integer.parseInt(terminalObj.getRenotifyTime()) < 24)
					terminalObj.setRenotifyTime(terminalObj.getRenotifyTime());
				else
					throw new InvalidDataException("Renotify Time can't be more than 23 ");

			}
			terminalObj.setExpiredDate(terminalObj.getExpiredDate());

			terminalObj.setTerminalCloseOutTime(terminalObj.getTerminalCloseOutTime());

			terminalObj.setStation(station);
			terminalRepo.saveAndFlush(terminalObj);

			return terminalObj;
		} else {
			throw new NoRecordsFoundException("Record with TerminalId " + terminalObj.getTerminalId() + " Not Found!");

		}
	}

	@Override
	public Terminal insertTerminal(@Valid Terminal terminalObj, Map<String, String> headers) {

		UserId.headerUserID(headers);

		// UserId.headerUserID(headers);
		if (terminalObj.getTerminalId() != null && terminalRepo.existsByTerminalId(terminalObj.getTerminalId())) {
			throw new RecordAlreadyExistsException("Terminal Id already exists:" + terminalObj.getTerminalId());
		}
		if (terminalObj.getTerminalName() != null && terminalRepo.existsByTerminalName(terminalObj.getTerminalName())) {
			throw new RecordAlreadyExistsException("Terminal Name already exists:" + terminalObj.getTerminalName());
		}

		terminalObj.setUpdateUserId(headers.get(CommonConstants.USER_ID));
		terminalObj.setCreateUserId(headers.get(CommonConstants.USER_ID));
		if (StringUtils.isNotEmpty(terminalObj.getUversion())) {
			terminalObj.setUversion(
					Character.toString((char) ((((int) terminalObj.getUversion().charAt(0) - 32) % 94) + 33)));
		}
		terminalObj.setUpdateExtensionSchema(headers.get(CommonConstants.EXTENSION_SCHEMA));

		Long terminalId = terminalRepo.SGK();
		terminalObj.setTerminalId(terminalId);

		TerminalInd terminalInd = new TerminalInd();
		terminalInd.setTerminalId(terminalId);
		terminalInd.setAgsIndicator(terminalObj.getTerminalInd().getAgsIndicator());
		terminalInd.setPrivateInd(terminalObj.getTerminalInd().getPrivateInd());
		terminalInd.setLastMovNSNotOK(terminalObj.getTerminalInd().getLastMovNSNotOK());
		terminalObj.setTerminalInd(terminalInd);
		if(terminalObj.getStnXrfId()!=null && terminalRepo.existsByStnXrfId(terminalObj.getStnXrfId())){
			throw new RecordAlreadyExistsException("Duplicate Station for Terminal");
		}else {
			terminalObj.setStnXrfId(terminalObj.getStnXrfId());
		}
		Station station = stationRepo.findByTermId(terminalObj.getStnXrfId());
		terminalObj.setStation(station);

		if (terminalObj.getDeferredTime() == null)
			terminalObj.setDeferredTime(null);
		else {
			if (Integer.parseInt(terminalObj.getDeferredTime()) < 24)
				terminalObj.setDeferredTime(terminalObj.getDeferredTime());
			else
				throw new InvalidDataException("Deferred Time can't be more than 23 ");

		}
		if (terminalObj.getRenotifyTime() == null)
			terminalObj.setRenotifyTime(null);
		else {
			if (Integer.parseInt(terminalObj.getRenotifyTime()) < 24)
				terminalObj.setRenotifyTime(terminalObj.getRenotifyTime());
			else
				throw new InvalidDataException("Renotify Time can't be more than 23 ");

		}

		terminalRepo.save(terminalObj);

		return terminalObj;

	}

	public Terminal getTerminal(Long termId) {

		Terminal terminal = terminalRepo.findByTerminalId(termId);
		if (terminal == null) {
			throw new NoRecordsFoundException("No Records Found");
		}
		return terminal;

	}

}
