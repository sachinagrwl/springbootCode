package com.nscorp.obis.repository;

import com.nscorp.obis.domain.ResourceList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResourceListRepository extends JpaRepository <ResourceList, String> {
    boolean existsByResourceNameIgnoreCase(String resourceNm);
}

