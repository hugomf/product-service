package org.aztekcoder.ecommerce.productservice.repository;

import org.aztekcoder.ecommerce.productservice.entity.Category;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface CategoryRepository extends MongoRepository<Category, String> {
    Optional<Category> findByName(String name);
}