package JdbcTemplate.SpringSecurityJWT.MainApp.model.DashboardView;

/**
 * Модель предстваления параметров обьекта
 */
public class Views {
    /**
     * ID параметра(датчика)
     */
    private String paramID;
    /**
     * Занчение датчика
     */
    private String value;
    /**
     * Название параметра
     */
    private String paramName;
    /**
     * Тип параметра
     */
    private String paramType;
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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    public String getParamType() {
        return paramType;
    }

    public void setParamType(String paramType) {
        this.paramType = paramType;
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
