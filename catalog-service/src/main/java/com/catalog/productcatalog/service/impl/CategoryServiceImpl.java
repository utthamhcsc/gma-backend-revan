package com.catalog.productcatalog.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.catalog.productcatalog.entities.Category;
import com.catalog.productcatalog.repository.CategoryRepository;
import com.catalog.productcatalog.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {


	private final Logger log = LoggerFactory.getLogger(CategoryServiceImpl.class);

    private final CategoryRepository CategoryRepository;


    public CategoryServiceImpl(CategoryRepository CategoryRepository) {
        this.CategoryRepository = CategoryRepository;
       
    }

    @Override
    public Category save(Category Category) {
        log.debug("Request to save Category : {}", Category);
        Category result = CategoryRepository.save(Category);
       // CategorySearchRepository.save(result);
        return result;
    }

    @Override
    public Optional<Category> partialUpdate(Category Category) {
        log.debug("Request to partially update Category : {}", Category);

        return CategoryRepository
            .findById(Category.getCategoryId())
            .map(existingCategory -> {
                if (Category.getCategoryName() != null) {
                    existingCategory.setCategoryName(Category.getCategoryName());
                }

                return existingCategory;
            })
            .map(CategoryRepository::save)
            .map(savedCategory -> {
               // CategorySearchRepository.save(savedCategory);

                return savedCategory;
            });
    }

    List<?> categoriesList(String parentcategory, List<Category> categories) {
		List<Category> li = new ArrayList<>();
		List<Map<String, Object>> categorymy = new CopyOnWriteArrayList<>();
		if (parentcategory == null) {
			li = categories
					.stream()
					.filter(i -> i.getParentCategory() == null)
					.collect(Collectors.toList());
		} else {
			li = categories
					.stream()
					.filter(i ->i.getParentCategory() != null && i.getParentCategory().equalsIgnoreCase(parentcategory))
					.collect(Collectors.toList());
		}
	for(Category item:li) {
			Map<String, Object> map = new HashMap<>();
			map.put("categoryName", item.getCategoryName());
			map.put("categoryId", item.getCategoryId());
			map.put("children", categoriesList(item.getCategoryId(), categories));
			categorymy.add(map);
		};

		return categorymy;

	}
    
    @Override
    @Transactional(readOnly = true)
    public List<?> findAll() {
        log.debug("Request to get all Category");
        List<Category> categories= CategoryRepository.findAll();
        return categoriesList(null, categories);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Category> findOne(String id) {
        log.debug("Request to get Category : {}", id);
        return CategoryRepository.findById(id);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete Category : {}", id);
        CategoryRepository.deleteById(id);
       // CategorySearchRepository.deleteById(id);
    }

	@Override
	public List<Category> findAllMainCategory() {	
		return CategoryRepository.getMainCategory();
	}

	@Override
	public List<Category> findAllChildCategory(String parentCategory) {

		return CategoryRepository.findByParentCategory(parentCategory);
	}

}
