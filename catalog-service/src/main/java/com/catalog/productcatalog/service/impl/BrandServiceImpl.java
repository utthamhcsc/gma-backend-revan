package com.catalog.productcatalog.service.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.catalog.productcatalog.entities.Brand;
import com.catalog.productcatalog.repository.BrandRepository;
import com.catalog.productcatalog.service.BrandService;

@Service
@Transactional
public class BrandServiceImpl implements BrandService{
	
	private final Logger log = LoggerFactory.getLogger(BrandServiceImpl.class);

    private final BrandRepository BrandRepository;


    public BrandServiceImpl(BrandRepository BrandRepository) {
        this.BrandRepository = BrandRepository;
       
    }

    @Override
    public Brand save(Brand Brand) {
        log.debug("Request to save Brand : {}", Brand);
        Brand result = BrandRepository.save(Brand);
       // BrandSearchRepository.save(result);
        return result;
    }

    @Override
    public Optional<Brand> partialUpdate(Brand Brand) {
        log.debug("Request to partially update Brand : {}", Brand);

        return BrandRepository
            .findById(Brand.getId())
            .map(existingBrand -> {
                if (Brand.getBrandName() != null) {
                    existingBrand.setBrandName(Brand.getBrandName());
                }

                return existingBrand;
            })
            .map(BrandRepository::save)
            .map(savedBrand -> {
               // BrandSearchRepository.save(savedBrand);

                return savedBrand;
            });
    }

    @Override
    @Transactional(readOnly = true)
    public List<Brand> findAll() {
        log.debug("Request to get all Countries");
        return BrandRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Brand> findOne(String id) {
        log.debug("Request to get Brand : {}", id);
        return BrandRepository.findById(id);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete Brand : {}", id);
        BrandRepository.deleteById(id);
       // BrandSearchRepository.deleteById(id);
    }


}
