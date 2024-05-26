package com.nscorp.obis.dto.health;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.nscorp.obis.domain.health.Components;
import lombok.Data;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@XmlRootElement(name = "application")
@XmlType(propOrder = {"name", "components"})
public class ApplicationDTO {

    private String name;
    private Components components;
}
