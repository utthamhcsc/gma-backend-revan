package com.catalog.productcatalog.service;

import java.util.List;
import java.util.Optional;

import com.catalog.productcatalog.entities.Brand;

/**
 * Service Interface for managing {@link Brand}.
 */
public interface BrandService {
	/**
     * Save a Brand.
     *
     * @param Brand the entity to save.
     * @return the persisted entity.
     */
    Brand save(Brand Brand);

    /**
     * Partially updates a Brand.
     *
     * @param Brand the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Brand> partialUpdate(Brand Brand);

    /**
     * Get all the Brands.
     *
     * @return the list of entities.
     */
    List<Brand> findAll();

    /**
     * Get the "id" Brand.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Brand> findOne(String id);

    /**
     * Delete the "id" Brand.
     *
     * @param id the id of the entity.
     */
    void delete(String id);

}
