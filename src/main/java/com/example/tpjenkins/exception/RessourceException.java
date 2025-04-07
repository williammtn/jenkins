package com.example.tpjenkins.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

/**
 * The type Ressource exception.
 */
@Getter
@Setter
public class RessourceException extends Exception
{
    private final String errorCode;
    private final HttpStatus status;

    /**
     * Instantiates a new Ressource exception.
     *
     * @param message   the message
     * @param errorCode the error code
     * @param status    the status
     */
    public RessourceException(String message, String errorCode, HttpStatus status)
    {
        super(message);
        this.errorCode = errorCode;
        this.status = status;
    }
}