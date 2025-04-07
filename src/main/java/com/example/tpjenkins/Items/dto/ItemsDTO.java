package com.example.tpjenkins.Items.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for exposing minimal product data to the frontend.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemsDTO {

    private Long id;
    private String title;
    private double price;
    private String image;
    private String description;
}