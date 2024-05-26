package com.nscorp.obis.services;

import com.nscorp.obis.domain.AARDamage;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.repository.AARDamageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@Transactional
public class AARDamageServiceImpl implements AARDamageService {
    @Autowired
    AARDamageRepository aarDamageRepo;
    @Override
    public List<AARDamage> getAllAarDamageCodes(String aarDamage) {
        List<AARDamage> aarDamageList = aarDamageRepo.findAllByOrderByJobCodeAsc();
        if(aarDamageList.isEmpty()) {
            throw new NoRecordsFoundException("No Records found!");
        }
        return aarDamageList;
    }
}
