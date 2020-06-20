package org.aztekcoder.ecommerce.productservice.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.math.BigDecimal;
import java.util.List;

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

    private BigDecimal basePrice;

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

    @PersistenceConstructor
    public Product(String sku, String title, List<ProductVariant> variants, List<Category> categories) {
        this.sku = sku;
        this.title =  title;
        this.variants = variants;
        this.categories = categories;
    }

    @PersistenceConstructor
    public Product(String title, List<ProductVariant> variants, List<Category> categories) {
        this(null, title, variants, categories);
    }
}
