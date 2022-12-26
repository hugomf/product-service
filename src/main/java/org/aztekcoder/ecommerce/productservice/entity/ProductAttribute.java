package org.aztekcoder.ecommerce.productservice.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Document(collection = "product_attributes")
public class ProductAttribute {

    @Id
    private String id;

    private String name;

    private String value;

    private ProductGroup productGroup;

    @PersistenceCreator
    public ProductAttribute(String name, String value, ProductGroup productGroup) {
        this.name = name;
        this.value = value;
        this.productGroup = productGroup;
    }
}
