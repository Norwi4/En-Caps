package com.javainuse.systemClient.repository;

import com.javainuse.systemClient.model.Device;
import com.javainuse.systemClient.model.Sensor;
import com.javainuse.systemClient.model.dto.ParametersDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DeviceRepositoryPLC {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    /**
     * Сохранение значений датчиков
     * @param deviceDTOS
     */
    public void save(List<ParametersDTO> deviceDTOS) {
        for (ParametersDTO deviceDTO : deviceDTOS) {
            jdbcTemplate.update("INSERT INTO parameters (device_id, timeInOven, value, codeError, format) VALUES(?,?,?,?,?)",
                    deviceDTO.getDevice_id(),
                    deviceDTO.getTimeInOven(),
                    deviceDTO.getValue(),
                    deviceDTO.getCodeError(),
                    deviceDTO.getFormat());
        }
    }

    /**
     * Сохранение значений датчиков
     * @param deviceDTOS
     */
    public void saveFailure(List<ParametersDTO> deviceDTOS) {
        for (ParametersDTO deviceDTO : deviceDTOS) {
            jdbcTemplate.update("INSERT INTO failure (device_id, timeInOven, value, codeError, format) VALUES(?,?,?,?,?)",
                    deviceDTO.getDevice_id(),
                    deviceDTO.getTimeInOven(),
                    deviceDTO.getValue(),
                    deviceDTO.getCodeError(),
                    deviceDTO.getFormat());
        }
    }

    /**
     * Сохранение информации по приборам с определенного ПЛК
     * @param devices
     */
    public void saveSensor(Device devices) {
        String type;
        for (Sensor sensors : devices.getSensors()) {
            if (sensors.getName().indexOf("Авария") >= 0){
                type = "Авария";
            } else {
                type = "Параметр";
            }
            jdbcTemplate.update("INSERT INTO device (plc_id, device_id, code, category_id, name, type) VALUES (?,?,?,?,?,?)",
                    devices.getId(),
                    sensors.getId(),
                    sensors.getCode(),
                    sensors.getCategory_id(),
                    sensors.getName(),
                    type);
        }
    }

    /**
     * Проверка существования таблицы device
     * @return
     */
    public Boolean existDeviceList() {
        return jdbcTemplate.queryForObject(
                "SELECT EXISTS(SELECT * FROM device)",
                Boolean.class);
    }
}
