package org.aztekcoder.ecommerce.productservice.service;

import java.util.List;

import org.aztekcoder.ecommerce.productservice.EntityNotFoundException;
import org.aztekcoder.ecommerce.productservice.entity.ProductAttribute;
import org.aztekcoder.ecommerce.productservice.repository.ProductAttributeRepository;
import org.springframework.stereotype.Service;

@Service
public class ProductAttributeService {

    private ProductAttributeRepository repository;

    public ProductAttributeService(ProductAttributeRepository repository) {
        this.repository = repository;
    }

    public List<ProductAttribute> fetchAttributesByGroup(String productGroup) throws EntityNotFoundException {
        return this.repository.
                findByProductGroup(productGroup).
                orElseThrow(()-> new EntityNotFoundException("productGroup:" + productGroup));
    }
}
