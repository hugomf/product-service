package org.aztekcoder.ecommerce.productservice.repository;

import org.aztekcoder.ecommerce.productservice.entity.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import java.util.List;

public interface ProductRepository extends MongoRepository<Product, String> {

    @Query(value="{ '_id': { $in: ?0 } }", delete = true)
    void deleteProductByIds(List<String> ids);
    
}
