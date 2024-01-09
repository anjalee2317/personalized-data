package com.example.personalizeddata.repository;

import com.example.personalizeddata.model.ShopperProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ShopperProductRepository extends JpaRepository<ShopperProduct, Long> {

    List<ShopperProduct> findByShopperId(String shopperId);

    List<ShopperProduct> findByProductId(String productId);
}

