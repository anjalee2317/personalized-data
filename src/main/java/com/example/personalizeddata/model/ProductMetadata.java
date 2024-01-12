package com.example.personalizeddata.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "product_metadata", schema = "personalizedshopping")
public class ProductMetadata {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_id")
    private String productId;
    private String category;
    private String brand;

    @OneToMany(mappedBy = "productMetadata")
    private List<ShopperProduct> shopperProducts;

}
