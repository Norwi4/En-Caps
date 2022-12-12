package com.javainuse.systemClient.plcParameters;

import com.javainuse.systemClient.model.Device;
import com.javainuse.systemClient.model.PLC;
import com.javainuse.systemClient.model.Parameters;
import com.javainuse.systemClient.model.dto.DevToDTO;
import com.javainuse.systemClient.model.dto.ParametersDTO;
import com.javainuse.systemClient.repository.DeviceRepositoryPLC;
import com.javainuse.systemClient.repository.PLKrepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;


public class plcInfo {
    private static final String HELLO_URL = "https://api.owencloud.ru/v1/device/index";
    private static final String DEVICE_INFO = "https://api.owencloud.ru/v1/device/309227";
    private static final String last_data = "https://api.owencloud.ru/v1/parameters/last-data";

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    PLKrepo plKrepo;
    @Autowired
    DeviceRepositoryPLC deviceRepositoryPLC;
    @Autowired
    private DevToDTO devToDTO;

    /**
     * Метод получения информации об ПЛК
     */
    /*public void getPlcInfo(HttpHeaders headers) {
        if (!headers.isEmpty()) {

            ResponseEntity<List<PLC>> result = restTemplate.exchange(
                    HELLO_URL,
                    HttpMethod.POST,
                    new HttpEntity<>("{\"company_id\" : 210742 }", headers),
                    new ParameterizedTypeReference<List<PLC>>() {
                    }
            );
            List<PLC> rates = result.getBody();
            plKrepo.save(rates);

        }
    }

    /**
     * Метод получения подключенных датчиков к плк
     */
    // TODO: 08.12.2022 Сделать его адаптивным, а не строгим
    /*public void getDeviceInfo(HttpHeaders headers) {
        if (!headers.isEmpty()) {
            ResponseEntity<Device> res = restTemplate.exchange(
                    DEVICE_INFO,
                    HttpMethod.POST,
                    new HttpEntity<>( headers ),
                    new ParameterizedTypeReference<Device>(){}
            );
            Device device = res.getBody();
            deviceRepositoryPLC.saveSensor(device);
        }

    }

    @Scheduled(fixedRate = 60000)
    public void getValueFromDevice(HttpHeaders headers) {

        if (!headers.isEmpty()) {
            ResponseEntity<List<Parameters>> result = restTemplate.exchange(
                    last_data,
                    HttpMethod.POST,
                    new HttpEntity<>("{\"ids\":[14783555,\n" +
                            "14783591,\n" +
                            "14783441,\n" +
                            "14783435,\n" +
                            "14783465,\n" +
                            "14783573,\n" +
                            "14784367,\n" +
                            "14783525,\n" +
                            "14783501,\n" +
                            "14783639,\n" +
                            "14783603,\n" +
                            "14783567,\n" +
                            "14783621,\n" +
                            "14784365]}", headers),
                    new ParameterizedTypeReference<List<Parameters>>() {
                    }
            );

            saveValue(result.getBody());
        }
    }

    private void saveValue(List<Parameters> parameters) {
        ArrayList<ParametersDTO> deviceDTOS = new ArrayList<>();

        for (Parameters device : parameters) {
            deviceDTOS.add(devToDTO.deviceDTO(device));
        }
        if (deviceDTOS != null) {
            deviceRepositoryPLC.save(deviceDTOS);
        }
    } */
}
