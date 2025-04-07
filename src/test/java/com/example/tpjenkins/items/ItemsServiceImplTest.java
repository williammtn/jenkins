package com.example.tpjenkins.items;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.tpjenkins.Items.ItemsModel;
import com.example.tpjenkins.Items.ItemsRepository;
import com.example.tpjenkins.Items.ItemsServiceImpl;
import com.example.tpjenkins.Items.dto.CreateItemsForm;
import com.example.tpjenkins.Items.dto.UpdateItemsForm;
import com.example.tpjenkins.exception.RessourceException;

@ExtendWith(SpringExtension.class)
class ItemsServiceImplTest {

    @InjectMocks
    private ItemsServiceImpl itemsService;

    @Mock
    private ItemsRepository itemsRepository;

    private ItemsModel testItem1;
    private ItemsModel testItem2;

    @BeforeEach
    void setUp() {
        testItem1 = new ItemsModel();
        testItem1.setId(1L);
        testItem1.setTitle("Test Item 1");
        testItem1.setDescription("Description 1");
        testItem1.setPrice(99.99);
        testItem1.setImage("image1.jpg");

        testItem2 = new ItemsModel();
        testItem2.setId(2L);
        testItem2.setTitle("Test Item 2");
        testItem2.setDescription("Description 2");
        testItem2.setPrice(149.99);
        testItem2.setImage("image2.jpg");
    }

    @Test
    void getAllItems_ShouldReturnListOfItems() {
        List<ItemsModel> itemsList = List.of(testItem1, testItem2);
        when(itemsRepository.findAll()).thenReturn(itemsList);

        List<ItemsModel> result = itemsService.getAllItems();

        assertNotNull(result);
        assertEquals(itemsList, result);
        verify(itemsRepository, times(1)).findAll();
    }

    @Test
    void getItemById_WithExistingId_ShouldReturnItem() throws RessourceException {
        when(itemsRepository.findById(testItem1.getId())).thenReturn(Optional.of(testItem1));

        ItemsModel result = itemsService.getItemById(testItem1.getId());

        assertNotNull(result);
        assertEquals(testItem1, result);
        verify(itemsRepository, times(1)).findById(testItem1.getId());
    }

    @Test
    void getItemById_WithNonExistingId_ShouldThrowException() {
        when(itemsRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(RessourceException.class, () -> itemsService.getItemById(999L));
        verify(itemsRepository, times(1)).findById(999L);
    }

    @Test
    void createItem_WithValidData_ShouldReturnCreatedItem() throws RessourceException {
        CreateItemsForm createForm = new CreateItemsForm();
        createForm.setTitle("New Item");
        createForm.setDescription("New Description");
        createForm.setPrice(199.99);
        createForm.setImage("new.jpg");

        when(itemsRepository.save(any(ItemsModel.class))).thenReturn(testItem1);

        ItemsModel result = itemsService.createItem(createForm);

        assertNotNull(result);
        assertEquals(testItem1, result);
        verify(itemsRepository, times(1)).save(any(ItemsModel.class));
    }

    @Test
    void createItem_WithDuplicateData_ShouldThrowException() {
        CreateItemsForm createForm = new CreateItemsForm();
        createForm.setTitle("Test Item");
        when(itemsRepository.save(any(ItemsModel.class))).thenThrow(DataIntegrityViolationException.class);

        assertThrows(RessourceException.class, () -> itemsService.createItem(createForm));
        verify(itemsRepository, times(1)).save(any(ItemsModel.class));
    }

    @Test
    void updateItem_WithValidData_ShouldReturnUpdatedItem() throws RessourceException {
        UpdateItemsForm updateForm = new UpdateItemsForm();
        updateForm.setId(1L);
        updateForm.setTitle("Updated Title");
        updateForm.setDescription("Updated Description");
        updateForm.setPrice(299.99);
        updateForm.setImage("updated.jpg");

        ItemsModel updatedItem = new ItemsModel();
        updatedItem.setId(1L);
        updatedItem.setTitle("Updated Title");
        updatedItem.setDescription("Updated Description");
        updatedItem.setPrice(299.99);
        updatedItem.setImage("updated.jpg");

        when(itemsRepository.findById(1L)).thenReturn(Optional.of(testItem1));
        when(itemsRepository.save(any(ItemsModel.class))).thenReturn(updatedItem);

        ItemsModel result = itemsService.updateItem(updateForm);

        assertNotNull(result);
        assertEquals("Updated Title", result.getTitle());
        assertEquals("Updated Description", result.getDescription());
        assertEquals(299.99, result.getPrice());
        assertEquals("updated.jpg", result.getImage());
        verify(itemsRepository, times(1)).findById(1L);
        verify(itemsRepository, times(1)).save(any(ItemsModel.class));
    }

    @Test
    void updateItem_WithNonExistingId_ShouldThrowException() {
        UpdateItemsForm updateForm = new UpdateItemsForm();
        updateForm.setId(999L);
        when(itemsRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(RessourceException.class, () -> itemsService.updateItem(updateForm));
        verify(itemsRepository, times(1)).findById(999L);
        verify(itemsRepository, never()).save(any(ItemsModel.class));
    }

    @Test
    void deleteItemById_WithExistingId_ShouldDeleteSuccessfully() throws RessourceException {
        when(itemsRepository.existsById(1L)).thenReturn(true);
        doNothing().when(itemsRepository).deleteById(1L);

        assertDoesNotThrow(() -> itemsService.deleteItemById(1L));
        verify(itemsRepository, times(1)).existsById(1L);
        verify(itemsRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteItemById_WithNonExistingId_ShouldThrowException() {
        when(itemsRepository.existsById(999L)).thenReturn(false);

        assertThrows(RessourceException.class, () -> itemsService.deleteItemById(999L));
        verify(itemsRepository, times(1)).existsById(999L);
        verify(itemsRepository, never()).deleteById(999L);
    }
}