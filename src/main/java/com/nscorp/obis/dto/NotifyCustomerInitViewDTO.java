package com.nscorp.obis.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class NotifyCustomerInitViewDTO {

    private Long customerId;

    private String equipmentInit;

    private String customerNo;

    private String customerName;

    private Timestamp updateDateTime;
}
