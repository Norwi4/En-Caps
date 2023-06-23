package JdbcTemplate.SpringSecurityJWT.MainApp.model.DashboardView;

import java.util.List;

/**
 * Модель представления обьектов и их параметров
 */
public class Dashboard {
    /**
     * Название объекта
     */
    private String objectName;
    /**
     * ID объекта (ПЛК)
     */
    private String objectID;
    /**
     * Список параметров
     */
    private List<Views> viewsList;

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    public String getObjectID() {
        return objectID;
    }

    public void setObjectID(String objectID) {
        this.objectID = objectID;
    }

    public List<Views> getViewsList() {
        return viewsList;
    }

    public void setViewsList(List<Views> viewsList) {
        this.viewsList = viewsList;
    }
}
