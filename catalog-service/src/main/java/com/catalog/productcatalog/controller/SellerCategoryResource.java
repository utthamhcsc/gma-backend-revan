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

import com.catalog.productcatalog.entities.SellerCategory;
import com.catalog.productcatalog.exceptions.BadRequestAlertException;
import com.catalog.productcatalog.repository.SellerCategoryRepository;
import com.catalog.productcatalog.service.SellerCategoryService;
import com.catalog.productcatalog.util.HeaderUtil;
import com.catalog.productcatalog.util.ResponseUtil;

/**
 * REST controller for managing {@link com.catalog.productcatalog.entities.SellerCategory}.
 */
@RestController
@RequestMapping("/api")
public class SellerCategoryResource {

    private final Logger log = LoggerFactory.getLogger(SellerCategoryResource.class);

    private static final String ENTITY_NAME = "SellerCategory";

    @Value("${gma.clientApp.name}")
    private String applicationName;

    private final SellerCategoryService sellerCategoryService;

    private final SellerCategoryRepository SellerCategoryRepository;

    public SellerCategoryResource(SellerCategoryService sellerCategoryService, SellerCategoryRepository SellerCategoryRepository) {
        this.sellerCategoryService = sellerCategoryService;
        this.SellerCategoryRepository = SellerCategoryRepository;
    }

    /**
     * {@code POST  /seller-categories} : Create a new SellerCategory.
     *
     * @param SellerCategory the SellerCategory to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new SellerCategory, or with status {@code 400 (Bad Request)} if the SellerCategory has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/seller-categories")
    public ResponseEntity<SellerCategory> createSellerCategory(@RequestBody SellerCategory SellerCategory) throws URISyntaxException {
        log.debug("REST request to save SellerCategory : {}", SellerCategory);
        if (SellerCategory.getCategoryId() != null) {
            throw new BadRequestAlertException("A new SellerCategory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SellerCategory result = sellerCategoryService.save(SellerCategory);
        return ResponseEntity
            .created(new URI("/api/seller-categories/" + result.getCategoryId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getCategoryId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /seller-categories/:id} : Updates an existing SellerCategory.
     *
     * @param id the id of the SellerCategory to save.
     * @param SellerCategory the SellerCategory to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated SellerCategory,
     * or with status {@code 400 (Bad Request)} if the SellerCategory is not valid,
     * or with status {@code 500 (Internal Server Error)} if the SellerCategory couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/seller-categories/{id}")
    public ResponseEntity<SellerCategory> updateSellerCategory(@PathVariable(value = "id", required = false) final String id, @RequestBody SellerCategory SellerCategory)
        throws URISyntaxException {
        log.debug("REST request to update SellerCategory : {}, {}", id, SellerCategory);
        if (SellerCategory.getCategoryId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, SellerCategory.getCategoryId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!SellerCategoryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        SellerCategory result = sellerCategoryService.save(SellerCategory);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, SellerCategory.getCategoryId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /seller-categories/:id} : Partial updates given fields of an existing SellerCategory, field will ignore if it is null
     *
     * @param id the id of the SellerCategory to save.
     * @param SellerCategory the SellerCategory to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated SellerCategory,
     * or with status {@code 400 (Bad Request)} if the SellerCategory is not valid,
     * or with status {@code 404 (Not Found)} if the SellerCategory is not found,
     * or with status {@code 500 (Internal Server Error)} if the SellerCategory couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/seller-categories/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SellerCategory> partialUpdateSellerCategory(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody SellerCategory SellerCategory
    ) throws URISyntaxException {
        log.debug("REST request to partial update SellerCategory partially : {}, {}", id, SellerCategory);
        if (SellerCategory.getCategoryId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, SellerCategory.getCategoryId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!SellerCategoryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SellerCategory> result = sellerCategoryService.partialUpdate(SellerCategory);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, SellerCategory.getCategoryId().toString())
        );
    }

    /**
     * {@code GET  /seller-categories} : get all the seller-categories.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of seller-categories in body.
     */
    @GetMapping("/seller-categories")
    public List<?> getAllSellerCategories() {
        log.debug("REST request to get all seller-categories");
        return sellerCategoryService.findAll();
    }
    
    /**
     * {@code GET  /seller-categories/child/:parentid} : get all child SellerCategory.
     *
     * @param parentid the parentid of the SellerCategory to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of seller-categories in body.
     * */
    @GetMapping("/seller-categories/child/{parentid}")
    public List<SellerCategory> getSellerChildCategory(@PathVariable String parentid) {
        log.debug("REST request to get SellerCategory : {}", parentid);
        List<SellerCategory> SellerCategory = sellerCategoryService.findAllChildCategory(parentid);
        return SellerCategory;
    }
    
    /**
     * {@code GET  /seller-categories/main} .
     *
     * 
     *@return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of seller-categories in body.
     *  */
    @GetMapping("/seller-categories/main")
    public List<SellerCategory> getSellermainCategory() {
    	 log.debug("REST request to get all main seller-categories");
        List<SellerCategory> SellerCategory = sellerCategoryService.findAllMainCategory();
        return SellerCategory;
    }

    /**
     * {@code GET  /seller-categories/:id} : get the "id" SellerCategory.
     *
     * @param id the id of the SellerCategory to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the SellerCategory, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/seller-categories/{id}")
    public ResponseEntity<SellerCategory> getSellerCategory(@PathVariable String id) {
        log.debug("REST request to get SellerCategory : {}", id);
        Optional<SellerCategory> SellerCategory = sellerCategoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(SellerCategory);
    }
    
    

    /**
     * {@code DELETE  /seller-categories/:id} : delete the "id" SellerCategory.
     *
     * @param id the id of the SellerCategory to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/seller-categories/{id}")
    public ResponseEntity<Void> deleteSellerCategory(@PathVariable String id) {
        log.debug("REST request to delete SellerCategory : {}", id);
        sellerCategoryService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /_search/seller-categories?query=:query} : search for the SellerCategory corresponding
     * to the query.
     *
     * @param query the query of the SellerCategory search.
     * @return the result of the search.
     */
//    @GetMapping("/_search/seller-categories")
//    public List<SellerCategory> searchseller-categories(@RequestParam String query) {
//        log.debug("REST request to search seller-categories for query {}", query);
//        return sellerCategoryService.search(query);
//    }
}