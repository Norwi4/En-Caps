package com.javainuse.systemAPI.service;

import com.javainuse.systemClient.model.dto.ParametersDTO;
import com.javainuse.systemAPI.repository.DeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeviceService {

    @Autowired
    DeviceRepository deviceRepository;

    public List<ParametersDTO> getLastData() {
        return deviceRepository.getLastData();
    }


}
