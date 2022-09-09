package org.fatmansoft.teach.models;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(	name = "trainorder"
//        ,uniqueConstraints = {
//        @UniqueConstraint(columnNames = "order_num"),
//        }
        )
public class trainorder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer order_id;

    @Size(max = 50)
    private String order_num;

    @Size(max = 50)
    private String order_state;

    @Size(max = 50)
    private String order_type;

    @Size(max = 50)
    private String order_time;

    @Size(max = 50)
    private String user_num;

    @Size(max = 50)
    private String passenger_name;

    @Size(max = 50)
    private String passenger_num;

    @Size(max = 50)
    private String passenger_phoneNum;

    @Size(max = 50)
    private String train_num;

    @Size(max = 50)
    private String train_name;

    @Size(max = 50)
    private String train_type;

    @Size(max = 50)
    private String train_roomNum;

    private Integer seat_id;

    @Size(max = 50)
    private String seat_num;

    @Size(max = 50)
    private String seat_type;

    @Size(max = 50)
    private String seat_state;

    @Size(max = 50)
    private String seat_price;

    @Size(max = 50)
    private String train_beginPlace;

    @Size(max = 50)
    private String train_endPlace;

    @Size(max = 50)
    private String train_beginDay;

    @Size(max = 50)
    private String train_endDay;

    @Size(max = 50)
    private String train_beginTime;

    @Size(max = 50)
    private String train_endTime;

    public trainorder(){};

    public Integer getOrder_id() {
        return order_id;
    }

    public void setOrder_id(Integer order_id) {
        this.order_id = order_id;
    }

    public String getOrder_num() {
        return order_num;
    }

    public void setOrder_num(String order_num) {
        this.order_num = order_num;
    }

    public String getOrder_state() {
        return order_state;
    }

    public void setOrder_state(String order_state) {
        this.order_state = order_state;
    }

    public String getOrder_type() {
        return order_type;
    }

    public void setOrder_type(String order_type) {
        this.order_type = order_type;
    }

    public String getOrder_time() {
        return order_time;
    }

    public void setOrder_time(String order_time) {
        this.order_time = order_time;
    }

    public String getUser_num() {
        return user_num;
    }

    public void setUser_num(String user_num) {
        this.user_num = user_num;
    }

    public String getPassenger_name() {
        return passenger_name;
    }

    public void setPassenger_name(String passenger_name) {
        this.passenger_name = passenger_name;
    }

    public String getPassenger_num() {
        return passenger_num;
    }

    public void setPassenger_num(String passenger_num) {
        this.passenger_num = passenger_num;
    }

    public String getPassenger_phoneNum() {
        return passenger_phoneNum;
    }

    public void setPassenger_phoneNum(String passenger_phoneNum) {
        this.passenger_phoneNum = passenger_phoneNum;
    }

    public String getTrain_num() {
        return train_num;
    }

    public void setTrain_num(String train_num) {
        this.train_num = train_num;
    }

    public String getTrain_name() {
        return train_name;
    }

    public void setTrain_name(String train_name) {
        this.train_name = train_name;
    }

    public String getTrain_type() {
        return train_type;
    }

    public void setTrain_type(String train_type) {
        this.train_type = train_type;
    }

    public String getTrain_roomNum() {
        return train_roomNum;
    }

    public void setTrain_roomNum(String train_roomNum) {
        this.train_roomNum = train_roomNum;
    }

    public Integer getSeat_id() {
        return seat_id;
    }

    public void setSeat_id(Integer seat_id) {
        this.seat_id = seat_id;
    }

    public String getSeat_num() {
        return seat_num;
    }

    public void setSeat_num(String seat_num) {
        this.seat_num = seat_num;
    }

    public String getSeat_type() {
        return seat_type;
    }

    public void setSeat_type(String seat_type) {
        this.seat_type = seat_type;
    }

    public String getSeat_state() {
        return seat_state;
    }

    public void setSeat_state(String seat_state) {
        this.seat_state = seat_state;
    }

    public String getSeat_price() {
        return seat_price;
    }

    public void setSeat_price(String seat_price) {
        this.seat_price = seat_price;
    }

    public String getTrain_beginPlace() {
        return train_beginPlace;
    }

    public void setTrain_beginPlace(String train_beginPlace) {
        this.train_beginPlace = train_beginPlace;
    }

    public String getTrain_endPlace() {
        return train_endPlace;
    }

    public void setTrain_endPlace(String train_endPlace) {
        this.train_endPlace = train_endPlace;
    }

    public String getTrain_beginDay() {
        return train_beginDay;
    }

    public void setTrain_beginDay(String train_beginDay) {
        this.train_beginDay = train_beginDay;
    }

    public String getTrain_endDay() {
        return train_endDay;
    }

    public void setTrain_endDay(String train_endDay) {
        this.train_endDay = train_endDay;
    }

    public String getTrain_beginTime() {
        return train_beginTime;
    }

    public void setTrain_beginTime(String train_beginTime) {
        this.train_beginTime = train_beginTime;
    }

    public String getTrain_endTime() {
        return train_endTime;
    }

    public void setTrain_endTime(String train_endTime) {
        this.train_endTime = train_endTime;
    }
}
