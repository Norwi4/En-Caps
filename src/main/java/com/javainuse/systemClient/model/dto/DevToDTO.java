package com.javainuse.systemClient.model.dto;

import com.javainuse.systemClient.model.Parameters;
import com.javainuse.systemClient.model.Values;
import org.springframework.stereotype.Component;

@Component
public class DevToDTO {

    public ParametersDTO deviceDTO(Parameters device) {

        ParametersDTO deviceDTO = new ParametersDTO();
        deviceDTO.setDevice_id(device.getId());

        Values values = device.getValues().get(0);
        deviceDTO.setTimeInOven(new java.util.Date( Long.valueOf(values.getTimeInOven())*1000) );
        deviceDTO.setValue(values.getValue());
        deviceDTO.setCodeError(values.getCodeError());
        deviceDTO.setFormat(values.getFormat());

        return deviceDTO;
    }
}
