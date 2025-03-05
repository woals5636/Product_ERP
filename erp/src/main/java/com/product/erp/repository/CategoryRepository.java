package com.product.erp.repository;

import com.product.erp.domain.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    // 페이징 전체 조회
    Page<Category> findAll(Pageable pageable);

    // 검색어를 포함한 페이징 조회
    Page<Category> findByCategoryNameContaining(String keyword, Pageable pageable);

    // 현재까지 생성된 제품 분류 코드 중 가장 마지막 코드 조회
    @Query("SELECT c.categoryCode FROM Category c WHERE c.categoryCode IS NOT NULL ORDER BY c.categoryId DESC LIMIT 1")
    String findLastCategoryCode();

}