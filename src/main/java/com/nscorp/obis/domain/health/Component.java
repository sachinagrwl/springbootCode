package com.nscorp.obis.domain.health;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@XmlRootElement(name = "component")
@XmlType(propOrder = {"name", "status","information"})
@Data
public class Component {

    private String name;
    private int status;
    private Information information;
}
