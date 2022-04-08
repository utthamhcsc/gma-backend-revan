package com.catalog.productcatalog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.catalog.productcatalog.entities.Variant;
/**
 * Spring Data SQL repository for the Variant entity.
 */
@Repository
public interface VariantRepository extends JpaRepository<Variant, String>{

}
