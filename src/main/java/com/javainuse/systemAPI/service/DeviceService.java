package com.javainuse.systemAPI.service;

import com.javainuse.systemAPI.dao.parameterDAO;
import com.javainuse.systemClient.model.PLC;
import com.javainuse.systemClient.model.Sensor;
import com.javainuse.systemClient.model.deviceList;
import com.javainuse.systemClient.model.dto.ParametersDTO;
import com.javainuse.systemAPI.repository.DeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeviceService {

    @Autowired
    DeviceRepository deviceRepository;

    public List<parameterDAO> getLastData() {
        return deviceRepository.getLastData();
    }
    public List<parameterDAO> getLastDataFailure() {
        return deviceRepository.getLastDataFailure();
    }
    public PLC getStatusPLC() {
        return deviceRepository.getStatusPLC();
    }
    public List<deviceList> getDeviceList() {
        return deviceRepository.getDeviceList();
    }
}
