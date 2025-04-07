package com.example.tpjenkins.Items;


import org.springframework.http.HttpStatus;

import com.example.tpjenkins.exception.RessourceException;

/**
 * Custom exception for item resources.
 */
public class ItemsRessourceException extends RessourceException {

    /**
     * Instantiates a new Items resource exception when not found by ID.
     *
     * @param id the item ID
     */
    public ItemsRessourceException(Long id) {
        super("Item with ID " + id + " was not found", "ITEM_NOT_FOUND", HttpStatus.NOT_FOUND);
    }

    /**
     * Instantiates a new Items resource exception with custom message and code.
     *
     * @param message the message
     * @param code    the error code
     * @param status  the HTTP status
     */
    public ItemsRessourceException(String message, String code, HttpStatus status) {
        super(message, code, status);
    }
}
