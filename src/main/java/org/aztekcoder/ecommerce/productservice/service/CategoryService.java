package org.aztekcoder.ecommerce.productservice.service;
import java.io.BufferedReader;
import java.io.FileReader;

import org.aztekcoder.ecommerce.productservice.entity.Category;
import org.aztekcoder.ecommerce.productservice.repository.CategoryRepository;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

  private CategoryRepository repository;
  
  public CategoryService(CategoryRepository repository) {
    this.repository = repository;
  }

  public void loadCategories(String filePath) throws Exception {
    BufferedReader reader = new BufferedReader(new FileReader(filePath));
    String line;
    boolean skipFirstLine = true;
    while ((line = reader.readLine()) != null) {

      // Skip the first line if the flag is set
      if (skipFirstLine) {
        skipFirstLine = false;
        continue;
      }

      String[] parts = line.split(" - ");
      String id = parts[0];
      String name = parts[1];
      Category parent = null;
      if (name.contains(">")) {
        String[] hierarchy = name.split(">");
        name = hierarchy[hierarchy.length - 1].trim();
        // Busca la categoría padre en la base de datos y la asigna a la variable parent
        String parentName = hierarchy[hierarchy.length - 2].trim();
        parent = repository.findByName(parentName).orElse(null);
      }
      Category category = new Category(id, name, parent, line);
      repository.save(category);
    }
    reader.close();
  }

}


