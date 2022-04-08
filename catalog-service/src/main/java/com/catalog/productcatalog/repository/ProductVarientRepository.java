package com.catalog.productcatalog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.catalog.productcatalog.entities.ProductVarients;
@Repository
public interface ProductVarientRepository extends JpaRepository<ProductVarients, Integer>{

}
