package com.catalog.productcatalog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
@NoRepositoryBean
public interface MyCurdRepo<T,ID> extends JpaRepository<T, ID> {

}
