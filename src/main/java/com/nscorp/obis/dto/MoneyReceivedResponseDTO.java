package com.nscorp.obis.dto;

import com.nscorp.obis.response.data.PaginationWrapper;
import lombok.Data;

@Data
public class MoneyReceivedResponseDTO {
    PaginationWrapper moneyReceivedList;
}
