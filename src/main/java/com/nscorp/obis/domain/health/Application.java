package com.nscorp.obis.domain.health;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@XmlRootElement(name = "application")
@XmlType(propOrder = {"name", "components"})
public class Application implements Serializable {

    private String name;
    private Components components;
}
