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
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.*;

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
    private static final String DEVICE_INFO = "https://api.owencloud.ru/v1/device/";
    private static final String last_data = "https://api.owencloud.ru/v1/parameters/last-data";
    private static final String EVENT_LIST = "https://api.owencloud.ru/v1/company/event-registration";

    HttpHeaders headers = new HttpHeaders();

    @Autowired
    private DevToDTO devToDTO;


    /**
     * Метод авторизации
     * @param user
     */
    public void auth(User user) {

        try {

            // авторизация
            User authRequest = new User();

            authRequest.setLogin(user.getLogin());
            authRequest.setPassword(user.getPassword());

            ResponseEntity<ResponseToken> authResponse = restTemplate.exchange(
                    AUTHENTICATION_URL,
                    HttpMethod.POST,
                    new HttpEntity<>( authRequest ),
                    new ParameterizedTypeReference<ResponseToken>(){}
            );

            System.out.println(authResponse.getBody().getToken());

            plKrepo.saveChildCompanies(authResponse.getBody().getChildCompanies()); // сохранение подключенных компаний

            headers.add("Authorization", "Bearer " + authResponse.getBody().getToken());
            getDeviceInfo();
        } catch (Exception ex) {
            System.out.println(ex);

        }
    }

    /**
     * Получение информации об подлюченных ПЛК к аккаутну
     *
     * company_id = 0, если ПЛК подключены к головному аккаунту
     */
    @Scheduled(fixedRate = 60000)
    public void get() {
        if (!headers.isEmpty()) {

            ResponseEntity<List<PLC>> result = restTemplate.exchange(
                    HELLO_URL,
                    HttpMethod.POST,
                    new HttpEntity<>( headers ),
                    new ParameterizedTypeReference<List<PLC>>() {}
            );
            List<PLC> rates = result.getBody();
            plKrepo.save(rates, "0");
            error(rates);

            for (ChildCompanies childCompanies : plKrepo.getChildCompanies()) {
                ResponseEntity<List<PLC>> resultCh = restTemplate.exchange(
                        HELLO_URL,
                        HttpMethod.POST,
                        new HttpEntity<>("{\"company_id\" : " + childCompanies.getCompany_id() +" }" ,headers ),
                        new ParameterizedTypeReference<List<PLC>>() {}
                );
                List<PLC> ratesCh = resultCh.getBody();
                plKrepo.save(ratesCh, childCompanies.getCompany_id());
                error(ratesCh);
            }

        }
    }

    public void error(List<PLC> PLCS) {
        for (PLC PLC : PLCS) {
            if (PLC.getStatus().equals("online")) {
                System.out.println("ПЛК "+ PLC.getDevice_id() + " - онлайн");
            }
        }
    }

    /**
     * Получение информации об подлюченных датчиках к ПЛК
     */
    public void getDeviceInfo() {
        if (!headers.isEmpty()) {
            try {
                ResponseEntity<Device> res = restTemplate.exchange(
                        DEVICE_INFO + "309227",
                        HttpMethod.POST,
                        new HttpEntity<>(headers),
                        new ParameterizedTypeReference<Device>() {
                        }
                );
                Device device = res.getBody();
                deviceRepositoryPLC.saveSensor(device);
            } catch (Exception ex) {
                System.out.println(ex);
            }
        }
    }

    /**
     * Показатели датчиков
     */
    @Scheduled(fixedRate = 60000)
    public void getValueFromDevice() {

        if (!headers.isEmpty()) {
            ResponseEntity<List<Parameters>> result = restTemplate.exchange(
                    last_data,
                    HttpMethod.POST,
                    new HttpEntity<>("{\"ids\":[14783555,\n" +
                            "14783591,\n" +
                            "14783441,\n" +
                            "15270225,\n" +
                            "14783435,\n" +
                            "14783465,\n" +
                            "14783573,\n" +
                            "15448429,\n" +
                            "14784367,\n" +
                            "15448423,\n" +
                            "15448447,\n" +
                            "15448411,\n" +
                            "15448417,\n" +
                            "15448399,\n" +
                            "15448435,\n" +
                            "15448405,\n" +
                            "15448393,\n" +
                            "15448441,\n" +
                            "14783525,\n" +
                            "15270213,\n" +
                            "15270219,\n" +
                            "15270231,\n" +
                            "15270237,\n" +
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

        }
    }

    /**
     * Показатели аварий
     */
    @Scheduled(fixedRate = 60000)
    public void getValueFromFailure() {

        if (!headers.isEmpty()) {
            ResponseEntity<List<Parameters>> result = restTemplate.exchange(
                    last_data,
                    HttpMethod.POST,
                    new HttpEntity<>("{\"ids\":[15270243,\n" +
                            "14783453,\n" +
                            "14783627,\n" +
                            "14783507,\n" +
                            "14783609,\n" +
                            "14965926,\n" +
                            "14965932,\n" +
                            "14783477,\n" +
                            "14783537,\n" +
                            "14783429,\n" +
                            "14783543,\n" +
                            "14783471,\n" +
                            "14783585,\n" +
                            "14783513,\n" +
                            "14783459,\n" +
                            "14783483,\n" +
                            "14783549,\n" +
                            "14783519,\n" +
                            "14783489,\n" +
                            "14783633,\n" +
                            "14872886]}", headers ),
                    new ParameterizedTypeReference<List<Parameters>>() {}
            );

            List<Parameters> rates = result.getBody();

            ArrayList<ParametersDTO> deviceDTOS = new ArrayList<>();

            for (Parameters device : rates) {
                deviceDTOS.add(devToDTO.deviceDTO(device));
            }
            if (deviceDTOS != null) {
                deviceRepositoryPLC.saveFailure(deviceDTOS);
            }

        }
    }

    /**
     * Получение списка событий/аварий
     * @return
     */
    public List<Events> eventsList(String body) {

        ResponseEntity<List<Events>> result = restTemplate.exchange(
                EVENT_LIST,
                HttpMethod.POST,
                new HttpEntity<>(body, headers),
                new ParameterizedTypeReference<List<Events>>() {
                }
        );

        List<Events> rates = result.getBody();
        try {
            for (Events events : rates) {
                if (events.getStart_dt() !=null) {
                    events.setStart_dt(converterTime(events.getStart_dt()));
                }
                if (events.getEnd_dt() != null) {
                    events.setEnd_dt(converterTime(events.getEnd_dt()));
                }
                if (events.getRead_dt() != null) {
                    events.setRead_dt(converterTime(events.getRead_dt()));
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        Collections.reverse(rates);
        return rates;
    }

    public String converterTime(String time) {
        long unixSeconds = Long.valueOf(time).longValue();
        Date date = new java.util.Date(unixSeconds*1000L);
        SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = sdf.format(date);
        return formattedDate;
    }

}
