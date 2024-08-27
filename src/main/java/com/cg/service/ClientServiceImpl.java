package com.cg.service;

import com.cg.bo.ClientBO;
import com.cg.client.EmployeeClient;
import com.cg.client.EmployeeFeignClient;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

@Service
public class ClientServiceImpl implements ClientService {

    private static final Logger logger = LoggerFactory.getLogger(ClientServiceImpl.class);

    private final EmployeeClient employeeClient;
    private final EmployeeFeignClient employeeFeignClient;

    @Autowired
    public ClientServiceImpl(EmployeeClient employeeClient, EmployeeFeignClient employeeFeignClient) {
        this.employeeClient = employeeClient;
        this.employeeFeignClient = employeeFeignClient;
    }

    //Using Rest template
    @Override
    @CircuitBreaker(name = "employeeClient", fallbackMethod = "getAllClientsFromRestFallback")
    public List<ClientBO> getAllClientsFromRest() {
        logger.info("Fetching all employees using RestTemplate");
        return employeeClient.getAllEmployees();
    }
    
    @Override
    @CircuitBreaker(name = "employeeClient", fallbackMethod = "getClientByIdFromRestFallback")
    public ClientBO getClientByIdFromRest(Long id) {
        logger.info("Fetching employee by id using RestTemplate");
        return employeeClient.getEmployeeById(id);
    }

    @Override
    @CircuitBreaker(name = "employeeClient", fallbackMethod = "createClientFromRestFallback")
    public ClientBO createClientFromRest(ClientBO clientBO) {
        logger.info("Creating employee using RestTemplate");
        return employeeClient.createEmployee(clientBO);
    }

    // Using Feign client
    @Override
    @CircuitBreaker(name = "employeeFeignClient", fallbackMethod = "getAllClientsFromFeignFallback")
    public List<ClientBO> getAllClientsFromFeign() {
        logger.info("Fetching all employees using FeignClient");
        return employeeFeignClient.getAllEmployees();
    }
   
    @Override
    @CircuitBreaker(name = "employeeFeignClient", fallbackMethod = "getClientByIdFromFeignFallback")
    public ClientBO getClientByIdFromFeign(Long id) {
        logger.info("Fetching employee by id using FeignClient");
        return employeeFeignClient.getEmployeeById(id);
    }

    @Override
    @CircuitBreaker(name = "employeeFeignClient", fallbackMethod = "createClientFromFeignFallback")
    public ClientBO createClientFromFeign(ClientBO clientBO) {
        logger.info("Creating employee using FeignClient");
        return employeeFeignClient.createEmployee(clientBO);
    }

    // Fallback Methods
    //For Rest template
    public List<ClientBO> getAllClientsFromRestFallback(Throwable t) {
    	logger.error("Fallback method called for getAllClientsFromRest due to: {}", t.toString());
        return List.of(createFallbackClient(-1L, "Service Unavailable", "The employee data could not be retrieved at this time"));
    }
    
    public ClientBO getClientByIdFromRestFallback(Long id, Throwable t) {
    	logger.error("Fallback method called for getClientByIdFromRest due to: {}", t.getMessage());        
        return createFallbackClient(id, "Service Unavailable", "The employee data could not be retrieved at this time");
        
    }

    public ClientBO createClientFromRestFallback(ClientBO clientBO, Throwable t) {
    	logger.error("Fallback method called for createClientFromRest due to: {}", t.getMessage());
        return createFallbackClient(-1L, "Creation Failed", "Service currently unavailable");
    }

  //For Feign client
    public List<ClientBO> getAllClientsFromFeignFallback(Throwable t) {
    	logger.error("Fallback method called for getAllClientsFromFeign due to: {}", t.toString());
    	return List.of(createFallbackClient(-1L, "Service Unavailable", "The employee data could not be retrieved at this time"));
    }
    
    public ClientBO getClientByIdFromFeignFallback(Long id, Throwable t) {
    	logger.error("Fallback method called for getClientByIdFromFeign due to: {}", t.getMessage());
        return createFallbackClient(id, "Service Unavailable", "The employee data could not be retrieved at this time");      
    }

    public ClientBO createClientFromFeignFallback(ClientBO clientBO, Throwable t) {
    	logger.error("Fallback method called for createClientFromFeign due to: {}", t.getMessage());
        return createFallbackClient(-1L, "Creation Failed", "Service currently unavailable");
    }
    
    private ClientBO createFallbackClient(Long id, String name, String email) {
    	// Create a ClientBO object with a message indicating fallback
        ClientBO fallbackClient = new ClientBO();
        fallbackClient.setId(id);
        fallbackClient.setEmp_Name(name);
        fallbackClient.setEmp_EmailId(email);
        fallbackClient.setEmp_Age(0); // Set to 0 to indicate unavailable age
        return fallbackClient;
    }

}
