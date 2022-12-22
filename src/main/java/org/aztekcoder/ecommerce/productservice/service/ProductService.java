package org.aztekcoder.ecommerce.productservice.service;

import org.aztekcoder.ecommerce.productservice.EntityNotFoundException;
import org.aztekcoder.ecommerce.productservice.controller.ProductIds;
import org.aztekcoder.ecommerce.productservice.dao.ProductRepository;
import org.aztekcoder.ecommerce.productservice.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private ProductRepository prodRepo;

    @Autowired
    public ProductService(ProductRepository prodRepo) {
        this.prodRepo = prodRepo;
    }

    public Product saveProduct(Product product) {
        return this.prodRepo.save(product);
    }

    public void updateProduct(String id, Product product)  throws  Exception  {
        Product oldProduct = getProduct(id);
        Product newProduct = populateProduct(oldProduct, product);
        this.prodRepo.save(newProduct);
    }

    private Product populateProduct(Product oldProduct, Product product) {
        oldProduct.setTitle(oldProduct.getTitle());
        oldProduct.setVariants(oldProduct.getVariants());
        return oldProduct;
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
