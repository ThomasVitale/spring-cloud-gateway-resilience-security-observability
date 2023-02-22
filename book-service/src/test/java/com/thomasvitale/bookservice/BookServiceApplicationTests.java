package com.thomasvitale.bookservice;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import dasniko.testcontainers.keycloak.KeycloakContainer;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
class BookServiceApplicationTests {

   private static KeycloakToken userTokens;

   @Autowired
   private WebTestClient webTestClient;

   @Container
   private static final KeycloakContainer keycloakContainer = new KeycloakContainer("quay.io/keycloak/keycloak:19.0")
      .withRealmImportFile("test-realm-config.json");

   @DynamicPropertySource
   static void dynamicProperties(DynamicPropertyRegistry registry) {
      registry.add("spring.security.oauth2.resourceserver.jwt.issuer-uri",
         () -> keycloakContainer.getAuthServerUrl() + "realms/PolarBookshop");
   }

   @BeforeAll
   static void generateAccessTokens() {
      WebClient webClient = WebClient.builder()
         .baseUrl(keycloakContainer.getAuthServerUrl() + "realms/PolarBookshop/protocol/openid-connect/token")
         .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
         .build();

      userTokens = authenticateWith("isabelle", "password", webClient);
   }

   @Test
   void whenGetRequestThenBooksReturned() {
      webTestClient
         .get()
         .uri("/books")
         .headers(headers -> headers.setBearerAuth(userTokens.accessToken()))
         .exchange()
         .expectStatus().is2xxSuccessful()
         .expectBodyList(Book.class).hasSize(3);
   }

   private static KeycloakToken authenticateWith(String username, String password, WebClient webClient) {
      return webClient
         .post()
         .body(BodyInserters.fromFormData("grant_type", "password")
                  .with("client_id", "polar-test")
                  .with("username", username)
                  .with("password", password)
         )
         .retrieve()
         .bodyToMono(KeycloakToken.class)
         .block();
  }

   private record KeycloakToken(String accessToken) {

      @JsonCreator
      private KeycloakToken(@JsonProperty("access_token") final String accessToken) {
         this.accessToken = accessToken;
      }

  }

}
