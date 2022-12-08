package com.javainuse.systemAPI.repository;

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
        return jdbcTemplate.query("SELECT * FROM (SELECT * FROM parameters ORDER BY id DESC LIMIT 14) t ORDER BY id", BeanPropertyRowMapper.newInstance(ParametersDTO.class));
    }
}
