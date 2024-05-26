package com.nscorp.obis.dto;

import lombok.Data;
import java.util.List;

@Data
public class NotifyQueueUpdatedDTO {
    List<NotifyQueueDTO> notifyQueueObjDto;
    boolean flag;
    String customerName;
    Long termId;
}
