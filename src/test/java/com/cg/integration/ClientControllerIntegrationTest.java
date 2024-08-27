package com.cg.integration;

import com.cg.bo.ClientBO;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ClientControllerIntegrationTest {

  @RegisterExtension
  static WireMockExtension wireMockExtension = WireMockExtension.newInstance().build();

  @Autowired
  private TestRestTemplate restTemplate;

  @BeforeEach
  void setup() {
      WireMock.configureFor("localhost", wireMockExtension.getPort());

      // Stubbing GET request for fetching all clients
      WireMock.stubFor(WireMock.get(WireMock.urlEqualTo("/emp"))
              .willReturn(WireMock.aResponse()
                      .withStatus(HttpStatus.OK.value())
                      .withBody("[{\"id\":1,\"emp_Name\":\"John Doe\",\"emp_EmailId\":\"john.doe@example.com\",\"emp_Age\":30}]")));

      // Stubbing POST request for creating a client
      WireMock.stubFor(WireMock.post(WireMock.urlEqualTo("/emp"))
              .willReturn(WireMock.aResponse()
                      .withStatus(HttpStatus.CREATED.value())
                      .withBody("{\"id\":1,\"emp_Name\":\"John Doe\",\"emp_EmailId\":\"john.doe@example.com\",\"emp_Age\":30}")));

      // Stubbing GET request for fetching a client by ID
      WireMock.stubFor(WireMock.get(WireMock.urlPathEqualTo("/emp/1"))
              .willReturn(WireMock.aResponse()
                      .withStatus(HttpStatus.OK.value())
                      .withBody("{\"id\":1,\"emp_Name\":\"John Doe\",\"emp_EmailId\":\"john.doe@example.com\",\"emp_Age\":30}")));
  }

  @Test
  void testGetClientsUsingRestTemplate() {
      ResponseEntity<String> response = restTemplate.getForEntity("/employee-client/rest-template", String.class);
      assertEquals(HttpStatus.OK, response.getStatusCode());
      assertNotNull(response.getBody());
      assertTrue(response.getBody().contains("John Doe"));
  }

  @Test
  void testCreateClientUsingRestTemplate() {
      ClientBO clientBO = new ClientBO(1L, "John Doe", "john.doe@example.com", 30);
      ResponseEntity<ClientBO> response = restTemplate.postForEntity("/employee-client/rest-template", clientBO, ClientBO.class);
      assertEquals(HttpStatus.CREATED, response.getStatusCode());
      assertNotNull(response.getBody());
      assertEquals("John Doe", response.getBody().getEmp_Name());
  }

  @Test
  void testGetClientByIdUsingRestTemplate() {
      ResponseEntity<ClientBO> response = restTemplate.getForEntity("/employee-client/rest-template/1", ClientBO.class);
      assertEquals(HttpStatus.OK, response.getStatusCode());
      assertNotNull(response.getBody());
      assertEquals("John Doe", response.getBody().getEmp_Name());
  }

  @Test
  void testGetClientsUsingFeign() {
      ResponseEntity<String> response = restTemplate.getForEntity("/employee-client/feign", String.class);
      assertEquals(HttpStatus.OK, response.getStatusCode());
      assertNotNull(response.getBody());
      assertTrue(response.getBody().contains("John Doe"));
  }

  @Test
  void testCreateClientUsingFeign() {
      ClientBO clientBO = new ClientBO(1L, "John Doe", "john.doe@example.com", 30);
      ResponseEntity<ClientBO> response = restTemplate.postForEntity("/employee-client/feign", clientBO, ClientBO.class);
      assertEquals(HttpStatus.CREATED, response.getStatusCode());
      assertNotNull(response.getBody());
      assertEquals("John Doe", response.getBody().getEmp_Name());
  }

  @Test
  void testGetClientByIdUsingFeign() {
      ResponseEntity<ClientBO> response = restTemplate.getForEntity("/employee-client/feign/1", ClientBO.class);
      assertEquals(HttpStatus.OK, response.getStatusCode());
      assertNotNull(response.getBody());
      assertEquals("John Doe", response.getBody().getEmp_Name());
  }
}

