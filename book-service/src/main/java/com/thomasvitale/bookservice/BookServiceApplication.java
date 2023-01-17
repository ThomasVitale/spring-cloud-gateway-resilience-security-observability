package com.thomasvitale.bookservice;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@SpringBootApplication
public class BookServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookServiceApplication.class, args);
    }

	@Bean
	DemoService demoService(WebClient.Builder builder) {
		WebClient client = builder.baseUrl("https://httpbin.org/").build();
		HttpServiceProxyFactory factory = HttpServiceProxyFactory.builder(WebClientAdapter.forClient(client)).build();
		return factory.createClient(DemoService.class);
	}

}

record Book(String title) {}

@RestController
class BookController {

    private static final Logger log = LoggerFactory.getLogger(BookController.class);

	private final DemoService demoService;

	BookController(DemoService demoService) {
		this.demoService = demoService;
	}

	@GetMapping("books")
    public Iterable<Book> getBooks() {
        log.info("Returning list of books in the catalog");
        return List.of(
                new Book("His Dark Materials"),
                new Book("The Hobbit"),
                new Book("The Lord of the Rings"));
    }

	@GetMapping("/")
	public String demo() {
		return demoService.get();
	}

}

interface DemoService {
    @GetExchange("/")
	String get();
}
