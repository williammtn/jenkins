package com.example.tpjenkins.Items;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import com.example.tpjenkins.Items.dto.CreateItemsForm;
import com.example.tpjenkins.Items.dto.ItemsDTO;
import com.example.tpjenkins.Items.dto.UpdateItemsForm;
import com.example.tpjenkins.exception.RessourceException;

/**
 * The interface Items controller (REST endpoints).
 */
@RequestMapping("/items")
@RestController
public interface ItemsController {

    /**
     * Gets all items.
     *
     * @return the all items
     */
    @GetMapping
    ResponseEntity<List<ItemsDTO>> getAllItems();

    /**
     * Gets an item by its ID.
     *
     * @param id the item ID
     * @return the item DTO
     * @throws ItemsRessourceException if item is not found
     */
    @GetMapping("/{id}")
    ResponseEntity<ItemsDTO> getItemById(@PathVariable("id") Long id) throws ItemsRessourceException;

    /**
     * Create a new item.
     *
     * @param createItemsForm the form data for creating an item
     * @return the created item DTO
     * @throws RessourceException if any error occurs during creation
     */
    @PostMapping
    ResponseEntity<ItemsDTO> createItem(@RequestBody CreateItemsForm createItemsForm) throws RessourceException;

    /**
     * Update an existing item.
     *
     * @param updateItemsForm the form data for updating an item
     * @return the updated item DTO
     * @throws RessourceException if any error occurs during update
     */
    @PatchMapping
    ResponseEntity<ItemsDTO> updateItem(@RequestBody UpdateItemsForm updateItemsForm) throws RessourceException;

    /**
     * Delete an item by ID.
     *
     * @param id the item ID
     * @return a response indicating success or failure
     * @throws RessourceException if any error occurs during deletion
     */
    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteItemById(@PathVariable("id") Long id) throws RessourceException;
}
