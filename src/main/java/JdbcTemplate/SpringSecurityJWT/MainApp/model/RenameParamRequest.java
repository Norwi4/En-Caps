package JdbcTemplate.SpringSecurityJWT.MainApp.model;

public class RenameParamRequest {

    /**
     * ID параметра(датчика)
     */
    private String paramID;
    /**
     * Название параметра для списка
     */
    private String paramLongName;

    /**
     * Название параметра для дашборда
     */
    private String paramShortName;

    public String getParamID() {
        return paramID;
    }

    public void setParamID(String paramID) {
        this.paramID = paramID;
    }

    public String getParamLongName() {
        return paramLongName;
    }

    public void setParamLongName(String paramLongName) {
        this.paramLongName = paramLongName;
    }

    public String getParamShortName() {
        return paramShortName;
    }

    public void setParamShortName(String paramShortName) {
        this.paramShortName = paramShortName;
    }
}
