package com.nscorp.obis.services;

import com.nscorp.obis.domain.ContainerChassisAssociation;

import java.util.List;
import java.util.Map;

public interface ContainerChassisAssociationService {


    List<ContainerChassisAssociation> getAllControllerChassisAssociations();

    ContainerChassisAssociation addContainerChassisAssociation(ContainerChassisAssociation association, Map<String, String> headers);
    
    ContainerChassisAssociation updateContainerChassisAssociation(ContainerChassisAssociation association,Map<String, String> headers);

	ContainerChassisAssociation expireContainerChassisAssociation(ContainerChassisAssociation association,Map<String, String> headers);
}
