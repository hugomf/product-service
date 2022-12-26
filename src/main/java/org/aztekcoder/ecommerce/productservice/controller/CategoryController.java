package org.aztekcoder.ecommerce.productservice.controller;

import org.aztekcoder.ecommerce.productservice.EntityNotFoundException;
import org.aztekcoder.ecommerce.productservice.entity.Category;
import org.aztekcoder.ecommerce.productservice.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;

import java.io.File;
import java.net.URI;

@RestController
public class CategoryController {

    private CategoryService categoryService;

    @Autowired
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
