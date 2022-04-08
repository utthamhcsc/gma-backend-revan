package com.catalog.productcatalog.util;

import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import java.util.Optional;

public interface ResponseUtil
{
    static <X> ResponseEntity<X> wrapOrNotFound(final Optional<X> maybeResponse) {
        return wrapOrNotFound(maybeResponse, null);
    }
    
    static <X> ResponseEntity<X> wrapOrNotFound(final Optional<X> maybeResponse, final HttpHeaders header) {
        return (ResponseEntity<X>)maybeResponse.map(response -> ((ResponseEntity.BodyBuilder)ResponseEntity.ok().headers(header)).body(response)).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}

