package JdbcTemplate.SpringSecurityJWT.OwenCore.async;

import JdbcTemplate.SpringSecurityJWT.OwenCore.async.dto.ParametersDTO;
import JdbcTemplate.SpringSecurityJWT.OwenCore.async.model.DeviceTokenModel;
import JdbcTemplate.SpringSecurityJWT.OwenCore.model.ParamsDictionaryModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AsyncService {

    @Autowired
    AsyncRepository asyncRepository;

    public List<DeviceTokenModel> deviceTokenModels() {
        return asyncRepository.deviceTokenModels();
    }

    public List<ParamsDictionaryModel> paramsDictionaryList(String device_id) {
        return asyncRepository.paramsDictionaryList(device_id);
    }

    public void saveObjectParam(List<ParametersDTO> deviceDTOS) {
        asyncRepository.saveObjectParam(deviceDTOS);
    }
}
