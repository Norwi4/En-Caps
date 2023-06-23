package JdbcTemplate.SpringSecurityJWT.MainApp.repository;

import JdbcTemplate.SpringSecurityJWT.MainApp.model.Company;
import JdbcTemplate.SpringSecurityJWT.MainApp.model.DashboardView.Dashboard;
import JdbcTemplate.SpringSecurityJWT.MainApp.model.DashboardView.ListObjects;
import JdbcTemplate.SpringSecurityJWT.MainApp.model.DashboardView.Views;
import JdbcTemplate.SpringSecurityJWT.MainApp.model.ObjectsListResponse;
import JdbcTemplate.SpringSecurityJWT.MainApp.model.RenameParamRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Repository
public class CompanyRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * Создание компании
     * @param company название компании
     */
    public Integer createCompany(Company company) {
        return jdbcTemplate.update("INSERT INTO company (name) VALUES(?)", new Object[]{company.getName()});
    }

    /**
     * Проверка существования компании
     * @param company_name наименование компании
     * @return
     */
    public Boolean existCompany(String company_name) {
        return jdbcTemplate.queryForObject("SELECT EXISTS(SELECT * FROM company where name = ?)",
                Boolean.class, company_name);
    }

    /**
     * Подсчет количества параметров обьекта
     * @param companyId
     * @return
     */
    public Integer getCountUsersObjects(String companyId) {
        return jdbcTemplate.queryForObject("select COUNT(*) from paramsdictionary\n" +
                "left join object o on paramsdictionary.device_id = o.device_id\n" +
                "left join user u on o.company_id = u.company_id where paramsdictionary.device_id=?", Integer.class, companyId);
    }

    /**
     * Список компаний пользователя
     * @param userName
     * @return
     */
    public List<ListObjects> listObjects(String userName) {
        return jdbcTemplate.query("select identity.object.device_id,\n" +
                "       identity.object.object_name\n" +
                "from object\n" +
                "         left join company c on c.id = object.company_id\n" +
                "         left join user u on c.id = u.company_id\n" +
                "where user_name =?", BeanPropertyRowMapper.newInstance(ListObjects.class), userName);
    }

    /**
     * Получение параметров всех обьектов пользователя
     * @param userName
     * @return
     */
    @Transactional
    public List<Dashboard> dashboardList(String userName) {
        List<Dashboard> dashboardList = new ArrayList<>();
        List<ListObjects> listObjects = listObjects(userName);

        if (!listObjects.isEmpty()) {
            for (ListObjects objects : listObjects) {
                Dashboard dashboard = new Dashboard();
                dashboard.setObjectID(objects.getDevice_id());
                dashboard.setObjectName(objects.getObject_name());
                dashboard.setViewsList(jdbcTemplate.query("select *\n" +
                        "from (select\n" +
                        "             identity.objectparams.object_id,\n" +
                        "             identity.objectparams.param_id as paramID,\n" +
                        "             identity.objectparams.value,\n" +
                        "             p.name                 as param_name,\n" +
                        "             p.type                 as paramType,\n" +
                        "             p.name_in_system       as paramLongName,\n" +
                        "             p.short_name_in_system as paramShotrName\n" +
                        "\n" +
                        "      from objectparams\n" +
                        "               left join paramsdictionary p on objectparams.param_id = p.param_id\n" +
                        "               left join object o on o.device_id = objectparams.object_id\n" +
                        "               left join user u on o.company_id = u.company_id\n" +
                        "      where user_name = ? and object_id =?\n" +
                        "      order by objectparams.id DESC\n" +
                        "      LIMIT " + getCountUsersObjects(objects.getDevice_id()) + ") as param\n" +
                        "", BeanPropertyRowMapper.newInstance(Views.class), userName, objects.getDevice_id()));
                dashboardList.add(dashboard);
            }
        }
        return dashboardList;
    }

    /**
     * Получение объектв пользователя
     * @param userName имя поользователя
     * @return список объектв
     */
    public List<ObjectsListResponse> getObjectsUsers(String userName) {
        return jdbcTemplate.query("select identity.object.device_id as objectId,\n" +
                "       identity.object.object_name as objectName\n" +
                "from object\n" +
                "         left join company c on c.id = object.company_id\n" +
                "         left join user u on c.id = u.company_id\n" +
                "where user_name=?", BeanPropertyRowMapper.newInstance(ObjectsListResponse.class), userName);
    }

    /**
     * Получение параметров и аварий объекта
     * @param userName пользоователь, которому он принадлежит
     * @param objectId Id объекта
     * @return список параметров
     */
    public List<Views> getViewsObject(String userName, String objectId) {
        return jdbcTemplate.query("select *\n" +
                "from (select\n" +
                "             identity.objectparams.object_id,\n" +
                "             identity.objectparams.param_id as paramID,\n" +
                "             identity.objectparams.value as value,\n" +
                "             p.name                 as paramName,\n" +
                "             p.type                 as paramType,\n" +
                "             p.name_in_system       as paramLongName,\n" +
                "             p.short_name_in_system as paramShortName\n" +
                "\n" +
                "      from objectparams\n" +
                "               left join paramsdictionary p on objectparams.param_id = p.param_id\n" +
                "               left join object o on o.device_id = objectparams.object_id\n" +
                "               left join user u on o.company_id = u.company_id\n" +
                "      where user_name=? and object_id=?\n" +
                "      order by objectparams.id desc limit " + getCountUsersObjects(objectId) + ") as param order by paramType",
                BeanPropertyRowMapper.newInstance(Views.class),
                userName,
                objectId);
    }

    /**
     * Изменение словаря параметров
     * @param paramID ID параметра
     * @param paramLongName длинное название
     * @param paramShortName короткое название
     */
    public void renameParam(String paramID, String paramLongName, String paramShortName) {
        jdbcTemplate.update("update paramsdictionary set name_in_system=?, short_name_in_system=? where param_id=?",
                paramLongName,
                paramShortName,
                paramID);
    }

}
