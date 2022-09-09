package org.fatmansoft.teach.repository;

import org.fatmansoft.teach.models.train;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface trainRepository extends JpaRepository<train,Integer> {
    @Query(value = "select * from train  where train_begin_place=?1", nativeQuery = true)
    List<train> findtrainListBybegin(String begin);
    @Query(value = "select * from train  where train_end_place=?1", nativeQuery = true)
    List<train> findtrainListByend(String end);
    @Query(value = "select * from train  where train_begin_day=?1", nativeQuery = true)
    List<train> findtrainListBytime(String time);
    @Query(value = "select * from train where train_begin_place=?1 and train_end_place=?2", nativeQuery = true)
    List<train> findtrainListBybeginend(String begin, String end);
    @Query(value = "select * from train  where train_begin_place=?1 and train_begin_day=?2", nativeQuery = true)
    List<train> findtrainListBybegintime(String begin, String time);
    @Query(value = "select * from train  where train_end_place=?1 and train_begin_day=?2", nativeQuery = true)
    List<train> findtrainListByendtime(String end, String time);
    @Query(value = "select * from train  where train_begin_place=?1 and train_end_place=?2 and train_begin_day=?3", nativeQuery = true)
    List<train> findtrainListBybeginendtime(String begin,String end,String time);
    @Query(value = "select * from train  where train_begin_place=?1 and train_end_place=?2 and train_num!=?3", nativeQuery = true)
    List<train> findtrainListBybeginend_trainNum(String begin,String end,String trainNum);

    @Query(value = "select t1.train_id as t1_id,t2.train_id as t2_id from train t1,train t2 where t1.train_begin_place=?1 and t2.train_end_place=?2 and t1.train_end_place=t2.train_begin_place", nativeQuery = true)
    List<Map<String,Object>> findtransfertrainListBybeginendnochangenotime(String begin,String end);
    @Query(value = "select t1.train_id as t1_id,t2.train_id as t2_id from train t1,train t2 where t1.train_begin_place=?1 and t2.train_end_place=?3 and t1.train_end_place=?2 and t2.train_begin_place=?2", nativeQuery = true)
    List<Map<String,Object>> findtransfertrainListBybeginendchange(String begin, String change, String end);
    @Query(value = "select t1.train_id as t1_id,t2.train_id as t2_id from train t1,train t2 where t1.train_begin_place=?1 and t2.train_end_place=?2 and t1.train_end_place=t2.train_begin_place and t1.train_begin_day=?3", nativeQuery = true)
    List<Map<String,Object>> findtransfertrainListBybeginendtime(String begin, String end, String time);
    @Query(value = "select t1.train_id as t1_id,t2.train_id as t2_id from train t1,train t2 where t1.train_begin_place=?1 and t2.train_end_place=?3 and t1.train_end_place=?2 and t2.train_begin_place=?2  and t1.train_begin_day=?4", nativeQuery = true)
    List<Map<String,Object>> findtransfertrainListBybeginendchangetime(String begin,String change, String end, String time);

}
