package org.fatmansoft.teach.repository;

import org.fatmansoft.teach.models.customer;
import org.fatmansoft.teach.models.passenger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;

public interface passengerRepository extends JpaRepository<passenger,Integer> {
    @Query(value = "select * from passenger  where user_num=?1", nativeQuery = true)
    List<passenger> findpassengerListByusernum(String usernum);
//    @Query(value = "select * from customer  where cus_num=?1", nativeQuery = true)
//    Optional<customer> findcustomerclassByNum(String num);
}
