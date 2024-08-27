package com.cg.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.cg.bo.ClientBO;
import com.cg.client.EmployeeClient;
import com.cg.client.EmployeeFeignClient;
import com.cg.service.ClientServiceImpl;

@ExtendWith(MockitoExtension.class)
public class ClientServiceImplTest {

    @Mock
    private EmployeeClient employeeClient;

    @Mock
    private EmployeeFeignClient employeeFeignClient;

    @InjectMocks
    private ClientServiceImpl clientService;

    private ClientBO clientBO;

    @BeforeEach
    public void setUp() {
        clientBO = new ClientBO(1L, "John Doe", "john.doe@example.com", 30);
    }

    @Test
    public void testGetAllClientsFromRest() {
        List<ClientBO> clientList = Arrays.asList(clientBO);
        when(employeeClient.getAllEmployees()).thenReturn(clientList);

        List<ClientBO> result = clientService.getAllClientsFromRest();
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(clientBO, result.get(0));
    }

    @Test
    public void testGetClientByIdFromRest() {
        // Arrange
        Long id = 1L;
        when(employeeClient.getEmployeeById(id)).thenReturn(clientBO);

        // Act
        ClientBO result = clientService.getClientByIdFromRest(id);

        // Assert
        assertNotNull(result);
        assertEquals(clientBO, result);
        assertEquals(id, result.getId());
    }
    
    @Test
    public void testCreateClientFromRest() {
        // Arrange
        ClientBO newClient = new ClientBO(2L, "Jane Doe", "jane.doe@example.com", 28);
        when(employeeClient.createEmployee(newClient)).thenReturn(newClient);

        // Act
        ClientBO result = clientService.createClientFromRest(newClient);

        // Assert
        assertNotNull(result);
        assertEquals(newClient, result);
        assertEquals("Jane Doe", result.getEmp_Name());
    }
    
    @Test
    public void testGetAllClientsFromFeign() {
        List<ClientBO> clientList = Arrays.asList(clientBO);
        when(employeeFeignClient.getAllEmployees()).thenReturn(clientList);

        List<ClientBO> result = clientService.getAllClientsFromFeign();
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(clientBO, result.get(0));
    }
    
    @Test
    public void testGetClientByIdFromFeign() {
        // Arrange
        Long id = 1L;
        when(employeeFeignClient.getEmployeeById(id)).thenReturn(clientBO);

        // Act
        ClientBO result = clientService.getClientByIdFromFeign(id);

        // Assert
        assertNotNull(result);
        assertEquals(clientBO, result);
        assertEquals(id, result.getId());
    }


    @Test
    public void testCreateClientFromFeign() {
        // Arrange
        ClientBO newClient = new ClientBO(2L, "Jane Doe", "jane.doe@example.com", 28);
        when(employeeFeignClient.createEmployee(newClient)).thenReturn(newClient);

        // Act
        ClientBO result = clientService.createClientFromFeign(newClient);

        // Assert
        assertNotNull(result);
        assertEquals(newClient, result);
        assertEquals("Jane Doe", result.getEmp_Name());
    }

}
