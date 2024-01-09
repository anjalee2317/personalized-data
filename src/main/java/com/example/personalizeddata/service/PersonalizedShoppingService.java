package com.example.personalizeddata.service;

import com.example.personalizeddata.model.ProductMetadata;
import com.example.personalizeddata.model.ShopperProduct;
import com.example.personalizeddata.repository.ProductMetadataRepository;
import com.example.personalizeddata.repository.ShopperProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PersonalizedShoppingService {

    private final ShopperProductRepository shopperProductRepository;
    private final ProductMetadataRepository productMetadataRepository;


    public void receiveShopperData(String shopperId, List<ShopperProduct> shelf) {
        // Save shopper data to the database
        shelf.forEach(product -> product.setShopperId(shopperId));
        shopperProductRepository.saveAll(shelf);
    }

    public void receiveProductMetadata(String productId, String category, String brand) {
        // Save product metadata to the database
        ProductMetadata metadata = ProductMetadata.builder()
                .productId(productId)
                .category(category)
                .brand(brand)
                .build();
        productMetadataRepository.save(metadata);
    }

    public List<Map<String, Object>> getProductsByShopper(String shopperId, String category, String brand, int limit) {
        List<ShopperProduct> shelf = shopperProductRepository.findByShopperId(shopperId);

        return shelf.stream()
                .filter(product -> (category == null || category.equals(productMetadataRepository.findByProductId(product.getProductId()).getCategory())) &&
                        (brand == null || brand.equals(productMetadataRepository.findByProductId(product.getProductId()).getBrand())))
                .limit(Math.min(limit, 100))
                .map(product -> {
                    Map<String, Object> productInfo = new HashMap<>();
                    productInfo.put("productId", product.getProductId());
                    productInfo.put("category", productMetadataRepository.findByProductId(product.getProductId()).getCategory());
                    productInfo.put("brand", productMetadataRepository.findByProductId(product.getProductId()).getBrand());
                    productInfo.put("relevancyScore", product.getRelevancyScore());
                    return productInfo;
                })
                .collect(Collectors.toList());
    }


    public List<Map<String, Object>> getShoppersByProduct(String productId, int limit) {
        List<ShopperProduct> shoppers = shopperProductRepository.findByProductId(productId);

        return shoppers.stream()
                .limit(Math.min(limit, 1000))
                .map(shopper -> {
                    Map<String, Object> shopperInfo = new HashMap<>();
                    shopperInfo.put("shopperId", shopper.getShopperId());
                    shopperInfo.put("relevancyScore", shopper.getRelevancyScore());
                    return shopperInfo;
                })
                .collect(Collectors.toList());
    }
}