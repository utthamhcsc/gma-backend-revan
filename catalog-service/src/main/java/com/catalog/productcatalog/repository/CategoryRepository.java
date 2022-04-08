package com.catalog.productcatalog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.catalog.productcatalog.entities.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, String> {
	
	List<Category> findByParentCategory(String parentCategoryId);
	
	@Query("from Category c where  c.parentCategory=null")
	List<Category> getMainCategory();
}
