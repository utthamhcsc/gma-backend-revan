package com.catalog.productcatalog.service;

import com.catalog.productcatalog.entities.Product;
import com.catalog.productcatalog.entities.ProductVarients;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Product}.
 */
public interface ProductService {
    /**
     * Save a Product.
     *
     * @param Product the entity to save.
     * @return the persisted entity.
     */
    Product save(Product Product);

    /**
     * Partially updates a Product.
     *
     * @param Product the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Product> partialUpdate(Product Product);

    /**
     * Get all the products.
     *
     * @return the list of entities.
     */
    List<ProductVarients> findAll();

    /**
     * Get the "id" Product.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Product> findOne(Long id);

    /**
     * Delete the "id" Product.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the Product corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    //List<Product> search(String query);
}