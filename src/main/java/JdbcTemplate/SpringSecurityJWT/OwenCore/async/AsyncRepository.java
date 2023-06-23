package JdbcTemplate.SpringSecurityJWT.OwenCore.async;

import JdbcTemplate.SpringSecurityJWT.OwenCore.async.dto.ParametersDTO;
import JdbcTemplate.SpringSecurityJWT.OwenCore.async.model.DeviceTokenModel;
import JdbcTemplate.SpringSecurityJWT.OwenCore.model.ParamsDictionaryModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AsyncRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * Получили все обьекты и их токены
     * @return
     */
    public List<DeviceTokenModel> deviceTokenModels() {
        return jdbcTemplate.query("select * from response", //where visible = 1
                new BeanPropertyRowMapper<DeviceTokenModel>(DeviceTokenModel.class));
    }

    /**
     * Получение параметров обьекта
     * @param device_id объект
     * @return список параметров
     */
    public List<ParamsDictionaryModel> paramsDictionaryList(String device_id) {
        return jdbcTemplate.query("select device_id, param_id from paramsdictionary where device_id = ?",
                new BeanPropertyRowMapper<ParamsDictionaryModel>(ParamsDictionaryModel.class), device_id);
    }

    /**
     * Сохранение значений датчиков
     * @param deviceDTOS
     */
    public void saveObjectParam(List<ParametersDTO> deviceDTOS) {
        for (ParametersDTO deviceDTO : deviceDTOS) {
            try {
                jdbcTemplate.update("INSERT INTO objectparams (object_id, param_id, value, timeInOven) VALUES(?,?,?,?)",
                        deviceDTO.getDevice_id(),
                        deviceDTO.getParam_id(),
                        deviceDTO.getValue(),
                        deviceDTO.getTimeInOven());
            } catch (Exception e) {
                System.out.println(e);
            }

        }
    }

}
