package com.catalog.productcatalog.service;

import java.util.List;
import java.util.Optional;

import com.catalog.productcatalog.entities.Variant;


	/**
	 * Service Interface for managing {@link Variant}.
	 */
public interface VariantService {
	    /**
	     * Save a Variant.
	     *
	     * @param Variant the entity to save.
	     * @return the persisted entity.
	     */
	    Variant save(Variant Variant);

	    /**
	     * Partially updates a Variant.
	     *
	     * @param Variant the entity to update partially.
	     * @return the persisted entity.
	     */
	    Optional<Variant> partialUpdate(Variant Variant);

	    /**
	     * Get all the variants.
	     *
	     * @return the list of entities.
	     */
	    List<Variant> findAll();

	    /**
	     * Get the "id" Variant.
	     *
	     * @param id the id of the entity.
	     * @return the entity.
	     */
	    Optional<Variant> findOne(String id);

	    /**
	     * Delete the "id" Variant.
	     *
	     * @param id the id of the entity.
	     */
	    void delete(String id);
}
