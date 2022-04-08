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

import com.catalog.productcatalog.entities.Brand;
import com.catalog.productcatalog.exceptions.BadRequestAlertException;
import com.catalog.productcatalog.repository.BrandRepository;
import com.catalog.productcatalog.service.BrandService;
import com.catalog.productcatalog.util.HeaderUtil;
import com.catalog.productcatalog.util.ResponseUtil;

/**
 * REST controller for managing {@link com.catalog.productcatalog.entities.Brand}.
 */
@RestController
@RequestMapping("/api")
public class BrandResource {

    private final Logger log = LoggerFactory.getLogger(BrandResource.class);

    private static final String ENTITY_NAME = "Brand";

    @Value("${gma.clientApp.name}")
    private String applicationName;

    private final BrandService brandservice;

    private final BrandRepository BrandRepository;

    public BrandResource(BrandService brandservice, BrandRepository BrandRepository) {
        this.brandservice = brandservice;
        this.BrandRepository = BrandRepository;
    }

    /**
     * {@code POST  /brands} : Create a new Brand.
     *
     * @param Brand the Brand to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new Brand, or with status {@code 400 (Bad Request)} if the Brand has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/brands")
    public ResponseEntity<Brand> createBrand(@RequestBody Brand Brand) throws URISyntaxException {
        log.debug("REST request to save Brand : {}", Brand);
        if (Brand.getId() != null) {
            throw new BadRequestAlertException("A new Brand cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Brand result = brandservice.save(Brand);
        return ResponseEntity
            .created(new URI("/api/brands/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /brands/:id} : Updates an existing Brand.
     *
     * @param id the id of the Brand to save.
     * @param Brand the Brand to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated Brand,
     * or with status {@code 400 (Bad Request)} if the Brand is not valid,
     * or with status {@code 500 (Internal Server Error)} if the Brand couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/brands/{id}")
    public ResponseEntity<Brand> updateBrand(@PathVariable(value = "id", required = false) final String id, @RequestBody Brand Brand)
        throws URISyntaxException {
        log.debug("REST request to update Brand : {}, {}", id, Brand);
        if (Brand.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, Brand.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!BrandRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Brand result = brandservice.save(Brand);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, Brand.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /brands/:id} : Partial updates given fields of an existing Brand, field will ignore if it is null
     *
     * @param id the id of the Brand to save.
     * @param Brand the Brand to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated Brand,
     * or with status {@code 400 (Bad Request)} if the Brand is not valid,
     * or with status {@code 404 (Not Found)} if the Brand is not found,
     * or with status {@code 500 (Internal Server Error)} if the Brand couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/brands/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Brand> partialUpdateBrand(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody Brand Brand
    ) throws URISyntaxException {
        log.debug("REST request to partial update Brand partially : {}, {}", id, Brand);
        if (Brand.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, Brand.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!BrandRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Brand> result = brandservice.partialUpdate(Brand);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, Brand.getId().toString())
        );
    }

    /**
     * {@code GET  /brands} : get all the brands.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of brands in body.
     */
    @GetMapping("/brands")
    public List<Brand> getAllbrands() {
        log.debug("REST request to get all brands");
        return brandservice.findAll();
    }

    /**
     * {@code GET  /brands/:id} : get the "id" Brand.
     *
     * @param id the id of the Brand to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the Brand, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/brands/{id}")
    public ResponseEntity<Brand> getBrand(@PathVariable String id) {
        log.debug("REST request to get Brand : {}", id);
        Optional<Brand> Brand = brandservice.findOne(id);
        return ResponseUtil.wrapOrNotFound(Brand);
    }

    /**
     * {@code DELETE  /brands/:id} : delete the "id" Brand.
     *
     * @param id the id of the Brand to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/brands/{id}")
    public ResponseEntity<Void> deleteBrand(@PathVariable String id) {
        log.debug("REST request to delete Brand : {}", id);
        brandservice.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /_search/brands?query=:query} : search for the Brand corresponding
     * to the query.
     *
     * @param query the query of the Brand search.
     * @return the result of the search.
     */
//    @GetMapping("/_search/brands")
//    public List<Brand> searchbrands(@RequestParam String query) {
//        log.debug("REST request to search brands for query {}", query);
//        return brandservice.search(query);
//    }
}