package com.cg.controller;

import com.cg.bo.ClientBO;
import com.cg.service.ClientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class ClientControllerTest {

    @Mock
    private ClientService clientService;

    @InjectMocks
    private ClientController clientController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetEmployeesUsingRestTemplate() {
        // Arrange
        ClientBO clientBO = new ClientBO(2L, "Jane Doe", "jane.doe@example.com", 28);
        List<ClientBO> clientList = Collections.singletonList(clientBO);

        when(clientService.getAllClientsFromRest()).thenReturn(clientList);

        // Act
        ResponseEntity<List<ClientBO>> response = clientController.getEmployeesUsingRestTemplate();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals("Jane Doe", response.getBody().get(0).getEmp_Name());
        assertEquals("jane.doe@example.com", response.getBody().get(0).getEmp_EmailId());
        assertEquals(28, response.getBody().get(0).getEmp_Age());
        verify(clientService, times(1)).getAllClientsFromRest();
    }

    @Test
    public void testGetEmployeeByIdUsingRestTemplate() {
        // Arrange
        ClientBO clientBO = new ClientBO(1L, "John Doe", "john.doe@example.com", 30);

        when(clientService.getClientByIdFromRest(1L)).thenReturn(clientBO);

        // Act
        ResponseEntity<ClientBO> response = clientController.getEmployeeByIdUsingRestTemplate(1L);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("John Doe", response.getBody().getEmp_Name());
        assertEquals("john.doe@example.com", response.getBody().getEmp_EmailId());
        assertEquals(30, response.getBody().getEmp_Age());
        verify(clientService, times(1)).getClientByIdFromRest(1L);
    }

    @Test
    public void testCreateEmployeeUsingRestTemplate() {
        // Arrange
        ClientBO clientBO = new ClientBO(1L, "John Doe", "john.doe@example.com", 30);
        when(clientService.createClientFromRest(any(ClientBO.class))).thenReturn(clientBO);

        // Act
        ResponseEntity<ClientBO> response = clientController.createEmployeeUsingRestTemplate(clientBO);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("John Doe", response.getBody().getEmp_Name());
        assertEquals("john.doe@example.com", response.getBody().getEmp_EmailId());
        assertEquals(30, response.getBody().getEmp_Age());
        verify(clientService, times(1)).createClientFromRest(any(ClientBO.class));
    }

    @Test
    public void testGetEmployeesUsingFeign() {
        // Arrange
        ClientBO clientBO = new ClientBO(1L, "John Doe", "john.doe@example.com", 30);
        List<ClientBO> clientList = Collections.singletonList(clientBO);

        when(clientService.getAllClientsFromFeign()).thenReturn(clientList);

        // Act
        ResponseEntity<List<ClientBO>> response = clientController.getEmployeesUsingFeign();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals("John Doe", response.getBody().get(0).getEmp_Name());
        assertEquals("john.doe@example.com", response.getBody().get(0).getEmp_EmailId());
        assertEquals(30, response.getBody().get(0).getEmp_Age());
        verify(clientService, times(1)).getAllClientsFromFeign();
    }

    @Test
    public void testGetEmployeeByIdUsingFeign() {
        // Arrange
        ClientBO clientBO = new ClientBO(1L, "John Doe", "john.doe@example.com", 30);

        when(clientService.getClientByIdFromFeign(1L)).thenReturn(clientBO);

        // Act
        ResponseEntity<ClientBO> response = clientController.getEmployeeByIdUsingFeign(1L);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("John Doe", response.getBody().getEmp_Name());
        assertEquals("john.doe@example.com", response.getBody().getEmp_EmailId());
        assertEquals(30, response.getBody().getEmp_Age());
        verify(clientService, times(1)).getClientByIdFromFeign(1L);
    }

    @Test
    public void testCreateEmployeeUsingFeign() {
        // Arrange
        ClientBO clientBO = new ClientBO(1L, "John Doe", "john.doe@example.com", 30);
        when(clientService.createClientFromFeign(any(ClientBO.class))).thenReturn(clientBO);

        // Act
        ResponseEntity<ClientBO> response = clientController.createEmployeeUsingFeign(clientBO);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("John Doe", response.getBody().getEmp_Name());
        assertEquals("john.doe@example.com", response.getBody().getEmp_EmailId());
        assertEquals(30, response.getBody().getEmp_Age());
        verify(clientService, times(1)).createClientFromFeign(any(ClientBO.class));
    }
}
