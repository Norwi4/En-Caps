package com.javainuse.systemClient.model.dto;

import java.util.Date;

public class ParametersDTO {

    private Long device_id;

    private Date timeInOven;

    private String value;

    private String codeError;

    private String format;

    public Long getDevice_id() {
        return device_id;
    }

    public void setDevice_id(Long device_id) {
        this.device_id = device_id;
    }

    public Date getTimeInOven() {
        return timeInOven;
    }

    public void setTimeInOven(Date timeInOven) {
        this.timeInOven = timeInOven;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getCodeError() {
        return codeError;
    }

    public void setCodeError(String codeError) {
        this.codeError = codeError;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

}
