package org.fatmansoft.teach.models;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(	name = "customer",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "cusNum"),
        })
public class customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer cusId;

    @Size(max = 50)
    private String cusNum;

    @Size(max = 50)
    private String cusName;

    @Size(max = 50)
    private String cusphone;

    @Size(max = 60)
    @Email
    private String cusemail;

    @Size(max = 50)
    private String cusaddress;

    @Size(max = 50)
    private String cussex;


    public customer() {
    }

    public Integer getCusId() {
        return cusId;
    }

    public void setCusId(Integer cusId) {
        this.cusId = cusId;
    }

    public String getCusNum() {
        return cusNum;
    }

    public void setCusNum(String cusNum) {
        this.cusNum = cusNum;
    }

    public String getCusName() {
        return cusName;
    }

    public void setCusName(String cusName) {
        this.cusName = cusName;
    }

    public String getCusphone() {
        return cusphone;
    }

    public void setCusphone(String cusphone) {
        this.cusphone = cusphone;
    }

    public String getCusemail() {
        return cusemail;
    }

    public void setCusemail(String cusemail) {
        this.cusemail = cusemail;
    }

    public String getCusaddress() {
        return cusaddress;
    }

    public void setCusaddress(String cusaddress) {
        this.cusaddress = cusaddress;
    }

    public String getCussex() {
        return cussex;
    }

    public void setCussex(String cussex) {
        this.cussex = cussex;
    }
}
