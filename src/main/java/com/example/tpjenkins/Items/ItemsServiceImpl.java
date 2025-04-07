package com.example.tpjenkins.Items;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.tpjenkins.Items.dto.CreateItemsForm;
import com.example.tpjenkins.Items.dto.UpdateItemsForm;
import com.example.tpjenkins.exception.RessourceException;

import lombok.RequiredArgsConstructor;

/**
 * The type Items service.
 */
@Service
@RequiredArgsConstructor
public class ItemsServiceImpl implements ItemsService {

    private final ItemsRepository itemsRepository;

    @Override
    public List<ItemsModel> getAllItems() {
        return itemsRepository.findAll();
    }

    @Override
    public ItemsModel getItemById(Long id) throws RessourceException {
        return itemsRepository.findById(id)
                .orElseThrow(() -> new RessourceException(
                        "ItemNotFound", "Item not found with ID: " + id, HttpStatus.NOT_FOUND));
    }

    @Override
    public ItemsModel createItem(CreateItemsForm form) throws RessourceException {
        ItemsModel item = new ItemsModel();
        item.setTitle(form.getTitle());
        item.setDescription(form.getDescription());
        item.setPrice(form.getPrice());
        item.setImage(form.getImage());

        try {
            return itemsRepository.save(item);
        } catch (DataIntegrityViolationException e) {
            throw new RessourceException(
                    "DuplicateError", "Item already exists.", HttpStatus.CONFLICT);
        } catch (Exception e) {
            throw new RessourceException(
                    "CreateError", "An error occurred while creating the item.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ItemsModel updateItem(UpdateItemsForm form) throws RessourceException {
        ItemsModel existing = getItemById(form.getId());

        if (form.getTitle() != null) existing.setTitle(form.getTitle());
        if (form.getDescription() != null) existing.setDescription(form.getDescription());
        if (form.getImage() != null) existing.setImage(form.getImage());
        existing.setPrice(form.getPrice());

        try {
            return itemsRepository.save(existing);
        } catch (Exception e) {
            throw new RessourceException(
                    "UpdateError", "An error occurred while updating the item.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void deleteItemById(Long id) throws RessourceException {
        if (!itemsRepository.existsById(id)) {
            throw new RessourceException(
                    "DeleteError", "Cannot delete: item not found with ID: " + id, HttpStatus.NOT_FOUND);
        }

        try {
            itemsRepository.deleteById(id);
        } catch (Exception e) {
            throw new RessourceException(
                    "DeleteError", "An error occurred while deleting the item.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
