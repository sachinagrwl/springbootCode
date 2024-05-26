package com.nscorp.obis.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

@SuppressWarnings("serial")
public class TermFreeDayPrimaryKeys implements Serializable {

    private Long termId;

    private LocalDate closeDate;

    private LocalTime closeFromTime;

    public TermFreeDayPrimaryKeys(Long termId, LocalDate closeDate, LocalTime closeFromTime) {
        super();
        this.termId = termId;
        this.closeDate = closeDate;
        this.closeFromTime = closeFromTime;
    }

    public TermFreeDayPrimaryKeys() {
        super();
    }

    @Override
    public int hashCode() {
        return Objects.hash(termId,closeDate,closeFromTime);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        TermFreeDayPrimaryKeys termFreeDayPrimaryKeys = (TermFreeDayPrimaryKeys) obj;
        return Objects.equals(termId, termFreeDayPrimaryKeys.termId)
                && Objects.equals(closeDate, termFreeDayPrimaryKeys.closeDate)
                && Objects.equals(closeFromTime, termFreeDayPrimaryKeys.closeFromTime);
    }

}
