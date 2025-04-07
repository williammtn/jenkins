package com.example.tpjenkins.Items;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * The type Product model aligned with frontend Product interface.
 */
@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "items")
public class ItemsModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    @NotNull(message = "Product title cannot be null")
    @NotBlank(message = "Product title cannot be blank")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    @NotNull(message = "Product price cannot be null")
    private double price;

    @Column(name = "image")
    private String image;
}