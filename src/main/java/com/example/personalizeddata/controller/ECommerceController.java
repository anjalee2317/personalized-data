package com.example.personalizeddata.controller;

import com.example.personalizeddata.dto.ProductInfoDTO;
import com.example.personalizeddata.dto.ShopperInfoDTO;
import com.example.personalizeddata.service.PersonalizedShoppingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/ecommerce")
@RequiredArgsConstructor
public class ECommerceController {
    private final PersonalizedShoppingService shoppingService;

    @GetMapping("/productsByShopper")
    public List<ProductInfoDTO> getProductsByShopper(
            @RequestParam String shopperId,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String brand,
            @RequestParam(defaultValue = "10") int limit
    ) {
        return shoppingService.getProductsByShopper(shopperId, category, brand, limit);
    }

    @GetMapping("/shopperByProduct")
    public List<ShopperInfoDTO> getShopperByProduct(
            @RequestParam String productId,
            @RequestParam(defaultValue = "10") int limit
    ) {
        return shoppingService.getShoppersByProduct(productId, limit);
    }
}

