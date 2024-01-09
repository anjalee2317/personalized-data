package com.example.personalizeddata.model;

import lombok.Data;

import java.util.List;

@Data
public class ShopperDataRequest {
    private String shopperId;
    private List<ShopperProduct> shelf;
}


