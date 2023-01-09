package com.javainuse.systemClient.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Events {
    @JsonProperty("id")
    private String log_id; //идентификатор записи в журнале
    private String event_id; //идентификатор события
    private String event_type;
    private String start_dt;
    private String end_dt;
    private String read_dt;
    private String read_by_user ;
    private String message;
    private List<Values> data;
    private String device_id;
    private String is_critical;
    private String type;

    public String getLog_id() {
        return log_id;
    }

    public void setLog_id(String log_id) {
        this.log_id = log_id;
    }

    public String getEvent_id() {
        return event_id;
    }

    public void setEvent_id(String event_id) {
        this.event_id = event_id;
    }

    public String getEvent_type() {
        return event_type;
    }

    public void setEvent_type(String event_type) {
        this.event_type = event_type;
    }

    public String getStart_dt() {
        return start_dt;
    }

    public void setStart_dt(String start_dt) {
        this.start_dt = start_dt;
    }

    public String getEnd_dt() {
        return end_dt;
    }

    public void setEnd_dt(String end_dt) {
        this.end_dt = end_dt;
    }

    public String getRead_dt() {
        return read_dt;
    }

    public void setRead_dt(String read_dt) {
        this.read_dt = read_dt;
    }

    public String getRead_by_user() {
        return read_by_user;
    }

    public void setRead_by_user(String read_by_user) {
        this.read_by_user = read_by_user;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Values> getData() {
        return data;
    }

    public void setData(List<Values> data) {
        this.data = data;
    }

    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }

    public String getIs_critical() {
        return is_critical;
    }

    public void setIs_critical(String is_critical) {
        this.is_critical = is_critical;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
