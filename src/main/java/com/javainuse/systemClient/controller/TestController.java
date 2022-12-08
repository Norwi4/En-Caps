package com.javainuse.systemClient.controller;

import com.javainuse.systemClient.auth.Auth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;

@RestController
public class TestController {

    @Autowired
    RestTemplate restTemplate;
    private static final String AUTHENTICATION_URL = "https://api.owencloud.ru/v1/auth/open";
    private static final String HELLO_URL = "https://api.owencloud.ru/v1/device/index";

    @RequestMapping(value = "/getResponse", method = RequestMethod.GET)
    public String getResponse() throws JsonProcessingException {

        String response = null;

        Auth auth = new Auth();
        auth.auth();
        return response;

    }



}