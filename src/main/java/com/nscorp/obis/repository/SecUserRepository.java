package com.nscorp.obis.repository;

import com.nscorp.obis.domain.SecUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SecUserRepository extends JpaRepository<SecUser,String> {
    SecUser findBySecUserId(String id);
}
