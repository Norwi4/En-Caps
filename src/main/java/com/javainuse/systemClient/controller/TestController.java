package com.javainuse.controller;

import com.javainuse.auth.Auth;
import com.javainuse.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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