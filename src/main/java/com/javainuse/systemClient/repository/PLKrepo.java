package com.javainuse.systemClient.repository;

import com.javainuse.systemClient.model.ChildCompanies;
import com.javainuse.systemClient.model.PLC;
import com.javainuse.systemClient.model.Sensor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PLKrepo {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * Сохранение данных об плк
     * @param PLC
     */
    public void save(List<PLC> PLC, String company_id) {
        for (PLC PLC1 : PLC) {
            jdbcTemplate.update("INSERT INTO plc (company_id, device_id, name, type, status) VALUES(?,?,?,?, ?)",
                    new Object[] {company_id, PLC1.getDevice_id(), PLC1.getName(), PLC1.getType(), PLC1.getStatus() });
        }
    }

    /**
     * Сохранение подключенных компаний
     * @param companies
     */
    public void saveChildCompanies(List<ChildCompanies> companies) {
        for (ChildCompanies childCompanies : companies) {
            jdbcTemplate.update("INSERT INTO companies (company_id,name) VALUES (?,?)",
                    new Object[] { childCompanies.getCompany_id(), childCompanies.getName() });
        }
    }

    public List<ChildCompanies> getChildCompanies() {
        return jdbcTemplate.query("SELECT * FROM companies" , new BeanPropertyRowMapper<ChildCompanies>(ChildCompanies.class));
    }

    public List<PLC> getPlcList() {
        return jdbcTemplate.query("SELECT * FROM plc" , new BeanPropertyRowMapper<PLC>(PLC.class));
    }
}
