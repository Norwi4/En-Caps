package com.javainuse.systemAPI.controller;

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

    @GetMapping("/device")
    public ResponseEntity<List<ParametersDTO>> getAllTutorials() {
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

}
