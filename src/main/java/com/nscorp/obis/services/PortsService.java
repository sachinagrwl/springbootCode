package com.nscorp.obis.services;

import com.nscorp.obis.domain.Ports;

import java.util.List;
import java.util.Map;

public interface PortsService {
    List<Ports> getAllPorts();

    Ports addPorts(Ports ports, Map<String, String> headers);

    Ports updatePorts(Ports ports, Map<String, String> headers);

    Ports deletePorts(Ports ports, Map<String, String> headers);
}
