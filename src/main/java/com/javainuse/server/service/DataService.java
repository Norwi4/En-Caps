package com.javainuse.server.service;

import com.javainuse.server.model.ParametersModel;
import com.javainuse.server.repository.dataCollection.DataRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DataService {

    final DataRepository dataRepository;
    final JdbcTemplate jdbcTemplate;

    public DataService(DataRepository dataRepository, JdbcTemplate jdbcTemplate) {
        this.dataRepository = dataRepository;
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Получение последних значений параметров
     * @param user_id
     * @return
     */
    public List<ParametersModel> getLastData(String user_id) {
        String value = dataRepository.getCountParameters(user_id);
        return dataRepository.getLastData(user_id, value);
    }

}
