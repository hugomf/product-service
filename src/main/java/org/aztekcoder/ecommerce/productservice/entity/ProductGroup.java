package org.aztekcoder.ecommerce.productservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@AllArgsConstructor
@Document(collection="product_groups")
public class ProductGroup {

    @Id
    private String id;

    private String name;

}
