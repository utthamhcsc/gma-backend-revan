package com.catalog.productcatalog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.catalog.productcatalog.entities.Unit;
@Repository
public interface UnitRepository extends JpaRepository<Unit, Integer>{

}
