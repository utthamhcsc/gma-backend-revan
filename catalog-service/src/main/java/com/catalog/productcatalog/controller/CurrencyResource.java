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

import com.catalog.productcatalog.entities.Currency;
import com.catalog.productcatalog.exceptions.BadRequestAlertException;
import com.catalog.productcatalog.repository.CurrencyRepository;
import com.catalog.productcatalog.service.CurrencyService;
import com.catalog.productcatalog.util.HeaderUtil;
import com.catalog.productcatalog.util.ResponseUtil;

/**
 * REST controller for managing {@link com.catalog.productcatalog.entities.Currency}.
 */
@RestController
@RequestMapping("/api")
public class CurrencyResource {

    private final Logger log = LoggerFactory.getLogger(CurrencyResource.class);

    private static final String ENTITY_NAME = "Currency";

    @Value("${gma.clientApp.name}")
    private String applicationName;

    private final CurrencyService Currencyservice;

    private final CurrencyRepository CurrencyRepository;

    public CurrencyResource(CurrencyService Currencyservice, CurrencyRepository CurrencyRepository) {
        this.Currencyservice = Currencyservice;
        this.CurrencyRepository = CurrencyRepository;
    }

    /**
     * {@code POST  /currency} : Create a new Currency.
     *
     * @param Currency the Currency to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new Currency, or with status {@code 400 (Bad Request)} if the Currency has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/currency")
    public ResponseEntity<Currency> createCurrency(@RequestBody Currency Currency) throws URISyntaxException {
        log.debug("REST request to save Currency : {}", Currency);
        if (Currency.getCurrencyId() != null) {
            throw new BadRequestAlertException("A new Currency cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Currency result = Currencyservice.save(Currency);
        return ResponseEntity
            .created(new URI("/api/currency/" + result.getCurrencyId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getCurrencyId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /currency/:id} : Updates an existing Currency.
     *
     * @param id the id of the Currency to save.
     * @param Currency the Currency to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated Currency,
     * or with status {@code 400 (Bad Request)} if the Currency is not valid,
     * or with status {@code 500 (Internal Server Error)} if the Currency couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/currency/{id}")
    public ResponseEntity<Currency> updateCurrency(@PathVariable(value = "id", required = false) final String id, @RequestBody Currency Currency)
        throws URISyntaxException {
        log.debug("REST request to update Currency : {}, {}", id, Currency);
        if (Currency.getCurrencyId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, Currency.getCurrencyId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!CurrencyRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Currency result = Currencyservice.save(Currency);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, Currency.getCurrencyId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /currency/:id} : Partial updates given fields of an existing Currency, field will ignore if it is null
     * @param id the id of the Currency to save.
     * @param Currency the Currency to update.
     * @return the {@link ResponseEntity} 
     * with status {@code 200 (OK)} and with body the updated Currency,
     * or with status {@code 400 (Bad Request)} if the Currency is not valid,
     * or with status {@code 404 (Not Found)} if the Currency is not found,
     * or with status {@code 500 (Internal Server Error)} if the Currency couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/currency/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Currency> partialUpdateCurrency(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody Currency Currency
    ) throws URISyntaxException {
        log.debug("REST request to partial update Currency partially : {}, {}", id, Currency);
        if (Currency.getCurrencyId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, Currency.getCurrencyId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!CurrencyRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Currency> result = Currencyservice.partialUpdate(Currency);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, Currency.getCurrencyId().toString())
        );
    }

    /**
     * {@code GET  /currency} : get all the Currencys.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of Currencys in body.
     */
    @GetMapping("/currency")
    public List<Currency> getAllCurrencys() {
        log.debug("REST request to get all Currencys");
        return Currencyservice.findAll();
    }

    /**
     * {@code GET  /currency/:id} : get the "id" Currency.
     *
     * @param id the id of the Currency to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the Currency, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/currency/{id}")
    public ResponseEntity<Currency> getCurrency(@PathVariable String id) {
        log.debug("REST request to get Currency : {}", id);
        Optional<Currency> Currency = Currencyservice.findOne(id);
        return ResponseUtil.wrapOrNotFound(Currency);
    }

    /**
     * {@code DELETE  /currency/:id} : delete the "id" Currency.
     *
     * @param id the id of the Currency to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/currency/{id}")
    public ResponseEntity<Void> deleteCurrency(@PathVariable String id) {
        log.debug("REST request to delete Currency : {}", id);
        Currencyservice.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /_search/currency?query=:query} : search for the Currency corresponding
     * to the query.
     *
     * @param query the query of the Currency search.
     * @return the result of the search.
     */
//    @GetMapping("/_search/currency")
//    public List<Currency> searchCurrencys(@RequestParam String query) {
//        log.debug("REST request to search Currencys for query {}", query);
//        return Currencyservice.search(query);
//    }
}
