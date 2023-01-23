package com.javainuse.systemAPI.repository;

import com.javainuse.systemAPI.dao.parameterDAO;
import com.javainuse.systemClient.model.PLC;
import com.javainuse.systemClient.model.Sensor;
import com.javainuse.systemClient.model.deviceList;
import com.javainuse.systemClient.model.dto.ParametersDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DeviceRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * Получение последних параметров
     * @return
     */
    public List<parameterDAO> getLastData() {
        return jdbcTemplate.query("SELECT parameters.device_id,\n" +
                "       parameters.value,\n" +
                "       d.name\n" +
                "FROM (SELECT * FROM parameters ORDER BY id DESC LIMIT 29) as parameters\n" +
                "         LEFT JOIN device d on d.device_id = parameters.device_id\n", BeanPropertyRowMapper.newInstance(parameterDAO.class));
    }

    /**
     * Получение последних ошибок
     * @return
     */
    public List<parameterDAO> getLastDataFailure() {
        return jdbcTemplate.query(
                "SELECT failure.device_id,\n" +
                        "       failure.value,\n" +
                        "       d.name\n" +
                        "FROM (SELECT * FROM failure ORDER BY id DESC LIMIT 21) as failure\n" +
                        "         LEFT JOIN device d on d.device_id = failure.device_id",
                BeanPropertyRowMapper.newInstance(parameterDAO.class)
        );
    }

    /**
     * Получение последних данных об ПЛК
     * @return
     */
    public PLC getStatusPLC() {
        return jdbcTemplate.queryForObject(
                "SELECT * FROM (SELECT * FROM plc ORDER BY id DESC LIMIT 1) t ORDER BY id",
                BeanPropertyRowMapper.newInstance(PLC.class)
        );
    }

    /**
     * Получение списка подключенных приборов к ПЛК (только параметров)
     * @return
     */
    public List<deviceList> getDeviceList() {
        return jdbcTemplate.query(
                "SELECT * FROM device",
                BeanPropertyRowMapper.newInstance(deviceList.class)
        );
    }
}
