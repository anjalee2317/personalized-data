package com.example.personalizeddata.dto;

import lombok.Data;

@Data
public class ProductMetadataRequest {

    private String productId;
    private String category;
    private String brand;

}