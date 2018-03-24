package com.ropor.microservice.lamaria.backend.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;


@RestController
public class DatabaseInfo {
    @Value("${spring.datasource.driver-class-name}")
    private String driver;
    @Value("${spring.datasource.url}")
    private String url;
    @Value("${spring.datasource.username}")
    private String user;
    @Value("${spring.datasource.password}")
    private String password;

    @RequestMapping(value = "/db/info", method= RequestMethod.GET)
    public ResponseEntity<Map<String, String>> getDatabaseInfo(){
        Map<String, String> results = new HashMap<>();
        results.put("driver",driver);
        results.put("url",url);
        results.put("user",user);
        results.put("password",password);
        return new ResponseEntity<>(results, HttpStatus.OK);
    }
}
