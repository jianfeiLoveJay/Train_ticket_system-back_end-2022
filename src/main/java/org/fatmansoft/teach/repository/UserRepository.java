package org.fatmansoft.teach.repository;


import java.util.List;
import java.util.Optional;

import org.fatmansoft.teach.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUserName(String userName);
    Optional<User> findByUsernum(String usernum);
    @Query(value = "select * from user where user_id=?1", nativeQuery = true)
    List<User> findUserById(Integer num);

    boolean existsByUserName(String username);

    boolean existsByUsernum(String usernum);
}