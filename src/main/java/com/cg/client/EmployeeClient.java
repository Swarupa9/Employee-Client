package com.cg.client;

import com.cg.bo.ClientBO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class EmployeeClient {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeClient.class);

    private final RestTemplate restTemplate;
    private final String baseUrl;
//    @Value("${app1.url}")
//    private String employeeServiceUrl;
    
    @Autowired
    public EmployeeClient(RestTemplate restTemplate, @Value("${app1.url}") String baseUrl ) {
        this.restTemplate = restTemplate;
        this.baseUrl = baseUrl;
        logger.info("Base URL loaded from configuration: {}", baseUrl); 
    }

    public List<ClientBO> getAllEmployees() {
        logger.info("Calling getAllEmployees from EmployeeClient");
        String url = baseUrl; //"http://localhost:8080/emp";
        ResponseEntity<List<ClientBO>> response = restTemplate.exchange(
                url, HttpMethod.GET, null, 
                new ParameterizedTypeReference<List<ClientBO>>() {}
        );
        return response.getBody();
    }

    public ClientBO getEmployeeById(Long id) {
        logger.info("Calling getEmployeeById from EmployeeClient");
        //String url = "http://localhost:8080/emp/" + id;
        String url = baseUrl + "/" + id; 
        ResponseEntity<ClientBO> response = restTemplate.exchange(
                url, HttpMethod.GET, null, ClientBO.class);
        return response.getBody();
    }

    public ClientBO createEmployee(ClientBO clientBO) {
        logger.info("Calling createEmployee from EmployeeClient");
        //String url = "http://localhost:8080/emp";
        String url = baseUrl;
        ResponseEntity<ClientBO> response = restTemplate.postForEntity(url, clientBO, ClientBO.class);
        return response.getBody();
    }
    
}


