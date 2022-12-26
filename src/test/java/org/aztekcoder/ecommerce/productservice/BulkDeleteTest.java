package org.aztekcoder.ecommerce.productservice;


import com.google.common.collect.Lists;
import org.aztekcoder.ecommerce.productservice.controller.ProductIds;
import org.aztekcoder.ecommerce.productservice.controller.Products;
import org.aztekcoder.ecommerce.productservice.entity.Product;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(Lifecycle.PER_CLASS)
//@Disabled

public class BulkDeleteTest {


   // @Value("${product.service.url}")
    String hostUrl;


    @LocalServerPort
    private int port;

    private String addedProductId;

    private RestTemplate restTemplate;


    private String createURLWithPort() {
        return "http://localhost:" + port + "/product";
    }

    private ResponseEntity<String>  fetchProduct(String prodId) throws URISyntaxException {
        this.restTemplate = new RestTemplate();
        final String baseUrl = createURLWithPort() + "/" +  prodId;
        URI uri = new URI(baseUrl);
        return restTemplate.getForEntity(uri  , String.class);
    }

    //@RepeatedTest(5)
    @Test
    public void testDeleteProductsSuccess() throws URISyntaxException {
        this.restTemplate = new RestTemplate();

        final String baseUrl = createURLWithPort();
        URI uri = new URI(baseUrl);

        // Fetch Products
        ResponseEntity<Products> resultFetchBeforeInsert = restTemplate.getForEntity(uri  , Products.class);
        int beforeInsertTotalPages = resultFetchBeforeInsert.getBody().getTotalPages();

        // Init bulk insert
        List<String> ids = Lists.newArrayList();
        ProductIds productIds = new ProductIds(ids);

        // Creating dummy products
        Product product1 = new Product("test11", null, null);
        ResponseEntity<Product> result = restTemplate.postForEntity(uri, product1, Product.class);
        ids.add(result.getBody().getId());

        Product product2 = new Product("test22", null, null);
        ResponseEntity<Product> result2 = restTemplate.postForEntity(uri, product2, Product.class);
        ids.add(result2.getBody().getId());

        Product product3 = new Product("test33", null, null);
        ResponseEntity<Product> result3 = restTemplate.postForEntity(uri, product3, Product.class);
        ids.add(result3.getBody().getId());

        Product product4 = new Product("test33", null, null);
        ResponseEntity<Product> result4 = restTemplate.postForEntity(uri, product3, Product.class);
        ids.add(result4.getBody().getId());

        Product product5 = new Product("test33", null, null);
        ResponseEntity<Product> result5 = restTemplate.postForEntity(uri, product3, Product.class);
        ids.add(result5.getBody().getId());

        Product product6 = new Product("test33", null, null);
        ResponseEntity<Product> result6 = restTemplate.postForEntity(uri, product3, Product.class);
        ids.add(result6.getBody().getId());

        Product product7 = new Product("test33", null, null);
        ResponseEntity<Product> result7 = restTemplate.postForEntity(uri, product3, Product.class);
        ids.add(result7.getBody().getId());

        Product product8 = new Product("test33", null, null);
        ResponseEntity<Product> result8 = restTemplate.postForEntity(uri, product3, Product.class);
        ids.add(result8.getBody().getId());


        Product product9 = new Product("test33", null, null);
        ResponseEntity<Product> result9 = restTemplate.postForEntity(uri, product3, Product.class);
        ids.add(result9.getBody().getId());


        Product product10 = new Product("test33", null, null);
        ResponseEntity<Product> result10 = restTemplate.postForEntity(uri, product3, Product.class);
        ids.add(result10.getBody().getId());

        //check totalElements and totalPages


        ResponseEntity<Products> resultFetchAllBeforeDelete = restTemplate.getForEntity(uri  , Products.class);
        int afterInsertTotalPages = resultFetchAllBeforeDelete.getBody().getTotalPages();
        assertEquals(beforeInsertTotalPages + 1, afterInsertTotalPages);

        // delete all the previously created products
        System.out.println("productsIds:" + productIds);
        HttpEntity<ProductIds> requestEntity = new HttpEntity<ProductIds>(productIds);
        ResponseEntity<?> resp = restTemplate.exchange(uri, HttpMethod.DELETE, requestEntity, ProductIds.class);
        assertEquals(201, result.getStatusCodeValue());


        // Verifing the first 3 ids doesn't exist anymore

        HttpClientErrorException exception = Assertions.assertThrows(HttpClientErrorException.class, ()-> {
            ResponseEntity<String> res1 = fetchProduct(ids.get(0));
        });
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());

        exception = Assertions.assertThrows(HttpClientErrorException.class, ()-> {
            ResponseEntity<String> res1 = fetchProduct(ids.get(1));
        });
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());


        exception = Assertions.assertThrows(HttpClientErrorException.class, ()-> {
            ResponseEntity<String> res1 = fetchProduct(ids.get(2));
        });
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());

        exception = Assertions.assertThrows(HttpClientErrorException.class, ()-> {
            ResponseEntity<String> res1 = fetchProduct(ids.get(3));
        });
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());


        exception = Assertions.assertThrows(HttpClientErrorException.class, ()-> {
            ResponseEntity<String> res1 = fetchProduct(ids.get(4));
        });
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());

        exception = Assertions.assertThrows(HttpClientErrorException.class, ()-> {
            ResponseEntity<String> res1 = fetchProduct(ids.get(5));
        });
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());

        exception = Assertions.assertThrows(HttpClientErrorException.class, ()-> {
            ResponseEntity<String> res1 = fetchProduct(ids.get(6));
        });
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());

        exception = Assertions.assertThrows(HttpClientErrorException.class, ()-> {
            ResponseEntity<String> res1 = fetchProduct(ids.get(7));
        });
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());

        exception = Assertions.assertThrows(HttpClientErrorException.class, ()-> {
            ResponseEntity<String> res1 = fetchProduct(ids.get(8));
        });
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());

        exception = Assertions.assertThrows(HttpClientErrorException.class, ()-> {
            ResponseEntity<String> res1 = fetchProduct(ids.get(9));
        });
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());

        // Last Verification
        //verify the last totalPages is equal to 'beforeInsertTotalPages'

        ResponseEntity<Products> resultFetchAllAfterDelete = restTemplate.getForEntity(uri  , Products.class);
        int afterDeleteTotalPages = resultFetchAllAfterDelete.getBody().getTotalPages();
        assertEquals(beforeInsertTotalPages, afterDeleteTotalPages);

    }
}
