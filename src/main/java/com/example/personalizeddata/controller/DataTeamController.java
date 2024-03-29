package com.example.personalizeddata.controller;

import com.example.personalizeddata.dto.ProductMetadataRequest;
import com.example.personalizeddata.dto.ShopperDataRequest;
import com.example.personalizeddata.service.PersonalizedShoppingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/internal/data")
@RequiredArgsConstructor
public class DataTeamController {

    private final PersonalizedShoppingService shoppingService;

    @PostMapping("/receiveShopperData")
    public void receiveShopperData(@RequestBody List<ShopperDataRequest> shopperDataRequests) {

        shoppingService.receiveShopperData(shopperDataRequests);
    }

    @PostMapping("/receiveProductMetadata")
    public void receiveProductMetadata(@RequestBody List<ProductMetadataRequest> productMetadataRequests) {

        shoppingService.receiveProductMetadata(productMetadataRequests);
    }
}

