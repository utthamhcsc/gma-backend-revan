package com.catalog.productcatalog.service;

import java.util.List;
import java.util.Optional;

import com.catalog.productcatalog.entities.SellerCategory;

public interface SellerCategoryService {
	/**
     * Save a SellerCategory.
     * @param SellerCategory the entity to save.
     * @return the persisted entity.
     */
    SellerCategory save(SellerCategory SellerCategory);
    
    /**
     * Partially updates a SellerCategory.
     * @param SellerCategory the entity to update partially.
     * @return the persisted entity.
     */
    Optional<SellerCategory> partialUpdate(SellerCategory SellerCategory);
    
    /**
     * Get all the SellerCategorys.
     * @return the list of entities.
     */
    List<?> findAll();
    
    /**
     * Get all the SellerMainCategorys.
     * @return the list of entities.
     */
    List<SellerCategory> findAllMainCategory();
    
    /**
     * Get all the SellerChildCategorys.
     * @return the list of entities.
     */
    List<SellerCategory> findAllChildCategory(String parentCategory);
    
    /**
     * Get the "id" SellerCategory.
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SellerCategory> findOne(String id);
    
    /**
     * Delete the "id" SellerCategory.
     * @param id the id of the entity.
     */
    void delete(String id);

}
