package JdbcTemplate.SpringSecurityJWT.OwenCore.async.dto;

import java.util.Date;

public class ParametersDTO {

    private String device_id;

    private Long param_id;

    private Date timeInOven;

    private String value;

    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }

    public Long getParam_id() {
        return param_id;
    }

    public void setParam_id(Long param_id) {
        this.param_id = param_id;
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



}
