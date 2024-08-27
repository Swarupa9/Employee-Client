package com.cg.client;

import com.cg.bo.ClientBO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "employee-service", url = "${app1.url}")
public interface EmployeeFeignClient {

    @GetMapping
    List<ClientBO> getAllEmployees();

    @GetMapping("/{id}")
    ClientBO getEmployeeById(@PathVariable("id") Long id);

    @PostMapping
    ClientBO createEmployee(@RequestBody ClientBO clientBO);
}

//url = "http://localhost:8080/emp"