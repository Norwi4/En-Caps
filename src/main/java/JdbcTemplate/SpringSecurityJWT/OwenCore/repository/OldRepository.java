package JdbcTemplate.SpringSecurityJWT.OwenCore.repository;

import JdbcTemplate.SpringSecurityJWT.OwenCore.vo.CompaniesModel;
import JdbcTemplate.SpringSecurityJWT.OwenCore.vo.Device;
import JdbcTemplate.SpringSecurityJWT.OwenCore.vo.PlcCompany;
import JdbcTemplate.SpringSecurityJWT.OwenCore.vo.Sensor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

@Repository
public class OldRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * Сохранение каждой ПЛК под видом отдельного обьекта
     * @param companies список обьектов (котельных) компании
     */
    public void saveCompanies(List<PlcCompany> companies, Integer company_id) {
        for (PlcCompany plcCompany : companies) {
            jdbcTemplate.update("insert into object (object_name, company_id, device_id) values (?, ?, ?)",
                    plcCompany.getName(),
                    company_id,
                    plcCompany.getId()
            );
        }
    }

    public Integer getCompanyByName(String companyName) {
        return jdbcTemplate.queryForObject("SELECT id FROM company where name = ?",
                Integer.class,
                companyName);
    }

    /**
     * Получение обьектов компании
     * @param company_id
     * @return список компаний
     */
    public List<CompaniesModel> getCompanies(Integer company_id) {
        return jdbcTemplate.query("SELECT * FROM object where company_id=?",
                BeanPropertyRowMapper.newInstance(CompaniesModel.class),
                company_id);
    }

    /**
     * Сохранение информации по приборам с определенного ПЛК
     * @param devices
     */
    public void saveSensor(Device devices) {
        String type;
        for (Sensor sensors : devices.getSensors()) {
            if (sensors.getName().indexOf("Авария")>= 0 || sensors.getName().indexOf("Ошибки") >=0) {
                type = "Авария";
            } else {
                type = "Параметр";
            }
            jdbcTemplate.update("INSERT INTO paramsdictionary (device_id, param_id, code,type, name) VALUES (?,?,?,?, ?)",
                    devices.getId(),
                    sensors.getId(),
                    sensors.getCode(),
                    type,
                    sensors.getName()
            );
        }
    }
}
