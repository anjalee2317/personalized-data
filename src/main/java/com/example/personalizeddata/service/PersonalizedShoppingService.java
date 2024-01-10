package com.example.personalizeddata.service;

import com.example.personalizeddata.dto.ProductInfoDTO;
import com.example.personalizeddata.dto.ShopperInfoDTO;
import com.example.personalizeddata.model.ProductMetadata;
import com.example.personalizeddata.model.ShopperProduct;
import com.example.personalizeddata.repository.ProductMetadataRepository;
import com.example.personalizeddata.repository.ShopperProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
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

    public List<ProductInfoDTO> getProductsByShopper(String shopperId, String category, String brand, int limit) {
        List<ShopperProduct> shelf = shopperProductRepository.findByShopperIdAndFilter(shopperId, category, brand, PageRequest.of(0, limit));

        return shelf.stream()
                .map(product -> new ProductInfoDTO(
                        product.getProductId(),
                        product.getProductMetadata().getCategory(),
                        product.getProductMetadata().getBrand(),
                        product.getRelevancyScore()
                ))
                .collect(Collectors.toList());
    }

    public List<ShopperInfoDTO> getShoppersByProduct(String productId, int limit) {
        List<ShopperProduct> shoppers = shopperProductRepository.findByProductId(productId, PageRequest.of(0, limit));

        return shoppers.stream()
                .map(shopper -> new ShopperInfoDTO(shopper.getShopperId(), shopper.getRelevancyScore()))
                .collect(Collectors.toList());
    }
}