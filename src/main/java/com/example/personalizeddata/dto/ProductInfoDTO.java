package com.example.personalizeddata.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductInfoDTO {

    private String productId;
    private String category;
    private String brand;
    private double relevancyScore;

}
