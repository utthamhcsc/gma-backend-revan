package com.catalog.productcatalog.service.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.catalog.productcatalog.repository.VariantRepository;
import com.catalog.productcatalog.entities.Variant;
import com.catalog.productcatalog.service.VariantService;

/**
 * Service Implementation for managing {@link Variant}.
 */
@Service
@Transactional
public class VariantServiceImpl implements VariantService {

    private final Logger log = LoggerFactory.getLogger(VariantServiceImpl.class);

    private final VariantRepository variantRepository;


    public VariantServiceImpl(VariantRepository variantRepository) {
        this.variantRepository = variantRepository;
       
    }

    @Override
    public Variant save(Variant Variant) {
        log.debug("Request to save Variant : {}", Variant);
        Variant result = variantRepository.save(Variant);
       // VariantSearchRepository.save(result);
        return result;
    }

    @Override
    public Optional<Variant> partialUpdate(Variant Variant) {
        log.debug("Request to partially update Variant : {}", Variant);

        return variantRepository
            .findById(Variant.getVariantName())
            .map(existingVariant -> {
                if (Variant.getVariantName() != null) {
                    existingVariant.setVariantName(Variant.getVariantName());
                }

                return existingVariant;
            })
            .map(variantRepository::save)
            .map(savedVariant -> {
               // VariantSearchRepository.save(savedVariant);

                return savedVariant;
            });
    }

    @Override
    @Transactional(readOnly = true)
    public List<Variant> findAll() {
        log.debug("Request to get all Countries");
        return variantRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Variant> findOne(String id) {
        log.debug("Request to get Variant : {}", id);
        return variantRepository.findById(id);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete Variant : {}", id);
        variantRepository.deleteById(id);
       // VariantSearchRepository.deleteById(id);

    }
    }
