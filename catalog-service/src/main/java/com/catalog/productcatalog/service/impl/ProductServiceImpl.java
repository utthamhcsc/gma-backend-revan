package com.catalog.productcatalog.service.impl;

import java.util.List;
import java.util.Optional;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.catalog.productcatalog.entities.Product;
import com.catalog.productcatalog.entities.ProductVarients;
import com.catalog.productcatalog.repository.ProductRepository;
import com.catalog.productcatalog.repository.ProductVarientRepository;
import com.catalog.productcatalog.service.ProductService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Product}.
 */
@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    private final Logger log = LoggerFactory.getLogger(ProductServiceImpl.class);

    private final ProductRepository ProductRepository;
    
    private final ProductVarientRepository productVarientRepository;


    public ProductServiceImpl(ProductRepository ProductRepository,ProductVarientRepository productVarientRepository) {
        this.productVarientRepository = productVarientRepository;
		this.ProductRepository = ProductRepository;
       
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW,isolation = Isolation.SERIALIZABLE)
    public Product save(Product Product) {
        log.debug("Request to save Product : {}", Product);
        Product result = ProductRepository.save(Product);
       // ProductSearchRepository.save(result);
        return result;
    }

    @Override
    public Optional<Product> partialUpdate(Product Product) {
        log.debug("Request to partially update Product : {}", Product);

        return ProductRepository
            .findById(Product.getId())
            .map(existingProduct -> {
                if (Product.getProductName() != null) {
                    existingProduct.setProductName(Product.getProductName());
                }

                return existingProduct;
            })
            .map(ProductRepository::save)
            .map(savedProduct -> {
               // ProductSearchRepository.save(savedProduct);

                return savedProduct;
            });
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductVarients> findAll() {
        log.debug("Request to get all products");
        return productVarientRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Product> findOne(Long id) {
        log.debug("Request to get Product : {}", id);
        return ProductRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Product : {}", id);
        ProductRepository.deleteById(id);
       // ProductSearchRepository.deleteById(id);
    }

//    @Override
//    @Transactional(readOnly = true)
//    public List<Product> search(String query) {
//        log.debug("Request to search Countries for query {}", query);
//        return StreamSupport.stream(ProductSearchRepository.search(query).spliterator(), false).collect(Collectors.toList());
//    }
}