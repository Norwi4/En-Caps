package com.javainuse.server.async;

import com.javainuse.generalModel.OwenUser;
import com.javainuse.generalModel.PlcCompany;
import com.javainuse.generalModel.User;
import com.javainuse.server.OwenUrl;
import com.javainuse.server.model.AuthResponse;
import com.javainuse.server.model.ChildCompanies;
import com.javainuse.server.model.CompaniesModel;
import com.javainuse.server.model.Device;
import com.javainuse.server.repository.auth.AuthRepository;
import com.javainuse.server.repository.dataCollection.DataRepository;
import com.javainuse.server.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.validation.constraints.NotNull;
import java.util.*;

@Component
public class Auth {


    RestTemplate restTemplate;
    AuthRepository authRepository;
    AuthService authService;
    DataRepository dataRepository;
    HttpHeaders headers = new HttpHeaders();

    public Auth(RestTemplate restTemplate, AuthRepository authRepository, AuthService authService, DataRepository dataRepository) {
        this.restTemplate = restTemplate;
        this.authRepository = authRepository;
        this.authService = authService;
        this.dataRepository = dataRepository;
    }

    /**
     * Метод авторизации пользователя в системе OwenCloud
     *
     * @param user пользователь системы EnCaps System
     */
    public void authOwen(OwenUser user) {
        try {
            class UserAuthParam {
                @NotNull
                private String login;
                @NotNull
                private String password;

                public String getLogin() {
                    return login;
                }

                public void setLogin(String login) {
                    this.login = login;
                }

                public String getPassword() {
                    return password;
                }

                public void setPassword(String password) {
                    this.password = password;
                }
            }

            UserAuthParam authParam = new UserAuthParam();
            authParam.login = user.getLogin();
            authParam.password = user.getPassword();

            // Сделали запрос к системе OwenCloud, получили токен и список дочерних компаний
            ResponseEntity<AuthResponse> authResponse = restTemplate.exchange(
                    OwenUrl.auth.toString(),
                    HttpMethod.POST,
                    new HttpEntity<>(authParam),
                    new ParameterizedTypeReference<AuthResponse>() {
                    }
            );
            // Сохраняем токен с привязкой к пользователю нашей системы ( с проверкой токена)
            authService.saveToken(user, "Bearer " + authResponse.getBody().getToken());
            headers.clear();
            headers.add("Authorization", "Bearer " + authResponse.getBody().getToken());

            getCompanyPlc(user, authResponse.getBody().getCompanies(), headers);
            getDevicePlc(user, headers);

        } catch (Exception ex) {
            System.out.println(ex);
        }

    }

    /**
     * Метод, который преобразует ответ от сервера в список котельных - компаний
     *
     * @param user      пользователь системы
     * @param companies список доступных компаний
     */
    public void getCompanyPlc(OwenUser user, List<ChildCompanies> companies, HttpHeaders headers) {
        List<PlcCompany> companyList = new ArrayList<>();
        // Получаем ПЛК главного аккаунта
        ResponseEntity<List<PlcCompany>> result = restTemplate.exchange(
                OwenUrl.device_list.toString(),
                HttpMethod.POST,
                new HttpEntity<>(headers),
                new ParameterizedTypeReference<List<PlcCompany>>() {
                }
        );
        companyList.addAll(result.getBody());

        // Получаем дочерние компании подключенные к аккаунту
        for (ChildCompanies childCompanies : companies) {
            ResponseEntity<List<PlcCompany>> resultCompanies = restTemplate.exchange(
                    OwenUrl.device_list.toString(),
                    HttpMethod.POST,
                    new HttpEntity<>("{\"company_id\" : " + childCompanies.getId() + " }", headers),
                    new ParameterizedTypeReference<List<PlcCompany>>() {
                    }
            );
            companyList.addAll(resultCompanies.getBody());
        }
        // Сохраняем плк в таблицу компаний
        authRepository.saveCompanies(companyList, user.getUser_id());
    }

    /**
     * Метод получения информации о приборах, подключенных к ПЛК каждой компании
     * Сохранение их в базе
     */
    public void getDevicePlc(OwenUser user, HttpHeaders headers) {
        List<CompaniesModel> companyList = authRepository.getCompanies(user.getUser_id());
        for (CompaniesModel company : companyList) {
            String url = OwenUrl.device_in_plc.toString() + company.getCompany_id();
            try {
                ResponseEntity<Device> res = restTemplate.exchange(
                        url ,
                        HttpMethod.POST,
                        new HttpEntity<>(headers),
                        new ParameterizedTypeReference<Device>() {
                        }
                );
                Device device = res.getBody();
                dataRepository.saveSensor(device, user);
            } catch (Exception ex) {
                System.out.println(ex);
            }
        }


    }
}
