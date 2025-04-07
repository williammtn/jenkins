package com.example.tpjenkins.Items.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Form used to create a simple item (frontend-aligned).
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateItemsForm {

    @NotNull(message = "Product title cannot be null")
    @NotBlank(message = "Product title cannot be blank")
    private String title;

    private String description;

    @NotNull(message = "Product price cannot be null")
    private double price;

    private String image;
}
