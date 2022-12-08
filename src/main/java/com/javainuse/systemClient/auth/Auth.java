package com.javainuse.systemClient.auth;

import com.javainuse.systemClient.model.*;
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
@Component
public class Auth {



    @Autowired
    RestTemplate restTemplate;

    @Autowired
    DeviceRepositoryPLC deviceRepositoryPLC;

    @Autowired
    PLKrepo plKrepo;
    private static final String AUTHENTICATION_URL = "https://api.owencloud.ru/v1/auth/open";
    private static final String HELLO_URL = "https://api.owencloud.ru/v1/device/index";
    private static final String DEVICE_INFO = "https://api.owencloud.ru/v1/device/309227";

    private static final String last_data = "https://api.owencloud.ru/v1/parameters/last-data";
    HttpHeaders headers = new HttpHeaders();

    private static final String CRON = "* * */1 * * *";
    @Autowired
    private DevToDTO devToDTO;


    /**
     * Метод авторизации
     */
    public void auth() {

        try {

            // авторизация
            User authRequest = new User();
            authRequest.setLogin("info@climatik.su");
            authRequest.setPassword("vtntkm");
            //ResponseToken authResponse = restTemplate.postForObject(AUTHENTICATION_URL, authRequest,  ResponseToken.class);
            ResponseEntity<ResponseToken> authResponse = restTemplate.exchange(
                    AUTHENTICATION_URL,
                    HttpMethod.POST,
                    new HttpEntity<>( authRequest ),
                    new ParameterizedTypeReference<ResponseToken>(){}
            );
            System.out.println(authResponse.getBody().getToken());
            for (ChildCompanies companies : authResponse.getBody().getChildCompanies()) {
                System.out.println(companies.getCompany_id());
                System.out.println(companies.getName());
            }


            headers.add("Authorization", "Bearer " + authResponse.getBody().getToken());

        } catch (Exception ex) {
            System.out.println("Вы не авторизованы");

        }
    }

    /**
     * Метод получения подключенных датчиков к плк
     */
    // TODO: 08.12.2022 Сделать его адаптивным, а не строгим
    public void getDeviceInfo() {
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

    /**
     * Метод получения информации об ПЛК
     */
    @Scheduled(fixedRate = 60000)
    public void get() {
        if (!headers.isEmpty()) {

            ResponseEntity<List<PLC>> result = restTemplate.exchange(
                    HELLO_URL,
                    HttpMethod.POST,
                    new HttpEntity<>("{\"company_id\" : 210742 }" ,headers ),
                    new ParameterizedTypeReference<List<PLC>>() {}
            );
            List<PLC> rates = result.getBody();
            plKrepo.save(rates);
            error(rates);
        }
    }

    public void error(List<PLC> PLCS) {
        for (PLC PLC : PLCS) {
            if (PLC.getStatus().equals("online")) {
                System.out.println("ПЛК "+ PLC.getId() + " - онлайн");
            }
        }
    }

    /**
     * Метод получения значений с датчиков
     */
    // TODO: 08.12.2022 Сделать его адаптивным, а не строгим 
    @Scheduled(fixedRate = 60000)
    public void getValueFromDevice() {

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
                            "14784365]}", headers ),
                    new ParameterizedTypeReference<List<Parameters>>() {}
            );

            List<Parameters> rates = result.getBody();

            ArrayList<ParametersDTO> deviceDTOS = new ArrayList<>();

            for (Parameters device : rates) {
                deviceDTOS.add(devToDTO.deviceDTO(device));
            }
            if (deviceDTOS != null) {
                deviceRepositoryPLC.save(deviceDTOS);
            }

        } else {
            auth();
            getDeviceInfo();
        }
    }
}
