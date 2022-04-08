package com.catalog.productcatalog.service.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.catalog.productcatalog.entities.VariantOptions;
import com.catalog.productcatalog.repository.VariantOptionsRepository;
import com.catalog.productcatalog.service.VariantOptionsService;

/**
 * Service Implementation for managing {@link VariantOptions}.
 */
@Service
@Transactional
public class VariantOptionsServiceImpl implements VariantOptionsService {

    private final Logger log = LoggerFactory.getLogger(VariantOptionsServiceImpl.class);

    private final VariantOptionsRepository VariantOptionsRepository;


    public VariantOptionsServiceImpl(VariantOptionsRepository VariantOptionsRepository) {
        this.VariantOptionsRepository = VariantOptionsRepository;
       
    }

    @Override
    public VariantOptions save(VariantOptions VariantOptions) {
        log.debug("Request to save VariantOptions : {}", VariantOptions);
        VariantOptions result = VariantOptionsRepository.save(VariantOptions);
       // VariantOptionsSearchRepository.save(result);
        return result;
    }

    @Override
    public Optional<VariantOptions> partialUpdate(VariantOptions VariantOptions) {
        log.debug("Request to partially update VariantOptions : {}", VariantOptions);

        return VariantOptionsRepository
            .findById(VariantOptions.getId())
            .map(existingVariantOptions -> {
                if (VariantOptions.getValue() != null) {
                    existingVariantOptions.setValue(VariantOptions.getValue());
                }

                return existingVariantOptions;
            })
            .map(VariantOptionsRepository::save)
            .map(savedVariantOptions -> {
               // VariantOptionsSearchRepository.save(savedVariantOptions);

                return savedVariantOptions;
            });
    }

    @Override
    @Transactional(readOnly = true)
    public List<VariantOptions> findAll() {
        log.debug("Request to get all Countries");
        return VariantOptionsRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<VariantOptions> findOne(String id) {
        log.debug("Request to get VariantOptions : {}", id);
        return VariantOptionsRepository.findById(id);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete VariantOptions : {}", id);
        VariantOptionsRepository.deleteById(id);
       // VariantOptionsSearchRepository.deleteById(id);
    }
}
