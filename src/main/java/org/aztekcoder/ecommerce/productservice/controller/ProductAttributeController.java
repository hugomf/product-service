package org.aztekcoder.ecommerce.productservice.controller;

import java.util.List;

import org.aztekcoder.ecommerce.productservice.EntityNotFoundException;
import org.aztekcoder.ecommerce.productservice.entity.ProductAttribute;
import org.aztekcoder.ecommerce.productservice.service.ProductAttributeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;


@RestController
public class ProductAttributeController {

    private ProductAttributeService prodAttrService;

    public ProductAttributeController(ProductAttributeService prodAttrService) {
        this.prodAttrService = prodAttrService;
    }

    @RequestMapping(value = "/attrs/{productGroup}", method= RequestMethod.GET)
    public ResponseEntity<List<ProductAttribute>> getAttributes(@PathVariable("productGroup")  String productGroup){
        try {
            List<ProductAttribute> productAttributes =  this.prodAttrService.fetchAttributesByGroup(productGroup);
            return ResponseEntity.ok().body(productAttributes);
        } catch (EntityNotFoundException pexc) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product Not found", pexc);
        } catch (Exception exc) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unknown Error", exc);
        }
    }

}
