package com.javainuse.server.async;

import com.javainuse.server.model.CompaniesModel;
import com.javainuse.server.model.ParametersModel;
import com.javainuse.server.model.UserToken;
import com.javainuse.server.model.lastData.ParametersDTO;
import com.javainuse.server.model.parameters.Parameters;
import com.javainuse.server.model.parameters.Values;
import com.javainuse.server.repository.auth.AuthRepository;
import com.javainuse.server.repository.dataCollection.DataRepository;

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

import java.util.stream.Collectors;

import static com.javainuse.server.OwenUrl.last_data;


/**
 * Методы собирающие информацию о датчиках, авариях и событиях раз в минуту
 */
@Component
public class GetParametersFromPlc {

    DataRepository dataRepository;
    AuthRepository authRepository;
    RestTemplate restTemplate;

    GetParametersFromPlc(DataRepository dataRepository, RestTemplate restTemplate, AuthRepository authRepository) {
        this.dataRepository = dataRepository;
        this.restTemplate = restTemplate;
        this.authRepository = authRepository;
    }

    /**
     * Метод собирающий показатели датчиков из OwenCloud
     */
    @Scheduled(fixedRate = 60000)
    public void getDeviceInfoFromOwen() {
        // Получаем список активных пользователей
        if (!dataRepository.getTokenList().isEmpty()){
            for (UserToken userToken : dataRepository.getTokenList()) {
                HttpHeaders headers = new HttpHeaders();
                headers.add("Authorization", userToken.getToken());
                // Получаем список компаний пользователя
                for (CompaniesModel company : authRepository.getCompanies(userToken.getUser_id())) {
                    String res = dataRepository.getDeviceCompany(company.getCompany_id()).stream().
                            map(i -> i.getDevice_id()).
                            collect(Collectors.joining(", "));
                    ResponseEntity<List<Parameters>> result = restTemplate.exchange(
                            last_data.toString(),
                            HttpMethod.POST,
                            new HttpEntity<>("{\"ids\":[" + res + "]}", headers),
                            new ParameterizedTypeReference<List<Parameters>>() {}
                    );

                    List<Parameters> rates = result.getBody();
                    ArrayList<ParametersDTO> deviceDTOS = new ArrayList<>();

                    for (Parameters device : rates) {
                        deviceDTOS.add(deviceDTO(device));
                    }

                    if (deviceDTOS != null) {
                        dataRepository.saveDeviceParameter(deviceDTOS,userToken.getUser_id(), company.getCompany_id());
                    }
                }
            }
        }
    }

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


    /**
     * Запрос последних данных котельной пользователя из базы
     * @param user_id пользователь нашей системы
     * @param company_id компания пользователя
     * @return список параметров определенной компании пользователя
     */
    public ParametersModel getLastParamByCompany(String user_id, String company_id) {
        ParametersModel asds = new ParametersModel();
        return asds ;
    }

    /**
     * Запрос последних данных котельной пользователя из базы
     * @param user_id пользователь нашей системы
     * @return список параметров его компаний
     */
    public ParametersModel getLastParam(String user_id) {
        ParametersModel asds = new ParametersModel();
        return asds ;
    }

}
