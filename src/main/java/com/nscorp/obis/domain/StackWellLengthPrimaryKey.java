package com.nscorp.obis.domain;

import javax.persistence.Column;
import java.io.Serializable;
@SuppressWarnings("serial")
public class StackWellLengthPrimaryKey implements Serializable {

    @Column(name = "UMLER_ID", columnDefinition = "double", nullable = false)
    private Long umlerId;

    @Column(name = "AAR_1ST_NR", columnDefinition = "char(1)", nullable = false)
    private String aar1stNr;

}