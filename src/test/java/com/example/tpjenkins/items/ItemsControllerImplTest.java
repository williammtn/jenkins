package com.example.tpjenkins.items;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.tpjenkins.Items.ItemsControllerImpl;
import com.example.tpjenkins.Items.ItemsModel;
import com.example.tpjenkins.Items.ItemsServiceImpl;
import com.example.tpjenkins.Items.dto.CreateItemsForm;
import com.example.tpjenkins.Items.dto.ItemsDTO;
import com.example.tpjenkins.Items.dto.UpdateItemsForm;
import com.example.tpjenkins.Items.mapper.ItemsMapper;
import com.example.tpjenkins.exception.RessourceException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ItemsControllerImpl.class)
class ItemsControllerImplTest {

    private static final String BASE_PATH = "/items";

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ItemsServiceImpl itemsService;

    @MockBean
    private ItemsMapper itemsMapper;

    @Autowired
    private ObjectMapper objectMapper;

    private ItemsModel testItem;
    private ItemsDTO testItemDTO;

    @BeforeEach
    void setUp() {
        testItem = new ItemsModel();
        testItem.setId(1L);
        testItem.setTitle("Test Item");
        testItem.setDescription("Test Description");
        testItem.setPrice(99.99);
        testItem.setImage("test.jpg");

        testItemDTO = new ItemsDTO();
        testItemDTO.setId(1L);
        testItemDTO.setTitle("Test Item");
        testItemDTO.setDescription("Test Description");
        testItemDTO.setPrice(99.99);
        testItemDTO.setImage("test.jpg");
    }

    @Test
    void getAllItems_ShouldReturnListOfItems() throws Exception {
        List<ItemsModel> items = Arrays.asList(testItem);
        List<ItemsDTO> itemsDTOs = Arrays.asList(testItemDTO);

        when(itemsService.getAllItems()).thenReturn(items);
        when(itemsMapper.toDtoList(items)).thenReturn(itemsDTOs);

        MvcResult result = mvc.perform(get(BASE_PATH)
                .characterEncoding("UTF-8"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        List<ItemsDTO> returnedDTOs = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                objectMapper.getTypeFactory().constructCollectionType(List.class, ItemsDTO.class));

        assertEquals(itemsDTOs.size(), returnedDTOs.size());
        assertEquals(itemsDTOs.get(0).getTitle(), returnedDTOs.get(0).getTitle());
    }

    @Test
    void getItemById_WithValidId_ShouldReturnItem() throws Exception {
        when(itemsService.getItemById(1L)).thenReturn(testItem);
        when(itemsMapper.toDto(testItem)).thenReturn(testItemDTO);

        mvc.perform(get(BASE_PATH + "/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(testItemDTO.getId()))
                .andExpect(jsonPath("$.title").value(testItemDTO.getTitle()));
    }

    @Test
    void createItem_WithValidData_ShouldReturnCreatedItem() throws Exception {
        CreateItemsForm createForm = new CreateItemsForm();
        createForm.setTitle("New Item");
        createForm.setDescription("New Description");
        createForm.setPrice(149.99);
        createForm.setImage("new.jpg");

        when(itemsService.createItem(any(CreateItemsForm.class))).thenReturn(testItem);
        when(itemsMapper.toDto(testItem)).thenReturn(testItemDTO);

        mvc.perform(post(BASE_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createForm)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.title").value(testItemDTO.getTitle()));
    }

    // @Test
    // void updateItem_WithValidData_ShouldReturnUpdatedItem() throws Exception {
    //     UpdateItemsForm updateForm = new UpdateItemsForm();
    //     updateForm.setId(1L);
    //     updateForm.setTitle("Updated Item");
    //     updateForm.setDescription("Updated Description");
    //     updateForm.setPrice(199.99);
    //     updateForm.setImage("updated.jpg");

    //     when(itemsService.updateItem(any(UpdateItemsForm.class))).thenReturn(testItem);
    //     when(itemsMapper.toDto(testItem)).thenReturn(testItemDTO);

    //     mvc.perform(patch(BASE_PATH + "/{id}", 1L)
    //             .contentType(MediaType.APPLICATION_JSON)
    //             .content(objectMapper.writeValueAsString(updateForm)))
    //             .andExpect(status().isOk())
    //             .andExpect(content().contentType(MediaType.APPLICATION_JSON))
    //             .andExpect(jsonPath("$.id").value(testItemDTO.getId()));
    // }

    @Test
    void deleteItemById_WithValidId_ShouldReturnNoContent() throws Exception {
        doNothing().when(itemsService).deleteItemById(1L);

        mvc.perform(delete(BASE_PATH + "/{id}", 1L))
                .andExpect(status().isNoContent());

        verify(itemsService, times(1)).deleteItemById(1L);
    }


}