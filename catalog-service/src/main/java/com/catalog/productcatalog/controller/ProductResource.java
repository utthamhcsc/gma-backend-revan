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

import com.catalog.productcatalog.entities.Product;
import com.catalog.productcatalog.entities.ProductVarients;
import com.catalog.productcatalog.exceptions.BadRequestAlertException;
import com.catalog.productcatalog.repository.ProductRepository;
import com.catalog.productcatalog.service.ProductService;
import com.catalog.productcatalog.util.HeaderUtil;
import com.catalog.productcatalog.util.ResponseUtil;

/**
 * REST controller for managing {@link com.catalog.productcatalog.entities.Product}.
 */
@RestController
@RequestMapping("/api")
public class ProductResource {

    private final Logger log = LoggerFactory.getLogger(ProductResource.class);

    private static final String ENTITY_NAME = "Product";

    @Value("${gma.clientApp.name}")
    private String applicationName;

    private final ProductService ProductService;

    private final ProductRepository ProductRepository;

    public ProductResource(ProductService ProductService, ProductRepository ProductRepository) {
        this.ProductService = ProductService;
        this.ProductRepository = ProductRepository;
    }

    /**
     * {@code POST  /products} : Create a new Product.
     *
     * @param Product the Product to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new Product, or with status {@code 400 (Bad Request)} if the Product has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/products")
    public ResponseEntity<Product> createProduct(@RequestBody Product Product) throws URISyntaxException {
        log.debug("REST request to save Product : {}", Product);
        if (Product.getId() != null) {
            throw new BadRequestAlertException("A new Product cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Product result = ProductService.save(Product);
        return ResponseEntity
            .created(new URI("/api/products/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /products/:id} : Updates an existing Product.
     *
     * @param id the id of the Product to save.
     * @param Product the Product to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated Product,
     * or with status {@code 400 (Bad Request)} if the Product is not valid,
     * or with status {@code 500 (Internal Server Error)} if the Product couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/products/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable(value = "id", required = false) final Long id, @RequestBody Product Product)
        throws URISyntaxException {
        log.debug("REST request to update Product : {}, {}", id, Product);
        if (Product.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, Product.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ProductRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Product result = ProductService.save(Product);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, Product.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /products/:id} : Partial updates given fields of an existing Product, field will ignore if it is null
     *
     * @param id the id of the Product to save.
     * @param Product the Product to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated Product,
     * or with status {@code 400 (Bad Request)} if the Product is not valid,
     * or with status {@code 404 (Not Found)} if the Product is not found,
     * or with status {@code 500 (Internal Server Error)} if the Product couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/products/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Product> partialUpdateProduct(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Product Product
    ) throws URISyntaxException {
        log.debug("REST request to partial update Product partially : {}, {}", id, Product);
        if (Product.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, Product.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ProductRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Product> result = ProductService.partialUpdate(Product);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, Product.getId().toString())
        );
    }

    /**
     * {@code GET  /products} : get all the products.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of products in body.
     */
    @GetMapping("/products")
    public List<ProductVarients> getAllproducts() {
        log.debug("REST request to get all products");
        return ProductService.findAll();
    }

    /**
     * {@code GET  /products/:id} : get the "id" Product.
     *
     * @param id the id of the Product to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the Product, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/products/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable Long id) {
        log.debug("REST request to get Product : {}", id);
        Optional<Product> Product = ProductService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Product);
    }

    /**
     * {@code DELETE  /products/:id} : delete the "id" Product.
     *
     * @param id the id of the Product to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/products/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        log.debug("REST request to delete Product : {}", id);
        ProductService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /_search/products?query=:query} : search for the Product corresponding
     * to the query.
     *
     * @param query the query of the Product search.
     * @return the result of the search.
     */
//    @GetMapping("/_search/products")
//    public List<Product> searchproducts(@RequestParam String query) {
//        log.debug("REST request to search products for query {}", query);
//        return ProductService.search(query);
//    }
}