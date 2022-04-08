package com.catalog.productcatalog.service;

import java.util.List;
import java.util.Optional;

import com.catalog.productcatalog.entities.Store;

/**
 * Service Interface for managing {@link Store}.
 */
public interface StoreService {
	/**
     * Save a Store.
     *
     * @param Store the entity to save.
     * @return the persisted entity.
     */
    Store save(Store Store);

    /**
     * Partially updates a Store.
     *
     * @param Store the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Store> partialUpdate(Store Store);

    /**
     * Get all the Stores.
     *
     * @return the list of entities.
     */
    List<Store> findAll();

    /**
     * Get the "id" Store.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Store> findOne(String id);

    /**
     * Delete the "id" Store.
     *
     * @param id the id of the entity.
     */
    void delete(String id);

}
