package com.catalog.productcatalog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.catalog.productcatalog.entities.SellerCategory;

public interface SellerCategoryRepository extends JpaRepository<SellerCategory, String>{
List<SellerCategory> findByParentCategory(String parentCategoryId);
	
	@Query("from SellerCategory c where c.parentCategory=null")
	List<SellerCategory> getMainCategory();
}
