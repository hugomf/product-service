package org.aztekcoder.ecommerce.productservice.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class Price {
    private BigDecimal basePrice;
    private String currency;
}
