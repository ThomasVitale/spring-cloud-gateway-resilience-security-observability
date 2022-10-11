package com.thomasvitale.bookservice;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BookServiceApplicationTests {

    @Autowired
    WebTestClient webTestClient;

//    @Test
//    void whenGetRequestsThenReturnBooks() { 
//        webTestClient
//                .get()
//                .uri("books")
//                .exchange()
//                .expectStatus().isOk()
//                .expectBodyList(Book.class)
//                .contains(
//                        new Book("Harry Potter"),
//                        new Book("His Dark Materials"),
//                        new Book("The Hobbit"),
//                        new Book("The Lord of the Rings")
//                );
//    }

}
