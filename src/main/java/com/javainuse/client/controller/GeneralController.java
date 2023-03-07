package com.javainuse.client.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.javainuse.generalModel.OwenUser;
import com.javainuse.server.async.Auth;
import com.javainuse.server.model.ParametersModel;
import com.javainuse.server.service.DataService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class GeneralController {

    private final Auth auth;
    private final DataService dataService;

    public GeneralController(Auth auth, DataService dataService) {
        this.auth = auth;
        this.dataService = dataService;
    }

    @PostMapping("/login")
    public ResponseEntity<String> createTutorial(@RequestBody @JsonInclude(JsonInclude.Include.NON_NULL) OwenUser user) {
        try {
            auth.authOwen(user);
            return new ResponseEntity<>("Вы авторизированы", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("get-last-data")
    public ResponseEntity<List<ParametersModel>> getLastData(@RequestParam("user_id") String user_id) {
        try {
            if (!dataService.getLastData(user_id).isEmpty()) {
                return new ResponseEntity<>(dataService.getLastData(user_id), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }





}
