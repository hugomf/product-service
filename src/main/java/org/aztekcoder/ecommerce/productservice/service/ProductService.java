package org.aztekcoder.ecommerce.productservice.service;

import org.aztekcoder.ecommerce.productservice.EntityNotFoundException;
import org.aztekcoder.ecommerce.productservice.controller.ProductIds;
import org.aztekcoder.ecommerce.productservice.entity.Product;
import org.aztekcoder.ecommerce.productservice.repository.CrudProductRepository;
import org.aztekcoder.ecommerce.productservice.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private ProductRepository prodRepo;

    private CrudProductRepository crudRepo;

    public ProductService(ProductRepository prodRepo, CrudProductRepository crudRepo) {
        this.prodRepo = prodRepo;
        this.crudRepo = crudRepo;
    }

    public Product saveProduct(Product product) {
        return this.crudRepo.save(product);
    }

    public void updateProduct(String id, Product product)  throws  Exception  {
        Product oldProduct = getProduct(id);
        mapProduct(oldProduct, product);
        this.crudRepo.save(oldProduct);
    }

    private void mapProduct(Product oldProduct, Product product) {
        oldProduct.setTitle(product.getTitle());
        oldProduct.setSku(product.getSku());
        oldProduct.setDescription(product.getDescription());
        oldProduct.setImagePath(product.getImagePath());
        oldProduct.setRating(product.getRating());
        oldProduct.setPrice(product.getPrice());
        oldProduct.setInventory(product.getInventory());
        oldProduct.setCategories(product.getCategories());
        oldProduct.setAttrs(product.getAttrs());
        oldProduct.setVariants(product.getVariants());
    }

    public Product getProduct(String id) throws  Exception {
        return this.prodRepo.
                findById(id).
                orElseThrow(()-> new EntityNotFoundException("id:" + id));
    }

    public Page<Product> getProducts(Integer pageNo, Integer pageSize, String sortBy, Integer asc) {
        Sort.Direction currentDir = (asc==-1) ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(currentDir, sortBy));
        return this.prodRepo.findAll(paging);
    }

    public void deleteProduct(String productId) throws  Exception {
        this.prodRepo.deleteById(productId);
}

    public void deleteProducts(ProductIds products) {
        this.prodRepo.deleteProductByIds(products.getIds());
    }
}
