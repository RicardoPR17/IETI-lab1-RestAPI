package org.adaschool.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class UserIDNotDetectedException extends ResponseStatusException {

    public UserIDNotDetectedException(String name) {
        super(HttpStatus.BAD_REQUEST, "user with name: " + name + " could not be added. Reason: No ID");
    }

}
