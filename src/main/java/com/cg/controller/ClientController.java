package com.cg.controller;

import com.cg.bo.ClientBO;
import com.cg.service.ClientService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/employee-client")
@Tag(name = "Employee Client", description = "Employee management endpoints using RestTemplate and Feign")
public class ClientController {

	private static final Logger logger = LoggerFactory.getLogger(ClientController.class);

	private final ClientService clientService;

	@Autowired
	public ClientController(ClientService clientService) {
		this.clientService = clientService;
	}

	@Operation(summary = "Fetch all employees using RestTemplate",
			description = "Retrieve a list of all employees from the employee service using RestTemplate",
			responses = {
					@ApiResponse(responseCode = "200", description = "Successful retrieval", 
							content = @Content(mediaType = "application/json", 
							schema = @Schema(implementation = ClientBO.class))),
					@ApiResponse(responseCode = "204", description = "No employees found"),
					@ApiResponse(responseCode = "500", description = "Internal Server Error")
	})

	@GetMapping("/rest-template")
	public ResponseEntity<List<ClientBO>> getEmployeesUsingRestTemplate() {
		logger.info("Fetching all employees using rest template");
		List<ClientBO> employees = clientService.getAllClientsFromRest();
		if (employees.isEmpty()) {
			logger.info("No employees found.");
			return ResponseEntity.noContent().build(); 
		}
		return ResponseEntity.ok(employees);
	}

	@Operation(summary = "Fetch employee by ID using RestTemplate",
			description = "Retrieve an employee's details by ID from the employee service using RestTemplate",
			responses = {
					@ApiResponse(responseCode = "200", description = "Successful retrieval", 
							content = @Content(mediaType = "application/json", 
							schema = @Schema(implementation = ClientBO.class))),
					@ApiResponse(responseCode = "404", description = "Employee not found"),
					@ApiResponse(responseCode = "500", description = "Internal Server Error")
	})

	@GetMapping("/rest-template/{id}")
	public ResponseEntity<ClientBO> getEmployeeByIdUsingRestTemplate(
			// @PathVariable("id") Long id) {
			@Parameter(description = "ID of the employee to be fetched") 
			@PathVariable("id") Long id) {
		logger.info("Fetching employee by id using RestTemplate");
		ClientBO clientBO = clientService.getClientByIdFromRest(id);
		if (clientBO != null) {
			logger.info("Employee with id: {} found", id);
			return ResponseEntity.ok(clientBO);
		} else {
			logger.warn("Employee with id: {} not found", id);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}    
	}

	@Operation(summary = "Create a new employee using RestTemplate",
			description = "Create a new employee by sending the employee details to the employee service using RestTemplate",
			responses = {
					@ApiResponse(responseCode = "200", description = "Employee successfully created", 
							content = @Content(mediaType = "application/json", 
							schema = @Schema(implementation = ClientBO.class))),
					@ApiResponse(responseCode = "400", description = "Invalid input data"),
					@ApiResponse(responseCode = "500", description = "Internal Server Error")
	})

	@PostMapping("/rest-template")
	public ResponseEntity<ClientBO> createEmployeeUsingRestTemplate(@RequestBody ClientBO clientBO) {
		logger.info("Creating new employee using RestTemplate");
		ClientBO createdClient = clientService.createClientFromRest(clientBO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdClient);
	}

	@Operation(summary = "Fetch all employees using FeignClient",
			description = "Retrieve a list of all employees from the employee service using FeignClient",
			responses = {
					@ApiResponse(responseCode = "200", description = "Successful retrieval", 
							content = @Content(mediaType = "application/json", 
							schema = @Schema(implementation = ClientBO.class))),
					@ApiResponse(responseCode = "500", description = "Internal Server Error")
	})

	@GetMapping("/feign")
	public ResponseEntity<List<ClientBO>> getEmployeesUsingFeign() {
		logger.info("Fetching all employees using feign client");
		List<ClientBO> employees = clientService.getAllClientsFromFeign();
        if (employees.isEmpty()) {
            logger.info("No employees found.");
            return ResponseEntity.noContent().build(); 
        }
        return ResponseEntity.ok(employees);
	}

	@Operation(summary = "Fetch employee by ID using FeignClient",
			description = "Retrieve an employee's details by ID from the employee service using FeignClient",
			responses = {
					@ApiResponse(responseCode = "200", description = "Successful retrieval", 
							content = @Content(mediaType = "application/json", 
							schema = @Schema(implementation = ClientBO.class))),
					@ApiResponse(responseCode = "404", description = "Employee not found"),
					@ApiResponse(responseCode = "500", description = "Internal Server Error")
	})

	@GetMapping("/feign/{id}")
	public ResponseEntity<ClientBO> getEmployeeByIdUsingFeign(
			//@PathVariable("id") Long id) {
			@Parameter(description = "ID of the employee to be fetched") 
			@PathVariable("id") Long id) {
		logger.info("Fetching employee by id using FeignClient");
		 ClientBO clientBO = clientService.getClientByIdFromFeign(id);
	        if (clientBO != null) {
	            logger.info("Employee with id: {} found", id);
	            return ResponseEntity.ok(clientBO);
	        } else {
	            logger.warn("Employee with id: {} not found", id);
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	        }
	}

	@Operation(summary = "Create a new employee using FeignClient",
			description = "Create a new employee by sending the employee details to the employee service using FeignClient",
			responses = {
					@ApiResponse(responseCode = "200", description = "Employee successfully created", 
							content = @Content(mediaType = "application/json", 
							schema = @Schema(implementation = ClientBO.class))),
					@ApiResponse(responseCode = "400", description = "Invalid input data"),
					@ApiResponse(responseCode = "500", description = "Internal Server Error")
	})
	@PostMapping("/feign")
	public ResponseEntity<ClientBO> createEmployeeUsingFeign(@RequestBody ClientBO clientBO) {
		logger.info("Creating new employee using FeignClient");
		ClientBO createdClient = clientService.createClientFromFeign(clientBO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdClient);
	}
}
