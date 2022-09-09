package org.fatmansoft.teach.models;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(	name = "train"
//        , uniqueConstraints = {
//                @UniqueConstraint(columnNames = "train_num"),
//        }
        )
public class train {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer train_id;

    @Size(max = 50)
    private String train_num;

    @Size(max = 50)
    private String train_name;

    @Size(max = 50)
    private String train_type;

    @Size(max = 50)
    private String train_room;

    @Size(max = 50)
    private String train_roomSize;

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

    public train(){};

    public Integer getTrain_id() {
        return train_id;
    }

    public void setTrain_id(Integer train_id) {
        this.train_id = train_id;
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

    public String getTrain_room() {
        return train_room;
    }

    public void setTrain_room(String train_room) {
        this.train_room = train_room;
    }

    public String getTrain_roomSize() {
        return train_roomSize;
    }

    public void setTrain_roomSize(String train_roomSize) {
        this.train_roomSize = train_roomSize;
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
