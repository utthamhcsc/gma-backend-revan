package com.catalog.productcatalog.service;

import java.util.List;
import java.util.Optional;

import com.catalog.productcatalog.entities.Category;

public interface CategoryService {
	/**
     * Save a Category.
     *
     * @param Category the entity to save.
     * @return the persisted entity.
     */
    Category save(Category Category);

    /**
     * Partially updates a Category.
     *
     * @param Category the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Category> partialUpdate(Category Category);

    /**
     * Get all the Categorys.
     *
     * @return the list of entities.
     */
    List<?> findAll();
    /**
     * Get all the SellerMainCategorys.
     *
     * @return the list of entities.
     */
    List<Category> findAllMainCategory();
    
    /**
     * Get all the SellerChildCategorys.
     *
     * @return the list of entities.
     */
    List<Category> findAllChildCategory(String parentCategory);
    
    /**
     * Get the "id" Category.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Category> findOne(String id);

    /**
     * Delete the "id" Category.
     *
     * @param id the id of the entity.
     */
    void delete(String id);

}
