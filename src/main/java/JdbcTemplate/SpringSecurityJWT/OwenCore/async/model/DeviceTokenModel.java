package JdbcTemplate.SpringSecurityJWT.OwenCore.async.model;

public class DeviceTokenModel {
    private String device_id;
    private String token;

    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
