package com.nscorp.obis.services;

import com.nscorp.obis.common.CommonConstants;
import com.nscorp.obis.common.UserId;
import com.nscorp.obis.domain.CustomerPerDiemRateSelection;
import com.nscorp.obis.domain.DamageArea;
import com.nscorp.obis.domain.DamageAreaComp;
import com.nscorp.obis.domain.DamageComponent;
import com.nscorp.obis.domain.DamageComponentSize;
import com.nscorp.obis.dto.CustomerPerDiemRateSelectionDTO;
import com.nscorp.obis.dto.DamageAreaComponentDTO;
import com.nscorp.obis.dto.mapper.DamageAreaComponentMapper;
import com.nscorp.obis.exception.InvalidDataException;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.exception.RecordNotDeletedException;
import com.nscorp.obis.repository.DamageAreaCompRepository;
import com.nscorp.obis.repository.DamageAreaRepository;
import com.nscorp.obis.repository.DamageComponentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.Valid;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
public class DamageAreaComponentServiceImpl implements DamageAreaComponentService {
    @Autowired
    DamageAreaCompRepository repository;
    @Autowired
    DamageAreaRepository areaRepository;
    @Autowired
    DamageComponentRepository componentRepository;
    @Autowired
    DamageAreaComponentMapper damageAreaComponentMapper;

    @Override
    public List<DamageAreaComponentDTO> fetchDamageComponentSize(Integer jobCode, String areaCode) {
        List<DamageAreaComp> damageAreaComponentList = new ArrayList<>();
        Sort sort = Sort.by("jobCode").ascending().and(Sort.by("areaCd").ascending());
        if (jobCode != null && areaCode != null) {
            damageAreaComponentList = repository.findByJobCodeAndAreaCdIgnoreCase(jobCode, areaCode,sort);
        } else if (jobCode != null) {
            damageAreaComponentList = repository.findByJobCode(jobCode,sort);
        } else if (areaCode != null) {
            damageAreaComponentList = repository.findByAreaCdIgnoreCase(areaCode,sort);
        } else {
            damageAreaComponentList = repository.findAll(sort);
        }

        if (damageAreaComponentList.isEmpty()) {
            throw new NoRecordsFoundException("No Records Found!");
        }

        List<DamageAreaComponentDTO> damageAreaComponentDTOList = new ArrayList<>();
        damageAreaComponentDTOList = damageAreaComponentList.stream().map(DamageAreaComponentMapper.INSTANCE::DamageAreaComponentToDamageAreaComponentDTO)
                .collect(Collectors.toList());

        damageAreaComponentDTOList.forEach(areacomp -> {
            DamageComponent damageComponent = componentRepository.findByJobCode(areacomp.getJobCode());
            if(damageComponent!=null){
                areacomp.setCompDscr(damageComponent.getCompDscr());
                areacomp.setCInd(damageComponent.getCInd());
                areacomp.setTInd(damageComponent.getTInd());
                areacomp.setZInd(damageComponent.getZInd());
                areacomp.setReasonTp(damageComponent.getReasonTp());
            }
            DamageArea area = areaRepository.findByAreaCd(areacomp.getAreaCd());
            if(area!=null){
                areacomp.setAreaDscr(area.getAreaDscr());
            }

        });
        return damageAreaComponentDTOList;
    }

    @Override
    public void deleteDamageAreaComponent(DamageAreaComponentDTO damageComponentDTOObj) {
        DamageAreaComp damageAreaComp = repository.findByAreaCdAndJobCode(damageComponentDTOObj.getAreaCd(), damageComponentDTOObj.getJobCode());
        if(damageAreaComp==null) {
            throw new RecordNotDeletedException("Record Not Found!");
        }
        repository.delete(damageAreaComp);
    }

    @Override
    public DamageAreaComponentDTO addDamageAreaComponent(
            @Valid DamageAreaComponentDTO damageAreaComponentDTO, Map<String, String> headers) throws SQLException {
        UserId.headerUserID(headers);
        if (!componentRepository.existsByJobCode(damageAreaComponentDTO.getJobCode())) {
			throw new NoRecordsFoundException("No records found for given jobCode");
		}
        if (!areaRepository.existsByAreaCd(damageAreaComponentDTO.getAreaCd())) {
			throw new NoRecordsFoundException("No records found for given AreaCode");
		}
        if (repository.existsByJobCodeAndAreaCdAndOrderCode
                (damageAreaComponentDTO.getJobCode(), damageAreaComponentDTO.getAreaCd(), damageAreaComponentDTO.getOrderCode())) {
            throw new RecordAlreadyExistsException("This area/order code already exists.");
        }
        damageAreaComponentDTO.setCreateUserId(headers.get(CommonConstants.USER_ID).toUpperCase());
        damageAreaComponentDTO.setUpdateUserId(headers.get(CommonConstants.USER_ID).toUpperCase());
        damageAreaComponentDTO.setUversion("!");
        damageAreaComponentDTO.setUpdateExtensionSchema(headers.get(CommonConstants.EXTENSION_SCHEMA));
        if (damageAreaComponentDTO.getUpdateExtensionSchema() == null) {
            damageAreaComponentDTO.setUpdateExtensionSchema("IMS01242");
        }

        DamageAreaComp damageAreaComponent = damageAreaComponentMapper.DamageAreaComponentDTOToDamageAreaComponent(damageAreaComponentDTO);

        DamageAreaComp addedDamageComponentSize = repository.save(damageAreaComponent);

        return damageAreaComponentMapper.DamageAreaComponentToDamageAreaComponentDTO(addedDamageComponentSize);

    }

    @Override
    public DamageAreaComponentDTO updateDamageAreaComponent(
            @Valid DamageAreaComponentDTO damageAreaComponentDTO, Map<String, String> headers) throws SQLException {
        UserId.headerUserID(headers);

        DamageAreaComp damageAreaComp;
        damageAreaComp = repository.findByAreaCdAndJobCode(damageAreaComponentDTO.getAreaCd(), damageAreaComponentDTO.getJobCode());
        if (damageAreaComp == null) {
            throw new NoRecordsFoundException("No record found for given AreaCd and JobCode");
        }
        damageAreaComp.setUpdateUserId(headers.get(CommonConstants.USER_ID).toUpperCase());
        damageAreaComp.setUversion("!");
        damageAreaComp.setUpdateExtensionSchema(headers.get(CommonConstants.EXTENSION_SCHEMA));
        if (damageAreaComp.getUpdateExtensionSchema() == null) {
            damageAreaComp.setUpdateExtensionSchema("IMS01242");
        }
        damageAreaComp.setOrderCode(damageAreaComponentDTO.getOrderCode());
        
        damageAreaComp = repository.save(damageAreaComp);
        return damageAreaComponentMapper.DamageAreaComponentToDamageAreaComponentDTO(damageAreaComp);

    }


}
