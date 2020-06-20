package org.aztekcoder.ecommerce.productservice.service;

import org.aztekcoder.ecommerce.productservice.EntityNotFoundException;
import org.aztekcoder.ecommerce.productservice.dao.ProductAttributeRepository;
import org.aztekcoder.ecommerce.productservice.entity.ProductAttribute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductAttributeService {

    private ProductAttributeRepository repo;


    @Autowired
    public ProductAttributeService(ProductAttributeRepository repo) {
        this.repo = repo;
    }

    public List<ProductAttribute> fetchAttributesByGroup(String productGroup) throws EntityNotFoundException {
        return this.repo.
                findByProductGroup(productGroup).
                orElseThrow(()-> new EntityNotFoundException("productGroup:" + productGroup));
    }
}
