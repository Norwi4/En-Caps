package JdbcTemplate.SpringSecurityJWT.OwenCore.async.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Values {

    @JsonProperty("d")
    private String timeInOven;

    @JsonProperty("v")
    private String value;

    @JsonProperty("e")
    private String codeError;

    @JsonProperty("f")
    private String format;

    public String getTimeInOven() {
        return timeInOven;
    }

    public void setTimeInOven(String timeInOven) {
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
