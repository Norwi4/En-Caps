package com.javainuse.server.repository.dataCollection;

import com.javainuse.generalModel.OwenUser;
import com.javainuse.server.model.Device;
import com.javainuse.server.model.ParametersModel;
import com.javainuse.server.model.Sensor;
import com.javainuse.server.model.UserToken;
import com.javainuse.server.model.lastData.DevId;
import com.javainuse.server.model.lastData.ParametersDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Репозиторий для запроса данных из OwenCloud, сохранения
 * их в базе и извлечение по запросу
 */

@Repository
public class DataRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * Получение списка пользователь - токен
     * @return список
     */
    public List<UserToken> getTokenList() {
        return jdbcTemplate.query("SELECT * FROM authresponse", new BeanPropertyRowMapper(UserToken.class));
    }


    /**
     * Сохранение информации по приборам с определенного ПЛК
     * @param devices
     */
    public void saveSensor(Device devices, OwenUser user) {
        String type;
        for (Sensor sensors : devices.getSensors()) {
            if (sensors.getName().indexOf("Авария") >= 0){
                type = "Авария";
            } else {
                type = "Параметр";
            }
            jdbcTemplate.update("INSERT INTO device (company_id, device_id, code, category_id, name, type, user_id) VALUES (?,?,?,?,?,?,?)",
                    devices.getId(),
                    sensors.getId(),
                    sensors.getCode(),
                    sensors.getCategory_id(),
                    sensors.getName(),
                    type,
                    user.getUser_id());
        }
    }

    /**
     * Получение id Параметров подключенных датчиков к ПЛК
     * @return
     */
    public List<DevId> getDeviceCompany(String company_id) {
        return jdbcTemplate.query(
                "SELECT * FROM device WHERE company_id=?",
                BeanPropertyRowMapper.newInstance(DevId.class),
                company_id
        );
    }

    /**
     * Сохранение значений датчиков
     * @param deviceDTOS
     */
    public void saveDeviceParameter(List<ParametersDTO> deviceDTOS, String user_id, String company_id) {
        for (ParametersDTO deviceDTO : deviceDTOS) {
            jdbcTemplate.update(
                    "INSERT INTO parameters (device_id, timeInOven, value, codeError, format, user_id, company_id) VALUES(?,?,?,?,?,?,?)",
                    new Object[]{
                            deviceDTO.getDevice_id(),
                            deviceDTO.getTimeInOven(),
                            deviceDTO.getValue(),
                            deviceDTO.getCodeError(),
                            deviceDTO.getFormat(),
                            user_id,
                            company_id});
                    /*deviceDTO.getDevice_id(),
                    deviceDTO.getTimeInOven(),
                    deviceDTO.getValue(),
                    deviceDTO.getCodeError(),
                    deviceDTO.getFormat())*/
            ;
        }
    }

    /**
     * Получение последних значений параметров
     * @param user_id
     * @return
     */
    public List<ParametersModel> getLastData(String user_id, String value) {
        return jdbcTemplate.query(
                "SELECT * FROM (SELECT * FROM parameters ORDER BY id DESC LIMIT ?) WHERE user_id = ?",
                BeanPropertyRowMapper.newInstance(ParametersModel.class),
                value,
                user_id);
    }

    /**
     * Получение кол-ва параметров всех компаний пользователя
     * @param user_id
     * @return
     */
    public String getCountParameters(String user_id) {
        return jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM device WHERE user_id=?",
                String.class,
                user_id);
    }
}
