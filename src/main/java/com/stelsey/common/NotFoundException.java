package com.stelsey.common;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class NotFoundException extends ResponseStatusException {

    public NotFoundException(String resource, Long id) {
        super(HttpStatus.NOT_FOUND, resource + " " + id + " not found");
    }
}
