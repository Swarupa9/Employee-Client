//package com.cg.config;
//
//import com.github.tomakehurst.wiremock.client.WireMock;
//import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
//import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
//import org.junit.jupiter.api.extension.RegisterExtension;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class WireMockConfig {
//
//    @RegisterExtension
//    static WireMockExtension wireMockRule = WireMockExtension.newInstance()
//            .options(WireMockConfiguration.wireMockConfig().dynamicPort())
//            .build();
//
//    @Bean
//    public void setupMockEndpoints() {
//        WireMock.stubFor(WireMock.get(WireMock.urlEqualTo("/emp"))
//                .willReturn(WireMock.aResponse()
//                        .withHeader("Content-Type", "application/json")
//                        .withBody("[{\"id\":1,\"emp_Name\":\"John Doe\",\"emp_EmailId\":\"john.doe@example.com\",\"emp_Age\":30}]")));
//    }
//
//}
