package org.fatmansoft.teach.models;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(	name = "seat")
public class seat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer seat_id;

//    @ManyToOne()
//    @JoinColumn(name = "train_Num")
//    private train train_Num;

    @Size(max = 50)
    private String train_Num;

    @Size(max = 50)
    private String train_roomNum;

    @Size(max = 50)
    private String seat_num;

    @Size(max = 50)
    private String seat_type;

    @Size(max = 50)
    private String seat_price;

    @Size(max = 50)
    private String seat_state;

    public seat(){};

    public Integer getSeat_id() {
        return seat_id;
    }

    public void setSeat_id(Integer seat_id) {
        this.seat_id = seat_id;
    }

    public String getTrain_Num() {
        return train_Num;
    }

    public void setTrain_Num(String train_Num) {
        this.train_Num = train_Num;
    }

    public String getTrain_roomNum() {
        return train_roomNum;
    }

    public void setTrain_roomNum(String train_roomNum) {
        this.train_roomNum = train_roomNum;
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

    public String getSeat_price() {
        return seat_price;
    }

    public void setSeat_price(String seat_price) {
        this.seat_price = seat_price;
    }

    public String getSeat_state() {
        return seat_state;
    }

    public void setSeat_state(String seat_state) {
        this.seat_state = seat_state;
    }
}
