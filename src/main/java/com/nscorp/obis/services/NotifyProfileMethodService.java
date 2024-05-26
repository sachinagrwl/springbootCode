package com.nscorp.obis.services;

import com.nscorp.obis.domain.NotifyProfileMethod;
import com.nscorp.obis.dto.NotifyProfileMethodDTO;

import javax.validation.Valid;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface NotifyProfileMethodService {
	
	List<NotifyProfileMethod> fetchNotifyProfileMethod(@Valid String notificationName,String notificationMethod,
			String notificationType,String editBox,Integer notifyAreaCode,Integer phonePrefix,Integer phoneSuffix,
			Integer phoneExtension,String notificationOrder,Character microwaveInd,Integer pageNo,Integer pageSize) throws SQLException;

	
	NotifyProfileMethod updateNotifyProfileMethod(@Valid NotifyProfileMethodDTO notifyProfileMethodDTO, Map<String,String> headers);

	NotifyProfileMethod insertNotifyProfileMethod(NotifyProfileMethod notifyProfileMethod,
			NotifyProfileMethodDTO notifyProfileMethodDTO, Map<String, String> headers);
}
