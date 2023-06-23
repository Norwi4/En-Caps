package JdbcTemplate.SpringSecurityJWT.OwenCore.async.dto;

import JdbcTemplate.SpringSecurityJWT.OwenCore.async.model.ParametersResponse;
import JdbcTemplate.SpringSecurityJWT.OwenCore.async.model.Values;
import org.springframework.stereotype.Component;

@Component
public class DevToDTO {

    public ParametersDTO deviceDTO(ParametersResponse device, String device_id) {

        ParametersDTO deviceDTO = new ParametersDTO();
        deviceDTO.setDevice_id(device_id);
        deviceDTO.setParam_id(device.getId());

        Values values = device.getValues().get(0);
        deviceDTO.setTimeInOven(new java.util.Date( Long.valueOf(values.getTimeInOven())*1000) );
        deviceDTO.setValue(values.getValue());


        return deviceDTO;
    }
}
