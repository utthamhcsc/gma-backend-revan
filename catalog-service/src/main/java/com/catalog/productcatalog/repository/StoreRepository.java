package com.catalog.productcatalog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.catalog.productcatalog.entities.Store;
@Repository
public interface StoreRepository extends JpaRepository<Store, String>{

}
