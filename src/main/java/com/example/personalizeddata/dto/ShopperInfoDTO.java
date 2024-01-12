package com.example.personalizeddata.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ShopperInfoDTO {

    private String shopperId;
    private double relevancyScore;

}
