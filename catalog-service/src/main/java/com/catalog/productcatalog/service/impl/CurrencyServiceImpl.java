package com.catalog.productcatalog.service.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.catalog.productcatalog.entities.Currency;
import com.catalog.productcatalog.repository.CurrencyRepository;
import com.catalog.productcatalog.service.CurrencyService;

@Service
@Transactional
public class CurrencyServiceImpl implements CurrencyService{
	
	private final Logger log = LoggerFactory.getLogger(CurrencyServiceImpl.class);

    private final CurrencyRepository CurrencyRepository;


    public CurrencyServiceImpl(CurrencyRepository CurrencyRepository) {
        this.CurrencyRepository = CurrencyRepository;
       
    }

    @Override
    public Currency save(Currency Currency) {
        log.debug("Request to save Currency : {}", Currency);
        Currency result = CurrencyRepository.save(Currency);
       // CurrencySearchRepository.save(result);
        return result;
    }

    @Override
    public Optional<Currency> partialUpdate(Currency Currency) {
        log.debug("Request to partially update Currency : {}", Currency);

        return CurrencyRepository
            .findById(Currency.getCurrencyId())
            .map(existingCurrency -> {
                if (Currency.getCurrencyName() != null) {
                    existingCurrency.setCurrencyName(Currency.getCurrencyName());
                }

                return existingCurrency;
            })
            .map(CurrencyRepository::save)
            .map(savedCurrency -> {
               // CurrencySearchRepository.save(savedCurrency);

                return savedCurrency;
            });
    }

    @Override
    @Transactional(readOnly = true)
    public List<Currency> findAll() {
        log.debug("Request to get all Countries");
        return CurrencyRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Currency> findOne(String id) {
        log.debug("Request to get Currency : {}", id);
        return CurrencyRepository.findById(id);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete Currency : {}", id);
        CurrencyRepository.deleteById(id);
       // CurrencySearchRepository.deleteById(id);
    }


}
