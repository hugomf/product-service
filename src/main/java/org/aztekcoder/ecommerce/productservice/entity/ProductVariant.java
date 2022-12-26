package org.aztekcoder.ecommerce.productservice.entity;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Document(collection="variants")
public class ProductVariant {


    private String type;

    @Indexed(unique = true)
    private String sku;

    private String name;

    private String value;

    @Version
    @Field("__v")
    private String version;

    public ProductVariant(String sku, String type, String name, String value) {

        if (sku.isEmpty())  {
            this.sku = ObjectId.get().toHexString();
        } else {
            this.sku = sku;
        }

        this.type = type;
        this.name = name;
        this.value = value;
    }

    public ProductVariant(String type, String name, String value) {
        this("", type,name, value);
    }
}
