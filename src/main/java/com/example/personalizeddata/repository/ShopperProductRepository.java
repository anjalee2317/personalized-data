package com.example.personalizeddata.repository;

import com.example.personalizeddata.model.ShopperProduct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ShopperProductRepository extends JpaRepository<ShopperProduct, Long> {

    List<ShopperProduct> findByShopperId(String shopperId);

//    Page<ShopperProduct> findByProductId(String productId, Pageable pageable);

    @Query("SELECT sp FROM ShopperProduct sp " +
            "WHERE sp.id IN (" +
            "   SELECT MIN(sp1.id) FROM ShopperProduct sp1 " +
            "   WHERE sp1.productId = :productId " +
            "   GROUP BY sp1.shopperId)")
    Page<ShopperProduct> findByProductId(
            @Param("productId") String productId, Pageable pageable);

    @Query("SELECT sp FROM ShopperProduct sp " +
            "JOIN FETCH sp.productMetadata pm " +
            "WHERE sp.shopperId = :shopperId " +
            "AND (:category IS NULL OR pm.category = :category) " +
            "AND (:brand IS NULL OR pm.brand = :brand)")
    Page<ShopperProduct> findByShopperIdAndFilter(
            @Param("shopperId") String shopperId,
            @Param("category") String category,
            @Param("brand") String brand,
            Pageable pageable);
}