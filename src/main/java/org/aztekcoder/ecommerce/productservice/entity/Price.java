package org.aztekcoder.ecommerce.productservice.entity;

import java.math.BigDecimal;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Price {
    
    private BigDecimal basePrice;
    
    private String currency;
}
