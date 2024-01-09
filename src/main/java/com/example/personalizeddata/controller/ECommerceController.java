package com.example.personalizeddata.controller;

import com.example.personalizeddata.service.PersonalizedShoppingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/ecommerce")
@RequiredArgsConstructor
public class ECommerceController {
    private final PersonalizedShoppingService shoppingService;

    @GetMapping("/productsByShopper")
    public List<Map<String, Object>> getProductsByShopper(
            @RequestParam String shopperId,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String brand,
            @RequestParam(defaultValue = "10") int limit
    ) {
        return shoppingService.getProductsByShopper(shopperId, category, brand, limit);
    }

    @GetMapping("/shopperByProduct")
    public List<Map<String, Object>> getShopperByProduct(
            @RequestParam String productId,
            @RequestParam(defaultValue = "10") int limit
    ) {
        return shoppingService.getShoppersByProduct(productId, limit);
    }
}

