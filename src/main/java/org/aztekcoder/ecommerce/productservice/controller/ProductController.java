package org.aztekcoder.ecommerce.productservice.controller;

import java.net.URI;

import org.aztekcoder.ecommerce.productservice.EntityNotFoundException;
import org.aztekcoder.ecommerce.productservice.entity.Product;
import org.aztekcoder.ecommerce.productservice.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


@RestController
public class ProductController {

    private ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @GetMapping("/")
    public ResponseEntity<String> ok() {
        return ResponseEntity.ok("Ok");
    }

    @PostMapping(path = "/product", consumes = "application/json", produces="application/json")
    public ResponseEntity<Product> addProduct(@RequestBody Product product) {
        try {
            product = this.service.saveProduct(product);
            URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(product.getId())
                    .toUri();
            return ResponseEntity.created(location).body(product);
        } catch (Exception exc) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Some Error", exc);
        }
    }

    @PutMapping(path = "/product/{id}", consumes = "application/json", produces="application/json")
    public ResponseEntity<Object> updateProduct(@PathVariable("id") String id, @RequestBody Product product) {
        try {
            this.service.updateProduct(id, product);
            URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(product.getId())
                    .toUri();
            return ResponseEntity.created(location).build();
        } catch (Exception exc) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Some Error", exc);
        }
    }


    @GetMapping(value = "/product/{id}", produces = "application/json")
    public ResponseEntity<Object>  getProduct(@PathVariable("id") String id) {
        try {
            Product product = this.service.getProduct(id);
            return ResponseEntity.ok().body(product);
        } catch (EntityNotFoundException pexc) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product Not found", pexc);
        } catch (Exception exc) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unknown Error", exc);
        }
    }

    @GetMapping(value = "/product", produces = "application/json")
    public ResponseEntity<Object>  getProduct( @RequestParam(defaultValue = "0") Integer page,
                                               @RequestParam(defaultValue = "10") Integer pageSize,
                                               @RequestParam(defaultValue = "id") String sortField,
                                               @RequestParam(defaultValue = "0") Integer sortOrder) {
        try {
            Page<Product> data = this.service.getProducts(page, pageSize, sortField, sortOrder);
            //log.debug("*******Data:{}", data);
            return ResponseEntity.ok().body(new Products(data.getContent(),
                    data.getContent().size(), data.getTotalPages(), data.getTotalElements(), page));
        } catch (Exception exc) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unknown Error", exc);
        }
    }

    @RequestMapping(path = "/product", method=RequestMethod.DELETE , consumes = "application/json", produces="application/json")
    public ResponseEntity<Object> deleteProduct(@RequestBody ProductIds ids) {
           this.service.deleteProducts(ids);
            return ResponseEntity.ok().build();
    }


    @DeleteMapping(value = "/product/{id}", produces = "application/json")
    public ResponseEntity<Object>  deleteProduct(@PathVariable("id") String id) {
        try {
            this.service.deleteProduct(id);
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException pexc) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product Not found", pexc);
        } catch (Exception exc) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unknown Error", exc);
        }
    }
}
