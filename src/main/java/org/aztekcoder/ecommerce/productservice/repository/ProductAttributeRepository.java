package org.aztekcoder.ecommerce.productservice.repository;

import org.aztekcoder.ecommerce.productservice.entity.ProductAttribute;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface ProductAttributeRepository extends MongoRepository<ProductAttribute, String> {

    public Optional<List<ProductAttribute>> findByProductGroup(String productGroup);

}
