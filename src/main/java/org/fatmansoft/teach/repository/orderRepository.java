package org.fatmansoft.teach.repository;

import org.fatmansoft.teach.models.trainorder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface orderRepository extends JpaRepository<trainorder,Integer> {
    @Query(value = "select * from trainorder  where user_num=?1", nativeQuery = true)
    List<trainorder> findorderListByusernum(String usernum);
    @Query(value = "select * from trainorder  where order_state=?1", nativeQuery = true)
    List<trainorder> findorderListByorderstate(String orderstate);
    @Query(value = "select * from trainorder  where user_num=?1 and order_state=?2", nativeQuery = true)
    List<trainorder> findorderListByusernumorderstate(String usernum,String orderstate);
    @Query(value = "select * from trainorder  where train_num=?1", nativeQuery = true)
    List<trainorder> findorderListBytrainNum(String trainNum);
    @Query(value = "select * from trainorder  where seat_id=?1", nativeQuery = true)
    List<trainorder> findorderListByseatId(Integer seatId);
    @Query(value = "select * from trainorder  where user_num=?1 and order_state='取消支付'or order_state='退票'or order_state='支付超时' or order_state='改签'", nativeQuery = true)
    List<trainorder> findcanceledorderListByusernum(String usernum);
//    @Query(value = "select * from trainorder  where order_id=?1", nativeQuery = true)
//    Optional<trainorder> findordersListByorderId(Integer orderId);
//    @Query(value = "select * from selectcourse  where courseteacher=?1", nativeQuery = true)
//    List<order> findselectCourseListBycourseteacher(String courseteacher);
//    @Query(value = "select * from selectcourse  where course_num=?1", nativeQuery = true)
//    List<order> findselectCourseListBycourseNum(String courseNum);
//    @Query(value = "select * from selectcourse  where stuclass=?1", nativeQuery = true)
//    List<order> findselectCourseListBystuclass(String stuclass);
//    @Query(value = "select * from selectcourse  where course_num=?1 and usernum=?2", nativeQuery = true)
//    Optional<order> findCourseListBycourseNumuserNum(String course_num, String usernum);
//    @Query(value = "select * from selectcourse  where  usernum=?1 and courseday=?2 and courseorder=?3", nativeQuery = true)
//    Optional<order> findCourseListByuserNumcoursedaycourseorder( String usernum,String courseday, String courseorder);
//    @Query(value = "select * from selectcourse  where course_num=?1 and stuclass=?2", nativeQuery = true)
//    List<order> findselectCourseListBycourseNumstuclass(String courseNum, String stuclass);

}
