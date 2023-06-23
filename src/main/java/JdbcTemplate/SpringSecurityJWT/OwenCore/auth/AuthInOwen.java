package JdbcTemplate.SpringSecurityJWT.OwenCore.auth;

import JdbcTemplate.SpringSecurityJWT.OwenCore.model.OwenRequestLogin;
import JdbcTemplate.SpringSecurityJWT.OwenCore.service.AuthService;
import JdbcTemplate.SpringSecurityJWT.OwenCore.service.OldService;
import JdbcTemplate.SpringSecurityJWT.OwenCore.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Component
public class AuthInOwen {



    RestTemplate restTemplate;
    HttpHeaders headers = new HttpHeaders();
    @Autowired
    OldService oldService;
    @Autowired
    AuthService authService;

    public AuthInOwen(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    public void authOwen(OwenRequestLogin owenRequestLogin) {
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
            authParam.login = owenRequestLogin.getUsername();
            authParam.password = owenRequestLogin.getUserpwd();

            // Сделали запрос к системе OwenCloud, получили токен и список дочерних компаний
            ResponseEntity<AuthResponse> authResponse = restTemplate.exchange(
                    OwenUrl.auth.toString(),
                    HttpMethod.POST,
                    new HttpEntity<>(authParam),
                    new ParameterizedTypeReference<AuthResponse>() {
                    }
            );
            // Сохраняем токен с привязкой к пользователю нашей системы ( с проверкой токена)
            //authService.saveToken(user, "Bearer " + authResponse.getBody().getToken());
            headers.clear();
            headers.add("Authorization", "Bearer " + authResponse.getBody().getToken());
            String token = authResponse.getBody().getToken();
            getCompanyPlc(authResponse.getBody().getCompanies(), headers, owenRequestLogin.getCompanyName(), token);
            getDevicePlc(owenRequestLogin.getCompanyName(), headers);

        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    /**
     * Метод, который преобразует ответ от сервера в список обьектов (котельных)
     * И сохраняет их как коомпании в таблицу object
     *
     * @param companies список доступных компаний
     */
    public void getCompanyPlc(List<ChildCompanies> companies, HttpHeaders headers, String companyName, String token) {
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
        oldService.saveCompanies(companyList, companyName);
        authService.saveToken(companyList, token);

    }

    /**
     * Метод получения информации о приборах, подключенных к ПЛК каждой компании
     * Сохранение их в базе
     */
    public void getDevicePlc(String companyName, HttpHeaders headers) {
        List<CompaniesModel> companyList = oldService.getCompanies(companyName);
        for (CompaniesModel company : companyList) {
            String url = OwenUrl.device_in_plc.toString() + company.getDevice_id();
            try {
                ResponseEntity<Device> res = restTemplate.exchange(
                        url ,
                        HttpMethod.POST,
                        new HttpEntity<>(headers),
                        new ParameterizedTypeReference<Device>() {
                        }
                );
                Device device = res.getBody();
                oldService.saveSensor(device);
            } catch (Exception ex) {
                System.out.println(ex);
            }
        }
    }


}
