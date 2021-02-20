package org.aztekcoder.ecommerce.productservice.service;

import org.aztekcoder.ecommerce.productservice.EntityNotFoundException;
import org.aztekcoder.ecommerce.productservice.dao.ProductAttributeRepository;
import org.aztekcoder.ecommerce.productservice.entity.ProductAttribute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductAttributeService {

    private ProductAttributeRepository repository;

    @Autowired
    public ProductAttributeService(ProductAttributeRepository repository) {
        this.repository = repository;
    }

    public List<ProductAttribute> fetchAttributesByGroup(String productGroup) throws EntityNotFoundException {
        return this.repository.
                findByProductGroup(productGroup).
                orElseThrow(()-> new EntityNotFoundException("productGroup:" + productGroup));
    }
}
