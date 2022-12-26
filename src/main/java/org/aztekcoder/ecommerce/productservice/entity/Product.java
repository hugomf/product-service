package org.aztekcoder.ecommerce.productservice.entity;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Document(collection="products")
public class Product {

    @Id
    private String id;

    @Indexed(unique=true)
    private String sku;
    private String title;

    private String imagePath;

    private String description;

    @Field("price")
    private Price price;

    @Field("attrs")
    private List<ProductAttribute> attrs;

    @Field("extra_attrs")
    private List<ProductAttribute> extra_attrs;

    @Field("variants")
    private List<ProductVariant> variants;

    @Field("categories")
    private List<Category> categories;

    @Version
    @Field("__v")
    private Long version;

    @PersistenceCreator
    public Product(String sku, String title, List<ProductVariant> variants, List<Category> categories) {
        this.sku = sku;
        this.title =  title;
        this.variants = variants;
        this.categories = categories;
    }

    @PersistenceCreator
    public Product(String title, List<ProductVariant> variants, List<Category> categories) {
        this(null, title, variants, categories);
    }
}
