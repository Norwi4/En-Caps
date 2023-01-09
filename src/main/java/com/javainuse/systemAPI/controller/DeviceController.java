package com.javainuse.systemAPI.controller;

import com.javainuse.systemClient.model.PLC;
import com.javainuse.systemClient.model.dto.ParametersDTO;
import com.javainuse.systemAPI.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class DeviceController {

    @Autowired
    DeviceService deviceService;

    /**
     * Получение последних значений датчиков
     * @return
     */
    @GetMapping("/last-data-sensor")
    public ResponseEntity<List<ParametersDTO>> getLastDataSensor() {
        try {
            List<ParametersDTO> devices = deviceService.getLastData();

            if (devices.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(devices, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Получение последних значений аварий
     * @return
     */
    @GetMapping("/last-data-failure")
    public ResponseEntity<List<ParametersDTO>> getLastDataFailure() {
        try {
            List<ParametersDTO> devices = deviceService.getLastDataFailure();

            if (devices.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(devices, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Получение статуса плк
     * @return
     */
    @GetMapping("/plc-status")
    public ResponseEntity<PLC> getStatusPLC() {
        try {
            PLC plc = deviceService.getStatusPLC();

            if (plc == null) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(plc, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
