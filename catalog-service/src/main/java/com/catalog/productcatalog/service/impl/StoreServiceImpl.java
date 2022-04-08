package com.catalog.productcatalog.service.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.catalog.productcatalog.entities.Store;
import com.catalog.productcatalog.repository.StoreRepository;
import com.catalog.productcatalog.service.StoreService;

@Service
@Transactional
public class StoreServiceImpl implements StoreService{
	
	private final Logger log = LoggerFactory.getLogger(StoreServiceImpl.class);

    private final StoreRepository StoreRepository;


    public StoreServiceImpl(StoreRepository StoreRepository) {
        this.StoreRepository = StoreRepository;
       
    }

    @Override
    public Store save(Store Store) {
        log.debug("Request to save Store : {}", Store);
        Store result = StoreRepository.save(Store);
       // StoreSearchRepository.save(result);
        return result;
    }

    @Override
    public Optional<Store> partialUpdate(Store Store) {
        log.debug("Request to partially update Store : {}", Store);

        return StoreRepository
            .findById(Store.getStoreId())
            .map(existingStore -> {
                if (Store.getStoreName() != null) {
                    existingStore.setStoreName(Store.getStoreName());
                }

                return existingStore;
            })
            .map(StoreRepository::save)
            .map(savedStore -> {
               // StoreSearchRepository.save(savedStore);

                return savedStore;
            });
    }

    @Override
    @Transactional(readOnly = true)
    public List<Store> findAll() {
        log.debug("Request to get all Countries");
        return StoreRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Store> findOne(String id) {
        log.debug("Request to get Store : {}", id);
        return StoreRepository.findById(id);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete Store : {}", id);
        StoreRepository.deleteById(id);
       // StoreSearchRepository.deleteById(id);
    }


}
