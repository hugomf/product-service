package org.aztekcoder.ecommerce.productservice.repository;

import org.aztekcoder.ecommerce.productservice.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import java.util.List;

public interface ProductRepository extends MongoRepository<Product, String> {


   // public static final String FILTER_TITLE_AND_DESCRIPTION = "Select p from Product p where UPPER(p.title) like CONCAT('%',UPPER(?1),'%') and UPPER(p.description) like CONCAT('%', UPPER(?2),'%') ";

//    @Query(FILTER_TITLE_AND_DESCRIPTION)
    @Query(value = "{ $or: [ { 'title' : {$regex:?0,$options:'i'} }, { 'description' : {$regex:?0,$options:'i'} } ] }")
    Page<Product> findByTitleLikeAndDescriptionLike(String title, String description, Pageable page);

    @Query(value="{ '_id': { $in: ?0 } }", delete = true)
    void deleteProductByIds(List<String> ids);
    
}
