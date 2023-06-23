package JdbcTemplate.SpringSecurityJWT.OwenCore.async;

import JdbcTemplate.SpringSecurityJWT.OwenCore.async.dto.DevToDTO;
import JdbcTemplate.SpringSecurityJWT.OwenCore.async.dto.ParametersDTO;
import JdbcTemplate.SpringSecurityJWT.OwenCore.async.model.DeviceTokenModel;
import JdbcTemplate.SpringSecurityJWT.OwenCore.async.model.ParametersResponse;
import JdbcTemplate.SpringSecurityJWT.OwenCore.model.ParamsDictionaryModel;
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
import java.util.Optional;
import java.util.stream.Collectors;

import static JdbcTemplate.SpringSecurityJWT.OwenCore.vo.OwenUrl.last_data;

@Component
public class Async {

    @Autowired
    RestTemplate restTemplate;
    @Autowired
    AsyncService asyncService;
    @Autowired
    private DevToDTO devToDTO;



    /**
     * Получение значений параметров раз в 1 минуту
     */
    @Scheduled(fixedRate = 60000)
    public void getObjectParam() {
        List<DeviceTokenModel> deviceList = asyncService.deviceTokenModels();
        if (deviceList != null) {
            for (DeviceTokenModel deviceTokenModel : deviceList) {
                HttpHeaders headers = new HttpHeaders();
                headers.add("Authorization", "Bearer " + deviceTokenModel.getToken().toString());

                // Получили список параметров определенного объекта
                List<ParamsDictionaryModel> paramsDictionaryList = asyncService.paramsDictionaryList(deviceTokenModel.getDevice_id());
                StringBuilder sb = new StringBuilder();
                for (ParamsDictionaryModel model : paramsDictionaryList) {
                    sb.append(model.getParam_id());
                    sb.append(",");
                }
                String res = sb.toString();
                if (res == null || res.length() == 0) {
                } else {

                    res = res.substring(0, res.length() - 1);
                    String request = "{ \"ids\" : [" + res + "]}"; // Список id датчиков для запроса

                    ResponseEntity<List<ParametersResponse>> result = restTemplate.exchange(
                            "https://api.owencloud.ru/v1/parameters/last-data",
                            HttpMethod.POST,
                            new HttpEntity<>(request, headers),
                            new ParameterizedTypeReference<List<ParametersResponse>>() {
                            }
                    );
                    List<ParametersResponse> rates = result.getBody();
                    ArrayList<ParametersDTO> deviceDTOS = new ArrayList<>();

                    for (ParametersResponse device : rates) {
                        deviceDTOS.add(devToDTO.deviceDTO(device, deviceTokenModel.getDevice_id()));
                    }

                    if (deviceDTOS != null) {
                        asyncService.saveObjectParam(deviceDTOS);
                    }
                }
            }

        }
    }


}
