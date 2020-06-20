package org.aztekcoder.ecommerce.productservice;


import org.aztekcoder.ecommerce.productservice.entity.Product;
import org.aztekcoder.ecommerce.productservice.entity.ProductAttribute;
import org.aztekcoder.ecommerce.productservice.entity.ProductGroup;
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

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(Lifecycle.PER_CLASS)
@Disabled
public class ProductAttributeTest {

    @LocalServerPort
    int randomServerPort;

    private String addedProductAttr;

    private RestTemplate restTemplate;


    @Test
    @Order(1)
    public void testPostProductAttributeSuccess() throws URISyntaxException {
        this.restTemplate = new RestTemplate();

        final String baseUrl = "http://localhost:"+randomServerPort+"/attrs";
        URI uri = new URI(baseUrl);


        ProductAttribute attr = new ProductAttribute("Height", "tall", new ProductGroup(null, "group-name"));
        ResponseEntity<ProductAttribute> result = restTemplate.postForEntity(uri, attr, ProductAttribute.class);
        this.addedProductAttr = result.getBody().getId();
        //Verify request succeed
        assertEquals(201, result.getStatusCodeValue());
    }


    @Test
    @Order(2)
    public void testGetProductSuccess() throws URISyntaxException {
        this.restTemplate = new RestTemplate();

        final String baseUrl = "http://localhost:"+randomServerPort+"/product/" + this.addedProductAttr;
        URI uri = new URI(baseUrl);

        ResponseEntity<String> result = restTemplate.getForEntity(uri  , String.class);

        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    @Order(3)
    public void testGetProductFailed() throws URISyntaxException {
        RestTemplate restTemplate = new RestTemplate();

        final String baseUrl = "http://localhost:"+randomServerPort+"/product/" + this.addedProductAttr;
        URI uri = new URI(baseUrl);

        Assertions.assertThrows(RestClientException.class, ()-> {
            ResponseEntity<String> result = restTemplate.getForEntity(uri + "/22", String.class);
        });

        //assertThat(result.getStatusCode(), equalTo(HttpStatus.NOT_FOUND));
    }

    @Test
    @Order(4)
    public void testPutProductSuccess() throws URISyntaxException {
        RestTemplate restTemplate = new RestTemplate();

        final String baseUrl = "http://localhost:"+randomServerPort+"/product/" +  this.addedProductAttr;
        URI uri = new URI(baseUrl);

        Product product = new Product("another title", null, null);
        HttpEntity<Product> httpProduct = new HttpEntity<Product>(product);
        ResponseEntity<String> result = restTemplate.exchange(uri, HttpMethod.PUT, httpProduct, String.class);
        //Verify request succeed
        assertEquals(201, result.getStatusCodeValue());
    }
}
