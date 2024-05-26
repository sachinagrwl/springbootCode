package com.nscorp.obis.repository;

import com.nscorp.obis.domain.NotifyProfileMethod;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.validation.Valid;
import java.util.List;

@Repository
public interface NotifyProfileMethodRepository extends JpaRepository<NotifyProfileMethod, Long> {

	Page<NotifyProfileMethod> findAll(Specification<NotifyProfileMethod> specification, Pageable pageable);
	
	NotifyProfileMethod findByNotifyMethodId(@Valid Long notifyMethodId);

	List<NotifyProfileMethod> findAll(Specification<NotifyProfileMethod> specs);

	boolean existsByNotifyMethodId(Long notifyMethodId);
	

}
