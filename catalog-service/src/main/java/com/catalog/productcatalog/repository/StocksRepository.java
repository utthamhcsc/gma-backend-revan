package com.catalog.productcatalog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.catalog.productcatalog.entities.Stocks;
@Repository
public interface StocksRepository extends JpaRepository<Stocks, Integer>{

}
