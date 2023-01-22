package com.javainuse.systemClient.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.javainuse.systemClient.auth.Auth;
import com.javainuse.systemClient.model.Events;
import com.javainuse.systemClient.model.ParamGraph;
import com.javainuse.systemClient.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TestController {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    Auth auth;

    private static final String FAILURE = "{\n" +
            "    \"event_type\":0,\n" +
            "    \"is_read\" : -1,\n" +
            "    \"is_critical\": 1,\n" + // 1 - аварии
            "    \"is_end\": -1\n" +
            "}";
    private static final String EVENT = "{\n" +
            "    \"event_type\":0,\n" +
            "    \"is_read\" : -1,\n" +
            "    \"is_critical\": 0,\n" + // 0 - события
            "    \"is_end\": -1\n" +
            "}";

    @RequestMapping(value = "/getResponse", method = RequestMethod.GET)
    public String getResponse() throws JsonProcessingException {

        String response = null;


        return response;

    }

    /**
     * Авторизация на сервере ОвенКлауд
     * @param user логин и пароль
     * @return сообщение об авторизации
     */
    @PostMapping("/login")
    public ResponseEntity<String> createTutorial(@RequestBody @JsonInclude(JsonInclude.Include.NON_NULL) User user) {
        try {

            auth.auth(user);
            return new ResponseEntity<>("Вы авторизированы", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Получение событий
     * @return список событий
     */
    @GetMapping("/events")
    public ResponseEntity<List<Events>> getEvents() {
        try {

            List<Events> eventsList = auth.eventsList(EVENT);
            return new ResponseEntity<>(eventsList, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Получение аварий
     * @return список аварий
     */
    @GetMapping("/failure")
    public ResponseEntity<List<Events>> getFailure() {
        try {

            List<Events> eventsList = auth.eventsList(FAILURE);
            return new ResponseEntity<>(eventsList, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/parameters/data")
    public ResponseEntity<List<ParamGraph>> getParamForGraph(@RequestBody String string){
        try{
            List<ParamGraph> param = auth.graphList(string);

            if (param == null) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(param, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}