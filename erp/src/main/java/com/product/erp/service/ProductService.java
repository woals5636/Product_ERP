package com.product.erp.service;

import com.product.erp.domain.Product;
import com.product.erp.domain.Category;
import com.product.erp.dto.ProductDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ProductService {

    // 전체 제품 목록을 페이징 처리하여 조회
    Page<Product> getAllProducts(Pageable pageable);

    // 검색어에 따라 제품 목록을 페이징 처리하여 조회
    Page<Product> getSearchProducts(String keyword, Pageable pageable);

    // 새로운 제품 코드 생성 ( 제품 카테고리 코드 + 0001 ... )
    String generateProductCode(Category productCategory);

    // 새로운 제품 추가
    void createProduct(ProductDTO productDTO);

    // 제품 이미지를 업로드
    String fileUpload(MultipartFile productImage) throws IOException;

    // 제품 이미지를 저장
    String saveFile(MultipartFile file) throws IOException;

    // 특정 제품 조회
    Product getProductById(Long productId);

    // 제품 정보 수정
    void modifyProduct(Long productId, ProductDTO productDTO);
}
