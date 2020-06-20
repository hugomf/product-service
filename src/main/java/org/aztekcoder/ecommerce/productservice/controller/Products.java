package org.aztekcoder.ecommerce.productservice.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.aztekcoder.ecommerce.productservice.entity.Product;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Products {
    private List<Product> data;
    private int total;
    private int totalPages;
    private long totalElements;
    private int page;
}
