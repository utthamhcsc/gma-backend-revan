package com.catalog.productcatalog.controller;


import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.catalog.productcatalog.entities.Category;
import com.catalog.productcatalog.exceptions.BadRequestAlertException;
import com.catalog.productcatalog.repository.CategoryRepository;
import com.catalog.productcatalog.service.CategoryService;
import com.catalog.productcatalog.util.HeaderUtil;
import com.catalog.productcatalog.util.ResponseUtil;

/**
 * REST controller for managing {@link com.catalog.productcatalog.entities.Category}.
 */
@RestController
@RequestMapping("/api")
public class CategoryResource {

    private final Logger log = LoggerFactory.getLogger(CategoryResource.class);

    private static final String ENTITY_NAME = "Category";

    @Value("${gma.clientApp.name}")
    private String applicationName;

    private final CategoryService CategoryService;

    private final CategoryRepository CategoryRepository;

    public CategoryResource(CategoryService CategoryService, CategoryRepository CategoryRepository) {
        this.CategoryService = CategoryService;
        this.CategoryRepository = CategoryRepository;
    }

    /**
     * {@code POST  /categories} : Create a new Category.
     *
     * @param Category the Category to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new Category, or with status {@code 400 (Bad Request)} if the Category has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/categories")
    public ResponseEntity<Category> createCategory(@RequestBody Category Category) throws URISyntaxException {
        log.debug("REST request to save Category : {}", Category);
        if (Category.getCategoryId() != null) {
            throw new BadRequestAlertException("A new Category cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Category result = CategoryService.save(Category);
        return ResponseEntity
            .created(new URI("/api/categories/" + result.getCategoryId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getCategoryId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /categories/:id} : Updates an existing Category.
     *
     * @param id the id of the Category to save.
     * @param Category the Category to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated Category,
     * or with status {@code 400 (Bad Request)} if the Category is not valid,
     * or with status {@code 500 (Internal Server Error)} if the Category couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/categories/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable(value = "id", required = false) final String id, @RequestBody Category Category)
        throws URISyntaxException {
        log.debug("REST request to update Category : {}, {}", id, Category);
        if (Category.getCategoryId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, Category.getCategoryId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!CategoryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Category result = CategoryService.save(Category);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, Category.getCategoryId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /categories/:id} : Partial updates given fields of an existing Category, field will ignore if it is null
     *
     * @param id the id of the Category to save.
     * @param Category the Category to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated Category,
     * or with status {@code 400 (Bad Request)} if the Category is not valid,
     * or with status {@code 404 (Not Found)} if the Category is not found,
     * or with status {@code 500 (Internal Server Error)} if the Category couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/categories/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Category> partialUpdateCategory(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody Category Category
    ) throws URISyntaxException {
        log.debug("REST request to partial update Category partially : {}, {}", id, Category);
        if (Category.getCategoryId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, Category.getCategoryId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!CategoryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Category> result = CategoryService.partialUpdate(Category);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, Category.getCategoryId().toString())
        );
    }

    /**
     * {@code GET  /categories} : get all the categories.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of categories in body.
     */
    @GetMapping("/categories")
    public List<?> getAllSellerCategories() {
        log.debug("REST request to get all categories");
        return CategoryService.findAll();
    }
    
    /**
     * {@code GET  /categories/child/:parentid} : get all child Category.
     *
     * @param parentid the parentid of the Category to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of categories in body.
     * */
    @GetMapping("/categories/child/{parentid}")
    public List<Category> getSellerChildCategory(@PathVariable String parentid) {
        log.debug("REST request to get Category : {}", parentid);
        List<Category> Category = CategoryService.findAllChildCategory(parentid);
        return Category;
    }
    
    /**
     * {@code GET  /categories/main} .
     *
     * 
     *@return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of categories in body.
     *  */
    @GetMapping("/categories/main")
    public List<Category> getSellermainCategory() {
    	 log.debug("REST request to get all main categories");
        List<Category> Category = CategoryService.findAllMainCategory();
        return Category;
    }

    /**
     * {@code GET  /categories/:id} : get the "id" Category.
     *
     * @param id the id of the Category to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the Category, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/categories/{id}")
    public ResponseEntity<Category> getCategory(@PathVariable String id) {
        log.debug("REST request to get Category : {}", id);
        Optional<Category> Category = CategoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Category);
    }
    
    

    /**
     * {@code DELETE  /categories/:id} : delete the "id" Category.
     *
     * @param id the id of the Category to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/categories/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable String id) {
        log.debug("REST request to delete Category : {}", id);
        CategoryService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /_search/categories?query=:query} : search for the Category corresponding
     * to the query.
     *
     * @param query the query of the Category search.
     * @return the result of the search.
     */
//    @GetMapping("/_search/categories")
//    public List<Category> searchcategories(@RequestParam String query) {
//        log.debug("REST request to search categories for query {}", query);
//        return CategoryService.search(query);
//    }
}