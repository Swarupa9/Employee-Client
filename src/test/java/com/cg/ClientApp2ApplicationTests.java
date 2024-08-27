package com.cg;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.config.client.ConfigClientProperties;
import org.springframework.cloud.config.client.ConfigServicePropertySourceLocator;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
//@ActiveProfiles("test")
class ClientApp2ApplicationTests {
	
//	@MockBean
//    private ConfigServicePropertySourceLocator configServicePropertySourceLocator;
//
//    @MockBean
//    private ConfigClientProperties configClientProperties;

	@Test
	void contextLoads() {
	}

}
