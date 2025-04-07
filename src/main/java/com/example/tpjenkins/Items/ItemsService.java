package com.example.tpjenkins.Items;



import java.util.List;

import com.example.tpjenkins.Items.dto.CreateItemsForm;
import com.example.tpjenkins.Items.dto.UpdateItemsForm;
import com.example.tpjenkins.exception.RessourceException;

public interface ItemsService {

    List<ItemsModel> getAllItems();

    ItemsModel getItemById(Long id) throws RessourceException;

    ItemsModel createItem(CreateItemsForm form) throws RessourceException;

    ItemsModel updateItem(UpdateItemsForm form) throws RessourceException;

    void deleteItemById(Long id) throws RessourceException;
}