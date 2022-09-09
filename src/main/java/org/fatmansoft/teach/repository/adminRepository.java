package org.fatmansoft.teach.repository;

import org.fatmansoft.teach.models.admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;

public interface adminRepository extends JpaRepository<admin,Integer> {
//    @Query(value = "select * from customer  where cus_num=?1", nativeQuery = true)
//    List<admin> findcustomerListByNum(String num);
//    @Query(value = "select * from customer  where cus_num=?1", nativeQuery = true)
//    Optional<customer> findcustomerclassByNum(String num);
}
