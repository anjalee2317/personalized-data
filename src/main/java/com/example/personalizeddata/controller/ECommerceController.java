package com.example.personalizeddata.controller;

import com.example.personalizeddata.dto.ProductInfoDTO;
import com.example.personalizeddata.dto.ShopperInfoDTO;
import com.example.personalizeddata.service.PersonalizedShoppingService;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/ecommerce")
@RequiredArgsConstructor
public class ECommerceController {
    private final PersonalizedShoppingService shoppingService;

    @GetMapping("/productsByShopper")
    public ResponseEntity<Page<ProductInfoDTO>> getProductsByShopper(
            @RequestParam @NotBlank String shopperId,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String brand,
            @RequestParam(defaultValue = "10") @Min(1) @Max(100) int limit,
            @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {

        Page<ProductInfoDTO> results = shoppingService.getProductsByShopper(shopperId, category, brand, limit, page, size);
        return ResponseEntity.ok(results);
    }

    @GetMapping("/shopperByProduct")
    public ResponseEntity<Page<ShopperInfoDTO>> getShopperByProduct(
            @RequestParam @NotBlank String productId,
            @RequestParam(defaultValue = "10") @Min(1) @Max(1000) int limit,
            @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {

        Page<ShopperInfoDTO> results = shoppingService.getShoppersByProduct(productId, limit, page, size);
        return ResponseEntity.ok(results);
    }
}

