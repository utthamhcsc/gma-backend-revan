package com.catalog.productcatalog.service;



import java.util.List;
import java.util.Optional;

import com.catalog.productcatalog.entities.VariantOptions;


	/**
	 * Service Interface for managing {@link VariantOptions}.
	 */
public interface VariantOptionsService {
	    /**
	     * Save a VariantOptions.
	     *
	     * @param VariantOptions the entity to save.
	     * @return the persisted entity.
	     */
	    VariantOptions save(VariantOptions VariantOptions);

	    /**
	     * Partially updates a VariantOptions.
	     *
	     * @param VariantOptions the entity to update partially.
	     * @return the persisted entity.
	     */
	    Optional<VariantOptions> partialUpdate(VariantOptions VariantOptions);

	    /**
	     * Get all the VariantOptionss.
	     *
	     * @return the list of entities.
	     */
	    List<VariantOptions> findAll();

	    /**
	     * Get the "id" VariantOptions.
	     *
	     * @param id the id of the entity.
	     * @return the entity.
	     */
	    Optional<VariantOptions> findOne(String id);

	    /**
	     * Delete the "id" VariantOptions.
	     *
	     * @param id the id of the entity.
	     */
	    void delete(String id);
}
