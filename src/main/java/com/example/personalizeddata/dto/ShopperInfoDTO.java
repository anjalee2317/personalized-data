package com.example.personalizeddata.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ShopperInfoDTO {

    private String shopperId;
    private double relevancyScore;

}
