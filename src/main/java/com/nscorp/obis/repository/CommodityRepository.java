package com.nscorp.obis.repository;

import com.nscorp.obis.domain.Commodity;
import com.nscorp.obis.domain.CommodityCompositePrimaryKeys;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface CommodityRepository
        extends JpaRepository<Commodity, CommodityCompositePrimaryKeys> {

    Page<Commodity> findAll(Specification<Commodity> specification, Pageable pageable);

    boolean existsByCommodityCode5AndCommodityCode2AndCommoditySubCode(Integer commodityCode5, Integer commodityCode2, Integer commoditySubCode);

    Optional<Commodity> findByCommodityCode5AndCommodityCode2AndCommoditySubCode(Integer commodityCode5, Integer commodityCode2, Integer commoditySubCode);

}
