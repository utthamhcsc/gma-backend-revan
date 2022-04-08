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

import com.catalog.productcatalog.entities.Store;
import com.catalog.productcatalog.exceptions.BadRequestAlertException;
import com.catalog.productcatalog.repository.StoreRepository;
import com.catalog.productcatalog.service.StoreService;
import com.catalog.productcatalog.util.HeaderUtil;
import com.catalog.productcatalog.util.ResponseUtil;

/**
 * REST controller for managing {@link com.catalog.productcatalog.entities.Store}.
 */
@RestController
@RequestMapping("/api")
public class StoreResource {

    private final Logger log = LoggerFactory.getLogger(StoreResource.class);

    private static final String ENTITY_NAME = "Store";
    

    @Value("${gma.clientApp.name}")
    private String applicationName;

    private final StoreService Storeservice;

    private final StoreRepository StoreRepository;

    public StoreResource(StoreService Storeservice, StoreRepository StoreRepository) {
        this.Storeservice = Storeservice;
        this.StoreRepository = StoreRepository;
    }

    /**
     * {@code POST  /Stores} : Create a new Store.
     *
     * @param Store the Store to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new Store, or with status {@code 400 (Bad Request)} if the Store has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/Stores")
    public ResponseEntity<Store> createStore(@RequestBody Store Store) throws URISyntaxException {
        log.debug("REST request to save Store : {}", Store);
        if (Store.getStoreId() != null) {
            throw new BadRequestAlertException("A new Store cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Store result = Storeservice.save(Store);
        return ResponseEntity
            .created(new URI("/api/stores/" + result.getStoreId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getStoreId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /stores/:id} : Updates an existing Store.
     *
     * @param id the id of the Store to save.
     * @param Store the Store to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated Store,
     * or with status {@code 400 (Bad Request)} if the Store is not valid,
     * or with status {@code 500 (Internal Server Error)} if the Store couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/stores/{id}")
    public ResponseEntity<Store> updateStore(@PathVariable(value = "id", required = false) final String id, @RequestBody Store Store)
        throws URISyntaxException {
        log.debug("REST request to update Store : {}, {}", id, Store);
        if (Store.getStoreId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, Store.getStoreId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!StoreRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Store result = Storeservice.save(Store);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, Store.getStoreId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /stores/:id} : Partial updates given fields of an existing Store, field will ignore if it is null
     *
     * @param id the id of the Store to save.
     * @param Store the Store to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated Store,
     * or with status {@code 400 (Bad Request)} if the Store is not valid,
     * or with status {@code 404 (Not Found)} if the Store is not found,
     * or with status {@code 500 (Internal Server Error)} if the Store couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/stores/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Store> partialUpdateStore(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody Store Store
    ) throws URISyntaxException {
        log.debug("REST request to partial update Store partially : {}, {}", id, Store);
        if (Store.getStoreId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, Store.getStoreId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!StoreRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Store> result = Storeservice.partialUpdate(Store);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, Store.getStoreId().toString())
        );
    }

    /**
     * {@code GET  /Stores} : get all the Stores.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of Stores in body.
     */
    @GetMapping("/Stores")
    public List<Store> getAllStores() {
        log.debug("REST request to get all Stores");
        return Storeservice.findAll();
    }

    /**
     * {@code GET  /stores/:id} : get the "id" Store.
     *
     * @param id the id of the Store to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the Store, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/stores/{id}")
    public ResponseEntity<Store> getStore(@PathVariable String id) {
        log.debug("REST request to get Store : {}", id);
        Optional<Store> Store = Storeservice.findOne(id);
        return ResponseUtil.wrapOrNotFound(Store);
    }

    /**
     * {@code DELETE  /stores/:id} : delete the "id" Store.
     *
     * @param id the id of the Store to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/stores/{id}")
    public ResponseEntity<Void> deleteStore(@PathVariable String id) {
        log.debug("REST request to delete Store : {}", id);
        Storeservice.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /_search/Stores?query=:query} : search for the Store corresponding
     * to the query.
     *
     * @param query the query of the Store search.
     * @return the result of the search.
     */
//    @GetMapping("/_search/Stores")
//    public List<Store> searchStores(@RequestParam String query) {
//        log.debug("REST request to search Stores for query {}", query);
//        return Storeservice.search(query);
//    }
}