package com.hamza.microservices.Product;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.testcontainers.containers.MongoDBContainer;

//@Import(TestcontainersConfiguration.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductServiceApplicationTests {

	static MongoDBContainer container = new MongoDBContainer("mongo:latest");

	@LocalServerPort //DI
	private Integer port;

	@BeforeEach
	void setUp() {

		RestAssured.baseURI = "http://localhost";
		//we make dynamic port allocation
		//utilisant l'injection de dependance
		RestAssured.port = port;
	}

	static {
		container.start();
	}

	@Test
	void CreateProductsTest() {
		String requestBody = """
				{
				     "Id": "2",
				     "Name": "s22",
				     "Description": "samsung",
				     "price": 1003213
				 }
				""";

		RestAssured.given().
				port(port)
				.contentType("application/json")
				.body(requestBody)
				.post("/api/product")
				.then().statusCode(201)
				.body("Id", org.hamcrest.Matchers.equalTo("2"))
				.body("Name", org.hamcrest.Matchers.equalTo("s22"))
				.body("Description", org.hamcrest.Matchers.equalTo("samsung"));
	}




}
