package org.fatmansoft.teach.models;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(	name = "passenger",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "passenger_id"),
        })
public class passenger {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer passenger_id;

    @Size(max = 50)
    private String user_num;

    @Size(max = 50)
    private String passenger_name;

    @Size(max = 50)
    private String passenger_num;

    @Size(max = 50)
    private String passenger_phoneNum;

    public passenger() {
    }

    public Integer getPassenger_id() {
        return passenger_id;
    }

    public void setPassenger_id(Integer passenger_id) {
        this.passenger_id = passenger_id;
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
}
