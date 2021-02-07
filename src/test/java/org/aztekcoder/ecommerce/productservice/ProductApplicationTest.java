package org.aztekcoder.ecommerce.productservice;


import com.google.common.collect.Lists;
import org.aztekcoder.ecommerce.productservice.entity.Category;
import org.aztekcoder.ecommerce.productservice.entity.Product;
import org.aztekcoder.ecommerce.productservice.entity.ProductVariant;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(Lifecycle.PER_CLASS)
//@Disabled
public class ProductApplicationTest {


   // @Value("${product.service.url}")
    String hostUrl;

    @LocalServerPort
    private int port;

    private String addedProductId;

    private RestTemplate restTemplate;

    private String createURLWithPort() {
        return "http://localhost:" + port + "/product";
    }

    @Test
    @Order(1)
    public void testPostProductSuccess() throws URISyntaxException {
        this.restTemplate = new RestTemplate();

        final String baseUrl = createURLWithPort();
        URI uri = new URI(baseUrl);

        List<ProductVariant> attrs = Lists.newArrayList(
                new ProductVariant("ColorType","Color","Red"),
                new ProductVariant("ColorType","Color","Blue"),
                new ProductVariant("ColorType","Color","Green"));

        List<Category> categories = Lists.newArrayList(
            new Category("Books", null),
            new Category("Magazines", null),
            new Category("Publications", null)
        );

        Product product = new Product("test1", attrs, categories);

        ResponseEntity<Product> result = restTemplate.postForEntity(uri, product, Product.class);
        this.addedProductId = result.getBody().getId();
        //Verify request succeed
        assertEquals(201, result.getStatusCodeValue());
    }


    @Test
    @Order(2)
    public void testGetProductSuccess() throws URISyntaxException {
        this.restTemplate = new RestTemplate();

        final String baseUrl = createURLWithPort() + "/" +  this.addedProductId;

        URI uri = new URI(baseUrl);

        ResponseEntity<String> result = restTemplate.getForEntity(uri  , String.class);

        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    @Order(3)
    public void testGetProductFailed() throws URISyntaxException {
        RestTemplate restTemplate = new RestTemplate();

        final String baseUrl = createURLWithPort();

        URI uri = new URI(baseUrl);

        Assertions.assertThrows(RestClientException.class, ()-> {
            ResponseEntity<String> result = restTemplate.getForEntity(uri + "/22", String.class);
        });

    }

    @Test
    @Order(4)
    public void testPutProductSuccess() throws URISyntaxException {
        RestTemplate restTemplate = new RestTemplate();

        final String baseUrl = createURLWithPort() + "/" + this.addedProductId;

        URI uri = new URI(baseUrl);

        Product product = new Product("another title", null, null);
        HttpEntity<Product> httpProduct = new HttpEntity<Product>(product);
        ResponseEntity<String> result = restTemplate.exchange(uri, HttpMethod.PUT, httpProduct, String.class);
        //Verify request succeed
        assertEquals(201, result.getStatusCodeValue());
    }

    @Test
    @Order(5)
    public void testDeleteProductSuccess() throws URISyntaxException {
        this.restTemplate = new RestTemplate();

        final String baseUrl = createURLWithPort() + "/" +  this.addedProductId;

        URI uri = new URI(baseUrl);
        try {
            restTemplate.delete(uri);
        } catch(Exception e){
            fail("Shouldn't throw exception:" + e.getMessage());
        }
    }


}
