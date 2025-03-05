package com.product.erp.repository;

import com.product.erp.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    // 전체 제품 조회 (페이징 포함)
    Page<Product> findAll(Pageable pageable);

    // 제품명으로 검색 (페이징 포함)
    Page<Product> findByProductNameContaining(String keyword, Pageable pageable);

    // 특정 분류 코드 제품 코드 찾기
    @Query("SELECT p.productCode FROM Product p WHERE p.productCode LIKE :categoryCode% ORDER BY p.productCode DESC LIMIT 1")
    String findLastProductCodeByCategory(@Param("categoryCode") String categoryCode);
}