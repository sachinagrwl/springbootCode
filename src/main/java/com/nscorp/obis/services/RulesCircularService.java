package com.nscorp.obis.services;

import com.nscorp.obis.domain.RulesCircular;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

public interface RulesCircularService {

    List<RulesCircular> getAllRulesCircular();

    RulesCircular addRulesCircular(@Valid RulesCircular rulesCircular, Map<String, String> headers);

    RulesCircular updateRulesCircular(@Valid RulesCircular rulesCircular, Map<String, String> headers);
    
    RulesCircular deleteRulesCircular(@Valid RulesCircular rulesCircular);
}
