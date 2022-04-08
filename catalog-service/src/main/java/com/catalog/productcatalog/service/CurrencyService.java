package com.catalog.productcatalog.service;

import java.util.List;
import java.util.Optional;

import com.catalog.productcatalog.entities.Currency;

public interface CurrencyService {
	/**
     * Save a Currency.
     *
     * @param Currency the entity to save.
     * @return the persisted entity.
     */
    Currency save(Currency Currency);

    /**
     * Partially updates a Currency.
     *
     * @param Currency the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Currency> partialUpdate(Currency Currency);

    /**
     * Get all the Currencys.
     *
     * @return the list of entities.
     */
    List<Currency> findAll();

    /**
     * Get the "id" Currency.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Currency> findOne(String id);

    /**
     * Delete the "id" Currency.
     *
     * @param id the id of the entity.
     */
    void delete(String id);

}
