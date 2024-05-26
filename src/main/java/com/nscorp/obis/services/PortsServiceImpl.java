package com.nscorp.obis.services;

import com.nscorp.obis.common.CommonConstants;
import com.nscorp.obis.common.UserId;
import com.nscorp.obis.domain.Ports;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordNotAddedException;
import com.nscorp.obis.repository.PortsRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
@Service
@Transactional
public class PortsServiceImpl implements PortsService{

    @Autowired
    PortsRepository portsRepository;

    public void portsValidations(Ports ports, Map<String, String> headers){
        if (headers.get(CommonConstants.EXTENSION_SCHEMA) == null) {
            throw new NullPointerException("Extension Schema should not be null, empty or blank.");
        }
    }

    @Override
    public List<Ports> getAllPorts() {
        List<Ports> portsList = portsRepository.findAll();
        if (portsList.isEmpty()) {
            throw new NoRecordsFoundException("No Record Found under this search!");
        }
        return portsList;
    }

    @Override
    public Ports addPorts(Ports ports, Map<String, String> headers) {
        UserId.headerUserID(headers);
        String userId = headers.get(CommonConstants.USER_ID);
        portsValidations(ports, headers);
        Long generatedPortId = portsRepository.SGKLong();
        ports.setPortId(generatedPortId);
        ports.setCreateUserId(userId.toUpperCase());
        ports.setUpdateUserId(userId.toUpperCase());
        ports.setUpdateExtensionSchema(headers.get(CommonConstants.EXTENSION_SCHEMA));
        ports.setUversion("!");
        return portsRepository.save(ports);
    }

    @Override
    public Ports updatePorts(Ports ports, Map<String, String> headers) {
        UserId.headerUserID(headers);
        portsValidations(ports, headers);
        String userId = headers.get(CommonConstants.USER_ID);
        if(portsRepository.existsByPortIdAndUversion(ports.getPortId(), ports.getUversion())) {
            Ports existingPort = portsRepository.findByPortId(ports.getPortId());
            if(existingPort.getExpiredDate() != null) {
                throw new RecordNotAddedException("Record with Port Id:"+ existingPort.getPortId()
                        + " and Uversion:" + existingPort.getUversion()
                + " has been marked for deletion, no updates allowed unless record is restored.");
            }
            existingPort.setPortCode(ports.getPortCode());
            existingPort.setPortName(ports.getPortName());
            existingPort.setPortCity(ports.getPortCity());
            existingPort.setPortCityGoodSpell(ports.getPortCityGoodSpell());
            existingPort.setPortStateOrProvince(ports.getPortStateOrProvince());
            existingPort.setPortCountry(ports.getPortCountry());
            existingPort.setUpdateUserId(userId.toUpperCase());
            existingPort.setUpdateExtensionSchema(headers.get(CommonConstants.EXTENSION_SCHEMA));
            /* Audit fields */
            if(StringUtils.isNotEmpty(existingPort.getUversion())) {
                existingPort.setUversion(
                        Character.toString((char) ((((int)existingPort.getUversion().charAt(0) - 32) % 94) + 33)));
            }
            portsRepository.save(existingPort);
            return existingPort;
        }
        else
            throw new NoRecordsFoundException("No record Found Under this Port Id:"+ports.getPortId()
                    + " and Uversion:" + ports.getUversion());
    }

    @Override
    public Ports deletePorts(Ports ports, Map<String, String> headers) {
        UserId.headerUserID(headers);
        portsValidations(ports, headers);
        String userId = headers.get(CommonConstants.USER_ID);
        if(portsRepository.existsByPortIdAndUversion(ports.getPortId(), ports.getUversion())) {
            Ports existingPort = portsRepository.findByPortId(ports.getPortId());
            if(ports.getExpiredDate() != null){
                if(existingPort.getExpiredDate() !=null){
                    throw new RecordNotAddedException("Record with Port Id:"+ existingPort.getPortId()
                        + " and Uversion:" + existingPort.getUversion()
                        + " is already expired.");
                }
                Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                existingPort.setExpiredDate(timestamp);
            }
            else {
                if (existingPort.getExpiredDate() == null) {
                    throw new RecordNotAddedException("Record with Port Id:" + existingPort.getPortId()
                            + " and Uversion:" + existingPort.getUversion()
                            + " is not currently marked for deletion.");
                }
                existingPort.setExpiredDate(null);
            }
            existingPort.setUpdateUserId(userId.toUpperCase());
            existingPort.setUpdateExtensionSchema(headers.get(CommonConstants.EXTENSION_SCHEMA));
            /* Audit fields */
            if(StringUtils.isNotEmpty(existingPort.getUversion())) {
                existingPort.setUversion(
                        Character.toString((char) ((((int)existingPort.getUversion().charAt(0) - 32) % 94) + 33)));
            }
            portsRepository.save(existingPort);
            return existingPort;
        }
        else {
            throw new NoRecordsFoundException("No record Found Under this Port Id:"+ports.getPortId()
                    + " and Uversion:" + ports.getUversion());
        }
    }
}
