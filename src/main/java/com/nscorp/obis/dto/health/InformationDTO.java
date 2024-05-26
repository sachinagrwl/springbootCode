package com.nscorp.obis.dto.health;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@XmlRootElement(name = "information")
@XmlType(propOrder = {"url", "message", "queue"})
@Data
public class InformationDTO {

    private String url;
    private String message;
}
