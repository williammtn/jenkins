package com.example.tpjenkins.Items;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.tpjenkins.Items.dto.CreateItemsForm;
import com.example.tpjenkins.Items.dto.ItemsDTO;
import com.example.tpjenkins.Items.dto.UpdateItemsForm;
import com.example.tpjenkins.Items.mapper.ItemsMapper;
import com.example.tpjenkins.exception.RessourceException;

import java.net.URI;
import java.util.List;

/**
 * The implementation of the Items controller.
 */
@Component
@RequiredArgsConstructor
public class ItemsControllerImpl implements ItemsController {

    @Autowired
    private final ItemsServiceImpl itemsService;
    

    /**
     * Gets all items.
     *
     * @return the all items
     */
    @Override
    public ResponseEntity<List<ItemsDTO>> getAllItems() {
        List<ItemsModel> items = itemsService.getAllItems();
        List<ItemsDTO> itemsDTO = ItemsMapper.INSTANCE.toDtoList(items);
        return ResponseEntity.ok(itemsDTO);
    }

    /**
     * Gets item by id.
     *
     * @param id the item ID
     * @return the item by ID
     * @throws ItemsRessourceException the items resource exception
     */
    @Override
    public ResponseEntity<ItemsDTO> getItemById(Long id) throws ItemsRessourceException {
        try {
            ItemsModel item = itemsService.getItemById(id);
            ItemsDTO itemDTO = ItemsMapper.INSTANCE.toDto(item);
            return ResponseEntity.status(HttpStatus.OK).body(itemDTO);
        } catch (RessourceException e) {
            throw new ItemsRessourceException(id);
        }
    }

    /**
     * Create a new item.
     *
     * @param createItemsForm the form to create a new item
     * @return the created item as DTO
     * @throws RessourceException the resource exception
     */
    @Override
    public ResponseEntity<ItemsDTO> createItem(CreateItemsForm createItemsForm) throws RessourceException {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/items").toUriString());
        return ResponseEntity.created(uri).body(ItemsMapper.INSTANCE.toDto(itemsService.createItem(createItemsForm)));
    }

    /**
     * Update an existing item.
     *
     * @param updateItemsForm the form with item update data
     * @return the updated item as DTO
     * @throws RessourceException the resource exception
     */
    @Override
    public ResponseEntity<ItemsDTO> updateItem(UpdateItemsForm updateItemsForm) throws RessourceException {
        ItemsModel updatedItem = itemsService.updateItem(updateItemsForm);
        ItemsDTO itemDTO = ItemsMapper.INSTANCE.toDto(updatedItem);
        return ResponseEntity.status(HttpStatus.OK).body(itemDTO);
    }

    /**
     * Delete an item by ID.
     *
     * @param id the item ID
     * @return the response indicating the success of the deletion
     * @throws RessourceException the resource exception
     */
    @Override
    public ResponseEntity<Void> deleteItemById(Long id) throws RessourceException {
        itemsService.deleteItemById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
