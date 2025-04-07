package com.example.tpjenkins.Items.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Form used to update a simple item (frontend-aligned).
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateItemsForm {

    @NotNull(message = "Product id cannot be null")
    private Long id;

    private String title;
    private String description;
    private double price;
    private String image;
}
