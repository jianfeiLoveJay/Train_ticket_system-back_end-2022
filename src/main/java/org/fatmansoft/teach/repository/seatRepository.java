package org.fatmansoft.teach.repository;

import org.fatmansoft.teach.models.seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface seatRepository extends JpaRepository<seat,Integer> {
    @Query(value = "select * from seat  where train_num=?1 and train_room_num=?2 and seat_state='未预定'", nativeQuery = true)
    List<seat> findnoreserveseatListBytrainNumtrainroomnum(String trainNum,String trainroomnum);

    @Query(value = "select * from seat  where train_num=?1 and train_room_num=?2 and seat_state='未预定' and seat_type='特等座'", nativeQuery = true)
    List<seat> findnoreserveseatTListBytrainNumtrainroomnum(String trainNum,String trainroomnum);
    @Query(value = "select * from seat  where train_num=?1 and train_room_num=?2 and seat_state='未预定' and seat_type='一等座'", nativeQuery = true)
    List<seat> findnoreserveseatYListBytrainNumtrainroomnum(String trainNum,String trainroomnum);
    @Query(value = "select * from seat  where train_num=?1 and train_room_num=?2 and seat_state='未预定' and seat_type='二等座'", nativeQuery = true)
    List<seat> findnoreserveseatEListBytrainNumtrainroomnum(String trainNum,String trainroomnum);
    @Query(value = "select * from seat  where train_num=?1 and train_room_num=?2 and seat_state='未预定' and seat_type='三等座'", nativeQuery = true)
    List<seat> findnoreserveseatSListBytrainNumtrainroomnum(String trainNum,String trainroomnum);

    @Query(value = "select * from seat  where train_num=?1 and train_room_num=?2", nativeQuery = true)
    List<seat> findseatListBytrainNumtrainroomnum(String trainNum,String trainroomnum);
    @Query(value = "select MAX(seat_num) from seat  where train_num=?1 and train_room_num=?2", nativeQuery = true)
    String findmaxseatnumBytrainNumtrainroomnum(String trainNum,String trainroomnum);
//    @Query(value = "select * from seat  where train_num=?1 and train_room_num=?2", nativeQuery = true)
//    Optional<seat> findseatBytrainNumtrainroomnum(String trainNum,String trainroomnum);

//    @Query(value = "select * from course  where course_name=?1", nativeQuery = true)
//    List<seat> findCourseListByName(String name);
//    @Query(value = "select * from course  where courseteacher=?1", nativeQuery = true)
//    List<seat> findCourseListBycourseteacher(String courseteacher);
//    @Query(value = "select * from course  where course_num=?1 and course_name=?2", nativeQuery = true)
//    List<seat> findCourseListByNumname(String num,String name);

}
