package com.product.erp.service;

import com.product.erp.domain.Category;
import com.product.erp.dto.CategoryDTO;
import com.product.erp.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    // 페이징 없이 모든 카테고리 조회
    @Override
    public List<Category> getAllCategories() {
        // 전체 카테고리 목록을 페이징 없이 조회
        return categoryRepository.findAll();
    }

    // 페이징 처리된 모든 카테고리 조회
    @Override
    public Page<Category> getAllCategories(Pageable pageable) {
        // 페이징 처리를 적용하여 카테고리 목록 조회
        return categoryRepository.findAll(pageable);
    }

    // 검색어로 카테고리 조회 (페이징 처리)
    @Override
    public Page<Category> getSearchCategories(String keyword, Pageable pageable) {
        // 'keyword'를 포함한 카테고리 목록을 페이징 처리하여 조회
        return categoryRepository.findByCategoryNameContaining(keyword, pageable);
    }

    // 새 카테고리 생성
    @Override
    public void createCategory(CategoryDTO categoryDTO) {
        Category category = new Category();
        category.setCategoryCode(generateCategoryCode());  // 카테고리 코드 생성
        category.setCategoryName(categoryDTO.getCategoryName());
        category.setIsDeleted(categoryDTO.getIsDeleted());

        // 카테고리 저장 후 반환
        categoryRepository.save(category);
    }

    // 카테고리 코드 생성 (최신 카테고리 코드 기반)
    @Override
    public String generateCategoryCode() {
        // 최근 카테고리 코드 조회
        String lastCategoryCode = categoryRepository.findLastCategoryCode();

        char currentPrefix = 'A'; // 기본값 'A'
        int currentNumber = 1;

        // 최근 카테고리 코드가 있으면 접두어와 번호 업데이트
        if (lastCategoryCode != null) {
            currentPrefix = lastCategoryCode.charAt(0); // 접두어 추출
            try {
                currentNumber = Integer.parseInt(lastCategoryCode.substring(1)) + 1; // 번호 증가
            } catch (NumberFormatException e) {
                // 번호 파싱 실패 시 기본 번호로 처리
                currentNumber = 1;
            }
        }

        // 번호가 9999 초과되면 currentPrefix를 변경하고 번호는 1로 초기화
        if (currentNumber > 9999) {
            currentPrefix = (char) (currentPrefix + 1);  // 접두어 증가
            currentNumber = 1;  // 번호 초기화
        }

        String newCategoryCode = String.format("%c%04d", currentPrefix, currentNumber);

        // 'A0001' 형식으로 카테고리 코드 반환
        return String.format("%c%04d", currentPrefix, currentNumber);
    }


    // 카테고리 업데이트
    @Override
    public void updateCategory(Long categoryId, CategoryDTO categoryDTO) {
        // 카테고리 조회 (없으면 예외 발생)
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("해당 카테고리가 존재하지 않습니다."));

        // 카테고리 업데이트
        category.setCategoryName(categoryDTO.getCategoryName());
        category.setIsDeleted(categoryDTO.getIsDeleted());

        // 업데이트된 카테고리 저장
        categoryRepository.save(category);
    }

    // 카테고리 조회 (DTO 반환)
    @Override
    public CategoryDTO getCategoryById(Long categoryId) {
        // 카테고리 조회 (없으면 예외 처리)
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("해당 카테고리가 존재하지 않습니다."));
        // Category 객체를 CategoryDTO로 변환하여 반환
        return new CategoryDTO(category.getCategoryId(), category.getCategoryCode(), category.getCategoryName(), category.getIsDeleted());
    }
}

