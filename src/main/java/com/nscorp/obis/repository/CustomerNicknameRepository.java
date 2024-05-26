package com.nscorp.obis.repository;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import com.nscorp.obis.domain.CustomerNickname;

public interface CustomerNicknameRepository extends JpaRepository<CustomerNickname, Double>{
	
	List<CustomerNickname> findAll(Specification<CustomerNickname> specification);

	boolean existsByCustomerNickname(String Nickname);
	boolean existsByCustomerNicknameAndTerminalId(String Nickname,Long terminalId);
	void deleteByCustomerNickname(String Nickname);
	void deleteAllByCustomerId(Long customerId);

	CustomerNickname findByCustomerNicknameAndTerminalId(String string, Long terminalId);

}
