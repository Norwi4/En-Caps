package JdbcTemplate.SpringSecurityJWT.MainApp.model;

/**
 * Модель компании
 * Моожет иметь несколько объектов
 */
public class Company {
    String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
