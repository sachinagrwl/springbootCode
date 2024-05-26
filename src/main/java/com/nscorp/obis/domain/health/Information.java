package com.nscorp.obis.domain.health;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@XmlRootElement(name = "information")
@XmlType(propOrder = {"url", "message"})
@Data
public class Information {

    private String url;
    private String message;
}
