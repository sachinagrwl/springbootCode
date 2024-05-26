package com.nscorp.obis.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Data
@Table(name = "SEC_USER")
public class SecUser extends AuditInfo{
    @Id
    @Column(name = "ID", columnDefinition = "char(8)", nullable = false)
    private String secUserId;

    @Column(name = "PASSWORD", columnDefinition = "char(8)", nullable = false)
    private String secUserPassword;

    @Column(name = "LST_CHGD", columnDefinition = "Date(4)", nullable = false)
    private Date lastChanged;

    @Column(name = "NM", columnDefinition = "char(40)", nullable = false)
    private String secUserName;

    @Column(name = "TITLE", columnDefinition = "char(20)", nullable = false)
    private String secUserTitle;

    @Column(name = "AREA_CD", columnDefinition = "char(3)", nullable = false)
    private String areaCode;

    @Column(name = "EXCH", columnDefinition = "char(3)", nullable = false)
    private String exchange;

    @Column(name = "EXT", columnDefinition = "char(4)", nullable = false)
    private String extension;

    @Column(name = "TERM_ID", columnDefinition = "double(8)", nullable = false)
    private Long termId;

    @Column(name = "LOCKED", columnDefinition = "char(1)", nullable = false)
    private String locked;

    @Column(name = "SYB_PASSWORD", columnDefinition = "char(8)", nullable = false)
    private String sybPassword;

    @Column(name = "OLD_SYB_PASSWORD", columnDefinition = "char(8)", nullable = false)
    private String oldSybPassword;

    @Column(name = "MULTI_LOGON", columnDefinition = "char(1)", nullable = false)
    private String multiLogon;

    @Column(name = "EXPIRED_DT",columnDefinition="Date(4)", nullable = false)
    private Date expiredDate;
}
