package com.example.personalizeddata.service;

import com.example.personalizeddata.dto.ProductInfoDTO;
import com.example.personalizeddata.dto.ProductMetadataRequest;
import com.example.personalizeddata.dto.ShopperDataRequest;
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
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class PersonalizedShoppingService {

    private static final Logger logger = LoggerFactory.getLogger(PersonalizedShoppingService.class);

    private final ShopperProductRepository shopperProductRepository;
    private final ProductMetadataRepository productMetadataRepository;


    public void receiveShopperData(List<ShopperDataRequest> shopperDataRequests) {
        // Save shopper data to the database
        List<ShopperProduct> shelfList = shopperDataRequests.stream()
                .flatMap(request -> request.getShelf().stream()
                        .map(product -> ShopperProduct
                                .builder()
                                .shopperId(request.getShopperId())
                                .productId(product.getProductId())
                                .relevancyScore(product.getRelevancyScore()).build()))
                .collect(Collectors.toList());
        shopperProductRepository.saveAll(shelfList);
    }

    public void receiveProductMetadata(List<ProductMetadataRequest> productMetadataRequests) {
        // Save product metadata to the database
        List<ProductMetadata> metadataList = productMetadataRequests.stream()
                .map(request -> {
                    // Check if productId already exists
                    if (productMetadataRepository.existsByProductId(request.getProductId())) {
                        throw new IllegalArgumentException("Duplicate productId: " + request.getProductId());
                    }
                    return ProductMetadata.builder()
                            .productId(request.getProductId())
                            .category(request.getCategory())
                            .brand(request.getBrand())
                            .build();
                })
                .collect(Collectors.toList());
        productMetadataRepository.saveAll(metadataList);
    }

    public Page<ProductInfoDTO> getProductsByShopper(String shopperId, String category, String brand, int limit, int page, int size) {

        try {
            Page<ShopperProduct> shelf =
                    shopperProductRepository.findByShopperIdAndFilter(shopperId, category, brand, PageRequest.of(page, Math.min(size, limit)));

            return shelf
                    .map(product -> ProductInfoDTO.builder()
                            .productId(product.getProductId())
                            .category(product.getProductMetadata().getCategory())
                            .brand(product.getProductMetadata().getBrand())
                            .relevancyScore(product.getRelevancyScore())
                            .build());
        } catch (Exception e) {
            logger.error("An unexpected error occurred while retrieving Products By Shopper", e);
            throw new PersonalizedDataRetrievalException("An error occurred in getProductsByShopper");
        }
    }

    public Page<ShopperInfoDTO> getShoppersByProduct(String productId, int limit, int page, int size) {

        try {
            Page<ShopperProduct> shoppers =
                    shopperProductRepository.findByProductId(productId, PageRequest.of(page, Math.min(size, limit)));

            return shoppers
                    .map(shopper -> ShopperInfoDTO.builder()
                            .shopperId(shopper.getShopperId())
                            .relevancyScore(shopper.getRelevancyScore())
                            .build());
        } catch (Exception e) {
            logger.error("An unexpected error occurred while retrieving Shoppers By Product", e);
            throw new PersonalizedDataRetrievalException("An error occurred in getShoppersByProduct");
        }
    }
}