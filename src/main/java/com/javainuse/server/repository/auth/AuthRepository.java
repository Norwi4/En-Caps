package com.javainuse.server.repository.auth;

import com.javainuse.generalModel.OwenUser;
import com.javainuse.generalModel.PlcCompany;
import com.javainuse.server.model.CompaniesModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Репозиторий для авторизации в системе OwenCloud, сохранение токена
 * Формирование и сохранения списка "Компаний"
 */
@Repository
public class AuthRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    /**
     * Сохраниение токена от OwenCloud
     * @param user_id пользователь, который авторизируется в системе
     * @param token токен OwenCloud
     */
    public void saveOwenToken(String user_id, String token) {
        jdbcTemplate.update("insert into authresponse (user_id, token) values (?, ?)",
                user_id,
                token);
    }

    /**
     * Сохранение ПЛК каждой компании под видом отдельной котельной
     * @param companies список компаний пользователя
     * @param user_id пользователь, который авторизовался в системе
     */
    public void saveCompanies(List<PlcCompany> companies, String user_id) {
        for (PlcCompany plcCompany : companies) {
            jdbcTemplate.update("insert into companies (company_id, name, user_id) values (?, ?, ?)",
                    plcCompany.getId(),
                    plcCompany.getName(),
                    user_id);
        }
    }

    /**
     * Получение компаний пользователя
     * @param user_id пользователь нашей системы
     * @return список компаний
     */
    public List<CompaniesModel> getCompanies(String user_id) {
        return jdbcTemplate.query("SELECT * FROM companies where user_id=?",
                BeanPropertyRowMapper.newInstance(CompaniesModel.class),
                user_id);
    }

    /**
     * Проверка существования токена от OwenCloud
     * @param user пользователь нашей системы
     * @return да/нет
     */
    public Boolean existToken(OwenUser user) {
        return jdbcTemplate.queryForObject(
                "SELECT EXISTS(SELECT token FROM authresponse where id=?)",
                Boolean.class,
                user.getUser_id()
        );
    }

    /**
     * Обновление токена OwenCloud
     * @param user пользователь нашей системы
     * @param token токен OwenCloud
     */
    public void updateToken(OwenUser user, String token) {
        jdbcTemplate.update("UPDATE authresponse SET token = ? WHERE user_id = ?",
                token,
                user.getUser_id()
        );
    }

    /**
     * Получение токена пользователя
     * @param user пользователь системы
     * @return токен
     */
    public String getToken(OwenUser user) {
        return jdbcTemplate.queryForObject("SELECT token FROM authresponse where id=?", String.class, user.getUser_id());
    }

}
