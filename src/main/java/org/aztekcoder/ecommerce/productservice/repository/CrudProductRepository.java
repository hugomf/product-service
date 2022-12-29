package org.aztekcoder.ecommerce.productservice.repository;

import org.aztekcoder.ecommerce.productservice.entity.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CrudProductRepository extends MongoRepository<Product, String> {
    
}
