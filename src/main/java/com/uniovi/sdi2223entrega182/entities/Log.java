package com.uniovi.sdi2223entrega182.entities;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "log")
public class Log {
    @Id
    @GeneratedValue
    private long id;
    private String type;

    private String details;

    private Date date;

    public Log(String type, String details, Date date) {
        this.type = type;
        this.details = details;
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
    public Long getId() {
        return id;
    }

    public Log() {

    }
}
