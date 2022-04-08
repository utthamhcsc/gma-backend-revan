package com.catalog.productcatalog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.catalog.productcatalog.entities.Currency;

public interface CurrencyRepository extends JpaRepository<Currency, String> {

}
