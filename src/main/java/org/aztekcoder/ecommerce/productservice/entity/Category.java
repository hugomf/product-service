package org.aztekcoder.ecommerce.productservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection="categories")
@NoArgsConstructor
@AllArgsConstructor
public class Category {

    @Indexed(unique=true)
    @Id
    private String id;
    private String name;
    private Category parent;
    private String path;

}
