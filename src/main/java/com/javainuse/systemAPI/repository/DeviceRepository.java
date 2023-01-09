package com.javainuse.systemAPI.repository;

import com.javainuse.systemClient.model.PLC;
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

    public List<ParametersDTO> getLastData() {
        return jdbcTemplate.query("SELECT parameters.device_id,\n" +
                "       parameters.value,\n" +
                "       d.name\n" +
                "FROM (SELECT * FROM parameters ORDER BY id DESC LIMIT 29) as parameters\n" +
                "         LEFT JOIN device d on d.device_id = parameters.device_id\n", BeanPropertyRowMapper.newInstance(ParametersDTO.class));
    }

    public List<ParametersDTO> getLastDataFailure() {
        return jdbcTemplate.query(
                "SELECT failure.device_id,\n" +
                        "       failure.value,\n" +
                        "       d.name\n" +
                        "FROM (SELECT * FROM failure ORDER BY id DESC LIMIT 21) as failure\n" +
                        "         LEFT JOIN device d on d.device_id = failure.device_id",
                BeanPropertyRowMapper.newInstance(ParametersDTO.class)
        );
    }

    public PLC getStatusPLC() {
        return jdbcTemplate.queryForObject(
                "SELECT * FROM (SELECT * FROM plc ORDER BY id DESC LIMIT 1) t ORDER BY id",
                BeanPropertyRowMapper.newInstance(PLC.class)
        );
    }
}
