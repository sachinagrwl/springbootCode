package com.nscorp.obis.domain;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

@SuppressWarnings("serial")
public class DamageAreaComponentPrimaryKeys implements Serializable {

    @Column(name = "AREA_CD", columnDefinition = "char(1)", nullable = true)
    private String areaCd;

    @Column(name = "JOB_CODE" , length = 4, columnDefinition = "SMALLINT", nullable = false)
    private Integer jobCode;

    public String getAreaCd() {
        return areaCd;
    }

    public void setAreaCd(String areaCd) {
        this.areaCd = areaCd;
    }

    public Integer getJobCode() {
        return jobCode;
    }

    public void setJobCode(Integer jobCode) {
        this.jobCode = jobCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DamageAreaComponentPrimaryKeys that = (DamageAreaComponentPrimaryKeys) o;
        return Objects.equals(areaCd, that.areaCd) && Objects.equals(jobCode, that.jobCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(areaCd, jobCode);
    }

    @Override
    public String toString() {
        return "DamageAreaComponentPrimaryKeys{" +
                "areaCd='" + areaCd + '\'' +
                ", jobCode=" + jobCode +
                '}';
    }
}