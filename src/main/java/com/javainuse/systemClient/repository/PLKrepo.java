package com.javainuse.systemClient.repository;

import com.javainuse.systemClient.model.PLC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PLKrepo {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void save(List<PLC> PLC) {
        for (PLC PLC1 : PLC) {
            jdbcTemplate.update("INSERT INTO plc (device_id, name, type, status) VALUES(?,?,?,?)",
                    new Object[] { PLC1.getId(), PLC1.getName(), PLC1.getType(), PLC1.getStatus() });
        }
    }
}
