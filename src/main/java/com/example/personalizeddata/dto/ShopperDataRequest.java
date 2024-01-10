package com.example.personalizeddata.dto;

import com.example.personalizeddata.model.ShopperProduct;
import lombok.Data;

import java.util.List;

@Data
public class ShopperDataRequest {
    private String shopperId;
    private List<ShopperProduct> shelf;
}


