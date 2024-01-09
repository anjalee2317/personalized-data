package com.example.personalizeddata.repository;

import com.example.personalizeddata.model.ProductMetadata;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductMetadataRepository extends JpaRepository<ProductMetadata, Long> {

    ProductMetadata findByProductId(String productId);

}