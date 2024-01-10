package com.example.personalizeddata.service;

import com.example.personalizeddata.dto.ProductInfoDTO;
import com.example.personalizeddata.dto.ShopperInfoDTO;
import com.example.personalizeddata.exception.PersonalizedDataRetrievalException;
import com.example.personalizeddata.model.ProductMetadata;
import com.example.personalizeddata.model.ShopperProduct;
import com.example.personalizeddata.repository.ProductMetadataRepository;
import com.example.personalizeddata.repository.ShopperProductRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class PersonalizedShoppingService {

    private static final Logger logger = LoggerFactory.getLogger(PersonalizedShoppingService.class);

    private final ShopperProductRepository shopperProductRepository;
    private final ProductMetadataRepository productMetadataRepository;


    public void receiveShopperData(String shopperId, List<ShopperProduct> shelf) {
        // Save shopper data to the database
        shelf.forEach(product -> product.setShopperId(shopperId));
        shopperProductRepository.saveAll(shelf);
    }

    public void receiveProductMetadata(String productId, String category, String brand) {

        // Check if productId already exists
        if (productMetadataRepository.existsByProductId(productId)) {
            throw new IllegalArgumentException("Duplicate productId: " + productId);
        }
        // Save product metadata to the database
        ProductMetadata metadata = ProductMetadata.builder()
                .productId(productId)
                .category(category)
                .brand(brand)
                .build();
        productMetadataRepository.save(metadata);
    }

    public Page<ProductInfoDTO> getProductsByShopper(String shopperId, String category, String brand, int limit, int page, int size) {

        try {
            // Set a maximum limit of 100
            int maxLimit = 100;
            if (limit > maxLimit) {
                limit = maxLimit;
            }
            Page<ShopperProduct> shelf = shopperProductRepository.findByShopperIdAndFilter(shopperId, category, brand, PageRequest.of(page, Math.min(size, limit)));

            return shelf
                    .map(product -> new ProductInfoDTO(
                            product.getProductId(),
                            product.getProductMetadata().getCategory(),
                            product.getProductMetadata().getBrand(),
                            product.getRelevancyScore()
                    ));
        } catch (Exception e) {
            logger.error("An unexpected error occurred while retrieving Products By Shopper", e);
            throw new PersonalizedDataRetrievalException("An error occurred in getProductsByShopper");
        }
    }

    public Page<ShopperInfoDTO> getShoppersByProduct(String productId, int limit, int page, int size) {

        try {
            // Set a maximum limit of 1000
            int maxLimit = 1000;
            if (limit > maxLimit) {
                limit = maxLimit;
            }
            Page<ShopperProduct> shoppers = shopperProductRepository.findByProductId(productId, PageRequest.of(page, Math.min(size, limit)));

            return shoppers
                    .map(shopper -> new ShopperInfoDTO(
                            shopper.getShopperId(),
                            shopper.getRelevancyScore()));
        } catch (Exception e) {
            logger.error("An unexpected error occurred while retrieving Shoppers By Product", e);
            throw new PersonalizedDataRetrievalException("An error occurred in getShoppersByProduct");
        }
    }
}