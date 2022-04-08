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
import com.catalog.productcatalog.entities.SellerCategory;
import com.catalog.productcatalog.repository.SellerCategoryRepository;
import com.catalog.productcatalog.service.SellerCategoryService;

@Service
@Transactional
public class SellerCategoryServiceImpl implements SellerCategoryService{

	private final Logger log = LoggerFactory.getLogger(SellerCategoryServiceImpl.class);

    private final SellerCategoryRepository SellerCategoryRepository;


    public SellerCategoryServiceImpl(SellerCategoryRepository SellerCategoryRepository) {
        this.SellerCategoryRepository = SellerCategoryRepository;
       
    }

    @Override
    public SellerCategory save(SellerCategory SellerCategory) {
        log.debug("Request to save SellerCategory : {}", SellerCategory);
        SellerCategory result = SellerCategoryRepository.save(SellerCategory);
       // SellerCategorySearchRepository.save(result);
        return result;
    }

    @Override
    public Optional<SellerCategory> partialUpdate(SellerCategory SellerCategory) {
        log.debug("Request to partially update SellerCategory : {}", SellerCategory);

        return SellerCategoryRepository
            .findById(SellerCategory.getCategoryId())
            .map(existingSellerCategory -> {
                if (SellerCategory.getCategoryName() != null) {
                    existingSellerCategory.setCategoryName(SellerCategory.getCategoryName());
                }

                return existingSellerCategory;
            })
            .map(SellerCategoryRepository::save)
            .map(savedSellerCategory -> {
               // SellerCategorySearchRepository.save(savedSellerCategory);

                return savedSellerCategory;
            });
    }

    List<?> categoriesList(String parentcategory, List<SellerCategory> categories) {
		List<SellerCategory> li = new ArrayList<>();
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
	for(SellerCategory item:li) {
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
        log.debug("Request to get all sellercategory");
        List<SellerCategory> categories= SellerCategoryRepository.findAll();
        return categoriesList(null, categories);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<SellerCategory> findOne(String id) {
        log.debug("Request to get SellerCategory : {}", id);
        return SellerCategoryRepository.findById(id);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete SellerCategory : {}", id);
        SellerCategoryRepository.deleteById(id);
       // SellerCategorySearchRepository.deleteById(id);
    }

	@Override
	public List<SellerCategory> findAllMainCategory() {
		
		return SellerCategoryRepository.getMainCategory();
	}

	@Override
	public List<SellerCategory> findAllChildCategory(String parentCategory) {

		return SellerCategoryRepository.findByParentCategory(parentCategory);
	}

	
}
