package com.product.erp.service;

import com.product.erp.domain.Category;
import com.product.erp.dto.CategoryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CategoryService {

    // 전체 카테고리 목록을 조회
    List<Category> getAllCategories();

    // 전체 카테고리 목록을 조회 (페이징 처리, Pageable 객체를 사용)
    Page<Category> getAllCategories(Pageable pageable);

    // 검색어에 따른 카테고리 목록을 페이징 처리하여 조회
    Page<Category> getSearchCategories(String keyword, Pageable pageable);

    // 새로운 카테고리 생성 (CategoryDTO를 이용하여 새 카테고리 추가)
    void createCategory(CategoryDTO categoryDTO);

    // 카테고리 코드 생성 (새로운 카테고리 코드 생성 규칙에 따라 반환)
    String generateCategoryCode();

    // 카테고리 정보 수정 (카테고리 ID와 CategoryDTO를 이용해 카테고리 수정)
    void updateCategory(Long categoryId, CategoryDTO categoryDTO);

    // 특정 카테고리 조회 (카테고리 ID를 기준으로 CategoryDTO 반환)
    CategoryDTO getCategoryById(Long categoryId);  // 메서드 추가
}
