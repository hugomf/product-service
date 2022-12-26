package org.aztekcoder.ecommerce.productservice.controller;

import java.io.File;

import org.aztekcoder.ecommerce.productservice.service.CategoryService;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class CategoryController {

    private CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }
 
    @GetMapping(path = "/load", produces="application/json")
    public ResponseEntity<?> loadCategories() {
        try {
            ClassPathResource resource = new ClassPathResource("/taxonomy-with-ids.en-US.txt");
            File file = resource.getFile();
            String path = file.getAbsolutePath();
            this.categoryService.loadCategories(path);
            return ResponseEntity.ok().build();
        }  catch (Exception exc) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unknown Error", exc);
        }
    }

}
