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
    private List<Product> items;
    private int rows;
    private int totalPages;
    private long totalRecords;
    private int page;
}
