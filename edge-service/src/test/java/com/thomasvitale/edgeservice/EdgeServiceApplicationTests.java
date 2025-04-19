package com.thomasvitale.edgeservice;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class EdgeServiceApplicationTests {

    @MockitoBean
	ReactiveClientRegistrationRepository clientRegistrationRepository;

    @Test
    void contextLoads() {
    }

}
