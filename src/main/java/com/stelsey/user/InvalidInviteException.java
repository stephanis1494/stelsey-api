package com.stelsey.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class InvalidInviteException extends ResponseStatusException {

    public InvalidInviteException() {
        super(HttpStatus.BAD_REQUEST, "This invite link is invalid or has expired");
    }
}
