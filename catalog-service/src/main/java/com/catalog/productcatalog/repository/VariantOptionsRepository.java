package com.catalog.productcatalog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.catalog.productcatalog.entities.VariantOptions;
@Repository
public interface VariantOptionsRepository extends JpaRepository<VariantOptions, String>{

}
