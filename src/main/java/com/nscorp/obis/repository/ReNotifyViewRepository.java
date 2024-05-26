package com.nscorp.obis.repository;

import com.nscorp.obis.domain.ReNotifyView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;

@Repository
public interface ReNotifyViewRepository extends JpaRepository<ReNotifyView,Long> {

    @Query(value = "Select rv from ReNotifyView rv where rv.termId=:termId " +
            "AND (rv.equipInit like CONCAT(upper(:equipInit),'%') or :equipInit is null) " +
            "AND (rv.equipNbr like CONCAT(:equipNbr,'%')  or :equipNbr is null) " +
            "AND (rv.notifyCustomerName like CONCAT('%',upper(:notifyCustomerName),'%') or :notifyCustomerName is null)")
    Page<ReNotifyView> searchAll(double termId, String equipInit, Integer equipNbr, String notifyCustomerName,Pageable pageable);

    @Query(value = "Select rv from ReNotifyView rv where rv.termId=:termId AND rv.notifyStat not in ('DELY','SEND')" +
            "AND (rv.notifyCustomerName like CONCAT('%',upper(:notifyCustomerName),'%') or :notifyCustomerName is null)")
    List<ReNotifyView> searchAllByTermIdAndCustomerName(double termId, String notifyCustomerName);

    @Query(value="SELECT COUNT(rv.notifyStat) FROM ReNotifyView rv WHERE rv.termId=:termId And rv.notifyStat='DELY'" +
            "AND (rv.notifyCustomerName like CONCAT('%',upper(:custName),'%') or :custName is null)")
    int getCountNotifyStateForTerminal(double termId, String custName);

}
